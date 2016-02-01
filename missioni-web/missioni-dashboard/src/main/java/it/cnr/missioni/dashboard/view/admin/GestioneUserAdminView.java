package it.cnr.missioni.dashboard.view.admin;

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
import it.cnr.missioni.dashboard.component.table.admin.ElencoUserTable;
import it.cnr.missioni.dashboard.component.window.UserCompletedRegistrationWindow;
import it.cnr.missioni.dashboard.component.window.WizardSetupWindow;
import it.cnr.missioni.dashboard.event.DashboardEvent;
import it.cnr.missioni.dashboard.event.DashboardEventBus;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.dashboard.view.GestioneTemplateView;
import it.cnr.missioni.el.model.search.builder.MissioneSearchBuilder;
import it.cnr.missioni.el.model.search.builder.UserSearchBuilder;
import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.model.user.User;
import it.cnr.missioni.rest.api.response.user.UserStore;

/**
 * @author Salvia Vito
 */
public class GestioneUserAdminView extends GestioneTemplateView implements View {

	/**
	 * 
	 */
	private static final long serialVersionUID = 93612577398232810L;
	/**
	 * 
	 */
	private ElencoUserTable elencoUserTable;
	private ComboBox selectPage = new ComboBox();
	private TextField idMissioneField;
	private DateField dataFromInserimentoMissioneField;
	private DateField dataToInserimentoMissioneField;
	private TextField numeroOrdineRimborsoField;
	private TextField oggettoMissioneField;

	private VerticalLayout layoutTable;
	private Button buttonEdit;
	private TextField multiMatchField;
	private VerticalLayout layoutForm;
	private User selectedUser;
	
		
	private UserSearchBuilder userSearchBuilder ;
	private UserStore userStore;

	// private CssLayout panel = new CssLayout();

	public GestioneUserAdminView() {
//		addStyleName(ValoTheme.PANEL_BORDERLESS);
//		addStyleName("missione-view");
//		setSizeFull();
//		DashboardEventBus.register(this);
//		setHeight("96%");
//		setWidth("98%");
//		addStyleName(ValoTheme.LAYOUT_CARD);
//		addStyleName("panel-view");
//		Responsive.makeResponsive(this);
//
//		CssLayout toolbar = new CssLayout();
//		toolbar.setWidth("100%");
//		toolbar.setStyleName("toolbar-search");
//		HorizontalLayout fullTextsearchLayout = new HorizontalLayout(buildFilter(), createButtonSearch());
//		fullTextsearchLayout.setSpacing(true);
//		fullTextsearchLayout.setStyleName("full-text-search");
//
//
//		layoutTable = buildTable();
//		layoutTable.setStyleName("layout-table-missione");
//
//		GridLayout buttonsLayout = buildButtonsSelectedUser();
//		buttonsLayout.setSpacing(true);
//		buttonsLayout.setStyleName("buttons-layout");
//		
//		
//		toolbar.addComponent(fullTextsearchLayout);
//		addComponent(toolbar);
////		setExpandRatio(toolbar, new Float(1));
////		addComponent(layoutForm);
////		Animator.animate(layoutForm, new Css().translateY("1000px")).duration(5000);
////		setExpandRatio(layoutForm, new Float(0.1));
//		addComponent(layoutTable);
//		addComponent(buttonsLayout);
//		setExpandRatio(layoutTable, new Float(1));
//		setComponentAlignment(buttonsLayout, Alignment.TOP_CENTER);
	}

	/**
	 * 
	 * @return VerticalLayout
	 */
	protected VerticalLayout buildTable() {
		VerticalLayout v = new VerticalLayout();

		this.elencoUserTable = new ElencoUserTable();

		this.elencoUserTable.addItemClickListener(new ItemClickEvent.ItemClickListener() {
			@Override
			public void itemClick(ItemClickEvent itemClickEvent) {
				selectedUser = (User)itemClickEvent.getItemId();
				enableDisableButtons(true);
			}
		});

		try {
			userStore = ClientConnector.getUser(userSearchBuilder);
			buildComboPage();
			this.elencoUserTable.aggiornaTable(userStore);

		} catch (Exception e) {
			Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
					Type.ERROR_MESSAGE);
		}

		HorizontalLayout layoutSelectPage = new HorizontalLayout();
		layoutSelectPage.addComponent(this.selectPage);

		layoutSelectPage.setMargin(true);
		v.addComponent(this.elencoUserTable);
		v.addComponent(layoutSelectPage);
		v.setComponentAlignment(elencoUserTable,
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
	protected void buildComboPage() {
		this.selectPage = new ComboBox();
		this.selectPage.removeAllItems();
		if (userStore != null) {
			long totale = userStore.getTotale();
			long totPage = totale % userSearchBuilder.getSize() == 0 ? totale / userSearchBuilder.getSize()
					: 1 + (totale / userSearchBuilder.getSize());
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
					int from = (nextPage - 1) * userSearchBuilder.getSize();
					userSearchBuilder.setFrom(from);
					try {
						userStore = ClientConnector.getUser(userSearchBuilder);
						elencoUserTable.aggiornaTable(userStore);

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

				


	protected Button createButtonSearch() {
		final Button buttonCerca = new Button();
		buttonCerca.setIcon(FontAwesome.SEARCH);
		buttonCerca.setStyleName(ValoTheme.BUTTON_PRIMARY);
		buttonCerca.setDescription("Ricerca full text");
		buttonCerca.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(final ClickEvent event) {

				try {
					userSearchBuilder.withMultiMatch(multiMatchField.getValue());
					userStore = ClientConnector.getUser(userSearchBuilder);
					buildComboPage();
					 DashboardEventBus.post(new
					 DashboardEvent.TableUserUpdatedEvent(userStore));
					 
			 

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

	protected GridLayout buildButtons() {
		GridLayout layout = new GridLayout(4, 1);

		buttonEdit = new Button("Dettagli");
		buttonEdit.setDescription("Visualizza i dettagli dell'user");
		buttonEdit.setIcon(FontAwesome.EDIT);
		buttonEdit.setStyleName(ValoTheme.BUTTON_PRIMARY);
		buttonEdit.setStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);

		buttonEdit.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				UserCompletedRegistrationWindow.open(selectedUser,true);

			}
			
		});


		layout.addComponents(buttonEdit);

		enableDisableButtons(false);

		return layout;

	}

	protected void enableDisableButtons(boolean enabled) {
		this.buttonEdit.setEnabled(enabled);
	}


	/**
	 * 
	 */
	protected void initialize() {
		this.userSearchBuilder =  UserSearchBuilder.getUserSearchBuilder();
	}

	/**
	 * @return
	 */
	@Override
	protected Button createButtonNew() {
		// TODO Auto-generated method stub
		return null;
	}

}