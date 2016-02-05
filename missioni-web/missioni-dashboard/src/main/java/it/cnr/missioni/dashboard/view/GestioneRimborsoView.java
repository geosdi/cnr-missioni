package it.cnr.missioni.dashboard.view;

import java.io.InputStream;
import java.util.Collection;

import javax.ws.rs.core.Response;

import org.vaadin.pagingcomponent.listener.impl.LazyPagingComponentListener;

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
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import it.cnr.missioni.dashboard.DashboardUI;
import it.cnr.missioni.dashboard.client.ClientConnector;
import it.cnr.missioni.dashboard.component.table.ElencoRimborsiTable;
import it.cnr.missioni.dashboard.component.window.DettagliMissioneWindow;
import it.cnr.missioni.dashboard.component.window.DettagliRimborsoWindow;
import it.cnr.missioni.dashboard.event.DashboardEvent;
import it.cnr.missioni.dashboard.event.DashboardEventBus;
import it.cnr.missioni.dashboard.utility.AdvancedFileDownloader;
import it.cnr.missioni.dashboard.utility.AdvancedFileDownloader.AdvancedDownloaderListener;
import it.cnr.missioni.dashboard.utility.AdvancedFileDownloader.DownloaderEvent;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.el.model.search.builder.MissioneSearchBuilder;
import it.cnr.missioni.el.model.search.builder.SearchConstants;
import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.model.rimborso.Rimborso;
import it.cnr.missioni.model.user.User;
import it.cnr.missioni.rest.api.response.missione.MissioniStore;

/**
 * @author Salvia Vito
 */
public class GestioneRimborsoView extends GestioneTemplateView<Missione> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 93612577398232810L;
	/**
	 * 
	 */
	private ElencoRimborsiTable elencoRimborsiTable;
	private TextField idMissioneField;
	private DateField dataFromInserimentoMissioneField;
	private DateField dataToInserimentoMissioneField;
	private TextField numeroOrdineRimborsoField;
	private TextField oggettoMissioneField;

	private VerticalLayout layoutTable;
	private VerticalLayout layoutForm;
	private Missione selectedMissione;

	private MissioneSearchBuilder missioneSearchBuilder;

	private MissioniStore missioniStore;

	public GestioneRimborsoView() {
		super();
	}

	/**
	 * 
	 * @return VerticalLayout
	 */
	protected VerticalLayout buildTable() {
		VerticalLayout v = new VerticalLayout();

		this.elencoRimborsiTable = new ElencoRimborsiTable();
		this.missioneSearchBuilder = MissioneSearchBuilder.getMissioneSearchBuilder().withIdUser(DashboardUI.getCurrentUser().getId())
				.withFieldExist("missione.rimborso")
				.withSortField(SearchConstants.MISSIONE_FIELD_RIMBORSO_DATA_RIMBORSO);

		this.elencoRimborsiTable.addItemClickListener(new ItemClickEvent.ItemClickListener() {
			@Override
			public void itemClick(ItemClickEvent itemClickEvent) {
				selectedMissione = (Missione) itemClickEvent.getItemId();
				enableDisableButtons(true);
			}
		});

		try {
			missioniStore = ClientConnector.getMissione(missioneSearchBuilder);

			this.elencoRimborsiTable.aggiornaTable(missioniStore);

		} catch (Exception e) {
			Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
					Type.ERROR_MESSAGE);
		}

		HorizontalLayout layoutSelectPage = new HorizontalLayout();

		layoutSelectPage.setMargin(true);
		v.addComponent(this.elencoRimborsiTable);
		v.addComponent(layoutSelectPage);
		v.setComponentAlignment(elencoRimborsiTable,
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

	@Override
	public void enter(final ViewChangeEvent event) {

	}

	protected Button createButtonSearch() {
		buttonCerca = new Button();
		buttonCerca.setIcon(FontAwesome.SEARCH);
		buttonCerca.setStyleName(ValoTheme.BUTTON_PRIMARY);
		buttonCerca.setDescription("Ricerca full text");
		buttonCerca.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(final ClickEvent event) {

				try {
					missioneSearchBuilder.withMultiMatch(multiMatchField.getValue());
					missioniStore = ClientConnector.getMissione(missioneSearchBuilder);
					DashboardEventBus.post(new DashboardEvent.TableRimborsiUpdatedEvent(missioniStore));

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
		layout.setSpacing(true);
		
		buttonDettagli = buildButton("Dettagli", "Visualizza i dettagli del Rimborso",FontAwesome.EDIT);
		buttonDettagli.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				DettagliRimborsoWindow.open(selectedMissione);

			}

		});


		buttonMissione = buildButton("Missone", "Visualizza i dati della Missione",FontAwesome.SUITCASE);

		buttonMissione.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				DettagliMissioneWindow.open(selectedMissione);

			}

		});
		buttonPDF = buildButton("PDF", "Download del PDF", FontAwesome.FILE_PDF_O);

		final AdvancedFileDownloader downloaderForLink = new AdvancedFileDownloader();
		downloaderForLink.addAdvancedDownloaderListener(new AdvancedDownloaderListener() {

			/**
			 * This method will be invoked just before the download starts.
			 * Thus, a new file path can be set.
			 *
			 * @param downloadEvent
			 */
			@Override
			public void beforeDownload(DownloaderEvent downloadEvent) {

				downloaderForLink.setFileDownloadResource(getResource());

			}

		});

		downloaderForLink.extend(buttonPDF);

		layout.addComponents(buttonDettagli, buttonMissione, buttonPDF);

		enableDisableButtons(false);

		return layout;

	}

	private StreamResource getResource() {
		try {

			Response r = ClientConnector.downloadRimborsoMissioneAsPdf(selectedMissione.getId());
			InputStream is = r.readEntity(InputStream.class);

			StreamResource stream = new StreamResource(new StreamSource() {
				@Override
				public InputStream getStream() {
					return is;
				}
			}, "rimborso.pdf");

			Utility.getNotification(Utility.getMessage("success_message"), null, Type.HUMANIZED_MESSAGE);
			return stream;
		} catch (Exception e) {
			Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
					Type.ERROR_MESSAGE);
		}
		return null;
	}

	protected void enableDisableButtons(boolean enabled) {
		// this.buttonMail.setEnabled(enabled);
		this.buttonDettagli.setEnabled(enabled);
		this.buttonPDF.setEnabled(enabled);
		this.buttonMissione.setEnabled(enabled);

	}

	/**
	 * 
	 */
	@Override
	protected void initialize() {

			if (missioniStore != null)
				buildPagination(missioniStore.getTotale());
			addListenerPagination();


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
			private static final long serialVersionUID = -1085832384947148622L;

			@Override
			protected Collection<Missione> getItemsList(int startIndex, int endIndex) {

				try {
					missioniStore = ClientConnector.getMissione(missioneSearchBuilder.withFrom(startIndex));
				} catch (Exception e) {
					Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
							Type.ERROR_MESSAGE);
				}
				DashboardEventBus.post(new  DashboardEvent.TableMissioniUpdateUpdatedEvent(missioniStore) );
				return missioniStore != null ? missioniStore.getMissioni() : null;

			}

			@Override
			protected Component displayItem(int index, Missione item) {
				return new Label(item.toString());
			}
		});
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