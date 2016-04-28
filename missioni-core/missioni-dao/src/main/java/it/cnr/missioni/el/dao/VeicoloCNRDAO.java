package it.cnr.missioni.el.dao;

import it.cnr.missioni.el.model.search.builder.IVeicoloCNRSearchBuilder;
import it.cnr.missioni.model.prenotazione.VeicoloCNR;
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
@Component(value = "veicoloCNRDAO")
public class VeicoloCNRDAO extends AbstractElasticSearchDAO<VeicoloCNR> implements IVeicoloCNRDAO {

    @Resource(name = "veicoloCNRMapper")
    @Override
    public <Mapper extends GPBaseMapper<VeicoloCNR>> void setMapper(Mapper theMapper) {
        this.mapper = theMapper;
    }

    @Resource(name = "veicoloCNRIndexCreator")
    @Override
    public <IC extends GPIndexCreator> void setIndexCreator(IC ic) {
        super.setIndexCreator(ic);
    }

    /**
     * @return
     * @throws Exception
     */
    @Override
    public List<VeicoloCNR> findLasts() throws Exception {
        return null;
    }

    /**
     * @param veicoloCNRSearchBuilder
     * @return
     * @throws Exception
     */
    @Override
    public IPageResult<VeicoloCNR> findVeicoloCNRByQuery(IVeicoloCNRSearchBuilder veicoloCNRSearchBuilder) throws Exception {
        List<VeicoloCNR> listaVeicoliCNR = new ArrayList<VeicoloCNR>();
        logger.debug("###############Try to find VeicoloCNR by Query: {}\n\n");
        Integer size = veicoloCNRSearchBuilder.isAll() ? count().intValue() : veicoloCNRSearchBuilder.getSize();
        return super.find(new MultiFieldsSearch(veicoloCNRSearchBuilder.getFieldSort(), SortOrder.DESC,veicoloCNRSearchBuilder.getFrom(),size,veicoloCNRSearchBuilder.getListAbstractBooleanSearch().toArray(new IBooleanSearch[veicoloCNRSearchBuilder.getListAbstractBooleanSearch().size()])));
    }

}
