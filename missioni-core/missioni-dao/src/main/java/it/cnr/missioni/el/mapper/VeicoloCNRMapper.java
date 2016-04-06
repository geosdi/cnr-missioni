package it.cnr.missioni.el.mapper;

import it.cnr.missioni.model.prenotazione.VeicoloCNR;
import org.geosdi.geoplatform.experimental.el.api.mapper.GPBaseMapper;
import org.geosdi.geoplatform.support.jackson.GPJacksonSupport;
import org.springframework.stereotype.Component;

/**
 * @author Salvia Vito
 */
@Component(value = "veicoloCNRMapper")
public class VeicoloCNRMapper extends GPBaseMapper<VeicoloCNR> {

    public VeicoloCNRMapper() {
        super(VeicoloCNR.class, new GPJacksonSupport()
        );
    }

    @Override
    public String getMapperName() {
        return "Veicolo CNR Mapper";
    }

    @Override
    public String toString() {
        return getMapperName();
    }
}