package it.cnr.missioni.dashboard.checkmassimale;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Test;

import it.cnr.missioni.model.configuration.Massimale;
import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.model.rimborso.Fattura;
import it.cnr.missioni.model.rimborso.Rimborso;

/**
 * @author Salvia Vito
 */
public class RimborsoTest {

	
	@Test
	public void checkMassimaleChainOfResponsibility() throws Exception {
		Rimborso r = buildRimborso();
		Missione missione = new Missione();
		missione.setIdUser("01");
		missione.setMissioneEstera(false);
		Fattura fattura = ((List<Fattura>) r.getMappaFattura().values().stream().filter(f -> f.getId().equals("03"))
				.collect(Collectors.toList())).get(0);
		r.getMappaFattura().values().stream().close();
		Massimale massimale = new Massimale();
		massimale.setValue(40.0);
		
		CheckMassimale checkMassimale = new CheckMassimale(missione,fattura, r, new HashMap<String,Fattura>());
		checkMassimale.initialize();

	}
	
	
	private Rimborso buildRimborso() {
		Rimborso rimborso = new Rimborso();
		rimborso.setAnticipazionePagamento(new Double(300));
		rimborso.setDataRimborso(new DateTime());
		rimborso.setMandatoPagamento("01");

		Fattura fattura = new Fattura();
		fattura.setNumeroFattura(new Long(138));
		fattura.setData(new DateTime(2015, 11, 12, 13, 0, DateTimeZone.UTC));
		fattura.setImporto(30.0);
		fattura.setValuta("Euro");
		fattura.setIdTipologiaSpesa("01");
		fattura.setShortDescriptionTipologiaSpesa("Vitto");
		fattura.setId("01");
		rimborso.getMappaFattura().put("01", fattura);

		fattura = new Fattura();
		fattura.setNumeroFattura(new Long(138));
		fattura.setData(new DateTime(2015, 11, 12, 18, 0, DateTimeZone.UTC));
		fattura.setImporto(25.0);
		fattura.setValuta("Euro");
		fattura.setIdTipologiaSpesa("01");
		fattura.setShortDescriptionTipologiaSpesa("Vitto");
		fattura.setId("03");
		rimborso.getMappaFattura().put("03", fattura);

		fattura = new Fattura();
		fattura.setNumeroFattura(new Long(135));
		fattura.setData(new DateTime(2015, 11, 13, 13, 0, DateTimeZone.UTC));
		fattura.setImporto(89.8);
		fattura.setValuta("Euro");
		fattura.setIdTipologiaSpesa("02");
		fattura.setShortDescriptionTipologiaSpesa("Albergo");
		fattura.setId("02");
		rimborso.getMappaFattura().put("02", fattura);

		return rimborso;
	}

}
