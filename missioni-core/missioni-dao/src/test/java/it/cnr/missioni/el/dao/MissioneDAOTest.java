package it.cnr.missioni.el.dao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.elasticsearch.common.geo.GeoPoint;
import org.geosdi.geoplatform.experimental.el.configurator.GPIndexConfigurator;
import org.geosdi.geoplatform.experimental.el.dao.GPElasticSearchDAO;
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

import it.cnr.missioni.el.model.bean.StatisticheMissioni;
import it.cnr.missioni.el.model.search.builder.MissioneSearchBuilder;
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
		List<Missione> lista = this.missioneDAO.findMissioneByQuery(missioneSearchBuilder).getResults();
		Assert.assertTrue("FIND MISSIONE BY USER", lista.size() == 2);

	}

	@Test
	public void C_findMissioneByUser_2Test() throws Exception {
		MissioneSearchBuilder missioneSearchBuilder = MissioneSearchBuilder.getMissioneSearchBuilder().withIdUser("03");

		List<Missione> lista = this.missioneDAO.findMissioneByQuery(missioneSearchBuilder).getResults();
		Assert.assertTrue("FIND MISSIONE BY USER", lista.isEmpty());

	}

	@Test
	public void D_findMissioneByStato_1Test() throws Exception {
		MissioneSearchBuilder missioneSearchBuilder = MissioneSearchBuilder.getMissioneSearchBuilder()
				.withStato(StatoEnum.PRESA_IN_CARICO.name());
		missioneSearchBuilder.setStato(StatoEnum.PRESA_IN_CARICO.name());
		List<Missione> lista = this.missioneDAO.findMissioneByQuery(missioneSearchBuilder).getResults();
		Assert.assertTrue("FIND MISSIONE BY STATO", lista.size() == 2);

	}

	@Test
	public void E_findMissioneByStato_2Test() throws Exception {
		MissioneSearchBuilder missioneSearchBuilder = MissioneSearchBuilder.getMissioneSearchBuilder()
				.withStato(StatoEnum.APPROVATA.name());

		List<Missione> lista = this.missioneDAO.findMissioneByQuery(missioneSearchBuilder).getResults();
		Assert.assertTrue("FIND MISSIONE BY STATO", lista.size() == 1);

	}

	@Test
	public void F_findMissioneByUserStato_1Test() throws Exception {
		MissioneSearchBuilder missioneSearchBuilder = MissioneSearchBuilder.getMissioneSearchBuilder()
				.withStato(StatoEnum.APPROVATA.name()).withIdUser("01");
		List<Missione> lista = this.missioneDAO.findMissioneByQuery(missioneSearchBuilder).getResults();
		Assert.assertTrue("FIND MISSIONE BY STATO", lista.isEmpty());

	}

	@Test
	public void G_findMissioneByUserStato_2Test() throws Exception {
		MissioneSearchBuilder missioneSearchBuilder = MissioneSearchBuilder.getMissioneSearchBuilder()
				.withStato(StatoEnum.PRESA_IN_CARICO.name()).withIdUser("01");
		List<Missione> lista = this.missioneDAO.findMissioneByQuery(missioneSearchBuilder).getResults();
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
				.withRangeDataInserimento(new DateTime(2015, 8, 13, 0, 0, DateTimeZone.UTC),
						new DateTime(2015, 8, 13, 0, 0, DateTimeZone.UTC));
		List<Missione> lista = this.missioneDAO.findMissioneByQuery(missioneSearchBuilder).getResults();
		Assert.assertTrue("FIND MISSIONE BY DATA", lista.size() == 1);
	}

	@Test
	public void M_findMissioniByData_2() throws Exception {

		MissioneSearchBuilder missioneSearchBuilder = MissioneSearchBuilder.getMissioneSearchBuilder()
				.withRangeDataInserimento(new DateTime(2015, 8, 14, 0, 0, DateTimeZone.UTC),
						new DateTime(2015, 8, 31, 0, 0, DateTimeZone.UTC));

		List<Missione> lista = this.missioneDAO.findMissioneByQuery(missioneSearchBuilder).getResults();
		Assert.assertTrue("FIND MISSIONE BY DATA", lista.size() == 0);

	}

	@Test
	public void N_findMissioniByAll() throws Exception {

		MissioneSearchBuilder missioneSearchBuilder = MissioneSearchBuilder.getMissioneSearchBuilder()
				.withRangeDataInserimento(new DateTime(2015, 8, 13, 0, 0, DateTimeZone.UTC),
						new DateTime(2015, 8, 31, 0, 0, DateTimeZone.UTC));

		missioneSearchBuilder.setStato(StatoEnum.PRESA_IN_CARICO.name());
		missioneSearchBuilder.setIdUser("01");

		List<Missione> lista = this.missioneDAO.findMissioneByQuery(missioneSearchBuilder).getResults();
		Assert.assertTrue("FIND MISSIONE BY ALL", lista.size() == 1);

	}

	@Test
	public void O_findMissioniByRimborsoNumeroOrdine() throws Exception {
		MissioneSearchBuilder missioneSearchBuilder = MissioneSearchBuilder.getMissioneSearchBuilder()
				.withNumeroOrdineMissione(new Long(1));
		List<Missione> lista = this.missioneDAO.findMissioneByQuery(missioneSearchBuilder).getResults();
		Assert.assertTrue("FIND MISSIONE BY NUMERO ORDINE RIMBORSO", lista.size() == 1);
	}

	@Test
	public void P_findMissioniByDataRimborso_1() throws Exception {

		MissioneSearchBuilder missioneSearchBuilder = MissioneSearchBuilder.getMissioneSearchBuilder()
				.withRangeDataRimborso(new DateTime(2015, 12, 12, 0, 0, DateTimeZone.UTC), null);

		List<Missione> lista = this.missioneDAO.findMissioneByQuery(missioneSearchBuilder).getResults();
		Assert.assertTrue("FIND MISSIONE BY DATA RIMBORSO", lista.size() == 1);
	}

	@Test
	public void Q_findAllMissioni() throws Exception {

		MissioneSearchBuilder missioneSearchBuilder = MissioneSearchBuilder.getMissioneSearchBuilder();

		List<Missione> lista = this.missioneDAO.findMissioneByQuery(missioneSearchBuilder).getResults();
		Assert.assertTrue("FIND ALL MISSIONI", lista.size() == 3);
	}

	@Test
	public void R_findAllMissioni() throws Exception {

		MissioneSearchBuilder missioneSearchBuilder = MissioneSearchBuilder.getMissioneSearchBuilder()
				.withIdMissione("");
		List<Missione> lista = this.missioneDAO.findMissioneByQuery(missioneSearchBuilder).getResults();
		Assert.assertTrue("FIND ALL MISSIONI", lista.size() == 3);
	}

	@Test
	public void S_findByOggetto() throws Exception {

		MissioneSearchBuilder missioneSearchBuilder = MissioneSearchBuilder.getMissioneSearchBuilder()
				.withOggetto("sviluppo");
		List<Missione> lista = this.missioneDAO.findMissioneByQuery(missioneSearchBuilder).getResults();
		Assert.assertTrue("FIND BY OGGETTO", lista.size() == 1);
	}

	@Test
	public void S_findByOggetto2() throws Exception {

		MissioneSearchBuilder missioneSearchBuilder = MissioneSearchBuilder.getMissioneSearchBuilder()
				.withOggetto("Riunione prova");
		List<Missione> lista = this.missioneDAO.findMissioneByQuery(missioneSearchBuilder).getResults();
		Assert.assertTrue("FIND BY OGGETTO", lista.size() == 2);
	}

	@Test
	public void T_findByMultiMatch() throws Exception {

		MissioneSearchBuilder missioneSearchBuilder = MissioneSearchBuilder.getMissioneSearchBuilder()
				.withMultiMatch("Milano Riunione prova");
		List<Missione> lista = this.missioneDAO.findMissioneByQuery(missioneSearchBuilder).getResults();
		Assert.assertTrue("FIND ALL MISSIONI", lista.size() == 3);
	}

	@Test
	public void U_findByMultiMatch_2() throws Exception {

		MissioneSearchBuilder missioneSearchBuilder = MissioneSearchBuilder.getMissioneSearchBuilder()
				.withMultiMatch("roma conferenza");
		List<Missione> lista = this.missioneDAO.findMissioneByQuery(missioneSearchBuilder).getResults();
		Assert.assertTrue("FIND ALL MISSIONI", lista.size() == 2);
	}

	@Test
	public void V_findByMultiMatch_3() throws Exception {

		MissioneSearchBuilder missioneSearchBuilder = MissioneSearchBuilder.getMissioneSearchBuilder()
				.withMultiMatch("roma sviluppo");
		List<Missione> lista = this.missioneDAO.findMissioneByQuery(missioneSearchBuilder).getResults();
		Assert.assertTrue("FIND ALL MISSIONI", lista.size() == 1);
	}

	@Test
	public void V_findByMultiMatch_4() throws Exception {

		MissioneSearchBuilder missioneSearchBuilder = MissioneSearchBuilder.getMissioneSearchBuilder()
				.withMultiMatch("sviluppo di applicazioni");
		List<Missione> lista = this.missioneDAO.findMissioneByQuery(missioneSearchBuilder).getResults();
		Assert.assertTrue("FIND ALL MISSIONI", lista.size() == 1);
	}

	@Test
	public void V_findByMaxNumeroOrdineMissione() throws Exception {

		Long max = this.missioneDAO.getMaxNumeroOrdineRimborso();
		Assert.assertTrue("FIND ALL MISSIONI", max == 2);
	}

	@Test
	public void V_findNumeroRimboroUser() throws Exception {

		MissioneSearchBuilder missioneSearchBuilder = MissioneSearchBuilder.getMissioneSearchBuilder().withIdUser("01")
				.withFieldExist("missione.rimborso");
		List<Missione> lista = this.missioneDAO.findMissioneByQuery(missioneSearchBuilder).getResults();
		Assert.assertTrue("FIND ALL MISSIONI", lista.size() == 1);
	}

	@Test
	public void V_findNumeroRimboroUser2() throws Exception {

		MissioneSearchBuilder missioneSearchBuilder = MissioneSearchBuilder.getMissioneSearchBuilder().withIdUser("02")
				.withFieldExist("missione.rimborso");
		List<Missione> lista = this.missioneDAO.findMissioneByQuery(missioneSearchBuilder).getResults();
		Assert.assertTrue("FIND ALL MISSIONI", lista.size() == 0);
	}

	@Test
	public void V_findMissioneNoRimborso() throws Exception {

		MissioneSearchBuilder missioneSearchBuilder = MissioneSearchBuilder.getMissioneSearchBuilder()
				.withFieldNotExist("missione.rimborso").withIdUser("01").withStato(StatoEnum.INSERITA.name());
		List<Missione> lista = this.missioneDAO.findMissioneByQuery(missioneSearchBuilder).getResults();
		Assert.assertTrue("FIND  MISSIONI NO RIMBORSO", lista.size() == 0);
	}

	@Test
	public void V_findRimborsoExsist() throws Exception {

		MissioneSearchBuilder missioneSearchBuilder = MissioneSearchBuilder.getMissioneSearchBuilder()
				.withFieldExist("missione.rimborso");
		List<Missione> lista = this.missioneDAO.findMissioneByQuery(missioneSearchBuilder).getResults();
		Assert.assertTrue("FIND  MISSIONI NO RIMBORSO", lista.size() == 1);
	}

	@Test
	public void V_getStatistiche() throws Exception {
		StatisticheMissioni statisticheMissioni = this.missioneDAO.getStatisticheMissioni();
		Assert.assertTrue("FIND STATISTICHE",
				statisticheMissioni.getMappaStatistiche().get(StatoEnum.PRESA_IN_CARICO.getStato()) == 2);
	}

	@Test
	public void V_getMaxNumOrdineMissione() throws Exception {
		long n = this.missioneDAO.getMaxNumeroOrdineRimborso();
		logger.debug("############################MAX_NUM_ORDINE_MISSIONE : {}\n", n);

	}

	@Test
	public void V_findRiByUser() throws Exception {

		MissioneSearchBuilder missioneSearchBuilder = MissioneSearchBuilder.getMissioneSearchBuilder().withIdUser("01");
		List<Missione> lista = this.missioneDAO.findMissioneByQuery(missioneSearchBuilder).getResults();
		Assert.assertTrue("FIND  MISSIONI NO RIMBORSO", lista.size() == 2);
	}

	@Test
	public void Z_tearDown() throws Exception {
		this.missioneDocIndexCreator.deleteIndex();
	}

	private void creaMissioni() {
		Missione missione = new Missione();
		missione.setId("M_01");
		missione.setOggetto("Conferenza prova per lo sviluppo di applicazioni");
		missione.setLocalita("Roma");
		missione.setIdUser("01");
		missione.setMissioneEstera(false);
		missione.setStato(StatoEnum.PRESA_IN_CARICO);
		missione.setFondo("fondo");
		missione.setGAE("GAE");
		missione.setDataInserimento(new DateTime(2015, 11, 13, 0, 0, DateTimeZone.UTC));
		missione.setMezzoProprio(true);
		missione.setTipoVeicolo("Veicolo Proprio");
		missione.setResponsabileGruppo("01");
		missione.setShortResponsabileGruppo("Salvia Vito");
		missione.setIdVeicolo("AA111BB");
		missione.setShortDescriptionVeicolo("Ford Fiesta");
		missione.setGeoPoint(new GeoPoint(41.9027835, 12.4963655));
		missione.setDistanza("353 Km");
		missione.setShortUser("Salvia Vito");
		DatiPeriodoMissione datiPeriodoMissione = new DatiPeriodoMissione();
		datiPeriodoMissione.setInizioMissione(new DateTime(2015, 11, 11, 0, 0, DateTimeZone.UTC));
		datiPeriodoMissione.setFineMissione(new DateTime(2015, 11, 15, 0, 0, DateTimeZone.UTC));
		missione.setDatiPeriodoMissione(datiPeriodoMissione);

		Fattura fattura = new Fattura();
		fattura.setNumeroFattura(new Long(134));
		fattura.setData(new DateTime(2015, 11, 12, 13, 0, DateTimeZone.UTC));
		fattura.setImporto(89.8);
		fattura.setValuta("Euro");
		fattura.setIdTipologiaSpesa("01");
		fattura.setShortDescriptionTipologiaSpesa("Vitto");
		fattura.setId("1111111111111");

		Fattura fattura_2 = new Fattura();
		fattura_2.setNumeroFattura(new Long(135));
		fattura_2.setData(new DateTime(2015, 11, 13, 13, 0, DateTimeZone.UTC));
		fattura_2.setImporto(89.8);
		fattura_2.setValuta("Euro");
		fattura_2.setIdTipologiaSpesa("02");
		fattura_2.setShortDescriptionTipologiaSpesa("Albergo");
		fattura_2.setId("2222222222222");

		Rimborso rimborso = new Rimborso();
		rimborso.setNumeroOrdine(new Long(1));
		rimborso.setAvvisoPagamento("Via Verdi");
		rimborso.setAnticipazionePagamento(0.0);
		rimborso.setDataRimborso(new DateTime(2015, 12, 12, 13, 14, DateTimeZone.UTC));
		rimborso.setTotale(179.6);
		rimborso.setTotaleDovuto(179.6);

		rimborso.getMappaFattura().put("1111111111111", fattura);
		rimborso.getMappaFattura().put("2222222222222", fattura_2);
		missione.setRimborso(rimborso);

		listaMissioni.add(missione);

		missione = new Missione();
		missione.setId("M_02");
		missione.getDatiPeriodoMissione().setInizioMissione(new DateTime(2015, 02, 11, 13, 14, DateTimeZone.UTC));
		missione.getDatiPeriodoMissione().setFineMissione(new DateTime(2015, 02, 15, 13, 14, DateTimeZone.UTC));

		missione.setOggetto("Conferenza");
		missione.setLocalita("Milano");
		missione.setIdUser("01");
		missione.setShortUser("Salvia Vito");
		missione.setResponsabileGruppo("01");
		missione.setShortResponsabileGruppo("Salvia Vito");
		missione.setStato(StatoEnum.PRESA_IN_CARICO);
		missione.setDataInserimento(new DateTime(2015, 8, 13, 0, 0, DateTimeZone.UTC));
		missione.setGeoPoint(new GeoPoint(45.4654219, 9.1859243));
		missione.setDistanza("901 Km");
		missione.setTipoVeicolo("Veicolo Proprio");
		missione.setMezzoProprio(true);
		listaMissioni.add(missione);

		missione = new Missione();
		missione.setId("M_03");
		missione.setOggetto("Riunione prova");
		missione.setLocalita("Milano");
		missione.getDatiPeriodoMissione().setInizioMissione(new DateTime(2015, 02, 11, 13, 14, DateTimeZone.UTC));
		missione.getDatiPeriodoMissione().setFineMissione(new DateTime(2015, 02, 15, 13, 14, DateTimeZone.UTC));
		missione.setIdUser("02");
		missione.setShortUser("Franco Luigi");
		missione.setResponsabileGruppo("01");
		missione.setShortResponsabileGruppo("Salvia Vito");
		missione.setStato(StatoEnum.APPROVATA);
		missione.setDataInserimento(new DateTime(2015, 11, 23, 0, 0, DateTimeZone.UTC));
		missione.setGeoPoint(new GeoPoint(45.4654219, 9.1859243));
		missione.setDistanza("901 Km");
		missione.setTipoVeicolo("Veicolo Proprio");
		missione.setMezzoProprio(true);
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
