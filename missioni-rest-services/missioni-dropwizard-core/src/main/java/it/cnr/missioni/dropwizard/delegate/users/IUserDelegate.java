package it.cnr.missioni.dropwizard.delegate.users;

import it.cnr.missioni.model.user.User;
import it.cnr.missioni.rest.api.response.user.UserStore;

/**
 * 
 * @author Salvia Vito
 *
 */
public interface IUserDelegate {
	
/**
 * 
 * @param username
 * @param password
 * @return
 * @throws Exception
 */
	 Boolean authorize(String username, String password) throws Exception;

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
 * @param id
 * @param from
 * @param size
 * @return
 * @throws Exception
 */
	 UserStore getUserByQuery(String nome, String cognome, String codiceFiscale, String matricola, String username,
				String targa,String numeroPatente, String cartaCircolazione, String polizzaAssicurativa, String iban, String mail, String id,int from,int size) throws Exception;

/**
 * 
 * @return
 * @throws Exception
 */
	UserStore getLastUserMissions() throws Exception;

	/**
	 * 
	 * @param user
	 * @return
	 * @throws Exception
	 */
	String addUser(User user) throws Exception;

/**
 * 
 * @param user
 * @return
 * @throws Exception
 */
	Boolean updateUser(User user) throws Exception;

/**
 * 
 * @param userID
 * @return
 * @throws Exception
 */
	Boolean deleteUser(String userID) throws Exception;
}
