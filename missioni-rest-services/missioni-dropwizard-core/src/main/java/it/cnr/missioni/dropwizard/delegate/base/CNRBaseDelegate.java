package it.cnr.missioni.dropwizard.delegate.base;

import com.fasterxml.uuid.EthernetAddress;
import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedGenerator;
import it.cnr.missioni.el.dao.IMissioneDAO;
import it.cnr.missioni.el.dao.IUtenteDAO;
import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.rest.api.response.base.MissioniStore;
import org.geosdi.geoplatform.exception.IllegalParameterFault;
import org.geosdi.geoplatform.exception.ResourceNotFoundFault;
import org.geosdi.geoplatform.logger.support.annotation.GeoPlatformLog;
import org.slf4j.Logger;

import javax.annotation.Resource;

/**
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
class CNRBaseDelegate implements BaseDelegate {

    static {
        gen = Generators.timeBasedGenerator(EthernetAddress.fromInterface());
    }

    private static final TimeBasedGenerator gen;
    //
    @GeoPlatformLog
    private static Logger logger;
    //
    @Resource(name = "utenteDAO")
    private IUtenteDAO utenteDAO;
    @Resource(name = "missioneDAO")
    private IMissioneDAO missioneDAO;

    @Override
    public Missione getMissioneByID(String missioneID) throws Exception {
        if ((missioneID == null) || (missioneID.isEmpty())) {
            throw new IllegalParameterFault("The Parameter missioneID must not " +
                    "be null or an Empty String");
        }
        Missione missione = this.missioneDAO.find(missioneID);

        if (missione == null) {
            throw new ResourceNotFoundFault("The Mission with ID : " + missioneID
                    + " doesn't exist.");
        }
        return missione;
    }

    @Override
    public MissioniStore getLastUserMissions(String userID) throws Exception {
        return null;
    }

    @Override
    public String addMissione(Missione missione) throws Exception {
        return null;
    }

    @Override
    public Boolean updateMissione(Missione missione) throws Exception {
        return Boolean.TRUE;
    }

    @Override
    public Boolean deleteMissione(String missioneID) throws Exception {
        if ((missioneID == null) || (missioneID.isEmpty())) {
            throw new IllegalParameterFault("The Parameter missioneID must not be null " +
                    "or an Empty String.");
        }
        this.missioneDAO.delete(missioneID);
        return Boolean.TRUE;
    }
}
