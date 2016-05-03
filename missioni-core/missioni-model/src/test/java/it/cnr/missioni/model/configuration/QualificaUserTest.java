package it.cnr.missioni.model.configuration;

import it.cnr.missioni.model.configuration.QualificaUser;
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
public class QualificaUserTest {

    private static Validator validator;

    @BeforeClass
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void fatturaErrataTest() {
        QualificaUser qualificaUser = createQualificaUser();
        qualificaUser.setValue(" ");
        Set<ConstraintViolation<QualificaUser>> constraintViolations = validator.validate(qualificaUser);
        assertEquals(1, constraintViolations.size());
    }

    @Test
    public void fatturaOkTest() {
        QualificaUser qualificaUser = createQualificaUser();
        Set<ConstraintViolation<QualificaUser>> constraintViolations = validator.validate(qualificaUser);
        assertEquals(0, constraintViolations.size());
    }

    private QualificaUser createQualificaUser(){
        return new QualificaUser(){
            {
                super.setValue("Ricercatore");
            }
        };
    }

}
