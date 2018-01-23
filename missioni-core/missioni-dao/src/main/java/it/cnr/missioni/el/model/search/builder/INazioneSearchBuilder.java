package it.cnr.missioni.el.model.search.builder;

import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.geosdi.geoplatform.experimental.el.dao.GPPageableElasticSearchDAO;
import org.geosdi.geoplatform.experimental.el.search.bool.BooleanExactSearch;
import org.geosdi.geoplatform.experimental.el.search.bool.IBooleanSearch;

/**
 * @author Salvia Vito
 */
public interface INazioneSearchBuilder extends ISearchBuilder<INazioneSearchBuilder> {


    public class NazioneSearchBuilder extends ISearchBuilder.AbstractSearchBuilder<INazioneSearchBuilder>
            implements INazioneSearchBuilder {

        /**
         *
         */
        private static final long serialVersionUID = -807918615534715747L;
        private NazioneSearchBuilder() {
            this.fieldSort = SearchConstants.NAZIONE_FIELD_VALUE;
        }

        public static INazioneSearchBuilder getNazioneSearchBuilder() {
            return new NazioneSearchBuilder();
        }

        /**
         * @return {@link INazioneSearchBuilder}
         */
        public INazioneSearchBuilder withId(String id) {
            this.id = id;
            if (id != null && !id.trim().equals(""))
                listAbstractBooleanSearch.add(new BooleanExactSearch(SearchConstants.NAZIONE_FIELD_ID, id, IBooleanSearch.BooleanQueryType.MUST, Operator.AND));
            return self();
        }

        public INazioneSearchBuilder build(){
            this.multiFieldsSearch = new GPPageableElasticSearchDAO.MultiFieldsSearch(this.listAbstractBooleanSearch.stream().toArray(IBooleanSearch[]::new));
            return self();
        }

        /**
         * @return
         */
        protected INazioneSearchBuilder self() {
            return this;
        }

    }

}


