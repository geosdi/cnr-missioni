package it.cnr.missioni.dropwizard.delegate.veicoloCNR;

import javax.annotation.Resource;

import org.geosdi.geoplatform.exception.IllegalParameterFault;
import org.geosdi.geoplatform.experimental.el.dao.PageResult;
import org.geosdi.geoplatform.logger.support.annotation.GeoPlatformLog;
import org.slf4j.Logger;

import com.fasterxml.uuid.EthernetAddress;
import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedGenerator;

import it.cnr.missioni.el.dao.IVeicoloCNRDAO;
import it.cnr.missioni.el.model.search.builder.VeicoloCNRSearchBuilder;
import it.cnr.missioni.model.prenotazione.VeicoloCNR;
import it.cnr.missioni.rest.api.response.veicoloCNR.VeicoloCNRStore;

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
	 * 
	 * @param stato
	 * @param from
	 * @param size
	 * @return
	 * @throws Exception
	 */
	@Override
	public VeicoloCNRStore getVeicoloCNRByQuery(String stato,String targa,String cartaCircolazione,String polizzaAssicurtiva,String id, int from, int size) throws Exception {

		VeicoloCNRSearchBuilder veicoloCNRSearchBuilder = VeicoloCNRSearchBuilder.getVeicoloCNRSearchBuilder().withStato(stato)
				.withCartaCircolazione(cartaCircolazione)
				.withPolizzaAssicurativa(polizzaAssicurtiva)
				.withTarga(targa)
				.withId(id)
				.withFrom(from).withSize(size);

		
		PageResult<VeicoloCNR> pageResult = this.veicoloCNRDAO.findVeicoloCNRByQuery(veicoloCNRSearchBuilder);
		if (!pageResult.getResults().isEmpty()) {
			VeicoloCNRStore veicoloCNRStore = new VeicoloCNRStore();
			veicoloCNRStore.setVeicoliCNR(pageResult.getResults());
			veicoloCNRStore.setTotale(pageResult.getTotal());
			return veicoloCNRStore;
		} else
			return null;
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
		this.veicoloCNRDAO.persist(veicoloCNR);
		return null;

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
