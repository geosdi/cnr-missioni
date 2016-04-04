package it.cnr.missioni.el.model.search.builder;

import it.cnr.missioni.el.model.search.ExactSearch;

/**
 * @author Salvia Vito
 */
public interface IQualificaUserSearchBuilder extends ISearchBuilder<IQualificaUserSearchBuilder>{
	
	/**
	 * 
	 * @param value
     * @return {@link IQualificaUserSearchBuilder}
	 */
	IQualificaUserSearchBuilder withValue(String value);
	
	/**
	 * 
     * @return {@link String}
	 */
	String getValue();
	
	/**
	 * 
	 * @param value
	 */
	void setValue(String value);
	
	public class QualificaUserSearchBuilder extends ISearchBuilder.AbstractSearchBuilder<IQualificaUserSearchBuilder>
	implements IQualificaUserSearchBuilder{


		private static final long serialVersionUID = 8819699628235873367L;
		private String value;
		
		private QualificaUserSearchBuilder() {
			this.fieldSort = SearchConstants.QUALIFICA_USER_FIELD_VALUE;
		}

		public static QualificaUserSearchBuilder getQualificaUserSearchBuilder() {
			return new QualificaUserSearchBuilder();
		}

		/**
		 * 
		 * @param value
		 * @return {@link IQualificaUserSearchBuilder}
		 */
		public IQualificaUserSearchBuilder withValue(String value) {
			this.setValue(value);
			if (value != null && !value.trim().equals(""))
				booleanModelSearch.getListaSearch().add(new ExactSearch(SearchConstants.QUALIFICA_USER_FIELD_VALUE, value));
			return self();
		}

		/**
		 * @return {@link String}
		 */
		public String getValue() {
			return value;
		}

		/**
		 * @param value 
		 */
		public void setValue(String value) {
			this.value = value;
		}

		/**
		 * 
         * @return {@link IQualificaUserSearchBuilder}
		 */
		protected IQualificaUserSearchBuilder self() {
			return this;
		}

		/**
		 * @param id
		 * @return {@link IQualificaUserSearchBuilder}
		 */
		@Override
		public IQualificaUserSearchBuilder withId(String id) {
			// TODO Auto-generated method stub
			return null;
		}

	}

}
