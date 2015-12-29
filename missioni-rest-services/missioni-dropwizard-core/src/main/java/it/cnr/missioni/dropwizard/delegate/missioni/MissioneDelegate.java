package it.cnr.missioni.dropwizard.delegate.missioni;

import com.fasterxml.uuid.EthernetAddress;
import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedGenerator;
import it.cnr.missioni.el.dao.IMissioneDAO;
import it.cnr.missioni.el.dao.IUserDAO;
import it.cnr.missioni.el.model.search.BooleanModelSearch;
import it.cnr.missioni.el.model.search.ExactSearch;
import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.rest.api.response.missione.MissioniStore;

import org.geosdi.geoplatform.exception.IllegalParameterFault;
import org.geosdi.geoplatform.exception.ResourceNotFoundFault;
import org.geosdi.geoplatform.experimental.el.dao.GPElasticSearchDAO.Page;
import org.geosdi.geoplatform.logger.support.annotation.GeoPlatformLog;
import org.slf4j.Logger;

import java.util.List;

import javax.annotation.Resource;

/**
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
class MissioneDelegate implements IMissioneDelegate {

	static {
		gen = Generators.timeBasedGenerator(EthernetAddress.fromInterface());
	}

	private static final TimeBasedGenerator gen;
	//
	@GeoPlatformLog
	private static Logger logger;

	@Resource(name = "missioneDAO")
	private IMissioneDAO missioneDAO;

	@Override
	public Missione getMissioneByID(String missioneID) throws Exception {
		if ((missioneID == null) || (missioneID.isEmpty())) {
			throw new IllegalParameterFault("The Parameter missioneID must not " + "be null or an Empty String");
		}
		Missione missione = this.missioneDAO.find(missioneID);

		if (missione == null) {
			throw new ResourceNotFoundFault("The Mission with ID : " + missioneID + " doesn't exist.");
		}
		return missione;
	}

	@Override
	public MissioniStore getLastUserMissions(String userID) throws Exception {

		if ((userID == null) || (userID.isEmpty())) {
			throw new IllegalParameterFault("The Parameter userID must not " + "be null or an Empty String");
		}

		BooleanModelSearch booleanModelSearch = new BooleanModelSearch();
		booleanModelSearch.getListaSearch().add(new ExactSearch("missione.idUser", userID));
		booleanModelSearch.buildQuery();
		MissioniStore missioniStore = new MissioniStore();
		missioniStore.setMissioni(this.missioneDAO.findMissioneByQuery(new Page(0, 10), booleanModelSearch));
		missioniStore.setUserID(userID);
		return missioniStore;
	}

	@Override
	public String addMissione(Missione missione) throws Exception {
		if ((missione == null)) {
			throw new IllegalParameterFault("The Parameter missione must not be null ");
		}
		this.missioneDAO.persist(missione);
		return null;
	}

	@Override
	public Boolean updateMissione(Missione missione) throws Exception {
		if ((missione == null)) {
			throw new IllegalParameterFault("The Parameter missione must not be null ");
		}
		this.missioneDAO.update(missione);
		return Boolean.TRUE;
	}

	@Override
	public Boolean deleteMissione(String missioneID) throws Exception {
		if ((missioneID == null) || (missioneID.isEmpty())) {
			throw new IllegalParameterFault("The Parameter missioneID must not be null " + "or an Empty String.");
		}
		this.missioneDAO.delete(missioneID);
		return Boolean.TRUE;
	}
}
