package it.cnr.missioni.el.model.search.builder;

import it.cnr.missioni.el.model.search.EnumBooleanType;
import it.cnr.missioni.el.model.search.ExactSearch;

/**
 * @author Salvia Vito
 */

public interface IMassimaleSearchBuilder extends ISearchBuilder<IMassimaleSearchBuilder> {

    /**
     * @param id
     * @return {@link IMassimaleSearchBuilder}
     */
    IMassimaleSearchBuilder withId(String id);

    /**
     * @param notId
     * @return {@link IMassimaleSearchBuilder}
     */
    IMassimaleSearchBuilder withNotId(String notId);

    /**
     * @param livello
     * @return {@link IMassimaleSearchBuilder}
     */
    IMassimaleSearchBuilder withLivello(String livello);

    /**
     * @param areaGeografica
     * @return {@link IMassimaleSearchBuilder}
     */
    IMassimaleSearchBuilder withAreaGeografica(String areaGeografica);

    /**
     * @param tipo
     * @return {@link IMassimaleSearchBuilder}
     */
    IMassimaleSearchBuilder withTipo(String tipo);

    /**
     * @return {@link String}
     */
    String getNotId();

    /**
     * @param notId
     */
    void setNotId(String notId);

    /**
     * @return {@link String}
     */
    String getLivello();

    /**
     * @param livello
     */
    void setLivello(String livello);

    /**
     * @return {@link String}
     */
    String getAreaGeografica();

    /**
     * @param areaGeografica
     */
    void setAreaGeografica(String areaGeografica);

    /**
     * @return {@link String}
     */
    String getTipo();

    /**
     * @param tipo
     */
    void setTipo(String tipo);

    class MassimaleSearchBuilder extends ISearchBuilder.AbstractSearchBuilder<IMassimaleSearchBuilder>
            implements IMassimaleSearchBuilder {

        /**
         *
         */
        private static final long serialVersionUID = 3676936353865160224L;
        private String notId;
        private String livello;
        private String areaGeografica;
        private String tipo;

        private MassimaleSearchBuilder() {
            this.fieldSort = SearchConstants.MASSIMALE_FIELD_AREA_GEOGRAFICA;
        }

        public static IMassimaleSearchBuilder getMassimaleSearchBuilder() {
            return new IMassimaleSearchBuilder.MassimaleSearchBuilder();
        }

        /**
         * @param id
         * @return {@link IMassimaleSearchBuilder}
         */
        public IMassimaleSearchBuilder withId(String id) {
            this.setId(id);
            if (id != null && !id.trim().equals(""))
                booleanModelSearch.getListaSearch().add(new ExactSearch(SearchConstants.MASSIMALE_FIELD_ID, id));
            return self();
        }

        /**
         * @param notId
         * @return {@link IMassimaleSearchBuilder}
         */
        public IMassimaleSearchBuilder withNotId(String notId) {
            this.notId = notId;
            if (notId != null && !notId.trim().equals(""))
                booleanModelSearch.getListaSearch().add(new ExactSearch(SearchConstants.MASSIMALE_FIELD_ID, notId, EnumBooleanType.MUST_NOT));
            return self();
        }

        /**
         * @param livello
         * @return {@link IMassimaleSearchBuilder}
         */
        public IMassimaleSearchBuilder withLivello(String livello) {
            this.setLivello(livello);
            if (livello != null && !livello.trim().equals(""))
                booleanModelSearch.getListaSearch().add(new ExactSearch(SearchConstants.MASSIMALE_FIELD_LIVELLO, livello));
            return self();
        }

        /**
         * @param areaGeografica
         * @return {@link IMassimaleSearchBuilder}
         */
        public IMassimaleSearchBuilder withAreaGeografica(String areaGeografica) {
            this.setAreaGeografica(areaGeografica);
            if (areaGeografica != null && !areaGeografica.trim().equals(""))
                booleanModelSearch.getListaSearch()
                        .add(new ExactSearch(SearchConstants.MASSIMALE_FIELD_AREA_GEOGRAFICA, areaGeografica));
            return self();
        }

        /**
         * @param tipo
         * @return {@link IMassimaleSearchBuilder}
         */
        public IMassimaleSearchBuilder withTipo(String tipo) {
            this.tipo = tipo;
            if (tipo != null && !tipo.trim().equals(""))
                booleanModelSearch.getListaSearch()
                        .add(new ExactSearch(SearchConstants.MASSIMALE_FIELD_TIPO, tipo));
            return self();
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
         * @return {@link String}
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
         * @return {@link String}
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
         * @return {@link String}
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
         * @return
         */
        protected IMassimaleSearchBuilder self() {
            return this;
        }
    }

}

