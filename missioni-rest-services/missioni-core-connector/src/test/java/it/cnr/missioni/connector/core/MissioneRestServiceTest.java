/*
 *  geo-platform
 *  Rich webgis framework
 *  http://geo-platform.org
 * ====================================================================
 *
 * Copyright (C) 2008-2014 geoSDI Group (CNR IMAA - Potenza - ITALY).
 *
 * This program is free software: you can redistribute it and/or modify it 
 * under the terms of the GNU General Public License as published by 
 * the Free Software Foundation, either version 3 of the License, or 
 * (at your option) any later version. This program is distributed in the 
 * hope that it will be useful, but WITHOUT ANY WARRANTY; without 
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR 
 * A PARTICULAR PURPOSE. See the GNU General Public License 
 * for more details. You should have received a copy of the GNU General 
 * Public License along with this program. If not, see http://www.gnu.org/licenses/ 
 *
 * ====================================================================
 *
 * Linking this library statically or dynamically with other modules is 
 * making a combined work based on this library. Thus, the terms and 
 * conditions of the GNU General Public License cover the whole combination. 
 * 
 * As a special exception, the copyright holders of this library give you permission 
 * to link this library with independent modules to produce an executable, regardless 
 * of the license terms of these independent modules, and to copy and distribute 
 * the resulting executable under terms of your choice, provided that you also meet, 
 * for each linked independent module, the terms and conditions of the license of 
 * that module. An independent module is a module which is not derived from or 
 * based on this library. If you modify this library, you may extend this exception 
 * to your version of the library, but you are not obligated to do so. If you do not 
 * wish to do so, delete this exception statement from your version. 
 *
 */
package it.cnr.missioni.connector.core;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

import javax.annotation.Resource;
import javax.ws.rs.core.Response;

import org.apache.commons.io.IOUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import it.cnr.missioni.connector.core.spring.connector.MissioniCoreClientConnector;
import it.cnr.missioni.el.model.bean.StatisticheMissioni;
import it.cnr.missioni.el.model.search.builder.MissioneSearchBuilder;
import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.model.rimborso.Rimborso;
import it.cnr.missioni.rest.api.response.geocoder.GeocoderStore;
import it.cnr.missioni.rest.api.response.missione.MissioniStore;
import it.cnr.missioni.rest.api.response.missione.distance.DistanceResponse;

/**
 *
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:applicationContext.xml" })
@FixMethodOrder(value = MethodSorters.NAME_ASCENDING)
public class MissioneRestServiceTest {

	static final String CORE_CONNECTOR_KEY = "MISSIONI_CORE_FILE_PROP";
	//
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Resource(name = "missioniCoreClientConnector")
	protected MissioniCoreClientConnector missioniCoreClientConnector;

	@BeforeClass
	public static void beforeClass() {
		System.setProperty(CORE_CONNECTOR_KEY, "missioni-core-test.prop");
	}

	@Before
	public void setUp() throws Exception {
		logger.debug("\n\n\t@@@@@@@ {}.setUp @@@@@@\n\n", this.getClass().getSimpleName());

	}

	// @After
	// public void tearDown() {
	// logger.debug("\n\t@@@ {}.tearDown @@@", this.getClass().getSimpleName());
	// // Delete Organization
	// this.deleteOrganization(organizationTest.getId());
	// }

	@AfterClass
	public static void afterClass() {
		System.clearProperty(CORE_CONNECTOR_KEY);
	}

	@Test
	public void A_testFindMissione() throws Exception {

		MissioneSearchBuilder missioneSearchBuilder = MissioneSearchBuilder.getMissioneSearchBuilder()
				.withIdMissione("M_01");
		MissioniStore missioniStore = missioniCoreClientConnector.getMissioneByQuery(missioneSearchBuilder);
		Assert.assertNotNull(missioniStore);
	}

	@Test
	public void B_testFindMissioneByUser() throws Exception {
		MissioneSearchBuilder missioneSearchBuilder = MissioneSearchBuilder.getMissioneSearchBuilder()
				.withIdMissione("03");

		MissioniStore missioniStore = missioniCoreClientConnector.getMissioneByQuery(missioneSearchBuilder);
		Assert.assertTrue(missioniStore.getTotale() == 0);
	}

	@Test
	public void C_testInsertMissione() throws Exception {

		Missione missione = new Missione();
		missione.setId("M_04");
		missione.setIdUser("01");
		missione.setLocalita("Roma");
		missioniCoreClientConnector.addMissione(missione);
		Thread.sleep(1000);
		MissioneSearchBuilder missioneSearchBuilder = MissioneSearchBuilder.getMissioneSearchBuilder().withIdUser("04");

		MissioniStore missioniStore = missioniCoreClientConnector.getMissioneByQuery(missioneSearchBuilder);
		logger.debug("############################INSERT MISSIONE\n");
	}

	@Test
	public void D_testUpdateMissione() throws Exception {

		Missione missione_update = new Missione();
		missione_update.setId("M_04");
		missione_update.setLocalita("Napoli");
		missione_update.setIdUser("01");
		Rimborso r = new Rimborso();
		missione_update.setRimborso(r);

		missioniCoreClientConnector.updateMissione(missione_update);
		Thread.sleep(1000);
		MissioneSearchBuilder missioneSearchBuilder = MissioneSearchBuilder.getMissioneSearchBuilder()
				.withIdMissione("M_04");

		missioneSearchBuilder.setIdMissione("M_04");
		MissioniStore missioniStore = missioniCoreClientConnector.getMissioneByQuery(missioneSearchBuilder);
		logger.debug("############################NUMERO ORDINE MISSIONE\n"
				+ missioniStore.getMissioni().get(0).getRimborso().getNumeroOrdine());
		Assert.assertTrue("Update Missione",
				missioniStore.getMissioni().get(0).getLocalita().equals(missione_update.getLocalita()));
	}

	@Test
	public void E_testDeleteMissione() throws Exception {
		missioniCoreClientConnector.deleteMissione("M_04");
		logger.debug("############################DELETE MISSIONE\n");
	}

	@Test
	public void F_testFindMissioneByRimborso() throws Exception {

		MissioneSearchBuilder missioneSearchBuilder = MissioneSearchBuilder.getMissioneSearchBuilder()
				.withRangeDataRimborso(new DateTime(2015, 12, 12, 0, 0, DateTimeZone.UTC), null);
		MissioniStore missioniStore = missioniCoreClientConnector.getMissioneByQuery(missioneSearchBuilder);
		Assert.assertTrue("FIND MISSIONE BY DATA RIMBORSO", missioniStore.getMissioni().size() == 1);
	}

	@Test
	public void G_testFindMissioneByNumeroRimborso() throws Exception {

		MissioneSearchBuilder missioneSearchBuilder = MissioneSearchBuilder.getMissioneSearchBuilder()
				.withNumeroOrdineMissione(new Long(1));
		MissioniStore missioniStore = missioniCoreClientConnector.getMissioneByQuery(missioneSearchBuilder);
		Assert.assertTrue("FIND MISSIONE BY NUMERO ORDINE RIMBORSO", missioniStore.getMissioni().size() == 1);
	}

	@Test
	public void H_testFindMissioneByData() throws Exception {

		MissioneSearchBuilder missioneSearchBuilder = MissioneSearchBuilder.getMissioneSearchBuilder()
				.withRangeDataInserimento(new DateTime(2015, 8, 13, 0, 0, DateTimeZone.UTC),
						new DateTime(2015, 8, 13, 0, 0, DateTimeZone.UTC));
		MissioniStore missioniStore = missioniCoreClientConnector.getMissioneByQuery(missioneSearchBuilder);
		Assert.assertTrue("FIND MISSIONE BY NUMERO ORDINE RIMBORSO", missioniStore.getMissioni().size() == 1);
	}

	@Test
	public void T_findByMultiMatch() throws Exception {

		MissioneSearchBuilder missioneSearchBuilder = MissioneSearchBuilder.getMissioneSearchBuilder()
				.withMultiMatch("Milano, Riunione prova");
		MissioniStore missioniStore = missioniCoreClientConnector.getMissioneByQuery(missioneSearchBuilder);
		Assert.assertTrue("FIND ALL MISSIONI", missioniStore.getMissioni().size() == 3);
	}

	@Test
	public void U_findByMultiMatch_2() throws Exception {

		MissioneSearchBuilder missioneSearchBuilder = MissioneSearchBuilder.getMissioneSearchBuilder()
				.withMultiMatch("roma conferenza");
		MissioniStore missioniStore = missioniCoreClientConnector.getMissioneByQuery(missioneSearchBuilder);
		Assert.assertTrue("FIND ALL MISSIONI", missioniStore.getMissioni().size() == 2);
	}

	@Test
	public void V_findByMultiMatch_3() throws Exception {

		MissioneSearchBuilder missioneSearchBuilder = MissioneSearchBuilder.getMissioneSearchBuilder()
				.withMultiMatch("roma sviluppo");
		MissioniStore missioniStore = missioniCoreClientConnector.getMissioneByQuery(missioneSearchBuilder);
		Assert.assertTrue("FIND ALL MISSIONI", missioniStore.getMissioni().size() == 1);
	}

	@Test
	public void V_findByMultiMatch_4() throws Exception {

		MissioneSearchBuilder missioneSearchBuilder = MissioneSearchBuilder.getMissioneSearchBuilder()
				.withMultiMatch("sviluppo di applicazioni");
		MissioniStore missioniStore = missioniCoreClientConnector.getMissioneByQuery(missioneSearchBuilder);
		Assert.assertTrue("FIND ALL MISSIONI", missioniStore.getMissioni().size() == 1);
	}

	@Test
	public void N_findByOggetto() throws Exception {

		MissioneSearchBuilder missioneSearchBuilder = MissioneSearchBuilder.getMissioneSearchBuilder()
				.withOggetto("conferenza");
		MissioniStore missioniStore = missioniCoreClientConnector.getMissioneByQuery(missioneSearchBuilder);
		Assert.assertTrue("FIND ALL MISSIONI", missioniStore.getMissioni().size() == 2);
	}

	@Test
	public void O_findByOggetto2() throws Exception {

		MissioneSearchBuilder missioneSearchBuilder = MissioneSearchBuilder.getMissioneSearchBuilder()
				.withOggetto("riunione prova");
		MissioniStore missioniStore = missioniCoreClientConnector.getMissioneByQuery(missioneSearchBuilder);
		Assert.assertTrue("FIND ALL MISSIONI", missioniStore.getMissioni().size() == 2);
	}

	@Test
	public void V_findNumeroRimboroUser() throws Exception {

		MissioneSearchBuilder missioneSearchBuilder = MissioneSearchBuilder.getMissioneSearchBuilder().withIdUser("01")
				.withFieldExist("missione.rimborso");
		MissioniStore missioniStore = missioniCoreClientConnector.getMissioneByQuery(missioneSearchBuilder);
		Assert.assertTrue("FIND ALL MISSIONI", missioniStore.getMissioni().size() == 1);
	}

	@Test
	public void V_findNumeroRimboroUser2() throws Exception {

		MissioneSearchBuilder missioneSearchBuilder = MissioneSearchBuilder.getMissioneSearchBuilder().withIdUser("02")
				.withFieldExist("missione.rimborso");
		MissioniStore missioniStore = missioniCoreClientConnector.getMissioneByQuery(missioneSearchBuilder);
		Assert.assertTrue(missioniStore.getTotale() == 0);
	}

	@Test
	public void Z_createMissionePDFFile() throws Exception {

		Response r = missioniCoreClientConnector.downloadMissioneAsPdf("M_01");

		InputStream is = r.readEntity(InputStream.class);
		File downloadfile = new File("./target/missione.pdf");
		byte[] byteArray = IOUtils.toByteArray(is);
		FileOutputStream fos = new FileOutputStream(downloadfile);
		fos.write(byteArray);
		fos.flush();
		fos.close();

	}

	@Test
	public void Z_getDistanceForMissione() throws Exception {

		DistanceResponse.MissioneDistanceResponse distanceResponce = missioniCoreClientConnector.getDistanceForMissione("Tito Scalo", "Madrid");

		logger.info("#####################DISTANCE FOR MISSIONE : {}\n", distanceResponce.getDistance());

	}
	
	
	@Test
	public void Z_getDistanceForMissione_2() throws Exception {

		DistanceResponse.MissioneDistanceResponse distanceResponce = missioniCoreClientConnector.getDistanceForMissione("Tito Scalo", "New York");

		logger.info("#####################DISTANCE FOR MISSIONE : {}\n", distanceResponce.getDistance());

	}

	@Test
	public void getGeocoderStoreForMissioneLocation() throws Exception {

		GeocoderStore geocoderStore = missioniCoreClientConnector.getGeocoderStoreForMissioneLocation("Roma");
		logger.info("#####################GEOCODER FOR MISSIONE : {}\n", geocoderStore);
	}

	@Test
	public void Z_findMissioneNoRimborso() throws Exception {

		MissioneSearchBuilder missioneSearchBuilder = MissioneSearchBuilder.getMissioneSearchBuilder()
				.withFieldNotExist("missione.rimborso");
		MissioniStore missioniStore = missioniCoreClientConnector.getMissioneByQuery(missioneSearchBuilder);
		Assert.assertTrue("FIND ALL MISSIONI",missioniStore.getMissioni().size() == 2);
	}
	
	@Test
	public void getStatistiche() throws Exception {

		StatisticheMissioni statisticheMissioni = missioniCoreClientConnector.getStatistiche();
	}
	
	@Test
	public void V_findRimborsoExsist() throws Exception {

		MissioneSearchBuilder missioneSearchBuilder = MissioneSearchBuilder.getMissioneSearchBuilder()
				.withFieldExist("missione.rimborso");
		MissioniStore missioniStore = missioniCoreClientConnector.getMissioneByQuery(missioneSearchBuilder);
		Assert.assertTrue("FIND  MISSIONI NO RIMBORSO", missioniStore.getTotale() == 1);
	}
	
}
