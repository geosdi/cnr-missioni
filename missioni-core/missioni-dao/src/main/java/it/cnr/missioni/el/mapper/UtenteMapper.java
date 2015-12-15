package it.cnr.missioni.el.mapper;

import static org.geosdi.geoplatform.support.jackson.property.GPJacksonSupportEnum.WRITE_DATES_AS_TIMESTAMPS_DISABLE;

import org.geosdi.geoplatform.experimental.el.api.mapper.GPBaseMapper;
import org.geosdi.geoplatform.support.jackson.GPJacksonSupport;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.datatype.joda.JodaModule;

import it.cnr.missioni.model.utente.Utente;

/**
 * @author Salvia Vito
 */
@Component(value = "utenteMapper")
public class UtenteMapper extends GPBaseMapper<Utente> {

    public UtenteMapper() {
        super(Utente.class, new GPJacksonSupport()
        		.registerModule(new JodaModule())
        		.configure(WRITE_DATES_AS_TIMESTAMPS_DISABLE));
    }

    @Override
    public String getMapperName() {
        return "Utente Mapper";
    }

    @Override
    public String toString() {
        return getMapperName();
    }
}