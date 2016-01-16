package it.cnr.missioni.model.missione;

import java.util.HashMap;
import java.util.Map;

public enum StatoEnum {
	INSERITA("Inserita"), APPROVATA("Approvata"), RESPINTA("Respinta"), PRESA_IN_CARICO("Presa in carico"), STAND_BY(
			"Stand by"), PAGATA("Pagata");

	private final String stato;
	private static Map<String, StatoEnum> mappa = new HashMap<String, StatoEnum>();

	static {
		for (StatoEnum s : StatoEnum.values()) {
			mappa.put(s.stato, s);
		}
	}

	StatoEnum(String stato) {
		this.stato = stato;
	}

	public static StatoEnum getStatoEnum(String stato) {
		return mappa.get(stato);
	}

	public String getStato() {
		return stato;
	}

	public static Map<String, StatoEnum> getMappa() {
		return mappa;
	}

	public static void setMappa(Map<String, StatoEnum> mappa) {
		StatoEnum.mappa = mappa;
	}
}
