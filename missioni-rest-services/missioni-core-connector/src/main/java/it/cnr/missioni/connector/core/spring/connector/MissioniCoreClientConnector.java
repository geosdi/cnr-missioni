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

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import it.cnr.missioni.dropwizard.connector.api.connector.AbstractClientConnector;
import it.cnr.missioni.dropwizard.connector.api.settings.ConnectorClientSettings;
import it.cnr.missioni.dropwizard.connector.api.token.MissioniTokenBuilder;
import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.model.utente.User;
import it.cnr.missioni.rest.api.response.missione.MissioniStore;

/**
 *
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
public class MissioniCoreClientConnector extends AbstractClientConnector {

	static final String LOGGER_MESSAGE = "\n\t@@@@@@@@@@@@@@@@@@@@@@@@@ACQUIRE ACCESS_TOKEN VALUE "
			+ ": {} for Method : {}\n";

	public MissioniCoreClientConnector(ConnectorClientSettings theClientSettings, Client theClient,
			MissioniTokenBuilder theTokenBuilder) {
		super(theClientSettings, theClient, theTokenBuilder);
	}

	public Missione getMissioneById(String missioneID) throws Exception {
//		String accessToken = super.createToken();

//		logger.trace(LOGGER_MESSAGE, accessToken, "insertOrganization");
		

		return client.target(super.getRestServiceURL()
//				.concat("v1/missioni/getMissioneByID/")
				)
				.path("v1/missioni/getMissioneByID/")
				.queryParam("missioneID", missioneID)
				.request(MediaType.APPLICATION_JSON)
//				.header(HttpHeaders.AUTHORIZATION, "bearer ".concat(accessToken))
				.get(Missione.class);
	}
	
	public MissioniStore getLastUserMissions(String userId) throws Exception {
		return client.target(super.getRestServiceURL()				)
				.path("v1/missioni/getLastUserMissions/")
				.queryParam("userID", userId)
				.request(MediaType.APPLICATION_JSON)
				.get(MissioniStore.class);
	}
	
	
	public Long addMissione(Missione missione) throws Exception {
		return client.target(super.getRestServiceURL()				)
				.path("v1/missioni/addMissione/")
				.request(MediaType.APPLICATION_JSON)
				 .post(Entity.entity(missione,
				 MediaType.APPLICATION_JSON), Long.class);
	}
	
	public Boolean deleteMissione(String missioneID) throws Exception {
		return client.target(super.getRestServiceURL()				)
				.path("v1/missioni/deleteMissione/")
				.queryParam("missioneID", missioneID)
				.request(MediaType.APPLICATION_JSON)
				.delete(Boolean.class);
	}
	
	public boolean updateMissione(Missione missione) throws Exception {
		return client.target(super.getRestServiceURL()				)
				.path("v1/missioni/updateMissione/")
				.request(MediaType.APPLICATION_JSON)
				.put(Entity.entity(missione,
				 MediaType.APPLICATION_JSON), Boolean.class);
	}
	
	public User getUserByUsername(String userName) throws Exception {
		return client.target(super.getRestServiceURL()
				)
				.path("v1/users/getUserByUserName/")
				.queryParam("username", userName)
				.request(MediaType.APPLICATION_JSON)
				.get(User.class);
	}
	
	
	public Long addUser(User user) throws Exception {
		return client.target(super.getRestServiceURL()				)
				.path("v1/users/addUser/")
				.request(MediaType.APPLICATION_JSON)
				 .post(Entity.entity(user,
				 MediaType.APPLICATION_JSON), Long.class);
	}
	
	public Boolean deleteUser(String userID) throws Exception {
		return client.target(super.getRestServiceURL()				)
				.path("v1/users/deleteUser/")
				.queryParam("userID", userID)
				.request(MediaType.APPLICATION_JSON)
				.delete(Boolean.class);
	}
	
	public boolean updateUser(User user) throws Exception {
		return client.target(super.getRestServiceURL()				)
				.path("v1/users/updateUser/")
				.request(MediaType.APPLICATION_JSON)
				.put(Entity.entity(user,
				 MediaType.APPLICATION_JSON), Boolean.class);
	}
	
	
	@Override
	public String getConnectorName() {
		return "CNR MISSIONI Core Client Connector";
	}
}
