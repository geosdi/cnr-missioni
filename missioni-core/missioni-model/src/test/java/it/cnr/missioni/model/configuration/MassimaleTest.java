package it.cnr.missioni.model.configuration;

import it.cnr.missioni.model.configuration.Massimale;
import it.cnr.missioni.model.configuration.Nazione.AreaGeograficaEnum;
import it.cnr.missioni.model.missione.TrattamentoMissioneEsteraEnum;
import it.cnr.missioni.model.user.DatiCNR.LivelloUserEnum;
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
public class MassimaleTest {

    private static Validator validator;

    @BeforeClass
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void fatturaErrataTest() {
        Massimale massimale = new Massimale();
        Set<ConstraintViolation<Massimale>> constraintViolations = validator.validate(massimale);
        assertEquals(5, constraintViolations.size());
    }

    @Test
    public void fatturaOkTest() {
        Massimale massimale = createMassimale();
        Set<ConstraintViolation<Massimale>> constraintViolations = validator.validate(massimale);
        assertEquals(0, constraintViolations.size());
    }

    private Massimale createMassimale(){
        return new Massimale(){
            {
                super.setId("01");
                super.setValue(new Double(60));
                super.setValueMezzaGiornata(new Double(60));
                super.setAreaGeografica(AreaGeograficaEnum.A);
                super.setLivello(LivelloUserEnum.I);
                super.setDescrizione("Massimale 1");
                super.setTipo(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
            }
        };
    }

}
