package it.cnr.missioni.model.missione;

public enum TrattamentoMissioneEsteraEnum {
	RIMBORSO_DOCUMENTATO("Rimborso Documentato"), TRATTAMENTO_ALTERNATIVO("Trattamento Alternativo");

	private final String stato;

	TrattamentoMissioneEsteraEnum(String stato) {
		this.stato = stato;
	}

	public TrattamentoMissioneEsteraEnum getStatoEnum(String stato) {
		for (TrattamentoMissioneEsteraEnum statoEnum : TrattamentoMissioneEsteraEnum.values()) {
			if (statoEnum.getStato().equals(stato))
				return statoEnum;
		}
		return null;
	}

	public String getStato() {
		return stato;
	}

}
