package it.cnr.missioni.el.mapper;

import org.geosdi.geoplatform.experimental.el.api.mapper.GPBaseMapper;
import org.geosdi.geoplatform.support.jackson.GPJacksonSupport;
import org.springframework.stereotype.Component;

import it.cnr.missioni.model.configuration.Direttore;
import it.cnr.missioni.model.configuration.Massimale;

/**
 * @author Salvia Vito
 */
@Component(value = "direttoreMapper")
public class DirettoreMapper extends GPBaseMapper<Direttore> {

    public DirettoreMapper() {
        super(Direttore.class, new GPJacksonSupport()
        		);
    }

    @Override
    public String getMapperName() {
        return "Direttore Mapper";
    }

    @Override
    public String toString() {
        return getMapperName();
    }
}