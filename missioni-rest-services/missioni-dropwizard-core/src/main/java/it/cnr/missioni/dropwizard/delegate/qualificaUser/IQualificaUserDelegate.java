package it.cnr.missioni.dropwizard.delegate.qualificaUser;

import it.cnr.missioni.model.configuration.QualificaUser;
import it.cnr.missioni.rest.api.response.qualificaUser.QualificaUserStore;

/**
 * 
 * @author Salvia Vito
 *
 */
public interface IQualificaUserDelegate {

/**
 * 
 * @param from
 * @param size
 * @param all
 * @return
 * @throws Exception
 */

	QualificaUserStore getQualificaUserByQuery(int from, int size,boolean all) throws Exception;

	/**
	 * 
	 * @param qualificaUser
	 * @return
	 * @throws Exception
	 */
	String addQualificaUser(QualificaUser qualificaUser) throws Exception;

	/**
	 * 
	 * @param qualificaUser
	 * @return
	 * @throws Exception
	 */
	Boolean updateQualificaUser(QualificaUser qualificaUser) throws Exception;

	/**
	 * 
	 * @param qualificaUserID
	 * @return
	 * @throws Exception
	 */
	Boolean deleteQualificaUser(String qualificaUserID) throws Exception;
}
