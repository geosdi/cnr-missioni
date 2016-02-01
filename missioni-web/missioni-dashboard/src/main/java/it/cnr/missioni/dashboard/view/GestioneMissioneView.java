package it.cnr.missioni.dashboard.view;

import java.io.InputStream;

import javax.ws.rs.core.Response;


import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.StreamResource;
import com.vaadin.server.StreamResource.StreamSource;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.AlignmentInfo.Bits;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import it.cnr.missioni.dashboard.client.ClientConnector;
import it.cnr.missioni.dashboard.component.table.ElencoMissioniTable;
import it.cnr.missioni.dashboard.component.window.DettagliMissioneWindow;
import it.cnr.missioni.dashboard.component.window.DettagliRimborsoWindow;
import it.cnr.missioni.dashboard.component.window.WizardSetupWindow;
import it.cnr.missioni.dashboard.component.wizard.missione.WizardMissione;
import it.cnr.missioni.dashboard.component.wizard.rimborso.WizardRimborso;
import it.cnr.missioni.dashboard.event.DashboardEvent;
import it.cnr.missioni.dashboard.event.DashboardEventBus;
import it.cnr.missioni.dashboard.utility.AdvancedFileDownloader;
import it.cnr.missioni.dashboard.utility.AdvancedFileDownloader.AdvancedDownloaderListener;
import it.cnr.missioni.dashboard.utility.AdvancedFileDownloader.DownloaderEvent;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.el.model.search.builder.MissioneSearchBuilder;
import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.model.rimborso.Rimborso;
import it.cnr.missioni.model.user.User;
import it.cnr.missioni.rest.api.response.missione.MissioniStore;

/**
 * @author Salvia Vito
 */
public class GestioneMissioneView extends GestioneTemplateView implements View {

	/**
	 * 
	 */
	private static final long serialVersionUID = 93612577398232810L;
	/**
	 * 
	 */
	private ElencoMissioniTable elencoMissioniTable;
	private ComboBox selectPage;
	private TextField idMissioneField;
	private DateField dataFromInserimentoMissioneField;
	private DateField dataToInserimentoMissioneField;
	private TextField numeroOrdineRimborsoField;
	private TextField oggettoMissioneField;

	private VerticalLayout layoutTable;
	private Button buttonDettagli;
//	private Button buttonMail;
	private Button buttonRimborso;
	private Button buttonPDF;
	private VerticalLayout layoutForm;
	private Missione selectedMissione;


	private User user;
	private MissioneSearchBuilder missioneSearchBuilder;
	private MissioniStore missioniStore;

	// private CssLayout panel = new CssLayout();

	public GestioneMissioneView() {
		super();
	}

	protected void initialize() {
		this.user = (User) VaadinSession.getCurrent().getAttribute(User.class.getName());
		this.missioneSearchBuilder = MissioneSearchBuilder.getMissioneSearchBuilder().withIdUser(user.getId());

	}

	/**
	 * 
	 * @return VerticalLayout
	 */
	protected VerticalLayout buildTable() {
		VerticalLayout v = new VerticalLayout();

		this.elencoMissioniTable = new ElencoMissioniTable();

		this.elencoMissioniTable.addItemClickListener(new ItemClickEvent.ItemClickListener() {
			@Override
			public void itemClick(ItemClickEvent itemClickEvent) {
				selectedMissione = (Missione) itemClickEvent.getItemId();
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
	protected void buildComboPage() {
		this.selectPage = new ComboBox();
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

	protected Button createButtonNew() {
		final Button buttonNewMissione = new Button("Nuova Missione");
		buttonNewMissione.setStyleName(ValoTheme.BUTTON_PRIMARY);
		buttonNewMissione.setIcon(FontAwesome.PLUS);
		buttonNewMissione.setDescription("Crea una nuova Missione");
		buttonNewMissione.setStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);

		buttonNewMissione.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				WizardSetupWindow.getWizardSetup().withTipo(new WizardMissione()).withMissione(new Missione())
						.build();
			}

		});
		return buttonNewMissione;
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
					missioneSearchBuilder.withMultiMatch(multiMatchField.getValue());
					missioniStore = ClientConnector.getMissione(missioneSearchBuilder);
					buildComboPage();
					DashboardEventBus.post(new DashboardEvent.TableMissioniUpdateUpdatedEvent(missioniStore));

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
		layout.setSpacing(true);
		buttonDettagli = new Button("Dettagli");
		buttonDettagli.setDescription("Visualizza i dettagli della Missione");
		buttonDettagli.setIcon(FontAwesome.EDIT);
		buttonDettagli.setStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
		
		buttonDettagli.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {

				DettagliMissioneWindow.open(selectedMissione);
				
			}

		});

//		buttonMail = new Button();
//		buttonMail.setDescription("Invia Mail");
//		buttonMail.setIcon(FontAwesome.MAIL_FORWARD);
//		buttonMail.setStyleName(ValoTheme.BUTTON_PRIMARY);
//
//		buttonMail.addClickListener(new Button.ClickListener() {
//
//			@Override
//			public void buttonClick(ClickEvent event) {
//				try {
//					ClientConnector.sendMissioneMail(selectedMissione.getId());
//					Utility.getNotification(Utility.getMessage("success_message"), null, Type.HUMANIZED_MESSAGE);
//				} catch (Exception e) {
//					Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("mail_error"),
//							Type.ERROR_MESSAGE);
//				}
//			}
//
//		});

		buttonRimborso = new Button("Rimborso");
		buttonRimborso.setDescription("Visualizza i dettagli del Rimborso");
		buttonRimborso.setIcon(FontAwesome.EURO);
		buttonRimborso.setStyleName(ValoTheme.BUTTON_PRIMARY);
		buttonRimborso.setStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
		buttonRimborso.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				Rimborso rimborso = null;
				// se è già associato il rimborso
				if (selectedMissione.getRimborso() != null) {
					rimborso = selectedMissione.getRimborso();
					DettagliRimborsoWindow.open(selectedMissione);

				} else {
					rimborso = new Rimborso();
					selectedMissione.setRimborso(rimborso);
					WizardSetupWindow.getWizardSetup().withTipo(new WizardRimborso())
					.withMissione(selectedMissione).build();
				}


			}

		});

		buttonPDF = new Button("PDF");
		buttonPDF.setDescription("Download del PDF");
		buttonPDF.setIcon(FontAwesome.FILE_PDF_O);
		buttonPDF.setStyleName(ValoTheme.BUTTON_PRIMARY);
		buttonPDF.setStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
		
		final AdvancedFileDownloader downloaderForLink = new AdvancedFileDownloader();
		downloaderForLink.addAdvancedDownloaderListener(new AdvancedDownloaderListener() {
			@Override
			public void beforeDownload(DownloaderEvent downloadEvent) {

				downloaderForLink.setFileDownloadResource(getResource());

			}

		});

		downloaderForLink.extend(buttonPDF);

		layout.addComponents(buttonDettagli, buttonRimborso, buttonPDF);

		enableDisableButtons(false);

		return layout;

	}
	
	private StreamResource getResource() {
		try {

			Response r = ClientConnector.downloadMissioneAsPdf(selectedMissione.getId());

			InputStream is = r.readEntity(InputStream.class);

			StreamResource stream = new StreamResource(new StreamSource() {
				@Override
				public InputStream getStream() {
					return is;
				}
			}, "missione.pdf");

			Utility.getNotification(Utility.getMessage("success_message"), null, Type.HUMANIZED_MESSAGE);
			return stream;
		} catch (Exception e) {
			Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
					Type.ERROR_MESSAGE);
		}
		return null;
	}

	protected void enableDisableButtons(boolean enabled) {
//		this.buttonMail.setEnabled(enabled);
		this.buttonDettagli.setEnabled(enabled);
		this.buttonPDF.setEnabled(enabled);
		this.buttonRimborso.setEnabled(enabled);
	}

	@Override
	public void enter(final ViewChangeEvent event) {

	}

}