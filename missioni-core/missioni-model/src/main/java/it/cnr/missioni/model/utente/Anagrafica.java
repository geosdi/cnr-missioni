package it.cnr.missioni.model.utente;

import java.io.Serializable;

import org.joda.time.DateTime;

/**
 * @author Salvia Vito
 */
public class Anagrafica implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2355131345839836682L;
	private String nome;
	private String cognome;
	private String codiceFiscale;
	private DateTime dataNascita;
	private String luogoNascita;

	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * @return the cognome
	 */
	public String getCognome() {
		return cognome;
	}

	/**
	 * @param cognome
	 */
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	/**
	 * @return the codiceFiscale
	 */
	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	/**
	 * @param codiceFiscale
	 */
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	/**
	 * @return the dataNascita
	 */
	public DateTime getDataNascita() {
		return dataNascita;
	}

	/**
	 * @param dataNascita
	 */
	public void setDataNascita(DateTime dataNascita) {
		this.dataNascita = dataNascita;
	}

	/**
	 * @return the luogoNascita
	 */
	public String getLuogoNascita() {
		return luogoNascita;
	}

	/**
	 * @param luogoNascita
	 */
	public void setLuogoNascita(String luogoNascita) {
		this.luogoNascita = luogoNascita;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Anagrafica [nome=" + nome + ", cognome=" + cognome + ", codiceFiscale=" + codiceFiscale
				+ ", luogoNascita=" + luogoNascita + "]";
	}

}
