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
import it.cnr.missioni.el.model.search.builder.TipologiaSpesaSearchBuilder;
import it.cnr.missioni.model.configuration.TipologiaSpesa;
import it.cnr.missioni.model.missione.TrattamentoMissioneEsteraEnum;
import it.cnr.missioni.rest.api.response.tipologiaSpesa.TipologiaSpesaStore;

/**
 * 
 * @author Salvia Vito
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:applicationContext.xml" })
@FixMethodOrder(value = MethodSorters.NAME_ASCENDING)
public class TipologiaSpesaRestServiceTest {

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
	public void A_addTipologiaSpesaTest() throws Exception {
		TipologiaSpesa tipologiaSpesa = new TipologiaSpesa();
		tipologiaSpesa.setId("05");
		tipologiaSpesa.setValue("VITTO");
		missioniCoreClientConnector.addTipologiaSpesa(tipologiaSpesa);
		Thread.sleep(1000);
		TipologiaSpesaSearchBuilder tipologiaSpesaSearchBuilder = TipologiaSpesaSearchBuilder.getTipologiaSpesaSearchBuilder();
		TipologiaSpesaStore tipologiaSpesaStore = missioniCoreClientConnector.getTipologiaSpesaByQuery(tipologiaSpesaSearchBuilder);
		Assert.assertTrue("ADD TIPOLOGIA SPESA", tipologiaSpesaStore.getTotale() == 5);
	}
	
	@Test
	public void B_updateTipologiaSpesaTest() throws Exception {
		TipologiaSpesa tipologiaSpesa = new TipologiaSpesa();
		tipologiaSpesa.setId("05");
		tipologiaSpesa.setValue("PRANZO");
		missioniCoreClientConnector.updateTipologiaSpesa(tipologiaSpesa);
		Thread.sleep(1000);
		TipologiaSpesaSearchBuilder tipologiaSpesaSearchBuilder = TipologiaSpesaSearchBuilder.getTipologiaSpesaSearchBuilder();
		TipologiaSpesaStore tipologiaSpesaStore = missioniCoreClientConnector.getTipologiaSpesaByQuery(tipologiaSpesaSearchBuilder);
		Assert.assertTrue("UPDATE TIPOLOGIA SPESA", tipologiaSpesaStore.getTotale() == 5);

	}
	
	@Test
	public void C_deleteTipologiaSpesaTest() throws Exception {
		missioniCoreClientConnector.deleteTipologiaSpesa("05");
		Thread.sleep(1000);
		TipologiaSpesaSearchBuilder tipologiaSpesaSearchBuilder = TipologiaSpesaSearchBuilder.getTipologiaSpesaSearchBuilder();
		TipologiaSpesaStore tipologiaSpesaStore = missioniCoreClientConnector.getTipologiaSpesaByQuery(tipologiaSpesaSearchBuilder);
		Assert.assertTrue("DELETE TIPOLOGIA SPESA", tipologiaSpesaStore.getTotale() == 4);

	}
	
	@Test
	public void F_findTipologiaSpesaTipoTest() throws Exception {
		TipologiaSpesaSearchBuilder tipologiaSpesaSearchBuilder = TipologiaSpesaSearchBuilder.getTipologiaSpesaSearchBuilder().withEstera(true);
		TipologiaSpesaStore tipologiaSpesaStore = missioniCoreClientConnector.getTipologiaSpesaByQuery(tipologiaSpesaSearchBuilder);
		Assert.assertTrue("FIND  TIPOLOGIA SPESA TIPO", tipologiaSpesaStore.getTipologiaSpesa().size() == 2);
	}
	
	@Test
	public void G_findTipologiaSpesaTipoTest() throws Exception {
		TipologiaSpesaSearchBuilder tipologiaSpesaSearchBuilder = TipologiaSpesaSearchBuilder.getTipologiaSpesaSearchBuilder().withItalia(true);
		TipologiaSpesaStore tipologiaSpesaStore = missioniCoreClientConnector.getTipologiaSpesaByQuery(tipologiaSpesaSearchBuilder);
		Assert.assertTrue("FIND  TIPOLOGIA SPESA TIPO", tipologiaSpesaStore.getTipologiaSpesa().size() == 2);
	}
	
	@Test
	public void G_findTipologiaSpesaIdTest() throws Exception {
		TipologiaSpesaSearchBuilder tipologiaSpesaSearchBuilder = TipologiaSpesaSearchBuilder.getTipologiaSpesaSearchBuilder().withId("03");
		TipologiaSpesaStore tipologiaSpesaStore = missioniCoreClientConnector.getTipologiaSpesaByQuery(tipologiaSpesaSearchBuilder);
		Assert.assertTrue("FIND  TIPOLOGIA SPESA ID", tipologiaSpesaStore.getTipologiaSpesa().size() == 1);
		Assert.assertTrue("FIND  TIPOLOGIA ID", tipologiaSpesaStore.getTipologiaSpesa().get(0).getId().equals("03"));
	}

	@Test
	public void H_findTipologiaSpesaTipoTrattamentoTest() throws Exception {
		TipologiaSpesaSearchBuilder tipoligiaSpesaSearchBuilder = TipologiaSpesaSearchBuilder.getTipologiaSpesaSearchBuilder().withTipoTrattamento(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO.name());
		TipologiaSpesaStore tipologiaSpesaStore = missioniCoreClientConnector.getTipologiaSpesaByQuery(tipoligiaSpesaSearchBuilder);
		Assert.assertTrue("FIND  TIPOLOGIA SPESA TIPO TRATTAMENTO", tipologiaSpesaStore.getTipologiaSpesa().size() == 2);

	}
	
	@Test
	public void I_findTipologiaSpesaTipoTrattamentoIdTest() throws Exception {
		TipologiaSpesaSearchBuilder tipoligiaSpesaSearchBuilder = TipologiaSpesaSearchBuilder.getTipologiaSpesaSearchBuilder().withId("01").withTipoTrattamento(TrattamentoMissioneEsteraEnum.TRATTAMENTO_ALTERNATIVO.name());
		TipologiaSpesaStore tipologiaSpesaStore = missioniCoreClientConnector.getTipologiaSpesaByQuery(tipoligiaSpesaSearchBuilder);
		Assert.assertTrue("FIND  TIPOLOGIA SPESA TIPO TRATTAMENTO", tipologiaSpesaStore.getTipologiaSpesa().size() == 1);

	}
	
}
