package it.cnr.missioni.dashboard.client;

import it.cnr.missioni.connector.core.spring.connector.MissioniCoreClientConnector;
import it.cnr.missioni.connector.core.spring.connector.provider.CoreConnectorProvider;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.dropwizard.connector.api.settings.ConnectorClientSettings;
import it.cnr.missioni.el.model.bean.StatisticheMissioni;
import it.cnr.missioni.el.model.search.builder.*;
import it.cnr.missioni.model.configuration.*;
import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.model.prenotazione.Prenotazione;
import it.cnr.missioni.model.prenotazione.VeicoloCNR;
import it.cnr.missioni.model.user.User;
import it.cnr.missioni.rest.api.response.geocoder.GeocoderStore;
import it.cnr.missioni.rest.api.response.massimale.MassimaleStore;
import it.cnr.missioni.rest.api.response.missione.MissioniStore;
import it.cnr.missioni.rest.api.response.missione.distance.DistanceResponse;
import it.cnr.missioni.rest.api.response.nazione.NazioneStore;
import it.cnr.missioni.rest.api.response.prenotazione.PrenotazioniStore;
import it.cnr.missioni.rest.api.response.qualificaUser.QualificaUserStore;
import it.cnr.missioni.rest.api.response.rimborsoKm.RimborsoKmStore;
import it.cnr.missioni.rest.api.response.tipologiaSpesa.TipologiaSpesaStore;
import it.cnr.missioni.rest.api.response.user.UserStore;
import it.cnr.missioni.rest.api.response.veicoloCNR.VeicoloCNRStore;
import org.glassfish.jersey.client.ClientConfig;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;

/**
 * @author Salvia Vito
 */
public class ClientConnector {

    private static final MissioniCoreClientConnector missioniCoreClientConnector;

    static {
        missioniCoreClientConnector = new MissioniCoreClientConnector(new ConnectorClientSettings() {
            @Override
            public String getRestServiceURL() {
                return Utility.getRestServiccesURL("rest_services_url");
            }

            @Override
            public String getConnectorName() {
                return "Missioni Core Client Connector Without Spring.";
            }

            @Override
            public void afterPropertiesSet() throws Exception {
            }
        }, ClientBuilder.newClient(new ClientConfig(CoreConnectorProvider.class)));
    }

    /**
     * @param user
     * @throws Exception
     */
    public static void addUser(User user) throws Exception {
        missioniCoreClientConnector.addUser(user);
    }

    /**
     * @param user
     * @throws Exception
     */
    public static void updateUser(User user) throws Exception {
        missioniCoreClientConnector.updateUser(user);
    }

    /**
     * @param userSearchBuilder
     * @return {@link UserStore}
     * @throws Exception
     */
    public static UserStore getUser(IUserSearchBuilder userSearchBuilder) throws Exception {
        return missioniCoreClientConnector.getUserByQuery(userSearchBuilder);
    }

    /***
     * @param missioneSearchBuilder
     * @return {@link MissioniStore}
     * @throws Exception
     */
    public static MissioniStore getMissione(IMissioneSearchBuilder missioneSearchBuilder) throws Exception {
        return missioniCoreClientConnector.getMissioneByQuery(missioneSearchBuilder);
    }

    /**
     * @param missione
     * @return {@link String}
     * @throws Exception
     */
    public static String addMissione(Missione missione) throws Exception {
        return missioniCoreClientConnector.addMissione(missione);
    }

    /**
     * @param missione
     * @throws Exception
     */
    public static void updateMissione(Missione missione) throws Exception {
        missioniCoreClientConnector.updateMissione(missione);
    }

    /**
     * @param missione
     * @throws Exception
     */
    public static void updateMissioneForAnticipo(Missione missione) throws Exception {
        missioniCoreClientConnector.updateMissioneForAnticipo(missione);
    }

    /**
     * @param missione
     * @throws Exception
     */
    public static void updateRimborso(Missione missione) throws Exception {
        missioniCoreClientConnector.updateRimborso(missione);
    }

    /**
     * @param veicoloCNRSearchBuilder
     * @return {@link VeicoloCNRStore}
     * @throws Exception
     */
    public static VeicoloCNRStore getVeicoloCNR(IVeicoloCNRSearchBuilder veicoloCNRSearchBuilder) throws Exception {
        return missioniCoreClientConnector.getVeicoloCNRByQuery(veicoloCNRSearchBuilder);
    }

    /**
     * @param prenotazione
     * @throws Exception
     */
    public static void addPrenotazione(Prenotazione prenotazione) throws Exception {
        missioniCoreClientConnector.addPrenotazione(prenotazione);
    }

    /**
     * @param prenotazione
     * @throws Exception
     */
    public static void updatePrenotazione(Prenotazione prenotazione) throws Exception {
        missioniCoreClientConnector.updatePrenotazione(prenotazione);
    }

    /**
     * @param prenotazioneID
     * @throws Exception
     */
    public static void deletePrenotazione(String prenotazioneID) throws Exception {
        missioniCoreClientConnector.deletePrenotazione(prenotazioneID);
    }

    /**
     * @param prenotazioneSearchBuilder
     * @return {@link PrenotazioniStore}
     * @throws Exception
     */
    public static PrenotazioniStore getPrenotazione(IPrenotazioneSearchBuilder prenotazioneSearchBuilder)
            throws Exception {
        return missioniCoreClientConnector.getPrenotazioneByQuery(prenotazioneSearchBuilder);
    }

    /**
     * @param veicoloCNR
     * @throws Exception
     */
    public static void updateVeicoloCNR(VeicoloCNR veicoloCNR) throws Exception {
        missioniCoreClientConnector.updateVeicoloCNR(veicoloCNR);
    }

    /**
     * @param veicoloCNR
     * @throws Exception
     */
    public static void addVeicoloCNR(VeicoloCNR veicoloCNR) throws Exception {
        missioniCoreClientConnector.addVeicoloCNR(veicoloCNR);
    }

    /**
     * @param missioneID
     * @throws Exception
     */
    public static void sendMissioneMail(String missioneID) throws Exception {
        missioniCoreClientConnector.sendMissioneMail(missioneID);
    }

    /**
     * @param missioneID
     * @throws Exception
     */
    public static void sendRimborsoMail(String missioneID) throws Exception {
        missioniCoreClientConnector.sendRimborsoMail(missioneID);
    }

    /**
     * @param missioneID
     * @return {@link Response}
     * @throws Exception
     */
    public static Response downloadMissioneAsPdf(String missioneID) throws Exception {
        return missioniCoreClientConnector.downloadMissioneAsPdf(missioneID);
    }

    /**
     * @param missioneID
     * @throws Exception
     */
    public static Response downloadVeicoloMissioneAsPdf(String missioneID) throws Exception {
        return missioniCoreClientConnector.downloadVeicoloMissioneAsPdf(missioneID);
    }

    /**
     * @param missioneID
     * @return {@link Response}
     * @throws Exception
     */
    public static Response downloadAnticipoPagamentoAsPdf(String missioneID) throws Exception {
        return missioniCoreClientConnector.downloadAnticipoPagamentoAsPdf(missioneID);
    }

    /**
     * @param missioneID
     * @return {@link Response}
     * @throws Exception
     */
    public static Response downloadRimborsoMissioneAsPdf(String missioneID) throws Exception {
        return missioniCoreClientConnector.downloadRimborsoMissioneAsPdf(missioneID);
    }

    /**
     * @param start
     * @param end
     * @return {@link DistanceResponse}
     * @throws Exception
     */
    public static DistanceResponse.MissioneDistanceResponse getDistanceForMissione(String start, String end)
            throws Exception {
        return missioniCoreClientConnector.getDistanceForMissione(start, end);
    }

    /**
     * @param location
     * @return {@link GeocoderStore}
     * @throws Exception
     */
    public static GeocoderStore getGeocoderStoreForMissioneLocation(String location) throws Exception {
        return missioniCoreClientConnector.getGeocoderStoreForMissioneLocation(location);
    }

    /**
     * @param nazioneSearchBuilder
     * @return {@link NazioneStore}
     * @throws Exception
     */
    public static NazioneStore getNazione(INazioneSearchBuilder nazioneSearchBuilder) throws Exception {
        return missioniCoreClientConnector.getNazioneByQuery(nazioneSearchBuilder);
    }

    /**
     * @param qualificaUserSearchBuilder
     * @return {@link QualificaUserStore}
     * @throws Exception
     */
    public static QualificaUserStore getQualificaUser(IQualificaUserSearchBuilder qualificaUserSearchBuilder)
            throws Exception {
        return missioniCoreClientConnector.getQualificaUserByQuery(qualificaUserSearchBuilder);
    }

    /**
     * @param rimborsoKmSearchBuilder
     * @return {@link RimborsoKmStore}
     * @throws Exception
     */
    public static RimborsoKmStore getRimborsoKm(IRimborsoKmSearchBuilder rimborsoKmSearchBuilder) throws Exception {
        return missioniCoreClientConnector.getRimborsoKmByQuery(rimborsoKmSearchBuilder);
    }

    /**
     * @param tipologiaSpesaSearchBuilder
     * @return {@link TipologiaSpesaStore}
     * @throws Exception
     */
    public static TipologiaSpesaStore getTipologiaSpesa(ITipologiaSpesaSearchBuilder tipologiaSpesaSearchBuilder)
            throws Exception {
        return missioniCoreClientConnector.getTipologiaSpesaByQuery(tipologiaSpesaSearchBuilder);
    }

    /**
     * @param massimaleSearchBuilder
     * @return {@link MassimaleStore}
     * @throws Exception
     */
    public static MassimaleStore getMassimale(IMassimaleSearchBuilder massimaleSearchBuilder) throws Exception {
        return missioniCoreClientConnector.getMassimaleByQuery(massimaleSearchBuilder);
    }

    /**
     * @param qualificaUser
     * @return {@link String}
     * @throws Exception
     */
    public static String addQualificaUser(QualificaUser qualificaUser) throws Exception {
        return missioniCoreClientConnector.addQualificaUser(qualificaUser);
    }

    /**
     * @param qualificaUser
     * @throws Exception
     */
    public static void updateQualificaUser(QualificaUser qualificaUser) throws Exception {
        missioniCoreClientConnector.updateQualificaUser(qualificaUser);
    }

    /**
     * @param nazione
     * @return {@link String}
     * @throws Exception
     */
    public static String addNazione(Nazione nazione) throws Exception {
        return missioniCoreClientConnector.addNazione(nazione);
    }

    /**
     * @param nazione
     * @throws Exception
     */
    public static void updateNazione(Nazione nazione) throws Exception {
        missioniCoreClientConnector.updateNazione(nazione);
    }

    /**
     * @param rimborsoKm
     * @return {@link String}
     * @throws Exception
     */
    public static String addRimborsoKm(RimborsoKm rimborsoKm) throws Exception {
        return missioniCoreClientConnector.addRimborsoKm(rimborsoKm);
    }

    /**
     * @param rimborsoKm
     * @throws Exception
     */
    public static void updateRimborsoKm(RimborsoKm rimborsoKm) throws Exception {
        missioniCoreClientConnector.updateRimborsoKm(rimborsoKm);
    }

    /**
     * @param tipologiaSpesa
     * @return {@link String}
     * @throws Exception
     */
    public static String addTipologiaSpesa(TipologiaSpesa tipologiaSpesa) throws Exception {
        return missioniCoreClientConnector.addTipologiaSpesa(tipologiaSpesa);
    }

    /**
     * @param tipologiaSpesa
     * @throws Exception
     */
    public static void updateTipologiaSpesa(TipologiaSpesa tipologiaSpesa) throws Exception {
        missioniCoreClientConnector.updateTipologiaSpesa(tipologiaSpesa);
    }

    /**
     * @param massimale
     * @return {@link String}
     * @throws Exception
     */
    public static String addMassimale(Massimale massimale) throws Exception {
        return missioniCoreClientConnector.addMassimale(massimale);
    }

    /**
     * @param massimale
     * @throws Exception
     */
    public static void updateMassimale(Massimale massimale) throws Exception {
        missioniCoreClientConnector.updateMassimale(massimale);
    }

    /**
     * @return {@link StatisticheMissioni}
     * @throws Exception
     */
    public static StatisticheMissioni getStatisticheMissioni() throws Exception {
        return missioniCoreClientConnector.getStatistiche();
    }
}
