package it.cnr.missioni.el.index;

import org.geosdi.geoplatform.experimental.el.index.GPAbstractIndexCreator;
import org.geosdi.geoplatform.experimental.el.index.GPIndexCreator;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import it.cnr.missioni.el.index.settings.MissioneIndexSettings;
import it.cnr.missioni.el.index.settings.RimborsoKmIndexSettings;
import it.cnr.missioni.el.index.settings.UserIndexSettings;


/**
 * @author Salvia Vito
 */
@Component(value = "rimborsoKmIndexCreator")
public class RimborsoKmIndexCreator extends GPAbstractIndexCreator {

    @Override
    public GPIndexCreator.GPIndexSettings getIndexSettings() {
        return RimborsoKmIndexSettings.RIMBORSO_KM_DOC_INDEX_SETTINGS.getValue();
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
	
}
