package it.cnr.missioni.notification.support.itext.missione;

import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import it.cnr.missioni.notification.support.itext.PDFBuilder;

/**
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
public class MissionePDFBuilder extends PDFBuilder.AbstractPDFBuilder {

	private static final Logger logger = LoggerFactory.getLogger(MissionePDFBuilder.class);


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
		DateFormat formatData = new SimpleDateFormat("dd/MM/yyyy", Locale.ITALY);
		DateFormat formatDataTime = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.ITALY);



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

		//Ordine di missione
		Paragraph paragraphOrdine2 = new Paragraph(
				"Ordine di missione N." + missione.getId() + " " + " del " + formatDataTime.format(missione.getDataInserimento().toDate()) + "\n\n\n", fontBold);
		paragraphOrdine2.setAlignment(Element.ALIGN_CENTER);
		document.add(paragraphOrdine2);

		PdfPTable tableUtente = new PdfPTable(2);
		
		//Prima riga - Cognome nome Qualifica e livello
		PdfPTable nestedTableUtente = new PdfPTable(2);
		PdfPCell cellQualifica = new PdfPCell(new Paragraph(user.getDatiCNR().getDescrizioneQualifica(), fontNormal));
		cellQualifica.setBorder(Rectangle.NO_BORDER);
		nestedTableUtente.addCell(cellQualifica);
		PdfPCell cellLivello = new PdfPCell(new Paragraph(user.getDatiCNR().getLivello().toString(), fontNormal));
		cellLivello.setBorder(Rectangle.LEFT);
		nestedTableUtente.addCell(cellLivello);

		//Seconda riga 
		tableUtente.addCell(
				new Paragraph(user.getAnagrafica().getCognome() + " " + user.getAnagrafica().getNome(), fontNormal));
		tableUtente.addCell(nestedTableUtente);
		PdfPCell cellCognomeNomeLabel = new PdfPCell(new Paragraph("cognome e nome", fontNormal6));
		PdfPCell cellQualificaLabel = new PdfPCell(new Paragraph("qualifica", fontNormal6));
		PdfPCell cellLivelloLabel = new PdfPCell(new Paragraph("livello", fontNormal6));
		cellCognomeNomeLabel.setBorder(Rectangle.NO_BORDER);
		cellQualificaLabel.setBorder(Rectangle.NO_BORDER);
		cellLivelloLabel.setBorder(Rectangle.NO_BORDER);

		PdfPCell c = new PdfPCell();
		c.setBorder(Rectangle.NO_BORDER);
		nestedTableUtente = new PdfPTable(2);
		c.addElement(nestedTableUtente);

		nestedTableUtente.addCell(cellQualificaLabel);
		nestedTableUtente.addCell(cellLivelloLabel);
		tableUtente.addCell(cellCognomeNomeLabel);
		tableUtente.addCell(c);

		//
		tableUtente.addCell(new Paragraph(formatData.format(user.getAnagrafica().getDataNascita().toDate()) + " " + user.getAnagrafica().getLuogoNascita(), fontNormal));
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
		PdfPCell cellCodiceTerzo = new PdfPCell(new Paragraph("Codice Terzo", fontNormal6));
		cellCodiceTerzo.setBorder(Rectangle.NO_BORDER);
		PdfPCell cellMatricola = new PdfPCell(new Paragraph("Matricola del CNR", fontNormal6));
		cellMatricola.setBorder(Rectangle.NO_BORDER);
		tableUtente.addCell(cellCodiceTerzo);
		tableUtente.addCell(cellMatricola);
		document.add(tableUtente);

		document.add(new Paragraph("\nSi dispone l'" + new Character('\u2032')
				+ " espletamento da parte della S.V. della seguente missione\n\n", fontBold));

		Chunk chunkOggetto = new Chunk("Oggetto:", fontBold);
		Paragraph paragraphOggetto = new Paragraph();
		paragraphOggetto.add(chunkOggetto);
		paragraphOggetto.add(new Chunk(missione.getOggetto(), fontNormal));
		document.add(paragraphOggetto);

		document.add(new Paragraph("\n"));

		PdfPTable tabletableInfo = new PdfPTable(2);
		//Localita
		PdfPCell cellLocalita = new PdfPCell(new Paragraph("Localit" + new Character('\u00E0'), fontBold));
		cellLocalita.setBorder(Rectangle.NO_BORDER);
		tabletableInfo.addCell(cellLocalita);
		tabletableInfo.addCell( new PdfPCell(new Paragraph(missione.getLocalita(), fontNormal)));
		
		//Altre localita
		PdfPCell cellAltreLocalita = new PdfPCell(new Paragraph("Altre Localit" + new Character('\u00E0'), fontBold));
		cellAltreLocalita.setBorder(Rectangle.NO_BORDER);
		tabletableInfo.addCell(cellAltreLocalita);
		tabletableInfo.addCell( new PdfPCell(new Paragraph(missione.getAltreLocalita() != null ? missione.getAltreLocalita() : "", fontNormal)));
		
		PdfPCell cellDistanza = new PdfPCell(new Paragraph("Distanza dalla sede di servizio km:", fontBold));
		cellDistanza.setBorder(Rectangle.NO_BORDER);
		tabletableInfo.addCell(cellDistanza);
		tabletableInfo.addCell(new PdfPCell(new Paragraph("" + missione.getDistanza(), fontNormal)));

		//Tipo veicolo
		PdfPCell cellMezzoUtilizzato = new PdfPCell(new Paragraph("Mezzo Utilizzato:", fontBold));
		cellMezzoUtilizzato.setBorder(Rectangle.NO_BORDER);
		tabletableInfo.addCell(cellMezzoUtilizzato);

		String tipoVeicolo = missione.getTipoVeicolo();
		tabletableInfo.addCell(new PdfPCell(new Paragraph(
				tipoVeicolo + (missione.getMotivazioni() != null ? " - " + missione.getMotivazioni() : ""),
				fontNormal)));
		
		//Altre disposizioni
		PdfPCell cellAltreDisposizioni = new PdfPCell(new Paragraph("Altre Disposizioni:", fontBold));
		cellAltreDisposizioni.setBorder(Rectangle.NO_BORDER);
		tabletableInfo.addCell(cellAltreDisposizioni);

		tabletableInfo.addCell(new PdfPCell(new Paragraph((missione.getAltreDisposizioni() != null ? missione.getAltreDisposizioni() : ""),
				fontNormal)));

		if (missione.isMissioneEstera()) {
			PdfPCell cellMissioneEstera = new PdfPCell(new Paragraph("Tipologia Rimborso", fontBold));
			cellMissioneEstera.setBorder(Rectangle.NO_BORDER);
			tabletableInfo.addCell(cellMissioneEstera);
			PdfPCell cellMissioneEstera2 = new PdfPCell(new Paragraph(
					missione.getDatiMissioneEstera().getTrattamentoMissioneEsteraEnum().getStato(), fontNormal));
			tabletableInfo.addCell(cellMissioneEstera2);
		}

		document.add(tabletableInfo);

		Paragraph paragraphObbligo = new Paragraph();
		document.add(paragraphObbligo);

		String caption;

		Days days = Days.daysBetween(missione.getDatiPeriodoMissione().getInizioMissione(),
				missione.getDatiPeriodoMissione().getFineMissione());

		caption = days.getDays() + " gg - ";

		caption.concat(Hours.hoursBetween(missione.getDatiPeriodoMissione().getInizioMissione(),
				missione.getDatiPeriodoMissione().getFineMissione()) + " HH");

		Paragraph paragraphData = new Paragraph();
		paragraphData.add((new Chunk("Durata Presunta:", fontBold)));
		paragraphData.add(new Chunk(caption, fontNormal));
		paragraphData.add("\t");
		paragraphData.add(new Chunk(" Data Inizio:", fontBold));
		paragraphData.add(new Chunk(formatDataTime.format(missione.getDatiPeriodoMissione().getInizioMissione().toDate()), fontNormal));
		paragraphData.add("\t");
		paragraphData.add(new Chunk(" Data Presunta Fine:", fontBold));
		paragraphData.add(new Chunk(formatDataTime.format(missione.getDatiPeriodoMissione().getFineMissione().toDate()), fontNormal));
		paragraphData.add("\t");
		document.add(paragraphData);

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
		paragraphFondo.add(new Chunk(
				"A seguito di:" + (missione.getShortUserSeguito() != null ? missione.getShortUserSeguito() : ""),
				fontNormal));
		tableFondo.addCell(new PdfPCell(paragraphFondo));
		PdfPCell cellFirmaResponsabile = new PdfPCell(
				new Paragraph("Responsabile fondo:" + missione.getShortResponsabileGruppo(), fontBold));
		cellFirmaResponsabile.setMinimumHeight(40f);
		tableFondo.addCell(cellFirmaResponsabile);
		document.add(tableFondo);

		Paragraph paragraphDirettore = new Paragraph("\n\nIl Direttore\n"+direttore.getValue()+"\n");
		paragraphDirettore.setAlignment(Paragraph.ALIGN_RIGHT);
		document.add(paragraphDirettore);

		Paragraph paragraphDataFooter = new Paragraph("\n\nData " + formatData.format((new DateTime()).toDate()));
		paragraphDataFooter.setAlignment(Paragraph.ALIGN_LEFT);
		document.add(paragraphDataFooter);

		Chunk underline = new Chunk("Avvertenza:Ai fini dell'"
				+ " ammissione a pagamento della missione "
				+ "il presente modulo e quello di richiesta rimborso devono essere compilati integralmente depennando eventuali dizioni che non interessano. "
				+ "\nLe spese non documentate non possono venire rimborsate. Per spese effettuate in valuta, ove non sia"
				+ " allegata distinta bancaria di cambio, il rimborso è disposto al cambio vigente alla data d'inizio missione.");

		underline.setUnderline(0.2f, -2f);
		Paragraph paragraphFooter = new Paragraph("\n\n", fontNormal);
		paragraphFooter.add(underline);
		paragraphFooter.setAlignment(Paragraph.ALIGN_JUSTIFIED);
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
		
		
		Chunk underline = new Chunk("RICHIESTA AUTORIZZAZIONE ALL' USO DEL MEZO PROPRIO\n\n");

		underline.setUnderline(0.2f, -2f);
		Paragraph paragraphUnderline = new Paragraph("\n\n", fontBold);
		paragraphUnderline.add(underline);
		paragraphUnderline.setAlignment(Element.ALIGN_CENTER);
		document.add(paragraphUnderline);
		DateFormat formatDataTime = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.ITALY);
		DateFormat formatData = new SimpleDateFormat("dd/MM/yyyy", Locale.ITALY);

		Paragraph paragraphDichiarazione = new Paragraph("Il sottoscritto " + user.getAnagrafica().getCognome() + " "
				+ user.getAnagrafica().getNome() + " qualifica: " + user.getDatiCNR().getDescrizioneQualifica()
				+ ", in esecuzione dell' incarico ricevuto con ordine di missione n. " + missione.getId() + " del "
				+ (formatDataTime.format(missione.getDataInserimento().toDate())), fontNormal);
		document.add(paragraphDichiarazione);

		underline = new Chunk("CHIEDE\n");

		underline.setUnderline(0.2f, -2f);
		paragraphUnderline = new Paragraph("\n\n", fontBold);
		paragraphUnderline.add(underline);
		paragraphUnderline.setAlignment(Element.ALIGN_CENTER);
		document.add(paragraphUnderline);

		document.add(new Paragraph(
				"di essere autorizzato ad utilizzare il mezzo proprio per effettuare i percorsi da Tito Scalo a "
						+ missione.getLocalita(),
				fontNormal));

		underline = new Chunk("Estremi Auto");

		underline.setUnderline(0.2f, -2f);
		paragraphUnderline = new Paragraph("\n", fontBold);
		paragraphUnderline.add(underline);
		paragraphUnderline.setAlignment(Element.ALIGN_LEFT);
		document.add(paragraphUnderline);

		Paragraph paragraphInformazione = new Paragraph(
				"\n" + "Tipo:" + veicolo.getTipo() + "\ntarga:" + veicolo.getTarga() + "\nCarta di circolazione:"
						+ veicolo.getCartaCircolazione() + "\nPolizza assicurazione:" + veicolo.getPolizzaAssicurativa()
						+ "\nPatente n.:" + user.getPatente().getNumeroPatente() + " con validit"+new Character('\u00E0')+" fino al "
						+ formatData.format(user.getPatente().getValidaFinoAl().toDate()) + "\n\n",
				fontNormal);

		document.add(paragraphInformazione);

		
		Paragraph paragraphNotifica = new Paragraph("Ai sensi di quanto disposto dall'articolo 6 c. 12 del D.L. 78/2010, dalla circolare n.36 del 22/10/2010 del Ministero dell'Economia e delle"
				+ "Finanze - Dipartimento della Ragioneria Generale dello Stato e della delibera della Corte dei COnti a Sezioni Riunite n.8/CONTR/11 del 7"
				+ "febbraio 2011, si allega alla presente la copia dei tariffari forniti dagli esercenti dei trasporti pubblici per le tratte sopra indicate.",
		fontBold);
		paragraphNotifica.setAlignment(Element.ALIGN_JUSTIFIED);
		document.add(paragraphNotifica);

		document.add(new Paragraph("\n\nIl Richiedente\n\n"));

		paragraphNotifica = new Paragraph("\n\nNel rispetto delle disposizioni normative in matteria (Art. 6 c. 12 del D.L. 78/2010, Circ. 36 del 22/10/2010 del M.E.F. - Dip.tp della"
				+ "ragioneria Generale dello Stato e delibera della Corte dei Conti a Sezioni riunite n. 8/CONTR/11 del 7  febbraio 2011) e trattandosi"
				+ " di una circostanza eccezionale e non ricorrente,si autorizza l'uso del mezzo proprio per le seguenti motivazioni:\n"
				+ missione.getMotivazioni(),fontBold);
		paragraphNotifica.setAlignment(Element.ALIGN_JUSTIFIED);
		document.add(paragraphNotifica);

		Paragraph paragraphDataFooter = new Paragraph("\n\nData " + formatData.format((new DateTime()).toDate()));
		paragraphDataFooter.setAlignment(Paragraph.ALIGN_LEFT);
		document.add(paragraphDataFooter);
		
		Paragraph paragraphDirettore = new Paragraph("Visto si Autorizza\t\nIl Direttore\n"+direttore.getValue());
		paragraphDirettore.setAlignment(Paragraph.ALIGN_RIGHT);
		document.add(paragraphDirettore);

		document.close();
	}
}
