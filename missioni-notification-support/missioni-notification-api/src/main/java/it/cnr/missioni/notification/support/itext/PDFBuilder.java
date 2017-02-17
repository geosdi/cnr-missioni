package it.cnr.missioni.notification.support.itext;

import java.io.File;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import com.google.common.base.Preconditions;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

import it.cnr.missioni.model.configuration.Direttore;
import it.cnr.missioni.model.configuration.UrlImage;
import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.model.user.User;
import it.cnr.missioni.model.user.Veicolo;

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
    PDFBuilder withMissioneList(List<Missione> userList);
    
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
     * @param urlImage
     * @return {@link PDFBuilder}
     */
    PDFBuilder withUrlImage(UrlImage urlImage);

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
        protected List<Missione> missioneList;
        protected OutputStream outputStream;
        private boolean isMezzoProprio;
        private UrlImage urlImage;
        protected Font fontBold_6 = FontFactory.getFont("Times-Roman", 6, Font.BOLD);
        protected Font fontNormal_6 = FontFactory.getFont("Times-Roman", 6);
        protected Font fontNormal_9 = FontFactory.getFont("Times-Roman", 9);
        protected Font fontBold_9 = new Font(Font.FontFamily.TIMES_ROMAN, 9,Font.BOLD);
        protected DateFormat formatData = new SimpleDateFormat("dd/MM/yyyy", Locale.ITALY);
        protected DateFormat formatDataTime = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.ITALY);
		
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
         * @param user
         * @return {@link PDFBuilder}
         */
        @Override
        public PDFBuilder withMissioneList(List<Missione> missioneList) {
            this.missioneList = missioneList;
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
         * @param urlImage
         * @return {@link PDFBuilder}
         */
        public PDFBuilder withUrlImage(UrlImage urlImage){
        	this.urlImage = urlImage;
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
            Preconditions.checkArgument((this.urlImage != null), "The Parameter UrlImage must not be null.");
            Preconditions.checkArgument(((this.file != null) || (this.outputStream != null)),
                    "You must define File or OutputStream Parameter.");
        }
        
		/**
         * @throws Exception
         */
        protected void checkArgumentsUserMissione() throws Exception {
            Preconditions.checkArgument((!this.missioneList.isEmpty()), "The Parameter User must not be null.");;
            Preconditions.checkArgument(((this.file != null) || (this.outputStream != null)),
                    "You must define File or OutputStream Parameter.");
        }
        
        
        protected void writeHeader(Document document) throws Exception{

    		PdfPTable tableImage = new PdfPTable(3);
    		PdfPCell cellImage1 = new PdfPCell();
    		PdfPCell cellImage2 = new PdfPCell();
    		PdfPCell cellImage3 = new PdfPCell();
    		cellImage1.setBorder(Rectangle.NO_BORDER);
    		cellImage2.setBorder(Rectangle.NO_BORDER);
    		cellImage3.setBorder(Rectangle.NO_BORDER);
    		Image logoMinistero = Image
    				.getInstance(urlImage.getLogoMinistero());
    		logoMinistero.scalePercent(30, 30);
    		logoMinistero.setAlignment(Image.ALIGN_CENTER);
    		Image logoCnr = Image.getInstance(urlImage.getLogoCnr());
    		logoCnr.setAlignment(Image.ALIGN_CENTER);
    		logoCnr.scalePercent(30, 30);
    		Image logoImaa = Image.getInstance(urlImage.getLogoImaa());
    		logoImaa.setAlignment(Image.ALIGN_CENTER);
    		logoImaa.scalePercent(15, 15);
    		cellImage1.addElement(logoCnr);
    		cellImage2.addElement(logoMinistero);
    		cellImage3.addElement(logoImaa);
    		tableImage.addCell(cellImage1);
    		tableImage.addCell(cellImage2);
    		tableImage.addCell(cellImage3);
    		document.add(tableImage);
    		Paragraph paragraphIntestazione = new Paragraph();
    		paragraphIntestazione.setAlignment(Element.ALIGN_CENTER);
    		Chunk chunkIntestazione = new Chunk("\nConsiglio Nazionale dell Ricerche\n",
    				new Font(Font.FontFamily.TIMES_ROMAN, Font.DEFAULTSIZE, Font.ITALIC));
    		paragraphIntestazione.add(chunkIntestazione);
    		document.add(paragraphIntestazione);
        }

        /**
         * @return {@link PDFBuilder}
         */
        protected PDFBuilder self() {
            return this;
        }
    }
}