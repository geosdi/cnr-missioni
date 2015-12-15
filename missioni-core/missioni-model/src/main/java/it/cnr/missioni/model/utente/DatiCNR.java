package it.cnr.missioni.model.utente;

/**
 * @author Salvia Vito
 */
public class DatiCNR {

	private int livello;
	private String qualifica;
	private String datoreLavoro;
	private String matricola;
	private String codiceTerzo;
	private String mail;
	private String iban;

	/**
	 * @return the livello
	 */
	public int getLivello() {
		return livello;
	}

	/**
	 * @param livello
	 */
	public void setLivello(int livello) {
		this.livello = livello;
	}

	/**
	 * @return the qualifica
	 */
	public String getQualifica() {
		return qualifica;
	}

	/**
	 * @param qualifica
	 */
	public void setQualifica(String qualifica) {
		this.qualifica = qualifica;
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DatiCNR [livello=" + livello + ", qualifica=" + qualifica + ", datoreLavoro=" + datoreLavoro
				+ ", matricola=" + matricola + ", codiceTerzo=" + codiceTerzo + ", mail=" + mail + ", iban=" + iban
				+ "]";
	}

}
