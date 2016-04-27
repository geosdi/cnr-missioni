package it.cnr.missioni.el.mapper;

import com.fasterxml.jackson.datatype.joda.JodaModule;
import it.cnr.missioni.model.missione.Missione;
import org.geosdi.geoplatform.experimental.el.api.mapper.GPBaseMapper;
import org.geosdi.geoplatform.support.jackson.GPJacksonSupport;
import org.springframework.stereotype.Component;

import static org.geosdi.geoplatform.support.jackson.property.GPJacksonSupportEnum.WRITE_DATES_AS_TIMESTAMPS_DISABLE;

import java.util.TimeZone;

/**
 * @author Salvia Vito
 */
@Component(value = "missioneMapper")
public class MissioneMapper extends GPBaseMapper<Missione> {

    public MissioneMapper() {
        super(Missione.class, new GPJacksonSupport()
                .registerModule(new JodaModule())
                .configure(WRITE_DATES_AS_TIMESTAMPS_DISABLE).setTimeZone(TimeZone.getDefault()));
    }

    @Override
    public String getMapperName() {
        return "Missione Mapper";
    }

    @Override
    public String toString() {
        return getMapperName();
    }
}