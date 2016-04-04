package it.cnr.missioni.el.model.search.builder;

import it.cnr.missioni.el.model.search.ExactSearch;

/**
 * @author Salvia Vito
 */
public interface ITipologiaSpesaSearchBuilder extends ISearchBuilder<ITipologiaSpesaSearchBuilder>{
	
	/**
	 * 
	 * @param estera
	 * @return {@link ITipologiaSpesaSearchBuilder}
	 */
	ITipologiaSpesaSearchBuilder withEstera(boolean estera);
	
	/**
	 * 
	 * @param italia
	 * @return {@link ITipologiaSpesaSearchBuilder}
	 */
	ITipologiaSpesaSearchBuilder withItalia(boolean italia);
	
	/**
	 * 
	 * @param tipoTrattamento
	 * @return {@link ITipologiaSpesaSearchBuilder}
	 */
	ITipologiaSpesaSearchBuilder withTipoTrattamento(String tipoTrattamento);
	
	/**
	 * 
	 * @return {@link String}
	 */
	String getTipoTrattamento();
	
	/**
	 * 
	 * @param tipoTrattamento
	 */
	void setTipoTrattamento(String tipoTrattamento);
	
	/**
	 * 
	 * @return {@link boolean}
	 */
	boolean isItalia();
	
	/**
	 * 
	 * @param italia
	 */
	void setItalia(boolean italia);
	
	/**
	 * 
	 * @return {@link boolean}
	 */
	boolean isEstera();
	
	/**
	 * 
	 * @param estera
	 */
	void setEstera(boolean estera);
	
	public class TipologiaSpesaSearchBuilder extends ISearchBuilder.AbstractSearchBuilder<ITipologiaSpesaSearchBuilder>
	implements ITipologiaSpesaSearchBuilder{

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
		 * 
		 * @param id
		 * @return {@link ITipologiaSpesaSearchBuilder}
		 */
		public ITipologiaSpesaSearchBuilder withId(String id) {
			this.setId(id);
			if (id != null && !id.trim().equals(""))
				booleanModelSearch.getListaSearch().add(new ExactSearch(SearchConstants.TIPOLOGIA_SPESA_FIELD_ID, id));
			return self();
		}

		/**
		 * 
		 * @param estera
		 * @return {@link ITipologiaSpesaSearchBuilder}
		 */
		public ITipologiaSpesaSearchBuilder withEstera(boolean estera) {
			this.estera = estera;
			if(estera)
				booleanModelSearch.getListaSearch().add(new ExactSearch(SearchConstants.TIPOLOGIA_SPESA_FIELD_ESTERA, estera));
			return self();
		}
		
		/**
		 * 
		 * @param italia
		 * @return {@link ITipologiaSpesaSearchBuilder}
		 */
		public ITipologiaSpesaSearchBuilder withItalia(boolean italia) {
			this.italia = italia;
			if(italia)
				booleanModelSearch.getListaSearch().add(new ExactSearch(SearchConstants.TIPOLOGIA_SPESA_FIELD_ITALIA, italia));
			return self();
		}

		/**
		 * 
		 * @param tipoTrattamento
		 * @return {@link ITipologiaSpesaSearchBuilder}
		 */
		public ITipologiaSpesaSearchBuilder withTipoTrattamento(String tipoTrattamento) {
			this.tipoTrattamento = tipoTrattamento;
			if (tipoTrattamento != null && !tipoTrattamento.trim().equals(""))
				booleanModelSearch.getListaSearch().add(new ExactSearch(SearchConstants.TIPOLOGIA_SPESA_FIELD_TIPO_TRATTAMENTO, tipoTrattamento));
			return self();
		}

		/**
		 * @return {@link String}
		 */
		public String getTipoTrattamento() {
			return tipoTrattamento;
		}

		/**
		 * @param tipoTrattamento 
		 */
		public void setTipoTrattamento(String tipoTrattamento) {
			this.tipoTrattamento = tipoTrattamento;
		}

		/**
		 * @return {@link boolean}
		 */
		public boolean isItalia() {
			return italia;
		}

		/**
		 * @param italia 
		 */
		public void setItalia(boolean italia) {
			this.italia = italia;
		}

		/**
		 * @return {@link boolean}
		 */
		public boolean isEstera() {
			return estera;
		}

		/**
		 * @param estera 
		 */
		public void setEstera(boolean estera) {
			this.estera = estera;
		}

		/**
		 * 
		 * @return {@link ITipologiaSpesaSearchBuilder}
		 */
		protected ITipologiaSpesaSearchBuilder self() {
			return this;
		}

	}

}