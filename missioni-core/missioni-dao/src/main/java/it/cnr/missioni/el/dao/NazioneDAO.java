package it.cnr.missioni.el.dao;

import it.cnr.missioni.el.model.search.builder.INazioneSearchBuilder;
import it.cnr.missioni.model.configuration.Nazione;
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
@Component(value = "nazioneDAO")
public class NazioneDAO extends AbstractElasticSearchDAO<Nazione> implements INazioneDAO {

    @Resource(name = "nazioneMapper")
    @Override
    public <Mapper extends GPBaseMapper<Nazione>> void setMapper(Mapper theMapper) {
        this.mapper = theMapper;
    }

    @Resource(name = "nazioneIndexCreator")
    @Override
    public <IC extends GPIndexCreator> void setIndexCreator(IC ic) {
        super.setIndexCreator(ic);
    }

    /**
     * @return
     * @throws Exception
     */
    @Override
    public List<Nazione> findLasts() throws Exception {
        return null;
    }

    /**
     * @param nazioneSearchBuilder
     * @return
     * @throws Exception
     */
    @Override
    public IPageResult<Nazione> findNazioneByQuery(INazioneSearchBuilder nazioneSearchBuilder) throws Exception {
        List<Nazione> listaNazione = new ArrayList<Nazione>();
        logger.debug("###############Try to find Nazione by Query: {}\n\n");
        Integer size = nazioneSearchBuilder.isAll() ? count().intValue() : nazioneSearchBuilder.getSize();
        return super.find(new MultiFieldsSearch(nazioneSearchBuilder.getFieldSort(), SortOrder.DESC,nazioneSearchBuilder.getFrom(),size,nazioneSearchBuilder.getListAbstractBooleanSearch().toArray(new IBooleanSearch[nazioneSearchBuilder.getListAbstractBooleanSearch().size()])));
    }

}
