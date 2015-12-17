package it.cnr.missioni.el.model.search;

/**
 * @author Salvia Vito
 */
public class UtenteModelSearch extends AbstractModelSearch {

	private final static String FIELD_NOME = "utente.anagrafica.nome";
	private final static String FIELD_COGNOME = "utente.anagrafica.cognome";
	private final static String FIELD_CODICE_FISCALE = "utente.anagrafica.codiceFiscale";
	private final static String FIELD_USERNAME = "utente.credenziali.username";

	private String nome;
	private String cognome;
	private String codiceFiscale;
	private String username;

	public UtenteModelSearch(String nome, String cognome, String codiceFiscale,String username) {
		super();
		this.nome = nome;
		this.cognome = cognome;
		this.codiceFiscale = codiceFiscale;
		this.username = username;
		buildQuery();
	}

	public void buildQuery() {
		if (username != null)
			buildExactValue(FIELD_USERNAME, username);
		if (nome != null)
			buildStringQueryPrefix(FIELD_NOME, nome);
		if (cognome != null)
			buildStringQueryPrefix(FIELD_COGNOME, cognome);
		if (codiceFiscale != null)
			buildStringQueryPrefix(FIELD_CODICE_FISCALE, codiceFiscale);
	}




}
