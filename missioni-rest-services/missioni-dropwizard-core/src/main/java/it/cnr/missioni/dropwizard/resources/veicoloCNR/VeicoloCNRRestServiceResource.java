package it.cnr.missioni.dropwizard.resources.veicoloCNR;

import it.cnr.missioni.dropwizard.delegate.veicoloCNR.IVeicoloCNRDelegate;
import it.cnr.missioni.model.prenotazione.VeicoloCNR;
import it.cnr.missioni.rest.api.resources.veicoloCNR.VeicoloCNRRestService;
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
@Component(value = "veicoloCNRRestServiceResource")
public class VeicoloCNRRestServiceResource implements VeicoloCNRRestService {

	@GeoPlatformLog
	static Logger logger;
	//
	@Resource(name = "veicoloCNRDelegate")
	private IVeicoloCNRDelegate veicoloCNRDelegate;

	/**
	 * @param id
	 * @param stato
	 * @param targa
	 * @param cartaCircolazione
	 * @param polizzaAssicurativa
	 * @param notId
	 * @param from
	 * @param size
	 * @param all
	 * @return
	 * @throws Exception
	 */
	@Override
	public Response getVeicoloCNRByQuery(String id,String stato, String targa, String cartaCircolazione,
			String polizzaAssicurativa, String notId, int from, int size, boolean all) throws Exception {
		return Response.ok(this.veicoloCNRDelegate.getVeicoloCNRByQuery(id,stato, targa, cartaCircolazione,
				polizzaAssicurativa, notId, from, size, all)).build();
	}

	/**
	 * 
	 * @param veicoloCNR
	 * @return
	 * @throws Exception
	 */
	@Override
	public Response addVeicoloCNR(VeicoloCNR veicoloCNR) throws Exception {
		return Response.ok(this.veicoloCNRDelegate.addVeicoloCNR(veicoloCNR)).build();
	}

	/**
	 * 
	 * @param veicoloCNR
	 * @return
	 * @throws Exception
	 */
	@Override
	public Response updateVeicoloCNR(VeicoloCNR veicoloCNR) throws Exception {
		return Response.ok(this.veicoloCNRDelegate.updateVeicoloCNR(veicoloCNR)).build();
	}

	/**
	 * 
	 * @param veicoloCNRID
	 * @return
	 * @throws Exception
	 */
	@Override
	public Response deleteVeicoloCNR(String veicoloCNRID) throws Exception {
		return Response.ok(this.veicoloCNRDelegate.deleteVeicoloCNR(veicoloCNRID)).build();
	}

}
