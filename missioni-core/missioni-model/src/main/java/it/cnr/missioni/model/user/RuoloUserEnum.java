package it.cnr.missioni.model.user;

/**
 * @author Salvia Vito 
 */
public enum RuoloUserEnum {

	UTENTE_ADMIN("Admin"), UTENTE_SEMPLICE("Utente Semplice");

	private String ruolo;

	RuoloUserEnum(String ruolo) {
		this.setRuolo(ruolo);
	}

	public String getRuolo() {
		return ruolo;
	}

	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}

	public RuoloUserEnum getRuoloEnum(String ruolo) {
		for (RuoloUserEnum r : RuoloUserEnum.values()) {
			if (r.getRuolo().equals(ruolo))
				return r;
		}
		return null;
	}

}
