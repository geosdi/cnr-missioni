package it.cnr.missioni.el.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.geosdi.geoplatform.experimental.el.configurator.GPIndexConfigurator;
import org.geosdi.geoplatform.experimental.el.dao.PageResult;
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

import it.cnr.missioni.el.model.search.builder.IMassimaleSearchBuilder;
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
public class InitDAOTest {

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
    
   static String idQualificaUser;
    
    @BeforeClass
    public static void oneTimeSetUp() {
    	idQualificaUser = UUID.randomUUID().toString();
    }

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
    
//    @Test
//    public void getMassimali() throws Exception{
//    	IMassimaleSearchBuilder m = IMassimaleSearchBuilder.MassimaleSearchBuilder.getMassimaleSearchBuilder().withAll(true);
//        PageResult<Massimale> page = massimaleDAO.findMassimaleByQuery(m);
//    	System.out.println("\nSize:"+page.getResults().size());
//    	page.getResults().stream().forEach(f->{
//    		try {
//				massimaleDAO.delete(f.getId());
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//    	});
//    }
    

    @Test
    public void insertUserTest() throws Exception {
        userDAO.persist(creaUser());
    }

    @Test
    public void insertQualificaTest() throws Exception {
        qualificaUserDAO.persist(creaQualifiche());
    }

    @Test
    public void insertNazioniTest() throws Exception {
        nazioneDAO.persist(creaNazioni());
    }

    @Test
    public void insertTipologiaSpesaTest() throws Exception {
        tipologiaSpesaDAO.persist(creaTipologiaSpesa());
    }

    @Test
    public void insertMassimaleTest() throws Exception {
        massimaleDAO.persist(creaMassimali());
    }

    private List<TipologiaSpesa> creaTipologiaSpesa() {
        List<TipologiaSpesa> lista = new ArrayList<TipologiaSpesa>();

        TipologiaSpesa t = new TipologiaSpesa();
        t.setId(UUID.randomUUID().toString());
        t.setEstera(true);
        t.setItalia(false);
        t.setTipoTrattamento(TrattamentoMissioneEsteraEnum.TRATTAMENTO_ALTERNATIVO);
        t.setValue("Aereo");
        t.setVoceSpesa(VoceSpesaEnum.ALTRO);
        t.setNoCheckData(true);
        lista.add(t);

        t = new TipologiaSpesa();
        t.setId(UUID.randomUUID().toString());
        t.setEstera(true);
        t.setItalia(false);
        t.setTipoTrattamento(TrattamentoMissioneEsteraEnum.TRATTAMENTO_ALTERNATIVO);
        t.setValue("Altro");
        t.setVoceSpesa(VoceSpesaEnum.ALTRO);
        lista.add(t);

        t = new TipologiaSpesa();
        t.setId(UUID.randomUUID().toString());
        t.setEstera(true);
        t.setItalia(false);
        t.setTipoTrattamento(TrattamentoMissioneEsteraEnum.TRATTAMENTO_ALTERNATIVO);
        t.setValue("Altro Albergo");
        t.setVoceSpesa(VoceSpesaEnum.ALTRO);
        lista.add(t);

        t = new TipologiaSpesa();
        t.setId(UUID.randomUUID().toString());
        t.setEstera(true);
        t.setItalia(false);
        t.setTipoTrattamento(TrattamentoMissioneEsteraEnum.TRATTAMENTO_ALTERNATIVO);
        t.setValue("Altro mezzo di trasporto");
        t.setVoceSpesa(VoceSpesaEnum.ALTRO);
        lista.add(t);

        t = new TipologiaSpesa();
        t.setId(UUID.randomUUID().toString());
        t.setEstera(true);
        t.setItalia(false);
        t.setTipoTrattamento(TrattamentoMissioneEsteraEnum.TRATTAMENTO_ALTERNATIVO);
        t.setValue("Altro pasto");
        t.setVoceSpesa(VoceSpesaEnum.ALTRO);
        lista.add(t);

        t = new TipologiaSpesa();
        t.setId(UUID.randomUUID().toString());
        t.setEstera(true);
        t.setItalia(false);
        t.setTipoTrattamento(TrattamentoMissioneEsteraEnum.TRATTAMENTO_ALTERNATIVO);
        t.setValue("Nave");
        t.setVoceSpesa(VoceSpesaEnum.ALTRO);
        lista.add(t);

        t = new TipologiaSpesa();
        t.setId(UUID.randomUUID().toString());
        t.setEstera(true);
        t.setItalia(false);
        t.setTipoTrattamento(TrattamentoMissioneEsteraEnum.TRATTAMENTO_ALTERNATIVO);
        t.setValue("Spese visto");
        t.setVoceSpesa(VoceSpesaEnum.ALTRO);
        lista.add(t);

        t = new TipologiaSpesa();
        t.setId(UUID.randomUUID().toString());
        t.setEstera(true);
        t.setItalia(false);
        t.setTipoTrattamento(TrattamentoMissioneEsteraEnum.TRATTAMENTO_ALTERNATIVO);
        t.setValue("Taxi");
        t.setVoceSpesa(VoceSpesaEnum.ALTRO);
        lista.add(t);

        t = new TipologiaSpesa();
        t.setId(UUID.randomUUID().toString());
        t.setEstera(true);
        t.setItalia(false);
        t.setTipoTrattamento(TrattamentoMissioneEsteraEnum.TRATTAMENTO_ALTERNATIVO);
        t.setValue("Treno");
        t.setVoceSpesa(VoceSpesaEnum.ALTRO);
        lista.add(t);

        t = new TipologiaSpesa();
        t.setId(UUID.randomUUID().toString());
        t.setEstera(true);
        t.setItalia(true);
        t.setTipoTrattamento(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
        t.setValue("Aereo");
        t.setVoceSpesa(VoceSpesaEnum.ALTRO);
        t.setNoCheckData(true);
        lista.add(t);

        t = new TipologiaSpesa();
        t.setId(UUID.randomUUID().toString());
        t.setEstera(true);
        t.setItalia(true);
        t.setTipoTrattamento(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
        t.setValue("Albergo");
        t.setVoceSpesa(VoceSpesaEnum.ALTRO);
        lista.add(t);

        t = new TipologiaSpesa();
        t.setId(UUID.randomUUID().toString());
        t.setEstera(true);
        t.setItalia(true);
        t.setTipoTrattamento(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
        t.setValue("Altre spese viaggio");
        t.setVoceSpesa(VoceSpesaEnum.ALTRO);
        lista.add(t);

        t = new TipologiaSpesa();
        t.setId(UUID.randomUUID().toString());
        t.setEstera(true);
        t.setItalia(true);
        t.setTipoTrattamento(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
        t.setValue("Altri costi congressuali");
        t.setVoceSpesa(VoceSpesaEnum.ALTRO);
        lista.add(t);

        t = new TipologiaSpesa();
        t.setId(UUID.randomUUID().toString());
        t.setEstera(true);
        t.setItalia(true);
        t.setTipoTrattamento(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
        t.setValue("Altro");
        t.setVoceSpesa(VoceSpesaEnum.ALTRO);
        lista.add(t);

        t = new TipologiaSpesa();
        t.setId(UUID.randomUUID().toString());
        t.setEstera(true);
        t.setItalia(true);
        t.setTipoTrattamento(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
        t.setValue("Altro albergo");
        t.setVoceSpesa(VoceSpesaEnum.ALTRO);
        lista.add(t);

        t = new TipologiaSpesa();
        t.setId(UUID.randomUUID().toString());
        t.setEstera(true);
        t.setItalia(true);
        t.setTipoTrattamento(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
        t.setValue("Altro mezzo trasporto");
        t.setVoceSpesa(VoceSpesaEnum.ALTRO);
        lista.add(t);

        t = new TipologiaSpesa();
        t.setId(UUID.randomUUID().toString());
        t.setEstera(true);
        t.setItalia(true);
        t.setTipoTrattamento(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
        t.setValue("Altro pasto");
        t.setVoceSpesa(VoceSpesaEnum.ALTRO);
        lista.add(t);

        t = new TipologiaSpesa();
        t.setId(UUID.randomUUID().toString());
        t.setEstera(true);
        t.setItalia(true);
        t.setTipoTrattamento(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
        t.setValue("Autobus\\corriera");
        t.setVoceSpesa(VoceSpesaEnum.ALTRO);
        lista.add(t);

        t = new TipologiaSpesa();
        t.setId(UUID.randomUUID().toString());
        t.setEstera(true);
        t.setItalia(true);
        t.setTipoTrattamento(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
        t.setValue("Cuccetta");
        t.setVoceSpesa(VoceSpesaEnum.ALTRO);
        lista.add(t);

        t = new TipologiaSpesa();
        t.setId(UUID.randomUUID().toString());
        t.setEstera(true);
        t.setItalia(true);
        t.setTipoTrattamento(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
        t.setValue("Indennità kilometrica");
        t.setVoceSpesa(VoceSpesaEnum.ALTRO);
        lista.add(t);

        t = new TipologiaSpesa();
        t.setId(UUID.randomUUID().toString());
        t.setEstera(true);
        t.setItalia(true);
        t.setTipoTrattamento(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
        t.setValue("Iscrizione congresso");
        t.setVoceSpesa(VoceSpesaEnum.ALTRO);
        t.setNoCheckData(true);
        lista.add(t);

        t = new TipologiaSpesa();
        t.setId(UUID.randomUUID().toString());
        t.setEstera(true);
        t.setItalia(true);
        t.setTipoTrattamento(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
        t.setValue("Nave");
        t.setVoceSpesa(VoceSpesaEnum.ALTRO);
        lista.add(t);

        t = new TipologiaSpesa();
        t.setId(UUID.randomUUID().toString());
        t.setEstera(true);
        t.setItalia(true);
        t.setTipoTrattamento(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
        t.setValue("Noleggio auto");
        t.setVoceSpesa(VoceSpesaEnum.ALTRO);
        lista.add(t);

        t = new TipologiaSpesa();
        t.setId(UUID.randomUUID().toString());
        t.setEstera(true);
        t.setItalia(true);
        t.setTipoTrattamento(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
        t.setValue("Parcheggio");
        t.setVoceSpesa(VoceSpesaEnum.ALTRO);
        lista.add(t);

        t = new TipologiaSpesa();
        t.setId(UUID.randomUUID().toString());
        t.setEstera(true);
        t.setItalia(true);
        t.setTipoTrattamento(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
        t.setValue("Pasto");
        t.setVoceSpesa(VoceSpesaEnum.PASTO);
        lista.add(t);

        t = new TipologiaSpesa();
        t.setId(UUID.randomUUID().toString());
        t.setEstera(true);
        t.setItalia(true);
        t.setTipoTrattamento(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
        t.setValue("Pedaggio autostrada");
        t.setVoceSpesa(VoceSpesaEnum.ALTRO);
        lista.add(t);

        t = new TipologiaSpesa();
        t.setId(UUID.randomUUID().toString());
        t.setEstera(true);
        t.setItalia(true);
        t.setTipoTrattamento(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
        t.setValue("Spese bagagli");
        t.setVoceSpesa(VoceSpesaEnum.ALTRO);
        lista.add(t);

        t = new TipologiaSpesa();
        t.setId(UUID.randomUUID().toString());
        t.setEstera(true);
        t.setItalia(true);
        t.setTipoTrattamento(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
        t.setValue("Spese visto");
        t.setVoceSpesa(VoceSpesaEnum.ALTRO);
        lista.add(t);

        t = new TipologiaSpesa();
        t.setId(UUID.randomUUID().toString());
        t.setEstera(true);
        t.setItalia(true);
        t.setTipoTrattamento(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
        t.setValue("Taxi");
        t.setVoceSpesa(VoceSpesaEnum.ALTRO);
        lista.add(t);

        return lista;
    }

    private List<Massimale> creaMassimali() {
        List<Massimale> lista = new ArrayList<Massimale>();

        // TAM ESTERO
        Massimale m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.A);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.I);
        m.setTipo(TrattamentoMissioneEsteraEnum.TRATTAMENTO_ALTERNATIVO);
        m.setValue(120.0);
        lista.add(m);

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.A);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.II);
        m.setTipo(TrattamentoMissioneEsteraEnum.TRATTAMENTO_ALTERNATIVO);
        m.setValue(120.0);
        lista.add(m);

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.A);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.III);
        m.setTipo(TrattamentoMissioneEsteraEnum.TRATTAMENTO_ALTERNATIVO);
        m.setValue(120.0);
        lista.add(m);

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.B);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.I);
        m.setTipo(TrattamentoMissioneEsteraEnum.TRATTAMENTO_ALTERNATIVO);
        m.setValue(120.0);
        lista.add(m);

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.B);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.II);
        m.setTipo(TrattamentoMissioneEsteraEnum.TRATTAMENTO_ALTERNATIVO);
        m.setValue(120.0);
        lista.add(m);

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.B);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.III);
        m.setTipo(TrattamentoMissioneEsteraEnum.TRATTAMENTO_ALTERNATIVO);
        m.setValue(120.0);
        lista.add(m);

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.C);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.I);
        m.setTipo(TrattamentoMissioneEsteraEnum.TRATTAMENTO_ALTERNATIVO);
        m.setValue(120.0);
        lista.add(m);

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.C);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.II);
        m.setTipo(TrattamentoMissioneEsteraEnum.TRATTAMENTO_ALTERNATIVO);
        m.setValue(120.0);
        lista.add(m);

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.C);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.III);
        m.setTipo(TrattamentoMissioneEsteraEnum.TRATTAMENTO_ALTERNATIVO);
        m.setValue(120.0);
        lista.add(m);

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.D);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.I);
        m.setTipo(TrattamentoMissioneEsteraEnum.TRATTAMENTO_ALTERNATIVO);
        m.setValue(125.0);
        lista.add(m);

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.D);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.II);
        m.setTipo(TrattamentoMissioneEsteraEnum.TRATTAMENTO_ALTERNATIVO);
        m.setValue(125.0);
        lista.add(m);

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.D);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.III);
        m.setTipo(TrattamentoMissioneEsteraEnum.TRATTAMENTO_ALTERNATIVO);
        m.setValue(125.0);
        lista.add(m);

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.E);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.I);
        m.setTipo(TrattamentoMissioneEsteraEnum.TRATTAMENTO_ALTERNATIVO);
        m.setValue(130.0);
        lista.add(m);

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.E);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.II);
        m.setTipo(TrattamentoMissioneEsteraEnum.TRATTAMENTO_ALTERNATIVO);
        m.setValue(130.0);
        lista.add(m);

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.E);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.III);
        m.setTipo(TrattamentoMissioneEsteraEnum.TRATTAMENTO_ALTERNATIVO);
        m.setValue(130.0);
        lista.add(m);

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.F);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.I);
        m.setTipo(TrattamentoMissioneEsteraEnum.TRATTAMENTO_ALTERNATIVO);
        m.setValue(140.0);
        lista.add(m);

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.F);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.II);
        m.setTipo(TrattamentoMissioneEsteraEnum.TRATTAMENTO_ALTERNATIVO);
        m.setValue(140.0);
        lista.add(m);

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.F);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.III);
        m.setTipo(TrattamentoMissioneEsteraEnum.TRATTAMENTO_ALTERNATIVO);
        m.setValue(140.0);
        lista.add(m);

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.G);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.I);
        m.setTipo(TrattamentoMissioneEsteraEnum.TRATTAMENTO_ALTERNATIVO);
        m.setValue(155.0);
        lista.add(m);

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.G);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.II);
        m.setTipo(TrattamentoMissioneEsteraEnum.TRATTAMENTO_ALTERNATIVO);
        m.setValue(155.0);
        lista.add(m);

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.G);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.III);
        m.setTipo(TrattamentoMissioneEsteraEnum.TRATTAMENTO_ALTERNATIVO);
        m.setValue(155.0);
        lista.add(m);

        //IV-VIII

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.A);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.IV);
        m.setTipo(TrattamentoMissioneEsteraEnum.TRATTAMENTO_ALTERNATIVO);
        m.setValue(120.0);
        lista.add(m);

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.A);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.V);
        m.setTipo(TrattamentoMissioneEsteraEnum.TRATTAMENTO_ALTERNATIVO);
        m.setValue(120.0);
        lista.add(m);

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.A);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.VI);
        m.setTipo(TrattamentoMissioneEsteraEnum.TRATTAMENTO_ALTERNATIVO);
        m.setValue(120.0);
        lista.add(m);


        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.A);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.VII);
        m.setTipo(TrattamentoMissioneEsteraEnum.TRATTAMENTO_ALTERNATIVO);
        m.setValue(120.0);
        lista.add(m);

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.A);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.VIII);
        m.setTipo(TrattamentoMissioneEsteraEnum.TRATTAMENTO_ALTERNATIVO);
        m.setValue(120.0);
        lista.add(m);

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.B);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.IV);
        m.setTipo(TrattamentoMissioneEsteraEnum.TRATTAMENTO_ALTERNATIVO);
        m.setValue(120.0);
        lista.add(m);

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.B);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.V);
        m.setTipo(TrattamentoMissioneEsteraEnum.TRATTAMENTO_ALTERNATIVO);
        m.setValue(120.0);
        lista.add(m);

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.B);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.VI);
        m.setTipo(TrattamentoMissioneEsteraEnum.TRATTAMENTO_ALTERNATIVO);
        m.setValue(120.0);
        lista.add(m);


        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.B);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.VII);
        m.setTipo(TrattamentoMissioneEsteraEnum.TRATTAMENTO_ALTERNATIVO);
        m.setValue(120.0);
        lista.add(m);

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.B);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.VIII);
        m.setTipo(TrattamentoMissioneEsteraEnum.TRATTAMENTO_ALTERNATIVO);
        m.setValue(120.0);
        lista.add(m);

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.C);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.IV);
        m.setTipo(TrattamentoMissioneEsteraEnum.TRATTAMENTO_ALTERNATIVO);
        m.setValue(120.0);
        lista.add(m);

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.C);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.V);
        m.setTipo(TrattamentoMissioneEsteraEnum.TRATTAMENTO_ALTERNATIVO);
        m.setValue(120.0);
        lista.add(m);

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.C);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.VI);
        m.setTipo(TrattamentoMissioneEsteraEnum.TRATTAMENTO_ALTERNATIVO);
        m.setValue(120.0);
        lista.add(m);


        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.C);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.VII);
        m.setTipo(TrattamentoMissioneEsteraEnum.TRATTAMENTO_ALTERNATIVO);
        m.setValue(120.0);
        lista.add(m);

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.C);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.VIII);
        m.setTipo(TrattamentoMissioneEsteraEnum.TRATTAMENTO_ALTERNATIVO);
        m.setValue(120.0);
        lista.add(m);

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.D);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.IV);
        m.setTipo(TrattamentoMissioneEsteraEnum.TRATTAMENTO_ALTERNATIVO);
        m.setValue(125.0);
        lista.add(m);

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.D);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.V);
        m.setTipo(TrattamentoMissioneEsteraEnum.TRATTAMENTO_ALTERNATIVO);
        m.setValue(125.0);
        lista.add(m);

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.D);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.VI);
        m.setTipo(TrattamentoMissioneEsteraEnum.TRATTAMENTO_ALTERNATIVO);
        m.setValue(125.0);
        lista.add(m);

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.D);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.VII);
        m.setTipo(TrattamentoMissioneEsteraEnum.TRATTAMENTO_ALTERNATIVO);
        m.setValue(125.0);
        lista.add(m);

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.D);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.VIII);
        m.setTipo(TrattamentoMissioneEsteraEnum.TRATTAMENTO_ALTERNATIVO);
        m.setValue(125.0);
        lista.add(m);

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.E);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.IV);
        m.setTipo(TrattamentoMissioneEsteraEnum.TRATTAMENTO_ALTERNATIVO);
        m.setValue(130.0);
        lista.add(m);

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.E);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.V);
        m.setTipo(TrattamentoMissioneEsteraEnum.TRATTAMENTO_ALTERNATIVO);
        m.setValue(130.0);
        lista.add(m);

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.E);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.VI);
        m.setTipo(TrattamentoMissioneEsteraEnum.TRATTAMENTO_ALTERNATIVO);
        m.setValue(130.0);
        lista.add(m);

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.E);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.VII);
        m.setTipo(TrattamentoMissioneEsteraEnum.TRATTAMENTO_ALTERNATIVO);
        m.setValue(130.0);
        lista.add(m);

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.E);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.VIII);
        m.setTipo(TrattamentoMissioneEsteraEnum.TRATTAMENTO_ALTERNATIVO);
        m.setValue(130.0);
        lista.add(m);

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.F);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.IV);
        m.setTipo(TrattamentoMissioneEsteraEnum.TRATTAMENTO_ALTERNATIVO);
        m.setValue(140.0);
        lista.add(m);

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.F);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.V);
        m.setTipo(TrattamentoMissioneEsteraEnum.TRATTAMENTO_ALTERNATIVO);
        m.setValue(140.0);
        lista.add(m);

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.F);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.VI);
        m.setTipo(TrattamentoMissioneEsteraEnum.TRATTAMENTO_ALTERNATIVO);
        m.setValue(140.0);
        lista.add(m);

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.F);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.VII);
        m.setTipo(TrattamentoMissioneEsteraEnum.TRATTAMENTO_ALTERNATIVO);
        m.setValue(140.0);
        lista.add(m);

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.F);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.VIII);
        m.setTipo(TrattamentoMissioneEsteraEnum.TRATTAMENTO_ALTERNATIVO);
        m.setValue(140.0);
        lista.add(m);

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.G);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.IV);
        m.setTipo(TrattamentoMissioneEsteraEnum.TRATTAMENTO_ALTERNATIVO);
        m.setValue(155.0);
        lista.add(m);

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.G);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.V);
        m.setTipo(TrattamentoMissioneEsteraEnum.TRATTAMENTO_ALTERNATIVO);
        m.setValue(155.0);
        lista.add(m);

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.G);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.VI);
        m.setTipo(TrattamentoMissioneEsteraEnum.TRATTAMENTO_ALTERNATIVO);
        m.setValue(155.0);
        lista.add(m);

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.G);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.VII);
        m.setTipo(TrattamentoMissioneEsteraEnum.TRATTAMENTO_ALTERNATIVO);
        m.setValue(155.0);
        lista.add(m);

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.G);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.VIII);
        m.setTipo(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
        m.setValue(155.0);
        lista.add(m);

        //RIMBORSO DOCUMENTATO ESTERO
        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.A);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.IV);
        m.setTipo(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
        m.setValue(40.0);
        lista.add(m);

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.A);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.V);
        m.setTipo(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
        m.setValue(40.0);
        lista.add(m);

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.A);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.VI);
        m.setTipo(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
        m.setValue(40.0);
        lista.add(m);

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.A);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.VII);
        m.setTipo(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
        m.setValue(40.0);
        lista.add(m);

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.A);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.VIII);
        m.setTipo(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
        m.setValue(40.0);
        lista.add(m);

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.B);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.IV);
        m.setTipo(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
        m.setValue(40.0);
        lista.add(m);

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.B);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.V);
        m.setTipo(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
        m.setValue(40.0);
        lista.add(m);

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.B);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.VI);
        m.setTipo(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
        m.setValue(40.0);
        lista.add(m);

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.B);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.VII);
        m.setTipo(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
        m.setValue(40.0);
        lista.add(m);

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.B);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.VIII);
        m.setTipo(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
        m.setValue(40.0);
        lista.add(m);

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.C);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.IV);
        m.setTipo(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
        m.setValue(45.0);
        lista.add(m);

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.C);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.V);
        m.setTipo(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
        m.setValue(45.0);
        lista.add(m);

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.C);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.VI);
        m.setTipo(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
        m.setValue(45.0);
        lista.add(m);

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.C);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.VII);
        m.setTipo(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
        m.setValue(45.0);
        lista.add(m);

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.C);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.VIII);
        m.setTipo(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
        m.setValue(45.0);
        lista.add(m);

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.D);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.IV);
        m.setTipo(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
        m.setValue(60.0);
        lista.add(m);

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.D);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.V);
        m.setTipo(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
        m.setValue(60.0);
        lista.add(m);

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.D);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.VI);
        m.setTipo(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
        m.setValue(60.0);
        lista.add(m);

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.D);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.VII);
        m.setTipo(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
        m.setValue(60.0);
        lista.add(m);

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.D);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.VIII);
        m.setTipo(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
        m.setValue(60.0);
        lista.add(m);

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.E);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.IV);
        m.setTipo(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
        m.setValue(65.0);
        lista.add(m);

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.E);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.V);
        m.setTipo(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
        m.setValue(65.0);
        lista.add(m);

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.E);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.VI);
        m.setTipo(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
        m.setValue(65.0);
        lista.add(m);

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.E);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.VII);
        m.setTipo(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
        m.setValue(65.0);
        lista.add(m);

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.E);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.VIII);
        m.setTipo(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
        m.setValue(65.0);
        lista.add(m);

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.F);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.IV);
        m.setTipo(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
        m.setValue(70.0);
        lista.add(m);

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.F);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.V);
        m.setTipo(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
        m.setValue(70.0);
        lista.add(m);

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.F);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.VI);
        m.setTipo(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
        m.setValue(70.0);
        lista.add(m);

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.F);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.VII);
        m.setTipo(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
        m.setValue(70.0);
        lista.add(m);

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.F);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.VIII);
        m.setTipo(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
        m.setValue(70.0);
        lista.add(m);

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.G);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.IV);
        m.setTipo(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
        m.setValue(75.0);
        lista.add(m);

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.G);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.V);
        m.setTipo(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
        m.setValue(75.0);
        lista.add(m);

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.G);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.VI);
        m.setTipo(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
        m.setValue(75.0);
        lista.add(m);

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.G);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.VII);
        m.setTipo(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
        m.setValue(75.0);
        lista.add(m);

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.G);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.VIII);
        m.setTipo(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
        m.setValue(75.0);
        lista.add(m);

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.A);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.I);
        m.setTipo(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
        m.setValue(60.0);
        lista.add(m);

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.A);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.II);
        m.setTipo(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
        m.setValue(60.0);
        lista.add(m);

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.A);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.III);
        m.setTipo(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
        m.setValue(60.0);
        lista.add(m);

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.B);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.I);
        m.setTipo(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
        m.setValue(60.0);
        lista.add(m);

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.B);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.II);
        m.setTipo(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
        m.setValue(60.0);
        lista.add(m);

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.B);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.III);
        m.setTipo(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
        m.setValue(60.0);
        lista.add(m);

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.C);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.I);
        m.setTipo(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
        m.setValue(60.0);
        lista.add(m);

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.C);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.II);
        m.setTipo(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
        m.setValue(60.0);
        lista.add(m);

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.C);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.III);
        m.setTipo(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
        m.setValue(60.0);
        lista.add(m);

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.D);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.I);
        m.setTipo(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
        m.setValue(70.0);
        lista.add(m);

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.D);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.II);
        m.setTipo(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
        m.setValue(70.0);
        lista.add(m);

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.D);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.III);
        m.setTipo(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
        m.setValue(70.0);
        lista.add(m);

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.E);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.I);
        m.setTipo(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
        m.setValue(80.0);
        lista.add(m);

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.E);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.II);
        m.setTipo(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
        m.setValue(80.0);
        lista.add(m);

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.E);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.III);
        m.setTipo(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
        m.setValue(80.0);
        lista.add(m);

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.F);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.I);
        m.setTipo(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
        m.setValue(85.0);
        lista.add(m);

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.F);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.II);
        m.setTipo(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
        m.setValue(85.0);
        lista.add(m);

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.F);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.III);
        m.setTipo(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
        m.setValue(85.0);
        lista.add(m);

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.G);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.I);
        m.setTipo(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
        m.setValue(95.0);
        lista.add(m);

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.G);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.II);
        m.setTipo(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
        m.setValue(95.0);
        lista.add(m);

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.G);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.III);
        m.setTipo(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
        m.setValue(95.0);
        lista.add(m);

        //ITALIA

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.ITALIA);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.I);
        m.setTipo(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
        m.setValue(61.10);
        m.setValueMezzaGiornata(30.55);
        lista.add(m);

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.ITALIA);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.II);
        m.setTipo(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
        m.setValue(61.10);
        m.setValueMezzaGiornata(30.55);
        lista.add(m);

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.ITALIA);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.III);
        m.setTipo(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
        m.setValue(61.10);
        m.setValueMezzaGiornata(30.55);
        lista.add(m);

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.ITALIA);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.IV);
        m.setTipo(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
        m.setValue(44.26);
        m.setValueMezzaGiornata(22.26);
        lista.add(m);

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.ITALIA);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.V);
        m.setTipo(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
        m.setValue(44.26);
        m.setValueMezzaGiornata(22.26);
        lista.add(m);

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.ITALIA);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.VI);
        m.setTipo(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
        m.setValue(44.26);
        m.setValueMezzaGiornata(22.26);
        lista.add(m);

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.ITALIA);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.VII);
        m.setTipo(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
        m.setValue(44.26);
        m.setValueMezzaGiornata(22.26);
        lista.add(m);

        m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.ITALIA);
        m.setId(UUID.randomUUID().toString());
        m.setLivello(LivelloUserEnum.VIII);
        m.setTipo(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
        m.setValue(44.26);
        m.setValueMezzaGiornata(22.26);
        lista.add(m);

        return lista;
    }

    private List<Nazione> creaNazioni() {
        List<Nazione> lista = new ArrayList<Nazione>();

        Nazione n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.G);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Austria-Vienna");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.G);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Germania-Berlino");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.G);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Germania-Bonn");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.G);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Giappone-Tokyo");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.G);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Libano");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.G);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Liecthenstein");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.G);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Svizzera");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.G);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Svizzera-Berna");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.G);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Svizzera-Ginevra");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.F);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Germania");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.F);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Paesi Bassi");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.F);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Stati Uniti-New York");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.F);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Stati Uniti-Washington");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.E);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Arabia Saudita");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.E);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Austria");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.E);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Bahrein");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.E);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Bealgio-Bruxelles");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.E);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Emirati Arabi Uniti");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.E);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Bealgio-Bruxelles");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.E);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Giappone");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.E);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Kuwait");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.E);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Oman");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.E);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Qaatar");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.E);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Stati Uniti D'America");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.E);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Yemen");
        lista.add(n);


        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.A);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Afghanistan");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.A);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Australia");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.A);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Botswana");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.A);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Bulgaria");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.A);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Burundi");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.A);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Cipro");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.A);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Comore");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.A);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Eritrea");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.A);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Etiopia");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.A);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Gibuti");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.A);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Grecia");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.A);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Iran");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.A);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Malta");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.A);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Mozambico");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.A);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Nauru Rep.");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.A);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Papua Nuova Guinea");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.A);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Portogallo");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.A);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Romania");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.A);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Ruanda");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.A);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Siria");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.A);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Somalia");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.A);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Spagna");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.A);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Uganda");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.A);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Ungheria");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.A);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Zimbabwe");
        lista.add(n);


        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.B);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Angola");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.B);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Armenia");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.B);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Arzerbaigian");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.B);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Bangladesh");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.B);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Bielorussia");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.B);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Canada");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.B);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Ceca Repubblica");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.B);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Cile");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.B);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Cina Rep. Popolare");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.B);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Costa Rica");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.B);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Cuba");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.B);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Estonia");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.B);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Figi");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.B);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Finlandia");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.B);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Finlandia - Helsinki");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.B);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Papua Nuova Guinea");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.B);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Georgia");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.B);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Giamaica");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.B);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Guatemala");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.B);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Honduras");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.B);
        n.setId(UUID.randomUUID().toString());
        n.setValue("India");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.B);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Iraq");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.B);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Irlanda");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.B);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Islanda");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.B);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Kazakistan");
        lista.add(n);
        //
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.B);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Kenia");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.B);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Kirghizistan");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.B);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Kiribati");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.B);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Lesotho");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.B);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Lettonia");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.B);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Lituania");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.B);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Madagascar");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.B);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Malawi");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.B);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Maldive");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.B);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Maurizio");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.B);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Messico");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.B);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Moldavia");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.B);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Monaco (Principato)");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.B);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Namibia");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.B);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Nepal");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.B);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Nuova Caledonia");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.B);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Nuova Zelanda");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.B);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Pakistan");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.B);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Polonia");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.B);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Russia - Federazione Russa");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.B);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Salomone");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.B);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Samoa");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.B);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Seicelle");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.B);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Slovacchia");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.B);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Spagna - Madrid");
        lista.add(n);
        //
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.B);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Sri Lanka");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.B);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Sudafricanna Repubbl.");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.B);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Swaziland");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.B);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Tagikistan");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.B);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Tanzania");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.B);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Tonga");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.B);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Turkmenistan");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.B);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Tuvalu");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.B);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Ucraina");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.B);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Nepal");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.B);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Uruguay");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.B);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Uzbekistan");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.B);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Vanuatu");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.B);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Zambia");
        lista.add(n);
        //
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.C);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Albania");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.C);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Argentina");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.C);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Bahama");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.C);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Belize");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.C);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Benin");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.C);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Bhutan");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.C);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Birmania");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.C);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Bolivia");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.C);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Bosnia ed Erzegovina");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.C);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Cina Taiwan");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.C);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Colombia");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.C);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Corea del Nord");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.C);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Corea del Sud");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.C);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Croazia");
        lista.add(n);
        //
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.C);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Danimarca");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.C);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Domicana Repubblica");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.C);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Dominica");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.C);
        n.setId(UUID.randomUUID().toString());
        n.setValue("El Salvador");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.C);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Filippine");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.C);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Francia");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.C);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Giordania");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.C);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Gran Bretagna");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.C);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Grenada");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.C);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Haiti");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.C);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Hong Kong");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.C);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Indonesia");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.C);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Israele");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.C);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Liberia");
        lista.add(n);
        //
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.C);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Macedonia");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.C);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Malaysia");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.C);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Marocco");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.C);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Mongolia");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.C);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Nicaragua");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.C);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Norvegia");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.C);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Panama");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.C);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Paraguay");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.C);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Perù");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.C);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Russia - Fed. Russa Mosca");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.C);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Saint - Lucia");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.C);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Saint - Vincente e Grenadine");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.C);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Serbia e Montenegro");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.C);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Singapore");
        lista.add(n);
        //
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.C);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Slovenia");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.C);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Sudan");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.C);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Svezia");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.C);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Thailandia");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.C);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Tunisia");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.C);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Turchia");
        lista.add(n);
        //
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.D);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Algeria");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.D);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Belgio");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.D);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Brasile");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.D);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Burkina");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.D);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Camerun");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.D);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Capo Verde");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.D);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Centraficana Repubbl.");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.D);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Ciad");
        lista.add(n);
        //
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.D);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Congo");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.D);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Congo (ex Zaire)");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.D);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Costa D'Avorio");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.D);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Francia - Parigi");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.D);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Gabon");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.D);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Gambia");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.D);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Ghana");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.D);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Gran Bretagna-Londra");
        lista.add(n);
        //
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.D);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Guinea");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.D);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Guinea - Bissau");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.D);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Guinea Equatoriale");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.D);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Guyana");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.D);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Laos");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.D);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Libia");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.D);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Lussemburgo");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.D);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Mali");
        lista.add(n);
        //
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.D);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Mauritania");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.D);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Niger");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.D);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Nigeria");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.D);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Sao-Tomè e Principe");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.D);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Senegal");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.D);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Sierra Leone");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.D);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Suriname");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.D);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Togo");
        lista.add(n);
        //
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.D);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Trinidad e Tobago");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.D);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Venezuela");
        lista.add(n);
        n = new Nazione();
        n.setAreaGeografica(AreaGeograficaEnum.D);
        n.setId(UUID.randomUUID().toString());
        n.setValue("Viet Nam");
        lista.add(n);
        return lista;

    }

    private List<QualificaUser> creaQualifiche() {
        List<QualificaUser> lista = new ArrayList<QualificaUser>();
        QualificaUser q = new QualificaUser();
        q.setId(UUID.randomUUID().toString());
        q.setValue("Assegnista");
        lista.add(q);
        q = new QualificaUser();
        q.setId(UUID.randomUUID().toString());
        q.setValue("Collaboratore Amministrativo");
        lista.add(q);
        q = new QualificaUser();
        q.setId(UUID.randomUUID().toString());
        q.setValue("Borsista");
        lista.add(q);
        q = new QualificaUser();
        q.setId(UUID.randomUUID().toString());
        q.setValue("Associato");
        lista.add(q);
        q = new QualificaUser();
        q.setId(UUID.randomUUID().toString());
        q.setValue("Collaboratore");
        lista.add(q);
        q = new QualificaUser();
        q.setId(UUID.randomUUID().toString());
        q.setValue("Ricercatore");
        lista.add(q);
        q = new QualificaUser();
        q.setId(idQualificaUser);
        q.setValue("Tecnologo");
        lista.add(q);
        q = new QualificaUser();
        q.setId(UUID.randomUUID().toString());
        q.setValue("CTER");
        lista.add(q);
        q = new QualificaUser();
        q.setId(UUID.randomUUID().toString());
        q.setValue("OPTER");
        lista.add(q);
        q = new QualificaUser();
        q.setId(UUID.randomUUID().toString());
        q.setValue("Operatore Amministrativo");
        lista.add(q);
        q = new QualificaUser();
        q.setId(UUID.randomUUID().toString());
        q.setValue("Funzionario Amministrativo");
        lista.add(q);
        q = new QualificaUser();
        q.setId(UUID.randomUUID().toString());
        q.setValue("Direttore");
        lista.add(q);
        return lista;
    }

    private User creaUser() {
        User user = null;
        Anagrafica anagrafica = null;
        Credenziali credenziali = null;
        user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setResponsabileGruppo(true);
        anagrafica = new Anagrafica();
        anagrafica.setGenere(Genere.UOMO);
        anagrafica.setCognome("Franco");
        anagrafica.setNome("Luigi");
        anagrafica.setDataNascita(new DateTime(1974, 3, 02, 0, 0, ISOChronology.getInstanceUTC()));
        anagrafica.setCodiceFiscale("frnlgu74c02g793k");
        anagrafica.setLuogoNascita("Polla");
        credenziali = new Credenziali();
        credenziali.setUsername("luigi.franco@imaa.cnr.it");
        credenziali.setRuoloUtente(RuoloUserEnum.UTENTE_ADMIN);
        credenziali.setPassword(IMd5PasswordGenerator.Md5PasswordGenerator.getMd5PasswordGenerator().withPassword("luigifranco").build());
        user.setCredenziali(credenziali);
        user.setAnagrafica(anagrafica);
        Veicolo veicolo = new Veicolo();
        veicolo.setTipo("Golf");
        veicolo.setTarga("DK969XJ");
        veicolo.setCartaCircolazione("12234");
        veicolo.setPolizzaAssicurativa("A1B2");
        veicolo.setVeicoloPrincipale(true);
        veicolo.setId(UUID.randomUUID().toString());
        Map<String, Veicolo> mappaVeicoli = new HashMap<String, Veicolo>();
        mappaVeicoli.put(veicolo.getId(), veicolo);
        user.setMappaVeicolo(mappaVeicoli);
        DatiCNR datiCNR = new DatiCNR();
        datiCNR.setIban("IT0000000000000000");
        datiCNR.setLivello(LivelloUserEnum.III);
        datiCNR.setMail("luigi.franco@imaa.cnr.it");
        datiCNR.setMatricola("1111111");
        datiCNR.setDescrizioneQualifica("Tecnologo");
        datiCNR.setIdQualifica(idQualificaUser);
        user.setDatiCNR(datiCNR);
        Patente p = new Patente();
        p.setDataRilascio(new DateTime(2001, 12, 15, 0, 0, ISOChronology.getInstanceUTC()));
        p.setNumeroPatente("SA2548911N");
        p.setRilasciataDa("MCTC");
        p.setValidaFinoAl(new DateTime(2023, 03, 02, 0, 0, ISOChronology.getInstanceUTC()));
        user.setPatente(p);
        Residenza r = new Residenza();
        r.setIndirizzo("Via Roma 27");
        r.setComune("S.Arsenio");
        user.setResidenza(r);
        user.setDataRegistrazione(new DateTime());
        user.setDateLastModified(new DateTime());
        user.setRegistrazioneCompletata(true);
        return user;
    }

}
