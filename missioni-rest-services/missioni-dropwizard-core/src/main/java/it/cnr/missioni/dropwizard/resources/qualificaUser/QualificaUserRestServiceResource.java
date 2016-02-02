package it.cnr.missioni.dropwizard.resources.qualificaUser;

import javax.annotation.Resource;
import javax.ws.rs.core.Response;

import org.geosdi.geoplatform.logger.support.annotation.GeoPlatformLog;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import it.cnr.missioni.dropwizard.delegate.qualificaUser.IQualificaUserDelegate;
import it.cnr.missioni.model.configuration.QualificaUser;
import it.cnr.missioni.rest.api.resources.qualificaUser.QualificaUserRestService;

/**
 * 
 * @author Salvia Vito
 *
 */
@Component(value = "qualificaUserRestServiceResource")
public class QualificaUserRestServiceResource implements QualificaUserRestService {

	@GeoPlatformLog
	static Logger logger;
	//
	@Resource(name = "qualificaUserDelegate")
	private IQualificaUserDelegate qualificaUserDelegate;

	/**
	 * @param from
	 * @param size
	 * @return
	 * @throws Exception
	 */
	@Override
	public Response getQualificaByQuery(int from, int size,boolean all) throws Exception {
		return Response.ok(this.qualificaUserDelegate.getQualificaUserByQuery(from, size,all)).build();

	}

	/**
	 * @param qualificaUser
	 * @return
	 * @throws Exception
	 */
	@Override
	public Response addQualificaUser(QualificaUser qualificaUser) throws Exception {
		return Response.ok(this.qualificaUserDelegate.addQualificaUser(qualificaUser)).build();
	}

	/**
	 * @param qualificaUser
	 * @return
	 * @throws Exception
	 */
	@Override
	public Response updateQualificaUser(QualificaUser qualificaUser) throws Exception {
		return Response.ok(this.qualificaUserDelegate.updateQualificaUser(qualificaUser)).build();

	}

	/**
	 * @param qualificaUserID
	 * @return
	 * @throws Exception
	 */
	@Override
	public Response deleteQualificaUser(String qualificaUserID) throws Exception {
		return Response.ok(this.qualificaUserDelegate.deleteQualificaUser(qualificaUserID)).build();

	}

}
