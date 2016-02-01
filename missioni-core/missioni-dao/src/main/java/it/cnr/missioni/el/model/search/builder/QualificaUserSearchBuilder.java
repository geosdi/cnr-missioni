package it.cnr.missioni.el.model.search.builder;

import java.io.Serializable;

import org.elasticsearch.index.query.BoolQueryBuilder;

import it.cnr.missioni.el.model.search.BooleanModelSearch;
import it.cnr.missioni.el.model.search.EnumBooleanType;
import it.cnr.missioni.el.model.search.ExactSearch;

/**
 * @author Salvia Vito
 */
public class QualificaUserSearchBuilder implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 8819699628235873367L;
	/**
	 * 
	 */
	private BooleanModelSearch booleanModelSearch;
	private String value = null;
	private String id;
	private int size = 10;
	private int from = 0;

	private QualificaUserSearchBuilder() {
		booleanModelSearch = new BooleanModelSearch();
	}

	public static QualificaUserSearchBuilder getQualificaUserSearchBuilder() {
		return new QualificaUserSearchBuilder();
	}

	public QualificaUserSearchBuilder withValue(String value) {
		this.setValue(value);
		if (value != null && !value.trim().equals(""))
			booleanModelSearch.getListaSearch().add(new ExactSearch(SearchConstants.QUALIFICA_USER_FIELD_VALUE, value));
		return self();
	}

	public QualificaUserSearchBuilder withId(String id) {
		this.setId(id);
		if (id != null && !id.trim().equals(""))
			booleanModelSearch.getListaSearch()
					.add(new ExactSearch(SearchConstants.QUALIFICA_USER_FIELD_ID, id, EnumBooleanType.MUST_NOT));
		return self();
	}

	/**
	 * @return the value
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

	public QualificaUserSearchBuilder withSize(int size) {
		this.size = size;
		return self();
	}

	public QualificaUserSearchBuilder withFrom(int from) {
		this.from = from;
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
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
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
	private QualificaUserSearchBuilder self() {
		return this;
	}

}
