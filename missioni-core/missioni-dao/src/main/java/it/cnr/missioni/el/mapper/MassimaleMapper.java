package it.cnr.missioni.el.mapper;

import org.geosdi.geoplatform.experimental.el.api.mapper.GPBaseMapper;
import org.geosdi.geoplatform.support.jackson.GPJacksonSupport;
import org.springframework.stereotype.Component;

import it.cnr.missioni.model.configuration.Massimale;

/**
 * @author Salvia Vito
 */
@Component(value = "massimaleMapper")
public class MassimaleMapper extends GPBaseMapper<Massimale> {

    public MassimaleMapper() {
        super(Massimale.class, new GPJacksonSupport()
        		);
    }

    @Override
    public String getMapperName() {
        return "Massimale Mapper";
    }

    @Override
    public String toString() {
        return getMapperName();
    }
}