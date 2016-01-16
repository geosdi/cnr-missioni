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

import it.cnr.missioni.el.model.search.builder.MissioneSearchBuilder;
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
	public void B_findMissioneByUser_1Test() throws Exception {
		MissioneSearchBuilder missioneSearchBuilder = MissioneSearchBuilder.getMissioneSearchBuilder().withIdUser("01");
		List<Missione> lista = this.missioneDAO.findMissioneByQuery(new Page(0, 10), missioneSearchBuilder);
		Assert.assertTrue("FIND MISSIONE BY USER", lista.size() == 2);

	}

	@Test
	public void C_findMissioneByUser_2Test() throws Exception {
		MissioneSearchBuilder missioneSearchBuilder = MissioneSearchBuilder.getMissioneSearchBuilder().withIdUser("03");

		List<Missione> lista = this.missioneDAO.findMissioneByQuery(new Page(0, 10), missioneSearchBuilder);
		Assert.assertTrue("FIND MISSIONE BY USER", lista.isEmpty());

	}

	@Test
	public void D_findMissioneByStato_1Test() throws Exception {
		MissioneSearchBuilder missioneSearchBuilder = MissioneSearchBuilder.getMissioneSearchBuilder()
				.withStato(StatoEnum.PRESA_IN_CARICO.name());
		missioneSearchBuilder.setStato(StatoEnum.PRESA_IN_CARICO.name());
		List<Missione> lista = this.missioneDAO.findMissioneByQuery(new Page(0, 10), missioneSearchBuilder);
		Assert.assertTrue("FIND MISSIONE BY STATO", lista.size() == 2);

	}

	@Test
	public void E_findMissioneByStato_2Test() throws Exception {
		MissioneSearchBuilder missioneSearchBuilder = MissioneSearchBuilder.getMissioneSearchBuilder()
				.withStato(StatoEnum.APPROVATA.name());

		List<Missione> lista = this.missioneDAO.findMissioneByQuery(new Page(0, 10), missioneSearchBuilder);
		Assert.assertTrue("FIND MISSIONE BY STATO", lista.size() == 1);

	}

	@Test
	public void F_findMissioneByUserStato_1Test() throws Exception {
		MissioneSearchBuilder missioneSearchBuilder = MissioneSearchBuilder.getMissioneSearchBuilder()
				.withStato(StatoEnum.APPROVATA.name()).withIdUser("01");
		List<Missione> lista = this.missioneDAO.findMissioneByQuery(new Page(0, 10), missioneSearchBuilder);
		Assert.assertTrue("FIND MISSIONE BY STATO", lista.isEmpty());

	}

	@Test
	public void G_findMissioneByUserStato_2Test() throws Exception {
		MissioneSearchBuilder missioneSearchBuilder = MissioneSearchBuilder.getMissioneSearchBuilder()
				.withStato(StatoEnum.PRESA_IN_CARICO.name()).withIdUser("01");
		List<Missione> lista = this.missioneDAO.findMissioneByQuery(new Page(0, 10), missioneSearchBuilder);
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

		MissioneSearchBuilder missioneSearchBuilder = MissioneSearchBuilder.getMissioneSearchBuilder()
				.withRangemDataInserimento(new DateTime(2015, 8, 13, 0, 0, DateTimeZone.UTC), new DateTime(2015, 8, 13, 0, 0, DateTimeZone.UTC));
		List<Missione> lista = this.missioneDAO.findMissioneByQuery(new Page(0, 10), missioneSearchBuilder);
		Assert.assertTrue("FIND MISSIONE BY DATA", lista.size() == 1);
	}

	@Test
	public void M_findMissioniByData_2() throws Exception {

		MissioneSearchBuilder missioneSearchBuilder = MissioneSearchBuilder.getMissioneSearchBuilder()
				.withRangemDataInserimento(new DateTime(2015, 8, 14, 0, 0, DateTimeZone.UTC), new DateTime(2015, 8, 31, 0, 0, DateTimeZone.UTC));
		
		List<Missione> lista = this.missioneDAO.findMissioneByQuery(new Page(0, 10), missioneSearchBuilder);
		Assert.assertTrue("FIND MISSIONE BY DATA", lista.size() == 0);

	}

	@Test
	public void N_findMissioniByAll() throws Exception {

		MissioneSearchBuilder missioneSearchBuilder = MissioneSearchBuilder.getMissioneSearchBuilder()
				.withRangemDataInserimento(new DateTime(2015, 8, 13, 0, 0, DateTimeZone.UTC), new DateTime(2015, 8, 31, 0, 0, DateTimeZone.UTC));
		
		missioneSearchBuilder.setStato(StatoEnum.PRESA_IN_CARICO.name());
		missioneSearchBuilder.setIdUser("01");

		List<Missione> lista = this.missioneDAO.findMissioneByQuery(new Page(0, 10), missioneSearchBuilder);
		Assert.assertTrue("FIND MISSIONE BY ALL", lista.size() == 1);

	}

	@Test
	public void O_findMissioniByRimborsoNumeroOrdine() throws Exception {
		MissioneSearchBuilder missioneSearchBuilder = MissioneSearchBuilder.getMissioneSearchBuilder()
				.withNumeroOrdineMissione("56");
		List<Missione> lista = this.missioneDAO.findMissioneByQuery(new Page(0, 10), missioneSearchBuilder);
		Assert.assertTrue("FIND MISSIONE BY NUMERO ORDINE RIMBORSO", lista.size() == 1);
	}

	@Test
	public void P_findMissioniByDataRimborso_1() throws Exception {

		MissioneSearchBuilder missioneSearchBuilder = MissioneSearchBuilder.getMissioneSearchBuilder()
				.withRangeDataRimborso(new DateTime(2015, 12, 12, 0, 0, DateTimeZone.UTC),null);

		List<Missione> lista = this.missioneDAO.findMissioneByQuery(new Page(0, 10), missioneSearchBuilder);
		Assert.assertTrue("FIND MISSIONE BY DATA RIMBORSO", lista.size() == 1);
	}

	@Test
	public void Q_findAllMissioni() throws Exception {

		MissioneSearchBuilder missioneSearchBuilder = MissioneSearchBuilder.getMissioneSearchBuilder();

		List<Missione> lista = this.missioneDAO.findMissioneByQuery(new Page(0, 10), missioneSearchBuilder);
		Assert.assertTrue("FIND ALL MISSIONI", lista.size() == 3);
	}

	@Test
	public void R_findAllMissioni() throws Exception {

		MissioneSearchBuilder missioneSearchBuilder = MissioneSearchBuilder.getMissioneSearchBuilder()
				.withIdMissione("");
		List<Missione> lista = this.missioneDAO.findMissioneByQuery(new Page(0, 10), missioneSearchBuilder);
		Assert.assertTrue("FIND ALL MISSIONI", lista.size() == 3);
	}

	// @Test
	// public void tearDown() throws Exception {
	// this.missioneDocIndexCreator.deleteIndex();
	// }

	private void creaMissioni() {
		Missione missione = new Missione();
		missione.setId("M_01");
		missione.setOggetto("Conferenza");
		missione.setLocalita("Roma");
		missione.setIdUser("01");
		missione.setMissioneEstera(false);
		missione.setStato(StatoEnum.PRESA_IN_CARICO);
		missione.setFondo("fondo");
		missione.setGAE("GAE");
		missione.setDataInserimento(new DateTime(2015, 11, 13, 0, 0, DateTimeZone.UTC));
		missione.setMezzoProprio(true);
		missione.setDistanza(100.00);
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
		fattura.setNumeroFattura(new Long(134));
		fattura.setData(new DateTime(2015, 11, 12, 13, 0, DateTimeZone.UTC));
		fattura.setImporto(89.8);
		fattura.setTipologiaSpesa("Pernottamento");
		fattura.setValuta("Euro");
		fattura.setId("1111111111111");

		Fattura fattura_2 = new Fattura();
		fattura_2.setNumeroFattura(new Long(135));
		fattura_2.setData(new DateTime(2015, 11, 13, 13, 0, DateTimeZone.UTC));
		fattura_2.setImporto(89.8);
		fattura_2.setTipologiaSpesa("Pernottamento");
		fattura_2.setValuta("Euro");
		fattura_2.setId("2222222222222");

		Rimborso rimborso = new Rimborso();
		rimborso.setNumeroOrdine("56");
		rimborso.setAvvisoPagamento("Via Verdi");
		rimborso.setAnticipazionePagamento(0.0);
		rimborso.setDataRimborso(new DateTime(2015, 12, 12, 13, 14, DateTimeZone.UTC));
		rimborso.setTotale(179.6);

		rimborso.getMappaFattura().put("1111111111111", fattura);
		rimborso.getMappaFattura().put("2222222222222", fattura_2);
		missione.setRimborso(rimborso);

		listaMissioni.add(missione);

		missione = new Missione();
		missione.setId("M_02");
		missione.setOggetto("Conferenza");
		missione.setLocalita("Milano");
		missione.setIdUser("01");
		missione.setStato(StatoEnum.PRESA_IN_CARICO);
		missione.setDataInserimento(new DateTime(2015, 8, 13, 0, 0, DateTimeZone.UTC));
		listaMissioni.add(missione);

		missione = new Missione();
		missione.setId("M_03");
		missione.setOggetto("Riunione");
		missione.setLocalita("Milano");
		missione.setIdUser("02");
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
