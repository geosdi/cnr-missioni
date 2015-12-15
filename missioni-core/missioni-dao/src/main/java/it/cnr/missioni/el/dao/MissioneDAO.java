package it.cnr.missioni.el.dao;

import java.util.List;

import org.geosdi.geoplatform.experimental.el.api.mapper.GPBaseMapper;
import org.geosdi.geoplatform.experimental.el.dao.AbstractElasticSearchDAO;

import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.model.utente.Utente;

/**
 * @author Salvia Vito
 */
public class MissioneDAO extends AbstractElasticSearchDAO<Missione>  implements IMissioneDAO{

	@Override
	public List<Missione> findLasts() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <Mapper extends GPBaseMapper<Missione>> void setMapper(Mapper theMapper) {
		// TODO Auto-generated method stub
		
	}



}
