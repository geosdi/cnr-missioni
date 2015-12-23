package it.cnr.missioni.dropwizard.resources.base;

import it.cnr.missioni.dropwizard.delegate.base.BaseDelegate;
import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.rest.api.resources.base.BaseRestService;
import org.geosdi.geoplatform.logger.support.annotation.GeoPlatformLog;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.ws.rs.core.Response;

/**
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
@Component(value = "cnrBaseRestService")
public class CNRBaseRestService implements BaseRestService {

    @GeoPlatformLog
    static Logger logger;
    //
    @Resource(name = "cnrBaseBaseDelegate")
    private BaseDelegate cnrBaseBaseDelegate;

    /**
     * @param missioneID
     * @return {@link Response}
     * @throws Exception
     */
    @Override
    public Response getMissioneByID(String missioneID) throws Exception {
        return Response.ok(this.cnrBaseBaseDelegate.getMissioneByID(missioneID)).build();
    }

    /**
     * @param userID
     * @return {@link Response}
     * @throws Exception
     */
    @Override
    public Response getLastUserMissions(String userID) throws Exception {
        return null;
    }

    /**
     * @param missione
     * @return {@link Response}
     * @throws Exception
     */
    @Override
    public Response addMissione(Missione missione) throws Exception {
        return null;
    }

    /**
     * @param missione
     * @return {@link Response}
     * @throws Exception
     */
    @Override
    public Response updateMissione(Missione missione) throws Exception {
        return null;
    }

    /**
     * @param missionID
     * @return {@link Response}
     * @throws Exception
     */
    @Override
    public Response deleteMissione(String missionID) throws Exception {
        return null;
    }
}
