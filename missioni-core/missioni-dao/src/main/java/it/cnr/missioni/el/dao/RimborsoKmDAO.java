package it.cnr.missioni.el.dao;

import com.google.common.base.Preconditions;
import it.cnr.missioni.el.model.search.builder.IRimborsoKmSearchBuilder;
import it.cnr.missioni.model.configuration.RimborsoKm;
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
@Component(value = "rimborsoKmDAO")
public class RimborsoKmDAO extends AbstractElasticSearchDAO<RimborsoKm> implements IRimborsoKmDAO {

    @Resource(name = "rimborsoKmMapper")
    @Override
    public <Mapper extends GPBaseMapper<RimborsoKm>> void setMapper(Mapper theMapper) {
        this.mapper = theMapper;
    }

    @Resource(name = "rimborsoKmIndexCreator")
    @Override
    public <IC extends GPIndexCreator> void setIndexCreator(IC ic) {
        super.setIndexCreator(ic);
    }

    /**
     * @return
     * @throws Exception
     */
    @Override
    public List<RimborsoKm> findLasts() throws Exception {
        return null;
    }

    /**
     * @param rimborsoKmSearchBuilder
     * @return
     * @throws Exception
     */
    @Override
    public IPageResult<RimborsoKm> findRimborsoKmByQuery(IRimborsoKmSearchBuilder rimborsoKmSearchBuilder)
            throws Exception {
        List<RimborsoKm> listaRimborsoKm = new ArrayList<RimborsoKm>();
        logger.debug("###############Try to find RimboroKm by Query: {}\n\n");
        return super.find(new MultiFieldsSearch("", SortOrder.DESC,0,1,null));
    }


    @Override
    public RimborsoKm persist(RimborsoKm document) throws Exception {
        logger.debug("#################Try to insert {}\n\n", document);
        Preconditions.checkArgument((count().intValue() == 0), "Rimborso Km already inserted");
        return super.persist(document);
    }

}
