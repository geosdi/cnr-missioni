package it.cnr.missioni.el.dao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.search.sort.SortOrder;
import org.geosdi.geoplatform.experimental.el.api.mapper.GPBaseMapper;
import org.geosdi.geoplatform.experimental.el.dao.AbstractElasticSearchDAO;
import org.geosdi.geoplatform.experimental.el.index.GPIndexCreator;
import org.geosdi.geoplatform.experimental.el.search.bool.BooleanExactSearch;
import org.geosdi.geoplatform.experimental.el.search.bool.IBooleanSearch;
import org.geosdi.geoplatform.experimental.el.search.bool.IBooleanSearch.BooleanQueryType;
import org.springframework.stereotype.Component;

import it.cnr.missioni.el.model.search.builder.IUserSearchBuilder;
import it.cnr.missioni.el.model.search.builder.SearchConstants;
import it.cnr.missioni.model.user.User;

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
    public IPageResult<User> findUserByQuery(IUserSearchBuilder userSearchBuilder) throws Exception {
        List<User> listaUsers = new ArrayList<User>();
        logger.debug("###############Try to find Users by Query: {}\n\n");
        Integer size = userSearchBuilder.isAll() ? count().intValue() : userSearchBuilder.getSize();
        return super.find(new MultiFieldsSearch(userSearchBuilder.getFieldSort(), SortOrder.ASC,userSearchBuilder.getFrom(),size,userSearchBuilder.getListAbstractBooleanSearch().toArray(new IBooleanSearch[userSearchBuilder.getListAbstractBooleanSearch().size()])));
    }
    
    /**
     * 
     * @param userSearchBuilder
     * @return
     * @throws Exception
     */
    public User findUserByUsername(String username) throws Exception{
        logger.debug("###############Try to find Users by Username: {}\n\n");
        List<User> users = super.find(new MultiFieldsSearch(new BooleanExactSearch(SearchConstants.USER_FIELD_USERNAME,
                username, BooleanQueryType.MUST, Operator.AND))).getResults();
        return ((users != null) && (users.size() == 1)) ? users.get(0) : null;
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
