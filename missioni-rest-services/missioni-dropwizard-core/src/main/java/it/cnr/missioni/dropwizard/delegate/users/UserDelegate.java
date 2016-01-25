package it.cnr.missioni.dropwizard.delegate.users;

import javax.annotation.Resource;

import org.geosdi.geoplatform.exception.IllegalParameterFault;
import org.geosdi.geoplatform.experimental.el.dao.PageResult;
import org.geosdi.geoplatform.logger.support.annotation.GeoPlatformLog;
import org.slf4j.Logger;

import com.fasterxml.uuid.EthernetAddress;
import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedGenerator;

import it.cnr.missioni.el.dao.IUserDAO;
import it.cnr.missioni.el.model.search.builder.UserSearchBuilder;
import it.cnr.missioni.model.user.User;
import it.cnr.missioni.rest.api.response.user.UserStore;

/**
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
class UserDelegate implements IUserDelegate {

	static {
		gen = Generators.timeBasedGenerator(EthernetAddress.fromInterface());
	}

	private static final TimeBasedGenerator gen;
	//
	@GeoPlatformLog
	private static Logger logger;
	//
	@Resource(name = "userDAO")
	private IUserDAO userDAO;

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
	@Override
	public UserStore getUserByQuery(String nome, String cognome, String codiceFiscale, String matricola,
			String username, String targa, String numeroPatente, String cartaCircolazione, String polizzaAssicurativa,
			String iban, String mail, String id, int from, int size) throws Exception {

		UserSearchBuilder userSearchBuilder = UserSearchBuilder.getUserSearchBuilder().withNome(nome)
				.withCognome(cognome).withCodiceFiscale(codiceFiscale).withMatricola(matricola).withUsername(username)
				.withTarga(targa).withNumeroPatente(numeroPatente).withCartaCircolazione(cartaCircolazione)
				.withPolizzaAssicurativa(polizzaAssicurativa).withIban(iban).withMail(mail).withId(id).withFrom(from)
				.withSize(size);

		PageResult<User> pageResult = this.userDAO.findUserByQuery(userSearchBuilder);
		if (!pageResult.getResults().isEmpty()) {
			UserStore userStore = new UserStore();
			userStore.setUsers(pageResult.getResults());
			userStore.setTotale(pageResult.getTotal());
			return userStore;
		} else
			return null;
	}

	/**
	 * @return
	 * @throws Exception
	 */
	@Override
	public UserStore getLastUserMissions() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 
	 * @param user
	 * @return
	 * @throws Exception
	 */
	@Override
	public String addUser(User user) throws Exception {
		if ((user == null)) {
			throw new IllegalParameterFault("The Parameter user must not be null");
		}
		this.userDAO.persist(user);
		return null;

	}

	/**
	 * 
	 * @param user
	 * @return
	 * @throws Exception
	 */
	@Override
	public Boolean updateUser(User user) throws Exception {
		if ((user == null)) {
			throw new IllegalParameterFault("The Parameter user must not be null");
		}
		this.userDAO.update(user);
		return Boolean.TRUE;
	}

	/**
	 * 
	 * @param userID
	 * @return
	 * @throws Exception
	 */
	@Override
	public Boolean deleteUser(String userID) throws Exception {
		if ((userID == null) || (userID.isEmpty())) {
			throw new IllegalParameterFault("The Parameter userID must not be null " + "or an Empty String.");
		}
		this.userDAO.delete(userID);
		return Boolean.TRUE;
	}

	/**
	 * 
	 * @param userName
	 * @param password
	 * @return
	 * @throws Exception
	 */
	@Override
	public Boolean authorize(String userName, String password) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
