package it.cnr.missioni.el.mapper;

import org.geosdi.geoplatform.experimental.el.api.mapper.GPBaseMapper;
import org.geosdi.geoplatform.support.jackson.GPJacksonSupport;
import org.springframework.stereotype.Component;

import it.cnr.missioni.model.configuration.Nazione;

/**
 * @author Salvia Vito
 */
@Component(value = "nazioneMapper")
public class NazioneMapper extends GPBaseMapper<Nazione> {

    public NazioneMapper() {
        super(Nazione.class, new GPJacksonSupport()
        		);
    }

    @Override
    public String getMapperName() {
        return "Nazione Mapper";
    }

    @Override
    public String toString() {
        return getMapperName();
    }
}