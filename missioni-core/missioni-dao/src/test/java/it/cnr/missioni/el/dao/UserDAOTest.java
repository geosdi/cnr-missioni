package it.cnr.missioni.el.dao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.geosdi.geoplatform.experimental.el.configurator.GPIndexConfigurator;
import org.geosdi.geoplatform.experimental.el.index.GPIndexCreator;
import org.geosdi.geoplatform.logger.support.annotation.GeoPlatformLog;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import it.cnr.missioni.el.model.search.builder.IUserSearchBuilder;
import it.cnr.missioni.el.utility.UserFunction;
import it.cnr.missioni.model.user.User;
import it.cnr.missioni.support.builder.generator.IMd5PasswordGenerator;

/**
 * @author Salvia Vito
 */
@FixMethodOrder(value = MethodSorters.NAME_ASCENDING)
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext-DAO-Test.xml"})
public class UserDAOTest {

    @GeoPlatformLog
    static Logger logger;
    @Resource(name = "missioniIndexConfigurator")
    private GPIndexConfigurator missioniDocIndexConfigurator;

    @Resource(name = "userIndexCreator")
    private GPIndexCreator userDocIndexCreator;

    @Resource(name = "userDAO")
    private IUserDAO userDAO;

    private List<User> listaUsers = new ArrayList<User>();

    @Before
    public void setUp() {
        Assert.assertNotNull(userDocIndexCreator);
        Assert.assertNotNull(missioniDocIndexConfigurator);
        Assert.assertNotNull(userDAO);
    }

    @Test
    public void A_createUserCNRTest() throws Exception {
        listaUsers = UserFunction.creaMassiveUsers();
        userDAO.persist(listaUsers);
        Thread.sleep(1000);
        logger.debug("############################NUMBER_OF_USERS: {}\n", this.userDAO.count().intValue());
    }

//    @Test
//    public void B_findUserByUsernameValidaTest() throws Exception {
//        IUserSearchBuilder userSearchBuilder = IUserSearchBuilder.UserSearchBuilder.getUserSearchBuilder()
//                .withUsername("vito.salvia");
//        List<User> lista = userDAO.findUserByQuery(userSearchBuilder).getResults();
//        logger.debug("############################UTENTE_WITH_USERNAME : {}\n",
//                lista.get(0).getAnagrafica().getCognome() + " " + lista.get(0).getAnagrafica().getNome());
//        Assert.assertTrue("FIND USER BY USERNAME VALIDA", lista.get(0) != null);
//    }
    
    @Test
    public void B_findUserByUsernameValida_Test() throws Exception {
        User user = userDAO.findUserByUsername("vito.salvia");
        logger.debug("############################UTENTE_WITH_USERNAME : {}\n",
        		user.getAnagrafica().getCognome() + " " + user.getAnagrafica().getNome());
        Assert.assertTrue("FIND USER BY USERNAME VALIDA", user != null);
    }

    @Test
    public void C_findUserByUsernameErrataTest() throws Exception {
        User user = userDAO.findUserByUsername("vito.salvi");
        Assert.assertTrue("FIND USER BY USERNAME VALIDA", user == null);
    }

    @Test
    public void D_updatePasswordUser() throws Exception {
        IUserSearchBuilder userSearchBuilder = IUserSearchBuilder.UserSearchBuilder.getUserSearchBuilder()
                .withUsername("vito.salvia");
        List<User> lista = userDAO.findUserByQuery(userSearchBuilder).getResults();
        User user = lista.get(0);
        String oldPassword = user.getCredenziali().getPassword();
        user.getCredenziali().setPassword(IMd5PasswordGenerator.Md5PasswordGenerator.getMd5PasswordGenerator().withPassword("salvia.vito").build());
        userDAO.update(user);
        Thread.sleep(1000);
        lista = userDAO.findUserByQuery(userSearchBuilder).getResults();
        user = lista.get(0);
        String newPassword = user.getCredenziali().getPassword();
        Assert.assertTrue("Update Password User", !oldPassword.equals(newPassword));

    }

    @Test
    public void E_findUserByCognome() throws Exception {
        IUserSearchBuilder userSearchBuilder = IUserSearchBuilder.UserSearchBuilder.getUserSearchBuilder()
                .withCognome("Salv");
        List<User> lista = userDAO.findUserByQuery(userSearchBuilder).getResults();
        Assert.assertTrue("FIND USER BY COGNOME", lista.size() == 1);
    }

    @Test
    public void F_findUserByNome() throws Exception {
        IUserSearchBuilder userSearchBuilder = IUserSearchBuilder.UserSearchBuilder.getUserSearchBuilder()
                .withNome("Vi");
        List<User> lista = userDAO.findUserByQuery(userSearchBuilder).getResults();
        Assert.assertTrue("FIND USER BY NOME", lista.size() == 1);
    }

    @Test
    public void G_findUserByCodiceFiscale() throws Exception {
        IUserSearchBuilder userSearchBuilder = IUserSearchBuilder.UserSearchBuilder.getUserSearchBuilder()
                .withCodiceFiscale("slvv");
        List<User> lista = userDAO.findUserByQuery(userSearchBuilder).getResults();
        Assert.assertTrue("FIND USER BY CODICE FISCALE", lista.size() == 1);
    }

    @Test
    public void G_findUserALL() throws Exception {

        IUserSearchBuilder userSearchBuilder = IUserSearchBuilder.UserSearchBuilder.getUserSearchBuilder()
                .withCodiceFiscale("slvvtttttttttttt").withNome("Vito").withCognome("salvia").withMatricola("1111111");
        List<User> lista = userDAO.findUserByQuery(userSearchBuilder).getResults();
        Assert.assertTrue("FIND USER BY ALL", lista.size() == 1);
    }

    @Test
    public void G_findUserErrataALL() throws Exception {
        IUserSearchBuilder userSearchBuilder = IUserSearchBuilder.UserSearchBuilder.getUserSearchBuilder()
                .withCodiceFiscale("slvvttttttttttt").withNome("Vito").withCognome("salvia").withMatricola("4111111");
        List<User> lista = userDAO.findUserByQuery(userSearchBuilder).getResults();
        Assert.assertTrue("FIND USER BY ALL", lista.size() == 0);
    }

    @Test
    public void H_findUserByMatricola() throws Exception {
        IUserSearchBuilder userSearchBuilder = IUserSearchBuilder.UserSearchBuilder.getUserSearchBuilder()
                .withSearchType("exact").withMatricola("1111111");
        List<User> lista = userDAO.findUserByQuery(userSearchBuilder).getResults();
        Assert.assertTrue("FIND USER BY MATRICOLA", lista.size() == 1);
    }

    @Test
    public void I_findUserByMatricolaErrata() throws Exception {
        IUserSearchBuilder userSearchBuilder = IUserSearchBuilder.UserSearchBuilder.getUserSearchBuilder()
                .withMatricola("2111111");
        List<User> lista = userDAO.findUserByQuery(userSearchBuilder).getResults();
        Assert.assertTrue("FIND USER BY MATRICOLA ERRATA", lista.size() == 0);
    }

    @Test
    public void L_findUserByTarga() throws Exception {
        IUserSearchBuilder userSearchBuilder = IUserSearchBuilder.UserSearchBuilder.getUserSearchBuilder()
                .withTarga("AA111BB");
        List<User> lista = userDAO.findUserByQuery(userSearchBuilder).getResults();
        Assert.assertTrue("FIND USER BY TARGA", lista.size() == 1);
    }

    @Test
    public void M_testUserByIDMustNot() throws Exception {
        IUserSearchBuilder userSearchBuilder = IUserSearchBuilder.UserSearchBuilder.getUserSearchBuilder()
                .withNotId("01");
        List<User> lista = userDAO.findUserByQuery(userSearchBuilder).getResults();
        // logger.debug("############################USER_ID FOUND{}\n",
        // userStore);
        Assert.assertTrue("FIND USER BY ID", lista.size() == 0);

    }

    @Test
    public void N_testUserByIDCodiceFiscale() throws Exception {
        IUserSearchBuilder userSearchBuilder = IUserSearchBuilder.UserSearchBuilder.getUserSearchBuilder()
                .withNotId("01").withCodiceFiscale("slvvttttttttttttt");
        List<User> lista = userDAO.findUserByQuery(userSearchBuilder).getResults();
        Assert.assertTrue("FIND USER BY ID", lista.size() == 0);

    }

    @Test
    public void O_testUserByIDIban() throws Exception {
        IUserSearchBuilder userSearchBuilder = IUserSearchBuilder.UserSearchBuilder.getUserSearchBuilder()
                .withNotId("01").withIban("0000000000000000");
        List<User> lista = userDAO.findUserByQuery(userSearchBuilder).getResults();
        Assert.assertTrue("FIND USER BY ID", lista.size() == 0);

    }

    @Test
    public void P_testUserByIDMatricola() throws Exception {
        IUserSearchBuilder userSearchBuilder = IUserSearchBuilder.UserSearchBuilder.getUserSearchBuilder()
                .withNotId("01").withMatricola("1111111");
        List<User> lista = userDAO.findUserByQuery(userSearchBuilder).getResults();
        Assert.assertTrue("FIND USER BY ID", lista.size() == 0);

    }

    @Test
    public void P_testUserByIDMail() throws Exception {
        IUserSearchBuilder userSearchBuilder = IUserSearchBuilder.UserSearchBuilder.getUserSearchBuilder()
                .withNotId("01").withMail("vito.salvia@gmail.com");
        List<User> lista = userDAO.findUserByQuery(userSearchBuilder).getResults();
        Assert.assertTrue("FIND USER BY ID", lista.size() == 0);

    }

    @Test
    public void Q_testUserByIDTarga() throws Exception {
        IUserSearchBuilder userSearchBuilder = IUserSearchBuilder.UserSearchBuilder.getUserSearchBuilder()
                .withNotId("01").withTarga("AA111BB");
        List<User> lista = userDAO.findUserByQuery(userSearchBuilder).getResults();
        Assert.assertTrue("FIND USER BY ID", lista.size() == 0);

    }

    @Test
    public void R_testUserByIDPolizza() throws Exception {
        IUserSearchBuilder userSearchBuilder = IUserSearchBuilder.UserSearchBuilder.getUserSearchBuilder()
                .withNotId("01").withPolizzaAssicurativa("A1B2");
        List<User> lista = userDAO.findUserByQuery(userSearchBuilder).getResults();
        Assert.assertTrue("FIND USER BY ID", lista.size() == 0);

    }

    @Test
    public void S_testUserByIDCartaCircolazione() throws Exception {
        IUserSearchBuilder userSearchBuilder = IUserSearchBuilder.UserSearchBuilder.getUserSearchBuilder()
                .withNotId("01").withCartaCircolazione("12234");
        List<User> lista = userDAO.findUserByQuery(userSearchBuilder).getResults();
        Assert.assertTrue("FIND USER BY ID", lista.size() == 0);

    }

    @Test
    public void T_findByMultiMatch() throws Exception {
        IUserSearchBuilder userSearchBuilder = IUserSearchBuilder.UserSearchBuilder.getUserSearchBuilder()
                .withMultiMatch("Salvia 1111111");
        List<User> lista = this.userDAO.findUserByQuery(userSearchBuilder).getResults();
        Assert.assertTrue("FIND ALL USER", lista.size() == 1);
    }

    @Test
    public void T_findByMultiMatch2() throws Exception {

        IUserSearchBuilder userSearchBuilder = IUserSearchBuilder.UserSearchBuilder.getUserSearchBuilder()
                .withMultiMatch("Paolo Salvia");
        List<User> lista = this.userDAO.findUserByQuery(userSearchBuilder).getResults();
        Assert.assertTrue("FIND ALL USER", lista.size() == 1);
    }

    @Test
    public void U_findByMail() throws Exception {
        IUserSearchBuilder userSearchBuilder = IUserSearchBuilder.UserSearchBuilder.getUserSearchBuilder()
                .withMail("prova@gmail.com");
        List<User> lista = this.userDAO.findUserByQuery(userSearchBuilder).getResults();
        Assert.assertTrue("FIND ALL USER", lista.size() == 0);
    }

    @Test
    public void V_findByRespnsabileGruppo() throws Exception {
        IUserSearchBuilder userSearchBuilder = IUserSearchBuilder.UserSearchBuilder.getUserSearchBuilder()
                .withResponsabileGruppo(true).withAll(true);

        List<User> lista = this.userDAO.findUserByQuery(userSearchBuilder).getResults();
        Assert.assertTrue("FIND ALL USER", lista.size() == 1);
    }

    @Test
    public void V_findByNotRespnsabileGruppo() throws Exception {
        IUserSearchBuilder userSearchBuilder = IUserSearchBuilder.UserSearchBuilder.getUserSearchBuilder()
                .withResponsabileGruppo(false).withAll(true);
        List<User> lista = this.userDAO.findUserByQuery(userSearchBuilder).getResults();
        Assert.assertTrue("FIND ALL USER", lista.size() == 0);
    }

    @Test
    public void V_findByIdNotPresent() throws Exception {
        IUserSearchBuilder userSearchBuilder = IUserSearchBuilder.UserSearchBuilder.getUserSearchBuilder().withId("10");
        List<User> lista = this.userDAO.findUserByQuery(userSearchBuilder).getResults();
        Assert.assertTrue("FIND USER BY ID", lista.isEmpty());
    }

    @Test
    public void V_findById() throws Exception {
        IUserSearchBuilder userSearchBuilder = IUserSearchBuilder.UserSearchBuilder.getUserSearchBuilder().withId("01");
        List<User> lista = this.userDAO.findUserByQuery(userSearchBuilder).getResults();
        Assert.assertTrue("FIND USER BY ID", lista.size() == 1);
    }
    

	public void tearDown() throws Exception {
		this.userDocIndexCreator.deleteIndex();
	}
}
