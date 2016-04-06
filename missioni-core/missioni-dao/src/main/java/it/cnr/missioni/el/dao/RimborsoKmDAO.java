package it.cnr.missioni.el.dao;

import com.google.common.base.Preconditions;
import it.cnr.missioni.el.model.search.builder.IRimborsoKmSearchBuilder;
import it.cnr.missioni.model.configuration.RimborsoKm;
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
    public PageResult<RimborsoKm> findRimborsoKmByQuery(IRimborsoKmSearchBuilder rimborsoKmSearchBuilder)
            throws Exception {
        List<RimborsoKm> listaRimborsoKm = new ArrayList<RimborsoKm>();
        logger.debug("###############Try to find RimboroKm by Query: {}\n\n");
        SearchResponse searchResponse = (this.elastichSearchClient.prepareSearch(getIndexName())
                .setTypes(getIndexType()).setQuery(rimborsoKmSearchBuilder.buildQuery())
        ).execute()
                .actionGet();
        if (searchResponse.status() != RestStatus.OK) {
            throw new IllegalStateException("Error in Elastic Search Query.");
        }
        for (SearchHit searchHit : searchResponse.getHits().hits()) {
            RimborsoKm rimborsoKm = this.mapper.read(searchHit.getSourceAsString());
            if (!rimborsoKm.isIdSetted()) {
                rimborsoKm.setId(searchHit.getId());
            }
            listaRimborsoKm.add(rimborsoKm);
        }
        return new PageResult<RimborsoKm>(searchResponse.getHits().getTotalHits(), listaRimborsoKm);
    }


    @Override
    public RimborsoKm persist(RimborsoKm document) throws Exception {
        logger.debug("#################Try to insert {}\n\n", document);
        Preconditions.checkArgument((count().intValue() == 0), "Rimborso Km already inserted");
        return super.persist(document);
    }

}
