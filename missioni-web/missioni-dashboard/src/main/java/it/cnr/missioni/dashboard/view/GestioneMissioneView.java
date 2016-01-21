package it.cnr.missioni.dashboard.view;

import org.vaadin.jouni.animator.client.CssAnimation;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.event.ShortcutListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.AlignmentInfo.Bits;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.DateField;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import it.cnr.missioni.dashboard.client.ClientConnector;
import it.cnr.missioni.dashboard.component.table.ElencoMissioniTable;
import it.cnr.missioni.dashboard.component.window.WizardSetupWindow;
import it.cnr.missioni.dashboard.event.DashboardEvent;
import it.cnr.missioni.dashboard.event.DashboardEventBus;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.el.model.search.builder.MissioneSearchBuilder;
import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.model.rimborso.Rimborso;
import it.cnr.missioni.model.user.User;
import it.cnr.missioni.rest.api.response.missione.MissioniStore;

/**
 * @author Salvia Vito
 */
public class GestioneMissioneView extends VerticalLayout implements View {

	/**
	 * 
	 */
	private static final long serialVersionUID = 93612577398232810L;
	/**
	 * 
	 */
	private ElencoMissioniTable elencoMissioniTable;
	private ComboBox selectPage = new ComboBox();
	private TextField idMissioneField;
	private DateField dataFromInserimentoMissioneField;
	private DateField dataToInserimentoMissioneField;
	private TextField numeroOrdineRimborsoField;
	private TextField oggettoMissioneField;

	private VerticalLayout layoutTable;
	private Button buttonModifica;
	private Button buttonMail;
	private Button buttonRimborso;
	private Button buttonPDF;
	private TextField multiMatchField;
	private VerticalLayout layoutForm;
	private Missione selectedMissione;
	
	private  CssAnimation a;
	
	private User user = (User) VaadinSession.getCurrent().getAttribute(User.class.getName());
	private MissioneSearchBuilder missioneSearchBuilder = MissioneSearchBuilder.getMissioneSearchBuilder()
			.withIdUser(user.getId());
	private MissioniStore missioniStore;

	// private CssLayout panel = new CssLayout();

	public GestioneMissioneView() {
		addStyleName(ValoTheme.PANEL_BORDERLESS);
		addStyleName("missione-view");
		setSizeFull();
		DashboardEventBus.register(this);
		setHeight("96%");
		setWidth("98%");
		addStyleName(ValoTheme.LAYOUT_CARD);
		addStyleName("panel-view");
		Responsive.makeResponsive(this);

		CssLayout toolbar = new CssLayout();
		toolbar.setWidth("100%");
		toolbar.setStyleName("toolbar-search");
		HorizontalLayout fullTextsearchLayout = new HorizontalLayout(buildFilter(), createButtonSearch());
		fullTextsearchLayout.setSpacing(true);
		fullTextsearchLayout.setStyleName("full-text-search");

		HorizontalLayout newMissioneLayout = new HorizontalLayout(createButtonNewMissione());
		newMissioneLayout.setSpacing(true);
		newMissioneLayout.setStyleName("button-new-missione");

		layoutTable = buildTable();
		layoutTable.setStyleName("layout-table-missione");

		GridLayout buttonsLayout = buildButtonsMissione();
		buttonsLayout.setSpacing(true);
		buttonsLayout.setStyleName("buttons-layout");
		
		 layoutForm = new VerticalLayout();
		layoutForm.addComponent(new TextField("aaaa"));
//		layoutForm.setVisible(false);
		layoutForm.setHeight("15%");

		


		
		toolbar.addComponent(newMissioneLayout);
		toolbar.addComponent(fullTextsearchLayout);
		addComponent(toolbar);
//		setExpandRatio(toolbar, new Float(1));
//		addComponent(layoutForm);
//		Animator.animate(layoutForm, new Css().translateY("1000px")).duration(5000);
//		setExpandRatio(layoutForm, new Float(0.1));
		addComponent(layoutTable);
		addComponent(buttonsLayout);
		setExpandRatio(layoutTable, new Float(1));
		setComponentAlignment(buttonsLayout, Alignment.TOP_CENTER);
	}

	/**
	 * 
	 * @return VerticalLayout
	 */
	private VerticalLayout buildTable() {
		VerticalLayout v = new VerticalLayout();

		this.elencoMissioniTable = new ElencoMissioniTable();

		this.elencoMissioniTable.addItemClickListener(new ItemClickEvent.ItemClickListener() {
			@Override
			public void itemClick(ItemClickEvent itemClickEvent) {
				selectedMissione = (Missione)itemClickEvent.getItemId();
				enableDisableButtons(true);
			}
		});

		try {
			missioniStore = ClientConnector.getMissione(missioneSearchBuilder);
			buildComboPage();
			this.elencoMissioniTable.aggiornaTable(missioniStore);

		} catch (Exception e) {
			Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
					Type.ERROR_MESSAGE);
		}

		HorizontalLayout layoutSelectPage = new HorizontalLayout();
		layoutSelectPage.addComponent(this.selectPage);

		layoutSelectPage.setMargin(true);
		v.addComponent(this.elencoMissioniTable);
		v.addComponent(layoutSelectPage);
		v.setComponentAlignment(elencoMissioniTable,
				new Alignment(Bits.ALIGNMENT_VERTICAL_CENTER | Bits.ALIGNMENT_HORIZONTAL_CENTER));
		v.setComponentAlignment(layoutSelectPage,
				new Alignment(Bits.ALIGNMENT_VERTICAL_CENTER | Bits.ALIGNMENT_HORIZONTAL_CENTER));

		return v;
	}

	/**
	 * 
	 * Costruisce la form di ricerca
	 * 
	 * @return HorizontalLayout
	 */
	// private HorizontalLayout buildForm() {
	// HorizontalLayout v = new HorizontalLayout();
	// v.setMargin(true);
	// FormLayout form = new FormLayout();
	// form.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
	// form.setSizeUndefined();
	// v.setWidth("98%");
	// final ComboBox statoSelect = new ComboBox();
	// statoSelect.setCaption("Stato");
	//
	// Map<String, StatoEnum> mappaS = StatoEnum.getMappa();
	//
	// mappaS.values().forEach(s -> {
	// statoSelect.addItem(s);
	// statoSelect.setItemCaption(s, s.getStato());
	// });
	//
	// idMissioneField = new TextField();
	// idMissioneField.setCaption("Id Missione");
	// dataFromInserimentoMissioneField = new DateField("Data dal");
	// dataToInserimentoMissioneField = new DateField("Data al");
	// dataToInserimentoMissioneField.setImmediate(true);
	// dataFromInserimentoMissioneField.setValidationVisible(false);
	// numeroOrdineRimborsoField = new TextField("Numero Rimborso");
	// oggettoMissioneField = new TextField("Oggetto Missione");
	//
	// addValidator();
	//
	// form.addComponent(statoSelect);
	// form.addComponent(idMissioneField);
	// form.addComponent(oggettoMissioneField);
	// form.addComponent(numeroOrdineRimborsoField);
	// form.addComponent(dataFromInserimentoMissioneField);
	// form.addComponent(dataToInserimentoMissioneField);
	// final Button buttonCerca = new Button("Cerca");
	// buttonCerca.setEnabled(true);
	// buttonCerca.addStyleName(ValoTheme.BUTTON_PRIMARY);
	//
	// buttonCerca.addClickListener(new Button.ClickListener() {
	// public void buttonClick(ClickEvent event) {
	//
	// DateTime from = null;
	// DateTime to = null;
	// String stato = null;
	// // se il valore della form è null JODATIME ritorna comunque la
	// // data odierna
	// if (dataFromInserimentoMissioneField.getValue() != null)
	// from = new DateTime(dataFromInserimentoMissioneField.getValue());
	// if (dataToInserimentoMissioneField.getValue() != null)
	// to = new DateTime(dataToInserimentoMissioneField.getValue());
	// if (statoSelect.getValue() != null)
	// stato = ((StatoEnum) statoSelect.getValue()).name();
	//
	// missioneSearchBuilder = MissioneSearchBuilder.getMissioneSearchBuilder()
	// .withOggetto(oggettoMissioneField.getValue())
	// .withNumeroOrdineMissione(numeroOrdineRimborsoField.getValue())
	// .withIdMissione(idMissioneField.getValue()).withStato(stato).withIdUser(user.getId())
	// .withRangeDataInserimento(from, to);
	//
	// try {
	// missioniStore = ClientConnector.getMissione(missioneSearchBuilder);
	// elencoMissioniTable.aggiornaTable(missioniStore);
	// } catch (Exception e) {
	// Utility.getNotification(Utility.getMessage("error_message"),
	// Utility.getMessage("request_error"),
	// Type.ERROR_MESSAGE);
	// }
	// }
	// });
	//
	// form.addComponent(buttonCerca);
	// v.addComponent(form);
	// v.setComponentAlignment(form, new
	// Alignment(Bits.ALIGNMENT_HORIZONTAL_CENTER));
	// v.setComponentAlignment(form, Alignment.MIDDLE_CENTER);
	// return v;
	// }
	//
	// private void addValidator() {
	//
	// dataToInserimentoMissioneField.addValidator(new Validator() {
	//
	// @Override
	// public void validate(Object value) throws InvalidValueException {
	// Date v = (Date) value;
	// if (v != null && v.before(dataFromInserimentoMissioneField.getValue()))
	// throw new InvalidValueException(Utility.getMessage("data_range_error"));
	//
	// }
	//
	// });
	//
	// dataToInserimentoMissioneField.addBlurListener(new BlurListener() {
	//
	// @Override
	// public void blur(BlurEvent event) {
	// try {
	// dataFromInserimentoMissioneField.validate();
	// } catch (Exception e) {
	// dataFromInserimentoMissioneField.setValidationVisible(true);
	// }
	//
	// }
	// });
	// }

	/**
	 * Costruisce la Select per la paginazione
	 */
	private void buildComboPage() {

		this.selectPage.removeAllItems();
		if (missioniStore != null) {
			long totale = missioniStore.getTotale();
			long totPage = totale % missioneSearchBuilder.getSize() == 0 ? totale / missioneSearchBuilder.getSize()
					: 1 + (totale / missioneSearchBuilder.getSize());
			this.selectPage.setValue(1);
			// selectPage.setNullSelectionAllowed(false);
			this.selectPage.setImmediate(true);
			this.selectPage.setInputPrompt("Seleziona Pagina");
			for (int j = 1; j <= totPage; j++) {
				this.selectPage.addItem(j);
				this.selectPage.setItemCaption(j, Integer.toString(j));
			}

			this.selectPage.addValueChangeListener(new ValueChangeListener() {
				@Override
				public void valueChange(ValueChangeEvent event) {
					int nextPage = Integer.parseInt(String.valueOf(event.getProperty().getValue()));
					int from = (nextPage - 1) * missioneSearchBuilder.getSize();
					missioneSearchBuilder.setFrom(from);
					try {
						missioniStore = ClientConnector.getMissione(missioneSearchBuilder);
						elencoMissioniTable.aggiornaTable(missioniStore);

					} catch (Exception e) {
						Utility.getNotification(Utility.getMessage("error_message"),
								Utility.getMessage("request_error"), Type.ERROR_MESSAGE);
					}
				}
			});
		}

	}

	@Override
	public void enter(final ViewChangeEvent event) {

	}

	private Button createButtonNewMissione() {
		final Button buttonNewMissione = new Button();
		buttonNewMissione.setStyleName(ValoTheme.BUTTON_PRIMARY);
		buttonNewMissione.setIcon(FontAwesome.PLUS);
		buttonNewMissione.setDescription("Nuova Missione");
		buttonNewMissione.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				WizardSetupWindow.getWizardSetup().withModifica(false).withTipo("missione").withMissione(new Missione()).build();				
			}
			
		});
		return buttonNewMissione;
	}						


	private Button createButtonSearch() {
		final Button buttonCerca = new Button();
		buttonCerca.setIcon(FontAwesome.SEARCH);
		buttonCerca.setStyleName(ValoTheme.BUTTON_PRIMARY);
		buttonCerca.setDescription("Ricerca full text");
		buttonCerca.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(final ClickEvent event) {

				try {
					missioneSearchBuilder.withMultiMatch(multiMatchField.getValue());
					missioniStore = ClientConnector.getMissione(missioneSearchBuilder);
					buildComboPage();
					 DashboardEventBus.post(new
					 DashboardEvent.TableMissioniUpdatedEvent(missioniStore));
					 
			 

//					 Animator anim = new Animator(new Label("Animate Me!"));

//					 layoutForm.setVisible(true);

				} catch (Exception e) {
					Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
							Type.ERROR_MESSAGE);
				}
			}

		});
		return buttonCerca;
	}

	private GridLayout buildButtonsMissione() {
		GridLayout layout = new GridLayout(4, 1);

		buttonModifica = new Button();
		buttonModifica.setDescription("Modifica");
		buttonModifica.setIcon(FontAwesome.PENCIL);
		buttonModifica.setStyleName(ValoTheme.BUTTON_PRIMARY);
		buttonModifica.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				WizardSetupWindow.getWizardSetup().withModifica(true).withTipo("missione").withMissione(selectedMissione).build();
				
			}
			
		});

		buttonMail = new Button();
		buttonMail.setDescription("Invia Mail");
		buttonMail.setIcon(FontAwesome.MAIL_FORWARD);
		buttonMail.setStyleName(ValoTheme.BUTTON_PRIMARY);

		buttonRimborso = new Button();
		buttonRimborso.setDescription("Rimborso");
		buttonRimborso.setIcon(FontAwesome.EURO);
		buttonRimborso.setStyleName(ValoTheme.BUTTON_PRIMARY);
		buttonRimborso.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				Rimborso rimborso = null;
				boolean modifica;
				// se non è stato ancora associato il rimborso
				if (selectedMissione.getRimborso() != null) {
					rimborso = selectedMissione.getRimborso();
					modifica = true;
				} else {
					rimborso = new Rimborso();
					modifica = false;
					selectedMissione.setRimborso(rimborso);
				}
				
				WizardSetupWindow.getWizardSetup().withModifica(modifica).withTipo("rimborso").withMissione(selectedMissione).build();				
			}
			
		});

		buttonPDF = new Button();
		buttonPDF.setDescription("Genera PDF");
		buttonPDF.setIcon(FontAwesome.FILE_PDF_O);
		buttonPDF.setStyleName(ValoTheme.BUTTON_PRIMARY);

		layout.addComponents(buttonModifica, buttonMail, buttonRimborso, buttonPDF);

		enableDisableButtons(false);

		return layout;

	}

	private void enableDisableButtons(boolean enabled) {
		this.buttonMail.setEnabled(enabled);
		this.buttonModifica.setEnabled(enabled);
		this.buttonPDF.setEnabled(enabled);
		this.buttonRimborso.setEnabled(enabled);
	}

	private Component buildFilter() {
		multiMatchField = new TextField();
		multiMatchField.addTextChangeListener(new TextChangeListener() {
			@Override
			public void textChange(final TextChangeEvent event) {

			}
		});

		multiMatchField.setInputPrompt("Testo da ricercare");
		multiMatchField.setIcon(FontAwesome.SEARCH);
		multiMatchField.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
		multiMatchField.addShortcutListener(new ShortcutListener("Clear", KeyCode.ESCAPE, null) {
			@Override
			public void handleAction(final Object sender, final Object target) {
			}
		});
		return multiMatchField;
	}

}