package it.cnr.missioni.el.dao;

import java.util.List;

import org.geosdi.geoplatform.experimental.el.api.mapper.GPBaseMapper;
import org.geosdi.geoplatform.experimental.el.dao.AbstractElasticSearchDAO;

import it.cnr.missioni.model.utente.Utente;

/**
 * @author Salvia Vito
 */
public class UtenteDAO extends AbstractElasticSearchDAO<Utente>  implements IUtenteDAO{

	@Override
	public List<Utente> findLasts() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <Mapper extends GPBaseMapper<Utente>> void setMapper(Mapper theMapper) {
		// TODO Auto-generated method stub
		
	}



}
