package it.cnr.missioni.el.dao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.geosdi.geoplatform.experimental.el.configurator.GPIndexConfigurator;
import org.geosdi.geoplatform.experimental.el.dao.GPElasticSearchDAO;
import org.geosdi.geoplatform.experimental.el.dao.GPElasticSearchDAO.Page;
import org.geosdi.geoplatform.experimental.el.index.GPIndexCreator;
import org.geosdi.geoplatform.logger.support.annotation.GeoPlatformLog;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import it.cnr.missioni.el.model.search.BooleanModelSearch;
import it.cnr.missioni.el.model.search.DateRangeSearch;
import it.cnr.missioni.el.model.search.ExactSearch;
import it.cnr.missioni.model.missione.DatiAnticipoPagamenti;
import it.cnr.missioni.model.missione.DatiPeriodoMissione;
import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.model.missione.StatoEnum;
import it.cnr.missioni.model.rimborso.Fattura;
import it.cnr.missioni.model.rimborso.Rimborso;

/**
 * @author Salvia Vito
 */
@FixMethodOrder(value = MethodSorters.NAME_ASCENDING)
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext-DAO-Test.xml" })
public class MissioneDAOTest {

	@GeoPlatformLog
	static Logger logger;
	@Resource(name = "missioniIndexConfigurator")
	private GPIndexConfigurator missioniDocIndexConfigurator;
	@Resource(name = "missioneIndexCreator")
	private GPIndexCreator missioneDocIndexCreator;

	@Resource(name = "missioneDAO")
	private IMissioneDAO missioneDAO;

	private List<Missione> listaMissioni = new ArrayList<Missione>();

	BooleanModelSearch booleanModelSearch;

	private static final String FIELD_DATA_INSERIMENTO = "missione.dataInserimento";
	private static final String FIELD_ID_UTENTE = "missione.idUtente";
	private static final String FIELD_STATO = "missione.stato";
	private static final String FIELD_RIMBORSO_NUMERO_ORDINE = "missione.rimborso.numeroOrdine";
	private static final String FIELD_RIMBORSO_DATA_RIMBORSO = "missione.rimborso.dataRimborso";

	@Before
	public void setUp() {
		Assert.assertNotNull(missioneDocIndexCreator);
		Assert.assertNotNull(missioniDocIndexConfigurator);
		Assert.assertNotNull(missioneDAO);
	}

	@Test
	public void A_createMissioneCNRTest() throws Exception {
		creaMissioni();
		missioneDAO.persist(this.listaMissioni);
		Thread.sleep(1000);
		logger.debug("############################NUMBER_OF_MISSIONI : {}\n", this.missioneDAO.count().intValue());

	}

	@Test
	public void B_findMissioneByUtente_1Test() throws Exception {
		booleanModelSearch = new BooleanModelSearch();
		ExactSearch e = new ExactSearch();
		e.setField(FIELD_ID_UTENTE);
		e.setValue("01");
		booleanModelSearch.getListaSearch().add(e);
		List<Missione> lista = this.missioneDAO.findMissioneByUtenteStato(new Page(0, 10), booleanModelSearch);
		Assert.assertTrue("FIND MISSIONE BY UTENTE", lista.size() == 2);

	}

	@Test
	public void C_findMissioneByUtente_2Test() throws Exception {
		booleanModelSearch = new BooleanModelSearch();
		ExactSearch e = new ExactSearch();
		e.setField(FIELD_ID_UTENTE);
		e.setValue("03");
		booleanModelSearch.getListaSearch().add(e);
		List<Missione> lista = this.missioneDAO.findMissioneByUtenteStato(new Page(0, 10), booleanModelSearch);
		Assert.assertTrue("FIND MISSIONE BY UTENTE", lista.isEmpty());

	}

	@Test
	public void D_findMissioneByStato_1Test() throws Exception {
		booleanModelSearch = new BooleanModelSearch();
		ExactSearch e = new ExactSearch();
		e.setField(FIELD_STATO);
		e.setValue(StatoEnum.PRESA_IN_CARICO.name());
		booleanModelSearch.getListaSearch().add(e);
		List<Missione> lista = this.missioneDAO.findMissioneByUtenteStato(new Page(0, 10), booleanModelSearch);
		Assert.assertTrue("FIND MISSIONE BY STATO", lista.size() == 2);

	}

	@Test
	public void E_findMissioneByStato_2Test() throws Exception {
		booleanModelSearch = new BooleanModelSearch();
		ExactSearch e = new ExactSearch();
		e.setField(FIELD_STATO);
		e.setValue(StatoEnum.APPROVATA.name());
		booleanModelSearch.getListaSearch().add(e);
		List<Missione> lista = this.missioneDAO.findMissioneByUtenteStato(new Page(0, 10), booleanModelSearch);
		Assert.assertTrue("FIND MISSIONE BY STATO", lista.size() == 1);

	}

	@Test
	public void F_findMissioneByUtenteStato_1Test() throws Exception {
		booleanModelSearch = new BooleanModelSearch();

		ExactSearch e = new ExactSearch();
		e.setField(FIELD_STATO);
		e.setValue(StatoEnum.APPROVATA.name());

		ExactSearch e2 = new ExactSearch();
		e2.setField(FIELD_ID_UTENTE);
		e2.setValue("01");

		booleanModelSearch.getListaSearch().add(e);
		booleanModelSearch.getListaSearch().add(e2);
		List<Missione> lista = this.missioneDAO.findMissioneByUtenteStato(new Page(0, 10), booleanModelSearch);
		Assert.assertTrue("FIND MISSIONE BY STATO", lista.isEmpty());

	}

	@Test
	public void G_findMissioneByUtenteStato_2Test() throws Exception {
		booleanModelSearch = new BooleanModelSearch();

		ExactSearch e = new ExactSearch();
		e.setField(FIELD_STATO);
		e.setValue(StatoEnum.PRESA_IN_CARICO.name());

		ExactSearch e2 = new ExactSearch();
		e2.setField(FIELD_ID_UTENTE);
		e2.setValue("01");

		booleanModelSearch.getListaSearch().add(e);
		booleanModelSearch.getListaSearch().add(e2);
		List<Missione> lista = this.missioneDAO.findMissioneByUtenteStato(new Page(0, 10), booleanModelSearch);
		Assert.assertTrue("FIND MISSIONE BY STATO", lista.size() == 2);
	}

	// @Test
	// public void H_insertMassiveMissioniTest() throws Exception {
	// BulkResponse bulkResponse =
	// this.missioneDAO.persist(createMassiveMissioni());
	// logger.info("#####################MASSIVE_MISSIONI_INSERT_TIME : {}\n",
	// bulkResponse.getTook().toString());
	// }

	@Test
	public void I_findMissioniWithPageTest() throws Exception {
		GPElasticSearchDAO.IPageResult<Missione> pageResult = this.missioneDAO
				.find(new GPElasticSearchDAO.Page(0, 3000));
		logger.info("########################MISSIONI_FOUND : {}\n", pageResult.getTotal());
	}

	// @Test
	// public void L_removeMissioneTest() throws Exception {
	// missioneDAO.delete("M_01");
	// Thread.sleep(1000);
	// logger.debug("############################NUMBER_OF_MISSIONI : {}\n",
	// this.missioneDAO.count().intValue());
	// }

	@Test
	public void L_findMissioniByData_1() throws Exception {
		booleanModelSearch = new BooleanModelSearch();
		DateRangeSearch d = new DateRangeSearch();
		d.setField(FIELD_DATA_INSERIMENTO);
		d.setFrom(new DateTime(2015, 8, 13, 0, 0, DateTimeZone.UTC));
		d.setTo(new DateTime(2015, 8, 13, 0, 0, DateTimeZone.UTC));
		booleanModelSearch.getListaSearch().add(d);
		List<Missione> lista = this.missioneDAO.findMissioneByUtenteStato(new Page(0, 10), booleanModelSearch);
		Assert.assertTrue("FIND MISSIONE BY DATA", lista.size() == 1);
	}

	@Test
	public void M_findMissioniByData_2() throws Exception {
		booleanModelSearch = new BooleanModelSearch();
		DateRangeSearch d = new DateRangeSearch();
		d.setField(FIELD_DATA_INSERIMENTO);
		d.setFrom(new DateTime(2015, 8, 14, 0, 0, DateTimeZone.UTC));
		d.setTo(new DateTime(2015, 8, 31, 0, 0, DateTimeZone.UTC));
		booleanModelSearch.getListaSearch().add(d);
		List<Missione> lista = this.missioneDAO.findMissioneByUtenteStato(new Page(0, 10), booleanModelSearch);
		Assert.assertTrue("FIND MISSIONE BY DATA", lista.size() == 0);

	}

	@Test
	public void N_findMissioniByAll() throws Exception {
		booleanModelSearch = new BooleanModelSearch();
		DateRangeSearch d = new DateRangeSearch();
		d.setField(FIELD_DATA_INSERIMENTO);
		d.setFrom(new DateTime(2015, 8, 13, 0, 0, DateTimeZone.UTC));
		d.setTo(new DateTime(2015, 8, 31, 0, 0, DateTimeZone.UTC));

		ExactSearch e = new ExactSearch();
		e.setField(FIELD_STATO);
		e.setValue(StatoEnum.PRESA_IN_CARICO.name());

		ExactSearch e2 = new ExactSearch();
		e2.setField(FIELD_ID_UTENTE);
		e2.setValue("01");

		booleanModelSearch.getListaSearch().add(d);
		booleanModelSearch.getListaSearch().add(e);
		booleanModelSearch.getListaSearch().add(e2);

		List<Missione> lista = this.missioneDAO.findMissioneByUtenteStato(new Page(0, 10), booleanModelSearch);
		Assert.assertTrue("FIND MISSIONE BY ALL", lista.size() == 1);

	}

	@Test
	public void O_findMissioniByRimborsoNumeroOrdine() throws Exception {

		ExactSearch e = new ExactSearch();
		e.setField(FIELD_RIMBORSO_NUMERO_ORDINE);
		e.setValue(new Long(156));
		booleanModelSearch = new BooleanModelSearch();
		booleanModelSearch.getListaSearch().add(e);
		List<Missione> lista = this.missioneDAO.findMissioneByUtenteStato(new Page(0, 10), booleanModelSearch);
		Assert.assertTrue("FIND MISSIONE BY NUMERO ORDINE RIMBORSO", lista.size() == 1);
	}

	@Test
	public void P_findMissioniByDataRimborso_1() throws Exception {
		booleanModelSearch = new BooleanModelSearch();
		DateRangeSearch d = new DateRangeSearch();
		d.setField(FIELD_RIMBORSO_DATA_RIMBORSO);
		d.setFrom(new DateTime(2015, 12, 12, 0, 0, DateTimeZone.UTC));
//		d.setTo(new DateTime(2015, 8, 31, 0, 0, DateTimeZone.UTC));
		booleanModelSearch.getListaSearch().add(d);
		List<Missione> lista = this.missioneDAO.findMissioneByUtenteStato(new Page(0, 10), booleanModelSearch);
		Assert.assertTrue("FIND MISSIONE BY DATA RIMBORSO", lista.size() == 1);
	}

	 @Test
	 public void tearDown() throws Exception {
	 this.missioneDocIndexCreator.deleteIndex();
	 }

	private void creaMissioni() {
		Missione missione = new Missione();
		missione.setId("M_01");
		missione.setOggetto("Conferenza");
		missione.setLocalita("Roma");
		missione.setIdUtente("01");
		missione.setMissioneEstera(false);
		missione.setStato(StatoEnum.PRESA_IN_CARICO);
		missione.setDataInserimento(new DateTime(2015, 11, 13, 0, 0, DateTimeZone.UTC));
		DatiAnticipoPagamenti dati = new DatiAnticipoPagamenti();
		dati.setAnticipazioniMonetarie(true);
		dati.setMandatoCNR("AA11");
		dati.setRimborsoDaTerzi(false);
		dati.setSpeseMissioniAnticipate(102.00);
		missione.setDatiAnticipoPagamenti(dati);
		DatiPeriodoMissione datiPeriodoMissione = new DatiPeriodoMissione();
		datiPeriodoMissione.setInizioMissione(new DateTime(2015, 11, 11, 0, 0, DateTimeZone.UTC));
		datiPeriodoMissione.setFineMissione(new DateTime(2015, 11, 15, 0, 0, DateTimeZone.UTC));
		missione.setDatiPeriodoMissione(datiPeriodoMissione);

		Fattura fattura = new Fattura();
		fattura.setNumeroFattura(134);
		fattura.setData(new DateTime(2015, 11, 12, 13, 0, DateTimeZone.UTC));
		fattura.setImporto(89.8);
		fattura.setTipologiaSpesa("Pernottamento");
		fattura.setValuta("Euro");

		Fattura fattura_2 = new Fattura();
		fattura_2.setNumeroFattura(135);
		fattura_2.setData(new DateTime(2015, 11, 13, 13, 0, DateTimeZone.UTC));
		fattura_2.setImporto(89.8);
		fattura_2.setTipologiaSpesa("Pernottamento");
		fattura_2.setValuta("Euro");

		Rimborso rimborso = new Rimborso();
		rimborso.setNumeroOrdine(156);
		rimborso.setAvvisoPagamento("Via Verdi");
		rimborso.setAnticipazionePagamento(0.0);
		rimborso.setDataRimborso(new DateTime(2015, 12, 12, 13, 14, DateTimeZone.UTC));
		rimborso.setTotale(179.6);

		rimborso.getListaFatture().add(fattura);
		rimborso.getListaFatture().add(fattura_2);
		missione.setRimborso(rimborso);

		listaMissioni.add(missione);

		missione = new Missione();
		missione.setId("M_02");
		missione.setOggetto("Conferenza");
		missione.setLocalita("Milano");
		missione.setIdUtente("01");
		missione.setStato(StatoEnum.PRESA_IN_CARICO);
		missione.setDataInserimento(new DateTime(2015, 8, 13, 0, 0, DateTimeZone.UTC));
		listaMissioni.add(missione);

		missione = new Missione();
		missione.setId("M_03");
		missione.setOggetto("Riunione");
		missione.setLocalita("Milano");
		missione.setIdUtente("02");
		missione.setStato(StatoEnum.APPROVATA);
		missione.setDataInserimento(new DateTime(2015, 11, 23, 0, 0, DateTimeZone.UTC));
		listaMissioni.add(missione);
	}

	// List<Missione> createMassiveMissioni() {
	// List<Missione> missioni = Lists.newArrayList();
	// for (int i = 0; i < 8000; i++) {
	// Missione missione = new Missione();
	// missione.setId(UUID.randomUUID().toString());
	// missione.setOggetto("OGGETTO-TEST - " + i);
	// missione.setLocalita("LOCALITAA' - " + i);
	// missione.setIdUtente(UUID.randomUUID().toString());
	// if ((i % 2) == 0) {
	// missione.setStato(StatoEnum.APPROVATA);
	// } else {
	// missione.setStato(StatoEnum.PRESA_IN_CARICO);
	// }
	// missioni.add(missione);
	// }
	// return missioni;
	// }
}
