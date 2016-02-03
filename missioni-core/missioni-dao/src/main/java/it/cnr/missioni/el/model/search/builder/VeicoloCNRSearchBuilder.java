package it.cnr.missioni.el.model.search.builder;

import java.io.Serializable;

import org.elasticsearch.index.query.BoolQueryBuilder;

import it.cnr.missioni.el.model.search.BooleanModelSearch;
import it.cnr.missioni.el.model.search.EnumBooleanType;
import it.cnr.missioni.el.model.search.ExactSearch;

/**
 * @author Salvia Vito
 */
public class VeicoloCNRSearchBuilder implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7659751658210843810L;
	private BooleanModelSearch booleanModelSearch;
	private String stato = null;
	private String targa;
	private String cartaCircolazione;
	private String polizzaAssicurativa;
	private String notId;
	private boolean all = false;
	private int size = 10;
	private int from = 0;

	private VeicoloCNRSearchBuilder() {
		booleanModelSearch = new BooleanModelSearch();
	}

	public static VeicoloCNRSearchBuilder getVeicoloCNRSearchBuilder() {
		return new VeicoloCNRSearchBuilder();
	}

	public VeicoloCNRSearchBuilder withStato(String stato) {
		this.stato = stato;
		if (stato != null && !stato.trim().equals(""))
			booleanModelSearch.getListaSearch().add(new ExactSearch(SearchConstants.VEICOLO_CNR_FIELD_STATO, stato));
		return self();
	}

	public VeicoloCNRSearchBuilder withTarga(String targa) {
		this.targa = targa;
		if (targa != null && !targa.trim().equals(""))
			booleanModelSearch.getListaSearch().add(new ExactSearch(SearchConstants.VEICOLO_CNR_FIELD_TARGA, targa));
		return self();
	}

	public VeicoloCNRSearchBuilder withPolizzaAssicurativa(String polizzaAssicurativa) {
		this.polizzaAssicurativa = polizzaAssicurativa;
		if (polizzaAssicurativa != null && !polizzaAssicurativa.trim().equals(""))
			booleanModelSearch.getListaSearch()
					.add(new ExactSearch(SearchConstants.VEICOLO_CNR_FIELD_POLIZZA_ASSICURATIVA,polizzaAssicurativa));
		return self();
	}

	public VeicoloCNRSearchBuilder withCartaCircolazione(String cartaCircolazione) {
		this.cartaCircolazione = cartaCircolazione;
		if (cartaCircolazione != null && !cartaCircolazione.trim().equals(""))
			booleanModelSearch.getListaSearch()
					.add(new ExactSearch(SearchConstants.VEICOLO_CNR_FIELD_CARTA_CIRCOLAZIONE,cartaCircolazione));
		return self();
	}

	public VeicoloCNRSearchBuilder withNotId(String notId) {
		this.notId = notId;
		if (notId != null && !notId.trim().equals(""))
			booleanModelSearch.getListaSearch()
					.add(new ExactSearch(SearchConstants.VEICOLO_CNR_FIELD_ID, notId, EnumBooleanType.MUST_NOT));
		return self();
	}

	public VeicoloCNRSearchBuilder withSize(int size) {
		this.size = size;
		return self();
	}

	public VeicoloCNRSearchBuilder withFrom(int from) {
		this.from = from;
		return self();
	}
	
	public VeicoloCNRSearchBuilder withAll(boolean all) {
		this.setAll(all);
		return self();
	}

	public BoolQueryBuilder buildQuery() {
		return booleanModelSearch.buildQuery();
	}

	/**
	 * @return the booleanModelSearch
	 */
	public BooleanModelSearch getBooleanModelSearch() {
		return booleanModelSearch;
	}

	/**
	 * @param booleanModelSearch
	 */
	public void setBooleanModelSearch(BooleanModelSearch booleanModelSearch) {
		this.booleanModelSearch = booleanModelSearch;
	}

	/**
	 * @return the stato
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
	 * @return the targa
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
	 * @return the cartaCircolazione
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
	 * @return the polizzaAssicurativa
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
	 * @return the notId
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
	 * @return the all
	 */
	public boolean isAll() {
		return all;
	}

	/**
	 * @param all 
	 */
	public void setAll(boolean all) {
		this.all = all;
	}

	/**
	 * @return the size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * @param size
	 */
	public void setSize(int size) {
		this.size = size;
	}

	/**
	 * @return the from
	 */
	public int getFrom() {
		return from;
	}

	/**
	 * @param from
	 */
	public void setFrom(int from) {
		this.from = from;
	}

	/**
	 * 
	 * @return
	 */
	private VeicoloCNRSearchBuilder self() {
		return this;
	}
	
}
