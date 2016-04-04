package it.cnr.missioni.el.utility;

import java.util.ArrayList;
import java.util.List;

import it.cnr.missioni.model.prenotazione.StatoVeicoloEnum;
import it.cnr.missioni.model.prenotazione.VeicoloCNR;

/**
 * @author Salvia Vito
 */
public class VeicoloCNRFunction{


	public static List<VeicoloCNR> creaMassiveVeicoloCNR(){
		
		List<VeicoloCNR> listaVeicoliCNR = new ArrayList<VeicoloCNR>();
		
		VeicoloCNR veicoloCNR = new VeicoloCNR();
		veicoloCNR.setId("01");
		veicoloCNR.setCartaCircolazione("Carta 123");
		veicoloCNR.setPolizzaAssicurativa("polizza 1");
		veicoloCNR.setTipo("Citroen");
		veicoloCNR.setTarga("56654");
		veicoloCNR.setStato(StatoVeicoloEnum.DISPONIBILE);
		listaVeicoliCNR.add(veicoloCNR);

		veicoloCNR = new VeicoloCNR();
		veicoloCNR.setId("02");
		veicoloCNR.setCartaCircolazione("Carta 456");
		veicoloCNR.setPolizzaAssicurativa("polizza 2");
		veicoloCNR.setTipo("Peugeout");
		veicoloCNR.setTarga("6575");
		veicoloCNR.setStato(StatoVeicoloEnum.NON_DISPONIBILE);
		listaVeicoliCNR.add(veicoloCNR);
		
		return listaVeicoliCNR;
	}


}
