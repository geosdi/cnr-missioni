package it.cnr.missioni.el.utility;

import it.cnr.missioni.model.user.*;
import it.cnr.missioni.model.user.Anagrafica.Genere;
import it.cnr.missioni.model.user.DatiCNR.LivelloUserEnum;
import org.joda.time.DateTime;
import org.joda.time.chrono.ISOChronology;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Salvia Vito
 */
public class UserFunction {


    public static List<User> creaMassiveUsers() {

        List<User> listaUsers = new ArrayList<User>();

        User user = null;
        Anagrafica anagrafica = null;
        Credenziali credenziali = null;
        user = new User();
        user.setId("01");
        user.setResponsabileGruppo(true);
        anagrafica = new Anagrafica();
        anagrafica.setGenere(Genere.UOMO);
        anagrafica.setCognome("Salvia");
        anagrafica.setNome("Vito");
        anagrafica.setDataNascita(new DateTime(1982, 7, 30, 0, 0, ISOChronology.getInstanceUTC()));
        anagrafica.setCodiceFiscale("slvvtttttttttttt");
        anagrafica.setLuogoNascita("Potenza");
        credenziali = new Credenziali();
        credenziali.setUsername("vito.salvia");
        credenziali.setRuoloUtente(RuoloUserEnum.UTENTE_ADMIN);
        credenziali.setPassword(credenziali.md5hash("vitosalvia"));
        user.setCredenziali(credenziali);
        user.setAnagrafica(anagrafica);
        Veicolo veicolo = new Veicolo();
        veicolo.setTipo("Ford Fiesta");
        veicolo.setTarga("AA111BB");
        veicolo.setCartaCircolazione("12234");
        veicolo.setPolizzaAssicurativa("A1B2");
        veicolo.setVeicoloPrincipale(true);
        veicolo.setId("01");
        Map<String, Veicolo> mappaVeicoli = new HashMap<String, Veicolo>();
        mappaVeicoli.put(veicolo.getId(), veicolo);
        user.setMappaVeicolo(mappaVeicoli);
        DatiCNR datiCNR = new DatiCNR();
        datiCNR.setDatoreLavoro("02");
        datiCNR.setShortDescriptionDatoreLavoro("Rossi Paolo");
        datiCNR.setIban("IT0000000000000000");
        datiCNR.setLivello(LivelloUserEnum.V);
        datiCNR.setMail("vito.salvia@gmail.com");
        datiCNR.setMatricola("1111111");
        datiCNR.setDescrizioneQualifica("Assegnista");
        datiCNR.setIdQualifica("01");
        user.setDatiCNR(datiCNR);
        Patente p = new Patente();
        p.setDataRilascio(new DateTime(2001, 12, 15, 0, 0, ISOChronology.getInstanceUTC()));
        p.setNumeroPatente("12334");
        p.setRilasciataDa("MCTC");
        p.setValidaFinoAl(new DateTime(2021, 12, 15, 0, 0, ISOChronology.getInstanceUTC()));
        user.setPatente(p);
        Residenza r = new Residenza();
        r.setIndirizzo("Via Verdi");
        r.setComune("Tito");
        r.setDomicilioFiscale("Via Convento");
        user.setResidenza(r);
        user.setDataRegistrazione(new DateTime(2015, 1, 4, 0, 0, ISOChronology.getInstanceUTC()));
        user.setDateLastModified(new DateTime(2015, 1, 4, 0, 0, ISOChronology.getInstanceUTC()));
        user.setRegistrazioneCompletata(true);
        listaUsers.add(user);

        user = new User();
        user.setId("02");
        anagrafica = new Anagrafica();
        anagrafica.setCognome("Franco");
        anagrafica.setNome("Luigi");
        credenziali = new Credenziali();
        credenziali.setUsername("luigi.franco");
        credenziali.setPassword(credenziali.md5hash("luigifranco"));
        credenziali.setRuoloUtente(RuoloUserEnum.UTENTE_ADMIN);
        user.setCredenziali(credenziali);
        user.setAnagrafica(anagrafica);
        user.setRegistrazioneCompletata(false);
        user.setResponsabileGruppo(false);
        listaUsers.add(user);

        user = new User();
        user.setId("03");
        anagrafica = new Anagrafica();
        anagrafica.setCognome("Bianchi");
        anagrafica.setNome("Mario");
        credenziali = new Credenziali();
        credenziali.setUsername("mario.bianchi");
        credenziali.setPassword(credenziali.md5hash("mariobianchi"));
        credenziali.setRuoloUtente(RuoloUserEnum.UTENTE_SEMPLICE);
        user.setCredenziali(credenziali);
        user.setAnagrafica(anagrafica);
        user.setRegistrazioneCompletata(false);
        user.setResponsabileGruppo(false);
        listaUsers.add(user);

        user = new User();
        user.setId("04");
        anagrafica = new Anagrafica();
        credenziali = new Credenziali();
        credenziali.setUsername("admin");
        credenziali.setPassword(credenziali.md5hash("admin"));
        credenziali.setRuoloUtente(RuoloUserEnum.UTENTE_ADMIN);
        user.setCredenziali(credenziali);
        user.setRegistrazioneCompletata(false);
        user.setResponsabileGruppo(false);
        listaUsers.add(user);

        return listaUsers;
    }


}
