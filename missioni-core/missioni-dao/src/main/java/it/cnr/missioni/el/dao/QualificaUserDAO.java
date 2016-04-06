package it.cnr.missioni.el.dao;

import it.cnr.missioni.el.model.search.builder.IQualificaUserSearchBuilder;
import it.cnr.missioni.model.configuration.QualificaUser;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.geosdi.geoplatform.experimental.el.api.mapper.GPBaseMapper;
import org.geosdi.geoplatform.experimental.el.dao.AbstractElasticSearchDAO;
import org.geosdi.geoplatform.experimental.el.dao.PageResult;
import org.geosdi.geoplatform.experimental.el.index.GPIndexCreator;
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
    public PageResult<QualificaUser> findQualificaUserByQuery(IQualificaUserSearchBuilder qualificaUserSearchBuilder)
            throws Exception {
        List<QualificaUser> listaQualificaUser = new ArrayList<QualificaUser>();
        logger.debug("###############Try to find Qualifica User by Query: {}\n\n");
        Page p = new Page(qualificaUserSearchBuilder.getFrom(), qualificaUserSearchBuilder.isAll() ? count().intValue() : qualificaUserSearchBuilder.getSize());
        SearchResponse searchResponse = p
                .buildPage(this.elastichSearchClient.prepareSearch(getIndexName()).setTypes(getIndexType())
                        .setQuery(qualificaUserSearchBuilder.buildQuery()))
                .addSort(qualificaUserSearchBuilder.getFieldSort(), qualificaUserSearchBuilder.getSortOrder()).execute().actionGet();
        if (searchResponse.status() != RestStatus.OK) {
            throw new IllegalStateException("Error in Elastic Search Query.");
        }
        for (SearchHit searchHit : searchResponse.getHits().hits()) {
            QualificaUser qualificaUser = this.mapper.read(searchHit.getSourceAsString());
            if (!qualificaUser.isIdSetted()) {
                qualificaUser.setId(searchHit.getId());
            }
            listaQualificaUser.add(qualificaUser);
        }
        return new PageResult<QualificaUser>(searchResponse.getHits().getTotalHits(), listaQualificaUser);
    }

}
