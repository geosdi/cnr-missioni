package it.cnr.missioni.el.dao;

import com.google.common.collect.Lists;

import it.cnr.missioni.el.model.search.MissioneModelSearch;
import it.cnr.missioni.model.missione.DatiAnticipoPagamenti;
import it.cnr.missioni.model.missione.DatiMissioneEstera;
import it.cnr.missioni.model.missione.DatiPeriodoMissione;
import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.model.missione.StatoEnum;
import it.cnr.missioni.model.rimborso.Fattura;
import it.cnr.missioni.model.rimborso.Rimborso;

import org.elasticsearch.action.bulk.BulkResponse;
import org.geosdi.geoplatform.experimental.el.configurator.GPIndexConfigurator;
import org.geosdi.geoplatform.experimental.el.dao.GPElasticSearchDAO;
import org.geosdi.geoplatform.experimental.el.dao.GPElasticSearchDAO.Page;
import org.geosdi.geoplatform.experimental.el.index.GPIndexCreator;
import org.geosdi.geoplatform.logger.support.annotation.GeoPlatformLog;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Salvia Vito
 */
@FixMethodOrder(value = MethodSorters.NAME_ASCENDING)
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext-DAO-Test.xml"})
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
    public void B_findMissioneByUtente_1Test() throws Exception { 	
    	MissioneModelSearch missioneModelSearch = new MissioneModelSearch(null, null, "01", null);  	
        List<Missione> lista = this.missioneDAO.findMissioneByUtenteStato(new Page(0,10),missioneModelSearch);
        Assert.assertTrue("FIND MISSIONE BY UTENTE", lista.size() == 2);

    }

    @Test
    public void C_findMissioneByUtente_2Test() throws Exception {
    	MissioneModelSearch missioneModelSearch = new MissioneModelSearch(null, null, "03", null); 
        List<Missione> lista = this.missioneDAO.findMissioneByUtenteStato(new Page(0,10),missioneModelSearch);
        Assert.assertTrue("FIND MISSIONE BY UTENTE", lista.isEmpty());

    }

    @Test
    public void D_findMissioneByStato_1Test() throws Exception {
    	MissioneModelSearch missioneModelSearch = new MissioneModelSearch(null, null, null, StatoEnum.PRESA_IN_CARICO.name()); 
        List<Missione> lista = this.missioneDAO.findMissioneByUtenteStato(new Page(0,10),missioneModelSearch);
        Assert.assertTrue("FIND MISSIONE BY STATO", lista.size() == 2);

    }

    @Test
    public void E_findMissioneByStato_2Test() throws Exception {
    	MissioneModelSearch missioneModelSearch = new MissioneModelSearch(null, null, null, StatoEnum.APPROVATA.name()); 
    	List<Missione> lista = this.missioneDAO.findMissioneByUtenteStato(new Page(0,10),missioneModelSearch);
    	Assert.assertTrue("FIND MISSIONE BY STATO", lista.size() == 1);

    }

    @Test
    public void F_findMissioneByUtenteStato_1Test() throws Exception {
    	MissioneModelSearch missioneModelSearch = new MissioneModelSearch(null, null, "01", StatoEnum.APPROVATA.name()); 
        List<Missione> lista = this.missioneDAO.findMissioneByUtenteStato(new Page(0,10),missioneModelSearch);
        Assert.assertTrue("FIND MISSIONE BY STATO", lista.isEmpty());

    }

    @Test
    public void G_findMissioneByUtenteStato_2Test() throws Exception {
    	MissioneModelSearch missioneModelSearch = new MissioneModelSearch(null, null, "01", StatoEnum.PRESA_IN_CARICO.name()); 
        List<Missione> lista = this.missioneDAO.findMissioneByUtenteStato(new Page(0,10),missioneModelSearch);
        Assert.assertTrue("FIND MISSIONE BY STATO", lista.size() == 2);
    }

    @Test
    public void H_insertMassiveMissioniTest() throws Exception {
        BulkResponse bulkResponse = this.missioneDAO.persist(createMassiveMissioni());
        logger.info("#####################MASSIVE_MISSIONI_INSERT_TIME : {}\n",
                bulkResponse.getTook().toString());
    }

    @Test
    public void I_findMissioniWithPageTest() throws Exception {
        GPElasticSearchDAO.IPageResult<Missione> pageResult = this.missioneDAO
                .find(new GPElasticSearchDAO.Page(0, 3000));
        logger.info("########################MISSIONI_FOUND : {}\n", pageResult.getTotal());
    }
    
    @Test
    public void L_removeMissioneTest() throws Exception {
    	missioneDAO.delete("M_01");
    	Thread.sleep(1000);
        logger.debug("############################NUMBER_OF_MISSIONI : {}\n", this.missioneDAO.count().intValue());
    }
    
    @Test
    public void L_findMissioniByData_1() throws Exception {
    	MissioneModelSearch missioneModelSearch = new MissioneModelSearch(new DateTime(2015,8,13,0,0), new DateTime(2015,8,13,0,0), null,null); 
        List<Missione> lista = this.missioneDAO.findMissioneByUtenteStato(new Page(0,10),missioneModelSearch);
        Assert.assertTrue("FIND MISSIONE BY DATA", lista.size() == 1);
    }
    
    @Test
    public void M_findMissioniByData_2() throws Exception {
    	MissioneModelSearch missioneModelSearch = new MissioneModelSearch(new DateTime(2015,8,14,0,0), new DateTime(2015,8,31,0,0), null,null); 
        List<Missione> lista = this.missioneDAO.findMissioneByUtenteStato(new Page(0,10),missioneModelSearch);
        Assert.assertTrue("FIND MISSIONE BY DATA", lista.size() == 0);
    }
    
    @Test
    public void N_findMissioniByAll() throws Exception {
    	MissioneModelSearch missioneModelSearch = new MissioneModelSearch(new DateTime(2015,8,1,0,0), new DateTime(2015,8,31,0,0), "01",StatoEnum.PRESA_IN_CARICO.name()); 
        List<Missione> lista = this.missioneDAO.findMissioneByUtenteStato(new Page(0,10),missioneModelSearch);
        Assert.assertTrue("FIND MISSIONE BY ALL", lista.size() == 1);
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
        missione.setDataInserimento(new DateTime(2015,11,13,0,0));
        DatiAnticipoPagamenti dati = new DatiAnticipoPagamenti();
        dati.setAnticipazioniMonetarie(true);
        dati.setMandatoCNR("AA11");
        dati.setRimborsoDaTerzi(false);
        dati.setSpeseMissioniAnticipate(102.00);
        missione.setDatiAnticipoPagamenti(dati);
        DatiPeriodoMissione datiPeriodoMissione = new DatiPeriodoMissione();
        datiPeriodoMissione.setInizioMissione(new DateTime(2015,11,11,0,0));
        datiPeriodoMissione.setFineMissione(new DateTime(2015,11,15,0,0));
        missione.setDatiPeriodoMissione(datiPeriodoMissione);
        
        Fattura fattura = new Fattura();
        fattura.setNumeroFattura(134);
        fattura.setData(new DateTime(2015,11,12,13,0));
        fattura.setImporto(89.8);
        fattura.setTipologiaSpesa("Pernottamento");
        fattura.setValuta("Euro");
        
        Fattura fattura_2 = new Fattura();
        fattura_2.setNumeroFattura(135);
        fattura_2.setData(new DateTime(2015,11,13,13,0));
        fattura_2.setImporto(89.8);
        fattura_2.setTipologiaSpesa("Pernottamento");
        fattura_2.setValuta("Euro");
        
        Rimborso rimborso = new Rimborso();
        rimborso.setNumeroOrdine(156);
        rimborso.setAvvisoPagamento("Via Verdi");
        rimborso.setAnticipazionePagamento(0.0);
        rimborso.setDataRimborso(new DateTime(2015,12,12,13,14));
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
        missione.setDataInserimento(new DateTime(2015,8,13,0,0));
        listaMissioni.add(missione);

        missione = new Missione();
        missione.setId("M_03");
        missione.setOggetto("Riunione");
        missione.setLocalita("Milano");
        missione.setIdUtente("02");
        missione.setStato(StatoEnum.APPROVATA);
        missione.setDataInserimento(new DateTime(2015,11,23,0,0));
        listaMissioni.add(missione);
    }

    List<Missione> createMassiveMissioni() {
        List<Missione> missioni = Lists.newArrayList();
        for (int i = 0; i < 8000; i++) {
            Missione missione = new Missione();
            missione.setId(UUID.randomUUID().toString());
            missione.setOggetto("OGGETTO-TEST - " + i);
            missione.setLocalita("LOCALITAA' - " + i);
            missione.setIdUtente(UUID.randomUUID().toString());
            if ((i % 2) == 0) {
                missione.setStato(StatoEnum.APPROVATA);
            } else {
                missione.setStato(StatoEnum.PRESA_IN_CARICO);
            }
            missioni.add(missione);
        }
        return missioni;
    }
}
