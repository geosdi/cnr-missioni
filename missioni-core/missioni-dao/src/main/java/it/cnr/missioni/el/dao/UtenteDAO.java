package it.cnr.missioni.el.dao;

import java.util.List;

import javax.annotation.Resource;

import org.geosdi.geoplatform.experimental.el.api.mapper.GPBaseMapper;
import org.geosdi.geoplatform.experimental.el.dao.AbstractElasticSearchDAO;
import org.geosdi.geoplatform.experimental.el.index.GPIndexCreator;
import org.springframework.stereotype.Component;

import it.cnr.missioni.model.utente.Utente;

/**
 * @author Salvia Vito
 */
@Component(value = "utenteDAO")
public class UtenteDAO extends AbstractElasticSearchDAO<Utente>  implements IUtenteDAO{

	@Override
	public List<Utente> findLasts() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Resource(name = "utenteMapper")
	@Override
	public <Mapper extends GPBaseMapper<Utente>> void setMapper(Mapper theMapper) {
		this.mapper = theMapper;
	}

	@Resource(name = "utenteIndexCreator")
	@Override
	public <IC extends GPIndexCreator> void setIndexCreator(IC ic) {
		super.setIndexCreator(ic);
	}



}
