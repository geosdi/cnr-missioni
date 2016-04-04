package it.cnr.missioni.dashboard.component.window;

import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

import it.cnr.missioni.dashboard.action.UpdateUserAction;
import it.cnr.missioni.dashboard.client.ClientConnector;
import it.cnr.missioni.dashboard.component.form.user.AnagraficaUserForm;
import it.cnr.missioni.dashboard.component.form.user.DatiCNRUserForm;
import it.cnr.missioni.dashboard.component.form.user.PatenteUserForm;
import it.cnr.missioni.dashboard.component.form.user.ResidenzaUserForm;
import it.cnr.missioni.dashboard.event.DashboardEvent.CloseOpenWindowsEvent;
import it.cnr.missioni.dashboard.event.DashboardEventBus;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.el.model.search.builder.IMassimaleSearchBuilder;
import it.cnr.missioni.model.configuration.Nazione.AreaGeograficaEnum;
import it.cnr.missioni.model.missione.TrattamentoMissioneEsteraEnum;
import it.cnr.missioni.model.user.Anagrafica;
import it.cnr.missioni.model.user.DatiCNR;
import it.cnr.missioni.model.user.Patente;
import it.cnr.missioni.model.user.Residenza;
import it.cnr.missioni.model.user.User;
import it.cnr.missioni.rest.api.response.massimale.MassimaleStore;

public class UserWindow extends IWindow.AbstractWindow {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9064172202629030178L;

	public static final String ID = "profilepreferenceswindow";
	private User user;
	private AnagraficaUserForm anagraficaForm;
	private DatiCNRUserForm datiCNRTabLayout;
	private PatenteUserForm patenteTablLayout;
	private ResidenzaUserForm residenzaTabLayout;

	private UserWindow(final User user,boolean isAdmin,boolean enabled, final boolean modifica) {
		super(isAdmin,enabled,modifica);
		this.user = user;
		setId(ID);
		build();
		buildTabs();
		if (!isAdmin)
			content.addComponent(buildFooter());

	}

	private void buildTabs() {
		buildTabAnagrafica();
		buildTabPatente();
		buildTabResidenza();
		buildTabDatiCNR();
		detailsWrapper.addComponent(buildMassimaleTab());
	}

	private void buildTabAnagrafica() {
		this.anagraficaForm = new AnagraficaUserForm(user, false, true, true);
		detailsWrapper.addComponent(buildTab("Anagrafica", FontAwesome.USER, this.anagraficaForm));

	}

	private void buildTabPatente() {
		this.patenteTablLayout = new PatenteUserForm(user, false, true, true);
		detailsWrapper.addComponent(buildTab("Patente", FontAwesome.CAR, this.patenteTablLayout));

	}

	private void buildTabResidenza() {
		this.residenzaTabLayout = new ResidenzaUserForm(user, false, true, true);
		detailsWrapper.addComponent(buildTab("Residenza", FontAwesome.HOME, this.residenzaTabLayout));

	}

	private void buildTabDatiCNR() {
		this.datiCNRTabLayout = new DatiCNRUserForm(user, false, true, true);
		detailsWrapper.addComponent(buildTab("Dati CNR", FontAwesome.INSTITUTION, this.datiCNRTabLayout));

	}

	private Component buildMassimaleTab() {
		HorizontalLayout root = new HorizontalLayout();
		root.setCaption("Info Massimali");
		root.setIcon(FontAwesome.EURO);
		root.setWidth(100.0f, Unit.PERCENTAGE);
		root.setSpacing(true);
		root.setMargin(true);

		FormLayout details = new FormLayout();
		details.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
		root.addComponent(details);
		root.setExpandRatio(details, 1);

		for (AreaGeograficaEnum a : AreaGeograficaEnum.values()) {
			try {

				double massimaleTAM = 0.0;
				double massimaleRimborsoDocumentato = 0.0;

				MassimaleStore massimaleStore = ClientConnector
						.getMassimale(IMassimaleSearchBuilder.MassimaleSearchBuilder.getMassimaleSearchBuilder()
								.withLivello(user.getDatiCNR().getLivello().name()).withAreaGeografica(a.name())
								.withTipo(TrattamentoMissioneEsteraEnum.TRATTAMENTO_ALTERNATIVO.name()));
				if (massimaleStore.getTotale() > 0)
					massimaleTAM = massimaleStore.getMassimale().get(0).getValue();

				massimaleStore = ClientConnector.getMassimale(IMassimaleSearchBuilder.MassimaleSearchBuilder.getMassimaleSearchBuilder()
						.withLivello(user.getDatiCNR().getLivello().name()).withAreaGeografica(a.name())
						.withTipo(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO.name()));
				if (massimaleStore.getTotale() > 0)
					massimaleRimborsoDocumentato = massimaleStore.getMassimale().get(0).getValue();
				details.addComponent(new Label("<b>Area geografica: </b>" + a.name() + " <b>TAM:</b> "
						+ Double.toString(massimaleTAM) + " € " + "<b>Rimborso documentato:</b> "
						+ Double.toString(massimaleRimborsoDocumentato) + " €", ContentMode.HTML));

			} catch (Exception e) {
				Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
						Type.ERROR_MESSAGE);
			}
		}

		return root;
	}

	protected Component buildFooter() {


		ok.addStyleName(ValoTheme.BUTTON_PRIMARY);
		ok.addClickListener(new ClickListener() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 5281413805855055939L;

			@Override
			public void buttonClick(ClickEvent event) {
				try {
					Anagrafica anagrafica = anagraficaForm.validate();
					Patente patente = patenteTablLayout.validate();
					DatiCNR datiCNR = datiCNRTabLayout.validate();
					Residenza residenza = residenzaTabLayout.validate();

					user.setAnagrafica(anagrafica);
					user.setPatente(patente);
					user.setDatiCNR(datiCNR);
					user.setResidenza(residenza);
					DashboardEventBus.post(new UpdateUserAction(user));
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

	public static void open(final User user, final boolean isAdmin,final boolean enabled,final boolean modifica) {
		DashboardEventBus.post(new CloseOpenWindowsEvent());
		Window w = new UserWindow(user, isAdmin,enabled,modifica);
		UI.getCurrent().addWindow(w);
		w.focus();
	}

}
