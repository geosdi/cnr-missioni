package it.cnr.missioni.model.prenotazione;

import it.cnr.missioni.model.prenotazione.Prenotazione;
import org.joda.time.DateTime;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * @author Salvia Vito
 */
public class PrenotazioneTest {

    private static Validator validator;

    @BeforeClass
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void prenotazioneErrataTest() {
        Prenotazione prenotazione = new Prenotazione();
        Set<ConstraintViolation<Prenotazione>> constraintViolations = validator.validate(prenotazione);
        assertEquals(5, constraintViolations.size());
    }

    @Test
    public void prenotazioneOkTest() {
        Prenotazione prenotazione = createPrenotazione();
        Set<ConstraintViolation<Prenotazione>> constraintViolations = validator.validate(prenotazione);
        assertEquals(0, constraintViolations.size());
    }

    private Prenotazione createPrenotazione(){
        return new Prenotazione(){
            {
                super.setDataFrom(new DateTime());
                super.setDataTo(new DateTime());
                super.setIdVeicoloCNR("1");
                super.setLocalita("Roma");
                super.setDescriptionVeicoloCNR("Ford");
            }
        };
    }

}
