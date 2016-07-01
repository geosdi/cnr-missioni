package it.cnr.missioni.dropwizard.resources.missione;

import it.cnr.missioni.dropwizard.delegate.missioni.IMissioneDelegate;
import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.rest.api.request.NotificationMissionRequest;
import it.cnr.missioni.rest.api.resources.missione.MissioneRestService;
import org.geosdi.geoplatform.logger.support.annotation.GeoPlatformLog;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.ws.rs.core.Response;

/**
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
@Component(value = "missioneRestServiceResource")
public class MissioneRestServiceResource implements MissioneRestService {

	@GeoPlatformLog
	static Logger logger;
	//
	@Resource(name = "missioneDelegate")
	private IMissioneDelegate missioneDelegate;

	/**
	 * 
	 * @param idMissione
	 * @param idUser
	 * @param stato
	 * @param numeroOrdineRimborso
	 * @param dataFromMissione
	 * @param dataToMissione
	 * @param dataFromRimborso
	 * @param dataToRimborso
	 * @param oggetto
	 * @param multiMatch
	 * @param fieldExist
	 * @param fieldNotExist
	 * @param rimborsoCompleted
	 * @param from
	 * @param size
	 * @return
	 * @throws Exception
	 */
	@Override
	public Response getMissioneByQuery(String idMissione, String idUser, String stato, String numeroOrdineRimborso,
			Long dataFromMissione, Long dataToMissione, Long dataFromRimborso, Long dataToRimborso, String oggetto,
			String multiMatch, String fieldExist, String fieldNotExist,boolean rimborsoCompleted, int from, int size) throws Exception {
		return Response.ok(this.missioneDelegate.getMissioneByQuery(idMissione, idUser, stato, numeroOrdineRimborso,
				dataFromMissione, dataToMissione, dataFromRimborso, dataToRimborso, oggetto, multiMatch, fieldExist,
				fieldNotExist,rimborsoCompleted, from, size)).build();
	}

	/**
	 * @param userID
	 * @return {@link Response}
	 * @throws Exception
	 */
	@Override
	public Response getLastUserMissions(String userID) throws Exception {
		return Response.ok(this.missioneDelegate.getLastUserMissions(userID)).build();
	}

	/**
	 * @param missione
	 * @return {@link Response}
	 * @throws Exception
	 */
	@Override
	public Response addMissione(Missione missione) throws Exception {
		return Response.ok(this.missioneDelegate.addMissione(missione)).build();
	}

	/**
	 * @param missione
	 * @return {@link Response}
	 * @throws Exception
	 */
	@Override
	public Response updateMissione(Missione missione) throws Exception {
		return Response.ok(this.missioneDelegate.updateMissione(missione)).build();
	}

	/**
	 * @param missionID
	 * @return {@link Response}
	 * @throws Exception
	 */
	@Override
	public Response deleteMissione(String missionID) throws Exception {
		return Response.ok(this.missioneDelegate.deleteMissione(missionID)).build();
	}

	/**
	 * @param request
	 * @return {@link Response}
	 * @throws Exception
	 */
	@Override
	public Response notifyMissionAdministration(NotificationMissionRequest request) throws Exception {
		return Response.ok(this.missioneDelegate.notifyMissionAdministration(request)).build();
	}

	/**
	 * @param request
	 * @return {@link Response}
	 * @throws Exception
	 */
	@Override
	public Response notifyRimborsoMissionAdministration(NotificationMissionRequest request) throws Exception {
		return Response.ok(this.missioneDelegate.notifyRimborsoMissionAdministration(request)).build();
	}

	/**
	 * @param missione
	 * @return {@link Response}
	 * @throws Exception
	 */
	@Override
	public Response updateRimborso(Missione missione) throws Exception {
		return Response.ok(this.missioneDelegate.updateRimborso(missione)).build();
	}

	/**
	 * @param missionID
	 * @return {@link Response}
	 * @throws Exception
	 */
	@Override
	public Response downloadMissioneAsPdf(String missionID) throws Exception {
		return Response.ok(this.missioneDelegate.downloadMissioneAsPdf(missionID))
				.header("Content-Disposition", "attachment; filename=Missione.pdf").build();
	}

	/**
	 * @param missionID
	 * @return {@link Response}
	 * @throws Exception
	 */
	@Override
	public Response downloadVeicoloMissioneAsPdf(String missionID) throws Exception {
		return Response.ok(this.missioneDelegate.downloadVeicoloMissioneAsPdf(missionID))
				.header("Content-Disposition", "attachment; filename=Missione.pdf").build();
	}

	/**
	 * @param missionID
	 * @return {@link Response}
	 * @throws Exception
	 */
	@Override
	public Response downloadRimborsoMissioneAsPdf(String missionID) throws Exception {
		return Response.ok(this.missioneDelegate.downloadRimborsoMissioneAsPdf(missionID))
				.header("Content-Disposition", "attachment; filename=RimborsoMissione.pdf").build();
	}

	/**
	 * @param location
	 * @return {@link Response}
	 * @throws Exception
	 */
	@Override
	public Response getGeocoderStoreForMissioneLocation(String location) throws Exception {
		return Response.ok(this.missioneDelegate.getGeocoderStoreForMissioneLocation(location)).build();
	}

	/**
	 * @param start
	 * @param end
	 * @return {@link Response}
	 * @throws Exception
	 */
	@Override
	public Response getDistanceForMissione(String start, String end) throws Exception {
		return Response.ok(this.missioneDelegate.getDistanceForMissione(start, end)).build();
	}

	/**
	 * @return
	 * @throws Exception
	 */
	@Override
	public Response getStatistiche() throws Exception {
		return Response.ok(this.missioneDelegate.getStatistiche()).build();

	}

	/**
	 * 
	 * @param missione
	 * @return
	 * @throws Exception
	 */
	@Override
	public Response updateMissioneForAnticipo(Missione missione) throws Exception {
		return Response.ok(this.missioneDelegate.updateMissioneForAnticipo(missione)).build();
	}

	/**
	 * @param missionID
	 * @return {@link Response}
	 * @throws Exception
	 */
	@Override
	public Response downloadAnticipoPagamentoAsPdf(String missionID) throws Exception {
		return Response.ok(this.missioneDelegate.downloadAnticipoPagamentoAsPdf(missionID))
				.header("Content-Disposition", "attachment; filename=AnticipoPagamento.pdf").build();
	}
}
