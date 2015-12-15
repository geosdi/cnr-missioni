package it.cnr.missioni.model.missione;

public enum StatoEnum {
	APPROVATA("Approvata"), RESPINTA("Respinta"), PRESA_IN_CARICO("Presa in carico"), STAND_BY("Stand by");

	private String stato;

	StatoEnum(String stato) {
		this.setStato(stato);
	}

	public StatoEnum getStatoEnum(String stato) {
		for (StatoEnum statoEnum : StatoEnum.values()) {
			if (statoEnum.getStato().equals(stato))
				return statoEnum;
		}
		return null;
	}

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}
}
