package it.cnr.missioni.el.model.search.builder;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.search.sort.SortOrder;

import it.cnr.missioni.el.model.search.BooleanModelSearch;
import it.cnr.missioni.el.model.search.EnumBooleanType;
import it.cnr.missioni.el.model.search.ExactSearch;

/**
 * @author Salvia Vito
 */

public interface IMassimaleSearchBuilder extends ISearchBuilder<IMassimaleSearchBuilder> {

    class MassimaleSearchBuilder extends ISearchBuilder.AbstractSearchBuilder<IMassimaleSearchBuilder>
            implements IMassimaleSearchBuilder {

        /**
         *
         */
        private static final long serialVersionUID = 3676936353865160224L;
        private BooleanModelSearch booleanModelSearch;
        private String value = null;
        private String id;
        private String notId;
        private String livello;
        private String areaGeografica;
        private String tipo;
        private int size = 10;
        private int from = 0;
        private String fieldSort = SearchConstants.MASSIMALE_FIELD_AREA_GEOGRAFICA;
        private SortOrder sortOrder = SortOrder.ASC;


        private MassimaleSearchBuilder() {
        }

        public static IMassimaleSearchBuilder getMassimaleSearchBuilder() {
            return new IMassimaleSearchBuilder.MassimaleSearchBuilder();
        }

        public IMassimaleSearchBuilder withId(String id) {
            this.setId(id);
            if (id != null && !id.trim().equals(""))
                booleanModelSearch.getListaSearch().add(new ExactSearch(SearchConstants.MASSIMALE_FIELD_ID, id));
            return self();
        }

        public IMassimaleSearchBuilder withNotId(String notId) {
            this.notId = notId;
            if (notId != null && !notId.trim().equals(""))
                booleanModelSearch.getListaSearch().add(new ExactSearch(SearchConstants.MASSIMALE_FIELD_ID, notId, EnumBooleanType.MUST_NOT));
            return self();
        }

        public IMassimaleSearchBuilder withLivello(String livello) {
            this.setLivello(livello);
            if (livello != null && !livello.trim().equals(""))
                booleanModelSearch.getListaSearch().add(new ExactSearch(SearchConstants.MASSIMALE_FIELD_LIVELLO, livello));
            return self();
        }

        public IMassimaleSearchBuilder withAreaGeografica(String areaGeografica) {
            this.setAreaGeografica(areaGeografica);
            if (areaGeografica != null && !areaGeografica.trim().equals(""))
                booleanModelSearch.getListaSearch()
                        .add(new ExactSearch(SearchConstants.MASSIMALE_FIELD_AREA_GEOGRAFICA, areaGeografica));
            return self();
        }

        public IMassimaleSearchBuilder withTipo(String tipo) {
            this.tipo = tipo;
            if (tipo != null && !tipo.trim().equals(""))
                booleanModelSearch.getListaSearch()
                        .add(new ExactSearch(SearchConstants.MASSIMALE_FIELD_TIPO, tipo));
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

        public IMassimaleSearchBuilder withSize(int size) {
            this.size = size;
            return self();
        }

        public IMassimaleSearchBuilder withFrom(int from) {
            this.from = from;
            return self();
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
         * @return the livello
         */
        public String getLivello() {
            return livello;
        }

        /**
         * @param livello
         */
        public void setLivello(String livello) {
            this.livello = livello;
        }

        /**
         * @return the areaGeografica
         */
        public String getAreaGeografica() {
            return areaGeografica;
        }

        /**
         * @param areaGeografica
         */
        public void setAreaGeografica(String areaGeografica) {
            this.areaGeografica = areaGeografica;
        }

        /**
         * @return the tipo
         */
        public String getTipo() {
            return tipo;
        }

        /**
         * @param tipo
         */
        public void setTipo(String tipo) {
            this.tipo = tipo;
        }


        /**
         * @return {@link String}
         */
        @Override
        public String getFieldSort() {
            return null;
        }

        /**
         * @param fieldSort
         */
        @Override
        public void setFieldSort(String fieldSort) {

        }

        /**
         * @return
         */
        protected IMassimaleSearchBuilder self() {
            return this;
        }
    }

}

