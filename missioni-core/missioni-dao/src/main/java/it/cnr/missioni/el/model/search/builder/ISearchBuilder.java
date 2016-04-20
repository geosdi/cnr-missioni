package it.cnr.missioni.el.model.search.builder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.search.sort.SortOrder;
import org.geosdi.geoplatform.experimental.el.dao.GPPageableElasticSearchDAO;
import org.geosdi.geoplatform.experimental.el.search.bool.IBooleanSearch;

/**
 * @author Salvia Vito
 */
public interface ISearchBuilder<T> extends Serializable {

    /**
     * @return {@link T}
     */
    T build();

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
     * @return {@link String}
     */
    String getId();

    /**
     *
     * @return {@link List<IBooleanSearch>}
     */
    public List<IBooleanSearch> getListAbstractBooleanSearch();

    abstract class AbstractSearchBuilder<T> implements ISearchBuilder<T> {

        /**
         *
         */
        protected static final long serialVersionUID = -6986748535462353268L;
        protected String id;
        protected int from = 0;
        protected int size = 10;
        protected boolean all = false;
        protected SortOrder sortOrder = SortOrder.ASC;
        protected String fieldSort;
        protected GPPageableElasticSearchDAO.MultiFieldsSearch multiFieldsSearch;
        protected List<IBooleanSearch> listAbstractBooleanSearch= new ArrayList<>();

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
         * @param sortOrder
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
         * @param fieldSort
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
         * @return {@link String}
         */
        public String getId() {
            return id;
        }

        /**
         * @return {@link int}
         */
        public int getFrom() {
            return from;
        }

        /**
         * @return {@link int}
         */
        public int getSize() {
            return size;
        }

        /**
         * @return {@link boolean}
         */
        public boolean isAll() {
            return all;
        }
        
        /**
         * @return {@link SortOrder}
         */
        public SortOrder getSortOrder() {
            return sortOrder;
        }

        /**
         * @return {@link String}
         */
        public String getFieldSort() {
            return this.fieldSort;
        }

        /**
         *
         * @return {@link List<IBooleanSearch>}
         */
        public List<IBooleanSearch> getListAbstractBooleanSearch() {
            return listAbstractBooleanSearch;
        }

    }

}
