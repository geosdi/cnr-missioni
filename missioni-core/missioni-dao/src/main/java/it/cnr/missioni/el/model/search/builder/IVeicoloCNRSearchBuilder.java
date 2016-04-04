package it.cnr.missioni.el.model.search.builder;

import it.cnr.missioni.el.model.search.EnumBooleanType;
import it.cnr.missioni.el.model.search.ExactSearch;

/**
 * @author Salvia Vito
 */
public interface IVeicoloCNRSearchBuilder extends ISearchBuilder<IVeicoloCNRSearchBuilder>{
	
	/**
	 * 
	 * @param stato
	 * @return {@link IVeicoloCNRSearchBuilder}
	 */
	IVeicoloCNRSearchBuilder withStato(String stato);
	
	/**
	 * 
	 * @param targa
	 * @return {@link IVeicoloCNRSearchBuilder}
	 */
	IVeicoloCNRSearchBuilder withTarga(String targa);
	
	/**
	 * 
	 * @param polizzaAssicurativa
	 * @return {@link IVeicoloCNRSearchBuilder}
	 */
	IVeicoloCNRSearchBuilder withPolizzaAssicurativa(String polizzaAssicurativa);
	
	/**
	 * 
	 * @param cartaCircolazione
	 * @return {@link IVeicoloCNRSearchBuilder}
	 */
	IVeicoloCNRSearchBuilder withCartaCircolazione(String cartaCircolazione);
	
	/**
	 * 
	 * @param notId
	 * @return {@link IVeicoloCNRSearchBuilder}
	 */
	IVeicoloCNRSearchBuilder withNotId(String notId);
	
	/**
	 * 
	 * @return {@link String}
	 */
	String getStato();
	
	/**
	 * 
	 * @param stato
	 */
	void setStato(String stato);
	
	/**
	 * 
	 * @return {@link String}
	 */
	String getTarga();
	
	/**
	 * 
	 * @param targa
	 */
	void setTarga(String targa);
	
	/**
	 * 
	 * @return {@link String}
	 */
	String getCartaCircolazione();
	
	/**
	 * 
	 * @param cartaCircolazione
	 */
	void setCartaCircolazione(String cartaCircolazione);
	
	/**
	 * 
	 * @return {@link String}
	 */
	String getPolizzaAssicurativa();
	
	/**
	 * 
	 * @param polizzaAssicurativa
	 */
	void setPolizzaAssicurativa(String polizzaAssicurativa);
	
	/**
	 * 
	 * @return {@link String}
	 */
	String getNotId();
	
	/**
	 * 
	 * @param notId
	 */
	void setNotId(String notId);
	
	public class VeicoloCNRSearchBuilder extends ISearchBuilder.AbstractSearchBuilder<IVeicoloCNRSearchBuilder>
	implements IVeicoloCNRSearchBuilder{

		/**
		 * 
		 */
		private static final long serialVersionUID = 7659751658210843810L;
		private String stato = null;
		private String targa;
		private String cartaCircolazione;
		private String polizzaAssicurativa;
		private String notId;

		private VeicoloCNRSearchBuilder() {
			this.fieldSort = SearchConstants.VEICOLO_CNR_FIELD_TIPO;
		}

		public static IVeicoloCNRSearchBuilder getVeicoloCNRSearchBuilder() {
			return new VeicoloCNRSearchBuilder();
		}

		/**
		 * 
		 * @param stato
		 * @return {@link IVeicoloCNRSearchBuilder}
		 */
		public IVeicoloCNRSearchBuilder withStato(String stato) {
			this.stato = stato;
			if (stato != null && !stato.trim().equals(""))
				booleanModelSearch.getListaSearch().add(new ExactSearch(SearchConstants.VEICOLO_CNR_FIELD_STATO, stato));
			return self();
		}

		/**
		 * 
		 * @param targa
		 * @return {@link IVeicoloCNRSearchBuilder}
		 */
		public IVeicoloCNRSearchBuilder withTarga(String targa) {
			this.targa = targa;
			if (targa != null && !targa.trim().equals(""))
				booleanModelSearch.getListaSearch().add(new ExactSearch(SearchConstants.VEICOLO_CNR_FIELD_TARGA, targa));
			return self();
		}

		/**
		 * 
		 * @param polizzaAssicurativa
		 * @return {@link IVeicoloCNRSearchBuilder}
		 */
		public IVeicoloCNRSearchBuilder withPolizzaAssicurativa(String polizzaAssicurativa) {
			this.polizzaAssicurativa = polizzaAssicurativa;
			if (polizzaAssicurativa != null && !polizzaAssicurativa.trim().equals(""))
				booleanModelSearch.getListaSearch()
						.add(new ExactSearch(SearchConstants.VEICOLO_CNR_FIELD_POLIZZA_ASSICURATIVA,polizzaAssicurativa));
			return self();
		}

		/**
		 * 
		 * @param cartaCircolazione
		 * @return {@link IVeicoloCNRSearchBuilder}
		 */
		public IVeicoloCNRSearchBuilder withCartaCircolazione(String cartaCircolazione) {
			this.cartaCircolazione = cartaCircolazione;
			if (cartaCircolazione != null && !cartaCircolazione.trim().equals(""))
				booleanModelSearch.getListaSearch()
						.add(new ExactSearch(SearchConstants.VEICOLO_CNR_FIELD_CARTA_CIRCOLAZIONE,cartaCircolazione));
			return self();
		}

		/**
		 * 
		 * @param notId
		 * @return {@link IVeicoloCNRSearchBuilder}
		 */
		public IVeicoloCNRSearchBuilder withNotId(String notId) {
			this.notId = notId;
			if (notId != null && !notId.trim().equals(""))
				booleanModelSearch.getListaSearch()
						.add(new ExactSearch(SearchConstants.VEICOLO_CNR_FIELD_ID, notId, EnumBooleanType.MUST_NOT));
			return self();
		}

		/**
		 * @return {@link String}
		 */
		public String getStato() {
			return stato;
		}

		/**
		 * @param stato
		 */
		public void setStato(String stato) {
			this.stato = stato;
		}

		/**
		 * @return {@link String}
		 */
		public String getTarga() {
			return targa;
		}

		/**
		 * @param targa
		 */
		public void setTarga(String targa) {
			this.targa = targa;
		}

		/**
		 * @return {@link String}
		 */
		public String getCartaCircolazione() {
			return cartaCircolazione;
		}

		/**
		 * @param cartaCircolazione
		 */
		public void setCartaCircolazione(String cartaCircolazione) {
			this.cartaCircolazione = cartaCircolazione;
		}

		/**
		 * @return {@link String}
		 */
		public String getPolizzaAssicurativa() {
			return polizzaAssicurativa;
		}

		/**
		 * @param polizzaAssicurativa
		 */
		public void setPolizzaAssicurativa(String polizzaAssicurativa) {
			this.polizzaAssicurativa = polizzaAssicurativa;
		}

		/**
		 * @return {@link String}
		 */
		public String getNotId() {
			return notId;
		}

		/**
		 * @param notId 
		 */
		public void setNotId(String notId) {
			this.notId = notId;
		}

		/**
		 * 
		 * @return {@link IVeicoloCNRSearchBuilder}
		 */
		protected IVeicoloCNRSearchBuilder self() {
			return this;
		}

		/**
		 * @param id
		 * @return
		 */
		@Override
		public IVeicoloCNRSearchBuilder withId(String id) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}

}
