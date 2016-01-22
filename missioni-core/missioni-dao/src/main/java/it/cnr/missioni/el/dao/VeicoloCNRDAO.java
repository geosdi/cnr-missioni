package it.cnr.missioni.el.dao;

import java.util.List;

import javax.annotation.Resource;

import org.geosdi.geoplatform.experimental.el.api.mapper.GPBaseMapper;
import org.geosdi.geoplatform.experimental.el.dao.AbstractElasticSearchDAO;
import org.geosdi.geoplatform.experimental.el.index.GPIndexCreator;
import org.springframework.stereotype.Component;

import it.cnr.missioni.model.prenotazione.VeicoloCNR;

/**
 * @author Salvia Vito
 */
@Component(value = "veicoloCNRDAO")
public class VeicoloCNRDAO extends AbstractElasticSearchDAO<VeicoloCNR> implements IVeicoloCNRDAO {

	

	@Resource(name = "veicoloCNRMapper")
	@Override
	public <Mapper extends GPBaseMapper<VeicoloCNR>> void setMapper(Mapper theMapper) {
		this.mapper = theMapper;
	}

	@Resource(name = "veicoloCNRIndexCreator")
	@Override
	public <IC extends GPIndexCreator> void setIndexCreator(IC ic) {
		super.setIndexCreator(ic);
	}

	/**
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<VeicoloCNR> findLasts() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}



}
