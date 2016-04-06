package it.cnr.missioni.el.dao;

import it.cnr.missioni.model.configuration.Direttore;
import org.geosdi.geoplatform.experimental.el.api.mapper.GPBaseMapper;
import org.geosdi.geoplatform.experimental.el.dao.AbstractElasticSearchDAO;
import org.geosdi.geoplatform.experimental.el.index.GPIndexCreator;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Salvia Vito
 */
@Component(value = "direttoreDAO")
public class DirettoreDAO extends AbstractElasticSearchDAO<Direttore> implements IDirettoreDAO {

    @Resource(name = "direttoreMapper")
    @Override
    public <Mapper extends GPBaseMapper<Direttore>> void setMapper(Mapper theMapper) {
        this.mapper = theMapper;
    }

    @Resource(name = "direttoreIndexCreator")
    @Override
    public <IC extends GPIndexCreator> void setIndexCreator(IC ic) {
        super.setIndexCreator(ic);
    }

    /**
     * @return
     * @throws Exception
     */
    @Override
    public List<Direttore> findLasts() throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

}
