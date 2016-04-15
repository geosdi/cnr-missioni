package it.cnr.missioni.model;

import it.cnr.missioni.model.rimborso.Fattura;
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
public class FatturaTest {

    private static Validator validator;

    @BeforeClass
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void fatturaErrataTest() {
        Fattura fattura = new Fattura();
        Set<ConstraintViolation<Fattura>> constraintViolations = validator.validate(fattura);
        assertEquals(6, constraintViolations.size());
    }

    @Test
    public void fatturaOkTest() {
        Fattura fattura = createFattura();
        Set<ConstraintViolation<Fattura>> constraintViolations = validator.validate(fattura);
        assertEquals(0, constraintViolations.size());
    }

    private Fattura createFattura(){
        return new Fattura(){
            {
                super.setNumeroFattura("1");
                super.setData(new DateTime());
                super.setImporto(25.00);
                super.setImportoSpettante(25.00);
                super.setValuta("Euro");
                super.setIdTipologiaSpesa("01");
            }
        };
    }

}
