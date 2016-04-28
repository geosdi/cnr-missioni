package it.cnr.missioni.el.dao;

import it.cnr.missioni.el.model.search.builder.IQualificaUserSearchBuilder;
import it.cnr.missioni.model.configuration.QualificaUser;
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
@Component(value = "qualificaUserDAO")
public class QualificaUserDAO extends AbstractElasticSearchDAO<QualificaUser> implements IQualificaUserDAO {

    @Resource(name = "qualificaUserMapper")
    @Override
    public <Mapper extends GPBaseMapper<QualificaUser>> void setMapper(Mapper theMapper) {
        this.mapper = theMapper;
    }

    @Resource(name = "qualificaUserIndexCreator")
    @Override
    public <IC extends GPIndexCreator> void setIndexCreator(IC ic) {
        super.setIndexCreator(ic);
    }

    /**
     * @return
     * @throws Exception
     */
    @Override
    public List<QualificaUser> findLasts() throws Exception {
        return null;
    }

    /**
     * @param qualificaUserSearchBuilder
     * @return
     * @throws Exception
     */
    @Override
    public IPageResult<QualificaUser> findQualificaUserByQuery(IQualificaUserSearchBuilder qualificaUserSearchBuilder)
            throws Exception {
        List<QualificaUser> listaQualificaUser = new ArrayList<QualificaUser>();
        logger.debug("###############Try to find Qualifica User by Query: {}\n\n");
        Integer size = qualificaUserSearchBuilder.isAll() ? count().intValue() : qualificaUserSearchBuilder.getSize();
        return super.find(new MultiFieldsSearch(qualificaUserSearchBuilder.getFieldSort(), SortOrder.DESC,qualificaUserSearchBuilder.getFrom(),size,qualificaUserSearchBuilder.getListAbstractBooleanSearch().toArray(new IBooleanSearch[qualificaUserSearchBuilder.getListAbstractBooleanSearch().size()])));
    }

}
