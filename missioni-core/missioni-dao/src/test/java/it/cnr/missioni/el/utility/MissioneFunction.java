package it.cnr.missioni.el.utility;

import it.cnr.missioni.model.missione.DatiPeriodoMissione;
import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.model.missione.StatoEnum;
import it.cnr.missioni.model.missione.Missione.TipoVeicoloEnum;
import it.cnr.missioni.model.rimborso.Fattura;
import it.cnr.missioni.model.rimborso.Rimborso;
import org.elasticsearch.common.geo.GeoPoint;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Salvia Vito
 */
public class MissioneFunction {

    public static List<Missione> creaMassiveMissioni() {

        List<Missione> listaMissioni = new ArrayList<Missione>();

        Missione missione = new Missione();
        missione.setOggetto("Conferenza prova per lo sviluppo di applicazioni");
        missione.setLocalita("Roma");
        missione.setIdUser("01");
        missione.setMissioneEstera(false);
        missione.setStato(StatoEnum.PRESA_IN_CARICO);
        missione.setFondo("fondo");
        missione.setGAE("GAE");
        missione.setDataInserimento(new DateTime(2015, 11, 13, 0, 0, DateTimeZone.UTC));
        missione.setMezzoProprio(true);
        missione.setTipoVeicolo(TipoVeicoloEnum.AUTOVETTURA_DI_SERVIZIO);
        missione.setResponsabileGruppo("01");
        missione.setShortResponsabileGruppo("Salvia Vito");
        missione.setIdVeicolo("AA111BB");
        missione.setShortDescriptionVeicolo("Ford Fiesta");
        missione.setGeoPoint(new GeoPoint(41.9027835, 12.4963655));
        missione.setDistanza("353 Km");
        missione.setShortUser("Salvia Vito");
        DatiPeriodoMissione datiPeriodoMissione = new DatiPeriodoMissione();
        datiPeriodoMissione.setInizioMissione(new DateTime(2015, 11, 11, 0, 0, DateTimeZone.UTC));
        datiPeriodoMissione.setFineMissione(new DateTime(2015, 11, 15, 0, 0, DateTimeZone.UTC));
        missione.setDatiPeriodoMissione(datiPeriodoMissione);

        Fattura fattura = new Fattura();
        fattura.setNumeroFattura("134");
        fattura.setData(new DateTime(2015, 11, 12, 13, 0, DateTimeZone.UTC));
        fattura.setImporto(89.8);
        fattura.setValuta("Euro");
        fattura.setIdTipologiaSpesa("01");
        fattura.setShortDescriptionTipologiaSpesa("Vitto");
        fattura.setId("1111111111111");

        Fattura fattura_2 = new Fattura();
        fattura_2.setNumeroFattura("135");
        fattura_2.setData(new DateTime(2015, 11, 13, 13, 0, DateTimeZone.UTC));
        fattura_2.setImporto(89.8);
        fattura_2.setValuta("Euro");
        fattura_2.setIdTipologiaSpesa("02");
        fattura_2.setShortDescriptionTipologiaSpesa("Albergo");
        fattura_2.setId("2222222222222");

        Rimborso rimborso = new Rimborso();
        rimborso.setNumeroOrdine("M_01");
        rimborso.setAvvisoPagamento("Via Verdi");
        rimborso.setAnticipazionePagamento(0.0);
        rimborso.setDataRimborso(new DateTime(2015, 12, 12, 13, 14, DateTimeZone.UTC));
        rimborso.setTotale(179.6);
        rimborso.setTotaleDovuto(179.6);

        rimborso.getMappaFattura().put("1111111111111", fattura);
        rimborso.getMappaFattura().put("2222222222222", fattura_2);
        missione.setRimborso(rimborso);

        listaMissioni.add(missione);

        missione = new Missione();
        missione.getDatiPeriodoMissione().setInizioMissione(new DateTime(2015, 02, 11, 13, 14, DateTimeZone.UTC));
        missione.getDatiPeriodoMissione().setFineMissione(new DateTime(2015, 02, 15, 13, 14, DateTimeZone.UTC));

        missione.setOggetto("Conferenza");
        missione.setLocalita("Milano");
        missione.setIdUser("01");
        missione.setShortUser("Salvia Vito");
        missione.setResponsabileGruppo("01");
        missione.setShortResponsabileGruppo("Salvia Vito");
        missione.setStato(StatoEnum.PRESA_IN_CARICO);
        missione.setDataInserimento(new DateTime(2015, 8, 13, 0, 0, DateTimeZone.UTC));
        missione.setGeoPoint(new GeoPoint(45.4654219, 9.1859243));
        missione.setDistanza("901 Km");
        missione.setTipoVeicolo(TipoVeicoloEnum.AUTOVETTURA_DI_SERVIZIO);
        missione.setMezzoProprio(true);
        listaMissioni.add(missione);

        missione = new Missione();
        missione.setOggetto("Riunione prova");
        missione.setLocalita("Milano");
        missione.getDatiPeriodoMissione().setInizioMissione(new DateTime(2015, 02, 11, 13, 14, DateTimeZone.UTC));
        missione.getDatiPeriodoMissione().setFineMissione(new DateTime(2015, 02, 15, 13, 14, DateTimeZone.UTC));
        missione.setIdUser("02");
        missione.setShortUser("Franco Luigi");
        missione.setResponsabileGruppo("01");
        missione.setShortResponsabileGruppo("Salvia Vito");
        missione.setStato(StatoEnum.APPROVATA);
        missione.setDataInserimento(new DateTime(2015, 11, 23, 0, 0, DateTimeZone.UTC));
        missione.setGeoPoint(new GeoPoint(45.4654219, 9.1859243));
        missione.setDistanza("901 Km");
        missione.setTipoVeicolo(TipoVeicoloEnum.AUTOVETTURA_DI_SERVIZIO);
        missione.setMezzoProprio(true);
        listaMissioni.add(missione);

        return listaMissioni;

    }

}
