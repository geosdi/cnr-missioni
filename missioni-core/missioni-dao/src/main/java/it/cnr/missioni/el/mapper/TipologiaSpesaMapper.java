package it.cnr.missioni.el.mapper;

import it.cnr.missioni.model.configuration.TipologiaSpesa;
import org.geosdi.geoplatform.experimental.el.api.mapper.GPBaseMapper;
import org.geosdi.geoplatform.support.jackson.GPJacksonSupport;
import org.springframework.stereotype.Component;

/**
 * @author Salvia Vito
 */
@Component(value = "tipologiaSpesaMapper")
public class TipologiaSpesaMapper extends GPBaseMapper<TipologiaSpesa> {

    public TipologiaSpesaMapper() {
        super(TipologiaSpesa.class, new GPJacksonSupport()
        );
    }

    @Override
    public String getMapperName() {
        return "Tipologia Spesa  Mapper";
    }

    @Override
    public String toString() {
        return getMapperName();
    }
}