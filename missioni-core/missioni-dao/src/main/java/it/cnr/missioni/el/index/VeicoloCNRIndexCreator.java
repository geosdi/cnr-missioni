package it.cnr.missioni.el.index;

import org.geosdi.geoplatform.experimental.el.index.GPAbstractIndexCreator;
import org.geosdi.geoplatform.experimental.el.index.GPIndexCreator;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import it.cnr.missioni.el.index.settings.PrenotazioneIndexSettings;
import it.cnr.missioni.el.index.settings.UserIndexSettings;
import it.cnr.missioni.el.index.settings.VeicoloCNRIndexSettings;


/**
 * @author Salvia Vito
 */
@Component(value = "veicoloCNRIndexCreator")
public class VeicoloCNRIndexCreator extends GPAbstractIndexCreator {

    @Override
    public GPIndexCreator.GPIndexSettings getIndexSettings() {
        return VeicoloCNRIndexSettings.VEICOLO_CNR_DOC_INDEX_SETTINGS.getValue();
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
	
}
