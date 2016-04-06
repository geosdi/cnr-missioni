package it.cnr.missioni.el.model.search.builder;

import it.cnr.missioni.el.model.search.ExactSearch;

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
                booleanModelSearch.getListaSearch()
                        .add(new ExactSearch(SearchConstants.RIMBORSO_KM_FIELD_ID, id));
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
