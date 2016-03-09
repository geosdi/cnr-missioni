package it.cnr.missioni.dashboard.component.window;

import org.vaadin.teemu.wizards.Wizard;
import org.vaadin.teemu.wizards.event.WizardProgressListener;

import it.cnr.missioni.dashboard.event.DashboardEventBus;
import it.cnr.missioni.dashboard.event.DashboardEvent.CloseOpenWindowsEvent;
import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.model.user.User;

/**
 * @author Salvia Vito
 */
public interface IWizard {
	
	void build();
	
	Wizard getWizard();
	
	void setMissione(Missione missione);
	
	void setUser(User user);
	
	void setModifica(boolean modifica);
	
	void setEnabled(boolean enabled);
	
	void isAdmin(boolean enambled);
	
	
	public abstract class AbstractWizard implements WizardProgressListener,  IWizard{

		private Wizard wizard;
		protected boolean enabled;
		protected boolean modifica;
		protected boolean isAdmin;

		protected void endWizard() {
			getWizard().setVisible(false);
			DashboardEventBus.post(new CloseOpenWindowsEvent());
		}
		
		protected void buildWizard(){
			setWizard(new Wizard());
			getWizard().setUriFragmentEnabled(true);
			getWizard().setVisible(true);
			getWizard().addListener(this);
			getWizard().getBackButton().setCaption("Indietro");
			getWizard().getCancelButton().setCaption("Cancella");
			getWizard().getNextButton().setCaption("Avanti");
			getWizard().getFinishButton().setCaption("Concludi");
			getWizard().setWidth("100%");
			getWizard().setHeight("95%");
		}

		/**
		 * @return the wizard
		 */
		public Wizard getWizard() {
			return wizard;
		}

		/**
		 * @param wizard 
		 */
		public void setWizard(Wizard wizard) {
			this.wizard = wizard;
		}
		
		public abstract void setMissione(Missione missione);
		
		public abstract void setUser(User user);
		
		public abstract void setEnabled(boolean enabled);
		
		public abstract void isAdmin(boolean isAdmin);
		
		public abstract void setModifica(boolean modifica);


	}


}
