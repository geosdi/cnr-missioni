package it.cnr.missioni.el.model.search.builder;

import org.joda.time.DateTime;

import it.cnr.missioni.el.model.search.DateRangeSearch;

/**
 * @author Salvia Vito
 */
public interface IPrenotazioneSearchBuilder extends ISearchBuilder<IPrenotazioneSearchBuilder>{
	
    /**
     * @return {@link IPrenotazioneSearchBuilder}
     */
	IPrenotazioneSearchBuilder withRangeData(DateTime dataFrom, DateTime dataTo);
	
	/**
	 * @return the dataFrom
	 */
	DateTime getDataFrom();

	/**
	 * @param dataFrom 
	 */
	void setDataFrom(DateTime dataFrom);

	/**
	 * @return the dataTo
	 */
	DateTime getDataTo();

	/**
	 * @param dataTo 
	 */
	void setDataTo(DateTime dataTo);
	
	public class PrenotazioneSearchBuilder extends ISearchBuilder.AbstractSearchBuilder<IPrenotazioneSearchBuilder>
	implements IPrenotazioneSearchBuilder{

		/**
		 * 
		 */
		private static final long serialVersionUID = 3425236912503251640L;
		/**
		 * 
		 */
		private DateTime dataFrom = null;
		private DateTime dataTo = null;
		

		private PrenotazioneSearchBuilder() {
		}

		public static IPrenotazioneSearchBuilder getPrenotazioneSearchBuilder() {
			return new PrenotazioneSearchBuilder();
		}

	     /**
         * @return {@link IPrenotazioneSearchBuilder}
         */
		public IPrenotazioneSearchBuilder withRangeData(DateTime dataFrom, DateTime dataTo) {
			this.dataFrom = dataFrom;
			this.dataTo = dataTo;
				booleanModelSearch.getListaSearch().add(new DateRangeSearch(SearchConstants.PRENOTAZIONE_FIELD_DATA_FROM,
						dataFrom, dataTo));
			return self();
		}

		/**
		 * @return the dataFrom
		 */
		public DateTime getDataFrom() {
			return dataFrom;
		}

		/**
		 * @param dataFrom 
		 */
		public void setDataFrom(DateTime dataFrom) {
			this.dataFrom = dataFrom;
		}

		/**
		 * @return the dataTo
		 */
		public DateTime getDataTo() {
			return dataTo;
		}

		/**
		 * @param dataTo 
		 */
		public void setDataTo(DateTime dataTo) {
			this.dataTo = dataTo;
		}

		/**
		 * 
		 * @return
		 */
		protected IPrenotazioneSearchBuilder self() {
			return this;
		}

		/**
		 * @param id
		 * @return
		 */
		@Override
		public IPrenotazioneSearchBuilder withId(String id) {
			// TODO Auto-generated method stub
			return null;
		}


	}

}
