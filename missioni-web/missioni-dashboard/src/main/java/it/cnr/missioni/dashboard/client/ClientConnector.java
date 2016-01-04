package it.cnr.missioni.dashboard.client;

import javax.ws.rs.client.ClientBuilder;

import org.glassfish.jersey.client.ClientConfig;

import it.cnr.missioni.connector.core.spring.connector.MissioniCoreClientConnector;
import it.cnr.missioni.connector.core.spring.connector.provider.CoreConnectorProvider;
import it.cnr.missioni.dropwizard.connector.api.settings.ConnectorClientSettings;
import it.cnr.missioni.el.model.search.builder.UserSearchBuilder;
import it.cnr.missioni.model.user.User;
import it.cnr.missioni.rest.api.response.user.UserStore;

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

	public static void addUser(User user) throws Exception {
			missioniCoreClientConnector.addUser(user);

	}
	
	public static UserStore getUser(UserSearchBuilder userSearchBuilder) throws Exception{
		return missioniCoreClientConnector.getUserByQuery(userSearchBuilder);
	}

}
