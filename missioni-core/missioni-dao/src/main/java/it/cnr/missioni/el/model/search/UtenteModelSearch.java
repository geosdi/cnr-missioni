package it.cnr.missioni.el.model.search;

/**
 * @author Salvia Vito
 */
public class UtenteModelSearch extends AbstractModelSearch {

	private final static String FIELD_NOME = "utente.anagrafica.nome";
	private final static String FIELD_COGNOME = "utente.anagrafica.cognome";
	private final static String FIELD_CODICE_FISCALE = "utente.anagrafica.codiceFiscale";

	private String nome;
	private String cognome;
	private String codiceFiscale;

	public UtenteModelSearch(String nome, String cognome, String codiceFiscale) {
		super();
		this.nome = nome;
		this.cognome = cognome;
		this.codiceFiscale = codiceFiscale;
		buildQuery();
	}

	public void buildQuery() {

		if (nome != null)
			buildStringQueryPrefix(FIELD_NOME, nome);
		if (cognome != null)
			buildStringQueryPrefix(FIELD_COGNOME, cognome);
		if (codiceFiscale != null)
			buildStringQueryPrefix(FIELD_CODICE_FISCALE, codiceFiscale);
	}




}
