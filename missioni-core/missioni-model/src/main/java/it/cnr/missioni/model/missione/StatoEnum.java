package it.cnr.missioni.model.missione;

import java.util.HashMap;
import java.util.Map;

public enum StatoEnum {
	ANNULLATA("Annullata"), INSERITA("Inserita"), APPROVATA("Approvata"), RESPINTA("Respinta"), PRESA_IN_CARICO("Presa in carico"), PAGATA("Pagata");

    private static Map<String, String> mappa = new HashMap<String, String>();

    static {
        for (StatoEnum s : StatoEnum.values()) {
            mappa.put(s.name(), s.stato);
        }
    }

    private final String stato;

    StatoEnum(String stato) {
        this.stato = stato;
    }

    public static StatoEnum getStatoE(String value) {
        for (StatoEnum s : StatoEnum.values()) {
            if (s.name().equals(value))
                return s;
        }
        return null;
    }

    public static String getStatoEnum(String name) {
        return mappa.get(name);
    }

    public String getStato() {
        return stato;
    }

}
