package it.cnr.missioni.el.model.search.builder;

import java.io.Serializable;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.search.sort.SortOrder;

import it.cnr.missioni.el.model.search.BooleanModelSearch;
import it.cnr.missioni.el.model.search.EnumBooleanType;
import it.cnr.missioni.el.model.search.ExactSearch;
import it.cnr.missioni.el.model.search.MultiMatchSearch;
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
	private String numeroPatente = null;
	private String iban = null;
	private String mail = null;
	private String targa = null;
	private String cartaCircolazione = null;
	private String polizzaAssicurativa = null;
	private String id = null;
	private String multiMatchValue;
	private String fieldMultiMatch = "user.anagrafica.nome,user.anagrafica.cognome,user.anagrafica.codiceFiscale,user.datiCNR.matricola,user.datiCNR.mail";
	private int size = 10;
	private int from = 0;
	private String fieldSort = SearchConstants.USER_FIELD_COGNOME;
	private SortOrder sortOrder = SortOrder.ASC;

	private UserSearchBuilder() {
		booleanModelSearch = new BooleanModelSearch();
	}

	public static UserSearchBuilder getUserSearchBuilder() {
		return new UserSearchBuilder();
	}

	public UserSearchBuilder withNome(String nome) {
		this.nome = nome;
		if( nome != null &&!nome.equals("") )
		booleanModelSearch.getListaSearch().add(new PrefixSearch(SearchConstants.USER_FIELD_NOME, nome));
		return this;
	}

	public UserSearchBuilder withCognome(String cognome) {
		this.cognome = cognome;
		if( cognome != null && !cognome.equals("")) 
		booleanModelSearch.getListaSearch().add(new PrefixSearch(SearchConstants.USER_FIELD_COGNOME, cognome));
		return this;
	}

	public UserSearchBuilder withCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
		if(codiceFiscale != null &&!codiceFiscale.equals("") )
		booleanModelSearch.getListaSearch()
				.add(new PrefixSearch(SearchConstants.USER_FIELD_CODICE_FISCALE, codiceFiscale));
		return this;
	}

	public UserSearchBuilder withMatricola(String matricola) {
		this.matricola = matricola;
		if( matricola != null &&!matricola.equals("") )
		booleanModelSearch.getListaSearch().add(new PrefixSearch(SearchConstants.USER_FIELD_MATRICOLA, matricola));
		return this;
	}

	public UserSearchBuilder withUsername(String username) {
		this.username = username;
		if(username != null && !username.equals("") )
		booleanModelSearch.getListaSearch().add(new ExactSearch(SearchConstants.USER_FIELD_USERNAME, username));
		return this;
	}

	public UserSearchBuilder withTarga(String targa) {
		this.targa = targa;
		if(targa != null &&!targa.equals("") )
		booleanModelSearch.getListaSearch().add(new ExactSearch(SearchConstants.USER_FIELD_VEICOLO_TARGA, targa));
		return this;
	}

	public UserSearchBuilder withCartaCircolazione(String cartaCircolazione) {
		this.cartaCircolazione = cartaCircolazione;
		if( cartaCircolazione != null && !cartaCircolazione.equals("") )
		booleanModelSearch.getListaSearch()
				.add(new ExactSearch(SearchConstants.USER_FIELD_VEICOLO_CARTA_CIRCOLAZIONE, cartaCircolazione));
		return this;
	}

	public UserSearchBuilder withPolizzaAssicurativa(String polizzaAssicurativa) {
		this.polizzaAssicurativa = polizzaAssicurativa;
		if( polizzaAssicurativa != null && !polizzaAssicurativa.equals("") )
		booleanModelSearch.getListaSearch()
				.add(new ExactSearch(SearchConstants.USER_FIELD_VEICOLO_POLIZZA_ASSICURATIVA, polizzaAssicurativa));
		return this;
	}

	public UserSearchBuilder withIban(String iban) {
		this.iban = iban;
		if( iban != null && !iban.equals("") )
		booleanModelSearch.getListaSearch().add(new ExactSearch(SearchConstants.USER_FIELD_IBAN, iban));
		return this;
	}

	public UserSearchBuilder withMail(String mail) {
		this.mail = mail;
		if( mail!= null &&!mail.equals("") )
		booleanModelSearch.getListaSearch().add(new ExactSearch(SearchConstants.USER_FIELD_MAIL, mail));
		return this;
	}

	public UserSearchBuilder withId(String id) {
		this.id = id;
		if(id != null &&!id.equals("") )
		booleanModelSearch.getListaSearch()
				.add(new ExactSearch(SearchConstants.USER_FIELD_ID, id, EnumBooleanType.MUST_NOT));
		return this;
	}

	public UserSearchBuilder withNumeroPatente(String numeroPatente) {
		this.numeroPatente = numeroPatente;
		if( numeroPatente != null &&!numeroPatente.equals("") )
		booleanModelSearch.getListaSearch()
				.add(new ExactSearch(SearchConstants.USER_FIELD_NUMERO_PATENTE, numeroPatente));
		return this;
	}

	
	public UserSearchBuilder withMultiMatch(String multiMatchValue) {
		this.multiMatchValue = multiMatchValue;
		if (multiMatchValue != null && !multiMatchValue.equals(""))

			booleanModelSearch.getListaSearch()
					.add(new MultiMatchSearch(fieldMultiMatch, multiMatchValue));
		return this;
	}
	
	public UserSearchBuilder withMultiMatchField(String fieldMultiMatch) {
		this.fieldMultiMatch = fieldMultiMatch;
		return this;
	}
	
	public UserSearchBuilder withSize(int size) {
		this.size = size;
		return this;
	}
	
	public UserSearchBuilder withFrom(int from) {
		this.from = from;
		return this;
	}
	
	public BoolQueryBuilder buildQuery() {
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

	/**
	 * @return the numeroPatente
	 */
	public String getNumeroPatente() {
		return numeroPatente;
	}

	/**
	 * @param numeroPatente
	 */
	public void setNumeroPatente(String numeroPatente) {
		this.numeroPatente = numeroPatente;
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
	 * @return the targa
	 */
	public String getTarga() {
		return targa;
	}

	/**
	 * @param targa
	 */
	public void setTarga(String targa) {
		this.targa = targa;
	}

	/**
	 * @return the cartaCircolazione
	 */
	public String getCartaCircolazione() {
		return cartaCircolazione;
	}

	/**
	 * @param cartaCircolazione
	 */
	public void setCartaCircolazione(String cartaCircolazione) {
		this.cartaCircolazione = cartaCircolazione;
	}

	/**
	 * @return the polizzaAssicurativa
	 */
	public String getPolizzaAssicurativa() {
		return polizzaAssicurativa;
	}

	/**
	 * @param polizzaAssicurativa
	 */
	public void setPolizzaAssicurativa(String polizzaAssicurativa) {
		this.polizzaAssicurativa = polizzaAssicurativa;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the multiMatchField
	 */
	public String getMultiMatchValue() {
		return multiMatchValue;
	}

	/**
	 * @param multiMatchField 
	 */
	public void setMultiMatchValue(String multiMatchValue) {
		this.multiMatchValue = multiMatchValue;
	}

	/**
	 * @return the fieldMultiMatch
	 */
	public String getFieldMultiMatch() {
		return fieldMultiMatch;
	}

	/**
	 * @param fieldMultiMatch 
	 */
	public void setFieldMultiMatch(String fieldMultiMatch) {
		this.fieldMultiMatch = fieldMultiMatch;
	}

	/**
	 * @return the size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * @param size
	 */
	public void setSize(int size) {
		this.size = size;
	}

	/**
	 * @return the from
	 */
	public int getFrom() {
		return from;
	}

	/**
	 * @param from
	 */
	public void setFrom(int from) {
		this.from = from;
	}
	
	/**
	 * @return the fieldSort
	 */
	public String getFieldSort() {
		return fieldSort;
	}

	/**
	 * @param fieldSort
	 */
	public void setFieldSort(String fieldSort) {
		this.fieldSort = fieldSort;
	}

	/**
	 * @return the sortOrder
	 */
	public SortOrder getSortOrder() {
		return sortOrder;
	}

	/**
	 * @param sortOrder
	 */
	public void setSortOrder(SortOrder sortOrder) {
		this.sortOrder = sortOrder;
	}

}
