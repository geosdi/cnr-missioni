package it.cnr.missioni.dropwizard.resources.users;

import javax.annotation.Resource;
import javax.ws.rs.core.Response;

import org.geosdi.geoplatform.logger.support.annotation.GeoPlatformLog;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import it.cnr.missioni.dropwizard.delegate.users.IUserDelegate;
import it.cnr.missioni.model.user.User;
import it.cnr.missioni.rest.api.resources.user.UsersRestService;

/**
 * 
 * @author Salvia Vito
 *
 */
@Component(value = "userRestServiceResource")
public class UserRestServiceResource implements UsersRestService {

	@GeoPlatformLog
	static Logger logger;
	//
	@Resource(name = "userDelegate")
	private IUserDelegate userDelegate;

	/**
	 * @param userName
	 * @param password
	 * @return
	 * @throws Exception
	 */
	@Override
	public Response authorize(String userName, String password) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @return
	 * @throws Exception
	 */
//	@Override
//	public Response getLastUsers() throws Exception {
//		// TODO Auto-generated method stub
//		return null;
//	}

	/**
	 * @param nome
	 * @param cognome
	 * @param codiceFiscale
	 * @param matricola
	 * @param username
	 * @param targa
	 * @param cartaCircolazione
	 * @param polizzaAssicurativa
	 * @param iban
	 * @param mail
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@Override
	public Response getUserByQuery(String nome, String cognome, String codiceFiscale, String matricola, String username,
			String targa, String numeroPatente,String cartaCircolazione, String polizzaAssicurativa, String iban, String mail, String id,int from,int size)
					throws Exception {
		return Response.ok(this.userDelegate.getUserByQuery(nome, cognome, codiceFiscale, matricola, username, targa,numeroPatente,
				cartaCircolazione, polizzaAssicurativa, iban, mail, id,from,size)).build();
	}

	/**
	 * @param user
	 * @return
	 * @throws Exception
	 */
	@Override
	public Response addUser(User user) throws Exception {
		return Response.ok(this.userDelegate.addUser(user)).build();
	}

	/**
	 * @param user
	 * @return
	 * @throws Exception
	 */
	@Override
	public Response updateUser(User user) throws Exception {
		return Response.ok(this.userDelegate.updateUser(user)).build();
	}

	/**
	 * @param userID
	 * @return
	 * @throws Exception
	 */
	@Override
	public Response deleteUser(String userID) throws Exception {
		return Response.ok(this.userDelegate.deleteUser(userID)).build();
	}

}
