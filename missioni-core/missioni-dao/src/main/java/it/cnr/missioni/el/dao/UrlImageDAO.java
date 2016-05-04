package it.cnr.missioni.el.dao;

import com.google.common.base.Preconditions;
import it.cnr.missioni.model.configuration.UrlImage;

import org.geosdi.geoplatform.experimental.el.api.mapper.GPBaseMapper;
import org.geosdi.geoplatform.experimental.el.dao.AbstractElasticSearchDAO;
import org.geosdi.geoplatform.experimental.el.index.GPIndexCreator;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Salvia Vito
 */
@Component(value = "urlImageDAO")
public class UrlImageDAO extends AbstractElasticSearchDAO<UrlImage> implements IUrlImageDAO {

    @Resource(name = "urlImageMapper")
    @Override
    public <Mapper extends GPBaseMapper<UrlImage>> void setMapper(Mapper theMapper) {
        this.mapper = theMapper;
    }

    @Resource(name = "urlImageIndexCreator")
    @Override
    public <IC extends GPIndexCreator> void setIndexCreator(IC ic) {
        super.setIndexCreator(ic);
    }

    @Override
    public UrlImage persist(UrlImage document) throws Exception {
        logger.debug("#################Try to insert {}\n\n", document);
        Preconditions.checkArgument((count().intValue() == 0), "Url Image Id already inserted");
        return super.persist(document);
    }

    /**
     * @return
     * @throws Exception
     */
    @Override
    public List<UrlImage> findLasts() throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

}
