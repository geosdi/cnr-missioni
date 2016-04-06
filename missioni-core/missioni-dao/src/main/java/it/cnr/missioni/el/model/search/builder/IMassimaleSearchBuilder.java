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
     * @return {@link String}
     */
    String getLivello();

    /**
     * @return {@link String}
     */
    String getAreaGeografica();

    /**
     * @return {@link String}
     */
    String getTipo();

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
            this.id = id;
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
            this.livello = livello;
            if (livello != null && !livello.trim().equals(""))
                booleanModelSearch.getListaSearch().add(new ExactSearch(SearchConstants.MASSIMALE_FIELD_LIVELLO, livello));
            return self();
        }

        /**
         * @param areaGeografica
         * @return {@link IMassimaleSearchBuilder}
         */
        public IMassimaleSearchBuilder withAreaGeografica(String areaGeografica) {
            this.areaGeografica = areaGeografica;
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
         * @return {@link String}
         */
        public String getLivello() {
            return livello;
        }

        /**
         * @return {@link String}
         */
        public String getAreaGeografica() {
            return areaGeografica;
        }

        /**
         * @return {@link String}
         */
        public String getTipo() {
            return tipo;
        }

        /**
         * @return
         */
        protected IMassimaleSearchBuilder self() {
            return this;
        }
    }

}

