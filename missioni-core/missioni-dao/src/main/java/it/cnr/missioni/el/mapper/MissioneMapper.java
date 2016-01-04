package it.cnr.missioni.el.mapper;

import static org.geosdi.geoplatform.support.jackson.property.GPJacksonSupportEnum.WRITE_DATES_AS_TIMESTAMPS_DISABLE;

import org.geosdi.geoplatform.experimental.el.api.mapper.GPBaseMapper;
import org.geosdi.geoplatform.support.jackson.GPJacksonSupport;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.datatype.joda.JodaModule;

import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.model.user.User;

/**
 * @author Salvia Vito
 */
@Component(value = "missioneMapper")
public class MissioneMapper extends GPBaseMapper<Missione> {

    public MissioneMapper() {
        super(Missione.class, new GPJacksonSupport()
        		.registerModule(new JodaModule())
        		.configure(WRITE_DATES_AS_TIMESTAMPS_DISABLE));
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