package it.cnr.missioni.el.model.search.builder;

import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.geosdi.geoplatform.experimental.el.dao.GPPageableElasticSearchDAO;
import org.geosdi.geoplatform.experimental.el.search.bool.BooleanExactSearch;
import org.geosdi.geoplatform.experimental.el.search.bool.IBooleanSearch;

/**
 * @author Salvia Vito
 */
public interface ITipologiaSpesaSearchBuilder extends ISearchBuilder<ITipologiaSpesaSearchBuilder> {

    /**
     * @param estera
     * @return {@link ITipologiaSpesaSearchBuilder}
     */
    ITipologiaSpesaSearchBuilder withEstera(boolean estera);

    /**
     * @param italia
     * @return {@link ITipologiaSpesaSearchBuilder}
     */
    ITipologiaSpesaSearchBuilder withItalia(boolean italia);

    /**
     * @param tipoTrattamento
     * @return {@link ITipologiaSpesaSearchBuilder}
     */
    ITipologiaSpesaSearchBuilder withTipoTrattamento(String tipoTrattamento);

    /**
     * @return {@link String}
     */
    String getTipoTrattamento();

    /**
     * @return {@link boolean}
     */
    boolean isItalia();

    /**
     * @return {@link boolean}
     */
    boolean isEstera();

    public class TipologiaSpesaSearchBuilder extends ISearchBuilder.AbstractSearchBuilder<ITipologiaSpesaSearchBuilder>
            implements ITipologiaSpesaSearchBuilder {

        /**
         *
         */
        private static final long serialVersionUID = -817549323873815355L;
        private String tipoTrattamento;
        private boolean italia = false;
        private boolean estera = false;

        private TipologiaSpesaSearchBuilder() {
            this.fieldSort = SearchConstants.TIPOLOGIA_SPESA_FIELD_VALUE;
        }

        public static TipologiaSpesaSearchBuilder getTipologiaSpesaSearchBuilder() {
            return new TipologiaSpesaSearchBuilder();
        }

        /**
         * @param id
         * @return {@link ITipologiaSpesaSearchBuilder}
         */
        public ITipologiaSpesaSearchBuilder withId(String id) {
            this.id = id;
            if (id != null && !id.trim().equals(""))
                listAbstractBooleanSearch.add(new BooleanExactSearch(SearchConstants.TIPOLOGIA_SPESA_FIELD_ID, id, IBooleanSearch.BooleanQueryType.MUST, Operator.AND));
            return self();
        }

        /**
         * @param estera
         * @return {@link ITipologiaSpesaSearchBuilder}
         */
        public ITipologiaSpesaSearchBuilder withEstera(boolean estera) {
            this.estera = estera;
            if (estera)
                listAbstractBooleanSearch.add(new BooleanExactSearch(SearchConstants.TIPOLOGIA_SPESA_FIELD_ESTERA, estera, IBooleanSearch.BooleanQueryType.MUST, Operator.AND));
            return self();
        }

        /**
         * @param italia
         * @return {@link ITipologiaSpesaSearchBuilder}
         */
        public ITipologiaSpesaSearchBuilder withItalia(boolean italia) {
            this.italia = italia;
            if (italia)
                listAbstractBooleanSearch.add(new BooleanExactSearch(SearchConstants.TIPOLOGIA_SPESA_FIELD_ITALIA, italia, IBooleanSearch.BooleanQueryType.MUST, Operator.AND));
            return self();
        }

        /**
         * @param tipoTrattamento
         * @return {@link ITipologiaSpesaSearchBuilder}
         */
        public ITipologiaSpesaSearchBuilder withTipoTrattamento(String tipoTrattamento) {
            this.tipoTrattamento = tipoTrattamento;
            if (tipoTrattamento != null && !tipoTrattamento.trim().equals(""))
                listAbstractBooleanSearch.add(new BooleanExactSearch(SearchConstants.TIPOLOGIA_SPESA_FIELD_TIPO_TRATTAMENTO, tipoTrattamento, IBooleanSearch.BooleanQueryType.MUST, Operator.AND));
            return self();
        }

        public ITipologiaSpesaSearchBuilder build(){
            this.multiFieldsSearch = new GPPageableElasticSearchDAO.MultiFieldsSearch(this.listAbstractBooleanSearch.stream().toArray(IBooleanSearch[]::new));
            return self();
        }

        /**
         * @return {@link String}
         */
        public String getTipoTrattamento() {
            return tipoTrattamento;
        }

        /**
         * @return {@link boolean}
         */
        public boolean isItalia() {
            return italia;
        }

        /**
         * @return {@link boolean}
         */
        public boolean isEstera() {
            return estera;
        }

        /**
         * @return {@link ITipologiaSpesaSearchBuilder}
         */
        protected ITipologiaSpesaSearchBuilder self() {
            return this;
        }

    }

}
