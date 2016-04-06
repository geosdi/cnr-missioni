package it.cnr.missioni.el.model.search.builder;

import it.cnr.missioni.el.model.search.*;
import org.elasticsearch.index.query.MatchQueryBuilder.Operator;
import org.joda.time.DateTime;

/**
 * @author Salvia Vito
 */

public interface IMissioneSearchBuilder extends ISearchBuilder<IMissioneSearchBuilder> {

    /**
     * @param fromDataInserimento
     * @param toDataInserimento
     * @return {@link IMissioneSearchBuilder}
     */
    IMissioneSearchBuilder withRangeDataInserimento(DateTime fromDataInserimento, DateTime toDataInserimento);

    /**
     * @param idUser
     * @return {@link IMissioneSearchBuilder}
     */
    IMissioneSearchBuilder withIdUser(String idUser);

    /**
     * @param stato
     * @return {@link IMissioneSearchBuilder}
     */
    IMissioneSearchBuilder withStato(String stato);

    /**
     * @param numeroOrdineRimborso
     * @return {@link IMissioneSearchBuilder}
     */
    IMissioneSearchBuilder withNumeroOrdineMissione(Long numeroOrdineRimborso);

    /**
     * @param fromDataRimborso
     * @param toDataRimborso
     * @return {@link IMissioneSearchBuilder}
     */
    IMissioneSearchBuilder withRangeDataRimborso(DateTime fromDataRimborso, DateTime toDataRimborso);

    /**
     * @param oggetto
     * @return {@link IMissioneSearchBuilder}
     */
    IMissioneSearchBuilder withOggetto(String oggetto);

    /**
     * @param multiMatchValue
     * @return {@link IMissioneSearchBuilder}
     */
    IMissioneSearchBuilder withMultiMatch(String multiMatchValue);

    /**
     * @param fieldExist
     * @return
     */
    IMissioneSearchBuilder withFieldExist(String fieldExist);

    /**
     * @param fieldNotExist
     * @return {@link IMissioneSearchBuilder}
     */
    IMissioneSearchBuilder withFieldNotExist(String fieldNotExist);

    /**
     * @param fieldMultiMatch
     * @return {@link IMissioneSearchBuilder}
     */
    IMissioneSearchBuilder withMultiMatchField(String fieldMultiMatch);

    /**
     * @return {@link String}
     */
    String getStato();

    /**
     * @param stato
     */
    void setStato(String stato);

    /**
     * @return {@link String}
     */
    String getIdUser();

    /**
     * @param idUser
     */
    void setIdUser(String idUser);

    /**
     * @return {@link DateTime}
     */
    DateTime getFromDataInserimento();

    /**
     * @param fromDataInserimento
     */
    void setFromDataInserimento(DateTime fromDataInserimento);

    /**
     * @return {@link DateTime}
     */
    DateTime getToDataInserimento();

    /**
     * @param toDataInserimento
     */
    void setToDataInserimento(DateTime toDataInserimento);

    /**
     * @return {@link Long}
     */
    Long getNumeroOrdineRimborso();

    /**
     * @param numeroOrdineRimborso
     */
    public void setNumeroOrdineRimborso(Long numeroOrdineRimborso);

    /**
     * @return {@link DateTime}
     */
    public DateTime getFromDataRimbroso();

    /**
     * @param fromDataRimbroso
     */
    public void setFromDataRimbroso(DateTime fromDataRimbroso);

    /**
     * @return {@link DateTime}
     */
    public DateTime getToDataRimbroso();

    /**
     * @param toDataRimbroso
     */
    public void setToDataRimbroso(DateTime toDataRimbroso);

    /**
     * @return {@link String}
     */
    public String getOggetto();

    /**
     * @param oggetto
     */
    public void setOggetto(String oggetto);

    /**
     * @return {@link String}
     */
    public String getMultiMatchValue();

    /**
     * @param multiMatchValue
     */
    public void setMultiMatchValue(String multiMatchValue);

    /**
     * @return {@link String}
     */
    public String getFieldExist();

    /**
     * @param fieldExist
     */
    public void setFieldExist(String fieldExist);

    /**
     * @return {@link String}
     */
    public String getFieldNotExist();

    /**
     * @param fieldNotExist
     */
    public void setFieldNotExist(String fieldNotExist);

    /**
     * @return {@link String}
     */
    public String getFieldMultiMatch();

    /**
     * @param fieldMultiMatch
     */
    public void setFieldMultiMatch(String fieldMultiMatch);

    class MissioneSearchBuilder extends ISearchBuilder.AbstractSearchBuilder<IMissioneSearchBuilder>
            implements IMissioneSearchBuilder {


        /**
         *
         */
        private static final long serialVersionUID = -1367921814725571059L;
        private DateTime fromDataInserimento = null;
        private DateTime toDataInserimento = null;
        private String idUser = null;
        private String stato = null;
        private Long numeroOrdineRimborso = null;
        private DateTime fromDataRimbroso = null;
        private DateTime toDataRimbroso = null;
        private String oggetto = null;
        private String multiMatchValue;
        private String fieldExist;
        private String fieldNotExist;
        private String fieldMultiMatch = "missione.localita,missione.oggetto,missione.id,missione.shortUser";

        private MissioneSearchBuilder() {
            this.fieldSort = SearchConstants.MISSIONE_FIELD_DATA_INSERIMENTO;
        }

        public static MissioneSearchBuilder getMissioneSearchBuilder() {
            return new MissioneSearchBuilder();
        }

        /**
         * @param fromDataInserimento
         * @param toDataInserimento
         * @return {@link IMissioneSearchBuilder}
         */
        public IMissioneSearchBuilder withRangeDataInserimento(DateTime fromDataInserimento, DateTime toDataInserimento) {
            this.fromDataInserimento = fromDataInserimento;
            this.toDataInserimento = toDataInserimento;
            if (fromDataInserimento != null || toDataInserimento != null)
                booleanModelSearch.getListaSearch().add(new DateRangeSearch(SearchConstants.MISSIONE_FIELD_DATA_INSERIMENTO,
                        fromDataInserimento, toDataInserimento));
            return self();
        }

        /**
         * @param idUser
         * @return {@link IMissioneSearchBuilder}
         */
        public IMissioneSearchBuilder withIdUser(String idUser) {
            this.idUser = idUser;
            if (idUser != null && !idUser.trim().equals(""))
                booleanModelSearch.getListaSearch().add(new ExactSearch(SearchConstants.MISSIONE_FIELD_ID_USER, idUser));
            return self();
        }

        /**
         * @param stato
         * @return {@link IMissioneSearchBuilder}
         */
        public IMissioneSearchBuilder withStato(String stato) {
            this.stato = stato;
            if (stato != null && !stato.trim().equals(""))
                booleanModelSearch.getListaSearch().add(new ExactSearch(SearchConstants.MISSIONE_FIELD_STATO, stato));
            return self();
        }

        /**
         * @param numeroOrdineRimborso
         * @return {@link IMissioneSearchBuilder}
         */
        public IMissioneSearchBuilder withNumeroOrdineMissione(Long numeroOrdineRimborso) {
            this.numeroOrdineRimborso = numeroOrdineRimborso;
            if (numeroOrdineRimborso != null && !numeroOrdineRimborso.equals(""))
                booleanModelSearch.getListaSearch()
                        .add(new ExactSearch(SearchConstants.MISSIONE_FIELD_RIMBORSO_NUMERO_ORDINE, numeroOrdineRimborso));
            return self();
        }

        /**
         * @param fromDataRimborso
         * @param toDataRimborso
         * @return {@link IMissioneSearchBuilder}
         */
        public IMissioneSearchBuilder withRangeDataRimborso(DateTime fromDataRimborso, DateTime toDataRimborso) {
            this.fromDataRimbroso = fromDataRimborso;
            this.toDataRimbroso = toDataRimborso;
            if (fromDataRimbroso != null || toDataRimbroso != null)
                booleanModelSearch.getListaSearch().add(new DateRangeSearch(
                        SearchConstants.MISSIONE_FIELD_RIMBORSO_DATA_RIMBORSO, fromDataRimbroso, toDataRimbroso));
            return self();
        }

        /**
         * @param id
         * @return {@link IMissioneSearchBuilder}
         */
        public IMissioneSearchBuilder withId(String id) {
            this.id = id;
            if (id != null && !id.trim().equals(""))
                booleanModelSearch.getListaSearch().add(new ExactSearch(SearchConstants.MISSIONE_FIELD_ID, id));
            return self();
        }

        /**
         * @param oggetto
         * @return {@link IMissioneSearchBuilder}
         */
        public IMissioneSearchBuilder withOggetto(String oggetto) {
            this.oggetto = oggetto;
            if (oggetto != null && !oggetto.trim().equals(""))

                booleanModelSearch.getListaSearch().add(new ExactSearch(SearchConstants.MISSIONE_FIELD_OGGETTO, oggetto,
                        EnumBooleanType.MUST, Operator.OR));
            return self();
        }

        /**
         * @param multiMatchValue
         * @return {@link IMissioneSearchBuilder}
         */
        public IMissioneSearchBuilder withMultiMatch(String multiMatchValue) {
            this.multiMatchValue = multiMatchValue;
            if (multiMatchValue != null && !multiMatchValue.trim().equals(""))

                booleanModelSearch.getListaSearch().add(new MultiMatchSearch(fieldMultiMatch, multiMatchValue));
            return self();
        }

        /**
         * @param fieldExist
         * @return {@link IMissioneSearchBuilder}
         */
        public IMissioneSearchBuilder withFieldExist(String fieldExist) {
            this.fieldExist = fieldExist;
            if (fieldExist != null && !fieldExist.trim().equals(""))

                booleanModelSearch.getListaSearch().add(new ExistFieldSearch(fieldExist));
            return self();
        }

        /**
         * @param fieldNotExist
         * @return {@link IMissioneSearchBuilder}
         */
        public IMissioneSearchBuilder withFieldNotExist(String fieldNotExist) {
            this.fieldNotExist = fieldNotExist;
            if (fieldNotExist != null && !fieldNotExist.trim().equals(""))

                booleanModelSearch.getListaSearch().add(new ExistFieldSearch(fieldNotExist, EnumBooleanType.MUST_NOT));
            return self();
        }

        /**
         * @param fieldMultiMatch
         * @return {@link IMissioneSearchBuilder}
         */
        public IMissioneSearchBuilder withMultiMatchField(String fieldMultiMatch) {
            this.fieldMultiMatch = fieldMultiMatch;
            return self();
        }

        /**
         * @return {@link DateTime}
         */
        public DateTime getFromDataInserimento() {
            return fromDataInserimento;
        }

        /**
         * @param fromDataInserimento
         */
        public void setFromDataInserimento(DateTime fromDataInserimento) {
            this.fromDataInserimento = fromDataInserimento;
        }

        /**
         * @return {@link DateTime}
         */
        public DateTime getToDataInserimento() {
            return toDataInserimento;
        }

        /**
         * @param toDataInserimento
         */
        public void setToDataInserimento(DateTime toDataInserimento) {
            this.toDataInserimento = toDataInserimento;
        }

        /**
         * @return {@link String}
         */
        public String getIdUser() {
            return idUser;
        }

        /**
         * @param idUser
         */
        public void setIdUser(String idUser) {
            this.idUser = idUser;
        }

        /**
         * @return {@link String}
         */
        public String getStato() {
            return stato;
        }

        /**
         * @param stato
         */
        public void setStato(String stato) {
            this.stato = stato;
        }

        /**
         * @return {@link Long}
         */
        public Long getNumeroOrdineRimborso() {
            return numeroOrdineRimborso;
        }

        /**
         * @param numeroOrdineRimborso
         */
        public void setNumeroOrdineRimborso(Long numeroOrdineRimborso) {
            this.numeroOrdineRimborso = numeroOrdineRimborso;
        }

        /**
         * @return {@link DateTime}
         */
        public DateTime getFromDataRimbroso() {
            return fromDataRimbroso;
        }

        /**
         * @param fromDataRimbroso
         */
        public void setFromDataRimbroso(DateTime fromDataRimbroso) {
            this.fromDataRimbroso = fromDataRimbroso;
        }

        /**
         * @return {@link DateTime}
         */
        public DateTime getToDataRimbroso() {
            return toDataRimbroso;
        }

        /**
         * @param toDataRimbroso
         */
        public void setToDataRimbroso(DateTime toDataRimbroso) {
            this.toDataRimbroso = toDataRimbroso;
        }

        /**
         * @return {@link String}
         */
        public String getOggetto() {
            return oggetto;
        }

        /**
         * @param oggetto
         */
        public void setOggetto(String oggetto) {
            this.oggetto = oggetto;
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
        public String getFieldExist() {
            return fieldExist;
        }

        /**
         * @param fieldExist
         */
        public void setFieldExist(String fieldExist) {
            this.fieldExist = fieldExist;
        }

        /**
         * @return {@link String}
         */
        public String getFieldNotExist() {
            return fieldNotExist;
        }

        /**
         * @param fieldNotExist
         */
        public void setFieldNotExist(String fieldNotExist) {
            this.fieldNotExist = fieldNotExist;
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
         * @return {@link IMissioneSearchBuilder}
         */
        protected IMissioneSearchBuilder self() {
            return this;
        }

    }

}

