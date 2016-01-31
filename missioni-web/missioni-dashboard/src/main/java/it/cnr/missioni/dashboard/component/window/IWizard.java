package it.cnr.missioni.dashboard.component.window;

import org.vaadin.teemu.wizards.Wizard;

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

}
