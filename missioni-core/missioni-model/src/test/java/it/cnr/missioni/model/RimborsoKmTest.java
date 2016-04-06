package it.cnr.missioni.model;

import it.cnr.missioni.model.configuration.RimborsoKm;
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
public class RimborsoKmTest {

    private static Validator validator;

    @BeforeClass
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void fatturaErrataTest() {
        RimborsoKm rimborsoKm = createRimborsoKM();
        rimborsoKm.setValue(null);
        Set<ConstraintViolation<RimborsoKm>> constraintViolations = validator.validate(rimborsoKm);
        assertEquals(1, constraintViolations.size());
    }

    @Test
    public void fatturaOkTest() {
        RimborsoKm rimborsoKm = createRimborsoKM();
        Set<ConstraintViolation<RimborsoKm>> constraintViolations = validator.validate(rimborsoKm);
        assertEquals(0, constraintViolations.size());
    }

    private RimborsoKm createRimborsoKM(){
        return new RimborsoKm(){
            {
                super.setValue(0.36);
            }
        };
    }
}
