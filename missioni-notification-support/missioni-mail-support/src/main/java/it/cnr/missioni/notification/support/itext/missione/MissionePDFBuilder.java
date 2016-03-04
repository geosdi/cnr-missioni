package it.cnr.missioni.notification.support.itext.missione;

import com.google.common.base.Preconditions;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import it.cnr.missioni.notification.support.itext.PDFBuilder;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;

/**
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
public class MissionePDFBuilder extends PDFBuilder.AbstractPDFBuilder {

	private static final Logger logger = LoggerFactory.getLogger(MissionePDFBuilder.class);

//	private Font fontBold = new Font(Font.FontFamily.TIMES_ROMAN, 9);
//	private Font fontNormal = new Font(Font.FontFamily.TIMES_ROMAN, 9);
//	private Font fontNormal6 = new Font(Font.FontFamily.TIMES_ROMAN, 6);
	private Font fontBold6 = FontFactory.getFont("Times-Roman", 6, Font.BOLD);
	private Font fontNormal6 = FontFactory.getFont("Times-Roman", 6);
	private Font fontNormal = FontFactory.getFont("Times-Roman", 9);
	private Font fontBold = FontFactory.getFont("Times-Roman", 9, Font.BOLD);

	protected MissionePDFBuilder() {
	}

	public static PDFBuilder newPDFBuilder() {
		return new MissionePDFBuilder();
	}

	/**
	 * @throws Exception
	 */
	@Override
	public void build() throws Exception {
		logger.debug("############################{} ::::::::::::<<<<<<<<< PDF GENERATION BEGIN" + " >>>>>>>>>>>>\n",
				getType());
		super.checkArguments();
		Document document = new Document(PageSize.A4, 50, 50, 50, 50);

		String dataInserimento = missione.getDataInserimento().toString();
		String dataNascita = user.getAnagrafica().getDataNascita().toString();
		String dataInizio = missione.getDatiPeriodoMissione().getInizioMissione().toString();
		String dataFine = missione.getDatiPeriodoMissione().getFineMissione().toString();

		PdfWriter.getInstance(document, ((this.file != null) ? new FileOutputStream(this.file) : this.outputStream));
		document.addSubject("Richiesta Missione");
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

		Paragraph paragraphOrdine = new Paragraph();
		paragraphOrdine.setAlignment(Element.ALIGN_CENTER);
		paragraphOrdine.add(new Chunk("\nORDINE DI MISSIONE\n", fontBold));
		document.add(paragraphOrdine);

		Paragraph paragraphOrdine2 = new Paragraph(
				"Ordine di missione N." + missione.getId() + " " + " del " + dataInserimento + "\n\n\n", fontBold);
		paragraphOrdine2.setAlignment(Element.ALIGN_CENTER);
		document.add(paragraphOrdine2);

		PdfPTable tableUtente = new PdfPTable(2);
		PdfPTable nestedTableUtente = new PdfPTable(2);
		PdfPCell cellQualifica = new PdfPCell(new Paragraph(user.getDatiCNR().getDescrizioneQualifica(), fontNormal));
		cellQualifica.setBorder(Rectangle.NO_BORDER);
		nestedTableUtente.addCell(cellQualifica);
		PdfPCell cellLivello = new PdfPCell(new Paragraph(user.getDatiCNR().getLivello().toString(), fontNormal));
		cellLivello.setBorder(Rectangle.LEFT);
		nestedTableUtente.addCell(cellLivello);
		tableUtente.addCell(
				new Paragraph(user.getAnagrafica().getCognome() + " " + user.getAnagrafica().getNome(), fontNormal));
		tableUtente.addCell(nestedTableUtente);

		PdfPCell cellCognomeNomeLabel = new PdfPCell(new Paragraph("cognome e nome", fontNormal6));
		PdfPCell cellQualificaLabel = new PdfPCell(new Paragraph("qualifica", fontNormal6));
		PdfPCell cellCodiceLabel = new PdfPCell(new Paragraph("livello", fontNormal6));
		cellCognomeNomeLabel.setBorder(Rectangle.NO_BORDER);
		cellQualificaLabel.setBorder(Rectangle.NO_BORDER);
		cellCodiceLabel.setBorder(Rectangle.NO_BORDER);

		PdfPCell c = new PdfPCell();
		c.setBorder(Rectangle.NO_BORDER);

		PdfPTable nestedTable2 = new PdfPTable(2);

		c.addElement(nestedTable2);

		nestedTable2.addCell(cellQualificaLabel);
		nestedTable2.addCell(cellCodiceLabel);
		tableUtente.addCell(cellCognomeNomeLabel);
		tableUtente.addCell(c);

		//
		tableUtente.addCell(new Paragraph(dataNascita + " " + user.getAnagrafica().getLuogoNascita(), fontNormal));
		tableUtente.addCell(new Paragraph(user.getDatiCNR().getDescrizioneQualifica(), fontNormal));

		//
		PdfPCell cellDataNascita = new PdfPCell(new Paragraph("Data e luogo di nascita", fontNormal6));
		cellDataNascita.setBorder(Rectangle.NO_BORDER);
		PdfPCell cellImpiego = new PdfPCell(new Paragraph("Posizione nei confronti del CNR:qualifica", fontNormal6));
		cellImpiego.setBorder(Rectangle.NO_BORDER);
		tableUtente.addCell(cellDataNascita);
		tableUtente.addCell(cellImpiego);

		//
		tableUtente.addCell(new Paragraph(user.getDatiCNR().getCodiceTerzo(), fontNormal));
		tableUtente.addCell(new Paragraph(user.getDatiCNR().getMatricola() + "", fontNormal));

		//
		PdfPCell cellPosizioneIndividuale = new PdfPCell(new Paragraph("Codice Terzo", fontNormal6));
		cellPosizioneIndividuale.setBorder(Rectangle.NO_BORDER);
		PdfPCell cellMatricola = new PdfPCell(new Paragraph("Matricola del CNR", fontNormal6));
		cellMatricola.setBorder(Rectangle.NO_BORDER);
		tableUtente.addCell(cellPosizioneIndividuale);
		tableUtente.addCell(cellMatricola);
		document.add(tableUtente);

		document.add(new Paragraph("\nSi dispone l" + new Character('\u2032')
				+ " espletamento da parte della S.V. della seguente missione\n\n", fontBold));

		Chunk chunkOggetto = new Chunk("Oggetto:", fontBold);
		Chunk chunkOggetto2 = new Chunk(missione.getOggetto(), fontNormal);
		Paragraph paragraphOggetto = new Paragraph();
		paragraphOggetto.add(chunkOggetto);
		paragraphOggetto.add(chunkOggetto2);
		document.add(paragraphOggetto);

		document.add(new Paragraph("\n"));

		//
		PdfPTable tableLocalita = new PdfPTable(2);
		PdfPCell cellLocalita = new PdfPCell(new Paragraph("Localit" + new Character('\u00E0'), fontBold));
		cellLocalita.setBorder(Rectangle.NO_BORDER);
		tableLocalita.addCell(cellLocalita);
		PdfPCell cellLocalita2 = new PdfPCell(new Paragraph(missione.getLocalita(), fontNormal));
		// cellOggetto2.setNoWrap(false);
		tableLocalita.addCell(cellLocalita2);
		PdfPCell cellDistanza = new PdfPCell(new Paragraph("Distanza dalla sede di servizio km:", fontBold));
		cellDistanza.setBorder(Rectangle.NO_BORDER);
		tableLocalita.addCell(cellDistanza);
		PdfPCell cellDistanza2 = new PdfPCell(new Paragraph("" + missione.getDistanza(), fontNormal));
		// cellOggetto2.setNoWrap(false);
		tableLocalita.addCell(cellDistanza2);

		PdfPCell cellDisposizioni = new PdfPCell(new Paragraph("Altre disposizioni:", fontBold));
		cellDisposizioni.setBorder(Rectangle.NO_BORDER);
		tableLocalita.addCell(cellDisposizioni);

		String tipoVeicolo = missione.isMezzoProprio() ? "Veicolo Proprio" : "Veicolo CNR";

		PdfPCell cellDisposizioni2 = new PdfPCell(new Paragraph(tipoVeicolo + " - "
				+ (missione.getMotivazioniMezzoProprio() != null ? missione.getMotivazioniMezzoProprio() : "" ),
				fontNormal));
		// cellOggetto2.setNoWrap(false);
		tableLocalita.addCell(cellDisposizioni2);
		document.add(tableLocalita);

		Paragraph paragraphObbligo = new Paragraph();
		document.add(paragraphObbligo);

		Chunk chunkDurata = (new Chunk("Durata:", fontBold));
		Chunk chunkDataInizio = (new Chunk(" Data Inizio:", fontBold));
		Chunk chunkDataFine = (new Chunk(" Data Fine:", fontBold));

		String caption;

		Days days = Days.daysBetween(missione.getDatiPeriodoMissione().getInizioMissione(),
				missione.getDatiPeriodoMissione().getFineMissione());

		if (days.getDays() > 0)
			caption = days.getDays() + " gg";
		else
			caption = Hours.hoursBetween(missione.getDatiPeriodoMissione().getInizioMissione(),
					missione.getDatiPeriodoMissione().getFineMissione()) + " HH";

		Chunk chunkDurata2 = new Chunk(caption, fontNormal);
		Chunk chunkData1 = (new Chunk(dataInizio, fontNormal));
		Chunk chunkData2 = (new Chunk(dataFine, fontNormal));
		Paragraph paragraphData = new Paragraph();
		paragraphData.add(chunkDurata);
		paragraphData.add(chunkDurata2);
		paragraphData.add("\t");
		paragraphData.add(chunkDataInizio);
		paragraphData.add(chunkData1);
		paragraphData.add("\t");
		paragraphData.add(chunkDataFine);
		paragraphData.add(chunkData2);
		paragraphData.add("\t");
		document.add(paragraphData);

		Chunk chunkUnderline = new Chunk("Le spese non documentate non possono venire rimborsate."
				+ "Per spese effettuate in valuta, ove non sia allegata distinta bancaria di cambio, " + "il rimborso "
				+ new Character('\u00E8') + " disposto al cambio ufficiale UIC " + "o cambio di parit"
				+ new Character('\u00E0') + " vigente per la valuta all" + new Character('\u2032')
				+ " epoca in cui le spese vennero sostenute.");
		chunkUnderline.setUnderline(0.2f, -2f);
		Paragraph paragraphUnderline = new Paragraph("\n", fontBold);
		paragraphUnderline.add(chunkUnderline);
		document.add(paragraphUnderline);

		document.add(new Paragraph("\n"));

		document.add(new Paragraph("\n"));

		PdfPTable tableFondo = new PdfPTable(2);
		Paragraph paragraphFondo = new Paragraph();
		paragraphFondo.add(new Chunk("Fondo: " + (missione.getFondo() != null ? missione.getFondo() : ""), fontNormal));
		paragraphFondo.add("\n");
		paragraphFondo.add(new Chunk("GAE: " + (missione.getGAE() != null ? missione.getGAE() : ""), fontNormal));
		paragraphFondo.add("\n");
		paragraphFondo.add(new Chunk("CUP:", fontNormal));
		paragraphFondo.add("\n");
		paragraphFondo.add(new Chunk("Responsabile Gruppo:" + missione.getShortResponsabileGruppo(), fontNormal));
		tableFondo.addCell(new PdfPCell(paragraphFondo));
		PdfPCell cellFirmaResponsabile = new PdfPCell(new Paragraph("Firma Responsabile fondo:", fontBold));
		cellFirmaResponsabile.setMinimumHeight(40f);
		tableFondo.addCell(cellFirmaResponsabile);
		document.add(tableFondo);

		Paragraph paragraphDirettore = new Paragraph("\n\nIl Direttore\nDott. Vincenzo Lapenna\n");
		paragraphDirettore.setAlignment(Paragraph.ALIGN_RIGHT);
		document.add(paragraphDirettore);

		Chunk underline = new Chunk(
				"Avvertenza:Ai fini dell" + new Character('\u2032') + " ammissione a pagamento della missione "
						+ "il presente modulo e quello di richiesta rimborso devono essere compilati in ogni "
						+ "loro parte, depennando eventuali dizioni che non interessino.");

		underline.setUnderline(0.2f, -2f);
		Paragraph paragraphFooter = new Paragraph("\n\n\n\n\n\n", new Font());
		paragraphFooter.add(underline);
		document.add(paragraphFooter);
		document.close();
	}

	/**
	 * @return {@link IPDFBuilderType}
	 */
	@Override
	public IPDFBuilderType getType() {
		return PDFBuilderType.MISSIONE_PDF_BUILDER;
	}

	/**
	 * @throws Exception
	 */
	@Override
	public void buildVeicolo() throws Exception {
		logger.debug(
				"############################{} ::::::::::::<<<<<<<<< PDF VEICOLO GENERATION BEGIN" + " >>>>>>>>>>>>\n",
				getType());
		Preconditions.checkArgument((this.veicolo != null), "The Parameter Veicolo must not be null.");
		Document document = new Document(PageSize.A4, 50, 50, 50, 50);

		PdfWriter.getInstance(document,
				((this.fileVeicolo != null) ? new FileOutputStream(this.fileVeicolo) : this.outputStream));
		document.addSubject("Modulo Mezzo Proprio");
		document.open();

		Chunk underline = new Chunk("RICHIESTA AUTORIZZAZIONE ALL' USO DEL MEZO PROPRIO");

		underline.setUnderline(0.2f, -2f);
		Paragraph paragraphUnderline = new Paragraph("\n\n", fontBold);
		paragraphUnderline.add(underline);
		paragraphUnderline.setAlignment(Element.ALIGN_CENTER);
		document.add(paragraphUnderline);

		Paragraph paragraphDichiarazione = new Paragraph("Il sottoscritto " + user.getAnagrafica().getCognome() + " "
				+ user.getAnagrafica().getNome() + " qualifica: " + user.getDatiCNR().getDescrizioneQualifica()
				+ ", in esecuzione dell' incarico ricevuto con ordine di missione n. " + missione.getId() + " del "
				+ (missione.getDataInserimento().toString()), fontNormal);
		document.add(paragraphDichiarazione);

		Chunk underline2 = new Chunk("CHIEDE");

		underline2.setUnderline(0.2f, -2f);
		Paragraph paragraphUnderline2 = new Paragraph("\n\n", fontBold);
		paragraphUnderline2.add(underline2);
		paragraphUnderline2.setAlignment(Element.ALIGN_CENTER);
		document.add(paragraphUnderline2);

		document.add(new Paragraph(
				"di essere autorizzato ad utilizzare il mezzo proprio per effettuare i percorsi da Tito Scalo a "
						+ missione.getLocalita(),
				fontNormal));

		Chunk underline3 = new Chunk("Estremi Auto");

		underline3.setUnderline(0.2f, -2f);
		Paragraph paragraphUnderline3 = new Paragraph("\n\n", fontBold);
		paragraphUnderline3.add(underline3);
		paragraphUnderline3.setAlignment(Element.ALIGN_CENTER);
		document.add(paragraphUnderline3);

		Paragraph paragraphInformazione = new Paragraph(
				"\n\n" + "Tipo:" + veicolo.getTipo() + "targa:" + veicolo.getTarga() + "\ncarta di circolazione:"
						+ veicolo.getCartaCircolazione() + "\nPolizza assicurazione:" + veicolo.getPolizzaAssicurativa()
						+ "\nPatente n.:" + user.getPatente().getNumeroPatente() + " con validit&agrave; "
						+ user.getPatente().getValidaFinoAl().toString() + "\n\n",
				fontNormal);

		document.add(paragraphInformazione);
		
		document.add(new Paragraph("Ai sensi di quanto disposto dall'articolo 6 c. 12 del D.L. 78/2010, dalla circolare n.36 del 22/10/2010 del Ministero dell'Economia e delle\n"
				+ "Finanze - Dipartimento della Ragioneria Generale dello Stato e della delibera della Corte dei COnti a Sezioni Riunite n.8/CONTR/11 del 7\n"
				+ "febbraio 2011, si allega alla presente la copia dei tariffari forniti dagli esercenti dei trasporti pubblici per le tratte sopra indicate.",fontBold6));
		
		document.add(new Paragraph("Il Richiedente\n\n"));
		
		document.add(new Paragraph("\n\nNel rispetto delle disposizioni normative in matteria (Art. 6 c. 12 del D.L. 78/2010, Circ. 36 del 22/10/2010 del M.E.F. - Dip.tp della\n"
				+ "ragioneria Generale dello Stato e delibera della Corte dei Conti a Sezioni riunite n. 8/CONTR/11 del 7  febbraio 2011) e trattandosi\n"
				+ " di una circostanza eccezionale e non ricorrente,si autorizza l'uso del mezzo proprio per le seguenti motivazioni:\n"
				+ missione.getMotivazioniMezzoProprio(),fontBold6));


		Paragraph paragraphDirettore = new Paragraph("Visto si Autorizza\t\nIl Dirigente");
		paragraphDirettore.setAlignment(Paragraph.ALIGN_RIGHT);
		document.add(paragraphDirettore);

		document.close();
	}
}
