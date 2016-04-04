package it.cnr.missioni.el.model.search.builder;

import it.cnr.missioni.el.model.search.BooleanModelSearch;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.search.sort.SortOrder;

import java.io.Serializable;

/**
 * @author Salvia Vito
 */
public interface ISearchBuilder<T> extends Serializable {

    /**
     * @return {@link BoolQueryBuilder}
     */
    BoolQueryBuilder buildQuery();

    /**
     * @param from
     * @return {@link T}
     */
    T withFrom(int from);

    /**
     * @param size
     * @return {@link T}
     */
    T withSize(int size);

    /**
     * @param id
     * @return {@link T}
     */
    T withId(String id);
    
    /**
     * @param fieldSort
     * @return {@link T}
     */
    T withFieldSort(String fieldSort);
    
    /**
     * @param sortOrder
     * @return {@link T}
     */
    T withSortOrder(SortOrder sortOrder);
    
    /**
     * @param all
     * @return {@link T}
     */
    T withAll(boolean all);

    /**
     * @return {@link int}
     */
    int getFrom();

    /**
     * @return {@link int}
     */
    int getSize();

    /**
     * @return {@link boolean}
     */
    boolean isAll();

    /**
     * @return {@link SortOrder}
     */
    SortOrder getSortOrder();

    /**
     * @return {@link String}
     */
    String getFieldSort();

    /**
     * @param fieldSort
     */
    void setFieldSort(String fieldSort);

    /**
     * @return {@link String}
     */
    String getId();

    /**
     * @param id
     */
    void setId(String id);

    abstract class AbstractSearchBuilder<T> implements ISearchBuilder<T> {

        /**
         *
         */
        protected static final long serialVersionUID = -6986748535462353268L;
        protected String id;
        protected int from = 0;
        protected int size = 10;
        protected BooleanModelSearch booleanModelSearch = new BooleanModelSearch();
        protected boolean all = false;
        protected SortOrder sortOrder = SortOrder.ASC;
        protected String fieldSort;


        protected AbstractSearchBuilder() {
        }

        protected abstract T self();

        public abstract T withId(String id);

        /**
         * @param size
         * @return {@link T}
         */
        public T withSize(int size) {
            this.size = size;
            return self();
        }
        
        /**
         * @param size
         * @return {@link T}
         */
        public T withSortOrder(SortOrder sortOrder) {
            this.sortOrder = sortOrder;
            return self();
        }
        
        /**
         * @param all
         * @return {@link T}
         */
        public T withAll(boolean all) {
            this.all = all;
            return self();
        }
        
        /**
         * @param size
         * @return {@link T}
         */
        public T withFieldSort(String fieldSort) {
            this.fieldSort = fieldSort;
            return self();
        }

        /**
         * @return
         */
        public T withFrom(int from) {
            this.from = from;
            return self();
        }

        /**
         * @return {@link BoolQueryBuilder}
         */
        public BoolQueryBuilder buildQuery() {
            return booleanModelSearch.buildQuery();
        }

        /**
         * @return {@link String}
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
         * @return {@link int}
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
         * @return {@link int}
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
         * @return {@link boolean}
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
         * @return {@link BooleanModelSearch}
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
         * @return {@link SortOrder}
         */
        public SortOrder getSortOrder() {
            return sortOrder;
        }

        /**
         * @param sortOrder
         */
        public void setSortOrder(SortOrder sortOrder) {
            this.sortOrder = sortOrder;
        }
        
        /**
         * @return {@link String}
         */
        public String getFieldSort(){
        	return this.fieldSort;
        }

        /**
         * @param fieldSort
         */
        public void setFieldSort(String fieldSort){
        	this.fieldSort = fieldSort;
        }
        
    }

}
