package it.cnr.missioni.dropwizard.resources.tipologiaSpesa;

import it.cnr.missioni.dropwizard.delegate.tipologiaSpesa.ITipologiaSpesaDelegate;
import it.cnr.missioni.model.configuration.TipologiaSpesa;
import it.cnr.missioni.rest.api.resources.tipologiaSpesa.TipologiaSpesaRestService;
import org.geosdi.geoplatform.logger.support.annotation.GeoPlatformLog;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.ws.rs.core.Response;

/**
 * 
 * @author Salvia Vito
 *
 */
@Component(value = "tipologiaSpesaRestServiceResource")
public class TipologiaSpesaRestServiceResource implements TipologiaSpesaRestService {

	@GeoPlatformLog
	static Logger logger;
	//
	@Resource(name = "tipologiaSpesaDelegate")
	private ITipologiaSpesaDelegate tipologiaSpesaDelegate;

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
	public Response getTipologiaSpesaByQuery(String id, boolean estera, boolean italia, String tipologiaTrattamento,
			int from, int size, boolean all) throws Exception {
		return Response.ok(
				this.tipologiaSpesaDelegate.getTipologiaSpesaByQuery(id, estera,italia, tipologiaTrattamento, from, size, all))
				.build();
	}

	/**
	 * 
	 * @param tipologiaSpesa
	 * @return
	 * @throws Exception
	 */
	@Override
	public Response addTipologiaSpesa(TipologiaSpesa tipologiaSpesa) throws Exception {
		return Response.ok(this.tipologiaSpesaDelegate.addTipologiaSpesa(tipologiaSpesa)).build();
	}

	/**
	 * 
	 * @param tipologiaSpesa
	 * @return
	 * @throws Exception
	 */
	@Override
	public Response updateTipologiaSpesa(TipologiaSpesa tipologiaSpesa) throws Exception {
		return Response.ok(this.tipologiaSpesaDelegate.updateTipologiaSpesa(tipologiaSpesa)).build();
	}

	/**
	 * 
	 * @param tipologiaSpesaID
	 * @return
	 * @throws Exception
	 */
	@Override
	public Response deleteTipologiaSpesa(String tipologiaSpesaID) throws Exception {
		return Response.ok(this.tipologiaSpesaDelegate.deleteTipologiaSpesa(tipologiaSpesaID)).build();
	}

}
