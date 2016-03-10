package it.cnr.missioni.el.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.geosdi.geoplatform.experimental.el.configurator.GPIndexConfigurator;
import org.geosdi.geoplatform.experimental.el.index.GPIndexCreator;
import org.geosdi.geoplatform.logger.support.annotation.GeoPlatformLog;
import org.joda.time.DateTime;
import org.joda.time.chrono.ISOChronology;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import it.cnr.missioni.model.configuration.Massimale;
import it.cnr.missioni.model.configuration.Nazione;
import it.cnr.missioni.model.configuration.QualificaUser;
import it.cnr.missioni.model.configuration.RimborsoKm;
import it.cnr.missioni.model.configuration.TipologiaSpesa;
import it.cnr.missioni.model.configuration.TipologiaSpesa.TipoSpesaEnum;
import it.cnr.missioni.model.missione.TrattamentoMissioneEsteraEnum;
import it.cnr.missioni.model.configuration.Nazione.AreaGeograficaEnum;
import it.cnr.missioni.model.user.Anagrafica;
import it.cnr.missioni.model.user.Credenziali;
import it.cnr.missioni.model.user.DatiCNR;
import it.cnr.missioni.model.user.Patente;
import it.cnr.missioni.model.user.Residenza;
import it.cnr.missioni.model.user.RuoloUserEnum;
import it.cnr.missioni.model.user.User;
import it.cnr.missioni.model.user.Veicolo;
import it.cnr.missioni.model.user.Anagrafica.Genere;
import it.cnr.missioni.model.user.DatiCNR.LivelloUserEnum;

/**
 * @author Salvia Vito
 */
@FixMethodOrder(value = MethodSorters.NAME_ASCENDING)
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext-DAO-Test.xml" })
public class InitDAOTest {

	@GeoPlatformLog
	static Logger logger;
	@Resource(name = "missioniIndexConfigurator")
	private GPIndexConfigurator missioniIndexConfigurator;

	@Resource(name = "qualificaUserIndexCreator")
	private GPIndexCreator qualificaUserDocIndexCreator;
	@Resource(name = "qualificaUserDAO")
	private IQualificaUserDAO qualificaUserDAO;

	@Resource(name = "userIndexCreator")
	private GPIndexCreator userDocIndexCreator;
	@Resource(name = "userDAO")
	private IUserDAO userDAO;

	@Resource(name = "nazioneIndexCreator")
	private GPIndexCreator nazioneDocIndexCreator;
	@Resource(name = "nazioneDAO")
	private INazioneDAO nazioneDAO;

	@Resource(name = "rimborsoKmIndexCreator")
	private GPIndexCreator rimborsoKmDocIndexCreator;
	@Resource(name = "rimborsoKmDAO")
	private IRimborsoKmDAO rimborsoKmDAO;

	@Resource(name = "tipologiaSpesaIndexCreator")
	private GPIndexCreator tipoligiaSpesaDocIndexCreator;
	@Resource(name = "tipologiaSpesaDAO")
	private ITipologiaSpesaDAO tipologiaSpesaDAO;

	@Before
	public void setUp() {
		Assert.assertNotNull(missioniIndexConfigurator);

		Assert.assertNotNull(qualificaUserDocIndexCreator);
		Assert.assertNotNull(qualificaUserDAO);
		Assert.assertNotNull(userDocIndexCreator);
		Assert.assertNotNull(qualificaUserDAO);
		Assert.assertNotNull(nazioneDocIndexCreator);
		Assert.assertNotNull(nazioneDAO);
		Assert.assertNotNull(rimborsoKmDocIndexCreator);
		Assert.assertNotNull(rimborsoKmDAO);
		Assert.assertNotNull(tipoligiaSpesaDocIndexCreator);
		Assert.assertNotNull(tipologiaSpesaDAO);
	}

	@Test
	public void insertUserTest() throws Exception {
		userDAO.persist(creaUser());
	}

	@Test
	public void insertQualificaTest() throws Exception {
		qualificaUserDAO.persist(creaQualifiche());
	}

	@Test
	public void insertRimborsoKmTest() throws Exception {
		RimborsoKm r = new RimborsoKm();
		r.setId(UUID.randomUUID().toString());
		r.setValue(0.36);
		rimborsoKmDAO.persist(r);
	}

	@Test
	public void insertNazioniTest() throws Exception {
		nazioneDAO.persist(creaNazioni());
	}
	
	@Test
	public void insertTipologiaSpesaTest() throws Exception {
		tipologiaSpesaDAO.persist(creaTipologiaSpesa());
	}
	
	

	private List<TipologiaSpesa> creaTipologiaSpesa() {
		List<TipologiaSpesa> lista = new ArrayList<TipologiaSpesa>();

		TipologiaSpesa t = new TipologiaSpesa();
		t.setId(UUID.randomUUID().toString());
		t.setTipo(TipoSpesaEnum.ESTERA);
		t.setTipoTrattamento(TrattamentoMissioneEsteraEnum.TRATTAMENTO_ALTERNATIVO);
		t.setValue("Aereo");
		lista.add(t);

		t = new TipologiaSpesa();
		t.setId(UUID.randomUUID().toString());
		t.setTipo(TipoSpesaEnum.ESTERA);
		t.setTipoTrattamento(TrattamentoMissioneEsteraEnum.TRATTAMENTO_ALTERNATIVO);
		t.setValue("Altro");
		lista.add(t);

		t = new TipologiaSpesa();
		t.setId(UUID.randomUUID().toString());
		t.setTipo(TipoSpesaEnum.ESTERA);
		t.setTipoTrattamento(TrattamentoMissioneEsteraEnum.TRATTAMENTO_ALTERNATIVO);
		t.setValue("Altro Albergo");
		lista.add(t);

		t = new TipologiaSpesa();
		t.setId(UUID.randomUUID().toString());
		t.setTipo(TipoSpesaEnum.ESTERA);
		t.setTipoTrattamento(TrattamentoMissioneEsteraEnum.TRATTAMENTO_ALTERNATIVO);
		t.setValue("Altro mezzo di trasporto");
		lista.add(t);

		t = new TipologiaSpesa();
		t.setId(UUID.randomUUID().toString());
		t.setTipo(TipoSpesaEnum.ESTERA);
		t.setTipoTrattamento(TrattamentoMissioneEsteraEnum.TRATTAMENTO_ALTERNATIVO);
		t.setValue("Altro pasto");
		lista.add(t);

		t = new TipologiaSpesa();
		t.setId(UUID.randomUUID().toString());
		t.setTipo(TipoSpesaEnum.ESTERA);
		t.setTipoTrattamento(TrattamentoMissioneEsteraEnum.TRATTAMENTO_ALTERNATIVO);
		t.setValue("Nave");
		lista.add(t);

		t = new TipologiaSpesa();
		t.setId(UUID.randomUUID().toString());
		t.setTipo(TipoSpesaEnum.ESTERA);
		t.setTipoTrattamento(TrattamentoMissioneEsteraEnum.TRATTAMENTO_ALTERNATIVO);
		t.setValue("Spese visto");
		lista.add(t);

		t = new TipologiaSpesa();
		t.setId(UUID.randomUUID().toString());
		t.setTipo(TipoSpesaEnum.ESTERA);
		t.setTipoTrattamento(TrattamentoMissioneEsteraEnum.TRATTAMENTO_ALTERNATIVO);
		t.setValue("Taxi");
		lista.add(t);

		t = new TipologiaSpesa();
		t.setId(UUID.randomUUID().toString());
		t.setTipo(TipoSpesaEnum.ESTERA);
		t.setTipoTrattamento(TrattamentoMissioneEsteraEnum.TRATTAMENTO_ALTERNATIVO);
		t.setValue("Treno");
		lista.add(t);
		
		
		t = new TipologiaSpesa();
		t.setId(UUID.randomUUID().toString());
		t.setTipo(TipoSpesaEnum.ITALIA);
		t.setTipoTrattamento(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
		t.setValue("Aereo");
		lista.add(t);
		
		t = new TipologiaSpesa();
		t.setId(UUID.randomUUID().toString());
		t.setTipo(TipoSpesaEnum.ITALIA);
		t.setTipoTrattamento(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
		t.setValue("Albergo");
		lista.add(t);
		
		t = new TipologiaSpesa();
		t.setId(UUID.randomUUID().toString());
		t.setTipo(TipoSpesaEnum.ITALIA);
		t.setTipoTrattamento(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
		t.setValue("Altre spese viaggio");
		lista.add(t);
		
		t = new TipologiaSpesa();
		t.setId(UUID.randomUUID().toString());
		t.setTipo(TipoSpesaEnum.ITALIA);
		t.setTipoTrattamento(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
		t.setValue("Altri costi congressuali");
		lista.add(t);
		
		t = new TipologiaSpesa();
		t.setId(UUID.randomUUID().toString());
		t.setTipo(TipoSpesaEnum.ITALIA);
		t.setTipoTrattamento(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
		t.setValue("Altro");
		lista.add(t);
		
		t = new TipologiaSpesa();
		t.setId(UUID.randomUUID().toString());
		t.setTipo(TipoSpesaEnum.ITALIA);
		t.setTipoTrattamento(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
		t.setValue("Altro albergo");
		lista.add(t);
		
		t = new TipologiaSpesa();
		t.setId(UUID.randomUUID().toString());
		t.setTipo(TipoSpesaEnum.ITALIA);
		t.setTipoTrattamento(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
		t.setValue("Altro mezzo trasporto");
		lista.add(t);
		
		t = new TipologiaSpesa();
		t.setId(UUID.randomUUID().toString());
		t.setTipo(TipoSpesaEnum.ITALIA);
		t.setTipoTrattamento(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
		t.setValue("Altro pasto");
		lista.add(t);
		
		t = new TipologiaSpesa();
		t.setId(UUID.randomUUID().toString());
		t.setTipo(TipoSpesaEnum.ITALIA);
		t.setTipoTrattamento(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
		t.setValue("Autobus\\corriera");
		lista.add(t);
		
		t = new TipologiaSpesa();
		t.setId(UUID.randomUUID().toString());
		t.setTipo(TipoSpesaEnum.ITALIA);
		t.setTipoTrattamento(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
		t.setValue("Cuccetta");
		lista.add(t);
		
		t = new TipologiaSpesa();
		t.setId(UUID.randomUUID().toString());
		t.setTipo(TipoSpesaEnum.ITALIA);
		t.setTipoTrattamento(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
		t.setValue("Indennità kilometrica");
		lista.add(t);
		
		t = new TipologiaSpesa();
		t.setId(UUID.randomUUID().toString());
		t.setTipo(TipoSpesaEnum.ITALIA);
		t.setTipoTrattamento(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
		t.setValue("Iscrizione congresso");
		lista.add(t);
		
		t = new TipologiaSpesa();
		t.setId(UUID.randomUUID().toString());
		t.setTipo(TipoSpesaEnum.ITALIA);
		t.setTipoTrattamento(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
		t.setValue("Nave");
		lista.add(t);
		
		t = new TipologiaSpesa();
		t.setId(UUID.randomUUID().toString());
		t.setTipo(TipoSpesaEnum.ITALIA);
		t.setTipoTrattamento(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
		t.setValue("Noleggio auto");
		lista.add(t);
		
		t = new TipologiaSpesa();
		t.setId(UUID.randomUUID().toString());
		t.setTipo(TipoSpesaEnum.ITALIA);
		t.setTipoTrattamento(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
		t.setValue("Parcheggio");
		lista.add(t);
		
		t = new TipologiaSpesa();
		t.setId(UUID.randomUUID().toString());
		t.setTipo(TipoSpesaEnum.ITALIA);
		t.setTipoTrattamento(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
		t.setValue("Pasto");
		lista.add(t);
		
		t = new TipologiaSpesa();
		t.setId(UUID.randomUUID().toString());
		t.setTipo(TipoSpesaEnum.ITALIA);
		t.setTipoTrattamento(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
		t.setValue("Pesaggio autostrada");
		lista.add(t);
		
		t = new TipologiaSpesa();
		t.setId(UUID.randomUUID().toString());
		t.setTipo(TipoSpesaEnum.ITALIA);
		t.setTipoTrattamento(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
		t.setValue("Spese bagagli");
		lista.add(t);
		
		t = new TipologiaSpesa();
		t.setId(UUID.randomUUID().toString());
		t.setTipo(TipoSpesaEnum.ITALIA);
		t.setTipoTrattamento(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
		t.setValue("Spese visto");
		lista.add(t);
		
		t = new TipologiaSpesa();
		t.setId(UUID.randomUUID().toString());
		t.setTipo(TipoSpesaEnum.ITALIA);
		t.setTipoTrattamento(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
		t.setValue("Taxi");
		lista.add(t);
		

		return lista;
	}

	private List<Massimale> creaMassimali(){
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
		lista.add(m);
		
		
		m = new Massimale();
		m.setAreaGeografica(AreaGeograficaEnum.ITALIA);
		m.setId(UUID.randomUUID().toString());
		m.setLivello(LivelloUserEnum.II);
		m.setTipo(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
		m.setValue(61.10);
		lista.add(m);
		
		m = new Massimale();
		m.setAreaGeografica(AreaGeograficaEnum.ITALIA);
		m.setId(UUID.randomUUID().toString());
		m.setLivello(LivelloUserEnum.III);
		m.setTipo(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
		m.setValue(61.0);
		lista.add(m);
		
		m = new Massimale();
		m.setAreaGeografica(AreaGeograficaEnum.ITALIA);
		m.setId(UUID.randomUUID().toString());
		m.setLivello(LivelloUserEnum.IV);
		m.setTipo(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
		m.setValue(44.26);
		lista.add(m);
		
		
		m = new Massimale();
		m.setAreaGeografica(AreaGeograficaEnum.ITALIA);
		m.setId(UUID.randomUUID().toString());
		m.setLivello(LivelloUserEnum.V);
		m.setTipo(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
		m.setValue(44.26);
		lista.add(m);
		
		m = new Massimale();
		m.setAreaGeografica(AreaGeograficaEnum.ITALIA);
		m.setId(UUID.randomUUID().toString());
		m.setLivello(LivelloUserEnum.VI);
		m.setTipo(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
		m.setValue(44.26);
		lista.add(m);
		
		m = new Massimale();
		m.setAreaGeografica(AreaGeograficaEnum.ITALIA);
		m.setId(UUID.randomUUID().toString());
		m.setLivello(LivelloUserEnum.VII);
		m.setTipo(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
		m.setValue(44.26);
		lista.add(m);
		
		m = new Massimale();
		m.setAreaGeografica(AreaGeograficaEnum.ITALIA);
		m.setId(UUID.randomUUID().toString());
		m.setLivello(LivelloUserEnum.VII);
		m.setTipo(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
		m.setValue(44.26);
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

		return lista;

	}

	private List<QualificaUser> creaQualifiche() {
		List<QualificaUser> lista = new ArrayList<QualificaUser>();
		QualificaUser q = new QualificaUser();
		q.setId(UUID.randomUUID().toString());
		q.setValue("Dipendente");
		lista.add(q);
		q = new QualificaUser();
		q.setId(UUID.randomUUID().toString());
		q.setValue("Assegnista");
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
		anagrafica.setCognome("Salvia");
		anagrafica.setNome("Vito");
		anagrafica.setDataNascita(new DateTime(1982, 7, 30, 0, 0, ISOChronology.getInstanceUTC()));
		anagrafica.setCodiceFiscale("slvvtttttttttttt");
		anagrafica.setLuogoNascita("Potenza");
		credenziali = new Credenziali();
		credenziali.setUsername("vito.salvia");
		credenziali.setRuoloUtente(RuoloUserEnum.UTENTE_ADMIN);
		credenziali.setPassword(credenziali.md5hash("vitosalvia"));
		user.setCredenziali(credenziali);
		user.setAnagrafica(anagrafica);
		Veicolo veicolo = new Veicolo();
		veicolo.setTipo("Ford Fiesta");
		veicolo.setTarga("AA111BB");
		veicolo.setCartaCircolazione("12234");
		veicolo.setPolizzaAssicurativa("A1B2");
		veicolo.setVeicoloPrincipale(true);
		veicolo.setId("01");
		Map<String, Veicolo> mappaVeicoli = new HashMap<String, Veicolo>();
		mappaVeicoli.put(veicolo.getId(), veicolo);
		user.setMappaVeicolo(mappaVeicoli);
		DatiCNR datiCNR = new DatiCNR();
		datiCNR.setDatoreLavoro("02");
		datiCNR.setShortDescriptionDatoreLavoro("Rossi Paolo");
		datiCNR.setIban("IT0000000000000000");
		datiCNR.setLivello(LivelloUserEnum.V);
		datiCNR.setMail("vito.salvia@gmail.com");
		datiCNR.setMatricola("1111111");
		datiCNR.setDescrizioneQualifica("Assegnista");
		datiCNR.setIdQualifica("01");
		user.setDatiCNR(datiCNR);
		Patente p = new Patente();
		p.setDataRilascio(new DateTime(2001, 12, 15, 0, 0, ISOChronology.getInstanceUTC()));
		p.setNumeroPatente("12334");
		p.setRilasciataDa("MCTC");
		p.setValidaFinoAl(new DateTime(2021, 12, 15, 0, 0, ISOChronology.getInstanceUTC()));
		user.setPatente(p);
		Residenza r = new Residenza();
		r.setIndirizzo("Via Verdi");
		r.setComune("Tito");
		r.setDomicilioFiscale("Via Convento");
		user.setResidenza(r);
		user.setDataRegistrazione(new DateTime(2015, 1, 4, 0, 0, ISOChronology.getInstanceUTC()));
		user.setDateLastModified(new DateTime(2015, 1, 4, 0, 0, ISOChronology.getInstanceUTC()));
		user.setRegistrazioneCompletata(true);
		return user;
	}

}
