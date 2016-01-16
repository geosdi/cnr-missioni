package it.cnr.missioni.dropwizard.delegate.missioni;

import java.util.List;

import javax.annotation.Resource;

import org.geosdi.geoplatform.exception.IllegalParameterFault;
import org.geosdi.geoplatform.experimental.el.dao.GPElasticSearchDAO.Page;
import org.geosdi.geoplatform.logger.support.annotation.GeoPlatformLog;
import org.slf4j.Logger;

import com.fasterxml.uuid.EthernetAddress;
import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedGenerator;

import it.cnr.missioni.el.dao.IMissioneDAO;
import it.cnr.missioni.el.model.search.builder.MissioneSearchBuilder;
import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.rest.api.response.missione.MissioniStore;

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
	public MissioniStore getMissioneByQuery(String idMissione, String idUser, String stato, String numeroOrdineRimborso)
			throws Exception {
		// if ((missioneID == null) || (missioneID.isEmpty())) {
		// throw new IllegalParameterFault("The Parameter missioneID must not "
		// + "be null or an Empty String");
		// }

		MissioneSearchBuilder missioneSearchBuilder = MissioneSearchBuilder.getMissioneSearchBuilder()
				.withIdUser(idUser).withIdMissione(idMissione).withStato(stato)
				.withNumeroOrdineMissione(numeroOrdineRimborso);

		List<Missione> listaMissioni = this.missioneDAO.findMissioneByQuery(new Page(0, 10), missioneSearchBuilder);
		if (!listaMissioni.isEmpty()) {
			MissioniStore missioniStore = new MissioniStore();
			missioniStore.setMissioni(listaMissioni);
			return missioniStore;
		} else
			return null;

	}

	@Override
	public MissioniStore getLastUserMissions(String userID) throws Exception {
		return null;
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
