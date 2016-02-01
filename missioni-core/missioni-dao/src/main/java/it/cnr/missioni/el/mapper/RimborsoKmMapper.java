package it.cnr.missioni.el.mapper;

import org.geosdi.geoplatform.experimental.el.api.mapper.GPBaseMapper;
import org.geosdi.geoplatform.support.jackson.GPJacksonSupport;
import org.springframework.stereotype.Component;

import it.cnr.missioni.model.configuration.RimborsoKm;
import it.cnr.missioni.model.prenotazione.VeicoloCNR;

/**
 * @author Salvia Vito
 */
@Component(value = "rimborsoKmMapper")
public class RimborsoKmMapper extends GPBaseMapper<RimborsoKm> {

    public RimborsoKmMapper() {
        super(RimborsoKm.class, new GPJacksonSupport()
        		);
    }

    @Override
    public String getMapperName() {
        return "Rimborso Km  Mapper";
    }

    @Override
    public String toString() {
        return getMapperName();
    }
}