package it.cnr.missioni.dropwizard.delegate.massimale;

import com.fasterxml.uuid.EthernetAddress;
import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedGenerator;
import it.cnr.missioni.el.dao.IMassimaleDAO;
import it.cnr.missioni.el.model.search.builder.IMassimaleSearchBuilder;
import it.cnr.missioni.model.configuration.Massimale;
import it.cnr.missioni.rest.api.response.massimale.MassimaleStore;
import org.geosdi.geoplatform.exception.IllegalParameterFault;
import org.geosdi.geoplatform.experimental.el.dao.PageResult;
import org.geosdi.geoplatform.logger.support.annotation.GeoPlatformLog;
import org.slf4j.Logger;

import javax.annotation.Resource;

/**
 * 
 * @author Salvia Vito
 *
 */
class MassimaleDelegate implements IMassimaleDelegate {

	static {
		gen = Generators.timeBasedGenerator(EthernetAddress.fromInterface());
	}

	private static final TimeBasedGenerator gen;
	//
	@GeoPlatformLog
	private static Logger logger;
	//
	@Resource(name = "massimaleDAO")
	private IMassimaleDAO massimaleDAO;

	/**
	 * 
	 * @param from
	 * @param size
	 * @param livello
	 * @param areaGeografica
	 * @param id
	 * @param notId
	 * @param tipo
	 * @return
	 * @throws Exception
	 */
	@Override
	public MassimaleStore getMassimaleByQuery(int from, int size, String livello, String areaGeografica,String id, String notId,
			String tipo) throws Exception {
		IMassimaleSearchBuilder massimaleUserSearchBuilder = IMassimaleSearchBuilder.MassimaleSearchBuilder.getMassimaleSearchBuilder()
				.withFrom(from).withSize(size).withLivello(livello).withAreaGeografica(areaGeografica).withNotId(notId)
				.withId(id)
				.withTipo(tipo);

		PageResult<Massimale> pageResult = this.massimaleDAO.findMassimaleByQuery(massimaleUserSearchBuilder);
		MassimaleStore massimaleStore = new MassimaleStore();
		massimaleStore.setMassimale(pageResult.getResults());
		massimaleStore.setTotale(pageResult.getTotal());
		return massimaleStore;
	}

	/**
	 * 
	 * @param massimale
	 * @return
	 * @throws Exception
	 */
	@Override
	public String addMassimale(Massimale massimale) throws Exception {
		if ((massimale == null)) {
			throw new IllegalParameterFault("The Parameter massimale must not be null");
		}
		if (massimale.getId() == null)
			massimale.setId(gen.generate().toString());
		return this.massimaleDAO.persist(massimale).getId();
	}

	/**
	 * 
	 * @param massimale
	 * @return
	 * @throws Exception
	 */
	@Override
	public Boolean updateMassimale(Massimale massimale) throws Exception {
		if ((massimale == null)) {
			throw new IllegalParameterFault("The Parameter massimale must not be null");
		}
		this.massimaleDAO.update(massimale);
		return Boolean.TRUE;
	}

	/**
	 * 
	 * @param massimaleID
	 * @return
	 * @throws Exception
	 */
	@Override
	public Boolean deleteMassimale(String massimaleID) throws Exception {
		if ((massimaleID == null) || (massimaleID.isEmpty())) {
			throw new IllegalParameterFault("The Parameter massimaleID must not be null " + "or an Empty String.");
		}
		this.massimaleDAO.delete(massimaleID);
		return Boolean.TRUE;
	}

}
