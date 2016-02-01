package it.cnr.missioni.el.index;

import org.geosdi.geoplatform.experimental.el.index.GPAbstractIndexCreator;
import org.geosdi.geoplatform.experimental.el.index.GPIndexCreator;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import it.cnr.missioni.el.index.settings.QualificaUserIndexSettings;
import it.cnr.missioni.el.index.settings.TipologiaSpesaIndexSettings;


/**
 * @author Salvia Vito
 */
@Component(value = "tipologiaSpesaIndexCreator")
public class TipologiaSpesaIndexCreator extends GPAbstractIndexCreator {

    @Override
    public GPIndexCreator.GPIndexSettings getIndexSettings() {
        return TipologiaSpesaIndexSettings.TIPOLOGIA_SPESA_DOC_INDEX_SETTINGS.getValue();
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
	
}
