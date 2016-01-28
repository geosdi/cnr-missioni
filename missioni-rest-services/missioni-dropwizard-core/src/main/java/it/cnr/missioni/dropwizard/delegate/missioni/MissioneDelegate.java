package it.cnr.missioni.dropwizard.delegate.missioni;


import com.fasterxml.uuid.EthernetAddress;
import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedGenerator;
import com.google.maps.model.GeocodingResult;
import it.cnr.missioni.el.dao.IMissioneDAO;
import it.cnr.missioni.el.dao.IUserDAO;
import it.cnr.missioni.el.model.search.builder.MissioneSearchBuilder;
import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.model.user.User;
import it.cnr.missioni.model.validator.ICNRMissionValidator;
import it.cnr.missioni.notification.dispatcher.MissioniMailDispatcher;
import it.cnr.missioni.notification.message.factory.NotificationMessageFactory;
import it.cnr.missioni.notification.spring.configuration.CNRMissioniEmail;
import it.cnr.missioni.notification.support.itext.missione.MissionePDFBuilder;
import it.cnr.missioni.notification.support.itext.rimborso.RimborsoPDFBuilder;
import it.cnr.missioni.rest.api.request.NotificationMissionRequest;
import it.cnr.missioni.rest.api.response.geocoder.GeocoderResponse;
import it.cnr.missioni.rest.api.response.geocoder.GeocoderStore;
import it.cnr.missioni.rest.api.response.missione.MissioneStreaming;
import it.cnr.missioni.rest.api.response.missione.MissioniStore;
import org.geosdi.geoplatform.exception.IllegalParameterFault;
import org.geosdi.geoplatform.exception.ResourceNotFoundFault;
import org.geosdi.geoplatform.experimental.el.dao.PageResult;
import org.geosdi.geoplatform.logger.support.annotation.GeoPlatformLog;
import org.geosdi.geoplatform.support.google.spring.services.geocoding.GPGeocodingService;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import javax.ws.rs.core.StreamingOutput;

/**
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
class MissioneDelegate implements IMissioneDelegate {

    static {
        gen = Generators.timeBasedGenerator(EthernetAddress.fromInterface());
    }

    private static final TimeBasedGenerator gen;

    @GeoPlatformLog
    private static Logger logger;
    //
    @Resource(name = "missioneDAO")
    private IMissioneDAO missioneDAO;
    @Resource(name = "userDAO")
    private IUserDAO userDAO;
    @Resource(name = "missioniMailDispatcher")
    private MissioniMailDispatcher missioniMailDispatcher;
    @Autowired
    private NotificationMessageFactory notificationMessageFactory;
    @Resource(name = "cnrMissioniItaliaEmail")
    private CNRMissioniEmail cnrMissioniItaliaEmail;
    @Resource(name = "cnrMissioniEsteroEmail")
    private CNRMissioniEmail cnrMissioniEsteroEmail;
    @Resource(name = "notificationMissionRequestValidator")
    private ICNRMissionValidator<NotificationMissionRequest, String> notificationMissionRequestValidator;
    @Resource(name = "gpGeocodingService")
    private GPGeocodingService gpGeocodingService;


    /**
     * @param request
     * @return {@link Boolean}
     * @throws Exception
     */
    @Override
    public Boolean notifyMissionAdministration(NotificationMissionRequest request) throws Exception {
        if (request == null)
            throw new IllegalParameterFault("The Parameter Request must not be null.");

        String message = this.notificationMissionRequestValidator.validate(request);
        if (message != null) {
            throw new IllegalParameterFault(message);
        }

        Missione missione = this.missioneDAO.find(request.getMissionID());
        if (missione == null)
            throw new ResourceNotFoundFault("La Missione con ID : " + request.getMissionID() + " non esiste");

        User user = this.userDAO.find(missione.getIdUser());
        if (user == null)
            throw new ResourceNotFoundFault("L'Utente con ID : " + missione.getIdUser() + " non esiste");

        this.missioniMailDispatcher.dispatchMessage(this.notificationMessageFactory
                .buildAddMissioneMessage(user.getAnagrafica().getNome(), user.getAnagrafica().getCognome(),
                        user.getDatiCNR().getMail(),
                        (missione.isMissioneEstera() ? this.cnrMissioniEsteroEmail.getEmail()
                                : this.cnrMissioniItaliaEmail.getEmail()),
                        MissionePDFBuilder
                                .newPDFBuilder()
                                .withUser(user)
                                .withMissione(missione)));

        return Boolean.TRUE;
    }

    @Override
    public Boolean notifyRimborsoMissionAdministration(NotificationMissionRequest request) throws Exception {
        if (request == null)
            throw new IllegalParameterFault("The Parameter Request must not be null.");

        String message = this.notificationMissionRequestValidator.validate(request);
        if (message != null) {
            throw new IllegalParameterFault(message);
        }

        Missione missione = this.missioneDAO.find(request.getMissionID());
        if (missione == null)
            throw new ResourceNotFoundFault("La Missione con ID : " + request.getMissionID() + " non esiste");

        User user = this.userDAO.find(missione.getIdUser());
        if (user == null)
            throw new ResourceNotFoundFault("L'Utente con ID : " + missione.getIdUser() + " non esiste");

        this.missioniMailDispatcher.dispatchMessage(this.notificationMessageFactory
                .buildAddRimborsoMessage(user.getAnagrafica().getNome(), user.getAnagrafica().getCognome(),
                        user.getDatiCNR().getMail(),
                        (missione.isMissioneEstera() ? this.cnrMissioniEsteroEmail.getEmail()
                                : this.cnrMissioniItaliaEmail.getEmail()), missione.getId(),
                        RimborsoPDFBuilder
                                .newPDFBuilder()
                                .withUser(user)
                                .withMissione(missione)));

        return Boolean.TRUE;
    }

    @Override
    public MissioniStore getMissioneByQuery(String idMissione, String idUser, String stato, Long numeroOrdineRimborso, Long dataFromMissione, Long dataToMissione,
            Long dataFromRimborso, Long dataToRimborso, String oggetto, String multiMatch, String fieldExist, int from, int size)
            throws Exception {
        // if ((missioneID == null) || (missioneID.isEmpty())) {
        // throw new IllegalParameterFault("The Parameter missioneID must not "
        // + "be null or an Empty String");
        // }

        DateTime fromInserimento = dataFromMissione != null ? new DateTime(dataFromMissione) : null;
        DateTime toInserimento = dataToMissione != null ? new DateTime(dataToMissione) : null;

        DateTime fromRimborso = dataFromRimborso != null ? new DateTime(dataFromRimborso) : null;
        DateTime toRimborso = dataToRimborso != null ? new DateTime(dataToRimborso) : null;

        MissioneSearchBuilder missioneSearchBuilder = MissioneSearchBuilder.getMissioneSearchBuilder()
                .withIdUser(idUser).withIdMissione(idMissione).withStato(stato)
                .withNumeroOrdineMissione(numeroOrdineRimborso)
                .withRangeDataInserimento(fromInserimento, toInserimento)
                .withRangeDataRimborso(fromRimborso, toRimborso)
                .withOggetto(oggetto)
                .withMultiMatch(multiMatch)
                .withFieldExist(fieldExist)
                .withFrom(from).withSize(size);
        PageResult<Missione> pageResult = this.missioneDAO.findMissioneByQuery(missioneSearchBuilder);
        if (!pageResult.getResults().isEmpty()) {
            MissioniStore missioniStore = new MissioniStore();
            missioniStore.setMissioni(pageResult.getResults());
            missioniStore.setTotale(pageResult.getTotal());
            return missioniStore;
        } else
            return null;

    }

    @Override
    public MissioniStore getLastUserMissions(String userID) throws Exception {
        return null;
    }

    @Override
    public String addMissione(Missione missione) throws Exception {
        if ((missione == null)) {
            throw new IllegalParameterFault("The Parameter missione must not be null ");
        }
        missione.setId(gen.generate().toString());
        this.missioneDAO.persist(missione);

        User user = this.userDAO.find(missione.getIdUser());
        if (user == null)
            throw new ResourceNotFoundFault("L'Utente con ID : " + missione.getIdUser() + " non esiste");
        this.missioniMailDispatcher.dispatchMessage(this.notificationMessageFactory
                .buildAddMissioneMessage(user.getAnagrafica().getNome(), user.getAnagrafica().getCognome(),
                        user.getDatiCNR().getMail(),
                        (missione.isMissioneEstera() ? this.cnrMissioniEsteroEmail.getEmail()
                                : this.cnrMissioniItaliaEmail.getEmail()),
                        MissionePDFBuilder
                                .newPDFBuilder()
                                .withUser(user)
                                .withMissione(missione)));
        return missione.getId();
    }

    @Override
    public Boolean updateMissione(Missione missione) throws Exception {
        if ((missione == null)) {
            throw new IllegalParameterFault("The Parameter missione must not be null ");
        }

        User user = this.userDAO.find(missione.getIdUser());
        if (user == null)
            throw new ResourceNotFoundFault("L'Utente con ID : " + missione.getIdUser() + " non esiste");

        if (missione.isRimborsoSetted() != null && missione.getRimborso().getNumeroOrdine() == null)
            missione.getRimborso().setNumeroOrdine(this.missioneDAO.getMaxNumeroOrdineRimborso());
        this.missioneDAO.update(missione);

        if (missione.isRimborsoSetted()) {
            this.missioniMailDispatcher.dispatchMessage(this.notificationMessageFactory
                    .buildAddRimborsoMessage(user.getAnagrafica().getNome(), user.getAnagrafica().getCognome(),
                            user.getDatiCNR().getMail(),
                            (missione.isMissioneEstera() ? this.cnrMissioniEsteroEmail.getEmail()
                                    : this.cnrMissioniItaliaEmail.getEmail()), missione.getId(),
                            RimborsoPDFBuilder
                                    .newPDFBuilder()
                                    .withUser(user)
                                    .withMissione(missione)));
        } else {
            this.missioniMailDispatcher.dispatchMessage(this.notificationMessageFactory
                    .buildAddMissioneMessage(user.getAnagrafica().getNome(), user.getAnagrafica().getCognome(),
                            user.getDatiCNR().getMail(),
                            (missione.isMissioneEstera() ? this.cnrMissioniEsteroEmail.getEmail()
                                    : this.cnrMissioniItaliaEmail.getEmail()),
                            MissionePDFBuilder
                                    .newPDFBuilder()
                                    .withUser(user)
                                    .withMissione(missione)));
        }
        return Boolean.TRUE;
    }

    @Override
    public Boolean deleteMissione(String missioneID) throws Exception {
        if ((missioneID == null) || (missioneID.isEmpty())) {
            throw new IllegalParameterFault("The Parameter missioneID must not be null " + "or an Empty String.");
        }
        this.missioneDAO.delete(missioneID);
        return Boolean.TRUE;
    }

    /**
     * @param missionID
     * @return {@link StreamingOutput}
     * @throws Exception
     */
    @Override
    public StreamingOutput downloadMissioneAsPdf(String missionID) throws Exception {
        if ((missionID == null) || (missionID.isEmpty())) {
            throw new IllegalParameterFault("The Parameter missioneID must not be null " + "or an Empty String.");
        }
        Missione missione = this.missioneDAO.find(missionID);
        if (missione == null)
            throw new ResourceNotFoundFault("La Missione con ID : " + missionID + " non esiste");

        User user = this.userDAO.find(missione.getIdUser());
        if (user == null)
            throw new ResourceNotFoundFault("L'Utente con ID : " + missione.getIdUser() + " non esiste");
        return new MissioneStreaming(MissionePDFBuilder.newPDFBuilder().withUser(user).withMissione(missione));
    }

    /**
     * @param missionID
     * @return {@link StreamingOutput}
     * @throws Exception
     */
    @Override
    public StreamingOutput downloadRimborsoMissioneAsPdf(String missionID) throws Exception {
        if ((missionID == null) || (missionID.isEmpty())) {
            throw new IllegalParameterFault("The Parameter missioneID must not be null " + "or an Empty String.");
        }
        Missione missione = this.missioneDAO.find(missionID);
        if (missione == null)
            throw new ResourceNotFoundFault("La Missione con ID : " + missionID + " non esiste");

        User user = this.userDAO.find(missione.getIdUser());
        if (user == null)
            throw new ResourceNotFoundFault("L'Utente con ID : " + missione.getIdUser() + " non esiste");
        return new MissioneStreaming(RimborsoPDFBuilder.newPDFBuilder().withUser(user).withMissione(missione));
    }

    /**
     * @param location
     * @return {@link GeocoderStore}
     * @throws Exception
     */
    @Override
    public GeocoderStore getGeocoderStoreForMissioneLocation(String location) throws Exception {
        if ((location == null) || (location.isEmpty())) {
            throw new IllegalParameterFault("The Parameter Location must not be null or an " +
                    "empty String");
        }
        GeocoderStore geocoderStore = new GeocoderStore();
        GeocodingResult[] results = this.gpGeocodingService.newRequest().address(location).await();
        if ((results != null) && (results.length > 0)) {
            for (GeocodingResult geocodingResult : results) {
                geocoderStore.addGeocoderResponse(new GeocoderResponse(geocodingResult.formattedAddress,
                        geocodingResult.geometry.location.lat, geocodingResult.geometry.location.lng));
            }
        }
        return geocoderStore;
    }
}