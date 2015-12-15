package it.cnr.missioni.model.utente;

/**
 * @author Salvia Vito 
 */
public enum RuoloUtenteEnum {

	UTENTE_ADMIN("Admin"), UTENTE_SEMPLICE("Utente Semplice");

	private String ruolo;

	RuoloUtenteEnum(String ruolo) {
		this.setRuolo(ruolo);
	}

	public String getRuolo() {
		return ruolo;
	}

	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}

	public RuoloUtenteEnum getRuoloEnum(String ruolo) {
		for (RuoloUtenteEnum r : RuoloUtenteEnum.values()) {
			if (r.getRuolo().equals(ruolo))
				return r;
		}
		return null;
	}

}
