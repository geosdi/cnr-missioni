package it.cnr.missioni.dashboard.client;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;

import it.cnr.missioni.connector.core.spring.connector.MissioniCoreClientConnector;
import it.cnr.missioni.connector.core.spring.connector.provider.CoreConnectorProvider;
import it.cnr.missioni.dropwizard.connector.api.settings.ConnectorClientSettings;
import it.cnr.missioni.el.model.search.builder.MissioneSearchBuilder;
import it.cnr.missioni.el.model.search.builder.PrenotazioneSearchBuilder;
import it.cnr.missioni.el.model.search.builder.UserSearchBuilder;
import it.cnr.missioni.el.model.search.builder.VeicoloCNRSearchBuilder;
import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.model.prenotazione.Prenotazione;
import it.cnr.missioni.model.prenotazione.VeicoloCNR;
import it.cnr.missioni.model.user.User;
import it.cnr.missioni.rest.api.response.geocoder.GeocoderStore;
import it.cnr.missioni.rest.api.response.missione.MissioniStore;
import it.cnr.missioni.rest.api.response.missione.distance.DistanceResponse;
import it.cnr.missioni.rest.api.response.prenotazione.PrenotazioniStore;
import it.cnr.missioni.rest.api.response.user.UserStore;
import it.cnr.missioni.rest.api.response.veicoloCNR.VeicoloCNRStore;

/**
 * @author Salvia Vito
 */
public class ClientConnector {

	private static final MissioniCoreClientConnector missioniCoreClientConnector;

	static {
		missioniCoreClientConnector = new MissioniCoreClientConnector(new ConnectorClientSettings() {

			@Override
			public String getRestServiceURL() {
				return "http://localhost:8282";
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
	 * 
	 * Aggiunge un nuovo user
	 * 
	 * @param user
	 * @throws Exception
	 */
	public static void addUser(User user) throws Exception {
		missioniCoreClientConnector.addUser(user);

	}

	/**
	 * 
	 * Aggiorna un user esistente
	 * 
	 * @param user
	 * @throws Exception
	 */
	public static void updateUser(User user) throws Exception {
		missioniCoreClientConnector.updateUser(user);

	}

	/**
	 * 
	 * Ricerca users
	 * 
	 * @param userSearchBuilder
	 * @return
	 * @throws Exception
	 */
	public static UserStore getUser(UserSearchBuilder userSearchBuilder) throws Exception {
		return missioniCoreClientConnector.getUserByQuery(userSearchBuilder);
	}

	/**
	 * 
	 * Ricerca missioni
	 * 
	 * @param userSearchBuilder
	 * @return
	 * @throws Exception
	 */
	public static MissioniStore getMissione(MissioneSearchBuilder missioneSearchBuilder) throws Exception {
		return missioniCoreClientConnector.getMissioneByQuery(missioneSearchBuilder);
	}

	/**
	 * 
	 * Aggiunge una nuova missione
	 * 
	 * @param user
	 * @throws Exception
	 */
	public static String  addMissione(Missione missione) throws Exception {
		return missioniCoreClientConnector.addMissione(missione);

	}

	/**
	 * 
	 * Aggiorna una missione esistente
	 * 
	 * @param user
	 * @throws Exception
	 */
	public static void updateMissione(Missione missione) throws Exception {
		missioniCoreClientConnector.updateMissione(missione);

	}

	/**
	 * 
	 * @param veicoloCNRSearchBuilder
	 * @throws Exception
	 */
	public static VeicoloCNRStore getVeicoloCNR(VeicoloCNRSearchBuilder veicoloCNRSearchBuilder) throws Exception {
		return missioniCoreClientConnector.getVeicoloCNRByQuery(veicoloCNRSearchBuilder);

	}

	/**
	 * 
	 * @param prenotazione
	 * @throws Exception
	 */
	public static void addPrenotazione(Prenotazione prenotazione) throws Exception {
		missioniCoreClientConnector.addPrenotazione(prenotazione);

	}

	/**
	 * 
	 * @param prenotazione
	 * @throws Exception
	 */
	public static void updatePrenotazione(Prenotazione prenotazione) throws Exception {
		missioniCoreClientConnector.updatePrenotazione(prenotazione);

	}

	/**
	 * 
	 * @param prenotazioneID
	 * @throws Exception
	 */
	public static void deletePrenotazione(String prenotazioneID) throws Exception {
		missioniCoreClientConnector.deletePrenotazione(prenotazioneID);

	}

	/**
	 * 
	 * @param prenotazioneSearchBuilder
	 * @return
	 * @throws Exception
	 */
	public static PrenotazioniStore getPrenotazione(PrenotazioneSearchBuilder prenotazioneSearchBuilder)
			throws Exception {
		return missioniCoreClientConnector.getPrenotazioneByQuery(prenotazioneSearchBuilder);

	}

	/**
	 * 
	 * @param veicoloCNR
	 * @throws Exception
	 */
	public static void updateVeicoloCNR(VeicoloCNR veicoloCNR) throws Exception {
		missioniCoreClientConnector.updateVeicoloCNR(veicoloCNR);

	}

	/**
	 * 
	 * @param veicoloCNR
	 * @throws Exception
	 */
	public static void addVeicoloCNR(VeicoloCNR veicoloCNR) throws Exception {
		missioniCoreClientConnector.addVeicoloCNR(veicoloCNR);
	}

	/**
	 * 
	 * @param missioneID
	 * @throws Exception
	 */
	public static void sendMissioneMail(String missioneID) throws Exception {
		missioniCoreClientConnector.sendMissioneMail(missioneID);
	}

	/**
	 * 
	 * @param missioneID
	 * @throws Exception
	 */
	public static void sendRimborsoMail(String missioneID) throws Exception {
		missioniCoreClientConnector.sendRimborsoMail(missioneID);
	}

	/**
	 * 
	 * @param missioneID
	 * @throws Exception
	 */
	public static Response downloadMissioneAsPdf(String missioneID) throws Exception {
		return missioniCoreClientConnector.downloadMissioneAsPdf(missioneID);
	}

	/**
	 * 
	 * @param missioneID
	 * @throws Exception
	 */
	public static Response downloadRimborsoMissioneAsPdf(String missioneID) throws Exception {
		return missioniCoreClientConnector.downloadRimborsoMissioneAsPdf(missioneID);
	}
	
	/**
	 * 
	 * @param location
	 * @return
	 * @throws Exception
	 */
	public static DistanceResponse.MissioneDistanceResponse getDistanceForMissione(String start, String end)
			throws Exception {
		return missioniCoreClientConnector.getDistanceForMissione(start, end);
	}

	/**
	 * 
	 * @param location
	 * @return
	 * @throws Exception
	 */
	public static GeocoderStore getGeocoderStoreForMissioneLocation(String location) throws Exception {
		return missioniCoreClientConnector.getGeocoderStoreForMissioneLocation(location);
	}

}
