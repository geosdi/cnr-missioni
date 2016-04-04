package it.cnr.missioni.el.utility;

import java.util.ArrayList;
import java.util.List;

import it.cnr.missioni.model.configuration.TipologiaSpesa;
import it.cnr.missioni.model.configuration.TipologiaSpesa.VoceSpesaEnum;
import it.cnr.missioni.model.missione.TrattamentoMissioneEsteraEnum;

/**
 * @author Salvia Vito
 */
public class TipologiaSpesaFunction{


	public static List<TipologiaSpesa> creaMassiveTipologiaSpesa(){
		
		List<TipologiaSpesa> listaTipoligiaSpesa = new ArrayList<TipologiaSpesa>();
		
		TipologiaSpesa tipoliogiaSpesa = new TipologiaSpesa();
		tipoliogiaSpesa.setId("01");
		tipoliogiaSpesa.setValue("Aereo");
		tipoliogiaSpesa.setItalia(false);
		tipoliogiaSpesa.setEstera(true);
		tipoliogiaSpesa.setTipoTrattamento(TrattamentoMissioneEsteraEnum.TRATTAMENTO_ALTERNATIVO);
		tipoliogiaSpesa.setVoceSpesa(VoceSpesaEnum.TRASPORTO);
		listaTipoligiaSpesa.add(tipoliogiaSpesa);

		tipoliogiaSpesa = new TipologiaSpesa();
		tipoliogiaSpesa.setId("02");
		tipoliogiaSpesa.setValue("Vitto");
		tipoliogiaSpesa.setItalia(true);
		tipoliogiaSpesa.setEstera(false);
		tipoliogiaSpesa.setItalia(true);
		tipoliogiaSpesa.setTipoTrattamento(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
		tipoliogiaSpesa.setVoceSpesa(VoceSpesaEnum.PASTO);

		listaTipoligiaSpesa.add(tipoliogiaSpesa);

		tipoliogiaSpesa = new TipologiaSpesa();
		tipoliogiaSpesa.setId("03");
		tipoliogiaSpesa.setValue("Albergo");
		tipoliogiaSpesa.setItalia(false);
		tipoliogiaSpesa.setEstera(true);
		tipoliogiaSpesa.setTipoTrattamento(TrattamentoMissioneEsteraEnum.TRATTAMENTO_ALTERNATIVO);
		tipoliogiaSpesa.setVoceSpesa(VoceSpesaEnum.ALLOGGIO);
		listaTipoligiaSpesa.add(tipoliogiaSpesa);

		tipoliogiaSpesa = new TipologiaSpesa();
		tipoliogiaSpesa.setId("04");
		tipoliogiaSpesa.setValue("Treno");
		tipoliogiaSpesa.setItalia(true);
		tipoliogiaSpesa.setEstera(false);
		tipoliogiaSpesa.setTipoTrattamento(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
		tipoliogiaSpesa.setVoceSpesa(VoceSpesaEnum.TRASPORTO);
		listaTipoligiaSpesa.add(tipoliogiaSpesa);
		
		return listaTipoligiaSpesa;
	}


}
