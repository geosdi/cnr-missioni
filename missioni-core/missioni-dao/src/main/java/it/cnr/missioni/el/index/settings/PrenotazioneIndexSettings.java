package it.cnr.missioni.el.index.settings;

import org.geosdi.geoplatform.experimental.el.index.GPIndexCreator;

/**
 * @author Salvia Vito
 */
public enum PrenotazioneIndexSettings {
	
    PRENOTAZIONE_DOC_INDEX_SETTINGS(new BaseIndexSettings("cnr_missioni_index_prenotazione", "prenotazione_type"));

    private final GPIndexCreator.GPIndexSettings value;

    private PrenotazioneIndexSettings(GPIndexCreator.GPIndexSettings value) {
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
