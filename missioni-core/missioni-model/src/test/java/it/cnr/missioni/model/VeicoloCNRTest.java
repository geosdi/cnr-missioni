package it.cnr.missioni.model;

import it.cnr.missioni.model.prenotazione.StatoVeicoloEnum;
import it.cnr.missioni.model.prenotazione.VeicoloCNR;
import it.cnr.missioni.model.user.Veicolo;
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
public class VeicoloCNRTest {

    private static Validator validator;

    @BeforeClass
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void veicoloErrataTest() {
        VeicoloCNR veicoloCNR = new VeicoloCNR();
        Set<ConstraintViolation<VeicoloCNR>> constraintViolations = validator.validate(veicoloCNR);
        assertEquals(6, constraintViolations.size());
    }

    @Test
    public void veicoloOkTest() {
        VeicoloCNR veicoloCNR = createVeicoloCnr();
        Set<ConstraintViolation<Veicolo>> constraintViolations = validator.validate(veicoloCNR);
        assertEquals(0, constraintViolations.size());
    }

    private VeicoloCNR createVeicoloCnr(){
        return new VeicoloCNR(){
            {
                super.setId("01");
                super.setTipo("Ford");
                super.setTarga("AA111BBB");
                super.setCartaCircolazione("carta");
                super.setPolizzaAssicurativa("polizza");
                super.setStato(StatoVeicoloEnum.DISPONIBILE);
            }
        };
    }

}
