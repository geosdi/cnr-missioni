package it.cnr.missioni.dashboard.checkmassimalepasto;

import it.cnr.missioni.dashboard.client.ClientConnector;
import it.cnr.missioni.el.model.search.builder.TipologiaSpesaSearchBuilder;
import it.cnr.missioni.model.configuration.TipologiaSpesa.VoceSpesaEnum;
import it.cnr.missioni.rest.api.response.tipologiaSpesa.TipologiaSpesaStore;

/**
 * 
 * Recupere l'user della missione
 * 
 * @author Salvia Vito
 */
public class CheckTipoFattura extends IControlCheckMassimale.AbstractControlCheckMassimale {

    protected CheckTipoFattura() {
    }

    public static IControlCheckMassimale newCheckTipoFattura() {
        return new CheckTipoFattura();
    }
	
	/**
	 * @throws Exception 
	 * 
	 */
	@Override
	public void check() throws Exception {
		TipologiaSpesaStore tipologiaSpesaStore = ClientConnector.getTipologiaSpesa(TipologiaSpesaSearchBuilder
				.getTipologiaSpesaSearchBuilder().withId(this.checkMassimale.getFattura().getIdTipologiaSpesa()));
		if(tipologiaSpesaStore.getTotale() > 0 && tipologiaSpesaStore.getTipologiaSpesa().get(0).getVoceSpesa() == VoceSpesaEnum.PASTO){
			this.nextControl.check();
		}

	}

}
