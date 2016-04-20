package it.cnr.missioni.el.model.search.builder;

import org.elasticsearch.index.query.MatchQueryBuilder;
import org.geosdi.geoplatform.experimental.el.dao.GPPageableElasticSearchDAO;
import org.geosdi.geoplatform.experimental.el.search.bool.BooleanExactSearch;
import org.geosdi.geoplatform.experimental.el.search.bool.IBooleanSearch;

/**
 * @author Salvia Vito
 */
public interface IRimborsoKmSearchBuilder extends ISearchBuilder<IRimborsoKmSearchBuilder> {

    public class RimborsoKmSearchBuilder extends ISearchBuilder.AbstractSearchBuilder<IRimborsoKmSearchBuilder>
            implements IRimborsoKmSearchBuilder {

        /**
         *
         */
        private static final long serialVersionUID = -817549323873815355L;

        private RimborsoKmSearchBuilder() {
        }

        public static RimborsoKmSearchBuilder getRimborsoKmSearchBuilder() {
            return new RimborsoKmSearchBuilder();
        }

        /**
         * @param id
         * @return {@link IRimborsoKmSearchBuilder}
         */
        public IRimborsoKmSearchBuilder withId(String id) {
            this.id = id;
            if (id != null && !id.trim().equals(""))
                listAbstractBooleanSearch.add(new BooleanExactSearch(SearchConstants.RIMBORSO_KM_FIELD_ID, id, IBooleanSearch.BooleanQueryType.MUST, MatchQueryBuilder.Operator.AND));
            return self();
        }

        public IRimborsoKmSearchBuilder build(){
            this.multiFieldsSearch = new GPPageableElasticSearchDAO.MultiFieldsSearch(this.listAbstractBooleanSearch.stream().toArray(IBooleanSearch[]::new));
            return self();
        }

        /**
         * @return
         */
        protected IRimborsoKmSearchBuilder self() {
            return this;
        }

    }

}
