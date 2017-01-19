/*
 *  geo-platform
 *  Rich webgis framework
 *  http://geo-platform.org
 * ====================================================================
 *
 * Copyright (C) 2008-2014 geoSDI Group (CNR IMAA - Potenza - ITALY).
 *
 * This program is free software: you can redistribute it and/or modify it 
 * under the terms of the GNU General Public License as published by 
 * the Free Software Foundation, either version 3 of the License, or 
 * (at your option) any later version. This program is distributed in the 
 * hope that it will be useful, but WITHOUT ANY WARRANTY; without 
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR 
 * A PARTICULAR PURPOSE. See the GNU General Public License 
 * for more details. You should have received a copy of the GNU General 
 * Public License along with this program. If not, see http://www.gnu.org/licenses/ 
 *
 * ====================================================================
 *
 * Linking this library statically or dynamically with other modules is 
 * making a combined work based on this library. Thus, the terms and 
 * conditions of the GNU General Public License cover the whole combination. 
 * 
 * As a special exception, the copyright holders of this library give you permission 
 * to link this library with independent modules to produce an executable, regardless 
 * of the license terms of these independent modules, and to copy and distribute 
 * the resulting executable under terms of your choice, provided that you also meet, 
 * for each linked independent module, the terms and conditions of the license of 
 * that module. An independent module is a module which is not derived from or 
 * based on this library. If you modify this library, you may extend this exception 
 * to your version of the library, but you are not obligated to do so. If you do not 
 * wish to do so, delete this exception statement from your version. 
 *
 */
package it.cnr.missioni.connector.core.spring.connector;

import it.cnr.missioni.dropwizard.connector.api.connector.AbstractClientConnector;
import it.cnr.missioni.dropwizard.connector.api.settings.ConnectorClientSettings;
import it.cnr.missioni.el.model.bean.StatisticheMissioni;
import it.cnr.missioni.el.model.search.builder.*;
import it.cnr.missioni.model.configuration.*;
import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.model.prenotazione.Prenotazione;
import it.cnr.missioni.model.prenotazione.VeicoloCNR;
import it.cnr.missioni.model.user.User;
import it.cnr.missioni.rest.api.request.NotificationMissionRequest;
import it.cnr.missioni.rest.api.request.RecuperaPasswordRequest;
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

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
public class MissioniCoreClientConnector extends AbstractClientConnector {

	static final String LOGGER_MESSAGE = "\n\t@@@@@@@@@@@@@@@@@@@@@@@@@ACQUIRE ACCESS_TOKEN VALUE "
			+ ": {} for Method : {}\n";

	public MissioniCoreClientConnector(ConnectorClientSettings theClientSettings, Client theClient) {
		super(theClientSettings, theClient);
	}

	public MissioniStore getMissioneByQuery(IMissioneSearchBuilder missioneSearchBuilder) throws Exception {
		return client.target(super.getRestServiceURL()).path("v1/missioni/getMissioneByQuery/")
				.queryParam("idMissione", missioneSearchBuilder.getId())
				.queryParam("idUser", missioneSearchBuilder.getIdUser())
				.queryParam("stato", missioneSearchBuilder.getStato())
				.queryParam("numeroOrdineRimborso", missioneSearchBuilder.getNumeroOrdineRimborso())
				.queryParam("dataFromMissione",
						missioneSearchBuilder.getFromDataInserimento() != null
								? missioneSearchBuilder.getFromDataInserimento().toDateTime().getMillis() : null)
				.queryParam("dataToMissione",
						missioneSearchBuilder.getToDataInserimento() != null
								? missioneSearchBuilder.getToDataInserimento().toDateTime().getMillis() : null)
				.queryParam("dataFromRimborso",
						missioneSearchBuilder.getFromDataRimbroso() != null
								? missioneSearchBuilder.getFromDataRimbroso().toDateTime().getMillis() : null)
				.queryParam("dataToRimborso",
						missioneSearchBuilder.getToDataRimbroso() != null
								? missioneSearchBuilder.getToDataRimbroso().toDateTime().getMillis() : null)
				.queryParam("oggetto", missioneSearchBuilder.getOggetto())
				.queryParam("multiMatch", missioneSearchBuilder.getMultiMatchValue())
				.queryParam("fieldExist", missioneSearchBuilder.getFieldExist())
				.queryParam("fieldNotExist", missioneSearchBuilder.getFieldNotExist())
				.queryParam("from", missioneSearchBuilder.getFrom()).queryParam("size", missioneSearchBuilder.getSize())
				.request(MediaType.APPLICATION_JSON).get(MissioniStore.class);
	}

	public String addMissione(Missione missione) throws Exception {
		return client.target(super.getRestServiceURL()).path("v1/missioni/addMissione/")
				.request(MediaType.APPLICATION_JSON)
				.post(Entity.entity(missione, MediaType.APPLICATION_JSON), String.class);
	}

	public Boolean deleteMissione(String missioneID) throws Exception {
		return client.target(super.getRestServiceURL()).path("v1/missioni/deleteMissione/")
				.queryParam("missioneID", missioneID).request(MediaType.APPLICATION_JSON).delete(Boolean.class);
	}

	public boolean updateMissione(Missione missione) throws Exception {
		return client.target(super.getRestServiceURL()).path("v1/missioni/updateMissione/")
				.request(MediaType.APPLICATION_JSON)
				.put(Entity.entity(missione, MediaType.APPLICATION_JSON), Boolean.class);
	}

	public boolean updateMissioneForAnticipo(Missione missione) throws Exception {
		return client.target(super.getRestServiceURL()).path("v1/missioni/updateMissioneForAnticipo/")
				.request(MediaType.APPLICATION_JSON)
				.put(Entity.entity(missione, MediaType.APPLICATION_JSON), Boolean.class);
	}

	public boolean updateRimborso(Missione missione) throws Exception {
		return client.target(super.getRestServiceURL()).path("v1/missioni/updateRimborso/")
				.request(MediaType.APPLICATION_JSON)
				.put(Entity.entity(missione, MediaType.APPLICATION_JSON), Boolean.class);
	}

	public UserStore getUserByQuery(IUserSearchBuilder userSearchBuilder) throws Exception {
		return client.target(super.getRestServiceURL()).path("v1/users/getUserByQuery/")
				.queryParam("nome", userSearchBuilder.getNome()).queryParam("cognome", userSearchBuilder.getCognome())
				.queryParam("codiceFiscale", userSearchBuilder.getCodiceFiscale())
				.queryParam("matricola", userSearchBuilder.getMatricola())
				.queryParam("username", userSearchBuilder.getUsername())
				.queryParam("targa", userSearchBuilder.getTarga())
				.queryParam("multiMatch", userSearchBuilder.getMultiMatchValue())
				.queryParam("numeroPatente", userSearchBuilder.getNumeroPatente())
				.queryParam("cartaCircolazione", userSearchBuilder.getCartaCircolazione())
				.queryParam("polizzaAssicurativa", userSearchBuilder.getPolizzaAssicurativa())
				.queryParam("iban", userSearchBuilder.getIban()).queryParam("mail", userSearchBuilder.getMail())
				.queryParam("notId", userSearchBuilder.getNotId()).queryParam("id", userSearchBuilder.getId())
				.queryParam("searchType", userSearchBuilder.getSearchType())
				.queryParam("responsabileGruppo", userSearchBuilder.isResponsabileGruppo())
				.queryParam("all", userSearchBuilder.isAll()).queryParam("from", userSearchBuilder.getFrom())
				.queryParam("size", userSearchBuilder.getSize()).request(MediaType.APPLICATION_JSON)
				.get(UserStore.class);
	}

	public User getUserByUsername(String username) throws Exception {
		return client.target(super.getRestServiceURL()).path("v1/users/getUserByUsername/")
				.queryParam("username", username).request(MediaType.APPLICATION_JSON).get(User.class);
	}

	public String addUser(User user) throws Exception {
		return client.target(super.getRestServiceURL()).path("v1/users/addUser/").request(MediaType.APPLICATION_JSON)
				.post(Entity.entity(user, MediaType.APPLICATION_JSON), String.class);
	}

	public Boolean deleteUser(String userID) throws Exception {
		return client.target(super.getRestServiceURL()).path("v1/users/deleteUser/").queryParam("userID", userID)
				.request(MediaType.APPLICATION_JSON).delete(Boolean.class);
	}

	public boolean updateUser(User user) throws Exception {
		return client.target(super.getRestServiceURL()).path("v1/users/updateUser/").request(MediaType.APPLICATION_JSON)
				.put(Entity.entity(user, MediaType.APPLICATION_JSON), Boolean.class);
	}

	public PrenotazioniStore getPrenotazioneByQuery(IPrenotazioneSearchBuilder prenotazioneSearchBuilder)
			throws Exception {
		return client.target(super.getRestServiceURL()).path("v1/prenotazioni/getPrenotazioniByQuery/")
				.queryParam("dataFrom", prenotazioneSearchBuilder.getDataFrom().toDateTime().getMillis())
				.queryParam("dataTo", prenotazioneSearchBuilder.getDataTo().toDateTime().getMillis())
				.request(MediaType.APPLICATION_JSON).get(PrenotazioniStore.class);
	}

	public String addPrenotazione(Prenotazione prenotazione) throws Exception {
		return client.target(super.getRestServiceURL()).path("v1/prenotazioni/addPrenotazione/")
				.request(MediaType.APPLICATION_JSON)
				.post(Entity.entity(prenotazione, MediaType.APPLICATION_JSON), String.class);
	}

	public Boolean deletePrenotazione(String prenotazioneID) throws Exception {
		return client.target(super.getRestServiceURL()).path("v1/prenotazioni/deletePrenotazione/")
				.queryParam("prenotazioneID", prenotazioneID).request(MediaType.APPLICATION_JSON).delete(Boolean.class);
	}

	public boolean updatePrenotazione(Prenotazione prenotazione) throws Exception {
		return client.target(super.getRestServiceURL()).path("v1/prenotazioni/updatePrenotazione/")
				.request(MediaType.APPLICATION_JSON)
				.put(Entity.entity(prenotazione, MediaType.APPLICATION_JSON), Boolean.class);
	}

	public VeicoloCNRStore getVeicoloCNRByQuery(IVeicoloCNRSearchBuilder veicoloCNRSearchBuilder) throws Exception {
		return client.target(super.getRestServiceURL()).path("v1/veicoloCNR/getVeicoloCNRByQuery/")
				.queryParam("stato", veicoloCNRSearchBuilder.getStato())
				.queryParam("targa", veicoloCNRSearchBuilder.getTarga())
				.queryParam("cartaCircolazione", veicoloCNRSearchBuilder.getCartaCircolazione())
				.queryParam("polizzaAssicurativa", veicoloCNRSearchBuilder.getPolizzaAssicurativa())
				.queryParam("notId", veicoloCNRSearchBuilder.getNotId())
				.queryParam("id", veicoloCNRSearchBuilder.getId()).queryParam("from", veicoloCNRSearchBuilder.getFrom())
				.queryParam("size", veicoloCNRSearchBuilder.getSize())
				.queryParam("all", veicoloCNRSearchBuilder.isAll()).request(MediaType.APPLICATION_JSON)
				.get(VeicoloCNRStore.class);
	}

	public String addVeicoloCNR(VeicoloCNR veicoloCNR) throws Exception {
		return client.target(super.getRestServiceURL()).path("v1/veicoloCNR/addVeicoloCNR/")
				.request(MediaType.APPLICATION_JSON)
				.post(Entity.entity(veicoloCNR, MediaType.APPLICATION_JSON), String.class);
	}

	public Boolean deleteVeicoloCNR(String veicoloCNRID) throws Exception {
		return client.target(super.getRestServiceURL()).path("v1/veicoloCNR/deleteVeicoloCNR/")
				.queryParam("veicoloCNRID", veicoloCNRID).request(MediaType.APPLICATION_JSON).delete(Boolean.class);
	}

	public boolean updateVeicoloCNR(VeicoloCNR veicoloCNR) throws Exception {
		return client.target(super.getRestServiceURL()).path("v1/veicoloCNR/updateVeicoloCNR/")
				.request(MediaType.APPLICATION_JSON)
				.put(Entity.entity(veicoloCNR, MediaType.APPLICATION_JSON), Boolean.class);
	}

	public boolean sendMissioneMail(String missioneID) throws Exception {
		NotificationMissionRequest request = new NotificationMissionRequest();
		request.setMissionID(missioneID);
		return client.target(super.getRestServiceURL()).path("v1/missioni/notifyMissionAdministration/")
				.request(MediaType.APPLICATION_JSON)
				.post(Entity.entity(request, MediaType.APPLICATION_JSON), Boolean.class);
	}

	public boolean sendRecuperaPasswordMail(String userName, String userSurname, String email, String password)
			throws Exception {
		RecuperaPasswordRequest request = new RecuperaPasswordRequest();
		request.setEmail(email);
		request.setUserName(userName);
		request.setPassword(password);
		request.setUserSurname(userSurname);
		return client.target(super.getRestServiceURL()).path("v1/users/recuperaPassword/")
				.request(MediaType.APPLICATION_JSON)
				.post(Entity.entity(request, MediaType.APPLICATION_JSON), Boolean.class);
	}

	public boolean sendRimborsoMail(String missioneID) throws Exception {
		NotificationMissionRequest request = new NotificationMissionRequest();
		request.setMissionID(missioneID);
		return client.target(super.getRestServiceURL()).path("v1/missioni/notifyRimborsoMissionAdministration/")
				.request(MediaType.APPLICATION_JSON)
				.post(Entity.entity(request, MediaType.APPLICATION_JSON), Boolean.class);
	}

	public Response downloadMissioneAsPdf(String missioneID) throws Exception {
		return client.target(super.getRestServiceURL()).path("v1/missioni/downloadMissioneAsPdf/")
				.queryParam("missionID", missioneID).request("application/pdf")

				.get(Response.class);
	}

	public Response downloadVeicoloMissioneAsPdf(String missioneID) throws Exception {
		return client.target(super.getRestServiceURL()).path("v1/missioni/downloadVeicoloMissioneAsPdf/")
				.queryParam("missionID", missioneID).request("application/pdf")

				.get(Response.class);
	}

	public Response downloadAnticipoPagamentoAsPdf(String missioneID) throws Exception {
		return client.target(super.getRestServiceURL()).path("v1/missioni/downloadAnticipoPagamentoAsPdf/")
				.queryParam("missionID", missioneID).request("application/pdf")

				.get(Response.class);
	}

	public Response downloadRimborsoMissioneAsPdf(String missioneID) throws Exception {
		return client.target(super.getRestServiceURL()).path("v1/missioni/downloadRimborsoMissioneAsPdf/")
				.queryParam("missionID", missioneID).request("application/pdf")

				.get(Response.class);
	}

	public DistanceResponse.MissioneDistanceResponse getDistanceForMissione(String start, String end) throws Exception {
		return client.target(super.getRestServiceURL()).path("v1/missioni/getDistanceForMissione/")
				.queryParam("start", start).queryParam("end", end).request(MediaType.APPLICATION_JSON)
				.get(DistanceResponse.MissioneDistanceResponse.class);
	}
	
	public Double getNewDistanceForMissione(String start, String end) throws Exception {
		return client.target(super.getRestServiceURL()).path("v1/missioni/getNewDistanceForMissione/")
				.queryParam("start", start).queryParam("end", end).request(MediaType.APPLICATION_JSON)
				.get(Double.class);
	}

	public GeocoderStore getGeocoderStoreForMissioneLocation(String location) throws Exception {
		return client.target(super.getRestServiceURL()).path("v1/missioni/getGeocoderStoreForMissioneLocation/")
				.queryParam("location", location).request(MediaType.APPLICATION_JSON).get(GeocoderStore.class);
	}

	public QualificaUserStore getQualificaUserByQuery(IQualificaUserSearchBuilder qualificaUserSearchBuilder)
			throws Exception {
		return client.target(super.getRestServiceURL()).path("v1/qualificaUser/getQualificaUserByQuery/")
				.queryParam("id", qualificaUserSearchBuilder.getId())
				.queryParam("from", qualificaUserSearchBuilder.getFrom())
				.queryParam("size", qualificaUserSearchBuilder.getSize())
				.queryParam("all", qualificaUserSearchBuilder.isAll()).request(MediaType.APPLICATION_JSON)
				.get(QualificaUserStore.class);
	}

	public String addQualificaUser(QualificaUser qualificaUser) throws Exception {
		return client.target(super.getRestServiceURL()).path("v1/qualificaUser/addQualificaUser/")
				.request(MediaType.APPLICATION_JSON)
				.post(Entity.entity(qualificaUser, MediaType.APPLICATION_JSON), String.class);
	}

	public Boolean deleteQualificaUser(String qualificaUserID) throws Exception {
		return client.target(super.getRestServiceURL()).path("v1/qualificaUser/deleteQualificaUser/")
				.queryParam("qualificaUserID", qualificaUserID).request(MediaType.APPLICATION_JSON)
				.delete(Boolean.class);
	}

	public boolean updateQualificaUser(QualificaUser qualificaUser) throws Exception {
		return client.target(super.getRestServiceURL()).path("v1/qualificaUser/updateQualificaUser/")
				.request(MediaType.APPLICATION_JSON)
				.put(Entity.entity(qualificaUser, MediaType.APPLICATION_JSON), Boolean.class);
	}

	public NazioneStore getNazioneByQuery(INazioneSearchBuilder nazioneSearchBuilder) throws Exception {
		return client.target(super.getRestServiceURL()).path("v1/nazione/getNazioneByQuery/")
				.queryParam("id", nazioneSearchBuilder.getId()).queryParam("from", nazioneSearchBuilder.getFrom())
				.queryParam("size", nazioneSearchBuilder.getSize()).queryParam("all", nazioneSearchBuilder.isAll())
				.request(MediaType.APPLICATION_JSON).get(NazioneStore.class);
	}

	public String addNazione(Nazione nazione) throws Exception {
		return client.target(super.getRestServiceURL()).path("v1/nazione/addNazione/")
				.request(MediaType.APPLICATION_JSON)
				.post(Entity.entity(nazione, MediaType.APPLICATION_JSON), String.class);
	}

	public Boolean deleteNazione(String nazioneID) throws Exception {
		return client.target(super.getRestServiceURL()).path("v1/nazione/deleteNazione/")
				.queryParam("nazioneID", nazioneID).request(MediaType.APPLICATION_JSON).delete(Boolean.class);
	}

	public boolean updateNazione(Nazione nazione) throws Exception {
		return client.target(super.getRestServiceURL()).path("v1/nazione/updateNazione/")
				.request(MediaType.APPLICATION_JSON)
				.put(Entity.entity(nazione, MediaType.APPLICATION_JSON), Boolean.class);
	}

	public RimborsoKmStore getRimborsoKmByQuery(IRimborsoKmSearchBuilder rimborsoKmSearchBuilder) throws Exception {
		return client.target(super.getRestServiceURL()).path("v1/rimborsoKm/getRimborsoKmByQuery/")
				.request(MediaType.APPLICATION_JSON).get(RimborsoKmStore.class);
	}

	public String addRimborsoKm(RimborsoKm rimborsoKm) throws Exception {
		return client.target(super.getRestServiceURL()).path("v1/rimborsoKm/addRimborsoKm/")
				.request(MediaType.APPLICATION_JSON)
				.post(Entity.entity(rimborsoKm, MediaType.APPLICATION_JSON), String.class);
	}

	public Boolean deleteRimborsoKm(String rimborsoKmID) throws Exception {
		return client.target(super.getRestServiceURL()).path("v1/rimborsoKm/deleteRimborsoKm/")
				.queryParam("rimborsoKmID", rimborsoKmID).request(MediaType.APPLICATION_JSON).delete(Boolean.class);
	}

	public boolean updateRimborsoKm(RimborsoKm rimborsoKm) throws Exception {
		return client.target(super.getRestServiceURL()).path("v1/rimborsoKm/updateRimborsoKm/")
				.request(MediaType.APPLICATION_JSON)
				.put(Entity.entity(rimborsoKm, MediaType.APPLICATION_JSON), Boolean.class);
	}

	public TipologiaSpesaStore getTipologiaSpesaByQuery(ITipologiaSpesaSearchBuilder tipologiaSpesaSearchBuilder)
			throws Exception {
		return client.target(super.getRestServiceURL()).path("v1/tipologiaSpesa/getTipologiaSpesaByQuery/")
				.queryParam("id", tipologiaSpesaSearchBuilder.getId())
				.queryParam("estera", tipologiaSpesaSearchBuilder.isEstera())
				.queryParam("italia", tipologiaSpesaSearchBuilder.isItalia())
				.queryParam("tipologiaTrattamento", tipologiaSpesaSearchBuilder.getTipoTrattamento())
				.queryParam("from", tipologiaSpesaSearchBuilder.getFrom())
				.queryParam("size", tipologiaSpesaSearchBuilder.getSize())
				.queryParam("all", tipologiaSpesaSearchBuilder.isAll()).request(MediaType.APPLICATION_JSON)
				.get(TipologiaSpesaStore.class);
	}

	public String addTipologiaSpesa(TipologiaSpesa tipologiaSpesa) throws Exception {
		return client.target(super.getRestServiceURL()).path("v1/tipologiaSpesa/addTipologiaSpesa/")
				.request(MediaType.APPLICATION_JSON)
				.post(Entity.entity(tipologiaSpesa, MediaType.APPLICATION_JSON), String.class);
	}

	public Boolean deleteTipologiaSpesa(String tipologiaSpesaID) throws Exception {
		return client.target(super.getRestServiceURL()).path("v1/tipologiaSpesa/deleteTipologiaSpesa/")
				.queryParam("tipologiaSpesaID", tipologiaSpesaID).request(MediaType.APPLICATION_JSON)
				.delete(Boolean.class);
	}

	public boolean updateTipologiaSpesa(TipologiaSpesa tipologiaSpesa) throws Exception {
		return client.target(super.getRestServiceURL()).path("v1/tipologiaSpesa/updateTipologiaSpesa/")
				.request(MediaType.APPLICATION_JSON)
				.put(Entity.entity(tipologiaSpesa, MediaType.APPLICATION_JSON), Boolean.class);
	}

	public MassimaleStore getMassimaleByQuery(IMassimaleSearchBuilder massimaleSpesaSearchBuilder) throws Exception {
		return client.target(super.getRestServiceURL()).path("v1/massimale/getMassimaleByQuery/")
				.queryParam("from", massimaleSpesaSearchBuilder.getFrom())
				.queryParam("size", massimaleSpesaSearchBuilder.getSize())
				.queryParam("livello", massimaleSpesaSearchBuilder.getLivello())
				.queryParam("areaGeografica", massimaleSpesaSearchBuilder.getAreaGeografica())
				.queryParam("id", massimaleSpesaSearchBuilder.getId())
				.queryParam("notId", massimaleSpesaSearchBuilder.getNotId())
				.queryParam("tipo", massimaleSpesaSearchBuilder.getTipo())
				// .queryParam("id", massimaleSpesaSearchBuilder.getId())
				.request(MediaType.APPLICATION_JSON).get(MassimaleStore.class);
	}

	public String addMassimale(Massimale massimale) throws Exception {
		return client.target(super.getRestServiceURL()).path("v1/massimale/addMassimale/")
				.request(MediaType.APPLICATION_JSON)
				.post(Entity.entity(massimale, MediaType.APPLICATION_JSON), String.class);
	}

	public Boolean deleteMassimale(String massimaleID) throws Exception {
		return client.target(super.getRestServiceURL()).path("v1/massimale/deleteMassimale/")
				.queryParam("massimaleID", massimaleID).request(MediaType.APPLICATION_JSON).delete(Boolean.class);
	}

	public boolean updateMassimale(Massimale massimale) throws Exception {
		return client.target(super.getRestServiceURL()).path("v1/massimale/updateMassimale/")
				.request(MediaType.APPLICATION_JSON)
				.put(Entity.entity(massimale, MediaType.APPLICATION_JSON), Boolean.class);
	}

	public StatisticheMissioni getStatistiche() throws Exception {
		return client.target(super.getRestServiceURL()).path("v1/missioni/getStatistiche/")
				.request(MediaType.APPLICATION_JSON).get(StatisticheMissioni.class);
	}

	@Override
	public String getConnectorName() {
		return "CNR MISSIONI Core Client Connector";
	}
}
