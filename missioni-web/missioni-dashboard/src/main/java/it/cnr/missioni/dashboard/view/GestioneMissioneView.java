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
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.VerticalLayout;

import it.cnr.missioni.dashboard.DashboardUI;
import it.cnr.missioni.dashboard.client.ClientConnector;
import it.cnr.missioni.dashboard.component.table.ElencoMissioniTable;
import it.cnr.missioni.dashboard.component.window.WizardSetupWindow;
import it.cnr.missioni.dashboard.component.window.admin.MissioneWindowAdmin;
import it.cnr.missioni.dashboard.component.window.admin.RimborsoWindowAdmin;
import it.cnr.missioni.dashboard.component.wizard.missione.WizardMissione;
import it.cnr.missioni.dashboard.component.wizard.rimborso.WizardRimborso;
import it.cnr.missioni.dashboard.event.DashboardEvent.TableMissioniUpdateUpdatedEvent;
import it.cnr.missioni.dashboard.utility.AdvancedFileDownloader;
import it.cnr.missioni.dashboard.utility.AdvancedFileDownloader.AdvancedDownloaderListener;
import it.cnr.missioni.dashboard.utility.AdvancedFileDownloader.DownloaderEvent;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.el.model.search.builder.MissioneSearchBuilder;
import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.model.missione.StatoEnum;
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
	protected Missione selectedMissione;
	protected GridLayout layout;
	protected MissioneSearchBuilder missioneSearchBuilder;
	private MissioniStore missioniStore;


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
		this.missioneSearchBuilder = MissioneSearchBuilder.getMissioneSearchBuilder();

		this.elencoMissioniTable.addItemClickListener(new ItemClickEvent.ItemClickListener() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 8667294597657389614L;

			@Override
			public void itemClick(ItemClickEvent itemClickEvent) {
				selectedMissione = (Missione) itemClickEvent.getItemId();
				enableButtons();
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

	protected Button createButtonSearch() {
		buttonCerca = buildButton("", "Ricerca full text", FontAwesome.SEARCH);
		buttonCerca.addClickListener(new Button.ClickListener() {
			/**
			 * 
			 */
			private static final long serialVersionUID = -8037478017978761885L;

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
		layout = new GridLayout(5, 1);
		layout.setSpacing(true);

		buttonNew = buildButton("Nuova Missione", "Crea una nuova Missione", FontAwesome.PLUS);
		buttonNew.addClickListener(new Button.ClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 5222232755705291087L;

			@Override
			public void buttonClick(ClickEvent event) {
				WizardSetupWindow.getWizardSetup().withTipo(new WizardMissione()).withMissione(new Missione()).build();
			}

		});

		buttonDettagli = buildButton("Dettagli", "Visualizza i dettagli della Missione", FontAwesome.EDIT);
		buildButtonDettagli();

		buttonRimborso = buildButton("Rimborso", "Visualizza i dettagli del Rimborso", FontAwesome.EURO);
		buildButtonRimborso();

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

		addButtonsToLayout();
		disableButtons();

		return layout;

	}

	protected void addButtonsToLayout() {
		layout.addComponents(buttonNew, buttonDettagli, buttonRimborso, buttonPDF, buttonVeicoloMissionePDF);
	}

	protected void buildButtonDettagli() {
		buttonDettagli.addClickListener(new Button.ClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 4610960850985679736L;

			@Override
			public void buttonClick(ClickEvent event) {

				MissioneWindowAdmin.open(selectedMissione, false, true);

			}

		});
	}

	protected void buildButtonRimborso() {
		buttonRimborso.addClickListener(new Button.ClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -7139259469623824900L;

			@Override
			public void buttonClick(ClickEvent event) {
				Rimborso rimborso = null;
				// se è già associato il rimborso
				if (selectedMissione.isRimborsoSetted()) {
					rimborso = selectedMissione.getRimborso();
					RimborsoWindowAdmin.open(selectedMissione, true, false,false);

				} else {
					rimborso = new Rimborso();
					selectedMissione.setRimborso(rimborso);
					WizardSetupWindow.getWizardSetup().withTipo(new WizardRimborso()).withMissione(selectedMissione)
							.build();
				}

			}

		});
	}

	protected StreamResource getResource() {
		try {

			Response r = ClientConnector.downloadMissioneAsPdf(selectedMissione.getId());

			InputStream is = r.readEntity(InputStream.class);

			StreamResource stream = new StreamResource(new StreamSource() {
				/**
				 * 
				 */
				private static final long serialVersionUID = -3569207106691297833L;

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
				/**
				 * 
				 */
				private static final long serialVersionUID = -8267176484781453078L;

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


	protected void enableButtons() {
		this.buttonDettagli.setEnabled(true);
		this.buttonPDF.setEnabled(true);
		this.buttonRimborso.setEnabled(selectedMissione.getStato() != StatoEnum.RESPINTA);
		this.buttonVeicoloMissionePDF.setEnabled(selectedMissione.isMezzoProprio());
	}

	protected void disableButtons() {
		this.buttonDettagli.setEnabled(false);
		this.buttonPDF.setEnabled(false);
		this.buttonRimborso.setEnabled(false);
		buttonVeicoloMissionePDF.setEnabled(false);
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