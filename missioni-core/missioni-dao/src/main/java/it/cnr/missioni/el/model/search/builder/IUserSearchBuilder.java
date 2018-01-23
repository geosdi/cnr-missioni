package it.cnr.missioni.el.model.search.builder;

import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.geosdi.geoplatform.experimental.el.dao.GPPageableElasticSearchDAO;
import org.geosdi.geoplatform.experimental.el.search.bool.BooleanExactSearch;
import org.geosdi.geoplatform.experimental.el.search.bool.BooleanMultiMatchSearch;
import org.geosdi.geoplatform.experimental.el.search.bool.BooleanPrefixSearch;
import org.geosdi.geoplatform.experimental.el.search.bool.IBooleanSearch;

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
     * @return {@link String}
     */
    String getCognome();

    /**
     * @return {@link String}
     */
    String getCodiceFiscale();

    /**
     * @return {@link String}
     */
    String getUsername();

    /**
     * @return {@link String}
     */
    String getMatricola();

    /**
     * @return {@link String}
     */
    String getNumeroPatente();

    /**
     * @return {@link String}
     */
    String getIban();

    /**
     * @return {@link String}
     */
    String getMail();

    /**
     * @return {@link String}
     */
    String getTarga();

    /**
     * @return {@link String}
     */
    String getCartaCircolazione();

    /**
     * @return {@link String}
     */
    String getPolizzaAssicurativa();

    /**
     * @return {@link String}
     */
    String getNotId();

    /**
     * @return {@link String}
     */
    String getMultiMatchValue();

    /**
     * @return {@link String}
     */
    String getFieldMultiMatch();

    /**
     * @return {@link Boolean}
     */
    Boolean isResponsabileGruppo();

    /**
     * @return {@link Boolean}
     */
    boolean isAll();

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
            listAbstractBooleanSearch.add(new BooleanExactSearch(SearchConstants.USER_REGISTRAZIONE_COMPLETATA, true, IBooleanSearch.BooleanQueryType.MUST, Operator.AND));
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
                listAbstractBooleanSearch.add(new BooleanPrefixSearch(SearchConstants.USER_FIELD_NOME, nome.toLowerCase(), IBooleanSearch.BooleanQueryType.MUST, Operator.OR));
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
                listAbstractBooleanSearch.add(new BooleanPrefixSearch(SearchConstants.USER_FIELD_COGNOME, cognome.toLowerCase(), IBooleanSearch.BooleanQueryType.MUST, Operator.AND));
            return self();
        }

        /**
         * @param codiceFiscale
         * @return {@link IUserSearchBuilder}
         */
        public IUserSearchBuilder withCodiceFiscale(String codiceFiscale) {
            this.codiceFiscale = codiceFiscale;
            if (codiceFiscale != null && !codiceFiscale.trim().equals(""))
                listAbstractBooleanSearch.add(new BooleanPrefixSearch(SearchConstants.USER_FIELD_CODICE_FISCALE, codiceFiscale.toLowerCase(), IBooleanSearch.BooleanQueryType.MUST, Operator.AND));
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
                    listAbstractBooleanSearch.add(new BooleanPrefixSearch(SearchConstants.USER_FIELD_MATRICOLA, matricola, IBooleanSearch.BooleanQueryType.MUST, Operator.AND));
                } else {
                    listAbstractBooleanSearch.add(new BooleanExactSearch(SearchConstants.USER_FIELD_MATRICOLA, matricola, IBooleanSearch.BooleanQueryType.MUST, Operator.AND));
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
                listAbstractBooleanSearch.add(new BooleanExactSearch(SearchConstants.USER_FIELD_USERNAME, username, IBooleanSearch.BooleanQueryType.MUST, Operator.AND));
            return self();
        }

        /**
         * @param targa
         * @return {@link IUserSearchBuilder}
         */
        public IUserSearchBuilder withTarga(String targa) {
            this.targa = targa;
            if (targa != null && !targa.trim().equals(""))
                listAbstractBooleanSearch.add(new BooleanExactSearch(SearchConstants.USER_FIELD_VEICOLO_TARGA, targa, IBooleanSearch.BooleanQueryType.MUST, Operator.AND));
            return self();
        }

        /**
         * @param cartaCircolazione
         * @return {@link IUserSearchBuilder}
         */
        public IUserSearchBuilder withCartaCircolazione(String cartaCircolazione) {
            this.cartaCircolazione = cartaCircolazione;
            if (cartaCircolazione != null && !cartaCircolazione.trim().equals(""))
                listAbstractBooleanSearch.add(new BooleanExactSearch(SearchConstants.USER_FIELD_VEICOLO_CARTA_CIRCOLAZIONE, cartaCircolazione, IBooleanSearch.BooleanQueryType.MUST, Operator.AND));
            return self();
        }

        /**
         * @param polizzaAssicurativa
         * @return {@link IUserSearchBuilder}
         */
        public IUserSearchBuilder withPolizzaAssicurativa(String polizzaAssicurativa) {
            this.polizzaAssicurativa = polizzaAssicurativa;
            if (polizzaAssicurativa != null && !polizzaAssicurativa.trim().equals(""))
                listAbstractBooleanSearch.add(new BooleanExactSearch(SearchConstants.USER_FIELD_VEICOLO_POLIZZA_ASSICURATIVA, polizzaAssicurativa, IBooleanSearch.BooleanQueryType.MUST, Operator.AND));
            return self();
        }

        /**
         * @param iban
         * @return {@link IUserSearchBuilder}
         */
        public IUserSearchBuilder withIban(String iban) {
            this.iban = iban;
            if (iban != null && !iban.trim().equals(""))
                listAbstractBooleanSearch.add(new BooleanExactSearch(SearchConstants.USER_FIELD_IBAN, iban, IBooleanSearch.BooleanQueryType.MUST, Operator.AND));
            return self();
        }

        /**
         * @param mail
         * @return {@link IUserSearchBuilder}
         */
        public IUserSearchBuilder withMail(String mail) {
            this.mail = mail;
            if (mail != null && !mail.trim().equals(""))
                listAbstractBooleanSearch.add(new BooleanExactSearch(SearchConstants.USER_FIELD_MAIL, mail, IBooleanSearch.BooleanQueryType.MUST, Operator.AND));
            return self();
        }

        /**
         * @param notId
         * @return {@link IUserSearchBuilder}
         */
        public IUserSearchBuilder withNotId(String notId) {
            this.setNotId(notId);
            if (notId != null && !notId.trim().equals(""))
                listAbstractBooleanSearch.add(new BooleanExactSearch(SearchConstants.USER_FIELD_ID, notId, IBooleanSearch.BooleanQueryType.MUST_NOT, Operator.AND));
            return self();
        }

        /**
         * @param id
         * @return {@link IUserSearchBuilder}
         */
        public IUserSearchBuilder withId(String id) {
            this.id = id;
            if (id != null && !id.trim().equals(""))
                listAbstractBooleanSearch.add(new BooleanExactSearch(SearchConstants.USER_FIELD_ID, id, IBooleanSearch.BooleanQueryType.MUST, Operator.AND));
            return self();
        }

        /**
         * @param numeroPatente
         * @return {@link IUserSearchBuilder}
         */
        public IUserSearchBuilder withNumeroPatente(String numeroPatente) {
            this.numeroPatente = numeroPatente;
            if (numeroPatente != null && !numeroPatente.trim().equals(""))
                listAbstractBooleanSearch.add(new BooleanExactSearch(SearchConstants.USER_FIELD_NUMERO_PATENTE, numeroPatente, IBooleanSearch.BooleanQueryType.MUST, Operator.AND));
            return self();
        }

        /**
         * @param multiMatchValue
         * @return {@link IUserSearchBuilder}
         */
        public IUserSearchBuilder withMultiMatch(String multiMatchValue) {
            this.multiMatchValue = multiMatchValue;
            if (multiMatchValue != null && !multiMatchValue.trim().equals(""))
                listAbstractBooleanSearch.add(new BooleanMultiMatchSearch(multiMatchValue, IBooleanSearch.BooleanQueryType.MUST, Operator.OR,fieldMultiMatch.split(",")));
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
                listAbstractBooleanSearch.add(new BooleanExactSearch(SearchConstants.USER_FIELD_RESPONSABILIE_GRUPPO, responsabileGruppo, IBooleanSearch.BooleanQueryType.MUST, Operator.AND));
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

        public IUserSearchBuilder build(){
            this.multiFieldsSearch = new GPPageableElasticSearchDAO.MultiFieldsSearch(this.listAbstractBooleanSearch.stream().toArray(IBooleanSearch[]::new));
            return self();
        }

        /**
         * @return
         */
        @Override
        protected IUserSearchBuilder self() {
            return this;
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
         * @return {@link String}
         */
        public String getCognome() {
            return cognome;
        }

        /**
         * @return {@link String}
         */
        public String getCodiceFiscale() {
            return codiceFiscale;
        }

        /**
         * @return {@link String}
         */
        public String getUsername() {
            return username;
        }

        /**
         * @return {@link String}
         */
        public String getMatricola() {
            return matricola;
        }

        /**
         * @return {@link String}
         */
        public String getNumeroPatente() {
            return numeroPatente;
        }

        /**
         * @return {@link String}
         */
        public String getIban() {
            return iban;
        }

        /**
         * @return {@link String}
         */
        public String getMail() {
            return mail;
        }

        /**
         * @return {@link String}
         */
        public String getTarga() {
            return targa;
        }

        /**
         * @return {@link String}
         */
        public String getCartaCircolazione() {
            return cartaCircolazione;
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

    }
}

