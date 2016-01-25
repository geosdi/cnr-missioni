package it.cnr.missioni.el.model.search.builder;

import java.io.Serializable;

import org.elasticsearch.index.query.BoolQueryBuilder;

import it.cnr.missioni.el.model.search.BooleanModelSearch;
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
		if(stato != null && !stato.trim().equals(""))
			booleanModelSearch.getListaSearch().add(new ExactSearch(SearchConstants.VEICOLO_CNR_FIELD_STATO, stato));
		return this;
	}

	public VeicoloCNRSearchBuilder withSize(int size) {
		this.size = size;
		return this;
	}

	public VeicoloCNRSearchBuilder withFrom(int from) {
		this.from = from;
		return this;
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

}
