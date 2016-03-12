package it.cnr.missioni.dashboard.component.window.admin;

import org.joda.time.Days;

import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

import it.cnr.missioni.dashboard.DashboardUI;
import it.cnr.missioni.dashboard.action.admin.UpdateRimborsoAction;
import it.cnr.missioni.dashboard.client.ClientConnector;
import it.cnr.missioni.dashboard.component.form.rimborso.DatiGeneraliRimborsoForm;
import it.cnr.missioni.dashboard.component.form.rimborso.DatiPeriodoEsteraMissioneForm;
import it.cnr.missioni.dashboard.component.form.rimborso.FatturaRimborsoForm;
import it.cnr.missioni.dashboard.component.window.IWindow;
import it.cnr.missioni.dashboard.event.DashboardEvent.CloseOpenWindowsEvent;
import it.cnr.missioni.dashboard.event.DashboardEventBus;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.el.model.search.builder.MassimaleSearchBuilder;
import it.cnr.missioni.el.model.search.builder.NazioneSearchBuilder;
import it.cnr.missioni.model.configuration.Nazione;
import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.model.missione.TrattamentoMissioneEsteraEnum;
import it.cnr.missioni.model.rimborso.Rimborso;
import it.cnr.missioni.rest.api.response.massimale.MassimaleStore;

public class RimborsoWindowAdmin extends IWindow.AbstractWindow {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8551406658251796691L;

	public static final String ID = "rimborsoadminwindow";


	private Rimborso rimborso;
	private Missione missione;
	
	private DatiGeneraliRimborsoForm datiGeneraliForm;
	private FatturaRimborsoForm fatturaForm;
	private DatiPeriodoEsteraMissioneForm datiPeriodoEsteraMissioneForm;


	private RimborsoWindowAdmin(final Missione missione,boolean isAdmin,boolean enabled,boolean modifica) {
		super(isAdmin,enabled,modifica);
		this.missione = missione;
		this.rimborso = missione.getRimborso();
		addStyleName("profile-window");
		setId(ID);
		Responsive.makeResponsive(this);

		build();
		buildTabs();
		if (enabled)
			content.addComponent(buildFooter());
	}

	private void buildTabs() {
		buildRimborsoTab();
		if(missione.isMissioneEstera())
			buildDatiEsteriTab();
		buildFatturaTab();

	}


	private void buildRimborsoTab() {		
		this.datiGeneraliForm = new  DatiGeneraliRimborsoForm(missione,isAdmin, enabled,modifica);
		detailsWrapper.addComponent(buildTab("Generale", FontAwesome.GEAR, this.datiGeneraliForm ));

	}



	private void buildFatturaTab() {
		this.fatturaForm =  new FatturaRimborsoForm(missione,isAdmin,enabled,modifica);
		detailsWrapper.addComponent(buildTab("Fattura", FontAwesome.EURO, this.fatturaForm ));

	}
	
	private void buildDatiEsteriTab() {
		this.datiPeriodoEsteraMissioneForm =  new DatiPeriodoEsteraMissioneForm(missione.getDatiMissioneEstera(),isAdmin,enabled,modifica,missione);
		detailsWrapper.addComponent(buildTab("Dati Missione Estera", FontAwesome.CALENDAR, this.datiPeriodoEsteraMissioneForm ));

	}


	protected Component buildFooter() {

		ok.addStyleName(ValoTheme.BUTTON_PRIMARY);
		ok.addClickListener(new ClickListener() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 127413665855903412L;

			@Override
			public void buttonClick(ClickEvent event) {
				try{
				Rimborso r = datiGeneraliForm.validate();
				missione.setRimborso(r);
				DashboardEventBus.post(new UpdateRimborsoAction(missione));
				close();
				} catch (InvalidValueException | CommitException e) {
					Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("commit_failed"),
							Type.ERROR_MESSAGE);
				}

			}
		});
		ok.focus();
		footer.addComponent(ok);
		footer.setComponentAlignment(ok, Alignment.TOP_RIGHT);
		return footer;
	}

	public static void open(final Missione missione,boolean isAdmin,boolean enabled,boolean modifica) {
		DashboardEventBus.post(new CloseOpenWindowsEvent());
		Window w = new RimborsoWindowAdmin(missione,isAdmin,enabled,modifica);
		UI.getCurrent().addWindow(w);
		w.focus();
	}

}
