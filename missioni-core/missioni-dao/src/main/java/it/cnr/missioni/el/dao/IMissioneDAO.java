package it.cnr.missioni.el.dao;

import java.util.List;

import org.geosdi.geoplatform.experimental.el.dao.GPElasticSearchDAO;

import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.model.missione.StatoEnum;
import it.cnr.missioni.model.utente.Utente;


/**
 * @author Salvia Vito
 */
public interface IMissioneDAO extends GPElasticSearchDAO.GPElasticSearchBaseDAO<Missione>{
	
/**
 * 
 * @param idUtente
 * @param stato
 * @return
 * @throws Exception
 */
	List<Missione> findMissioneByUtenteStato(String idUtente,StatoEnum stato) throws Exception;
	

}
