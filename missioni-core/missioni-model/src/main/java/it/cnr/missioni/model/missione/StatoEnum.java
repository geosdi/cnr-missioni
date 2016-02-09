package it.cnr.missioni.model.missione;

import java.util.HashMap;
import java.util.Map;

public enum StatoEnum {
	INSERITA("Inserita"), APPROVATA("Approvata"), RESPINTA("Respinta"), PRESA_IN_CARICO("Presa in carico"), STAND_BY(
			"Stand by"), PAGATA("Pagata");

	private final String stato;
	private static Map<String, String> mappa = new HashMap<String, String>();

	static {
		for (StatoEnum s : StatoEnum.values()) {
			mappa.put(s.name(),s.stato);
		}
	}

	StatoEnum(String stato) {
		this.stato = stato;
	}

	public static String getStatoEnum(String name) {
		return mappa.get(name);
	}

	public String getStato() {
		return stato;
	}

}
