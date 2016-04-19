package it.cnr.missioni.model.user;

import it.cnr.missioni.model.user.*;
import it.cnr.missioni.model.user.Anagrafica.Genere;
import it.cnr.missioni.model.user.DatiCNR.LivelloUserEnum;
import org.joda.time.DateTime;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * @author Salvia Vito
 */
public class UserTest {

    private static Validator validator;

    @BeforeClass
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void anagraficaErrataTest() {
        User user = createUser();
        Anagrafica anagrafica = new Anagrafica();
        anagrafica.setCodiceFiscale("sl");
        DateTime now = new DateTime();
        DateTime d = now.plusDays(1);
        anagrafica.setDataNascita(d);
        user.setAnagrafica(anagrafica);
        Set<ConstraintViolation<Anagrafica>> constraintViolations = validator.validate(anagrafica);
        assertEquals(6, constraintViolations.size());
    }

    @Test
    public void anagraficaOkTest() {
        User user = createUser();
        Anagrafica anagrafica = new Anagrafica();
        anagrafica.setCodiceFiscale("slvvti82l30g942l");
        anagrafica.setCognome("Salvia");
        anagrafica.setNome("Vito");
        anagrafica.setLuogoNascita("Potenza");
        anagrafica.setGenere(Genere.UOMO);
        DateTime now = new DateTime();
        DateTime d = now.minusDays(1);
        anagrafica.setDataNascita(d);
        user.setAnagrafica(anagrafica);
        Set<ConstraintViolation<Anagrafica>> constraintViolations = validator.validate(anagrafica);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void credenzialiErrataTest() {
        User user = createUser();
        user.setCredenziali(new Credenziali());
        Set<ConstraintViolation<Credenziali>> constraintViolations = validator.validate(user.getCredenziali());
        assertEquals(2, constraintViolations.size());
    }

    @Test
    public void credenzialiOkTest() {
        User user = createUser();
        Set<ConstraintViolation<Credenziali>> constraintViolations = validator.validate(user.getCredenziali());
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void residenzaErrataTest() {
        User user = createUser();
        user.setResidenza(new Residenza());
        Set<ConstraintViolation<Residenza>> constraintViolations = validator.validate(user.getResidenza());
        assertEquals(2, constraintViolations.size());
    }

    @Test
    public void residenzaOkTest() {
        User user = createUser();

        Set<ConstraintViolation<Residenza>> constraintViolations = validator.validate(user.getResidenza());
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void patenteErrataTest() {
        User user = createUser();
        Patente patente = new Patente();
        DateTime now = new DateTime();
        DateTime d = now.plusDays(1);
        patente.setDataRilascio(d);
        patente.setValidaFinoAl(now.minusDays(1));
        user.setPatente(patente);
        Set<ConstraintViolation<Patente>> constraintViolations = validator.validate(user.getPatente());
        assertEquals(4, constraintViolations.size());
    }

    @Test
    public void patenteOkTest() {
        User user = createUser();
        Set<ConstraintViolation<Patente>> constraintViolations = validator.validate(user.getPatente());
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void datiCNRErrataTest() {
        User user = createUser();
        user.setDatiCNR(new DatiCNR());
        Set<ConstraintViolation<DatiCNR>> constraintViolations = validator.validate(user.getDatiCNR());
        assertEquals(6, constraintViolations.size());
    }

    @Test
    public void datiCNROkTest() {
        User user = createUser();
        Set<ConstraintViolation<DatiCNR>> constraintViolations = validator.validate(user.getDatiCNR());
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void veicoloErrataTest() {
        Veicolo veicolo = new Veicolo();
        Set<ConstraintViolation<Veicolo>> constraintViolations = validator.validate(veicolo);
        assertEquals(4, constraintViolations.size());
    }

    @Test
    public void veicoloOkTest() {
        Veicolo veicolo = new Veicolo();
        veicolo.setId("aaaaqqqq");
        veicolo.setTipo("Ford");
        veicolo.setTarga("AA111BBB");
        veicolo.setCartaCircolazione("carta");
        veicolo.setPolizzaAssicurativa("polizza");
        Set<ConstraintViolation<Veicolo>> constraintViolations = validator.validate(veicolo);
        assertEquals(0, constraintViolations.size());
    }

    private User createUser(){
        return new User(){
            {
                Anagrafica anagrafica = null;
                Credenziali credenziali = null;
                super.setId("01");
                anagrafica = new Anagrafica();
                anagrafica.setCognome("Salvia");
                anagrafica.setNome("Vito");
                anagrafica.setDataNascita(new DateTime(1982, 7, 30, 0, 0));
                anagrafica.setCodiceFiscale("slvvttttttttttt");
                anagrafica.setLuogoNascita("Potenza");
                credenziali = new Credenziali();
                credenziali.setUsername("vito.salvia");
                credenziali.setRuoloUtente(RuoloUserEnum.UTENTE_SEMPLICE);
                credenziali.setPassword(("vitosalvia"));
                super.setCredenziali(credenziali);
                super.setAnagrafica(anagrafica);
                Veicolo veicolo = new Veicolo();
                veicolo.setTipo("Ford Fiesta");
                veicolo.setTarga("AA111BB");
                veicolo.setCartaCircolazione("12234");
                veicolo.setPolizzaAssicurativa("A1B2");
                veicolo.setVeicoloPrincipale(true);
                Map<String, Veicolo> mappaVeicoli = new HashMap<String, Veicolo>();
                mappaVeicoli.put(veicolo.getTarga(), veicolo);
                super.setMappaVeicolo(mappaVeicoli);
                DatiCNR datiCNR = new DatiCNR();
                datiCNR.setIban("IT0000000000000000");
                datiCNR.setLivello(LivelloUserEnum.V);
                datiCNR.setMail("vito.salvia@gmail.com");
                datiCNR.setMatricola("1111111");
                datiCNR.setDescrizioneQualifica("Ricercatore");
                datiCNR.setCodiceTerzo("123");
                datiCNR.setIdQualifica("01");
                super.setDatiCNR(datiCNR);
                Patente p = new Patente();
                p.setDataRilascio(new DateTime(2001, 12, 15, 0, 0));
                p.setNumeroPatente("12334");
                p.setRilasciataDa("MCTC");
                p.setValidaFinoAl(new DateTime(2021, 12, 15, 0, 0));
                super.setPatente(p);
                Residenza r = new Residenza();
                r.setIndirizzo("Via Verdi");
                r.setComune("Tito");
                r.setDomicilioFiscale("Via Convento");
                super.setResidenza(r);
                super.setDataRegistrazione(new DateTime(2015, 1, 4, 0, 0));
                super.setDateLastModified(new DateTime(2015, 1, 4, 0, 0));
                super.setRegistrazioneCompletata(true);
            }
        };
    }

}
