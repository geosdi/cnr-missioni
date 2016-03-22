package it.cnr.missioni.notification.support.itext.anticipopagamento;

import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import it.cnr.missioni.model.user.Anagrafica.Genere;
import it.cnr.missioni.notification.support.itext.PDFBuilder;

/**
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
public class AnticipoPagamentoPDFBuilder extends PDFBuilder.AbstractPDFBuilder {

	private static final Logger logger = LoggerFactory.getLogger(AnticipoPagamentoPDFBuilder.class);

	protected AnticipoPagamentoPDFBuilder() {
	}

	public static PDFBuilder newPDFBuilder() {
		return new AnticipoPagamentoPDFBuilder();
	}

	@Override
	public void build() throws Exception {
		logger.debug("############################{} ::::::::::::<<<<<<<<< PDF GENERATION BEGIN" + " >>>>>>>>>>>>\n",
				getType());
		super.checkArguments();
		DateFormat formatData = new SimpleDateFormat("dd/MM/yyyy", Locale.ITALY);

		Document document = new Document(PageSize.A4, 50, 50, 50, 50);
		PdfWriter.getInstance(document, ((this.file != null) ? new FileOutputStream(this.file) : this.outputStream));
		document.addSubject("Richiesta Rimborso");
		document.open();

		PdfPTable tableImage = new PdfPTable(3);
		PdfPCell cellImage1 = new PdfPCell();
		PdfPCell cellImage2 = new PdfPCell();
		PdfPCell cellImage3 = new PdfPCell();
		cellImage1.setBorder(Rectangle.NO_BORDER);
		cellImage2.setBorder(Rectangle.NO_BORDER);
		cellImage3.setBorder(Rectangle.NO_BORDER);
		Image logoMinistero = Image
				.getInstance("http://www.missioni.imaa.cnr.it/rimborsomissioni/icons/logoMinistero.jpg");
		logoMinistero.scalePercent(30, 30);
		logoMinistero.setAlignment(Image.ALIGN_CENTER);
		Image logoCnr = Image.getInstance("http://www.missioni.imaa.cnr.it/rimborsomissioni/icons/logoCnr.jpg");
		logoCnr.setAlignment(Image.ALIGN_CENTER);
		logoCnr.scalePercent(30, 30);
		Image logoImaa = Image.getInstance("http://www.missioni.imaa.cnr.it/rimborsomissioni/icons/logoImaa.jpg");
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

		Font fontBold = new Font(Font.FontFamily.TIMES_ROMAN, 9);
		Font fontNormal = new Font(Font.FontFamily.TIMES_ROMAN, 9);
		Font fontNormal6 = new Font(Font.FontFamily.TIMES_ROMAN, 6);

		Paragraph paragraphHeader = new Paragraph(
				"\nRichiesta di anticipo missione " + missione.getId() + " del " + formatData.format(missione.getDataInserimento().toDate()) + "\n\n\n",
				fontBold);
		paragraphHeader.setAlignment(Element.ALIGN_CENTER);
		document.add(paragraphHeader);

		String articol = user.getAnagrafica().getGenere() == Genere.UOMO ? "Il" : "La";
		String suffix = user.getAnagrafica().getGenere() == Genere.UOMO ? "o" : "a";

		
		Paragraph paragraphUtente = new Paragraph(articol+" sottoscritt"+suffix+" Dr. " + this.user.getAnagrafica().getCognome()
				+ " " + user.getAnagrafica().getNome() + ", C.F. " + user.getAnagrafica().getCodiceFiscale()
				+ ", nat_ a " + user.getAnagrafica().getLuogoNascita() + "  il " + formatData.format(user.getAnagrafica().getDataNascita().toDate())
				+ ", " + " e residente a " + user.getResidenza().getComune() + " in via "
				+ user.getResidenza().getIndirizzo() + " in servizio c/o l'istituto in qualità di "
				+ user.getDatiCNR().getDescrizioneQualifica() + "\n\n");

		Paragraph paragraphChiede = new Paragraph("CHIEDE");
		paragraphChiede.setAlignment(Element.ALIGN_CENTER);
		int days = Days.daysBetween(missione.getDatiPeriodoMissione().getInizioMissione(), missione.getDatiPeriodoMissione().getFineMissione()).getDays();
		
		
		Paragraph paragraphDettagli = new Paragraph("un anticipo di missione utilizzando:\n\n"
				+ missione.getDatiMissioneEstera().getTrattamentoMissioneEsteraEnum().getStato());

		Paragraph paragraphAttivita = new Paragraph("\nper la seguente attività: " + missione.getOggetto() +

		" da svolgersi a " + missione.getLocalita() + ", nazione " + missione.getShortDescriptionNazione()
				+ " per il periodo " + formatData.format(missione.getDatiPeriodoMissione().getInizioMissione().toDate()) +

		" per la durata presunta di n." + days + " gg.\n" +

		"La missione avrà inizio alle ore " + missione.getDatiPeriodoMissione().getInizioMissione().getHourOfDay()
				+ " e terminerà presumibilmente alle ore"
				+ missione.getDatiPeriodoMissione().getFineMissione().getHourOfDay() + " (da compilarsi solo per " +

		"T.a.m.)");

		Paragraph paragraphAllegati = new Paragraph("\nSi allegano alla presente:");
		StringBuilder builder = new StringBuilder();
		if(missione.getDatiAnticipoPagamenti().isSpeseAlberghiere())
			builder.append("\nSpese alberghiere/alloggio preventivate (Trattamento di missione con rimborso documentato);");
		if(missione.getDatiAnticipoPagamenti().isSpeseViaggioDocumentato())
			builder.append("\nSpese di viaggio (Trattamento di missione con rimborso documentato);");
		if(missione.getDatiAnticipoPagamenti().isSpeseViaggioTam())
			builder.append("\nSpese di viaggio (Trattamento alternativo di missione);");
		if(missione.getDatiAnticipoPagamenti().isProspetto())
			builder.append("\nProspetto calcolo anticipo (Trattamento alternativo di missione);");
		Paragraph paragraphElencoAllegati = new Paragraph("\n"+builder.toString());
		

		Paragraph paragraphData = new Paragraph("\n\nTito Scalo," + formatData.format(new DateTime().toDate()) + "\n");
		paragraphData.setAlignment(Element.ALIGN_LEFT);

		Paragraph paragraphInFede = new Paragraph("\n\nIn fede\n\n___________________\n");
		paragraphData.setAlignment(Element.ALIGN_RIGHT);

		String importo = missione.getDatiAnticipoPagamenti().getSpeseMissioniAnticipate() > 0 ? Double.toString(missione.getDatiAnticipoPagamenti().getSpeseMissioniAnticipate()) : "____";
		
		Paragraph paragraphFooter = new Paragraph(
				"\n\nVISTO SI AUTORIZZA LA LIQUIDAZIONE PARI AD € "+importo+"\n\nIl Direttore CNR/IMAA\n\n_____________________\n("+direttore.getValue()+")");

		document.add(paragraphUtente);
		document.add(paragraphChiede);
		document.add(paragraphDettagli);
		document.add(paragraphAttivita);
		document.add(paragraphAllegati);
		document.add(paragraphElencoAllegati);
		document.add(paragraphInFede);
		document.add(paragraphFooter);

		document.close();
	}

	@Override
	public IPDFBuilderType getType() {
		return PDFBuilderType.ANTICIPO_PAGAMENTI_PDF_BUIDER;
	}

	/**
	 * @throws Exception
	 */
	@Override
	public void buildVeicolo() throws Exception {
		// TODO Auto-generated method stub

	}

}
