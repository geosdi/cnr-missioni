package it.cnr.missioni.el.index;

import org.geosdi.geoplatform.experimental.el.index.GPAbstractIndexCreator;
import org.geosdi.geoplatform.experimental.el.index.GPIndexCreator;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import it.cnr.missioni.el.index.settings.UtenteIndexSettings;


/**
 * @author Salvia Vito
 */
@Component(value = "utenteIndexCreator")
public class UtenteIndexCreator extends GPAbstractIndexCreator {

    @Override
    public GPIndexCreator.GPIndexSettings getIndexSettings() {
        return UtenteIndexSettings.UTENTE_DOC_INDEX_SETTINGS.getValue();
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
	
}
