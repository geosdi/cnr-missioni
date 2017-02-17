package it.cnr.missioni.el.utility;

import it.cnr.missioni.model.configuration.Nazione;
import it.cnr.missioni.model.configuration.Nazione.AreaGeograficaEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Salvia Vito
 */
public class NazioneFunction {

    public static List<Nazione> creaMassiveNazioni() {

        List<Nazione> listaNazione = new ArrayList<Nazione>();

        Nazione nazione = new Nazione();
        nazione.setId("01");
        nazione.setValue("Germania");
        nazione.setAreaGeografica(AreaGeograficaEnum.F);
        listaNazione.add(nazione);

        nazione = new Nazione();
        nazione.setId("02");
        nazione.setValue("Stati Uniti");
        nazione.setAreaGeografica(AreaGeograficaEnum.E);
        listaNazione.add(nazione);


        return listaNazione;

    }

}
