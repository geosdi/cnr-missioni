package it.cnr.missioni.dropwizard.delegate.prenotazione;

import com.fasterxml.uuid.EthernetAddress;
import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedGenerator;
import it.cnr.missioni.el.dao.IPrenotazioneDAO;
import it.cnr.missioni.el.model.search.builder.IPrenotazioneSearchBuilder;
import it.cnr.missioni.model.prenotazione.Prenotazione;
import it.cnr.missioni.rest.api.response.prenotazione.PrenotazioniStore;
import org.geosdi.geoplatform.exception.IllegalParameterFault;
import org.geosdi.geoplatform.experimental.el.dao.GPPageableElasticSearchDAO;
import org.geosdi.geoplatform.logger.support.annotation.GeoPlatformLog;
import org.joda.time.DateTime;
import org.slf4j.Logger;

import javax.annotation.Resource;

/**
 * 
 * @author Salvia Vito
 *
 */
class PrenotazioneDelegate implements IPrenotazioneDelegate {

	static {
		gen = Generators.timeBasedGenerator(EthernetAddress.fromInterface());
	}

	private static final TimeBasedGenerator gen;
	//
	@GeoPlatformLog
	private static Logger logger;
	//
	@Resource(name = "prenotazioneDAO")
	private IPrenotazioneDAO prenotazioneDAO;

	/**
	 * @param prenotazione
	 * @return
	 * @throws Exception
	 */
	@Override
	public String addPrenotazione(Prenotazione prenotazione) throws Exception {
		if ((prenotazione == null)) {
			throw new IllegalParameterFault("The Parameter prenotazione must not be null");
		}
		if (prenotazione.getId() == null)
			prenotazione.setId(gen.generate().toString());
		return this.prenotazioneDAO.persist(prenotazione).getId();
	}

	/**
	 * @param prenotazione
	 * @return
	 * @throws Exception
	 */
	@Override
	public Boolean updatePrenotazione(Prenotazione prenotazione) throws Exception {
		if ((prenotazione == null)) {
			throw new IllegalParameterFault("The Parameter prenotazione must not be null");
		}
		this.prenotazioneDAO.update(prenotazione);
		return Boolean.TRUE;
	}

	/**
	 * @param prenotazioneID
	 * @return
	 * @throws Exception
	 */
	@Override
	public Boolean deletePrenotazione(String prenotazioneID) throws Exception {
		if ((prenotazioneID == null) || (prenotazioneID.isEmpty())) {
			throw new IllegalParameterFault("The Parameter prenotazioneID must not be null " + "or an Empty String.");
		}
		this.prenotazioneDAO.delete(prenotazioneID);
		return Boolean.TRUE;
	}

	/**
	 * @param dataFrom
	 * @param dataTo
	 * @return
	 * @throws Exception
	 */
	@Override
	public PrenotazioniStore getPrenotazioneByQuery(Long dataFrom, Long dataTo) throws Exception {
		IPrenotazioneSearchBuilder prenotazioneSearchBuilder = IPrenotazioneSearchBuilder.PrenotazioneSearchBuilder.getPrenotazioneSearchBuilder()
				.withRangeData(new DateTime(dataFrom), new DateTime(dataTo));
		GPPageableElasticSearchDAO.IPageResult<Prenotazione> pageResult = this.prenotazioneDAO.findPrenotazioneByQuery(prenotazioneSearchBuilder);
		PrenotazioniStore prenotazioniStore = new PrenotazioniStore();
		prenotazioniStore.setPrenotazioni(pageResult.getResults());
		prenotazioniStore.setTotale(pageResult.getTotal());
		return prenotazioniStore;
	}

}
