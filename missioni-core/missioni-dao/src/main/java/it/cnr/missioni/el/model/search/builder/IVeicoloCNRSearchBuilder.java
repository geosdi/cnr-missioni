package it.cnr.missioni.el.model.search.builder;

import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.geosdi.geoplatform.experimental.el.dao.GPPageableElasticSearchDAO;
import org.geosdi.geoplatform.experimental.el.search.bool.BooleanExactSearch;
import org.geosdi.geoplatform.experimental.el.search.bool.IBooleanSearch;

/**
 * @author Salvia Vito
 */
public interface IVeicoloCNRSearchBuilder extends ISearchBuilder<IVeicoloCNRSearchBuilder> {

    /**
     * @param stato
     * @return {@link IVeicoloCNRSearchBuilder}
     */
    IVeicoloCNRSearchBuilder withStato(String stato);

    /**
     * @param targa
     * @return {@link IVeicoloCNRSearchBuilder}
     */
    IVeicoloCNRSearchBuilder withTarga(String targa);

    /**
     * @param polizzaAssicurativa
     * @return {@link IVeicoloCNRSearchBuilder}
     */
    IVeicoloCNRSearchBuilder withPolizzaAssicurativa(String polizzaAssicurativa);

    /**
     * @param cartaCircolazione
     * @return {@link IVeicoloCNRSearchBuilder}
     */
    IVeicoloCNRSearchBuilder withCartaCircolazione(String cartaCircolazione);

    /**
     * @param notId
     * @return {@link IVeicoloCNRSearchBuilder}
     */
    IVeicoloCNRSearchBuilder withNotId(String notId);

    /**
     * @return {@link String}
     */
    String getStato();

    /**
     * @return {@link String}
     */
    String getTarga();

    /**
     * @return {@link String}
     */
    String getCartaCircolazione();

    /**
     * @return {@link String}
     */
    String getPolizzaAssicurativa();

    /**
     * @return {@link String}
     */
    String getNotId();

    public class VeicoloCNRSearchBuilder extends ISearchBuilder.AbstractSearchBuilder<IVeicoloCNRSearchBuilder>
            implements IVeicoloCNRSearchBuilder {

        /**
         *
         */
        private static final long serialVersionUID = 7659751658210843810L;
        private String stato = null;
        private String targa;
        private String cartaCircolazione;
        private String polizzaAssicurativa;
        private String notId;

        private VeicoloCNRSearchBuilder() {
            this.fieldSort = SearchConstants.VEICOLO_CNR_FIELD_TIPO;
        }

        public static IVeicoloCNRSearchBuilder getVeicoloCNRSearchBuilder() {
            return new VeicoloCNRSearchBuilder();
        }

        /**
         * @param stato
         * @return {@link IVeicoloCNRSearchBuilder}
         */
        public IVeicoloCNRSearchBuilder withStato(String stato) {
            this.stato = stato;
            if (stato != null && !stato.trim().equals(""))
                listAbstractBooleanSearch.add(new BooleanExactSearch(SearchConstants.VEICOLO_CNR_FIELD_STATO, stato, IBooleanSearch.BooleanQueryType.MUST, Operator.AND));
            return self();
        }

        /**
         * @param targa
         * @return {@link IVeicoloCNRSearchBuilder}
         */
        public IVeicoloCNRSearchBuilder withTarga(String targa) {
            this.targa = targa;
            if (targa != null && !targa.trim().equals(""))
                listAbstractBooleanSearch.add(new BooleanExactSearch(SearchConstants.VEICOLO_CNR_FIELD_TARGA, targa, IBooleanSearch.BooleanQueryType.MUST, Operator.AND));
            return self();
        }

        /**
         * @param polizzaAssicurativa
         * @return {@link IVeicoloCNRSearchBuilder}
         */
        public IVeicoloCNRSearchBuilder withPolizzaAssicurativa(String polizzaAssicurativa) {
            this.polizzaAssicurativa = polizzaAssicurativa;
            if (polizzaAssicurativa != null && !polizzaAssicurativa.trim().equals(""))
                listAbstractBooleanSearch.add(new BooleanExactSearch(SearchConstants.VEICOLO_CNR_FIELD_POLIZZA_ASSICURATIVA, polizzaAssicurativa, IBooleanSearch.BooleanQueryType.MUST, Operator.AND));
            return self();
        }

        /**
         * @param id
         * @return {@link IVeicoloCNRSearchBuilder}
         */
        public IVeicoloCNRSearchBuilder withId(String id) {
            this.id = id;
            if (id != null && !id.trim().equals(""))
                listAbstractBooleanSearch.add(new BooleanExactSearch(SearchConstants.VEICOLO_CNR_FIELD_ID, id, IBooleanSearch.BooleanQueryType.MUST, Operator.AND));
            return self();
        }

        /**
         * @param cartaCircolazione
         * @return {@link IVeicoloCNRSearchBuilder}
         */
        public IVeicoloCNRSearchBuilder withCartaCircolazione(String cartaCircolazione) {
            this.cartaCircolazione = cartaCircolazione;
            if (cartaCircolazione != null && !cartaCircolazione.trim().equals(""))
                listAbstractBooleanSearch.add(new BooleanExactSearch(SearchConstants.VEICOLO_CNR_FIELD_CARTA_CIRCOLAZIONE, cartaCircolazione, IBooleanSearch.BooleanQueryType.MUST, Operator.AND));
            return self();
        }

        /**
         * @param notId
         * @return {@link IVeicoloCNRSearchBuilder}
         */
        public IVeicoloCNRSearchBuilder withNotId(String notId) {
            this.notId = notId;
            if (notId != null && !notId.trim().equals(""))
                listAbstractBooleanSearch.add(new BooleanExactSearch(SearchConstants.VEICOLO_CNR_FIELD_ID, notId, IBooleanSearch.BooleanQueryType.MUST_NOT, Operator.AND));
            return self();
        }

        public IVeicoloCNRSearchBuilder build(){
            this.multiFieldsSearch = new GPPageableElasticSearchDAO.MultiFieldsSearch(this.listAbstractBooleanSearch.stream().toArray(IBooleanSearch[]::new));
            return self();
        }

        /**
         * @return {@link String}
         */
        public String getStato() {
            return stato;
        }

        /**
         * @return {@link String}
         */
        public String getTarga() {
            return targa;
        }

        /**
         * @param targa
         */
        public void setTarga(String targa) {
            this.targa = targa;
        }

        /**
         * @return {@link String}
         */
        public String getCartaCircolazione() {
            return cartaCircolazione;
        }

        /**
         * @return {@link String}
         */
        public String getPolizzaAssicurativa() {
            return polizzaAssicurativa;
        }

        /**
         * @return {@link String}
         */
        public String getNotId() {
            return notId;
        }

        /**
         * @return {@link IVeicoloCNRSearchBuilder}
         */
        protected IVeicoloCNRSearchBuilder self() {
            return this;
        }
    }

}
