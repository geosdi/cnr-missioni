package it.cnr.missioni.dashboard.view.admin;

import java.util.Collection;

import org.vaadin.pagingcomponent.listener.impl.LazyPagingComponentListener;

import com.google.common.eventbus.Subscribe;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.AlignmentInfo.Bits;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import it.cnr.missioni.dashboard.client.ClientConnector;
import it.cnr.missioni.dashboard.component.table.admin.ElencoUserTable;
import it.cnr.missioni.dashboard.component.window.UserWindow;
import it.cnr.missioni.dashboard.event.DashboardEvent.TableUserUpdatedEvent;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.dashboard.view.GestioneTemplateView;
import it.cnr.missioni.el.model.search.builder.UserSearchBuilder;
import it.cnr.missioni.model.user.User;
import it.cnr.missioni.rest.api.response.user.UserStore;

/**
 * @author Salvia Vito
 */
public class GestioneUserAdminView extends GestioneTemplateView<User> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 93612577398232810L;
	/**
	 * 
	 */
	private ElencoUserTable elencoUserTable;
	private TextField idMissioneField;
	private DateField dataFromInserimentoMissioneField;
	private DateField dataToInserimentoMissioneField;
	private TextField numeroOrdineRimborsoField;
	private TextField oggettoMissioneField;

	private VerticalLayout layoutTable;
	private VerticalLayout layoutForm;
	private User selectedUser;

	private UserSearchBuilder userSearchBuilder;
	private UserStore userStore;

	public GestioneUserAdminView() {
		super();
	}
	
	protected void inizialize() {
		this.userSearchBuilder = UserSearchBuilder.getUserSearchBuilder();
	}

	/**
	 * 
	 * @return VerticalLayout
	 */
	protected VerticalLayout buildTable() {
		VerticalLayout v = new VerticalLayout();


		this.elencoUserTable = new ElencoUserTable();
		this.elencoUserTable.addItemClickListener(new ItemClickEvent.ItemClickListener() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1826612705343869714L;

			@Override
			public void itemClick(ItemClickEvent itemClickEvent) {
				selectedUser = (User) itemClickEvent.getItemId();
				enableButtons();
			}
		});

		try {
			userStore = ClientConnector.getUser(userSearchBuilder);
			this.elencoUserTable.aggiornaTable(userStore);

		} catch (Exception e) {
			Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
					Type.ERROR_MESSAGE);
		}

		HorizontalLayout layoutSelectPage = new HorizontalLayout();


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

	// /**
	// * Costruisce la Select per la paginazione
	// */
	// protected void buildComboPage() {
	// this.selectPage = new ComboBox();
	// this.selectPage.removeAllItems();
	// if (userStore != null) {
	// long totale = userStore.getTotale();
	// long totPage = totale % userSearchBuilder.getSize() == 0 ? totale /
	// userSearchBuilder.getSize()
	// : 1 + (totale / userSearchBuilder.getSize());
	// this.selectPage.setValue(1);
	// // selectPage.setNullSelectionAllowed(false);
	// this.selectPage.setImmediate(true);
	// this.selectPage.setInputPrompt("Seleziona Pagina");
	// for (int j = 1; j <= totPage; j++) {
	// this.selectPage.addItem(j);
	// this.selectPage.setItemCaption(j, Integer.toString(j));
	// }
	//
	// this.selectPage.addValueChangeListener(new ValueChangeListener() {
	// @Override
	// public void valueChange(ValueChangeEvent event) {
	// int nextPage =
	// Integer.parseInt(String.valueOf(event.getProperty().getValue()));
	// int from = (nextPage - 1) * userSearchBuilder.getSize();
	// userSearchBuilder.setFrom(from);
	// try {
	// userStore = ClientConnector.getUser(userSearchBuilder);
	// elencoUserTable.aggiornaTable(userStore);
	//
	// } catch (Exception e) {
	// Utility.getNotification(Utility.getMessage("error_message"),
	// Utility.getMessage("request_error"), Type.ERROR_MESSAGE);
	// }
	// }
	// });
	// }
	//
	// }

	@Override
	public void enter(final ViewChangeEvent event) {

	}

	protected Button createButtonSearch() {
		
		buttonCerca = buildButton("", "Ricerca full text",FontAwesome.SEARCH);
		buttonCerca.addClickListener(new Button.ClickListener() {
			/**
			 * 
			 */
			private static final long serialVersionUID = -2401290057230222368L;

			@Override
			public void buttonClick(final ClickEvent event) {

				try {
					userSearchBuilder.withMultiMatch(multiMatchField.getValue());
					userStore = ClientConnector.getUser(userSearchBuilder);
					elencoUserTable.aggiornaTable(userStore);
				} catch (Exception e) {
					Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
							Type.ERROR_MESSAGE);
				}
			}

		});
		return buttonCerca;
	}

	protected GridLayout addActionButtons() {
		GridLayout layout = new GridLayout(4, 1);
		buttonDettagli = buildButton("Dettagli", "Visualizza i dettagli dell'user",FontAwesome.EDIT);

		buttonDettagli.addClickListener(new Button.ClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 7939532417002125241L;

			@Override
			public void buttonClick(ClickEvent event) {
				UserWindow.open(selectedUser, true);

			}

		});

		layout.addComponents(buttonDettagli);

		disableButtons();

		return layout;

	}

//	protected void enableDisableButtons(boolean enabled) {
//		this.buttonDettagli.setEnabled(enabled);
//	}

	/**
	 * 
	 */
	protected void initPagination() {
		buildPagination(userStore != null ? userStore.getTotale() : 0);
		addListenerPagination();
	}

	/**
	 * 
	 * Aggiunge il listener alla paginazione
	 * 
	 */
	protected void addListenerPagination() {

		pagingComponent.addListener(new LazyPagingComponentListener<User>(itemsArea) {

			/**
			 * 
			 */
			private static final long serialVersionUID = 2537782371968033967L;

			@Override
			protected Collection<User> getItemsList(int startIndex, int endIndex) {

				try {
					userStore = ClientConnector.getUser(userSearchBuilder.withFrom(startIndex));
				} catch (Exception e) {
					Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
							Type.ERROR_MESSAGE);
				}
				elencoUserTable.aggiornaTable(userStore);
				return userStore != null ? userStore.getUsers() : null;

			}

			@Override
			protected Component displayItem(int index, User item) {
				return new Label(item.toString());
			}
		});
	}


	
	/**
	 * 
	 * Aggiorna la table e la paginazione a seguito di un inserimento o una modifica
	 * 
	 */
	@Subscribe
	public void aggiornaTableUser(final TableUserUpdatedEvent event) {

		try {
			this.userStore = ClientConnector.getUser(this.userSearchBuilder);
			elencoUserTable.aggiornaTable(this.userStore);
			updatePagination(userStore.getTotale());
		} catch (Exception e) {
			Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
					Type.ERROR_MESSAGE);
		}

	}

	/**
	 * 
	 */
	@Override
	protected void enableButtons() {
		this.buttonDettagli.setEnabled(true);
		
	}

	/**
	 * 
	 */
	@Override
	protected void disableButtons() {
		this.buttonDettagli.setEnabled(false);
		
	}

}