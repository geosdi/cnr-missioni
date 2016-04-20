package it.cnr.missioni.el.dao;

import it.cnr.missioni.el.model.search.builder.IVeicoloCNRSearchBuilder;
import it.cnr.missioni.model.prenotazione.VeicoloCNR;
import org.geosdi.geoplatform.experimental.el.dao.GPElasticSearchDAO;

/**
 * @author Salvia Vito
 */
public interface IVeicoloCNRDAO extends GPElasticSearchDAO.GPElasticSearchBaseDAO<VeicoloCNR> {

    IPageResult<VeicoloCNR> findVeicoloCNRByQuery(IVeicoloCNRSearchBuilder veicoloCNRSearchBuilder) throws Exception;


}
