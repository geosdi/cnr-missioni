package it.cnr.missioni.model.user;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

/**
 * @author Salvia Vito
 */
public class Credenziali implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5504155444424046718L;
	@NotBlank
	private String username ="";
	@NotBlank
	private String password = "";
	private RuoloUserEnum ruoloUtente;
	
	/**
	 * 
	 * Calcola l' md5 della password
	 * 
	 * @param password
	 * @return String
	 */
	public String md5hash(String password) {
		String hashString = null;
		try {
			java.security.MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
			byte[] hash = digest.digest(password.getBytes());
			hashString = "";
			for (int i = 0; i < hash.length; i++) {
				hashString += Integer.toHexString((hash[i] & 0xFF) | 0x100).toLowerCase().substring(1, 3);
			}
		} catch (java.security.NoSuchAlgorithmException e) {
			// logger.error(e);
		}
		return hashString;
	}

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
	public RuoloUserEnum getRuoloUtente() {
		return ruoloUtente;
	}

	/**
	 * @param ruoloUtente
	 */
	public void setRuoloUtente(RuoloUserEnum ruoloUtente) {
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
