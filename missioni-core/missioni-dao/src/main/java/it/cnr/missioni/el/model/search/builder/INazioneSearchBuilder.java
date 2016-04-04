package it.cnr.missioni.el.model.search.builder;

import it.cnr.missioni.el.model.search.ExactSearch;

/**
 * @author Salvia Vito
 */
public interface INazioneSearchBuilder extends ISearchBuilder<INazioneSearchBuilder>{
	
	
	public class NazioneSearchBuilder extends ISearchBuilder.AbstractSearchBuilder<INazioneSearchBuilder> 
	implements INazioneSearchBuilder{

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
			this.setId(id);
			if (id != null && !id.trim().equals(""))
				booleanModelSearch.getListaSearch()
						.add(new ExactSearch(SearchConstants.NAZIONE_FIELD_ID, id));
			return self();
		}

		/**
		 * 
		 * @return
		 */
		protected INazioneSearchBuilder self() {
			return this;
		}

	}

}


