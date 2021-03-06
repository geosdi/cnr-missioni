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

import it.cnr.missioni.connector.core.spring.connector.MissioniCoreClientConnector;
import it.cnr.missioni.el.model.search.builder.IRimborsoKmSearchBuilder;
import it.cnr.missioni.model.configuration.RimborsoKm;
import it.cnr.missioni.rest.api.response.rimborsoKm.RimborsoKmStore;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * 
 * @author Salvia Vito
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:applicationContext.xml" })
@FixMethodOrder(value = MethodSorters.NAME_ASCENDING)
public class RimborsoKmRestServiceTest {

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

	@AfterClass
	public static void afterClass() {
		System.clearProperty(CORE_CONNECTOR_KEY);
	}
	
	@Test
	public void A_deleteRimborsoKmTest() throws Exception {
		missioniCoreClientConnector.deleteRimborsoKm("01");
	}
	
	@Test
	public void B_addRimborsoKmTest() throws Exception {
		RimborsoKm rimborsoKm = new RimborsoKm();
		rimborsoKm.setId("01");
		rimborsoKm.setValue(new Double(0.36));
		Thread.sleep(2000);
		missioniCoreClientConnector.addRimborsoKm(rimborsoKm);
		Thread.sleep(2000);
		IRimborsoKmSearchBuilder rimborsoSearchBuilder = IRimborsoKmSearchBuilder.RimborsoKmSearchBuilder.getRimborsoKmSearchBuilder();
		RimborsoKmStore rimborsoKmStore = missioniCoreClientConnector.getRimborsoKmByQuery(rimborsoSearchBuilder);
		Assert.assertTrue("ADD RIMBORSO KM", rimborsoKmStore.getTotale() == 1);
	}
	
	@Test
	public void C_updateRimborsoKmTest() throws Exception {
		RimborsoKmStore rimborsoKmStore = missioniCoreClientConnector.getRimborsoKmByQuery(IRimborsoKmSearchBuilder.RimborsoKmSearchBuilder.getRimborsoKmSearchBuilder());
		RimborsoKm rimborsoKm = rimborsoKmStore.getRimborsoKm().get(0);
		rimborsoKm.setValue(new Double(0.46));
		missioniCoreClientConnector.updateRimborsoKm(rimborsoKm);
		Thread.sleep(1000);
		IRimborsoKmSearchBuilder rimborsoSearchBuilder = IRimborsoKmSearchBuilder.RimborsoKmSearchBuilder.getRimborsoKmSearchBuilder();
		rimborsoKmStore = missioniCoreClientConnector.getRimborsoKmByQuery(rimborsoSearchBuilder);
		Assert.assertTrue("UPDATE RIMBORSO KM", rimborsoKmStore.getTotale() == 1);
	}
}
