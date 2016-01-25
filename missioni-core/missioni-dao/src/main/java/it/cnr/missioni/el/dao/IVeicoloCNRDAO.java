package it.cnr.missioni.el.dao;

import org.geosdi.geoplatform.experimental.el.dao.GPElasticSearchDAO;
import org.geosdi.geoplatform.experimental.el.dao.PageResult;

import it.cnr.missioni.el.model.search.builder.VeicoloCNRSearchBuilder;
import it.cnr.missioni.model.prenotazione.VeicoloCNR;

/**
 * @author Salvia Vito
 */
public interface IVeicoloCNRDAO extends GPElasticSearchDAO.GPElasticSearchBaseDAO<VeicoloCNR> {

	PageResult<VeicoloCNR> findVeicoloCNRByQuery(VeicoloCNRSearchBuilder veicoloCNRSearchBuilder) throws Exception;


}
