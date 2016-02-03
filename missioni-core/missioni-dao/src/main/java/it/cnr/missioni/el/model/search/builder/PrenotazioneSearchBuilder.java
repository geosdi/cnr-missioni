package it.cnr.missioni.el.model.search.builder;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.joda.time.DateTime;

import it.cnr.missioni.el.model.search.BooleanModelSearch;
import it.cnr.missioni.el.model.search.DateRangeSearch;

/**
 * @author Salvia Vito
 */
public class PrenotazioneSearchBuilder implements ISearchBuilder {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3425236912503251640L;
	/**
	 * 
	 */

	private BooleanModelSearch booleanModelSearch;
	private DateTime dataFrom = null;
	private DateTime dataTo = null;
	

	private PrenotazioneSearchBuilder() {
		booleanModelSearch = new BooleanModelSearch();
	}

	public static PrenotazioneSearchBuilder getPrenotazioneSearchBuilder() {
		return new PrenotazioneSearchBuilder();
	}

	public PrenotazioneSearchBuilder withRangeData(DateTime dataFrom, DateTime dataTo) {

		this.dataFrom = dataFrom;
		this.dataTo = dataTo;
			booleanModelSearch.getListaSearch().add(new DateRangeSearch(SearchConstants.PRENOTAZIONE_FIELD_DATA_FROM,
					dataFrom, dataTo));
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
	private PrenotazioneSearchBuilder self() {
		return this;
	}

}
