package it.cnr.missioni.el.backup;

import java.io.File;
import java.util.List;

import javax.annotation.Resource;

import org.geosdi.geoplatform.experimental.el.configurator.GPIndexConfigurator;
import org.geosdi.geoplatform.experimental.el.dao.GPPageableElasticSearchDAO.Page;
import org.geosdi.geoplatform.experimental.el.index.GPIndexCreator;
import org.geosdi.geoplatform.logger.support.annotation.GeoPlatformLog;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.databind.SerializationFeature;

import it.cnr.missioni.el.backup.IBackupStore.DirettoreBackupStore;
import it.cnr.missioni.el.backup.IBackupStore.MassimaleBackupStore;
import it.cnr.missioni.el.backup.IBackupStore.MissioneBackupStore;
import it.cnr.missioni.el.backup.IBackupStore.MissioneIdBackupStore;
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
import it.cnr.missioni.el.dao.IMissioneIdDAO;
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
import it.cnr.missioni.model.configuration.MissioneId;
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
    
    @Resource(name = "missioneIdIndexCreator")
    private GPIndexCreator missioneIdDocIndexCreator;
    @Resource(name = "missioneIdDAO")
    private IMissioneIdDAO missioneIdDAO;

    
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
        Assert.assertNotNull(missioneIdDocIndexCreator);
        Assert.assertNotNull(missioneIdDAO);
    }
    
    @Ignore
    @Test
    public void a_backupNazioneTest() throws Exception {
    	int count = this.nazioneDAO.count().intValue();
    	List<Nazione> lista = this.nazioneDAO.find(new Page(0,count)).getResults();
    	IBackupStore<Nazione> conf = new NazioneBackupStore();
    	conf.setList(lista);
    	mapper.enable(SerializationFeature.INDENT_OUTPUT);
    	mapper.writeValue(new File("./backup/NazioneBackup.json"), conf);
    	Assert.assertTrue("############BACKUP_NAZIONE", conf.getList().size() == count);
    }
    
    @Ignore
    @Test
    public void a_backupMissioneIdTest() throws Exception {
    	int count = this.missioneIdDAO.count().intValue();
    	List<MissioneId> lista = this.missioneIdDAO.find(new Page(0,count)).getResults();
    	IBackupStore<MissioneId> conf = new MissioneIdBackupStore();
    	conf.setList(lista);
    	mapper.enable(SerializationFeature.INDENT_OUTPUT);
    	mapper.writeValue(new File("./backup/MissioneIDBackup.json"), conf);
    	Assert.assertTrue("############BACKUP_MISSIONE_ID", conf.getList().size() == count);
    }
    
    @Ignore
    @Test
    public void b_backupTipologiaSpesaTest() throws Exception {
    	int count = this.tipologiaSpesaDAO.count().intValue();
    	List<TipologiaSpesa> lista = this.tipologiaSpesaDAO.find(new Page(0,count)).getResults();
    	IBackupStore<TipologiaSpesa> conf = new TipologiaSpesaBackupStore();
    	conf.setList(lista);
    	mapper.enable(SerializationFeature.INDENT_OUTPUT);
    	mapper.writeValue(new File("./backup/TipologiaSpesaBackup.json"), conf);
    	Assert.assertTrue("############BACKUP_TIPOLOGIA_SPESA", conf.getList().size() == count);

    }
    
    @Ignore
    @Test
    public void c_backupMassimaleTest() throws Exception {
    	int count = this.massimaleDAO.count().intValue();
    	List<Massimale> lista = this.massimaleDAO.find(new Page(0,count)).getResults();
    	IBackupStore<Massimale> conf = new MassimaleBackupStore();
    	conf.setList(lista);
    	mapper.enable(SerializationFeature.INDENT_OUTPUT);
    	mapper.writeValue(new File("./backup/MassimaleBackup.json"), conf);
    	Assert.assertTrue("############BACKUP_MASSIMALE", conf.getList().size() == count);

    }
    
    @Ignore
    @Test
    public void d_backupDirettoreTest() throws Exception {
    	int count = this.direttoreDAO.count().intValue();
    	List<Direttore> lista = this.direttoreDAO.find(new Page(0,count)).getResults();
    	IBackupStore<Direttore> conf = new DirettoreBackupStore();
    	conf.setList(lista);
    	mapper.enable(SerializationFeature.INDENT_OUTPUT);
    	mapper.writeValue(new File("./backup/DirettoreBackup.json"), conf);
    	Assert.assertTrue("############BACKUP_DIRETTORE", conf.getList().size() == count);

    }
    
    @Ignore
    @Test
    public void e_backupUrlImageTest() throws Exception {
    	int count = this.urlImageDAO.count().intValue();
    	List<UrlImage> lista = this.urlImageDAO.find(new Page(0,count)).getResults();
    	IBackupStore<UrlImage> conf = new UrlImageBackupStore();
    	conf.setList(lista);
    	mapper.enable(SerializationFeature.INDENT_OUTPUT);
    	mapper.writeValue(new File("./backup/UrlImageBackup.json"), conf);
    	Assert.assertTrue("############BACKUP_URL_IMAGE", conf.getList().size() == count);

    }
    
    @Ignore
    @Test
    public void f_backupQualificaTest() throws Exception {
    	int count = this.qualificaUserDAO.count().intValue();
    	List<QualificaUser> lista = this.qualificaUserDAO.find(new Page(0,count)).getResults();
    	IBackupStore<QualificaUser> conf = new QualificaBackupStore();
    	conf.setList(lista);
    	mapper.enable(SerializationFeature.INDENT_OUTPUT);
    	mapper.writeValue(new File("./backup/QualificaUserBackup.json"), conf);
    	Assert.assertTrue("############BACKUP_QUALIFICA", conf.getList().size() == count);

    }
    
    @Ignore
    @Test
    public void g_backupRimborsoKmTest() throws Exception {
    	int count = this.rimborsoKmDAO.count().intValue();
    	List<RimborsoKm> lista = this.rimborsoKmDAO.find(new Page(0,count)).getResults();
    	IBackupStore<RimborsoKm> conf = new RimborsoKmBackupStore();
    	conf.setList(lista);
    	mapper.enable(SerializationFeature.INDENT_OUTPUT);
    	mapper.writeValue(new File("./backup/RimborsoKmBackup.json"), conf);
    	Assert.assertTrue("############BACKUP_RIMBORSO_KM", conf.getList().size() == count);

    }
    
    @Ignore
    @Test
    public void h_backupUserTest() throws Exception {
    	int count = this.userDAO.count().intValue();
    	List<User> lista = this.userDAO.find(new Page(0,count)).getResults();
    	IBackupStore<User> conf = new IBackupStore.UserBackupStore();
    	conf.setList(lista);
    	mapper.enable(SerializationFeature.INDENT_OUTPUT);
    	mapper.writeValue(new File("./backup/UserBackup.json"), conf);
    	Assert.assertTrue("############BACKUP_USER", conf.getList().size() == count);

    }
    
    @Ignore
    @Test
    public void i_backupMissioneTest() throws Exception {
    	int count = this.missioneDAO.count().intValue();
    	List<Missione> lista = this.missioneDAO.find(new Page(0,count)).getResults();
    	IBackupStore<Missione> conf = new IBackupStore.MissioneBackupStore();
    	conf.setList(lista);
    	mapper.enable(SerializationFeature.INDENT_OUTPUT);
    	mapper.writeValue(new File("./backup/MissioneBackup.json"), conf);
    	Assert.assertTrue("############BACKUP_MISSIONE", conf.getList().size() == count);

    }
    
    @Ignore
    @Test
    public void l_backupPrenotazioneTest() throws Exception {
    	int count = this.prenotazioneDAO.count().intValue();
    	List<Prenotazione> lista = this.prenotazioneDAO.find(new Page(0,count)).getResults();
    	IBackupStore<Prenotazione> conf = new IBackupStore.PrenotazioneBackupStore();
    	conf.setList(lista);
    	mapper.enable(SerializationFeature.INDENT_OUTPUT);
    	mapper.writeValue(new File("./backup/PrenotazioneBackup.json"), conf);
    	Assert.assertTrue("############BACKUP_PRENOTAZIONE", conf.getList().size() == count);

    }
    
    @Ignore
    @Test
    public void m_backupVeicoloCnrTest() throws Exception {
    	int count = this.veicoloCNRDAO.count().intValue();
    	List<VeicoloCNR> lista = this.veicoloCNRDAO.find(new Page(0,count)).getResults();
    	IBackupStore<VeicoloCNR> conf = new IBackupStore.VeicoloCnrBackupStore();
    	conf.setList(lista);
    	mapper.enable(SerializationFeature.INDENT_OUTPUT);
    	mapper.writeValue(new File("./backup/VeicoloCnrBackup.json"), conf);
    	Assert.assertTrue("############BACKUP_VEICOLO_CNR", conf.getList().size() == count);
    }
    
//    @Ignore
    @Test
    public void n_readNazioneBackupTest() throws Exception {
    	IBackupStore<Nazione> conf = mapper.readValue(new File("./backup/NazioneBackup.json"), NazioneBackupStore.class);
    	conf.getList().stream().forEach(f->{
    		try {
				nazioneDAO.persist(f);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		//logger.info("@@@@@@@@@@@@@@NAZIONE: {}\n",f);
    	});
    	//Assert.assertTrue("############READ_NAZIONE", conf.getList().size() == this.nazioneDAO.count().intValue());

    }
    
//    @Ignore
    @Test
    public void o_readMissioneIdBackupTest() throws Exception {
    	IBackupStore<MissioneId> conf = mapper.readValue(new File("./backup/MissioneIDBackup.json"), MissioneIdBackupStore.class);
    	conf.getList().stream().forEach(f->{
    		try {
				missioneIdDAO.persist(f);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//    		logger.info("@@@@@@@@@@@@@@TIPOLOGIA SPESA: {}\n",f);
    	});
//    	Assert.assertTrue("############READ_TIPOLOGIA_SPESA", conf.getList().size() == this.tipologiaSpesaDAO.count().intValue());

    }
    
//  @Ignore
  @Test
  public void o_readTipologiaSpesaBackupTest() throws Exception {
  	IBackupStore<TipologiaSpesa> conf = mapper.readValue(new File("./backup/TipologiaSpesaBackup.json"), TipologiaSpesaBackupStore.class);
  	conf.getList().stream().forEach(f->{
  		try {
				tipologiaSpesaDAO.persist(f);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//  		logger.info("@@@@@@@@@@@@@@TIPOLOGIA SPESA: {}\n",f);
  	});
//  	Assert.assertTrue("############READ_TIPOLOGIA_SPESA", conf.getList().size() == this.tipologiaSpesaDAO.count().intValue());

  }
    
//    @Ignore
    @Test
    public void p_readMassimaleBackupTest() throws Exception {
    	IBackupStore<Massimale> conf = mapper.readValue(new File("./backup/MassimaleBackup.json"), MassimaleBackupStore.class);
    	conf.getList().stream().forEach(f->{
    		try {
				massimaleDAO.persist(f);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//    		logger.info("@@@@@@@@@@@@@@MASSIMALE: {}\n",f);
    	});
//    	Assert.assertTrue("############READ_MASSIMALE", conf.getList().size() == this.massimaleDAO.count().intValue());

    }
    
//    @Ignore
    @Test
    public void q_readDirettoreBackupTest() throws Exception {
    	IBackupStore<Direttore> conf = mapper.readValue(new File("./backup/DirettoreBackup.json"), DirettoreBackupStore.class);
    	conf.getList().stream().forEach(f->{
    		try {
				direttoreDAO.persist(f);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//    		logger.info("@@@@@@@@@@@@@@DIRETTORE: {}\n",f);
    	});
//    	Assert.assertTrue("############READ_DIRETTORE", conf.getList().size() == this.direttoreDAO.count().intValue());

    }
    
//    @Ignore
    @Test
    public void r_readUrlImageBackupTest() throws Exception {
    	IBackupStore<UrlImage> conf = mapper.readValue(new File("./backup/UrlImageBackup.json"), UrlImageBackupStore.class);
    	conf.getList().stream().forEach(f->{
    		try {
				urlImageDAO.persist(f);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//    		logger.info("@@@@@@@@@@@@@@URL IMAGE: {}\n",f);
    	});
//    	Assert.assertTrue("############READ_URL_IMAGE", conf.getList().size() == this.urlImageDAO.count().intValue());
    }
    
//    @Ignore
    @Test
    public void s_readQualificaBackupTest() throws Exception {
    	IBackupStore<QualificaUser> conf = mapper.readValue(new File("./backup/QualificaUserBackup.json"), QualificaBackupStore.class);
    	conf.getList().stream().forEach(f->{
    		try {
				qualificaUserDAO.persist(f);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//    		logger.info("@@@@@@@@@@@@@@QUALIFICA: {}\n",f);
    	});
//    	Assert.assertTrue("############READ_QUALIFICA", conf.getList().size() == this.qualificaUserDAO.count().intValue());
    }
    
//    @Ignore
    @Test
    public void t_readRimborsoKmBackupTest() throws Exception {
    	IBackupStore<RimborsoKm> conf = mapper.readValue(new File("./backup/RimborsoKmBackup.json"), RimborsoKmBackupStore.class);
    	conf.getList().stream().forEach(f->{
    		try {
				rimborsoKmDAO.persist(f);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//    		logger.info("@@@@@@@@@@@@@@RIMBORSO KM: {}\n",f);
    	});
//    	Assert.assertTrue("############READ_RIMBORSO_KM", conf.getList().size() == this.rimborsoKmDAO.count().intValue());
    }
    
//    @Ignore
    @Test
    public void u_readUserBackupTest() throws Exception {
    	IBackupStore<User> conf = mapper.readValue(new File("./backup/UserBackup.json"), UserBackupStore.class);
    	conf.getList().stream().forEach(f->{
    		try {
				userDAO.persist(f);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//    		logger.info("@@@@@@@@@@@@@@USER: {}\n",f);
    	});
//    	Assert.assertTrue("############READ_USER", conf.getList().size() == this.userDAO.count().intValue());
    }
    
//    @Ignore
    @Test
    public void v_readMissioneBackupTest() throws Exception {
    	IBackupStore<Missione> conf = mapper.readValue(new File("./backup/MissioneBackup.json"), MissioneBackupStore.class);
    	conf.getList().stream().forEach(f->{
    		try {
    			if(f.isRimborsoSetted())
    				f.setRimborsoCompleted(true);
				missioneDAO.persist(f);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//    		logger.info("@@@@@@@@@@@@@@MISSIONE: {}\n",f);
    	});
//    	Assert.assertTrue("############READ_MISSIONE", conf.getList().size() == this.missioneDAO.count().intValue());
    }
    
//    @Ignore
    @Test
    public void x_readPrenotazioneBackupTest() throws Exception {
    	IBackupStore<Prenotazione> conf = mapper.readValue(new File("./backup/PrenotazioneBackup.json"), PrenotazioneBackupStore.class);
    	conf.getList().stream().forEach(f->{
    		try {
				prenotazioneDAO.persist(f);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//    		logger.info("@@@@@@@@@@@@@@PRENOTAZIONE: {}\n",f);
    	});
//    	Assert.assertTrue("############READ_PRENOTAZIONE", conf.getList().size() == this.prenotazioneDAO.count().intValue());
    }
    
//    @Ignore
    @Test
    public void y_readVeicoloCnrBackupTest() throws Exception {
    	IBackupStore<VeicoloCNR> conf = mapper.readValue(new File("./backup/VeicoloCnrBackup.json"), VeicoloCnrBackupStore.class);
    	conf.getList().stream().forEach(f->{
    		try {
				veicoloCNRDAO.persist(f);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//    		logger.info("@@@@@@@@@@@@@@VEICOLO CNR: {}\n",f);
    	});
//    	Assert.assertTrue("############READ_VEICOLO_CNR", conf.getList().size() == this.veicoloCNRDAO.count().intValue());
    }

}
