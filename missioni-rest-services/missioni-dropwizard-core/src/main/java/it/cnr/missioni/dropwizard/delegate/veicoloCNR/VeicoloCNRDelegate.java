package it.cnr.missioni.dropwizard.delegate.veicoloCNR;

import com.fasterxml.uuid.EthernetAddress;
import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedGenerator;
import it.cnr.missioni.el.dao.IVeicoloCNRDAO;
import it.cnr.missioni.el.model.search.builder.IVeicoloCNRSearchBuilder;
import it.cnr.missioni.model.prenotazione.VeicoloCNR;
import it.cnr.missioni.rest.api.response.veicoloCNR.VeicoloCNRStore;
import org.geosdi.geoplatform.exception.IllegalParameterFault;
import org.geosdi.geoplatform.experimental.el.dao.GPPageableElasticSearchDAO;
import org.geosdi.geoplatform.logger.support.annotation.GeoPlatformLog;
import org.slf4j.Logger;

import javax.annotation.Resource;

/**
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
class VeicoloCNRDelegate implements IVeicoloCNRDelegate {

	static {
		gen = Generators.timeBasedGenerator(EthernetAddress.fromInterface());
	}

	private static final TimeBasedGenerator gen;
	//
	@GeoPlatformLog
	private static Logger logger;
	//
	@Resource(name = "veicoloCNRDAO")
	private IVeicoloCNRDAO veicoloCNRDAO;

	/**
	 * @param id
	 * @param stato
	 * @param targa
	 * @param cartaCircolazione
	 * @param polizzaAssicurtiva
	 * @param notId
	 * @param from
	 * @param size
	 * @param all
	 * @return
	 * @throws Exception
	 */
	@Override
	public VeicoloCNRStore getVeicoloCNRByQuery(String id,String stato, String targa, String cartaCircolazione,
			String polizzaAssicurtiva, String notId, int from, int size, boolean all) throws Exception {
		IVeicoloCNRSearchBuilder veicoloCNRSearchBuilder = IVeicoloCNRSearchBuilder.VeicoloCNRSearchBuilder.getVeicoloCNRSearchBuilder().withId(id)
				.withStato(stato).withCartaCircolazione(cartaCircolazione).withPolizzaAssicurativa(polizzaAssicurtiva)
				.withTarga(targa).withNotId(notId).withFrom(from).withSize(size).withAll(all);
		GPPageableElasticSearchDAO.IPageResult<VeicoloCNR> pageResult = this.veicoloCNRDAO.findVeicoloCNRByQuery(veicoloCNRSearchBuilder);
		VeicoloCNRStore veicoloCNRStore = new VeicoloCNRStore();
		veicoloCNRStore.setTotale(pageResult.getTotal());
		veicoloCNRStore.setVeicoliCNR(pageResult.getResults());
		return veicoloCNRStore;

	}

	/**
	 * 
	 * @param veicoloCNR
	 * @return
	 * @throws Exception
	 */
	@Override
	public String addVeicoloCNR(VeicoloCNR veicoloCNR) throws Exception {
		if ((veicoloCNR == null)) {
			throw new IllegalParameterFault("The Parameter veicoloCNR must not be null");
		}
		if (veicoloCNR.getId() == null)
			veicoloCNR.setId(gen.generate().toString());
		return this.veicoloCNRDAO.persist(veicoloCNR).getId();

	}

	/**
	 * 
	 * @param veicoloCNR
	 * @return
	 * @throws Exception
	 */
	@Override
	public Boolean updateVeicoloCNR(VeicoloCNR veicoloCNR) throws Exception {
		if ((veicoloCNR == null)) {
			throw new IllegalParameterFault("The Parameter veicoloCNR must not be null");
		}
		this.veicoloCNRDAO.update(veicoloCNR);
		return Boolean.TRUE;
	}

	/**
	 * 
	 * @param veicoloCNRID
	 * @return
	 * @throws Exception
	 */
	@Override
	public Boolean deleteVeicoloCNR(String veicoloCNRID) throws Exception {
		if ((veicoloCNRID == null) || (veicoloCNRID.isEmpty())) {
			throw new IllegalParameterFault("The Parameter veicoloCNRID must not be null " + "or an Empty String.");
		}
		this.veicoloCNRDAO.delete(veicoloCNRID);
		return Boolean.TRUE;
	}

}
