package it.cnr.missioni.el.model.search.builder;

import it.cnr.missioni.el.model.search.EnumBooleanType;
import it.cnr.missioni.el.model.search.ExactSearch;
import it.cnr.missioni.el.model.search.MultiMatchSearch;
import it.cnr.missioni.el.model.search.PrefixSearch;

/**
 * @author Salvia Vito
 */

public interface IUserSearchBuilder extends ISearchBuilder<IUserSearchBuilder> {

    /**
     * @param nome
     * @return
     */
    IUserSearchBuilder withNome(String nome);

    /**
     * @param searchType
     * @return {@link IUserSearchBuilder}
     */
    IUserSearchBuilder withSearchType(String searchType);

    /**
     * @param cognome
     * @return {@link IUserSearchBuilder}
     */
    IUserSearchBuilder withCognome(String cognome);

    /**
     * @param codiceFiscale
     * @return {@link IUserSearchBuilder}
     */
    IUserSearchBuilder withCodiceFiscale(String codiceFiscale);

    /**
     * @param matricola
     * @return {@link IUserSearchBuilder}
     */
    IUserSearchBuilder withMatricola(String matricola);

    /**
     * @param username
     * @return {@link IUserSearchBuilder}
     */
    IUserSearchBuilder withUsername(String username);

    /**
     * @param targa
     * @return {@link IUserSearchBuilder}
     */
    IUserSearchBuilder withTarga(String targa);

    /**
     * @param cartaCircolazione
     * @return {@link IUserSearchBuilder}
     */
    IUserSearchBuilder withCartaCircolazione(String cartaCircolazione);

    /**
     * @param polizzaAssicurativa
     * @return {@link IUserSearchBuilder}
     */
    IUserSearchBuilder withPolizzaAssicurativa(String polizzaAssicurativa);

    /**
     * @param iban
     * @return {@link IUserSearchBuilder}
     */
    IUserSearchBuilder withIban(String iban);

    /**
     * @param mail
     * @return {@link IUserSearchBuilder}
     */
    IUserSearchBuilder withMail(String mail);

    /**
     * @param notId
     * @return {@link IUserSearchBuilder}
     */
    IUserSearchBuilder withNotId(String notId);

    /**
     * @param id
     * @return {@link IUserSearchBuilder}
     */
    IUserSearchBuilder withId(String id);

    /**
     * @param numeroPatente
     * @return {@link IUserSearchBuilder}
     */
    IUserSearchBuilder withNumeroPatente(String numeroPatente);

    /**
     * @param multiMatchValue
     * @return {@link IUserSearchBuilder}
     */
    IUserSearchBuilder withMultiMatch(String multiMatchValue);

    /**
     * @param responsabileGruppo
     * @return {@link IUserSearchBuilder}
     */
    IUserSearchBuilder withResponsabileGruppo(Boolean responsabileGruppo);

    /**
     * @param all
     * @return {@link IUserSearchBuilder}
     */
    IUserSearchBuilder withAll(boolean all);

    /**
     * @return {@link String}
     */
    String getSearchType();

    /**
     * @param searchType
     */
    void setSearchType(String searchType);

    /**
     * @return {@link String}
     */
    String getNome();

    /**
     * @param nome
     */
    void setNome(String nome);

    /**
     * @return {@link String}
     */
    String getCognome();

    /**
     * @param cognome
     */
    void setCognome(String cognome);

    /**
     * @return {@link String}
     */
    String getCodiceFiscale();

    /**
     * @param codiceFiscale
     */
    void setCodiceFiscale(String codiceFiscale);

    /**
     * @return {@link String}
     */
    String getUsername();

    /**
     * @param username
     */
    void setUsername(String username);

    /**
     * @return {@link String}
     */
    String getMatricola();

    /**
     * @param matricola
     */
    void setMatricola(String matricola);

    /**
     * @return {@link String}
     */
    String getNumeroPatente();

    /**
     * @param numeroPatente
     */
    void setNumeroPatente(String numeroPatente);

    /**
     * @return {@link String}
     */
    String getIban();

    /**
     * @param iban
     */
    void setIban(String iban);

    /**
     * @return {@link String}
     */
    String getMail();

    /**
     * @param mail
     */
    void setMail(String mail);

    /**
     * @return {@link String}
     */
    String getTarga();

    /**
     * @param targa
     */
    void setTarga(String targa);

    /**
     * @return {@link String}
     */
    String getCartaCircolazione();

    /**
     * @param cartaCircolazione
     */
    void setCartaCircolazione(String cartaCircolazione);

    /**
     * @return {@link String}
     */
    String getPolizzaAssicurativa();

    /**
     * @param polizzaAssicurativa
     */
    void setPolizzaAssicurativa(String polizzaAssicurativa);

    /**
     * @return {@link String}
     */
    String getNotId();

    /**
     * @param notId
     */
    void setNotId(String notId);

    /**
     * @return {@link String}
     */
    String getMultiMatchValue();

    /**
     * @param multiMatchValue
     */
    void setMultiMatchValue(String multiMatchValue);

    /**
     * @return {@link String}
     */
    String getFieldMultiMatch();

    /**
     * @param fieldMultiMatch
     */
    void setFieldMultiMatch(String fieldMultiMatch);

    /**
     * @return {@link Boolean}
     */
    Boolean isResponsabileGruppo();

    /**
     * @param responsabileGruppo
     */
    void setResponsabileGruppo(Boolean responsabileGruppo);

    /**
     * @return {@link Boolean}
     */
    boolean isAll();

    /**
     * @param all
     */
    void setAll(boolean all);

    class UserSearchBuilder extends ISearchBuilder.AbstractSearchBuilder<IUserSearchBuilder>
            implements IUserSearchBuilder {

        /**
		 * 
		 */
		private static final long serialVersionUID = 5963891982301629069L;
		private String searchType = "prefix";
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
        private String notId;
        private String multiMatchValue;
        private String fieldMultiMatch = "user.anagrafica.nome,user.anagrafica.cognome,user.anagrafica.codiceFiscale,user.datiCNR.matricola,user.datiCNR.mail";
        private Boolean responsabileGruppo = null;
        private boolean all = false;

        private UserSearchBuilder() {
        	this.fieldSort = SearchConstants.USER_FIELD_COGNOME;
        }

        public static IUserSearchBuilder getUserSearchBuilder() {
            return new UserSearchBuilder();
        }
        
        /**
         * @param nome
         * @return {@link IUserSearchBuilder}
         */
        public IUserSearchBuilder withNome(String nome) {
            this.nome = nome;
            if (nome != null && !nome.trim().equals(""))
                booleanModelSearch.getListaSearch().add(new PrefixSearch(SearchConstants.USER_FIELD_NOME, nome));
            return self();
        }

        /**
         * @param searchType
         * @return {@link IUserSearchBuilder}
         */
        public IUserSearchBuilder withSearchType(String searchType) {
            this.setSearchType(searchType);
            return self();
        }

        /**
         * @param cognome
         * @return {@link IUserSearchBuilder}
         */
        public IUserSearchBuilder withCognome(String cognome) {
            this.cognome = cognome;
            if (cognome != null && !cognome.trim().equals(""))
                booleanModelSearch.getListaSearch().add(new PrefixSearch(SearchConstants.USER_FIELD_COGNOME, cognome));
            return self();
        }

        /**
         * @param codiceFiscale
         * @return {@link IUserSearchBuilder}
         */
        public IUserSearchBuilder withCodiceFiscale(String codiceFiscale) {
            this.codiceFiscale = codiceFiscale;
            if (codiceFiscale != null && !codiceFiscale.trim().equals(""))
                booleanModelSearch.getListaSearch()
                        .add(new PrefixSearch(SearchConstants.USER_FIELD_CODICE_FISCALE, codiceFiscale));
            return self();
        }

        /**
         * @param matricola
         * @return {@link IUserSearchBuilder}
         */
        public IUserSearchBuilder withMatricola(String matricola) {
            this.matricola = matricola;
            if (matricola != null && !matricola.trim().equals("")) {
                if (searchType.equals("prefix") || searchType == null) {
                    booleanModelSearch.getListaSearch().add(new PrefixSearch(SearchConstants.USER_FIELD_MATRICOLA, matricola));
                } else {
                    booleanModelSearch.getListaSearch().add(new ExactSearch(SearchConstants.USER_FIELD_MATRICOLA, matricola));
                }
            }
            return self();
        }

        /**
         * @param username
         * @return {@link IUserSearchBuilder}
         */
        public IUserSearchBuilder withUsername(String username) {
            this.username = username;
            if (username != null && !username.trim().equals(""))
                booleanModelSearch.getListaSearch().add(new ExactSearch(SearchConstants.USER_FIELD_USERNAME, username));
            return self();
        }

        /**
         * @param targa
         * @return {@link IUserSearchBuilder}
         */
        public IUserSearchBuilder withTarga(String targa) {
            this.targa = targa;
            if (targa != null && !targa.trim().equals(""))
                booleanModelSearch.getListaSearch().add(new ExactSearch(SearchConstants.USER_FIELD_VEICOLO_TARGA, targa));
            return self();
        }

        /**
         * @param cartaCircolazione
         * @return {@link IUserSearchBuilder}
         */
        public IUserSearchBuilder withCartaCircolazione(String cartaCircolazione) {
            this.cartaCircolazione = cartaCircolazione;
            if (cartaCircolazione != null && !cartaCircolazione.trim().equals(""))
                booleanModelSearch.getListaSearch()
                        .add(new ExactSearch(SearchConstants.USER_FIELD_VEICOLO_CARTA_CIRCOLAZIONE, cartaCircolazione));
            return self();
        }

        /**
         * @param polizzaAssicurativa
         * @return {@link IUserSearchBuilder}
         */
        public IUserSearchBuilder withPolizzaAssicurativa(String polizzaAssicurativa) {
            this.polizzaAssicurativa = polizzaAssicurativa;
            if (polizzaAssicurativa != null && !polizzaAssicurativa.trim().equals(""))
                booleanModelSearch.getListaSearch()
                        .add(new ExactSearch(SearchConstants.USER_FIELD_VEICOLO_POLIZZA_ASSICURATIVA, polizzaAssicurativa));
            return self();
        }

        /**
         * @param iban
         * @return {@link IUserSearchBuilder}
         */
        public IUserSearchBuilder withIban(String iban) {
            this.iban = iban;
            if (iban != null && !iban.trim().equals(""))
                booleanModelSearch.getListaSearch().add(new ExactSearch(SearchConstants.USER_FIELD_IBAN, iban));
            return self();
        }

        /**
         * @param mail
         * @return {@link IUserSearchBuilder}
         */
        public IUserSearchBuilder withMail(String mail) {
            this.mail = mail;
            if (mail != null && !mail.trim().equals(""))
                booleanModelSearch.getListaSearch().add(new ExactSearch(SearchConstants.USER_FIELD_MAIL, mail));
            return self();
        }

        /**
         * @param notId
         * @return {@link IUserSearchBuilder}
         */
        public IUserSearchBuilder withNotId(String notId) {
            this.setNotId(notId);
            if (notId != null && !notId.trim().equals(""))
                booleanModelSearch.getListaSearch()
                        .add(new ExactSearch(SearchConstants.USER_FIELD_ID, notId, EnumBooleanType.MUST_NOT));
            return self();
        }

        /**
         * @param id
         * @return {@link IUserSearchBuilder}
         */
        public IUserSearchBuilder withId(String id) {
            this.setId(id);
            if (id != null && !id.trim().equals(""))
                booleanModelSearch.getListaSearch()
                        .add(new ExactSearch(SearchConstants.USER_FIELD_ID, id));
            return self();
        }

        /**
         * @param numeroPatente
         * @return {@link IUserSearchBuilder}
         */
        public IUserSearchBuilder withNumeroPatente(String numeroPatente) {
            this.numeroPatente = numeroPatente;
            if (numeroPatente != null && !numeroPatente.trim().equals(""))
                booleanModelSearch.getListaSearch()
                        .add(new ExactSearch(SearchConstants.USER_FIELD_NUMERO_PATENTE, numeroPatente));
            return self();
        }

        /**
         * @param multiMatchValue
         * @return {@link IUserSearchBuilder}
         */
        public IUserSearchBuilder withMultiMatch(String multiMatchValue) {
            this.multiMatchValue = multiMatchValue;
            if (multiMatchValue != null && !multiMatchValue.trim().equals(""))

                booleanModelSearch.getListaSearch()
                        .add(new MultiMatchSearch(fieldMultiMatch, multiMatchValue));
            return self();
        }

        /**
         * @param fieldMultiMatch
         * @return {@link IUserSearchBuilder}
         */
        public IUserSearchBuilder withMultiMatchField(String fieldMultiMatch) {
            this.fieldMultiMatch = fieldMultiMatch;
            return self();
        }

        /**
         * @param responsabileGruppo
         * @return {@link IUserSearchBuilder}
         */
        public IUserSearchBuilder withResponsabileGruppo(Boolean responsabileGruppo) {
            this.responsabileGruppo = responsabileGruppo;
            if (responsabileGruppo != null)
                booleanModelSearch.getListaSearch()
                        .add(new ExactSearch(SearchConstants.USER_FIELD_RESPONSABILIE_GRUPPO, responsabileGruppo));
            return self();
        }

        /**
         * @param all
         * @return {@link IUserSearchBuilder}
         */
        public IUserSearchBuilder withAll(boolean all) {
            this.all = all;
            return self();
        }

        /**
         * @return {@link String}
         */
        public String getSearchType() {
            return searchType;
        }

        /**
         * @param searchType
         */
        public void setSearchType(String searchType) {
            this.searchType = searchType;
        }

        /**
         * @return {@link String}
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
         * @return {@link String}
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
         * @return {@link String}
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
         * @return {@link String}
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
         * @return {@link String}
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
         * @return {@link String}
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
         * @return {@link String}
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
         * @return {@link String}
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
         * @return {@link String}
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
         * @return {@link String}
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
         * @return {@link String}
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
         * @return {@link String}
         */
        public String getNotId() {
            return notId;
        }

        /**
         * @param notId
         */
        public void setNotId(String notId) {
            this.notId = notId;
        }

        /**
         * @return {@link String}
         */
        public String getMultiMatchValue() {
            return multiMatchValue;
        }

        /**
         * @param multiMatchValue
         */
        public void setMultiMatchValue(String multiMatchValue) {
            this.multiMatchValue = multiMatchValue;
        }

        /**
         * @return {@link String}
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
         * @return {@link String}
         */
        public Boolean isResponsabileGruppo() {
            return responsabileGruppo;
        }

        /**
         * @param responsabileGruppo
         */
        public void setResponsabileGruppo(Boolean responsabileGruppo) {
            this.responsabileGruppo = responsabileGruppo;
        }

        /**
         * @return {@link boolean}
         */
        public boolean isAll() {
            return all;
        }

        /**
         * @param all
         */
        public void setAll(boolean all) {
            this.all = all;
        }

        /**
         * @return
         */
        @Override
        protected IUserSearchBuilder self() {
            return this;
        }

    }
}

