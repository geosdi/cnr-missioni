package it.cnr.missioni.model;

import it.cnr.missioni.model.configuration.Nazione;
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
public class NazioneTest {

    private static Validator validator;

    @BeforeClass
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void fatturaErrataTest() {
        Nazione nazione = new Nazione();
        Set<ConstraintViolation<Nazione>> constraintViolations = validator.validate(nazione);
        assertEquals(2, constraintViolations.size());
    }

    @Test
    public void fatturaOkTest() {
        Nazione nazione = createNazione();
        Set<ConstraintViolation<Nazione>> constraintViolations = validator.validate(nazione);
        assertEquals(0, constraintViolations.size());
    }

    private Nazione createNazione(){
        return new Nazione(){
            {
                super.setValue("GERMANIA");
                super.setAreaGeografica(AreaGeograficaEnum.G);
            }
        };
    }

}
