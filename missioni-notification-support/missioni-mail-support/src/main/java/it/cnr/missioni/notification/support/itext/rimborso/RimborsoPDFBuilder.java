package it.cnr.missioni.notification.support.itext.rimborso;

import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

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

import it.cnr.missioni.model.rimborso.Fattura;
import it.cnr.missioni.notification.support.itext.PDFBuilder;

/**
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
public class RimborsoPDFBuilder extends PDFBuilder.AbstractPDFBuilder {

    private static final Logger logger = LoggerFactory.getLogger(RimborsoPDFBuilder.class);

    protected RimborsoPDFBuilder() {
    }

    public static PDFBuilder newPDFBuilder() {
        return new RimborsoPDFBuilder();
    }

    @Override
    public void build() throws Exception {
        logger.debug("############################{} ::::::::::::<<<<<<<<< PDF GENERATION BEGIN" + " >>>>>>>>>>>>\n",
                getType());
        super.checkArguments();
		DateFormat formatDataTime = new SimpleDateFormat("dd/MM/yyyy", Locale.ITALY);
        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
        String dataInserimentoMissione = missione.getDataInserimento().toString();
        String dataInizio = formatDataTime.format(missione.getDatiPeriodoMissione().getInizioMissione().toDate());
        String dataFine = formatDataTime.format(missione.getDatiPeriodoMissione().getFineMissione().toDate());
        String dataAttraversamentoFrontieraAndata = "";
        String dataAttraversamentoFrontieraRitorno = "";
        if (missione.isMissioneEstera()){
            dataAttraversamentoFrontieraAndata =formatDataTime.format( missione.getDatiMissioneEstera().getAttraversamentoFrontieraAndata().toDate());
            dataAttraversamentoFrontieraRitorno = formatDataTime.format(missione.getDatiMissioneEstera().getAttraversamentoFrontieraRitorno().toDate());
        }

        Map<String, Fattura> treeMap = new TreeMap<String, Fattura>(missione.getRimborso().getMappaFattura());

        List<Fattura> listaScontrini = new ArrayList<Fattura>(treeMap.values());
        
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

        Paragraph paragraphHeader = new Paragraph("\nRichiesta di rimborso della missione autorizzata con Ordine n. "
                + missione.getId() + " del " + dataInserimentoMissione + "\n\n\n", fontBold);
        paragraphHeader.setAlignment(Element.ALIGN_CENTER);
        document.add(paragraphHeader);

        PdfPTable tableDatiUtente = new PdfPTable(2);
        tableDatiUtente.addCell(new PdfPCell(new Paragraph(
                new Paragraph(user.getAnagrafica().getCognome() + " " + user.getAnagrafica().getNome(), fontNormal))));
        tableDatiUtente.addCell(
                new PdfPCell(new Paragraph(new Paragraph(user.getAnagrafica().getCodiceFiscale(), fontNormal))));

        PdfPCell cellUtente = new PdfPCell(new Paragraph("Cognome e nome", fontNormal6));
        PdfPCell cellCodiceFiscale = new PdfPCell(new Paragraph("Codice fiscale", fontNormal6));
        cellUtente.setBorder(Rectangle.NO_BORDER);
        cellCodiceFiscale.setBorder(Rectangle.NO_BORDER);
        tableDatiUtente.addCell(cellUtente);
        tableDatiUtente.addCell(cellCodiceFiscale);
        document.add(tableDatiUtente);

        Chunk chunkOggetto = new Chunk("Oggetto:", fontBold);
        Paragraph paragraphOggetto = new Paragraph();
        paragraphOggetto.add(chunkOggetto);
        paragraphOggetto.add(new Chunk(missione.getOggetto(), fontNormal));
        document.add(paragraphOggetto);

        Chunk chunkAllegare = new Chunk(
                "lettera convocazione; documenti originali di viaggio come da distinta; autorizzazione per eventuale uso mezzo trasporto privato.",
                fontBold);
        chunkAllegare.setUnderline(0.2f, -2f);
        Paragraph paragraphAllegare = new Paragraph("\n\nAllegare:", fontBold);
        paragraphAllegare.add(chunkAllegare);
        paragraphAllegare.add("\n\n");
        document.add(paragraphAllegare);

        PdfPTable tableDate = new PdfPTable(6);
        PdfPCell cellAndata = new PdfPCell(new Paragraph("Andata", fontBold));
        cellAndata.setColspan(2);
        tableDate.addCell(cellAndata);
        tableDate.addCell(new PdfPCell(new Paragraph("Data", fontBold)));
        PdfPCell cellRitorno = new PdfPCell(new Paragraph("Ritorno", fontBold));
        cellRitorno.setColspan(2);
        tableDate.addCell(cellRitorno);
        tableDate.addCell(new PdfPCell(new Paragraph("Data", fontBold)));
        PdfPCell cellInizioMissione = new PdfPCell(new Paragraph("Inizio missione", fontBold));
        cellInizioMissione.setColspan(2);
        tableDate.addCell(cellInizioMissione);
        tableDate.addCell(new PdfPCell(new Paragraph(dataInizio, fontNormal6)));
        PdfPCell cellAttraversamentoAndata = new PdfPCell(
                new Paragraph("Attraversamento frontiera o sbarco estero", fontBold));
        cellAttraversamentoAndata.setColspan(2);
        tableDate.addCell(cellAttraversamentoAndata);
        tableDate.addCell(new PdfPCell(new Paragraph(dataAttraversamentoFrontieraRitorno, fontNormal6)));
        PdfPCell cellAttraversamentoRitorno = new PdfPCell(
                new Paragraph("Attraversamento frontiera o sbarco estero", fontBold));
        cellAttraversamentoRitorno.setColspan(2);
        tableDate.addCell(cellAttraversamentoRitorno);
        tableDate.addCell(new PdfPCell(new Paragraph(dataAttraversamentoFrontieraAndata, fontNormal6)));
        PdfPCell cellFine = new PdfPCell(new Paragraph("Fine missione", fontBold));
        cellFine.setColspan(2);
        tableDate.addCell(cellFine);
        tableDate.addCell(new PdfPCell(new Paragraph(dataFine, fontNormal6)));
        document.add(tableDate);

        document.add(new Paragraph("\n"));

        PdfPTable tableScontrino = new PdfPTable(5);

        tableScontrino.addCell(new PdfPCell(new Paragraph("Data", fontBold)));
        tableScontrino.addCell(new PdfPCell(new Paragraph("Descrizione", fontBold)));
        tableScontrino.addCell(new PdfPCell(new Paragraph("Numero Giustificativo", fontBold)));
        tableScontrino.addCell(new PdfPCell(new Paragraph("Importo", fontBold)));
        tableScontrino.addCell(new PdfPCell(new Paragraph("Valuta", fontBold)));

        for (Fattura fattura : listaScontrini) {
            tableScontrino.addCell(new PdfPCell(new Paragraph(formatDataTime.format(fattura.getData().toDate()), fontNormal)));
            tableScontrino.addCell(new PdfPCell(new Paragraph(fattura.getShortDescriptionTipologiaSpesa(), fontNormal)));
            tableScontrino.addCell(new PdfPCell(new Paragraph(fattura.getNumeroFattura().toString(), fontNormal)));
            tableScontrino.addCell(new PdfPCell(new Paragraph(new DecimalFormat("#0.00").format(fattura.getImporto()) + " € ", fontNormal)));
            tableScontrino.addCell(new PdfPCell(new Paragraph(fattura.getValuta() + "", fontNormal)));
        }
        int size = listaScontrini.size();
        PdfPCell cellVuota = new PdfPCell();
        cellVuota.setFixedHeight(10f);
        for (int i = 0; i < 10 - size; i++) {
            tableScontrino.addCell(cellVuota);
            tableScontrino.addCell(cellVuota);
            tableScontrino.addCell(cellVuota);
            tableScontrino.addCell(cellVuota);
            tableScontrino.addCell(cellVuota);
        }
        document.add(tableScontrino);

        document.add(new Paragraph("\n"));

        PdfPTable tablePagamento = new PdfPTable(2);
        
        PdfPCell cellTotKm = new PdfPCell(
                new Paragraph("Tot.Km da rimborsare", fontBold));
        cellTotKm.setBorder(Rectangle.NO_BORDER);
        tablePagamento.addCell(cellTotKm);
        PdfPCell cellTotKm2 = new PdfPCell(new Paragraph(missione.getRimborso().getTotKm() != null ? missione.getRimborso().getTotKm().toString():"0.0", fontNormal));
        cellTotKm2.setMinimumHeight(20f);
        tablePagamento.addCell(cellTotKm2);
        PdfPCell cellAvviso = new PdfPCell(
                new Paragraph("Avviso di pagamento presso (Via Citt" + new Character('\u00E0') + " CAP)", fontBold));
        cellAvviso.setBorder(Rectangle.NO_BORDER);
        tablePagamento.addCell(cellAvviso);
        PdfPCell cellAvviso2 = new PdfPCell(new Paragraph(missione.getRimborso().getAvvisoPagamento(), fontNormal));
        cellAvviso2.setMinimumHeight(20f);
        tablePagamento.addCell(cellAvviso2);
        PdfPCell cellModalita = new PdfPCell(
                new Paragraph("Modalit" + new Character('\u00E0') + " di pagamento", fontBold));
        cellModalita.setBorder(Rectangle.NO_BORDER);
        tablePagamento.addCell(cellModalita);
        PdfPCell cellModalita2 = new PdfPCell(new
                Paragraph(user.getDatiCNR().getIban(), fontNormal));
        cellModalita2.setMinimumHeight(20f);
        tablePagamento.addCell(cellModalita2);

        PdfPCell cellImportoTerzi = new PdfPCell(
                new Paragraph("Importo da Terzi", fontBold));
        cellImportoTerzi.setBorder(Rectangle.NO_BORDER);
        tablePagamento.addCell(cellImportoTerzi);
        PdfPCell importoDaTerzi2 = new PdfPCell(new
                Paragraph((new DecimalFormat("#0.00").format(missione.getRimborso().getImportoDaTerzi())+" €" ), fontNormal));
        importoDaTerzi2.setMinimumHeight(20f);
        tablePagamento.addCell(importoDaTerzi2);
        
        String mandato = missione.getDatiAnticipoPagamenti().getMandatoCNR() != null ? missione.getDatiAnticipoPagamenti().getMandatoCNR() :"";
        
        PdfPCell cellAnticipazioni = new PdfPCell(
                new Paragraph("Anticipazioni da detrarre", fontBold));
        cellAnticipazioni.setBorder(Rectangle.NO_BORDER);
        tablePagamento.addCell(cellAnticipazioni);
        PdfPCell cellAnticipazioni2 = new PdfPCell(new
                Paragraph((missione.getRimborso().getAnticipazionePagamento() != null ? missione.getRimborso().getAnticipazionePagamento()+" € con mandato "+mandato: "") , fontNormal));
        cellAnticipazioni2.setMinimumHeight(20f);
        tablePagamento.addCell(cellAnticipazioni2);

        document.add(tablePagamento);

        document.add(new Paragraph("Il Richiedente"));

        Paragraph paragraphDirettore = new Paragraph("Il Direttore\t\n("+direttore.getValue()+")\n\n");
        paragraphDirettore.setAlignment(Paragraph.ALIGN_RIGHT);
        document.add(paragraphDirettore);

        Chunk underline = new Chunk(
                "N.B Allegare lettera di convocazione e " + "documenti originali di viaggio come da distinta");

        underline.setUnderline(0.2f, -2f);


        Paragraph paragraphFooter = new Paragraph("\n\n", new Font());
        paragraphFooter.add(underline);
        document.add(paragraphFooter);
        document.add(new Paragraph("\n\nN.B. La validità del presente modulo è strettamente legata alla compilazione in tutte le sue parti",fontNormal6));
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
		// TODO Auto-generated method stub
		
	}


}
