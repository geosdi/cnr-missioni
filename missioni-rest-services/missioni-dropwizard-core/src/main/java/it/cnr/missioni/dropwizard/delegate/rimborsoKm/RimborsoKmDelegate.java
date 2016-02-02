package it.cnr.missioni.dropwizard.delegate.rimborsoKm;

import javax.annotation.Resource;

import org.geosdi.geoplatform.exception.IllegalParameterFault;
import org.geosdi.geoplatform.experimental.el.dao.PageResult;
import org.geosdi.geoplatform.logger.support.annotation.GeoPlatformLog;
import org.slf4j.Logger;

import com.fasterxml.uuid.EthernetAddress;
import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedGenerator;

import it.cnr.missioni.el.dao.IRimborsoKmDAO;
import it.cnr.missioni.el.model.search.builder.RimborsoKmSearchBuilder;
import it.cnr.missioni.model.configuration.RimborsoKm;
import it.cnr.missioni.rest.api.response.rimborsoKm.RimborsoKmStore;

/**
 * 
 * @author Salvia Vito
 *
 */
class RimborsoKmDelegate implements IRimborsoKmDelegate {

	static {
		gen = Generators.timeBasedGenerator(EthernetAddress.fromInterface());
	}

	private static final TimeBasedGenerator gen;
	//
	@GeoPlatformLog
	private static Logger logger;
	//
	@Resource(name = "rimborsoKmDAO")
	private IRimborsoKmDAO rimborsoKmDAO;

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	@Override
	public RimborsoKmStore getRimborsoKmByQuery() throws Exception {

		RimborsoKmSearchBuilder rimborsoSearchBuilder = RimborsoKmSearchBuilder.getRimborsoKmSearchBuilder();

		PageResult<RimborsoKm> pageResult = this.rimborsoKmDAO.findRimborsoKmByQuery(rimborsoSearchBuilder);
		if (!pageResult.getResults().isEmpty()) {
			RimborsoKmStore rimborsoKmStore = new RimborsoKmStore();
			rimborsoKmStore.setRimborsoKm(pageResult.getResults());
			rimborsoKmStore.setTotale(pageResult.getTotal());
			return rimborsoKmStore;
		} else
			return null;
	}

	/**
	 * 
	 * @param rimborsoKm
	 * @return
	 * @throws Exception
	 */
	@Override
	public String addRimborsoKm(RimborsoKm rimborsoKm) throws Exception {
		if ((rimborsoKm == null)) {
			throw new IllegalParameterFault("The Parameter rimborsoKm must not be null");
		}
		if (rimborsoKm.getId() == null)
			rimborsoKm.setId(gen.generate().toString());
		this.rimborsoKmDAO.persist(rimborsoKm);
		return null;

	}

	/**
	 * 
	 * @param rimborsoKm
	 * @return
	 * @throws Exception
	 */
	@Override
	public Boolean updateRimborsoKm(RimborsoKm rimborsoKm) throws Exception {
		if ((rimborsoKm == null)) {
			throw new IllegalParameterFault("The Parameter rimborsoKm must not be null");
		}
		this.rimborsoKmDAO.update(rimborsoKm);
		return Boolean.TRUE;
	}

	/**
	 * 
	 * @param rimborsoKmID
	 * @return
	 * @throws Exception
	 */
	@Override
	public Boolean deleteRimborsoKm(String rimborsoKmID) throws Exception {
		if ((rimborsoKmID == null) || (rimborsoKmID.isEmpty())) {
			throw new IllegalParameterFault("The Parameter rimborsoKmID must not be null " + "or an Empty String.");
		}
		this.rimborsoKmDAO.delete(rimborsoKmID);
		return Boolean.TRUE;
	}

}
