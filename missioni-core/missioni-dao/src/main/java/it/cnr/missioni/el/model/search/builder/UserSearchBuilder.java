package it.cnr.missioni.el.model.search.builder;

import java.io.Serializable;

import org.elasticsearch.index.query.BoolQueryBuilder;

import it.cnr.missioni.el.model.search.BooleanModelSearch;
import it.cnr.missioni.el.model.search.ExactSearch;
import it.cnr.missioni.el.model.search.PrefixSearch;

/**
 * @author Salvia Vito
 */
public class UserSearchBuilder implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4387150079550066147L;

	private BooleanModelSearch booleanModelSearch;
	private String nome = null;
	private String cognome = null;
	private String codiceFiscale = null;
	private String username = null;
	private String matricola = null;
	
	public UserSearchBuilder(){}
	
	public UserSearchBuilder(String nome,String cognome,String codiceFiscale,String matricola,String username){
		this.nome = nome;
		this.cognome = cognome;
		this.codiceFiscale = codiceFiscale;
		this.matricola = matricola;
		this.username = username;
	}
	

	public BoolQueryBuilder buildQuery() {
		booleanModelSearch = new BooleanModelSearch();
				
		if (nome != null)
			booleanModelSearch.getListaSearch().add(new PrefixSearch(SearchConstants.USER_FIELD_NOME, nome));
		if (cognome != null)
			booleanModelSearch.getListaSearch().add(new PrefixSearch(SearchConstants.USER_FIELD_COGNOME, cognome));
		if (codiceFiscale != null)
			booleanModelSearch.getListaSearch()
					.add(new PrefixSearch(SearchConstants.USER_FIELD_CODICE_FISCALE, codiceFiscale));
		if (matricola != null)
			booleanModelSearch.getListaSearch().add(new PrefixSearch(SearchConstants.USER_FIELD_MATRICOLA, matricola));
		if (username != null)
			booleanModelSearch.getListaSearch().add(new ExactSearch(SearchConstants.USER_FIELD_USERNAME, username));
		return booleanModelSearch.buildQuery();
	}

	/**
	 * @return the booleanModelSearch
	 */
	public BooleanModelSearch getBooleanModelSearch() {
		return booleanModelSearch;
	}

	/**
	 * @param booleanModelSearch
	 */
	public void setBooleanModelSearch(BooleanModelSearch booleanModelSearch) {
		this.booleanModelSearch = booleanModelSearch;
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

}
