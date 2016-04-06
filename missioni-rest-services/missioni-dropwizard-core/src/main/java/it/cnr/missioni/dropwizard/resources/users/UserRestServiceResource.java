package it.cnr.missioni.dropwizard.resources.users;

import it.cnr.missioni.dropwizard.delegate.users.IUserDelegate;
import it.cnr.missioni.model.user.User;
import it.cnr.missioni.rest.api.resources.user.UsersRestService;
import org.geosdi.geoplatform.logger.support.annotation.GeoPlatformLog;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.ws.rs.core.Response;

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
		return null;
	}

	/**
	 * 
	 * @param nome
	 * @param cognome
	 * @param codiceFiscale
	 * @param matricola
	 * @param username
	 * @param targa
	 * @param numeroPatente
	 * @param cartaCircolazione
	 * @param polizzaAssicurativa
	 * @param iban
	 * @param mail
	 * @param notId
	 * @param id
	 * @param responsabileGruppo
	 * @param all
	 * @param from
	 * @param size
	 * @return
	 * @throws Exception
	 */
	@Override
	public Response getUserByQuery(String nome, String cognome, String codiceFiscale, String matricola, String username,
			String targa, String numeroPatente, String cartaCircolazione, String polizzaAssicurativa, String iban,
			String mail, String notId, String id, Boolean responsabileGruppo,String multiMatch,String searchType, boolean all, int from, int size)
					throws Exception {
		return Response.ok(this.userDelegate.getUserByQuery(nome, cognome, codiceFiscale, matricola, username, targa,
				numeroPatente, cartaCircolazione, polizzaAssicurativa, iban, mail, notId, id, responsabileGruppo,multiMatch,searchType, all,
				from, size)).build();
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
