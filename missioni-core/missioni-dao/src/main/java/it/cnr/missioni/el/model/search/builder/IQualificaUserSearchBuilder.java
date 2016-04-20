package it.cnr.missioni.el.model.search.builder;

import org.elasticsearch.index.query.MatchQueryBuilder;
import org.geosdi.geoplatform.experimental.el.dao.GPPageableElasticSearchDAO;
import org.geosdi.geoplatform.experimental.el.search.bool.BooleanExactSearch;
import org.geosdi.geoplatform.experimental.el.search.bool.IBooleanSearch;

/**
 * @author Salvia Vito
 */
public interface IQualificaUserSearchBuilder extends ISearchBuilder<IQualificaUserSearchBuilder> {

    /**
     * @param value
     * @return {@link IQualificaUserSearchBuilder}
     */
    IQualificaUserSearchBuilder withValue(String value);

    /**
     * @return {@link String}
     */
    String getValue();

    public class QualificaUserSearchBuilder extends ISearchBuilder.AbstractSearchBuilder<IQualificaUserSearchBuilder>
            implements IQualificaUserSearchBuilder {


        private static final long serialVersionUID = 8819699628235873367L;
        private String value;

        private QualificaUserSearchBuilder() {
            this.fieldSort = SearchConstants.QUALIFICA_USER_FIELD_VALUE;
        }

        public static QualificaUserSearchBuilder getQualificaUserSearchBuilder() {
            return new QualificaUserSearchBuilder();
        }

        /**
         * @param value
         * @return {@link IQualificaUserSearchBuilder}
         */
        public IQualificaUserSearchBuilder withValue(String value) {
            this.value = value;
            if (value != null && !value.trim().equals(""))
                listAbstractBooleanSearch.add(new BooleanExactSearch(SearchConstants.QUALIFICA_USER_FIELD_VALUE, value, IBooleanSearch.BooleanQueryType.MUST, MatchQueryBuilder.Operator.AND));
            return self();
        }

        /**
         * @param id
         * @return {@link IQualificaUserSearchBuilder}
         */
        public IQualificaUserSearchBuilder withId(String id) {
            this.id = id;
            if (id != null && !id.trim().equals(""))
                listAbstractBooleanSearch.add(new BooleanExactSearch(SearchConstants.QUALIFICA_USER_FIELD_ID, id, IBooleanSearch.BooleanQueryType.MUST, MatchQueryBuilder.Operator.AND));

/*            booleanModelSearch.getListaSearch()
                    .add(new ExactSearch(SearchConstants.QUALIFICA_USER_FIELD_ID, id));*/
            return self();
        }

        public IQualificaUserSearchBuilder build(){
            this.multiFieldsSearch = new GPPageableElasticSearchDAO.MultiFieldsSearch(this.listAbstractBooleanSearch.stream().toArray(IBooleanSearch[]::new));
            return self();
        }

        /**
         * @return {@link String}
         */
        public String getValue() {
            return value;
        }

        /**
         * @return {@link IQualificaUserSearchBuilder}
         */
        protected IQualificaUserSearchBuilder self() {
            return this;
        }



    }

}
