package it.cnr.missioni.dropwizard.delegate.tipologiaSpesa;

import com.fasterxml.uuid.EthernetAddress;
import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedGenerator;
import it.cnr.missioni.el.dao.ITipologiaSpesaDAO;
import it.cnr.missioni.el.model.search.builder.ITipologiaSpesaSearchBuilder;
import it.cnr.missioni.model.configuration.TipologiaSpesa;
import it.cnr.missioni.rest.api.response.tipologiaSpesa.TipologiaSpesaStore;
import org.geosdi.geoplatform.exception.IllegalParameterFault;
import org.geosdi.geoplatform.experimental.el.dao.GPPageableElasticSearchDAO;
import org.geosdi.geoplatform.logger.support.annotation.GeoPlatformLog;
import org.slf4j.Logger;

import javax.annotation.Resource;

/**
 * 
 * @author Salvia Vito
 *
 */
class TipologiaSpesaDelegate implements ITipologiaSpesaDelegate {

	static {
		gen = Generators.timeBasedGenerator(EthernetAddress.fromInterface());
	}

	private static final TimeBasedGenerator gen;
	//
	@GeoPlatformLog
	private static Logger logger;
	//
	@Resource(name = "tipologiaSpesaDAO")
	private ITipologiaSpesaDAO tipologiaSpesaDAO;

	/**
	 * 
	 * @param id
	 * @param estera
	 * @param italia
	 * @param tipologiaTrattamento
	 * @param from
	 * @param size
	 * @param all
	 * @return
	 * @throws Exception
	 */
	@Override
	public TipologiaSpesaStore getTipologiaSpesaByQuery(String id, boolean estera, boolean italia,
			String tipologiaTrattamento, int from, int size, boolean all) throws Exception {
		ITipologiaSpesaSearchBuilder tipologiaSpesaUserSearchBuilder = ITipologiaSpesaSearchBuilder.TipologiaSpesaSearchBuilder
				.getTipologiaSpesaSearchBuilder().withFrom(from).withSize(size)
				.withTipoTrattamento(tipologiaTrattamento).withAll(all).withId(id).withEstera(estera).withItalia(italia);
		GPPageableElasticSearchDAO.IPageResult<TipologiaSpesa> pageResult = this.tipologiaSpesaDAO
				.findTipologiaSpesaByQuery(tipologiaSpesaUserSearchBuilder);
		TipologiaSpesaStore tipologiaSpesaStore = new TipologiaSpesaStore();
		tipologiaSpesaStore.setTipologiaSpesa(pageResult.getResults());
		tipologiaSpesaStore.setTotale(pageResult.getTotal());
		return tipologiaSpesaStore;

	}

	/**
	 * 
	 * @param tipologiaSpesa
	 * @return
	 * @throws Exception
	 */
	@Override
	public String addTipologiaSpesa(TipologiaSpesa tipologiaSpesa) throws Exception {
		if ((tipologiaSpesa == null)) {
			throw new IllegalParameterFault("The Parameter tipologiaSpesa must not be null");
		}
		if (tipologiaSpesa.getId() == null)
			tipologiaSpesa.setId(gen.generate().toString());
		return this.tipologiaSpesaDAO.persist(tipologiaSpesa).getId();

	}

	/**
	 * 
	 * @param tipologiaSpesa
	 * @return
	 * @throws Exception
	 */
	@Override
	public Boolean updateTipologiaSpesa(TipologiaSpesa tipologiaSpesa) throws Exception {
		if ((tipologiaSpesa == null)) {
			throw new IllegalParameterFault("The Parameter tipologiaSpesa must not be null");
		}
		this.tipologiaSpesaDAO.update(tipologiaSpesa);
		return Boolean.TRUE;
	}

	/**
	 * 
	 * @param tipologiaSpesaID
	 * @return
	 * @throws Exception
	 */
	@Override
	public Boolean deleteTipologiaSpesa(String tipologiaSpesaID) throws Exception {
		if ((tipologiaSpesaID == null) || (tipologiaSpesaID.isEmpty())) {
			throw new IllegalParameterFault("The Parameter tipologiaSpesaID must not be null " + "or an Empty String.");
		}
		this.tipologiaSpesaDAO.delete(tipologiaSpesaID);
		return Boolean.TRUE;
	}

}
