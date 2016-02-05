package it.cnr.missioni.dashboard.action.admin;

import com.vaadin.ui.Notification.Type;

import it.cnr.missioni.dashboard.action.IAction;
import it.cnr.missioni.dashboard.client.ClientConnector;
import it.cnr.missioni.dashboard.event.DashboardEvent;
import it.cnr.missioni.dashboard.event.DashboardEventBus;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.el.model.search.builder.TipologiaSpesaSearchBuilder;
import it.cnr.missioni.model.configuration.TipologiaSpesa;
import it.cnr.missioni.rest.api.response.tipologiaSpesa.TipologiaSpesaStore;

/**
 * @author Salvia Vito
 */
public class TipologiaSpesaAction implements IAction {

	private TipologiaSpesa tipologiaSpesa;
	private boolean modifica;
	
	public TipologiaSpesaAction(TipologiaSpesa tipologiaSpesa ,boolean modifica ){
		this.tipologiaSpesa =  tipologiaSpesa;
		this.modifica = modifica;
	}


	public boolean doAction() {

		try {
			
			if(modifica)
				ClientConnector.updateTipologiaSpesa(tipologiaSpesa);
			else
				ClientConnector.addTipologiaSpesa(tipologiaSpesa);
			Thread.sleep(1000);
			
			
			Utility.getNotification(Utility.getMessage("success_message"),null,
					Type.HUMANIZED_MESSAGE);
				
			//ricarico  le tipologie
			TipologiaSpesaStore nazioneStore = ClientConnector.getTipologiaSpesa(TipologiaSpesaSearchBuilder.getTipologiaSpesaSearchBuilder());
			DashboardEventBus.post(new  DashboardEvent.TableTipologiaSpesaUpdatedEvent(nazioneStore) );
			return true;

		} catch (Exception e) {
			Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
					Type.ERROR_MESSAGE);
			return false;
		}

	}

}
