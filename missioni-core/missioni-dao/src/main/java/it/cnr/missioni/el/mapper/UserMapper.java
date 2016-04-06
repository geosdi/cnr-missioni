package it.cnr.missioni.el.mapper;

import com.fasterxml.jackson.datatype.joda.JodaModule;
import it.cnr.missioni.model.user.User;
import org.geosdi.geoplatform.experimental.el.api.mapper.GPBaseMapper;
import org.geosdi.geoplatform.support.jackson.GPJacksonSupport;
import org.springframework.stereotype.Component;

import static org.geosdi.geoplatform.support.jackson.property.GPJacksonSupportEnum.WRITE_DATES_AS_TIMESTAMPS_DISABLE;

/**
 * @author Salvia Vito
 */
@Component(value = "userMapper")
public class UserMapper extends GPBaseMapper<User> {

    public UserMapper() {
        super(User.class, new GPJacksonSupport()
                .registerModule(new JodaModule())
                .configure(WRITE_DATES_AS_TIMESTAMPS_DISABLE));
    }

    @Override
    public String getMapperName() {
        return "User Mapper";
    }

    @Override
    public String toString() {
        return getMapperName();
    }
}