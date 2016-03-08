package it.cnr.missioni.model.user;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author Salvia Vito
 */
public class DatiCNR implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1534365538828825181L;
	@NotNull
	private LivelloUserEnum livello;
	private String IdQualifica;
	@NotBlank
	private String descrizioneQualifica;
	private String datoreLavoro;
	private String shortDescriptionDatoreLavoro;
	@NotBlank
	private String matricola;
	private String codiceTerzo;
	@NotBlank
	@Email
	private String mail;
	@NotBlank
	private String iban;
	
	public enum LivelloUserEnum {
		

		
		I(1),II(2),III(3),IV(4),V(5),VI(6),VII(7),VIII(8);
		
		private final int stato;
		
		LivelloUserEnum(int stato){
			this.stato = stato;
		}

		/**
		 * @return the stato
		 */
		public int getStato() {
			return stato;
		}
		
	}





	/**
	 * @return the livello
	 */
	public LivelloUserEnum getLivello() {
		return livello;
	}

	/**
	 * @param livello 
	 */
	public void setLivello(LivelloUserEnum livello) {
		this.livello = livello;
	}

	/**
	 * @return the idQualifica
	 */
	public String getIdQualifica() {
		return IdQualifica;
	}

	/**
	 * @param idQualifica 
	 */
	public void setIdQualifica(String idQualifica) {
		IdQualifica = idQualifica;
	}

	/**
	 * @return the descrizioneQualifica
	 */
	public String getDescrizioneQualifica() {
		return descrizioneQualifica;
	}

	/**
	 * @param descrizioneQualifica 
	 */
	public void setDescrizioneQualifica(String descrizioneQualifica) {
		this.descrizioneQualifica = descrizioneQualifica;
	}

	/**
	 * @return the datoreLavoro
	 */
	public String getDatoreLavoro() {
		return datoreLavoro;
	}

	/**
	 * @param datoreLavoro
	 */
	public void setDatoreLavoro(String datoreLavoro) {
		this.datoreLavoro = datoreLavoro;
	}

	/**
	 * @return the shortDescriptionDatoreLavoro
	 */
	public String getShortDescriptionDatoreLavoro() {
		return shortDescriptionDatoreLavoro;
	}

	/**
	 * @param shortDescriptionDatoreLavoro 
	 */
	public void setShortDescriptionDatoreLavoro(String shortDescriptionDatoreLavoro) {
		this.shortDescriptionDatoreLavoro = shortDescriptionDatoreLavoro;
	}

	/**
	 * @return the matricola
	 */
	public String getMatricola() {
		return matricola;
	}

	/**
	 * @param matricola
	 */
	public void setMatricola(String matricola) {
		this.matricola = matricola;
	}

	/**
	 * @return the codiceTerzo
	 */
	public String getCodiceTerzo() {
		return codiceTerzo;
	}

	/**
	 * @param codiceTerzo
	 */
	public void setCodiceTerzo(String codiceTerzo) {
		this.codiceTerzo = codiceTerzo;
	}

	/**
	 * @return the mail
	 */
	public String getMail() {
		return mail;
	}

	/**
	 * @param mail
	 */
	public void setMail(String mail) {
		this.mail = mail;
	}

	/**
	 * @return the iban
	 */
	public String getIban() {
		return iban;
	}

	/**
	 * @param iban
	 */
	public void setIban(String iban) {
		this.iban = iban;
	}

	/**
	 * @return
	 */
	@Override
	public String toString() {
		return "DatiCNR [livello=" + livello + ", IdQualifica=" + IdQualifica + ", descrizioneQualifica="
				+ descrizioneQualifica + ", datoreLavoro=" + datoreLavoro + ", shortDescriptionDatoreLavoro="
				+ shortDescriptionDatoreLavoro + ", matricola=" + matricola + ", codiceTerzo=" + codiceTerzo + ", mail="
				+ mail + ", iban=" + iban + "]";
	}



}
