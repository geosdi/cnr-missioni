package it.cnr.missioni.dropwizard.delegate.qualificaUser;

import javax.annotation.Resource;

import org.geosdi.geoplatform.exception.IllegalParameterFault;
import org.geosdi.geoplatform.experimental.el.dao.PageResult;
import org.geosdi.geoplatform.logger.support.annotation.GeoPlatformLog;
import org.slf4j.Logger;

import com.fasterxml.uuid.EthernetAddress;
import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedGenerator;

import it.cnr.missioni.el.dao.IQualificaUserDAO;
import it.cnr.missioni.el.model.search.builder.QualificaUserSearchBuilder;
import it.cnr.missioni.model.configuration.QualificaUser;
import it.cnr.missioni.rest.api.response.qualificaUser.QualificaUserStore;

/**
 * 
 * @author Salvia Vito
 *
 */
class QualificaUserDelegate implements IQualificaUserDelegate {

	static {
		gen = Generators.timeBasedGenerator(EthernetAddress.fromInterface());
	}

	private static final TimeBasedGenerator gen;
	//
	@GeoPlatformLog
	private static Logger logger;
	//
	@Resource(name = "qualificaUserDAO")
	private IQualificaUserDAO qualificaUserDAO;

	/**
	 * 
	 * @param from
	 * @param size
	 * @return
	 * @throws Exception
	 */
	@Override
	public QualificaUserStore getQualificaUserByQuery(int from, int size, boolean all) throws Exception {

		QualificaUserSearchBuilder qualificaUserSearchBuilder = QualificaUserSearchBuilder
				.getQualificaUserSearchBuilder().withFrom(from).withSize(size).withAll(all);

		PageResult<QualificaUser> pageResult = this.qualificaUserDAO
				.findQualificaUserByQuery(qualificaUserSearchBuilder);

		QualificaUserStore qualificaUserStore = new QualificaUserStore();
		qualificaUserStore.setQualificaUser(pageResult.getResults());
		qualificaUserStore.setTotale(pageResult.getTotal());
		return qualificaUserStore;

	}

	/**
	 * 
	 * @param qualificaUser
	 * @return
	 * @throws Exception
	 */
	@Override
	public String addQualificaUser(QualificaUser qualificaUser) throws Exception {
		if ((qualificaUser == null)) {
			throw new IllegalParameterFault("The Parameter qualificaUser must not be null");
		}
		if (qualificaUser.getId() == null)
			qualificaUser.setId(gen.generate().toString());
		return this.qualificaUserDAO.persist(qualificaUser).getId();

	}

	/**
	 * 
	 * @param qualificaUser
	 * @return
	 * @throws Exception
	 */
	@Override
	public Boolean updateQualificaUser(QualificaUser qualificaUser) throws Exception {
		if ((qualificaUser == null)) {
			throw new IllegalParameterFault("The Parameter qualificaUser must not be null");
		}
		this.qualificaUserDAO.update(qualificaUser);
		return Boolean.TRUE;
	}

	/**
	 * 
	 * @param qualificaUserID
	 * @return
	 * @throws Exception
	 */
	@Override
	public Boolean deleteQualificaUser(String qualificaUserID) throws Exception {
		if ((qualificaUserID == null) || (qualificaUserID.isEmpty())) {
			throw new IllegalParameterFault("The Parameter qualificaUserID must not be null " + "or an Empty String.");
		}
		this.qualificaUserDAO.delete(qualificaUserID);
		return Boolean.TRUE;
	}

}
