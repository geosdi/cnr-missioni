package it.cnr.missioni.el.dao;

import it.cnr.missioni.el.model.search.builder.IPrenotazioneSearchBuilder;
import it.cnr.missioni.model.prenotazione.Prenotazione;
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
@Component(value = "prenotazioneDAO")
public class PrenotazioneDAO extends AbstractElasticSearchDAO<Prenotazione> implements IPrenotazioneDAO {

    @Resource(name = "prenotazioneMapper")
    @Override
    public <Mapper extends GPBaseMapper<Prenotazione>> void setMapper(Mapper theMapper) {
        this.mapper = theMapper;
    }

    @Resource(name = "prenotazioneIndexCreator")
    @Override
    public <IC extends GPIndexCreator> void setIndexCreator(IC ic) {
        super.setIndexCreator(ic);
    }

    /**
     * @return
     * @throws Exception
     */
    @Override
    public List<Prenotazione> findLasts() throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @param prenotazioneSearchBuilder
     * @return
     * @throws Exception
     */
    @Override
    public IPageResult<Prenotazione> findPrenotazioneByQuery(IPrenotazioneSearchBuilder prenotazioneSearchBuilder)
            throws Exception {
        List<Prenotazione> listaPrenotazioni = new ArrayList<Prenotazione>();
        logger.debug("###############Try to find Prenotazioni by Query: {}\n\n");
        return super.find(new MultiFieldsSearch("", SortOrder.DESC,0,count().intValue(),prenotazioneSearchBuilder.getListAbstractBooleanSearch().stream().toArray(IBooleanSearch[]::new)));
    }

}
