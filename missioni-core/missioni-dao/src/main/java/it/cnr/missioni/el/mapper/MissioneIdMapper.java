package it.cnr.missioni.el.mapper;

import org.geosdi.geoplatform.experimental.el.api.mapper.GPBaseMapper;
import org.geosdi.geoplatform.support.jackson.GPJacksonSupport;
import org.springframework.stereotype.Component;

import it.cnr.missioni.model.configuration.MissioneId;

/**
 * @author Salvia Vito
 */
@Component(value = "missioneIdMapper")
public class MissioneIdMapper extends GPBaseMapper<MissioneId> {

    public MissioneIdMapper() {
        super(MissioneId.class, new GPJacksonSupport()
        );
    }

    @Override
    public String getMapperName() {
        return "Missione Id Mapper";
    }

    @Override
    public String toString() {
        return getMapperName();
    }
}