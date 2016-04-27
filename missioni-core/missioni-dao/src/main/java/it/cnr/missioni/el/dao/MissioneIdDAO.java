package it.cnr.missioni.el.dao;

import java.util.List;

import javax.annotation.Resource;

import org.geosdi.geoplatform.experimental.el.api.mapper.GPBaseMapper;
import org.geosdi.geoplatform.experimental.el.dao.AbstractElasticSearchDAO;
import org.geosdi.geoplatform.experimental.el.index.GPIndexCreator;
import org.springframework.stereotype.Component;

import com.google.common.base.Preconditions;

import it.cnr.missioni.model.configuration.MissioneId;

/**
 * @author Salvia Vito
 */
@Component(value = "missioneIdDAO")
public class MissioneIdDAO extends AbstractElasticSearchDAO<MissioneId> implements IMissioneIdDAO {

    @Resource(name = "missioneIdMapper")
    @Override
    public <Mapper extends GPBaseMapper<MissioneId>> void setMapper(Mapper theMapper) {
        this.mapper = theMapper;
    }

    @Resource(name = "missioneIdIndexCreator")
    @Override
    public <IC extends GPIndexCreator> void setIndexCreator(IC ic) {
        super.setIndexCreator(ic);
    }
    
    @Override
    public MissioneId persist(MissioneId document) throws Exception {
        logger.debug("#################Try to insert {}\n\n", document);
        Preconditions.checkArgument((count().intValue() == 0), "Missione Id already inserted");
        return super.persist(document);
    }

    /**
     * @return
     * @throws Exception
     */
    @Override
    public List<MissioneId> findLasts() throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

}
