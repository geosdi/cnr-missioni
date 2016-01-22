package it.cnr.missioni.el.index.settings;

import org.geosdi.geoplatform.experimental.el.index.GPIndexCreator;

/**
 * @author Salvia Vito
 */
public enum VeicoloCNRIndexSettings {
	
    VEICOLO_CNR_DOC_INDEX_SETTINGS(new BaseIndexSettings("cnr_missioni_index_veicolocnr", "veicolocnr_type"));

    private final GPIndexCreator.GPIndexSettings value;

    private VeicoloCNRIndexSettings(GPIndexCreator.GPIndexSettings value) {
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
