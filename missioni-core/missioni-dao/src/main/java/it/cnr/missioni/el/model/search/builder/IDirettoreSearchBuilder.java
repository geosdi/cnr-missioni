package it.cnr.missioni.el.model.search.builder;

import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.geosdi.geoplatform.experimental.el.dao.GPPageableElasticSearchDAO;
import org.geosdi.geoplatform.experimental.el.search.bool.BooleanExactSearch;
import org.geosdi.geoplatform.experimental.el.search.bool.IBooleanSearch;

/**
 * @author Salvia Vito
 */
public interface IDirettoreSearchBuilder extends ISearchBuilder<IDirettoreSearchBuilder> {


    class DirettoreSearchBuilder extends ISearchBuilder.AbstractSearchBuilder<IDirettoreSearchBuilder>
            implements IDirettoreSearchBuilder {

        /**
         *
         */
        private static final long serialVersionUID = -2499082941102110757L;

        private DirettoreSearchBuilder() {
        }

        public static IDirettoreSearchBuilder getDirettoreSearchBuilder() {
            return new DirettoreSearchBuilder();
        }

        /**
         * @return {@link IDirettoreSearchBuilder}
         */
        public IDirettoreSearchBuilder withId(String id) {
            this.id = id;
            if (id != null && !id.trim().equals(""))
                listAbstractBooleanSearch.add(new BooleanExactSearch(SearchConstants.MASSIMALE_FIELD_ID, id, IBooleanSearch.BooleanQueryType.MUST, Operator.AND));
            return self();
        }

        public IDirettoreSearchBuilder build(){
            this.multiFieldsSearch = new GPPageableElasticSearchDAO.MultiFieldsSearch(this.listAbstractBooleanSearch.stream().toArray(IBooleanSearch[]::new));
            return self();
        }

        /**
         * @return {@link String}
         */
        @Override
        protected IDirettoreSearchBuilder self() {
            return this;
        }
    }
}


