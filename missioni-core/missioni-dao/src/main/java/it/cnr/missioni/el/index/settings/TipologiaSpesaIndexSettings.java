package it.cnr.missioni.el.index.settings;

import org.geosdi.geoplatform.experimental.el.index.GPIndexCreator;

/**
 * @author Salvia Vito
 */
public enum TipologiaSpesaIndexSettings {
	
    TIPOLOGIA_SPESA_DOC_INDEX_SETTINGS(new BaseIndexSettings("cnr_missioni_index_configuration", "tipologia_spesa_type"));

    private final GPIndexCreator.GPIndexSettings value;

    private TipologiaSpesaIndexSettings(GPIndexCreator.GPIndexSettings value) {
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
