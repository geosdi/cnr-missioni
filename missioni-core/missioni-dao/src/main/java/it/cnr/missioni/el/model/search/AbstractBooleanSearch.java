package it.cnr.missioni.el.model.search;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.geosdi.geoplatform.experimental.el.dao.GPElasticSearchDAO.Page;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Salvia Vito
 */
public abstract class AbstractBooleanSearch<S> {
	
	
	protected String field;
	protected S value;

	//DEFAULT
	protected EnumBooleanType type = EnumBooleanType.MUST;
    protected static final Logger logger = LoggerFactory.getLogger(Page.class);

	
	public AbstractBooleanSearch(){

	}
	
	public AbstractBooleanSearch(String field,S value){
		this.field = field;
		this.value = value;
	}
	
	public AbstractBooleanSearch(String field){
		this.field = field;

	}
	
	public abstract  QueryBuilder getBooleanQuery() throws Exception;
	
	public  void addBooleanQuery(BoolQueryBuilder queryBuilder ) throws Exception {
		switch (type.name()) {
		case "SHOUD":
			queryBuilder.should(getBooleanQuery());
			break;
		case "MUST":
			queryBuilder.must(getBooleanQuery());
			break;
		case "MUST_NOT":
			queryBuilder.mustNot(getBooleanQuery());
			break;
		}

	}

	/**
	 * @return the field
	 */
	public String getField() {
		return field;
	}

	/**
	 * @param field 
	 */
	public void setField(String field) {
		this.field = field;
	}

	/**
	 * @return the value
	 */
	public S getValue() {
		return value;
	}

	/**
	 * @param value 
	 */
	public void setValue(S value) {
		this.value = value;
	}

	/**
	 * @return the type
	 */
	public EnumBooleanType getType() {
		return type;
	}

	/**
	 * @param type 
	 */
	public void setType(EnumBooleanType type) {
		this.type = type;
	}
	
    @Override
    public String toString() {
        return getClass().getSimpleName() + " {"
                + " field = " + field
                + ", value = " + value + '}';
    }
	

}
