package it.cnr.missioni.dropwizard.resources.massimale;

import javax.annotation.Resource;
import javax.ws.rs.core.Response;

import org.geosdi.geoplatform.logger.support.annotation.GeoPlatformLog;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import it.cnr.missioni.dropwizard.delegate.massimale.IMassimaleDelegate;
import it.cnr.missioni.model.configuration.Massimale;
import it.cnr.missioni.rest.api.resources.massimale.MassimaleRestService;

/**
 * 
 * @author Salvia Vito
 *
 */
@Component(value = "massimaleRestServiceResource")
public class MassimaleRestServiceResource implements MassimaleRestService {

	@GeoPlatformLog
	static Logger logger;
	//
	@Resource(name = "massimaleDelegate")
	private IMassimaleDelegate massimaleDelegate;

	/**
	 * 
	 * @param from
	 * @param size
	 * @param livello
	 * @param areaGeografica
	 * @param notId
	 * @param tipo
	 * @return
	 * @throws Exception
	 */
	@Override
	public Response getMassimaleByQuery(int from, int size, String livello, String areaGeografica,String id, String notId,
			String tipo) throws Exception {
		return Response.ok(this.massimaleDelegate.getMassimaleByQuery(from, size, livello, areaGeografica,id, notId, tipo))
				.build();

	}

	/**
	 * 
	 * @param massimale
	 * @return
	 * @throws Exception
	 */
	@Override
	public Response addMassimale(Massimale massimale) throws Exception {
		return Response.ok(this.massimaleDelegate.addMassimale(massimale)).build();
	}

	/**
	 * 
	 * @param massimale
	 * @return
	 * @throws Exception
	 */
	@Override
	public Response updateMassimale(Massimale massimale) throws Exception {
		return Response.ok(this.massimaleDelegate.updateMassimale(massimale)).build();

	}

	/**
	 * 
	 * @param massimaleID
	 * @return
	 * @throws Exception
	 */
	@Override
	public Response deleteMassimale(String massimaleID) throws Exception {
		return Response.ok(this.massimaleDelegate.deleteMassimale(massimaleID)).build();

	}

}
