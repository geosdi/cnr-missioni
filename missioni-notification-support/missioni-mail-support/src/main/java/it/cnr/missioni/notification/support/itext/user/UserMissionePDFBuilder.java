package it.cnr.missioni.notification.support.itext.user;

import java.io.FileOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import it.cnr.missioni.notification.support.itext.PDFBuilder;

/**
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
public class UserMissionePDFBuilder extends PDFBuilder.AbstractPDFBuilder {

    private static final Logger logger = LoggerFactory.getLogger(UserMissionePDFBuilder.class);

    protected UserMissionePDFBuilder() {
    }

    public static PDFBuilder newPDFBuilder() {
        return new UserMissionePDFBuilder();
    }

    @Override
    public void build() throws Exception {
		logger.debug("############################{} ::::::::::::<<<<<<<<< PDF GENERATION BEGIN" + " >>>>>>>>>>>>\n",
				getType());
        super.checkArgumentsUserMissione();
		Document document = new Document(PageSize.A4, 50, 50, 50, 50);
		PdfWriter.getInstance(document, ((this.file != null) ? new FileOutputStream(this.file) : this.outputStream));
        document.addSubject("Users in Missione");
		document.open();
		//writeHeader(document);
		
        PdfPTable table = new PdfPTable(2);
        table.addCell(new PdfPCell(new Paragraph("Cognome Nome", fontBold_9)));
        table.addCell(new PdfPCell(new Paragraph("Localita", fontBold_9)));
        this.missioneList.stream().forEach(missione->{
            table.addCell(new PdfPCell(new Paragraph(missione.getShortUser(), fontNormal_6)));
            table.addCell(new PdfPCell(new Paragraph(missione.getLocalita(), fontNormal_6)));

        });
		document.add(table);
        document.close();
    }

    @Override
    public IPDFBuilderType getType() {
        return PDFBuilderType.RIMBORSO_PDF_BUIDER;
    }

	/**
	 * @throws Exception
	 */
	@Override
	public void buildVeicolo() throws Exception {
		
	}


}
