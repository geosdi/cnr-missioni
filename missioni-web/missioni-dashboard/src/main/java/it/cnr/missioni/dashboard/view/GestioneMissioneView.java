package it.cnr.missioni.dashboard.view;

import com.google.common.eventbus.Subscribe;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.StreamResource;
import com.vaadin.server.StreamResource.StreamSource;
import com.vaadin.shared.ui.AlignmentInfo.Bits;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification.Type;
import it.cnr.missioni.dashboard.DashboardUI;
import it.cnr.missioni.dashboard.client.ClientConnector;
import it.cnr.missioni.dashboard.component.table.ElencoMissioniTable;
import it.cnr.missioni.dashboard.component.window.AnticipoPagamentiWindow;
import it.cnr.missioni.dashboard.component.window.WizardSetupWindow;
import it.cnr.missioni.dashboard.component.window.admin.MissioneWindowAdmin;
import it.cnr.missioni.dashboard.component.window.admin.RimborsoWindowAdmin;
import it.cnr.missioni.dashboard.component.wizard.missione.WizardMissione;
import it.cnr.missioni.dashboard.component.wizard.rimborso.WizardRimborso;
import it.cnr.missioni.dashboard.event.DashboardEvent.ResetSelectedMissioneAdminEvent;
import it.cnr.missioni.dashboard.event.DashboardEvent.ResetSelectedMissioneEvent;
import it.cnr.missioni.dashboard.event.DashboardEvent.ResetSelectedMissioneRimborsoEvent;
import it.cnr.missioni.dashboard.event.DashboardEvent.TableMissioniUpdateUpdatedEvent;
import it.cnr.missioni.dashboard.utility.AdvancedFileDownloader;
import it.cnr.missioni.dashboard.utility.AdvancedFileDownloader.AdvancedDownloaderListener;
import it.cnr.missioni.dashboard.utility.AdvancedFileDownloader.DownloaderEvent;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.el.model.search.builder.MissioneSearchBuilder;
import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.model.missione.StatoEnum;
import it.cnr.missioni.model.rimborso.Rimborso;
import it.cnr.missioni.model.user.User;
import it.cnr.missioni.rest.api.response.missione.MissioniStore;
import org.vaadin.pagingcomponent.listener.impl.LazyPagingComponentListener;

import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.util.Collection;

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
	protected ElencoMissioniTable elencoMissioniTable;
	protected Missione selectedMissione;
	protected HorizontalLayout layout;
	protected MissioneSearchBuilder missioneSearchBuilder;
	protected MissioniStore missioniStore;

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

	protected User getUser() {
		return DashboardUI.getCurrentUser();
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

		this.elencoMissioniTable.addItemClickListener(new ItemClickEvent.ItemClickListener() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 8667294597657389614L;

			@Override
			public void itemClick(ItemClickEvent itemClickEvent) {

				try {
					selectedMissione = ClientConnector
							.getMissione(MissioneSearchBuilder.getMissioneSearchBuilder()
									.withIdMissione(((Missione) itemClickEvent.getItemId()).getId()))
							.getMissioni().get(0);
					enableButtons();
				} catch (Exception e) {
					Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
							Type.ERROR_MESSAGE);
				}
			}
		});

		try {
			missioniStore = ClientConnector.getMissione(missioneSearchBuilder);
			aggiornaTable();

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

	protected void aggiornaTable() {
		this.elencoMissioniTable.aggiornaTable(missioniStore);
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
					aggiornaTable();

				} catch (Exception e) {
					Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
							Type.ERROR_MESSAGE);
				}
			}

		});
		return buttonCerca;
	}
	

	protected void openWizardMissione() {
		WizardSetupWindow.getWizardSetup().withTipo(new WizardMissione()).withMissione(new Missione())
				.withUser(getUser()).withIsAdmin(false).withEnabled(true).withModifica(false)
				.withEvent(new ResetSelectedMissioneEvent())
				.build();
	}

	protected HorizontalLayout addActionButtons() {
		layout = new HorizontalLayout();
		// layout.setWidth("100%");
		layout.setSpacing(true);

		buttonNew = buildButton("Nuova Missione", "Crea una nuova Missione", FontAwesome.PLUS);
		buttonNew.addClickListener(new Button.ClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 5222232755705291087L;

			@Override
			public void buttonClick(ClickEvent event) {
				selectedMissione = null;
				elencoMissioniTable.select(null);
				openWizardMissione();
			}

		});

		buttonDettagli = buildButton("Dettagli", "Visualizza i dettagli della Missione", FontAwesome.EDIT);
		buttonDettagli.addClickListener(new Button.ClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 4610960850985679736L;

			@Override
			public void buttonClick(ClickEvent event) {

				addActionButtonDettagli();
			}

		});

		buttonRimborso = buildButton("Rimborso", "Visualizza i dettagli del Rimborso", FontAwesome.EURO);
		buttonRimborso.addClickListener(new Button.ClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -7139259469623824900L;

			@Override
			public void buttonClick(ClickEvent event) {
				addActionButtonRimborso();

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

		this.buttonAnticipoPagamento = buildButton("Anticipo", "Anticipo Pagamento", FontAwesome.EURO);
		this.buttonAnticipoPagamento.addClickListener(new ClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1757314072772842924L;

			@Override
			public void buttonClick(ClickEvent event) {
				openWindowAnticipoPagamenti();

			}

		});

		this.buttonAnticipoPagamentoPdf = buildButton("Anticipo", "Pdf Anticipo Pagamento", FontAwesome.EURO);

		final AdvancedFileDownloader anticipoPagamentoDownloaderForLink = new AdvancedFileDownloader();
		anticipoPagamentoDownloaderForLink.addAdvancedDownloaderListener(new AdvancedDownloaderListener() {
			@Override
			public void beforeDownload(DownloaderEvent downloadEvent) {
				downloadAnticipoPagamentoAsPdf(anticipoPagamentoDownloaderForLink);
			}

		});
		anticipoPagamentoDownloaderForLink.extend(buttonAnticipoPagamentoPdf);

		addButtonsToLayout();
		disableButtons();

		return layout;

	}

	protected void addButtonsToLayout() {
		layout.addComponents(buttonNew, buttonDettagli, buttonAnticipoPagamento, buttonAnticipoPagamentoPdf,
				buttonRimborso, buttonPDF, buttonVeicoloMissionePDF);
	}

	protected void openWindowAnticipoPagamenti() {
		AnticipoPagamentiWindow.open(selectedMissione, false, true, false);
	}

	protected void downloadAnticipoPagamentoAsPdf(AdvancedFileDownloader anticipoPagamentoDownloaderForLink) {
		anticipoPagamentoDownloaderForLink.setFileDownloadResource(getResourceAnticipoPagamento());
	}

	protected void addActionButtonDettagli() {
		MissioneWindowAdmin.open(selectedMissione, true, false, true);

	}

	protected boolean enableButtonWindowAnticipoPagamento() {
		return selectedMissione != null && !selectedMissione.getDatiAnticipoPagamenti().isInserted()
				&& selectedMissione.isMissioneEstera();
	}

	protected boolean enableButtonDownloadPdf() {
		return selectedMissione != null && selectedMissione.isMissioneEstera()
				&& selectedMissione.getDatiAnticipoPagamenti().isInserted();
	}

	protected void addActionButtonRimborso() {
		// se è già associato il rimborso
		if (selectedMissione.isRimborsoSetted()) {
			RimborsoWindowAdmin.open(selectedMissione, false, false, true);

		} else {
			selectedMissione.setRimborso(new Rimborso());
			WizardSetupWindow.getWizardSetup().withTipo(new WizardRimborso()).withMissione(selectedMissione)
					.withUser(this.getUser()).withEnabled(true).withIsAdmin(false).withModifica(false).build();
		}

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

	protected StreamResource getResourceAnticipoPagamento() {
		try {

			Response r = ClientConnector.downloadAnticipoPagamentoAsPdf(selectedMissione.getId());

			InputStream is = r.readEntity(InputStream.class);

			StreamResource stream = new StreamResource(new StreamSource() {

				/**
				 * 
				 */
				private static final long serialVersionUID = -2020953643006677106L;

				@Override
				public InputStream getStream() {
					return is;
				}
			}, "anticipoPagamento.pdf");

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
		this.buttonAnticipoPagamento.setVisible(enableButtonWindowAnticipoPagamento());
		this.buttonAnticipoPagamentoPdf.setVisible(enableButtonDownloadPdf());
		this.buttonAnticipoPagamento.setEnabled(enableButtonWindowAnticipoPagamento());
		this.buttonAnticipoPagamentoPdf.setEnabled(enableButtonDownloadPdf());
		if (!buttonAnticipoPagamentoPdf.isVisible())
			buttonAnticipoPagamento.setVisible(true);
	}

	protected void disableButtons() {
		this.buttonDettagli.setEnabled(false);
		this.buttonPDF.setEnabled(false);
		this.buttonRimborso.setEnabled(false);
		this.buttonVeicoloMissionePDF.setEnabled(false);
		this.buttonVeicoloMissionePDF.setEnabled(false);
		this.buttonAnticipoPagamento.setEnabled(false);
		this.buttonAnticipoPagamentoPdf.setVisible(false);

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
			buildPagination(missioniStore.getTotale());
			addListenerPagination();
			disableButtons();
		} catch (Exception e) {
			Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
					Type.ERROR_MESSAGE);
		}

	}

	/**
	 * Reset missione se il wizard rimborso viene cancellato
	 */
	@Subscribe
	public void resetSelectedMissione(final ResetSelectedMissioneEvent event) {
		try {
			this.selectedMissione = ClientConnector
					.getMissione(
							MissioneSearchBuilder.getMissioneSearchBuilder().withIdMissione(selectedMissione.getId()))
					.getMissioni().get(0);

		} catch (Exception e) {
			Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
					Type.ERROR_MESSAGE);
		}
	}

}