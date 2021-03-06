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
import it.cnr.missioni.el.model.search.builder.IVeicoloCNRSearchBuilder;
import it.cnr.missioni.model.prenotazione.StatoVeicoloEnum;
import it.cnr.missioni.model.prenotazione.VeicoloCNR;
import it.cnr.missioni.rest.api.response.veicoloCNR.VeicoloCNRStore;
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
public class VeicoloCNRRestServiceTest {

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

//	@Test
//	public void A_createTest() throws Exception {
//		List<VeicoloCNR> lista = VeicoloCNRFunction.creaMassiveVeicoloCNR();
//		lista.stream().forEach(m->{
//			try {
//				missioniCoreClientConnector.addVeicoloCNR(m);
//			} catch (Exception e) {
//			}
//		});
//		Thread.sleep(1000);
//	}
	
	@Test
	public void A_addVeicolotest() throws Exception {
		VeicoloCNR v = new VeicoloCNR();
		v.setCartaCircolazione("carta");
		v.setId("03");
		v.setStato(StatoVeicoloEnum.DISPONIBILE);
		v.setPolizzaAssicurativa("polizza");
		v.setTarga("AAAAAA");
		v.setTipo("Tipo");
		missioniCoreClientConnector.addVeicoloCNR(v);
		Thread.sleep(3000);
		IVeicoloCNRSearchBuilder veicoloCNRSearchBuilder = IVeicoloCNRSearchBuilder.VeicoloCNRSearchBuilder.getVeicoloCNRSearchBuilder();
		VeicoloCNRStore veicoloCNRStore = missioniCoreClientConnector.getVeicoloCNRByQuery(veicoloCNRSearchBuilder);
		Assert.assertTrue("ADD VEICOLO CNR", veicoloCNRStore.getTotale() == 3);
	}
	
	@Test
	public void B_updateVeicolOCNRTest() throws Exception {
		VeicoloCNR v = new VeicoloCNR();
		v.setCartaCircolazione("carta 123");
		v.setId("03");
		v.setStato(StatoVeicoloEnum.DISPONIBILE);
		v.setPolizzaAssicurativa("polizza 123");
		v.setTarga("BBBBB");
		v.setTipo("Tipo");
		missioniCoreClientConnector.updateVeicoloCNR(v);
		Thread.sleep(1000);
		IVeicoloCNRSearchBuilder veicoloCNRSearchBuilder = IVeicoloCNRSearchBuilder.VeicoloCNRSearchBuilder.getVeicoloCNRSearchBuilder();
		VeicoloCNRStore veicoloCNRStore = missioniCoreClientConnector.getVeicoloCNRByQuery(veicoloCNRSearchBuilder);
		Assert.assertTrue("UPDATE VEICOLO CNR", veicoloCNRStore.getTotale() == 3);
	}
	
	@Test
	public void C_deleteVeicoloCNRTest() throws Exception {
		missioniCoreClientConnector.deleteVeicoloCNR("03");
		Thread.sleep(1000);
		IVeicoloCNRSearchBuilder veicoloCNRSearchBuilder = IVeicoloCNRSearchBuilder.VeicoloCNRSearchBuilder.getVeicoloCNRSearchBuilder();
		VeicoloCNRStore veicoloCNRStore = missioniCoreClientConnector.getVeicoloCNRByQuery(veicoloCNRSearchBuilder);
		Assert.assertTrue("DELETE VEICOLO CNR", veicoloCNRStore.getTotale() == 2);
	}

	@Test
	public void D_testFindVeicoloCNR() throws Exception {
		IVeicoloCNRSearchBuilder veicoloCNRSearchBuilder = IVeicoloCNRSearchBuilder.VeicoloCNRSearchBuilder.getVeicoloCNRSearchBuilder();
		VeicoloCNRStore veicoloCNRStore = missioniCoreClientConnector.getVeicoloCNRByQuery(veicoloCNRSearchBuilder);
		Assert.assertTrue("FIND VEICOLO CNR", veicoloCNRStore.getTotale() == 2);
	}
	
	@Test
	public void E_findVeicoloDisponibileCNRTest() throws Exception {
		IVeicoloCNRSearchBuilder veicoloCNRSearchBuilder = IVeicoloCNRSearchBuilder.VeicoloCNRSearchBuilder.getVeicoloCNRSearchBuilder().withStato(StatoVeicoloEnum.DISPONIBILE.name());
		VeicoloCNRStore veicoloCNRStore = missioniCoreClientConnector.getVeicoloCNRByQuery(veicoloCNRSearchBuilder);
		Assert.assertTrue("FIND DISPONIBILE VEICOLO CNR", veicoloCNRStore.getTotale() == 1);
	}
	
	@Test
	public void F_findVeicoloTargaWithNotID() throws Exception {
		IVeicoloCNRSearchBuilder veicoloCNRSearchBuilder = IVeicoloCNRSearchBuilder.VeicoloCNRSearchBuilder.getVeicoloCNRSearchBuilder()
				.withTarga("6575").withNotId("02");
		VeicoloCNRStore veicoloCNRStore = missioniCoreClientConnector.getVeicoloCNRByQuery(veicoloCNRSearchBuilder);
		Thread.sleep(1000);	
		Assert.assertTrue("FIND  VEICOLO CNR", veicoloCNRStore.getVeicoliCNR().isEmpty());
	}

	@Test
	public void G_findVeicoloCartaCircolazioneWithNotID() throws Exception {
		IVeicoloCNRSearchBuilder veicoloCNRSearchBuilder = IVeicoloCNRSearchBuilder.VeicoloCNRSearchBuilder.getVeicoloCNRSearchBuilder()
				.withCartaCircolazione("carta 456").withNotId("02");
		VeicoloCNRStore veicoloCNRStore = missioniCoreClientConnector.getVeicoloCNRByQuery(veicoloCNRSearchBuilder);
		Thread.sleep(1000);		
		Assert.assertTrue("FIND  VEICOLO CNR", veicoloCNRStore.getTotale() == 0);

	}
	
	@Test
	public void H_findVeicoloPolizzaAssicurativaWithNotID() throws Exception {
		IVeicoloCNRSearchBuilder veicoloCNRSearchBuilder = IVeicoloCNRSearchBuilder.VeicoloCNRSearchBuilder.getVeicoloCNRSearchBuilder()
				.withPolizzaAssicurativa("polizza 2").withNotId("02");
		VeicoloCNRStore veicoloCNRStore = missioniCoreClientConnector.getVeicoloCNRByQuery(veicoloCNRSearchBuilder);
		Thread.sleep(1000);		
		Assert.assertTrue("FIND  VEICOLO CNR", veicoloCNRStore.getVeicoliCNR().isEmpty());
	}
	
	@Test
	public void I_findVeicoloPolizza() throws Exception {
		IVeicoloCNRSearchBuilder veicoloCNRSearchBuilder = IVeicoloCNRSearchBuilder.VeicoloCNRSearchBuilder.getVeicoloCNRSearchBuilder()
				.withPolizzaAssicurativa("polizza 2");
		VeicoloCNRStore veicoloCNRStore = missioniCoreClientConnector.getVeicoloCNRByQuery(veicoloCNRSearchBuilder);
		Thread.sleep(1000);		
		Assert.assertTrue("FINR  VEICOLO CNR", veicoloCNRStore.getTotale() == 1);

	}
	
	@Test
	public void L_findVeicoloPolizza_2() throws Exception {
		IVeicoloCNRSearchBuilder veicoloCNRSearchBuilder = IVeicoloCNRSearchBuilder.VeicoloCNRSearchBuilder.getVeicoloCNRSearchBuilder()
				.withPolizzaAssicurativa("Polizza 2");
		VeicoloCNRStore veicoloCNRStore = missioniCoreClientConnector.getVeicoloCNRByQuery(veicoloCNRSearchBuilder);
		Thread.sleep(1000);		
		Assert.assertTrue("FINR  VEICOLO CNR", veicoloCNRStore.getTotale()== 1);
	}
	
	@Test
	public void F_testFindVeicoloCNRBiId() throws Exception {
		IVeicoloCNRSearchBuilder veicoloCNRSearchBuilder = IVeicoloCNRSearchBuilder.VeicoloCNRSearchBuilder
				.getVeicoloCNRSearchBuilder().withId("01");
		VeicoloCNRStore veicoloCNRStore = missioniCoreClientConnector.getVeicoloCNRByQuery(veicoloCNRSearchBuilder);
		logger.debug("############################NUMBER_OF_VEICOLO_CNR: {}\n", veicoloCNRStore.getTotale());
		Assert.assertTrue("FINR  VEICOLO CNR", veicoloCNRStore.getTotale() == 1);
	}
	
	@Test
	public void F_testFindVeicoloCNRBiId_2() throws Exception {
		IVeicoloCNRSearchBuilder veicoloCNRSearchBuilder = IVeicoloCNRSearchBuilder.VeicoloCNRSearchBuilder
				.getVeicoloCNRSearchBuilder().withId("05");
		VeicoloCNRStore veicoloCNRStore = missioniCoreClientConnector.getVeicoloCNRByQuery(veicoloCNRSearchBuilder);
		logger.debug("############################NUMBER_OF_VEICOLO_CNR: {}\n", veicoloCNRStore.getTotale());
		Assert.assertTrue("FINR  VEICOLO CNR", veicoloCNRStore.getTotale() == 0);
	}
}
