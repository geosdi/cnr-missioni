package it.cnr.missioni.el.dao;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.geosdi.geoplatform.experimental.el.configurator.GPIndexConfigurator;
import org.geosdi.geoplatform.experimental.el.dao.GPPageableElasticSearchDAO.Page;
import org.geosdi.geoplatform.experimental.el.index.GPIndexCreator;
import org.geosdi.geoplatform.logger.support.annotation.GeoPlatformLog;
import org.joda.time.DateTime;
import org.joda.time.chrono.ISOChronology;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import it.cnr.missioni.el.mapper.NazioneMapper;
import it.cnr.missioni.model.configuration.Massimale;
import it.cnr.missioni.model.configuration.Nazione;
import it.cnr.missioni.model.configuration.Nazione.AreaGeograficaEnum;
import it.cnr.missioni.model.configuration.QualificaUser;
import it.cnr.missioni.model.configuration.TipologiaSpesa;
import it.cnr.missioni.model.configuration.TipologiaSpesa.VoceSpesaEnum;
import it.cnr.missioni.model.missione.TrattamentoMissioneEsteraEnum;
import it.cnr.missioni.model.user.Anagrafica;
import it.cnr.missioni.model.user.Anagrafica.Genere;
import it.cnr.missioni.model.user.Credenziali;
import it.cnr.missioni.model.user.DatiCNR;
import it.cnr.missioni.model.user.DatiCNR.LivelloUserEnum;
import it.cnr.missioni.model.user.Patente;
import it.cnr.missioni.model.user.Residenza;
import it.cnr.missioni.model.user.RuoloUserEnum;
import it.cnr.missioni.model.user.User;
import it.cnr.missioni.model.user.Veicolo;
import it.cnr.missioni.support.builder.generator.IMd5PasswordGenerator;

/**
 * @author Salvia Vito
 */
@FixMethodOrder(value = MethodSorters.NAME_ASCENDING)
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext-DAO-Test.xml"})
public class RestoreDAOTest {

    @GeoPlatformLog
    static Logger logger;
    @Resource(name = "missioniIndexConfigurator")
    private GPIndexConfigurator missioniIndexConfigurator;

    @Resource(name = "qualificaUserIndexCreator")
    private GPIndexCreator qualificaUserDocIndexCreator;
    @Resource(name = "qualificaUserDAO")
    private IQualificaUserDAO qualificaUserDAO;

    @Resource(name = "prenotazioneIndexCreator")
    private GPIndexCreator prenotazioneDocIndexCreator;
    @Resource(name = "prenotazioneDAO")
    private IPrenotazioneDAO prenotazioneDAO;

    @Resource(name = "veicoloCNRIndexCreator")
    private GPIndexCreator veicoloCNRDocIndexCreator;
    @Resource(name = "veicoloCNRDAO")
    private IVeicoloCNRDAO veicoloCNRDAO;

    @Resource(name = "missioneIndexCreator")
    private GPIndexCreator missioneDocIndexCreator;
    @Resource(name = "missioneDAO")
    private IMissioneDAO missioneDAO;

    @Resource(name = "userIndexCreator")
    private GPIndexCreator userDocIndexCreator;
    @Resource(name = "userDAO")
    private IUserDAO userDAO;

    @Resource(name = "nazioneIndexCreator")
    private GPIndexCreator nazioneDocIndexCreator;
    @Resource(name = "nazioneDAO")
    private INazioneDAO nazioneDAO;

    @Resource(name = "tipologiaSpesaIndexCreator")
    private GPIndexCreator tipoligiaSpesaDocIndexCreator;
    @Resource(name = "tipologiaSpesaDAO")
    private ITipologiaSpesaDAO tipologiaSpesaDAO;

    @Resource(name = "massimaleIndexCreator")
    private GPIndexCreator massimaleDocIndexCreator;
    @Resource(name = "massimaleDAO")
    private IMassimaleDAO massimaleDAO;
    
    private final NazioneMapper nazioneMapper = new NazioneMapper();



    @Before
    public void setUp() {
        Assert.assertNotNull(missioniIndexConfigurator);
        Assert.assertNotNull(qualificaUserDocIndexCreator);
        Assert.assertNotNull(qualificaUserDAO);
        Assert.assertNotNull(userDocIndexCreator);
        Assert.assertNotNull(qualificaUserDAO);
        Assert.assertNotNull(nazioneDocIndexCreator);
        Assert.assertNotNull(nazioneDAO);
        Assert.assertNotNull(tipoligiaSpesaDocIndexCreator);
        Assert.assertNotNull(tipologiaSpesaDAO);
        Assert.assertNotNull(veicoloCNRDocIndexCreator);
        Assert.assertNotNull(veicoloCNRDAO);
        Assert.assertNotNull(prenotazioneDocIndexCreator);
        Assert.assertNotNull(prenotazioneDAO);
        Assert.assertNotNull(missioneDocIndexCreator);
        Assert.assertNotNull(missioneDAO);
        Assert.assertNotNull(massimaleDocIndexCreator);
        Assert.assertNotNull(massimaleDAO);
    }
    
    @Test
    public void restoreNazioneTest() throws Exception {
    	List<Nazione> lista = this.nazioneDAO.find(new Page(0,this.nazioneDAO.count().intValue())).getResults();
    	this.nazioneMapper.write(new File("./target/NazioneConf.json"), lista.get(0));
    }

}
