package it.cnr.missioni.el.model.search;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Salvia Vito
 */
public class BooleanModelSearch implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -5617290380156544514L;
    private BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
    private List<IBooleanSearch> listaSearch = new ArrayList<IBooleanSearch>();

    public BoolQueryBuilder buildQuery() {

        listaSearch.forEach(booleanSearch -> {
            try {

                addBooleanQuery(booleanSearch);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });

        return queryBuilder;
    }

    public void addBooleanQuery(IBooleanSearch booleanSearch) throws Exception {
        switch (booleanSearch.getType().name()) {
            case "SHOULD":
                queryBuilder.should(booleanSearch.getBooleanQuery());
                break;
            case "MUST":
                queryBuilder.must(booleanSearch.getBooleanQuery());
                break;
            case "MUST_NOT":
                queryBuilder.mustNot(booleanSearch.getBooleanQuery());
                break;
        }

    }


    /**
     * @return the queryBuilder
     */
    public BoolQueryBuilder getQueryBuilder() {
        return queryBuilder;
    }

    /**
     * @param queryBuilder
     */
    public void setQueryBuilder(BoolQueryBuilder queryBuilder) {
        this.queryBuilder = queryBuilder;
    }

    /**
     * @return the listaSearch
     */
    public List<IBooleanSearch> getListaSearch() {
        return listaSearch;
    }

    /**
     * @param listaSearch
     */
    public void setListaSearch(List<IBooleanSearch> listaSearch) {
        this.listaSearch = listaSearch;
    }


}
