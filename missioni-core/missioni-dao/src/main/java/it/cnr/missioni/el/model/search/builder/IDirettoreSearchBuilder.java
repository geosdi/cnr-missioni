package it.cnr.missioni.el.model.search.builder;

import it.cnr.missioni.el.model.search.ExactSearch;

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
            this.setId(id);
            if (id != null && !id.trim().equals(""))
                booleanModelSearch.getListaSearch().add(new ExactSearch(SearchConstants.MASSIMALE_FIELD_ID, id));
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


