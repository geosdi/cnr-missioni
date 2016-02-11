package it.cnr.missioni.dashboard.view;

import java.io.InputStream;
import java.util.Collection;

import javax.ws.rs.core.Response;

import org.vaadin.pagingcomponent.listener.impl.LazyPagingComponentListener;

import com.google.common.eventbus.Subscribe;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.StreamResource;
import com.vaadin.server.StreamResource.StreamSource;
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

import it.cnr.missioni.dashboard.DashboardUI;
import it.cnr.missioni.dashboard.client.ClientConnector;
import it.cnr.missioni.dashboard.component.table.ElencoMissioniTable;
import it.cnr.missioni.dashboard.component.window.DettagliMissioneWindow;
import it.cnr.missioni.dashboard.component.window.DettagliRimborsoWindow;
import it.cnr.missioni.dashboard.component.window.WizardSetupWindow;
import it.cnr.missioni.dashboard.component.wizard.missione.WizardMissione;
import it.cnr.missioni.dashboard.component.wizard.rimborso.WizardRimborso;
import it.cnr.missioni.dashboard.event.DashboardEvent.TableMissioniUpdateUpdatedEvent;
import it.cnr.missioni.dashboard.utility.AdvancedFileDownloader;
import it.cnr.missioni.dashboard.utility.AdvancedFileDownloader.AdvancedDownloaderListener;
import it.cnr.missioni.dashboard.utility.AdvancedFileDownloader.DownloaderEvent;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.el.model.search.builder.MissioneSearchBuilder;
import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.model.rimborso.Rimborso;
import it.cnr.missioni.rest.api.response.missione.MissioniStore;

/**
 * @author Salvia Vito
 */
public class GestioneMissioneView extends GestioneTemplateView<Missione> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 93612577398232810L;
	/**
	 * 
	 */
	private ElencoMissioniTable elencoMissioniTable;
	private TextField idMissioneField;
	private DateField dataFromInserimentoMissioneField;
	private DateField dataToInserimentoMissioneField;
	private TextField numeroOrdineRimborsoField;
	private TextField oggettoMissioneField;

	private VerticalLayout layoutTable;

	private VerticalLayout layoutForm;
	protected Missione selectedMissione;

	protected MissioneSearchBuilder missioneSearchBuilder;
	private MissioniStore missioniStore;

	// private CssLayout panel = new CssLayout();

	public GestioneMissioneView() {
		super();
		
	}

	protected void initPagination() {
		buildPagination(missioniStore != null ? missioniStore.getTotale() : 0);
		addListenerPagination();
	}
	
	protected void inizialize() {
		this.missioneSearchBuilder = MissioneSearchBuilder.getMissioneSearchBuilder()
				.withIdUser(DashboardUI.getCurrentUser().getId());
	}

	/**
	 * 
	 * Aggiunge il listener alla paginazione
	 * 
	 */
	protected void addListenerPagination() {

		pagingComponent.addListener(new LazyPagingComponentListener<Missione>(itemsArea) {

			/**
			 * 
			 */
			private static final long serialVersionUID = -5762496116323381908L;

			@Override
			protected Collection<Missione> getItemsList(int startIndex, int endIndex) {

				try {
					missioniStore = ClientConnector.getMissione(missioneSearchBuilder.withFrom(startIndex));
				} catch (Exception e) {
					Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
							Type.ERROR_MESSAGE);
				}
				elencoMissioniTable.aggiornaTable(missioniStore);
				return missioniStore != null ? missioniStore.getMissioni() : null;

			}

			@Override
			protected Component displayItem(int index, Missione item) {
				return new Label(item.toString());
			}
		});
	}

	/**
	 * 
	 * @return VerticalLayout
	 */
	protected VerticalLayout buildTable() {
		VerticalLayout v = new VerticalLayout();

		this.elencoMissioniTable = new ElencoMissioniTable();
		this.missioneSearchBuilder = MissioneSearchBuilder.getMissioneSearchBuilder()
				.withIdUser(DashboardUI.getCurrentUser().getId());

		this.elencoMissioniTable.addItemClickListener(new ItemClickEvent.ItemClickListener() {
			@Override
			public void itemClick(ItemClickEvent itemClickEvent) {
				selectedMissione = (Missione) itemClickEvent.getItemId();
				enableDisableButtons(true);
			}
		});

		try {
			missioniStore = ClientConnector.getMissione(missioneSearchBuilder);
			this.elencoMissioniTable.aggiornaTable(missioniStore);

		} catch (Exception e) {
			Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
					Type.ERROR_MESSAGE);
		}

		HorizontalLayout layoutSelectPage = new HorizontalLayout();

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



	protected Button createButtonSearch() {
		buttonCerca = buildButton("", "Ricerca full text", FontAwesome.SEARCH);
		buttonCerca.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(final ClickEvent event) {

				try {
					missioneSearchBuilder.withMultiMatch(multiMatchField.getValue());
					missioniStore = ClientConnector.getMissione(missioneSearchBuilder);
					elencoMissioniTable.aggiornaTable(missioniStore);

				} catch (Exception e) {
					Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
							Type.ERROR_MESSAGE);
				}
			}

		});
		return buttonCerca;
	}

	protected GridLayout addActionButtons() {
		GridLayout layout = new GridLayout(5, 1);
		layout.setSpacing(true);
		
		buttonNew = buildButton("Nuova Missione", "Crea una nuova Missione", FontAwesome.PLUS);
		buttonNew.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				WizardSetupWindow.getWizardSetup().withTipo(new WizardMissione()).withMissione(new Missione()).build();
			}

		});
		
		buttonDettagli = buildButton("Dettagli", "Visualizza i dettagli della Missione", FontAwesome.EDIT);

		buttonDettagli.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {

				DettagliMissioneWindow.open(selectedMissione);

			}

		});
		buttonRimborso = buildButton("Rimborso", "Visualizza i dettagli del Rimborso", FontAwesome.EURO);
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
					WizardSetupWindow.getWizardSetup().withTipo(new WizardRimborso()).withMissione(selectedMissione)
							.build();
				}

			}

		});

		buttonPDF = buildButton("PDF MISSIONE", "Download del PDF", FontAwesome.FILE_PDF_O);

		final AdvancedFileDownloader downloaderForLink = new AdvancedFileDownloader();
		downloaderForLink.addAdvancedDownloaderListener(new AdvancedDownloaderListener() {
			@Override
			public void beforeDownload(DownloaderEvent downloadEvent) {

				downloaderForLink.setFileDownloadResource(getResource());

			}

		});

		downloaderForLink.extend(buttonPDF);

		buttonVeicoloMissionePDF = buildButton("PDF VEICOLO", "Download del PDF", FontAwesome.FILE_PDF_O);

		final AdvancedFileDownloader veicoloDownloaderForLink = new AdvancedFileDownloader();
		veicoloDownloaderForLink.addAdvancedDownloaderListener(new AdvancedDownloaderListener() {
			@Override
			public void beforeDownload(DownloaderEvent downloadEvent) {

				veicoloDownloaderForLink.setFileDownloadResource(getResourceVeicolo());

			}

		});

		veicoloDownloaderForLink.extend(buttonVeicoloMissionePDF);

		layout.addComponents(buttonNew,buttonDettagli, buttonRimborso, buttonPDF, buttonVeicoloMissionePDF);

		enableDisableButtons(false);

		return layout;

	}

	protected StreamResource getResource() {
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

	protected StreamResource getResourceVeicolo() {
		try {

			Response r = ClientConnector.downloadVeicoloMissioneAsPdf(selectedMissione.getId());

			InputStream is = r.readEntity(InputStream.class);

			StreamResource stream = new StreamResource(new StreamSource() {
				@Override
				public InputStream getStream() {
					return is;
				}
			}, "moduloMezzoProprio.pdf");

			Utility.getNotification(Utility.getMessage("success_message"), null, Type.HUMANIZED_MESSAGE);
			return stream;
		} catch (Exception e) {
			Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
					Type.ERROR_MESSAGE);
		}
		return null;
	}

	protected void enableDisableButtons(boolean enabled) {
		this.buttonDettagli.setEnabled(enabled);
		this.buttonPDF.setEnabled(enabled);
		this.buttonRimborso.setEnabled(enabled);

		if (selectedMissione != null && selectedMissione.isMezzoProprio())
			buttonVeicoloMissionePDF.setEnabled(true);
		if (selectedMissione == null || !selectedMissione.isMezzoProprio())
			buttonVeicoloMissionePDF.setEnabled(false);

	}

	@Override
	public void enter(final ViewChangeEvent event) {

	}

	/**
	 * 
	 * Aggiorna la table e la paginazione a seguito di un inserimento o una
	 * modifica
	 * 
	 */
	@Subscribe
	public void aggiornaTableMissione(final TableMissioniUpdateUpdatedEvent event) {

		try {
			this.missioniStore = ClientConnector.getMissione(this.missioneSearchBuilder);
			elencoMissioniTable.aggiornaTable(this.missioniStore);
			updatePagination(missioniStore.getTotale());
		} catch (Exception e) {
			Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
					Type.ERROR_MESSAGE);
		}

	}

}