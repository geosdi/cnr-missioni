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

import java.util.List;

import javax.annotation.Resource;

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
import it.cnr.missioni.el.model.search.builder.MassimaleSearchBuilder;
import it.cnr.missioni.el.model.search.builder.NazioneSearchBuilder;
import it.cnr.missioni.model.configuration.Massimale;
import it.cnr.missioni.model.configuration.Nazione;
import it.cnr.missioni.model.configuration.Nazione.AreaGeograficaEnum;
import it.cnr.missioni.model.missione.TrattamentoMissioneEsteraEnum;
import it.cnr.missioni.model.user.DatiCNR.LivelloUserEnum;
import it.cnr.missioni.rest.api.response.massimale.MassimaleStore;
import it.cnr.missioni.rest.api.response.nazione.NazioneStore;

/**
 * 
 * @author Salvia Vito
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:applicationContext.xml" })
@FixMethodOrder(value = MethodSorters.NAME_ASCENDING)
public class MassimaleRestServiceTest {

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
	public void A_addMassimaleTest() throws Exception {
		Massimale massimale = new Massimale();
		massimale.setValue(new Double(130));
		massimale.setId("12");
		massimale.setLivello(LivelloUserEnum.I);
		massimale.setAreaGeografica(AreaGeograficaEnum.E);
		missioniCoreClientConnector.addMassimale(massimale);
		Thread.sleep(1000);
		MassimaleSearchBuilder massimaleSearchBuilder = MassimaleSearchBuilder.getMassimaleSearchBuilder();
		MassimaleStore massimaleStore = missioniCoreClientConnector.getMassimaleByQuery(massimaleSearchBuilder);
		Assert.assertTrue("ADD MASSIMALE", massimaleStore.getTotale() == 12);
	}
	
	@Test
	public void B_updateMassimaleTest() throws Exception {
		Massimale massimale = new Massimale();
		massimale.setValue(new Double(130));
		massimale.setId("12");
		massimale.setLivello(LivelloUserEnum.VII);
		massimale.setAreaGeografica(AreaGeograficaEnum.E);
		missioniCoreClientConnector.updateMassimale(massimale);
		Thread.sleep(1000);
		MassimaleSearchBuilder massimaleSearchBuilder = MassimaleSearchBuilder.getMassimaleSearchBuilder();
		MassimaleStore massimaleStore = missioniCoreClientConnector.getMassimaleByQuery(massimaleSearchBuilder);
		Assert.assertTrue("UPDATE MASSIMALE", massimaleStore.getTotale() == 12);

	}
	
	@Test
	public void C_deleteMassimaleTest() throws Exception {
		missioniCoreClientConnector.deleteMassimale("12");
		Thread.sleep(1000);
		MassimaleSearchBuilder massimaleSearchBuilder = MassimaleSearchBuilder.getMassimaleSearchBuilder();
		MassimaleStore massimaleStore = missioniCoreClientConnector.getMassimaleByQuery(massimaleSearchBuilder);
		Assert.assertTrue("DELETE MASSIMALE", massimaleStore.getTotale() == 11);

	}

	

//	@Test
//	public void F_findByIdTest() throws Exception {
//		MassimaleSearchBuilder massimaleSearchBuilder = MassimaleSearchBuilder.getMassimaleSearchBuilder().withId("02");
//		MassimaleStore massimaleStore = missioniCoreClientConnector.getMassimaleByQuery(massimaleSearchBuilder);
//		logger.debug("############################FIND_MASSIMALE_BY_ID: {}\n", massimaleStore.getTotale());
//		Assert.assertTrue("FIND MASSIMALE BY ID", massimaleStore.getTotale() == 1);
//		Assert.assertTrue("FIND  MASSIMALE BY ID", massimaleStore.getMassimale().get(0).getId().equals("02"));
//	}
//
//	@Test
//	public void F_findByIdTest_2() throws Exception {
//		MassimaleSearchBuilder massimaleSearchBuilder = MassimaleSearchBuilder.getMassimaleSearchBuilder().withId("13");
//		MassimaleStore massimaleStore = missioniCoreClientConnector.getMassimaleByQuery(massimaleSearchBuilder);
//		Assert.assertNull("FIND MASSIMALE BY ID", massimaleStore);
//	}

	@Test
	public void G_findByLivelloAreaGeograficaTest() throws Exception {
		MassimaleSearchBuilder massimaleSearchBuilder = MassimaleSearchBuilder.getMassimaleSearchBuilder()
				.withLivello(LivelloUserEnum.I.name()).withAreaGeografica(AreaGeograficaEnum.A.name())
				.withTipo(TrattamentoMissioneEsteraEnum.TRATTAMENTO_ALTERNATIVO.name());
		MassimaleStore massimaleStore = missioniCoreClientConnector.getMassimaleByQuery(massimaleSearchBuilder);
		Assert.assertTrue("FIND MASSIMALE BY LIVELLO-AREA", massimaleStore.getTotale() == 1);
	}

	@Test
	public void H_findByLivelloAreaGeograficaTest_2() throws Exception {
		MassimaleSearchBuilder massimaleSearchBuilder = MassimaleSearchBuilder.getMassimaleSearchBuilder()
				.withLivello(LivelloUserEnum.IV.name()).withAreaGeografica(AreaGeograficaEnum.A.name())
				.withTipo(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO.name());
		MassimaleStore massimaleStore = missioniCoreClientConnector.getMassimaleByQuery(massimaleSearchBuilder);
		Assert.assertTrue("FIND MASSIMALE BY LIVELLO-AREA", massimaleStore.getTotale() == 1);
	}

	@Test
	public void I_findByLivelloAreaGeograficaTest_3() throws Exception {
		MassimaleSearchBuilder massimaleSearchBuilder = MassimaleSearchBuilder.getMassimaleSearchBuilder()
				.withLivello(LivelloUserEnum.IV.name()).withAreaGeografica(AreaGeograficaEnum.C.name())
				.withTipo(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO.name());
		MassimaleStore massimaleStore = missioniCoreClientConnector.getMassimaleByQuery(massimaleSearchBuilder);
		Assert.assertNull("FIND MASSIMALE BY LIVELLO-AREA", massimaleStore);
	}

	@Test
	public void L_findByIdNotTest() throws Exception {
		MassimaleSearchBuilder massimaleSearchBuilder = MassimaleSearchBuilder.getMassimaleSearchBuilder()
				.withNotId("10").withLivello(LivelloUserEnum.IV.name()).withAreaGeografica(AreaGeograficaEnum.A.name())
				.withTipo(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO.name());
		MassimaleStore massimaleStore = missioniCoreClientConnector.getMassimaleByQuery(massimaleSearchBuilder);
		Assert.assertNull("FIND MASSIMALE BY LIVELLO-AREA", massimaleStore);
	}

	@Test
	public void L_findByIdNotTest_2() throws Exception {
		MassimaleSearchBuilder massimaleSearchBuilder = MassimaleSearchBuilder.getMassimaleSearchBuilder()
				.withNotId("01").withLivello(LivelloUserEnum.I.name()).withAreaGeografica(AreaGeograficaEnum.A.name())
				.withTipo(TrattamentoMissioneEsteraEnum.TRATTAMENTO_ALTERNATIVO.name());
		MassimaleStore massimaleStore = missioniCoreClientConnector.getMassimaleByQuery(massimaleSearchBuilder);
		Assert.assertNull("FIND MASSIMALE BY LIVELLO-AREA", massimaleStore);
	}

	
}
