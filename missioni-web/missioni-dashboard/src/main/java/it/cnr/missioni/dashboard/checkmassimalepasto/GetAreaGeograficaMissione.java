package it.cnr.missioni.dashboard.checkmassimalepasto;

import it.cnr.missioni.dashboard.client.ClientConnector;
import it.cnr.missioni.el.model.search.builder.NazioneSearchBuilder;
import it.cnr.missioni.model.configuration.Nazione;
import it.cnr.missioni.model.configuration.Nazione.AreaGeograficaEnum;

/**
 * 
 * Recupere l'area geografica della missione
 * 
 * @author Salvia Vito
 */
public class GetAreaGeograficaMissione extends IControlCheckMassimale.AbstractControlCheckMassimale {

    protected GetAreaGeograficaMissione() {
    }

    public static IControlCheckMassimale newGetAreaGeografica() {
        return new GetAreaGeograficaMissione();
    }
	
	/**
	 * @throws Exception 
	 * 
	 */
	@Override
	public void check() throws Exception {
		if (checkMassimale.getFattura().getData().isBefore(checkMassimale.getMissione().getDatiMissioneEstera().getAttraversamentoFrontieraAndata())
				|| checkMassimale.getFattura().getData().isAfter(checkMassimale.getMissione().getDatiMissioneEstera().getAttraversamentoFrontieraRitorno()) || !checkMassimale.getMissione().isMissioneEstera())
			checkMassimale.setAreaGeografica(AreaGeograficaEnum.ITALIA.name());
		else {
			Nazione nazione = ClientConnector
					.getNazione(NazioneSearchBuilder.getNazioneSearchBuilder().withId(checkMassimale.getMissione().getIdNazione()))
					.getNazione().get(0);
			checkMassimale.setAreaGeografica(nazione.getAreaGeografica().name());
			}
		this.nextControl.check();

	}
}
