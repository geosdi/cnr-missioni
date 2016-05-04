package it.cnr.missioni.el.backup;

import static org.geosdi.geoplatform.support.jackson.property.GPJacksonSupportEnum.WRITE_DATES_AS_TIMESTAMPS_DISABLE;

import java.io.File;
import java.util.List;
import java.util.TimeZone;

import javax.annotation.Resource;

import org.geosdi.geoplatform.experimental.el.configurator.GPIndexConfigurator;
import org.geosdi.geoplatform.experimental.el.dao.GPPageableElasticSearchDAO.Page;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.joda.JodaModule;

import it.cnr.missioni.el.backup.IBackupStore.DirettoreBackupStore;
import it.cnr.missioni.el.backup.IBackupStore.MassimaleBackupStore;
import it.cnr.missioni.el.backup.IBackupStore.MissioneBackupStore;
import it.cnr.missioni.el.backup.IBackupStore.NazioneBackupStore;
import it.cnr.missioni.el.backup.IBackupStore.PrenotazioneBackupStore;
import it.cnr.missioni.el.backup.IBackupStore.QualificaBackupStore;
import it.cnr.missioni.el.backup.IBackupStore.RimborsoKmBackupStore;
import it.cnr.missioni.el.backup.IBackupStore.TipologiaSpesaBackupStore;
import it.cnr.missioni.el.backup.IBackupStore.UrlImageBackupStore;
import it.cnr.missioni.el.backup.IBackupStore.UserBackupStore;
import it.cnr.missioni.el.backup.IBackupStore.VeicoloCnrBackupStore;
import it.cnr.missioni.el.dao.IDirettoreDAO;
import it.cnr.missioni.el.dao.IMassimaleDAO;
import it.cnr.missioni.el.dao.IMissioneDAO;
import it.cnr.missioni.el.dao.INazioneDAO;
import it.cnr.missioni.el.dao.IPrenotazioneDAO;
import it.cnr.missioni.el.dao.IQualificaUserDAO;
import it.cnr.missioni.el.dao.IRimborsoKmDAO;
import it.cnr.missioni.el.dao.ITipologiaSpesaDAO;
import it.cnr.missioni.el.dao.IUrlImageDAO;
import it.cnr.missioni.el.dao.IUserDAO;
import it.cnr.missioni.el.dao.IVeicoloCNRDAO;
import it.cnr.missioni.model.configuration.Direttore;
import it.cnr.missioni.model.configuration.Massimale;
import it.cnr.missioni.model.configuration.Nazione;
import it.cnr.missioni.model.configuration.QualificaUser;
import it.cnr.missioni.model.configuration.RimborsoKm;
import it.cnr.missioni.model.configuration.TipologiaSpesa;
import it.cnr.missioni.model.configuration.UrlImage;
import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.model.prenotazione.Prenotazione;
import it.cnr.missioni.model.prenotazione.VeicoloCNR;
import it.cnr.missioni.model.user.User;

/**
 * @author Salvia Vito
 */
@FixMethodOrder(value = MethodSorters.NAME_ASCENDING)
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext-DAO-Test.xml"})
public class BackupDAOTest {

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
    
    @Resource(name = "direttoreIndexCreator")
    private GPIndexCreator direttoreDocIndexCreator;
    @Resource(name = "direttoreDAO")
    private IDirettoreDAO direttoreDAO;
    
    @Resource(name = "rimborsoKmIndexCreator")
    private GPIndexCreator rimborsoKmDocIndexCreator;
    @Resource(name = "rimborsoKmDAO")
    private IRimborsoKmDAO rimborsoKmDAO;
    
    @Resource(name = "urlImageIndexCreator")
    private GPIndexCreator urlImageDocIndexCreator;
    @Resource(name = "urlImageDAO")
    private IUrlImageDAO urlImageDAO;

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

    
    BackupMapper mapper = new BackupMapper();

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
    public void a_backupNazioneTest() throws Exception {
    	List<Nazione> lista = this.nazioneDAO.find(new Page(0,this.nazioneDAO.count().intValue())).getResults();
    	IBackupStore<Nazione> conf = new NazioneBackupStore();
    	conf.setList(lista);
    	mapper.enable(SerializationFeature.INDENT_OUTPUT);
    	mapper.writeValue(new File("./target/NazioneBackup.json"), conf);
    }
    
    @Test
    public void b_backupTipologiaSpesaTest() throws Exception {
    	List<TipologiaSpesa> lista = this.tipologiaSpesaDAO.find(new Page(0,this.tipologiaSpesaDAO.count().intValue())).getResults();
    	IBackupStore<TipologiaSpesa> conf = new TipologiaSpesaBackupStore();
    	conf.setList(lista);
    	mapper.enable(SerializationFeature.INDENT_OUTPUT);
    	mapper.writeValue(new File("./target/TipologiaSpesaBackup.json"), conf);
    }
    
    @Test
    public void c_backupMassimaleTest() throws Exception {
    	List<Massimale> lista = this.massimaleDAO.find(new Page(0,this.massimaleDAO.count().intValue())).getResults();
    	IBackupStore<Massimale> conf = new MassimaleBackupStore();
    	conf.setList(lista);
    	mapper.enable(SerializationFeature.INDENT_OUTPUT);
    	mapper.writeValue(new File("./target/MassimaleBackup.json"), conf);
    }
    
    @Test
    public void d_backupDirettoreTest() throws Exception {
    	List<Direttore> lista = this.direttoreDAO.find(new Page(0,this.direttoreDAO.count().intValue())).getResults();
    	IBackupStore<Direttore> conf = new DirettoreBackupStore();
    	conf.setList(lista);
    	mapper.enable(SerializationFeature.INDENT_OUTPUT);
    	mapper.writeValue(new File("./target/DirettoreBackup.json"), conf);
    }
    
    @Test
    public void e_backupUrlImageTest() throws Exception {
    	List<UrlImage> lista = this.urlImageDAO.find(new Page(0,this.urlImageDAO.count().intValue())).getResults();
    	IBackupStore<UrlImage> conf = new UrlImageBackupStore();
    	conf.setList(lista);
    	mapper.enable(SerializationFeature.INDENT_OUTPUT);
    	mapper.writeValue(new File("./target/UrlImageBackup.json"), conf);
    }
    
    @Test
    public void f_backupQualificaTest() throws Exception {
    	List<QualificaUser> lista = this.qualificaUserDAO.find(new Page(0,this.qualificaUserDAO.count().intValue())).getResults();
    	IBackupStore<QualificaUser> conf = new QualificaBackupStore();
    	conf.setList(lista);
    	mapper.enable(SerializationFeature.INDENT_OUTPUT);
    	mapper.writeValue(new File("./target/QualificaUserBackup.json"), conf);
    }
    
    @Test
    public void g_backupRimborsoKmTest() throws Exception {
    	List<RimborsoKm> lista = this.rimborsoKmDAO.find(new Page(0,this.rimborsoKmDAO.count().intValue())).getResults();
    	IBackupStore<RimborsoKm> conf = new RimborsoKmBackupStore();
    	conf.setList(lista);
    	mapper.enable(SerializationFeature.INDENT_OUTPUT);
    	mapper.writeValue(new File("./target/RimborsoKmBackup.json"), conf);
    }
    
    @Test
    public void h_backupUserTest() throws Exception {
    	List<User> lista = this.userDAO.find(new Page(0,this.userDAO.count().intValue())).getResults();
    	IBackupStore<User> conf = new IBackupStore.UserBackupStore();
    	conf.setList(lista);
    	mapper.enable(SerializationFeature.INDENT_OUTPUT);
    	mapper.writeValue(new File("./target/UserBackup.json"), conf);
    }
    
    @Test
    public void i_backupMissioneTest() throws Exception {
    	List<Missione> lista = this.missioneDAO.find(new Page(0,this.missioneDAO.count().intValue())).getResults();
    	IBackupStore<Missione> conf = new IBackupStore.MissioneBackupStore();
    	conf.setList(lista);
    	mapper.enable(SerializationFeature.INDENT_OUTPUT);
    	mapper.writeValue(new File("./target/MissioneBackup.json"), conf);
    }
    
    @Test
    public void l_backupPrenotazioneTest() throws Exception {
    	List<Prenotazione> lista = this.prenotazioneDAO.find(new Page(0,this.prenotazioneDAO.count().intValue())).getResults();
    	IBackupStore<Prenotazione> conf = new IBackupStore.PrenotazioneBackupStore();
    	conf.setList(lista);
    	mapper.enable(SerializationFeature.INDENT_OUTPUT);
    	mapper.writeValue(new File("./target/PrenotazioneBackup.json"), conf);
    }
    
    @Test
    public void m_backupVeicoloCnrTest() throws Exception {
    	List<VeicoloCNR> lista = this.veicoloCNRDAO.find(new Page(0,this.veicoloCNRDAO.count().intValue())).getResults();
    	IBackupStore<VeicoloCNR> conf = new IBackupStore.VeicoloCnrBackupStore();
    	conf.setList(lista);
    	mapper.enable(SerializationFeature.INDENT_OUTPUT);
    	mapper.writeValue(new File("./target/VeicoloCnrBackup.json"), conf);
    }
    
    @Test
    public void n_readNazioneBackupTest() throws Exception {
    	IBackupStore<Nazione> conf = mapper.readValue(new File("./target/NazioneBackup.json"), NazioneBackupStore.class);
    	conf.getList().stream().forEach(f->{
    		logger.info("@@@@@@@@@@@@@@NAZIONE: {}\n",f);
    	});
    }
    
    @Test
    public void o_readTipologiaSpesaBackupTest() throws Exception {
    	IBackupStore<TipologiaSpesa> conf = mapper.readValue(new File("./target/TipologiaSpesaBackup.json"), TipologiaSpesaBackupStore.class);
    	conf.getList().stream().forEach(f->{
    		logger.info("@@@@@@@@@@@@@@TIPOLOGIA SPESA: {}\n",f);
    	});
    }
    
    @Test
    public void p_readMassimaleBackupTest() throws Exception {
    	IBackupStore<Massimale> conf = mapper.readValue(new File("./target/MassimaleBackup.json"), MassimaleBackupStore.class);
    	conf.getList().stream().forEach(f->{
    		logger.info("@@@@@@@@@@@@@@MASSIMALE: {}\n",f);
    	});
    }
    
    @Test
    public void q_readDirettoreBackupTest() throws Exception {
    	IBackupStore<Direttore> conf = mapper.readValue(new File("./target/DirettoreBackup.json"), DirettoreBackupStore.class);
    	conf.getList().stream().forEach(f->{
    		logger.info("@@@@@@@@@@@@@@DIRETTORE: {}\n",f);
    	});
    }
    
    @Test
    public void r_readUrlImageBackupTest() throws Exception {
    	IBackupStore<UrlImage> conf = mapper.readValue(new File("./target/UrlImageBackup.json"), UrlImageBackupStore.class);
    	conf.getList().stream().forEach(f->{
    		logger.info("@@@@@@@@@@@@@@URL IMAGE: {}\n",f);
    	});
    }
    
    @Test
    public void s_readQualificaBackupTest() throws Exception {
    	IBackupStore<QualificaUser> conf = mapper.readValue(new File("./target/QualificaUserBackup.json"), QualificaBackupStore.class);
    	conf.getList().stream().forEach(f->{
    		logger.info("@@@@@@@@@@@@@@QUALIFICA: {}\n",f);
    	});
    }
    
    @Test
    public void t_readRimborsoKmBackupTest() throws Exception {
    	IBackupStore<RimborsoKm> conf = mapper.readValue(new File("./target/RimborsoKmBackup.json"), RimborsoKmBackupStore.class);
    	conf.getList().stream().forEach(f->{
    		logger.info("@@@@@@@@@@@@@@RIMBORSO KM: {}\n",f);
    	});
    }
    
    @Test
    public void u_readUserBackupTest() throws Exception {
    	IBackupStore<User> conf = mapper.readValue(new File("./target/UserBackup.json"), UserBackupStore.class);
    	conf.getList().stream().forEach(f->{
    		logger.info("@@@@@@@@@@@@@@USER: {}\n",f);
    	});
    }
    
    @Test
    public void v_readMissioneBackupTest() throws Exception {
    	IBackupStore<Missione> conf = mapper.readValue(new File("./target/MissioneBackup.json"), MissioneBackupStore.class);
    	conf.getList().stream().forEach(f->{
    		logger.info("@@@@@@@@@@@@@@MISSIONE: {}\n",f);
    	});
    }
    
    @Test
    public void x_readPrenotazioneBackupTest() throws Exception {
    	IBackupStore<Prenotazione> conf = mapper.readValue(new File("./target/PrenotazioneBackup.json"), PrenotazioneBackupStore.class);
    	conf.getList().stream().forEach(f->{
    		logger.info("@@@@@@@@@@@@@@PRENOTAZIONE: {}\n",f);
    	});
    }
    
    @Test
    public void y_readVeicoloCnrBackupTest() throws Exception {
    	IBackupStore<VeicoloCNR> conf = mapper.readValue(new File("./target/VeicoloCnrBackup.json"), VeicoloCnrBackupStore.class);
    	conf.getList().stream().forEach(f->{
    		logger.info("@@@@@@@@@@@@@@VEICOLO CNR: {}\n",f);
    	});
    }

}
