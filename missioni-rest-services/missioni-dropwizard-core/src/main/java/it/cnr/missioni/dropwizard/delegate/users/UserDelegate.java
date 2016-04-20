package it.cnr.missioni.dropwizard.delegate.users;

import com.fasterxml.uuid.EthernetAddress;
import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedGenerator;
import it.cnr.missioni.el.dao.IUserDAO;
import it.cnr.missioni.el.model.search.builder.IUserSearchBuilder;
import it.cnr.missioni.model.user.User;
import it.cnr.missioni.notification.dispatcher.MissioniMailDispatcher;
import it.cnr.missioni.notification.message.factory.NotificationMessageFactory;
import it.cnr.missioni.rest.api.request.RecuperaPasswordRequest;
import it.cnr.missioni.rest.api.response.user.UserStore;
import org.geosdi.geoplatform.exception.IllegalParameterFault;
import org.geosdi.geoplatform.experimental.el.dao.GPPageableElasticSearchDAO;
import org.geosdi.geoplatform.logger.support.annotation.GeoPlatformLog;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

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
	@Resource(name = "missioniMailDispatcher")
	private MissioniMailDispatcher missioniMailDispatcher;
	@Autowired
	private NotificationMessageFactory notificationMessageFactory;

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
	 * @param multiMatch
	 * @param all
	 * @param from
	 * @param size
	 * @return
	 * @throws Exception
	 */
	@Override
	public UserStore getUserByQuery(String nome, String cognome, String codiceFiscale, String matricola,
			String username, String targa, String numeroPatente, String cartaCircolazione, String polizzaAssicurativa,
			String iban, String mail, String notId, String id, Boolean responsabileGruppo, String multiMatch,String searchType,
			boolean all, int from, int size) throws Exception {
		IUserSearchBuilder userSearchBuilder = IUserSearchBuilder.UserSearchBuilder.getUserSearchBuilder().withNome(nome)
				.withCognome(cognome).withCodiceFiscale(codiceFiscale)
				.withSearchType(searchType)
				.withMatricola(matricola).withUsername(username)
				.withTarga(targa).withNumeroPatente(numeroPatente).withCartaCircolazione(cartaCircolazione)
				.withPolizzaAssicurativa(polizzaAssicurativa).withIban(iban).withMail(mail).withNotId(notId).withId(id)
				.withMultiMatch(multiMatch).withResponsabileGruppo(responsabileGruppo).withAll(all).withFrom(from)
				.withSize(size);
		GPPageableElasticSearchDAO.IPageResult<User> pageResult = this.userDAO.findUserByQuery(userSearchBuilder);
		UserStore userStore = new UserStore();
		userStore.setUsers(pageResult.getResults());
		userStore.setTotale(pageResult.getTotal());
		return userStore;
	}

	/**
	 * @return
	 * @throws Exception
	 */
	@Override
	public UserStore getLastUserMissions() throws Exception {
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
		if (user.getId() == null)
			user.setId(gen.generate().toString());
		return this.userDAO.persist(user).getId();
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
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Override
	public Boolean recuperaPassword(RecuperaPasswordRequest request) throws Exception {
		if (request == null)
			throw new IllegalParameterFault("The Parameter Request must not be null.");
		this.missioniMailDispatcher.dispatchMessage(this.notificationMessageFactory.buildRecuperaPasswordMessage(request.getUserName(), request.getUserSurname(), request.getEmail(), request.getPassword()));
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
		return null;
	}

}
