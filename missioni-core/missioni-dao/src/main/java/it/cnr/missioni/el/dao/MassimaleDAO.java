package it.cnr.missioni.el.dao;

import it.cnr.missioni.el.model.search.builder.IMassimaleSearchBuilder;
import it.cnr.missioni.model.configuration.Massimale;
import org.elasticsearch.search.sort.SortOrder;
import org.geosdi.geoplatform.experimental.el.api.mapper.GPBaseMapper;
import org.geosdi.geoplatform.experimental.el.dao.AbstractElasticSearchDAO;
import org.geosdi.geoplatform.experimental.el.index.GPIndexCreator;
import org.geosdi.geoplatform.experimental.el.search.bool.IBooleanSearch;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Salvia Vito
 */
@Component(value = "massimaleDAO")
public class MassimaleDAO extends AbstractElasticSearchDAO<Massimale> implements IMassimaleDAO {

    @Resource(name = "massimaleMapper")
    @Override
    public <Mapper extends GPBaseMapper<Massimale>> void setMapper(Mapper theMapper) {
        this.mapper = theMapper;
    }

    @Resource(name = "massimaleIndexCreator")
    @Override
    public <IC extends GPIndexCreator> void setIndexCreator(IC ic) {
        super.setIndexCreator(ic);
    }

    /**
     * @return
     * @throws Exception
     */
    @Override
    public List<Massimale> findLasts() throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @param massimaleSearchBuilder
     * @return
     * @throws Exception
     */
    @Override
    public IPageResult<Massimale> findMassimaleByQuery(IMassimaleSearchBuilder massimaleSearchBuilder) throws Exception {
        List<Massimale> listaMassimale = new ArrayList<Massimale>();
        logger.debug("###############Try to find Massimale by Query: {}\n\n");
        Integer size = massimaleSearchBuilder.isAll() ? count().intValue() : massimaleSearchBuilder.getSize();
        return super.find(new MultiFieldsSearch(massimaleSearchBuilder.getFieldSort(), SortOrder.DESC,massimaleSearchBuilder.getFrom(),size,massimaleSearchBuilder.getListAbstractBooleanSearch().toArray(new IBooleanSearch[massimaleSearchBuilder.getListAbstractBooleanSearch().size()])));
    }

}
