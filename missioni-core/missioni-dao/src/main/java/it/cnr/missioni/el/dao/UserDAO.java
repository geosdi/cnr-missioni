package it.cnr.missioni.el.dao;

import it.cnr.missioni.el.model.search.builder.IUserSearchBuilder;
import it.cnr.missioni.model.user.User;
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
        return super.find(new MultiFieldsSearch(userSearchBuilder.getFieldSort(), SortOrder.DESC,userSearchBuilder.getFrom(),size,userSearchBuilder.getListAbstractBooleanSearch().stream().toArray(IBooleanSearch[]::new)));
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
