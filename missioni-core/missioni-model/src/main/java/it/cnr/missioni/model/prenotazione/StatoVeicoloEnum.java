package it.cnr.missioni.model.prenotazione;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Salvia Vito
 */
public enum StatoVeicoloEnum {

    DISPONIBILE("Disponibile"), NON_DISPONIBILE("Non disponibile");

    private static Map<String, StatoVeicoloEnum> mappa = new HashMap<String, StatoVeicoloEnum>();

    static {
        for (StatoVeicoloEnum s : StatoVeicoloEnum.values()) {
            mappa.put(s.getStato(), s);
        }
    }

    private final String stato;

    StatoVeicoloEnum(String stato) {
        this.stato = stato;
    }

    public static StatoVeicoloEnum getStatoVeicoloEnum(String stato) {
        return mappa.get(stato);
    }

    public static Map<String, StatoVeicoloEnum> getMappa() {
        return mappa;
    }

    public static void setMappa(Map<String, StatoVeicoloEnum> mappa) {
        StatoVeicoloEnum.mappa = mappa;
    }

    public String getStato() {
        return stato;
    }

}
