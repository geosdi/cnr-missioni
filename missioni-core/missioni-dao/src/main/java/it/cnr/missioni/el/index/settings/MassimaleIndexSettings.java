package it.cnr.missioni.el.index.settings;

import org.geosdi.geoplatform.experimental.el.index.GPIndexCreator;

/**
 * @author Salvia Vito
 */
public enum MassimaleIndexSettings {
	
    MASSIMALE_DOC_INDEX_SETTINGS(new BaseIndexSettings("cnr_missioni_index_configuration", "massimale_type"));

    private final GPIndexCreator.GPIndexSettings value;

    private MassimaleIndexSettings(GPIndexCreator.GPIndexSettings value) {
        this.value = value;
    }

    /**
     * @return the value
     */
    public GPIndexCreator.GPIndexSettings getValue() {
        return value;
    }

    @Override
    public String toString() {
        return this.value.toString();
    }

}
