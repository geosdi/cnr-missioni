package it.cnr.missioni.dropwizard.delegate.missioni;

import java.text.DecimalFormat;
import java.util.Locale;

import javax.annotation.Resource;
import javax.ws.rs.core.StreamingOutput;

import org.geosdi.geoplatform.exception.IllegalParameterFault;
import org.geosdi.geoplatform.exception.ResourceNotFoundFault;
import org.geosdi.geoplatform.experimental.el.dao.PageResult;
import org.geosdi.geoplatform.logger.support.annotation.GeoPlatformLog;
import org.geosdi.geoplatform.support.google.spring.services.distance.GPDistanceMatrixService;
import org.geosdi.geoplatform.support.google.spring.services.geocoding.GPGeocodingService;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.uuid.EthernetAddress;
import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedGenerator;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.DistanceMatrixElement;
import com.google.maps.model.DistanceMatrixElementStatus;
import com.google.maps.model.DistanceMatrixRow;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.TravelMode;

import it.cnr.missioni.el.dao.IMissioneDAO;
import it.cnr.missioni.el.dao.IUserDAO;
import it.cnr.missioni.el.model.bean.StatisticheMissioni;
import it.cnr.missioni.el.model.search.builder.MissioneSearchBuilder;
import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.model.user.User;
import it.cnr.missioni.model.user.Veicolo;
import it.cnr.missioni.model.validator.ICNRMissionValidator;
import it.cnr.missioni.notification.dispatcher.MissioniMailDispatcher;
import it.cnr.missioni.notification.message.factory.NotificationMessageFactory;
import it.cnr.missioni.notification.spring.configuration.CNRMissioniEmail;
import it.cnr.missioni.notification.support.itext.PDFBuilder;
import it.cnr.missioni.notification.support.itext.anticipoPagamento.AnticipoPagamentoPDFBuilder;
import it.cnr.missioni.notification.support.itext.missione.MissionePDFBuilder;
import it.cnr.missioni.notification.support.itext.rimborso.RimborsoPDFBuilder;
import it.cnr.missioni.rest.api.request.NotificationMissionRequest;
import it.cnr.missioni.rest.api.response.geocoder.GeocoderResponse;
import it.cnr.missioni.rest.api.response.geocoder.GeocoderStore;
import it.cnr.missioni.rest.api.response.missione.AnticipoPagamentoStreaming;
import it.cnr.missioni.rest.api.response.missione.MissioneStreaming;
import it.cnr.missioni.rest.api.response.missione.MissioniStore;
import it.cnr.missioni.rest.api.response.missione.VeicoloMissioneStreaming;
import it.cnr.missioni.rest.api.response.missione.distance.DistanceResponse;

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
	@Resource(name = "gpDistanceMatrixService")
	private GPDistanceMatrixService gpDistanceMatrixService;

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

		PDFBuilder pdfBuilder = MissionePDFBuilder.newPDFBuilder().withUser(user).withMissione(missione);
		if (missione.isMezzoProprio()) {
			pdfBuilder.setMezzoProprio(missione.isMezzoProprio());
			Veicolo veicolo = user.getVeicoloPrincipale();
			pdfBuilder.withVeicolo(veicolo);
		}
		String mailResponsabile = "";
		if (missione.getResponsabileGruppo() != null) {
			User userResponsabileGruppo = this.userDAO.find(missione.getResponsabileGruppo());
			if (userResponsabileGruppo == null)
				throw new ResourceNotFoundFault("L'Utente con ID : " + missione.getIdUser() + " non esiste");
			mailResponsabile = userResponsabileGruppo.getDatiCNR().getMail();
		}
		this.missioniMailDispatcher.dispatchMessage(this.notificationMessageFactory.buildAddMissioneMessage(
				user.getAnagrafica().getNome(), user.getAnagrafica().getCognome(),
				user.getDatiCNR().getMail(), mailResponsabile, (missione.isMissioneEstera()
						? this.cnrMissioniEsteroEmail.getEmail() : this.cnrMissioniItaliaEmail.getEmail()),
				pdfBuilder));

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

		this.missioniMailDispatcher.dispatchMessage(this.notificationMessageFactory.buildAddRimborsoMessage(
				user.getAnagrafica().getNome(), user.getAnagrafica().getCognome(), user.getDatiCNR().getMail(),
				(missione.isMissioneEstera() ? this.cnrMissioniEsteroEmail.getEmail()
						: this.cnrMissioniItaliaEmail.getEmail()),
				missione.getId(), RimborsoPDFBuilder.newPDFBuilder().withUser(user).withMissione(missione)));

		return Boolean.TRUE;
	}

	@Override
	public Boolean updateRimborso(Missione missione) throws Exception {
		if ((missione == null)) {
			throw new IllegalParameterFault("The Parameter missione must not be null ");
		}

		this.missioneDAO.update(missione);
		User user = this.userDAO.find(missione.getIdUser());
		if (user == null)
			throw new ResourceNotFoundFault("L'Utente con ID : " + missione.getIdUser() + " non esiste");

		this.missioniMailDispatcher.dispatchMessage(this.notificationMessageFactory.buildUpdateRimborsoMessage(
				user.getAnagrafica().getNome(), user.getAnagrafica().getCognome(), user.getDatiCNR().getMail(),
				missione.getRimborso().getNumeroOrdine().toString(),
				missione.getRimborso().isPagata() ? "Si" : "No", missione.getRimborso().getMandatoPagamento() != null
						? missione.getRimborso().getMandatoPagamento() : "",
				missione.getRimborso().getTotaleDovuto(), null));

		return Boolean.TRUE;
	}

	@Override
	public MissioniStore getMissioneByQuery(String idMissione, String idUser, String stato, Long numeroOrdineRimborso,
			Long dataFromMissione, Long dataToMissione, Long dataFromRimborso, Long dataToRimborso, String oggetto,
			String multiMatch, String fieldExist, String fieldNotExist, int from, int size) throws Exception {
		DateTime fromInserimento = dataFromMissione != null ? new DateTime(dataFromMissione) : null;
		DateTime toInserimento = dataToMissione != null ? new DateTime(dataToMissione) : null;

		DateTime fromRimborso = dataFromRimborso != null ? new DateTime(dataFromRimborso) : null;
		DateTime toRimborso = dataToRimborso != null ? new DateTime(dataToRimborso) : null;

		MissioneSearchBuilder missioneSearchBuilder = MissioneSearchBuilder.getMissioneSearchBuilder()
				.withIdUser(idUser).withIdMissione(idMissione).withStato(stato)
				.withNumeroOrdineMissione(numeroOrdineRimborso).withRangeDataInserimento(fromInserimento, toInserimento)
				.withRangeDataRimborso(fromRimborso, toRimborso).withOggetto(oggetto).withMultiMatch(multiMatch)
				.withFieldExist(fieldExist).withFieldNotExist(fieldNotExist).withFrom(from).withSize(size);
		PageResult<Missione> pageResult = this.missioneDAO.findMissioneByQuery(missioneSearchBuilder);
		MissioniStore missioniStore = new MissioniStore();
		missioniStore.setMissioni(pageResult.getResults());
		missioniStore.setTotale(pageResult.getTotal());
		return missioniStore;

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
		if (missione.getId() == null)
			missione.setId(this.missioneDAO.getMaxNumeroMissioneAnno());
		// missione.setId(gen.generate().toString());
		this.missioneDAO.persist(missione);

		User user = this.userDAO.find(missione.getIdUser());
		if (user == null)
			throw new ResourceNotFoundFault("L'Utente con ID : " + missione.getIdUser() + " non esiste");

		PDFBuilder pdfBuilder = MissionePDFBuilder.newPDFBuilder().withUser(user).withMissione(missione);
		if (missione.isMezzoProprio()) {
			pdfBuilder.setMezzoProprio(missione.isMezzoProprio());
			Veicolo veicolo = user.getVeicoloPrincipale();
			pdfBuilder.withVeicolo(veicolo);
		}

		return this.missioneDAO.persist(missione).getId();
	}

	@Override
	public Boolean updateMissione(Missione missione) throws Exception {
		if ((missione == null)) {
			throw new IllegalParameterFault("The Parameter missione must not be null ");
		}

		User user = this.userDAO.find(missione.getIdUser());
		if (user == null)
			throw new ResourceNotFoundFault("L'Utente con ID : " + missione.getIdUser() + " non esiste");

		if (missione.isRimborsoSetted() && missione.getRimborso().getNumeroOrdine() == null)
			missione.getRimborso().setNumeroOrdine(this.missioneDAO.getMaxNumeroOrdineRimborso());
		this.missioneDAO.update(missione);

		PDFBuilder pdfBuilder = MissionePDFBuilder.newPDFBuilder().withUser(user).withMissione(missione);

		if (missione.isRimborsoSetted()) {
//			this.missioniMailDispatcher.dispatchMessage(this.notificationMessageFactory.buildAddRimborsoMessage(
//					user.getAnagrafica().getNome(),
//					user.getAnagrafica().getCognome(), user.getDatiCNR().getMail(), (missione.isMissioneEstera()
//							? this.cnrMissioniEsteroEmail.getEmail() : this.cnrMissioniItaliaEmail.getEmail()),
//					missione.getId(), pdfBuilder));
		} else {

			if (missione.isMezzoProprio()) {
				pdfBuilder.setMezzoProprio(missione.isMezzoProprio());
				Veicolo veicolo = user.getVeicoloPrincipale();
				pdfBuilder.withVeicolo(veicolo);
			}

			String mailResponsabile = "";
			if (missione.getResponsabileGruppo() != null) {
				User userResponsabileGruppo = this.userDAO.find(missione.getResponsabileGruppo());
				if (userResponsabileGruppo == null)
					throw new ResourceNotFoundFault("L'Utente con ID : " + missione.getIdUser() + " non esiste");
				mailResponsabile = userResponsabileGruppo.getDatiCNR().getMail();
			}

			this.missioniMailDispatcher.dispatchMessage(this.notificationMessageFactory.buildUpdateMissioneMessage(
					user.getAnagrafica().getNome(), user.getAnagrafica().getCognome(), missione.getStato().getStato(),
					user.getDatiCNR().getMail(), mailResponsabile, missione.getId(), pdfBuilder));
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
	public StreamingOutput downloadVeicoloMissioneAsPdf(String missionID) throws Exception {
		if ((missionID == null) || (missionID.isEmpty())) {
			throw new IllegalParameterFault("The Parameter missioneID must not be null " + "or an Empty String.");
		}
		Missione missione = this.missioneDAO.find(missionID);
		if (missione == null)
			throw new ResourceNotFoundFault("La Missione con ID : " + missionID + " non esiste");

		User user = this.userDAO.find(missione.getIdUser());
		if (user == null)
			throw new ResourceNotFoundFault("L'Utente con ID : " + missione.getIdUser() + " non esiste");
		Veicolo veicolo = user.getMappaVeicolo().get(missione.getIdVeicolo());
		return new VeicoloMissioneStreaming(
				MissionePDFBuilder.newPDFBuilder().withUser(user).withMissione(missione).withVeicolo(veicolo));
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
			throw new IllegalParameterFault("The Parameter Location must not be null or an " + "empty String");
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

	/**
	 * @param start
	 * @param end
	 * @return {@link DistanceResponse}
	 * @throws Exception
	 */
	@Override
	public DistanceResponse getDistanceForMissione(String start, String end) throws Exception {
		if ((start == null) || (start.isEmpty())) {
			throw new IllegalParameterFault("The Parameter start must not be null or an " + "empty String");
		}

		if ((end == null) || (end.isEmpty())) {
			throw new IllegalParameterFault("The Parameter end must not be null or an " + "empty String");
		}
		DistanceMatrix distanceMatrix = this.gpDistanceMatrixService
				.getDistanceMatrix(new String[] { start }, new String[] { end }).mode(TravelMode.DRIVING).language("it")
				.await();

		DistanceMatrixRow distanceMatrixRow = distanceMatrix.rows[0];
		DistanceMatrixElement element = distanceMatrixRow.elements[0];

		if (element.status == DistanceMatrixElementStatus.NOT_FOUND) {
			throw new IllegalParameterFault("Error in Geocoding the Start and End Location to have Distance");
		}

		if (element.status == DistanceMatrixElementStatus.ZERO_RESULTS) {
			GeocoderStore geocoderEnd = getGeocoderStoreForMissioneLocation(end);
			GeocoderStore geocoderStart = getGeocoderStoreForMissioneLocation(start);
			String distance = distance(geocoderStart.getGeocoderResponses().get(0).getLat(),
					geocoderStart.getGeocoderResponses().get(0).getLon(),
					geocoderEnd.getGeocoderResponses().get(0).getLat(),
					geocoderEnd.getGeocoderResponses().get(0).getLon());

			return new DistanceResponse.MissioneDistanceResponse(distance, "");
		}
		return new DistanceResponse.MissioneDistanceResponse(element.distance.humanReadable,
				element.duration.humanReadable);
	}

	/**
	 * 
	 * Calcola la distanza dati 2 punti. Usata nel caso in cui la distanza tra
	 * start e end non è percorribile tramite auto
	 * 
	 * @param lat1
	 * @param lon1
	 * @param lat2
	 * @param lon2
	 * @return
	 */
	public static String distance(double lat1, double lon1, double lat2, double lon2) {
		double theta = lon1 - lon2;
		double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2))
				+ Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
		dist = Math.acos(dist);
		dist = rad2deg(dist);
		dist = dist * 60 * 1.1515;
		dist = dist * 1.609344;

		DecimalFormat decimalFormatLocalIT = (DecimalFormat) DecimalFormat.getInstance(Locale.ITALY);
		return decimalFormatLocalIT.format(dist) + " km";
	}

	private static double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}

	// This function converts radians to decimal degrees
	private static double rad2deg(double rad) {
		return (rad * 180 / Math.PI);
	}

	/**
	 * @return
	 * @throws Exception
	 */
	@Override
	public StatisticheMissioni getStatistiche() throws Exception {
		return this.missioneDAO.getStatisticheMissioni();
	}

	/**
	 * 
	 * @param missione
	 * @param modifica
	 * @return
	 * @throws Exception
	 */
	@Override
	public Boolean updateMissioneForAnticipo(Missione missione) throws Exception {
		if ((missione == null)) {
			throw new IllegalParameterFault("The Parameter missione must not be null ");
		}

		User user = this.userDAO.find(missione.getIdUser());
		if (user == null)
			throw new ResourceNotFoundFault("L'Utente con ID : " + missione.getIdUser() + " non esiste");
		PDFBuilder pdfBuilder = AnticipoPagamentoPDFBuilder.newPDFBuilder().withUser(user).withMissione(missione);

		Missione m = this.missioneDAO.find(missione.getId());
		this.missioneDAO.update(missione);

		if (!m.getDatiAnticipoPagamenti().isInserted()) {
			this.missioniMailDispatcher.dispatchMessage(this.notificationMessageFactory
					.buildAddAnticipoPagamentoMessage(user.getAnagrafica().getNome(), user.getAnagrafica().getCognome(),
							user.getDatiCNR().getMail(), (missione.isMissioneEstera()
									? this.cnrMissioniEsteroEmail.getEmail() : this.cnrMissioniItaliaEmail.getEmail()),
							missione.getId(), pdfBuilder));
		} else {
			this.missioniMailDispatcher.dispatchMessage(
					this.notificationMessageFactory.buildUpdateAnticipoPagamentoMessage(user.getAnagrafica().getNome(),
							user.getAnagrafica().getCognome(),
							user.getDatiCNR().getMail(), (missione.isMissioneEstera()
									? this.cnrMissioniEsteroEmail.getEmail() : this.cnrMissioniItaliaEmail.getEmail()),
							missione.getId(), pdfBuilder));
		}

		return Boolean.TRUE;
	}

	/**
	 * @param missionID
	 * @return {@link StreamingOutput}
	 * @throws Exception
	 */
	@Override
	public StreamingOutput downloadAnticipoPagamentoAsPdf(String missionID) throws Exception {
		if ((missionID == null) || (missionID.isEmpty())) {
			throw new IllegalParameterFault("The Parameter missioneID must not be null " + "or an Empty String.");
		}
		Missione missione = this.missioneDAO.find(missionID);
		if (missione == null)
			throw new ResourceNotFoundFault("La Missione con ID : " + missionID + " non esiste");

		User user = this.userDAO.find(missione.getIdUser());
		if (user == null)
			throw new ResourceNotFoundFault("L'Utente con ID : " + missione.getIdUser() + " non esiste");
		return new AnticipoPagamentoStreaming(
				AnticipoPagamentoPDFBuilder.newPDFBuilder().withUser(user).withMissione(missione));
	}
}