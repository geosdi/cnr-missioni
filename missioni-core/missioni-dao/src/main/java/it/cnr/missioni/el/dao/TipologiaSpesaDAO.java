package it.cnr.missioni.el.dao;

import it.cnr.missioni.el.model.search.builder.ITipologiaSpesaSearchBuilder;
import it.cnr.missioni.model.configuration.TipologiaSpesa;
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
@Component(value = "tipologiaSpesaDAO")
public class TipologiaSpesaDAO extends AbstractElasticSearchDAO<TipologiaSpesa> implements ITipologiaSpesaDAO {

    @Resource(name = "tipologiaSpesaMapper")
    @Override
    public <Mapper extends GPBaseMapper<TipologiaSpesa>> void setMapper(Mapper theMapper) {
        this.mapper = theMapper;
    }

    @Resource(name = "tipologiaSpesaIndexCreator")
    @Override
    public <IC extends GPIndexCreator> void setIndexCreator(IC ic) {
        super.setIndexCreator(ic);
    }

    /**
     * @return
     * @throws Exception
     */
    @Override
    public List<TipologiaSpesa> findLasts() throws Exception {
        return null;
    }

    /**
     * @param tipologiaSpesaSearchBuilder
     * @return
     * @throws Exception
     */
    @Override
    public IPageResult<TipologiaSpesa> findTipologiaSpesaByQuery(ITipologiaSpesaSearchBuilder tipologiaSpesaSearchBuilder)
            throws Exception {
        List<TipologiaSpesa> listaTipologiaSpesa = new ArrayList<TipologiaSpesa>();
        logger.debug("###############Try to find Tipologia Spesa by Query: {}\n\n");
        Integer size = tipologiaSpesaSearchBuilder.isAll() ? count().intValue() : tipologiaSpesaSearchBuilder.getSize();
        return super.find(new MultiFieldsSearch(tipologiaSpesaSearchBuilder.getFieldSort(), SortOrder.ASC,tipologiaSpesaSearchBuilder.getFrom(),size,tipologiaSpesaSearchBuilder.getListAbstractBooleanSearch().toArray(new IBooleanSearch[tipologiaSpesaSearchBuilder.getListAbstractBooleanSearch().size()])));
    }

}
