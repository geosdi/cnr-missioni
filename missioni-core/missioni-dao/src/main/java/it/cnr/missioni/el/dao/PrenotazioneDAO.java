package it.cnr.missioni.el.dao;

import java.util.List;

import javax.annotation.Resource;

import org.geosdi.geoplatform.experimental.el.api.mapper.GPBaseMapper;
import org.geosdi.geoplatform.experimental.el.dao.AbstractElasticSearchDAO;
import org.geosdi.geoplatform.experimental.el.index.GPIndexCreator;
import org.springframework.stereotype.Component;

import it.cnr.missioni.model.prenotazione.Prenotazione;

/**
 * @author Salvia Vito
 */
@Component(value = "prenotazioneDAO")
public class PrenotazioneDAO extends AbstractElasticSearchDAO<Prenotazione> implements IPrenotazioneDAO {

	

	@Resource(name = "prenotazioneMapper")
	@Override
	public <Mapper extends GPBaseMapper<Prenotazione>> void setMapper(Mapper theMapper) {
		this.mapper = theMapper;
	}

	@Resource(name = "prenotazioneIndexCreator")
	@Override
	public <IC extends GPIndexCreator> void setIndexCreator(IC ic) {
		super.setIndexCreator(ic);
	}

	/**
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<Prenotazione> findLasts() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}



}
