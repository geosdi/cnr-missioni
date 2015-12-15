package it.cnr.missioni.el.dao;

import java.util.List;

import javax.annotation.Resource;

import org.geosdi.geoplatform.experimental.el.api.mapper.GPBaseMapper;
import org.geosdi.geoplatform.experimental.el.dao.AbstractElasticSearchDAO;
import org.geosdi.geoplatform.experimental.el.index.GPIndexCreator;
import org.springframework.stereotype.Component;

import it.cnr.missioni.model.missione.Missione;

/**
 * @author Salvia Vito
 */
@Component(value = "missioneDAO")
public class MissioneDAO extends AbstractElasticSearchDAO<Missione>  implements IMissioneDAO{

	@Override
	public List<Missione> findLasts() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Resource(name = "missioneMapper")
	@Override
	public <Mapper extends GPBaseMapper<Missione>> void setMapper(Mapper theMapper) {
		this.mapper = theMapper;
	}

	@Resource(name = "missioneIndexCreator")
	@Override
	public <IC extends GPIndexCreator> void setIndexCreator(IC ic) {
		super.setIndexCreator(ic);
	}



}
