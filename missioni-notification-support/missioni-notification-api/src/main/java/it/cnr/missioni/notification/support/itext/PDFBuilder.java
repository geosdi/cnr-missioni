package it.cnr.missioni.notification.support.itext;

import com.google.common.base.Preconditions;

import it.cnr.missioni.model.configuration.Direttore;
import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.model.user.User;
import it.cnr.missioni.model.user.Veicolo;

import java.io.File;
import java.io.OutputStream;

/**
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
public interface PDFBuilder {

    /**
     * @param user
     * @return {@link PDFBuilder}
     */
    PDFBuilder withUser(User user);

    /**
     * @param missione
     * @return {@link PDFBuilder}
     */
    PDFBuilder withMissione(Missione missione);
    
    /**
     * @param veicolo
     * @return {@link PDFBuilder}
     */
    PDFBuilder withVeicolo(Veicolo veicolo);
    /**
     * @param file
     * @return {@link PDFBuilder}
     */
    PDFBuilder withFile(File file);
    
    /**
     * @param file
     * @return {@link PDFBuilder}
     */
    PDFBuilder withFileVeicolo(File fileVeicolo);

    /**
     * @param outputStream
     * @return {@link PDFBuilder}
     */
    PDFBuilder withOutputStream(OutputStream outputStream);

    /**
     * @return {@link IPDFBuilderType}
     */
    IPDFBuilderType getType();
    
    /**
     * @param direttore
     * @return {@link PDFBuilder}
     */
    PDFBuilder withDirettore(Direttore direttore);

    /**
     * @throws Exception
     */
    void build() throws Exception;
    
    /**
     * @throws Exception
     */
    void buildVeicolo() throws Exception;
    
    /**
     * 
     * @return
     */
    boolean isMezzoProprio();
    
    /**
     * 
     * @param isMezzoProprio
     */    
    public void setMezzoProprio(boolean isMezzoProprio) ;

    /**
     *
     */
    interface IPDFBuilderType {

        /**
         * @return {@link String}
         */
        String getType();
    }

    /**
     *
     */
    enum PDFBuilderType implements IPDFBuilderType {
        MISSIONE_PDF_BUILDER("MISSIONE_PDF_BUILDER"),
        RIMBORSO_PDF_BUIDER("RIMBORSO_PDF_BUIDER"),
        ANTICIPO_PAGAMENTI_PDF_BUIDER("ANTICIPO_PAGAMENTI_PDF_BUIDER");

        private final String value;

        PDFBuilderType(String value) {
            this.value = value;
        }


        /**
         * @return {@link String}
         */
        @Override
        public String getType() {
            return this.value;
        }

        @Override
        public String toString() {
            return this.value;
        }
    }

    /**
     *
     */
    abstract class AbstractPDFBuilder implements PDFBuilder {

        protected User user;
        protected Direttore direttore;
        protected Missione missione;
        protected Veicolo veicolo;
        protected File file;
        protected File fileVeicolo;
        protected OutputStream outputStream;
        private boolean isMezzoProprio;

        protected AbstractPDFBuilder() {
        }

        /**
         * @param user
         * @return {@link PDFBuilder}
         */
        @Override
        public PDFBuilder withUser(User user) {
            this.user = user;
            return self();
        }
        
        /**
         * @param direttore
         * @return {@link PDFBuilder}
         */
        @Override
        public PDFBuilder withDirettore(Direttore direttore) {
            this.direttore = direttore;
            return self();
        }

        /**
         * @param missione
         * @return {@link PDFBuilder}
         */
        @Override
        public PDFBuilder withMissione(Missione missione) {
            this.missione = missione;
            return self();
        }
        
        /**
         * @param veicolo
         * @return {@link PDFBuilder}
         */
        @Override
        public PDFBuilder withVeicolo(Veicolo veicolo) {
            this.veicolo = veicolo;
            return self();
        }


        /**
         * @param file
         * @return {@link File}
         */
        @Override
        public PDFBuilder withFile(File file) {
            this.file = file;
            return self();
        }
        
        /**
         * @param fileVeicolo
         * @return {@link File}
         */
        @Override
        public PDFBuilder withFileVeicolo(File fileVeicolo) {
            this.fileVeicolo = fileVeicolo;
            return self();
        }

        /**
         * @param outputStream
         * @return {@link PDFBuilder}
         */
        @Override
        public PDFBuilder withOutputStream(OutputStream outputStream) {
            this.outputStream = outputStream;
            return self();
        }

        /**
		 * @return the isMezzoProprio
		 */
		public boolean isMezzoProprio() {
			return isMezzoProprio;
		}

		/**
		 * @param isMezzoProprio 
		 */
		public void setMezzoProprio(boolean isMezzoProprio) {
			this.isMezzoProprio = isMezzoProprio;
		}

		/**
         * @throws Exception
         */
        protected void checkArguments() throws Exception {
            Preconditions.checkArgument((this.user != null), "The Parameter User must not be null.");
            Preconditions.checkArgument((this.missione != null), "The Parameter Missione must not be null.");
            Preconditions.checkArgument(((this.file != null) || (this.outputStream != null)),
                    "You must define File or OutputStream Parameter.");
        }

        /**
         * @return {@link PDFBuilder}
         */
        protected PDFBuilder self() {
            return this;
        }
    }
}