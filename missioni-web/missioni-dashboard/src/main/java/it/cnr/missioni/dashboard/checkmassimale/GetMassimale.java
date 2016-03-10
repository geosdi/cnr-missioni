package it.cnr.missioni.dashboard.checkmassimale;

import it.cnr.missioni.dashboard.client.ClientConnector;
import it.cnr.missioni.el.model.search.builder.MassimaleSearchBuilder;
import it.cnr.missioni.model.missione.TrattamentoMissioneEsteraEnum;
import it.cnr.missioni.rest.api.response.massimale.MassimaleStore;

/**
 * @author Salvia Vito
 */
public class GetMassimale extends IControlCheckMassimale.AbstractControlCheckMassimale {

    protected GetMassimale() {
    }

    public static IControlCheckMassimale newGetMassimale() {
        return new GetMassimale();
    }
	
	/**
	 * @throws Exception 
	 * 
	 */
	@Override
	public void check() throws Exception {
		MassimaleStore massimaleStore = ClientConnector.getMassimale(MassimaleSearchBuilder
				.getMassimaleSearchBuilder().withLivello(checkMassimale.getLivello()).withAreaGeografica(checkMassimale.getAreaGeografica())
				.withTipo(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO.name()));
		if(massimaleStore.getTotale() > 0){
			this.checkMassimale.setMassimale(massimaleStore.getMassimale().get(0));
			this.nextControl.check();
		}

	}

}
