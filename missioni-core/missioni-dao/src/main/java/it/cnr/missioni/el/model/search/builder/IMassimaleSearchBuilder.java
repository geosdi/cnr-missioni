package it.cnr.missioni.el.model.search.builder;

import org.elasticsearch.index.query.MatchQueryBuilder;
import org.geosdi.geoplatform.experimental.el.dao.GPPageableElasticSearchDAO;
import org.geosdi.geoplatform.experimental.el.search.bool.BooleanExactSearch;
import org.geosdi.geoplatform.experimental.el.search.bool.IBooleanSearch;

/**
 * @author Salvia Vito
 */

public interface IMassimaleSearchBuilder extends ISearchBuilder<IMassimaleSearchBuilder> {

    /**
     * @param id
     * @return {@link IMassimaleSearchBuilder}
     */
    IMassimaleSearchBuilder withId(String id);

    /**
     * @param notId
     * @return {@link IMassimaleSearchBuilder}
     */
    IMassimaleSearchBuilder withNotId(String notId);

    /**
     * @param livello
     * @return {@link IMassimaleSearchBuilder}
     */
    IMassimaleSearchBuilder withLivello(String livello);

    /**
     * @param areaGeografica
     * @return {@link IMassimaleSearchBuilder}
     */
    IMassimaleSearchBuilder withAreaGeografica(String areaGeografica);

    /**
     * @param tipo
     * @return {@link IMassimaleSearchBuilder}
     */
    IMassimaleSearchBuilder withTipo(String tipo);

    /**
     * @return {@link String}
     */
    String getNotId();

    /**
     * @return {@link String}
     */
    String getLivello();

    /**
     * @return {@link String}
     */
    String getAreaGeografica();

    /**
     * @return {@link String}
     */
    String getTipo();

    class MassimaleSearchBuilder extends ISearchBuilder.AbstractSearchBuilder<IMassimaleSearchBuilder>
            implements IMassimaleSearchBuilder {

        /**
         *
         */
        private static final long serialVersionUID = 3676936353865160224L;
        private String notId;
        private String livello;
        private String areaGeografica;
        private String tipo;

        private MassimaleSearchBuilder() {
            this.fieldSort = SearchConstants.MASSIMALE_FIELD_AREA_GEOGRAFICA;
        }

        public static IMassimaleSearchBuilder getMassimaleSearchBuilder() {
            return new IMassimaleSearchBuilder.MassimaleSearchBuilder();
        }

        /**
         * @param id
         * @return {@link IMassimaleSearchBuilder}
         */
        public IMassimaleSearchBuilder withId(String id) {
            this.id = id;
            if (id != null && !id.trim().equals(""))
                listAbstractBooleanSearch.add(new BooleanExactSearch(SearchConstants.MASSIMALE_FIELD_ID, id, IBooleanSearch.BooleanQueryType.MUST, MatchQueryBuilder.Operator.AND));
            return self();
        }

        /**
         * @param notId
         * @return {@link IMassimaleSearchBuilder}
         */
        public IMassimaleSearchBuilder withNotId(String notId) {
            this.notId = notId;
            if (notId != null && !notId.trim().equals(""))
                listAbstractBooleanSearch.add(new BooleanExactSearch(SearchConstants.MASSIMALE_FIELD_ID, notId, IBooleanSearch.BooleanQueryType.MUST_NOT, MatchQueryBuilder.Operator.AND));
            return self();
        }

        /**
         * @param livello
         * @return {@link IMassimaleSearchBuilder}
         */
        public IMassimaleSearchBuilder withLivello(String livello) {
            this.livello = livello;
            if (livello != null && !livello.trim().equals(""))
                listAbstractBooleanSearch.add(new BooleanExactSearch(SearchConstants.MASSIMALE_FIELD_LIVELLO, livello, IBooleanSearch.BooleanQueryType.MUST, MatchQueryBuilder.Operator.AND));
            return self();
        }

        /**
         * @param areaGeografica
         * @return {@link IMassimaleSearchBuilder}
         */
        public IMassimaleSearchBuilder withAreaGeografica(String areaGeografica) {
            this.areaGeografica = areaGeografica;
            if (areaGeografica != null && !areaGeografica.trim().equals(""))
                listAbstractBooleanSearch.add(new BooleanExactSearch(SearchConstants.MASSIMALE_FIELD_AREA_GEOGRAFICA, areaGeografica, IBooleanSearch.BooleanQueryType.MUST, MatchQueryBuilder.Operator.AND));
            return self();
        }

        /**
         * @param tipo
         * @return {@link IMassimaleSearchBuilder}
         */
        public IMassimaleSearchBuilder withTipo(String tipo) {
            this.tipo = tipo;
            if (tipo != null && !tipo.trim().equals(""))
                listAbstractBooleanSearch.add(new BooleanExactSearch(SearchConstants.MASSIMALE_FIELD_TIPO, tipo, IBooleanSearch.BooleanQueryType.MUST, MatchQueryBuilder.Operator.AND));
            return self();
        }

        public IMassimaleSearchBuilder build(){
            this.multiFieldsSearch = new GPPageableElasticSearchDAO.MultiFieldsSearch(this.listAbstractBooleanSearch.stream().toArray(IBooleanSearch[]::new));
            return self();
        }

        /**
         * @return {@link String}
         */
        public String getNotId() {
            return notId;
        }

        /**
         * @return {@link String}
         */
        public String getLivello() {
            return livello;
        }

        /**
         * @return {@link String}
         */
        public String getAreaGeografica() {
            return areaGeografica;
        }

        /**
         * @return {@link String}
         */
        public String getTipo() {
            return tipo;
        }

        /**
         * @return
         */
        protected IMassimaleSearchBuilder self() {
            return this;
        }
    }

}

