package it.cnr.missioni.el.mapper;

import static org.geosdi.geoplatform.support.jackson.property.GPJacksonSupportEnum.WRITE_DATES_AS_TIMESTAMPS_DISABLE;

import org.geosdi.geoplatform.experimental.el.api.mapper.GPBaseMapper;
import org.geosdi.geoplatform.support.jackson.GPJacksonSupport;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.datatype.joda.JodaModule;

import it.cnr.missioni.model.prenotazione.Prenotazione;

/**
 * @author Salvia Vito
 */
@Component(value = "prenotazioneMapper")
public class PrenotazioneMapper extends GPBaseMapper<Prenotazione> {

    public PrenotazioneMapper() {
        super(Prenotazione.class, new GPJacksonSupport()
        		.registerModule(new JodaModule())
        		.configure(WRITE_DATES_AS_TIMESTAMPS_DISABLE));
    }

    @Override
    public String getMapperName() {
        return "Prenotazione Mapper";
    }

    @Override
    public String toString() {
        return getMapperName();
    }
}