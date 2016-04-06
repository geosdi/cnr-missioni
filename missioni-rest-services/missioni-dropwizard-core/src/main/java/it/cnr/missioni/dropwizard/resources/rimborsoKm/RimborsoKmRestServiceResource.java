package it.cnr.missioni.dropwizard.resources.rimborsoKm;

import it.cnr.missioni.dropwizard.delegate.rimborsoKm.IRimborsoKmDelegate;
import it.cnr.missioni.model.configuration.RimborsoKm;
import it.cnr.missioni.rest.api.resources.rimborsoKm.RimborsoKmRestService;
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
@Component(value = "rimborsoKmRestServiceResource")
public class RimborsoKmRestServiceResource implements RimborsoKmRestService {

	@GeoPlatformLog
	static Logger logger;
	//
	@Resource(name = "rimborsoKmDelegate")
	private IRimborsoKmDelegate rimborsoKmDelegate;


	/**
	 * @return
	 * @throws Exception
	 */
	@Override
	public Response getRimborsoKmByQuery() throws Exception {
		return Response.ok(this.rimborsoKmDelegate.getRimborsoKmByQuery()).build();
	}

	/**
	 * @param rimborsoKm
	 * @return
	 * @throws Exception
	 */
	@Override
	public Response addRimborsoKm(RimborsoKm rimborsoKm) throws Exception {
		return Response.ok(this.rimborsoKmDelegate.addRimborsoKm(rimborsoKm)).build();
	}

	/**
	 * @param rimborsoKm
	 * @return
	 * @throws Exception
	 */
	@Override
	public Response updateRimborsoKm(RimborsoKm rimborsoKm) throws Exception {
		return Response.ok(this.rimborsoKmDelegate.updateRimborsoKm(rimborsoKm)).build();
	}

	/**
	 * @param rimborsoKmID
	 * @return
	 * @throws Exception
	 */
	@Override
	public Response deleteRimborsoKm(String rimborsoKmID) throws Exception {
		return Response.ok(this.rimborsoKmDelegate.deleteRimborsoKm(rimborsoKmID)).build();
	}

}
