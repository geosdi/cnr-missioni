package it.cnr.missioni.model.utente;

/**
 * @author Salvia Vito
 */
public class Credenziali {

	private String username;
	private String password;
	private RuoloUtenteEnum ruoloUtente;

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the ruoloUtente
	 */
	public RuoloUtenteEnum getRuoloUtente() {
		return ruoloUtente;
	}

	/**
	 * @param ruoloUtente
	 */
	public void setRuoloUtente(RuoloUtenteEnum ruoloUtente) {
		this.ruoloUtente = ruoloUtente;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Credenziali [username=" + username + ", password=" + password + ", ruoloUtente=" + ruoloUtente + "]";
	}

}
