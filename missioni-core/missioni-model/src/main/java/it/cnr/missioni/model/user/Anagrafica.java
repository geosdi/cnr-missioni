package it.cnr.missioni.model.user;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.DateTime;


/**
 * @author Salvia Vito
 */
public class Anagrafica implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2355131345839836682L;
	@NotBlank
	private String nome;
	@NotBlank
	private String cognome;
	@Length(min=16,max=16)
	private String codiceFiscale;
	@NotNull
	@Past
	private DateTime dataNascita;
	@NotBlank
	private String luogoNascita;
	@NotNull
	private Genere genere;
	
	public enum Genere{
		UOMO,DONNA;
	}

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

	/**
	 * @return the genere
	 */
	public Genere getGenere() {
		return genere;
	}

	/**
	 * @param genere 
	 */
	public void setGenere(Genere genere) {
		this.genere = genere;
	}

	/**
	 * @return
	 */
	@Override
	public String toString() {
		return "Anagrafica [nome=" + nome + ", cognome=" + cognome + ", codiceFiscale=" + codiceFiscale
				+ ", dataNascita=" + dataNascita + ", luogoNascita=" + luogoNascita + ", genere=" + genere + "]";
	}

}
