package it.cnr.missioni.el.mapper;

import org.geosdi.geoplatform.experimental.el.api.mapper.GPBaseMapper;
import org.geosdi.geoplatform.support.jackson.GPJacksonSupport;
import org.springframework.stereotype.Component;

import it.cnr.missioni.model.configuration.QualificaUser;
import it.cnr.missioni.model.configuration.RimborsoKm;
import it.cnr.missioni.model.prenotazione.VeicoloCNR;

/**
 * @author Salvia Vito
 */
@Component(value = "qualificaUserMapper")
public class QualificaUserMapper extends GPBaseMapper<QualificaUser> {

    public QualificaUserMapper() {
        super(QualificaUser.class, new GPJacksonSupport()
        		);
    }

    @Override
    public String getMapperName() {
        return "Qualifica User  Mapper";
    }

    @Override
    public String toString() {
        return getMapperName();
    }
}