package it.cnr.missioni.dashboard.component.window;

import org.joda.time.DateTime;

import com.vaadin.data.Validator;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.fieldgroup.FieldGroupFieldFactory;
import com.vaadin.data.util.BeanItem;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Field;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

import it.cnr.missioni.dashboard.action.MissioneAction;
import it.cnr.missioni.dashboard.component.table.ElencoMissioniTable;
import it.cnr.missioni.dashboard.event.DashboardEvent.CloseOpenWindowsEvent;
import it.cnr.missioni.dashboard.event.DashboardEventBus;
import it.cnr.missioni.dashboard.utility.BeanFieldGrouFactory;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.model.missione.TrattamentoMissioneEsteraEnum;
import it.cnr.missioni.model.user.User;

public class DettagliMissioneWindow extends Window {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8010154636177339477L;

	public static final String ID = "detttaglimissionewindow";



	private TextField distanzaField;
	private final Missione missione;

	private DettagliMissioneWindow(final Missione missione) {

		this.missione = missione;

		addStyleName("profile-window");
		setId(ID);
		Responsive.makeResponsive(this);

		setModal(true);
		setCloseShortcut(KeyCode.ESCAPE, null);
		setResizable(false);
		setClosable(true);
		setHeight(90.0f, Unit.PERCENTAGE);

		VerticalLayout content = new VerticalLayout();
		content.setSizeFull();
		content.setMargin(new MarginInfo(true, false, false, false));
		setContent(content);

		TabSheet detailsWrapper = new TabSheet();
		detailsWrapper.setSizeFull();
		detailsWrapper.addStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);
		detailsWrapper.addStyleName(ValoTheme.TABSHEET_ICONS_ON_TOP);
		detailsWrapper.addStyleName(ValoTheme.TABSHEET_CENTERED_TABS);
		content.addComponent(detailsWrapper);
		content.setExpandRatio(detailsWrapper, 1f);

		detailsWrapper.addComponent(buildGeneraleTab());
		detailsWrapper.addComponent(buildFondoGAE());
		detailsWrapper.addComponent(buildVeicolo());
		detailsWrapper.addComponent(buildDateMissioni());
		if(missione.isMissioneEstera())
			detailsWrapper.addComponent(buildMissioneEstera());
		detailsWrapper.addComponent(buildAnticipazioniMonetarie());


	}

	private Component buildGeneraleTab() {
		HorizontalLayout root = new HorizontalLayout();
		root.setCaption("Generale");
		root.setIcon(FontAwesome.SUITCASE);
		root.setWidth(100.0f, Unit.PERCENTAGE);
		root.setSpacing(true);
		root.setMargin(true);

		FormLayout details = new FormLayout();
		details.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
		root.addComponent(details);
		root.setExpandRatio(details, 1);

		String tipoMissione = missione.isMissioneEstera() ? "Italia" : "Estera";

		details.addComponent(new Label("Tipo Missione: " + tipoMissione));
		details.addComponent(new Label("Località: " + missione.getLocalita()));
		details.addComponent(new Label("Oggetto: " + missione.getOggetto()));
		details.addComponent(new Label("Fondo: " + missione.getFondo()));
		details.addComponent(new Label("GAE: " + missione.getGAE()));
		details.addComponent(new Label("Distanza: " + missione.getDistanza()));
		details.addComponent(new Label("Data Inserimento: " + missione.getDataInserimento()));
		details.addComponent(new Label("Data Ultima Modifica: " + missione.getDateLastModified()));


		return root;
	}

	private Component buildFondoGAE() {

		HorizontalLayout root = new HorizontalLayout();
		root.setCaption("Fondo\\GAE");
		root.setIcon(FontAwesome.SUITCASE);
		root.setWidth(100.0f, Unit.PERCENTAGE);
		root.setSpacing(true);
		root.setMargin(true);

		FormLayout details = new FormLayout();
		details.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
		root.addComponent(details);
		root.setExpandRatio(details, 1);

		details.addComponent(new Label("Fondo: " + missione.getFondo()));
		details.addComponent(new Label("GAE: " + missione.getGAE()));
		details.addComponent(new Label("Responsabile Gruppo: " + missione.getShortResponsabileGruppo()));

		return root;

	}

	private Component buildVeicolo() {

		HorizontalLayout root = new HorizontalLayout();
		root.setCaption("Veicolo");
		root.setIcon(FontAwesome.CAR);
		root.setWidth(100.0f, Unit.PERCENTAGE);
		root.setSpacing(true);
		root.setMargin(true);

		FormLayout details = new FormLayout();
		details.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
		root.addComponent(details);
		root.setExpandRatio(details, 1);
		String tipoVeicolo = missione.isMezzoProprio() ? "Veicolo Proprio" : "Veicolo CNR";

		details.addComponent(new Label("Distanza: " + missione.getDistanza()));
		details.addComponent(new Label("Veicolo: " + tipoVeicolo));
		if (missione.isMezzoProprio())
			details.addComponent(new Label("Motivazione mezzo proprio: " + tipoVeicolo));

		return root;

	}

	private Component buildDateMissioni() {

		HorizontalLayout root = new HorizontalLayout();
		root.setCaption("Date Missione");
		root.setIcon(FontAwesome.CALENDAR);
		root.setWidth(100.0f, Unit.PERCENTAGE);
		root.setSpacing(true);
		root.setMargin(true);

		FormLayout details = new FormLayout();
		details.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
		root.addComponent(details);
		root.setExpandRatio(details, 1);
		String tipoVeicolo = missione.isMezzoProprio() ? "Veicolo Proprio" : "Veicolo CNR";

		details.addComponent(
				new Label("Inizio Missione: " + missione.getDatiPeriodoMissione().getInizioMissione().toString()));
		details.addComponent(
				new Label("Fine Missione: " + missione.getDatiPeriodoMissione().getFineMissione().toString()));

		return root;

	}

	private Component buildMissioneEstera() {

		HorizontalLayout root = new HorizontalLayout();
		root.setCaption("Missione Estera");
		root.setIcon(FontAwesome.PLANE);
		root.setWidth(100.0f, Unit.PERCENTAGE);
		root.setSpacing(true);
		root.setMargin(true);

		FormLayout details = new FormLayout();
		details.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
		root.addComponent(details);
		root.setExpandRatio(details, 1);
		String tipoVeicolo = missione.isMezzoProprio() ? "Veicolo Proprio" : "Veicolo CNR";

		details.addComponent(new Label("Trattamento Rimborso: "
				+ missione.getDatiMissioneEstera().getTrattamentoMissioneEsteraEnum().values()));
		details.addComponent(new Label("Attraversamento Frontiera Andata: "
				+ missione.getDatiMissioneEstera().getAttraversamentoFrontieraAndata().toString()));
		details.addComponent(new Label("Attraversamento Frontiera Ritorno: "
				+ missione.getDatiMissioneEstera().getAttraversamentoFrontieraRitorno().toString()));

		return root;

	}

	private Component buildAnticipazioniMonetarie() {

		HorizontalLayout root = new HorizontalLayout();
		root.setCaption("Anticipazioni Monteratie");
		root.setIcon(FontAwesome.EURO);
		root.setWidth(100.0f, Unit.PERCENTAGE);
		root.setSpacing(true);
		root.setMargin(true);

		FormLayout details = new FormLayout();
		details.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
		root.addComponent(details);
		root.setExpandRatio(details, 1);
		details.addComponent(new Label(
				"Anticipazioni Monetarie: " + missione.getDatiAnticipoPagamenti().isAnticipazioniMonetarie()));

		details.addComponent(new Label("Numero Mandato CNR: " + missione.getDatiAnticipoPagamenti().getMandatoCNR()));
		details.addComponent(new Label("Altre Spese di Missione Anticipate: "
				+ missione.getDatiAnticipoPagamenti().getSpeseMissioniAnticipate()));
		details.addComponent(
				new Label("Rimborso da Terzi: " + missione.getDatiAnticipoPagamenti().getImportoDaTerzi()));
		details.addComponent(new Label("Importo da Terzi: " + missione.getDatiAnticipoPagamenti().getImportoDaTerzi()));

		return root;

	}

	public static void open(final Missione missione) {
		DashboardEventBus.post(new CloseOpenWindowsEvent());
		Window w = new DettagliMissioneWindow(missione);
		UI.getCurrent().addWindow(w);
		w.focus();
	}

}
