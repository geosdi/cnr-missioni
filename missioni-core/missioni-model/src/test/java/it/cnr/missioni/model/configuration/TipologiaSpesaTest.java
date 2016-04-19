package it.cnr.missioni.model.configuration;

import it.cnr.missioni.model.configuration.TipologiaSpesa;
import it.cnr.missioni.model.missione.TrattamentoMissioneEsteraEnum;
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
public class TipologiaSpesaTest {

    private static Validator validator;

    @BeforeClass
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void fatturaErrataTest() {
        TipologiaSpesa tipologiaSpesa = new TipologiaSpesa();
        Set<ConstraintViolation<TipologiaSpesa>> constraintViolations = validator.validate(tipologiaSpesa);
        assertEquals(3, constraintViolations.size());
    }

    @Test
    public void fatturaOkTest() {
        TipologiaSpesa tipologiaSpesa = createTipologiaSpesa();
        Set<ConstraintViolation<TipologiaSpesa>> constraintViolations = validator.validate(tipologiaSpesa);
        assertEquals(0, constraintViolations.size());
    }

    private TipologiaSpesa createTipologiaSpesa(){
        return new TipologiaSpesa(){
            {
                super.setValue("VITTO");
                super.setVoceSpesa(VoceSpesaEnum.PASTO);
                super.setTipoTrattamento(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);            }
        };
    }
}
