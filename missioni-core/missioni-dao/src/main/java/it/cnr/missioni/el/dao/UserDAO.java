package it.cnr.missioni.el.dao;

import it.cnr.missioni.el.model.search.builder.IUserSearchBuilder;
import it.cnr.missioni.model.user.User;
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
@Component(value = "userDAO")
public class UserDAO extends AbstractElasticSearchDAO<User> implements IUserDAO {

    /**
     * @param userSearchBuilder
     * @return
     * @throws Exception
     */
    @Override
    public PageResult<User> findUserByQuery(IUserSearchBuilder userSearchBuilder) throws Exception {
        List<User> listaUsers = new ArrayList<User>();
        logger.debug("###############Try to find Users by Query: {}\n\n");
        Page p = new Page(userSearchBuilder.getFrom(), userSearchBuilder.isAll() ? count().intValue() : userSearchBuilder.getSize());
        SearchResponse searchResponse = p
                .buildPage(this.elastichSearchClient.prepareSearch(getIndexName()).setTypes(getIndexType())
                        .setQuery(userSearchBuilder.buildQuery()))
                .addSort(userSearchBuilder.getFieldSort(), userSearchBuilder.getSortOrder()).execute().actionGet();
        if (searchResponse.status() != RestStatus.OK) {
            throw new IllegalStateException("Error in Elastic Search Query.");
        }
        for (SearchHit searchHit : searchResponse.getHits().hits()) {
            User user = this.mapper.read(searchHit.getSourceAsString());
            if (!user.isIdSetted()) {
                user.setId(searchHit.getId());
            }
            listaUsers.add(user);
        }
        return new PageResult<User>(searchResponse.getHits().getTotalHits(), listaUsers);
    }

    @Override
    public List<User> findLasts() throws Exception {
        return null;
    }

    @Resource(name = "userMapper")
    @Override
    public <Mapper extends GPBaseMapper<User>> void setMapper(Mapper theMapper) {
        this.mapper = theMapper;
    }

    @Resource(name = "userIndexCreator")
    @Override
    public <IC extends GPIndexCreator> void setIndexCreator(IC ic) {
        super.setIndexCreator(ic);
    }

}
