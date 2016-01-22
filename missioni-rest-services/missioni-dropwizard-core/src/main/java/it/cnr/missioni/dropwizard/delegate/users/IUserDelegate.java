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
	 * @param userName
	 * @param password
	 * @return
	 * @throws Exception
	 */
	 Boolean authorize(String username, String password) throws Exception;

	/**
	 * 
	 * @param userName
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
	 * @param missione
	 * @return
	 * @throws Exception
	 */
	String addUser(User user) throws Exception;

	/**
	 * 
	 * @param utente
	 * @return
	 * @throws Exception
	 */
	Boolean updateUser(User user) throws Exception;

	/**
	 * 
	 * @param utenteID
	 * @return
	 * @throws Exception
	 */
	Boolean deleteUser(String userID) throws Exception;
}
