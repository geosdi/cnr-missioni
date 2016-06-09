package it.cnr.missioni.el.model.search.builder;

import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder.Operator;
import org.geosdi.geoplatform.experimental.el.dao.GPPageableElasticSearchDAO;
import org.geosdi.geoplatform.experimental.el.search.bool.BooleanExactSearch;
import org.geosdi.geoplatform.experimental.el.search.bool.BooleanFieldExistsSearch;
import org.geosdi.geoplatform.experimental.el.search.bool.BooleanMultiMatchSearch;
import org.geosdi.geoplatform.experimental.el.search.bool.IBooleanSearch;
import org.geosdi.geoplatform.experimental.el.search.date.IGPDateQuerySearch;
import org.geosdi.geoplatform.experimental.el.search.phrase.GPPhraseSearch;
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
     * @return {@link String}
     */
    String getIdUser();

    /**
     * @return {@link DateTime}
     */
    DateTime getFromDataInserimento();

    /**
     * @return {@link DateTime}
     */
    DateTime getToDataInserimento();

    /**
     * @return {@link Long}
     */
    Long getNumeroOrdineRimborso();

    /**
     * @return {@link DateTime}
     */
    public DateTime getFromDataRimbroso();

    /**
     * @return {@link DateTime}
     */
    public DateTime getToDataRimbroso();

    /**
     * @return {@link String}
     */
    public String getOggetto();

    /**
     * @return {@link String}
     */
    public String getMultiMatchValue();

    /**
     * @return {@link String}
     */
    public String getFieldExist();

    /**
     * @return {@link String}
     */
    public String getFieldNotExist();


    /**
     * @return {@link String}
     */
    public String getFieldMultiMatch();

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
        private String fieldMultiMatch = "missione.localita,missione.oggetto,missione.id,missione.shortUser,missione.progressivo";

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
                listAbstractBooleanSearch.add(new IGPDateQuerySearch.GPDateQuerySearch(SearchConstants.MISSIONE_FIELD_DATA_INSERIMENTO, IBooleanSearch.BooleanQueryType.MUST , fromDataInserimento,toDataInserimento));
            return self();
        }

        /**
         * @param idUser
         * @return {@link IMissioneSearchBuilder}
         */
        public IMissioneSearchBuilder withIdUser(String idUser) {
            this.idUser = idUser;
            if (idUser != null && !idUser.trim().equals(""))
                listAbstractBooleanSearch.add(new BooleanExactSearch(SearchConstants.MISSIONE_FIELD_ID_USER, idUser, IBooleanSearch.BooleanQueryType.MUST, MatchQueryBuilder.Operator.AND));
            return self();
        }

        /**
         * @param stato
         * @return {@link IMissioneSearchBuilder}
         */
        public IMissioneSearchBuilder withStato(String stato) {
            this.stato = stato;
            if (stato != null && !stato.trim().equals(""))
                listAbstractBooleanSearch.add(new BooleanExactSearch(SearchConstants.MISSIONE_FIELD_STATO, stato, IBooleanSearch.BooleanQueryType.MUST, MatchQueryBuilder.Operator.AND));
            return self();
        }

        /**
         * @param numeroOrdineRimborso
         * @return {@link IMissioneSearchBuilder}
         */
        public IMissioneSearchBuilder withNumeroOrdineMissione(Long numeroOrdineRimborso) {
            this.numeroOrdineRimborso = numeroOrdineRimborso;
            if (numeroOrdineRimborso != null && !numeroOrdineRimborso.equals(""))
                listAbstractBooleanSearch.add(new BooleanExactSearch(SearchConstants.MISSIONE_FIELD_RIMBORSO_NUMERO_ORDINE, numeroOrdineRimborso, IBooleanSearch.BooleanQueryType.MUST, MatchQueryBuilder.Operator.AND));
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
                listAbstractBooleanSearch.add(new IGPDateQuerySearch.GPDateQuerySearch(SearchConstants.MISSIONE_FIELD_RIMBORSO_DATA_RIMBORSO, IBooleanSearch.BooleanQueryType.MUST , fromDataRimbroso,toDataRimbroso));
            return self();
        }

        /**
         * @param id
         * @return {@link IMissioneSearchBuilder}
         */
        public IMissioneSearchBuilder withId(String id) {
            this.id = id;
            if (id != null && !id.trim().equals(""))
                listAbstractBooleanSearch.add(new BooleanExactSearch(SearchConstants.MISSIONE_FIELD_ID, id, IBooleanSearch.BooleanQueryType.MUST, MatchQueryBuilder.Operator.AND));
            return self();
        }

        /**
         * @param oggetto
         * @return {@link IMissioneSearchBuilder}
         */
        public IMissioneSearchBuilder withOggetto(String oggetto) {
            this.oggetto = oggetto;
            if (oggetto != null && !oggetto.trim().equals(""))
                listAbstractBooleanSearch.add(new GPPhraseSearch(SearchConstants.MISSIONE_FIELD_OGGETTO, oggetto, IBooleanSearch.BooleanQueryType.MUST));
            return self();
        }

        /**
         * @param multiMatchValue
         * @return {@link IMissioneSearchBuilder}
         */
        public IMissioneSearchBuilder withMultiMatch(String multiMatchValue) {
            this.multiMatchValue = multiMatchValue;
            if (multiMatchValue != null && !multiMatchValue.trim().equals(""))
                listAbstractBooleanSearch.add(new BooleanMultiMatchSearch(multiMatchValue, IBooleanSearch.BooleanQueryType.MUST,Operator.OR,fieldMultiMatch.split(",")));
            return self();
        }

        /**
         * @param fieldExist
         * @return {@link IMissioneSearchBuilder}
         */
        public IMissioneSearchBuilder withFieldExist(String fieldExist) {
            this.fieldExist = fieldExist;
            if (fieldExist != null && !fieldExist.trim().equals(""))
                listAbstractBooleanSearch.add(new BooleanFieldExistsSearch(fieldExist, IBooleanSearch.BooleanQueryType.MUST));
            return self();
        }

        /**
         * @param fieldNotExist
         * @return {@link IMissioneSearchBuilder}
         */
        public IMissioneSearchBuilder withFieldNotExist(String fieldNotExist) {
            this.fieldNotExist = fieldNotExist;
            if (fieldNotExist != null && !fieldNotExist.trim().equals(""))
                listAbstractBooleanSearch.add(new BooleanFieldExistsSearch(fieldNotExist, IBooleanSearch.BooleanQueryType.MUST_NOT));
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

        public IMissioneSearchBuilder build(){
            this.multiFieldsSearch = new GPPageableElasticSearchDAO.MultiFieldsSearch(this.listAbstractBooleanSearch.stream().toArray(org.geosdi.geoplatform.experimental.el.search.bool.IBooleanSearch[]::new));
            return self();
        }

        /**
         * @return {@link DateTime}
         */
        public DateTime getFromDataInserimento() {
            return fromDataInserimento;
        }

        /**
         * @return {@link DateTime}
         */
        public DateTime getToDataInserimento() {
            return toDataInserimento;
        }

        /**
         * @return {@link String}
         */
        public String getIdUser() {
            return idUser;
        }

        /**
         * @return {@link String}
         */
        public String getStato() {
            return stato;
        }


        /**
         * @return {@link Long}
         */
        public Long getNumeroOrdineRimborso() {
            return numeroOrdineRimborso;
        }


        /**
         * @return {@link DateTime}
         */
        public DateTime getFromDataRimbroso() {
            return fromDataRimbroso;
        }


        /**
         * @return {@link DateTime}
         */
        public DateTime getToDataRimbroso() {
            return toDataRimbroso;
        }

        /**
         * @return {@link String}
         */
        public String getOggetto() {
            return oggetto;
        }

        /**
         * @return {@link String}
         */
        public String getMultiMatchValue() {
            return multiMatchValue;
        }

        /**
         * @return {@link String}
         */
        public String getFieldExist() {
            return fieldExist;
        }

        /**
         * @return {@link String}
         */
        public String getFieldNotExist() {
            return fieldNotExist;
        }

        /**
         * @return {@link String}
         */
        public String getFieldMultiMatch() {
            return fieldMultiMatch;
        }

        /**
         * @return {@link IMissioneSearchBuilder}
         */
        protected IMissioneSearchBuilder self() {
            return this;
        }
    }

}

