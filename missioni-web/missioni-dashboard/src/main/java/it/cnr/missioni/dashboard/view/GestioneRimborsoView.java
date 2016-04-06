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
import com.vaadin.ui.Notification.Type;
import it.cnr.missioni.dashboard.DashboardUI;
import it.cnr.missioni.dashboard.client.ClientConnector;
import it.cnr.missioni.dashboard.component.table.ElencoRimborsiTable;
import it.cnr.missioni.dashboard.component.window.WizardSetupWindow;
import it.cnr.missioni.dashboard.component.window.admin.MissioneWindowAdmin;
import it.cnr.missioni.dashboard.component.window.admin.RimborsoWindowAdmin;
import it.cnr.missioni.dashboard.component.wizard.rimborso.WizardRimborso;
import it.cnr.missioni.dashboard.event.DashboardEvent.ResetSelectedMissioneRimborsoEvent;
import it.cnr.missioni.dashboard.event.DashboardEvent.TableRimborsiUpdatedEvent;
import it.cnr.missioni.dashboard.utility.AdvancedFileDownloader;
import it.cnr.missioni.dashboard.utility.AdvancedFileDownloader.AdvancedDownloaderListener;
import it.cnr.missioni.dashboard.utility.AdvancedFileDownloader.DownloaderEvent;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.el.model.search.builder.IMissioneSearchBuilder;
import it.cnr.missioni.el.model.search.builder.SearchConstants;
import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.model.user.User;
import it.cnr.missioni.rest.api.response.missione.MissioniStore;
import org.vaadin.pagingcomponent.listener.impl.LazyPagingComponentListener;

import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.util.Collection;

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
    protected ElencoRimborsiTable elencoRimborsiTable;
    protected Missione selectedMissione;
    protected IMissioneSearchBuilder missioneSearchBuilder;
    protected MissioniStore missioniStore;

    public GestioneRimborsoView() {
        super();
    }

    protected void inizialize() {
        this.missioneSearchBuilder = IMissioneSearchBuilder.MissioneSearchBuilder.getMissioneSearchBuilder()
                .withIdUser(getUser().getId()).withFieldExist("missione.rimborso")
                .withFieldSort(SearchConstants.MISSIONE_FIELD_RIMBORSO_DATA_RIMBORSO);
    }

    protected User getUser() {
        return DashboardUI.getCurrentUser();
    }

    /**
     * @return VerticalLayout
     */
    protected VerticalLayout buildTable() {
        VerticalLayout v = new VerticalLayout();
        this.elencoRimborsiTable = new ElencoRimborsiTable();
        this.elencoRimborsiTable.addItemClickListener(new ItemClickEvent.ItemClickListener() {
            /**
             *
             */
            private static final long serialVersionUID = 8533964899401961490L;

            @Override
            public void itemClick(ItemClickEvent itemClickEvent) {
                try {
                    selectedMissione = ClientConnector
                            .getMissione(IMissioneSearchBuilder.MissioneSearchBuilder.getMissioneSearchBuilder()
                                    .withId(((Missione) itemClickEvent.getItemId()).getId()))
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
        v.addComponent(this.elencoRimborsiTable);
        v.addComponent(layoutSelectPage);
        v.setComponentAlignment(elencoRimborsiTable,
                new Alignment(Bits.ALIGNMENT_VERTICAL_CENTER | Bits.ALIGNMENT_HORIZONTAL_CENTER));
        v.setComponentAlignment(layoutSelectPage,
                new Alignment(Bits.ALIGNMENT_VERTICAL_CENTER | Bits.ALIGNMENT_HORIZONTAL_CENTER));
        return v;
    }


    @Override
    public void enter(final ViewChangeEvent event) {
    }

    protected Button createButtonSearch() {
        buttonCerca = buildButton("", "Ricerca full text", FontAwesome.SEARCH);
        buttonCerca.addClickListener(new Button.ClickListener() {
            /**
             *
             */
            private static final long serialVersionUID = -4994850358099629357L;

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

    protected void aggiornaTable() {
        this.elencoRimborsiTable.aggiornaTable(missioniStore);
    }

    protected void openRimborsoDettgali() {
        if (selectedMissione.isRimborsoSetted())
//			RimborsoWindowAdmin.open(selectedMissione, false, false, true);
            RimborsoWindowAdmin.getRimborsoWindowAdmin().withBean(selectedMissione).withIsAdmin(false).withEnabled(false).withModifica(true).build();
        else
            WizardSetupWindow.getWizardSetup().withTipo(new WizardRimborso()).withMissione(selectedMissione).withUser(getUser()).withIsAdmin(false).withEnabled(true).withModifica(false)
                    .withEvent(new ResetSelectedMissioneRimborsoEvent())
                    .build();
    }

    protected HorizontalLayout addActionButtons() {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setSpacing(true);
        buttonDettagli = buildButton("Dettagli", "Visualizza i dettagli del Rimborso", FontAwesome.EDIT);
        buttonDettagli.addClickListener(new Button.ClickListener() {

            /**
             *
             */
            private static final long serialVersionUID = -8783796549904544814L;

            @Override
            public void buttonClick(ClickEvent event) {
                openRimborsoDettgali();
            }

        });

        buttonMissione = buildButton("Missone", "Visualizza i dati della Missione", FontAwesome.SUITCASE);
        buttonMissione.addClickListener(new Button.ClickListener() {

            /**
             *
             */
            private static final long serialVersionUID = 2610578422556405210L;

            @Override
            public void buttonClick(ClickEvent event) {
//				MissioneWindowAdmin.open(selectedMissione, true, false, true);
                MissioneWindowAdmin.getMissioneWindowAdmin().withBean(selectedMissione).withIsAdmin(true).withEnabled(false).withModifica(true);
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
        disableButtons();
        return layout;
    }


    private StreamResource getResource() {
        try {

            Response r = ClientConnector.downloadRimborsoMissioneAsPdf(selectedMissione.getId());
            InputStream is = r.readEntity(InputStream.class);
            StreamResource stream = new StreamResource(new StreamSource() {
                /**
                 *
                 */
                private static final long serialVersionUID = 8737605368476795000L;

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

    /**
     *
     */
    @Override
    protected void initPagination() {
        buildPagination(missioniStore != null ? missioniStore.getTotale() : 0);
        addListenerPagination();
    }

    /**
     * Aggiunge il listener alla paginazione
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
                elencoRimborsiTable.aggiornaTable(missioniStore);
                return missioniStore != null ? missioniStore.getMissioni() : null;
            }

            @Override
            protected Component displayItem(int index, Missione item) {
                return new Label(item.toString());
            }
        });
    }

    /**
     * Aggiorna la table e la paginazione a seguito di un inserimento o una
     * modifica
     */
    @Subscribe
    public void aggiornaTableMissione(final TableRimborsiUpdatedEvent event) {
        try {
            this.missioniStore = ClientConnector.getMissione(this.missioneSearchBuilder);
            elencoRimborsiTable.aggiornaTable(this.missioniStore);
            buildPagination(missioniStore.getTotale());
            addListenerPagination();
            disableButtons();
        } catch (Exception e) {
            Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
                    Type.ERROR_MESSAGE);
        }
    }

    /**
     * Reset missione se il wizard viene cancellato
     */
    @Subscribe
    public void resetSelectedMissione(final ResetSelectedMissioneRimborsoEvent event) {
        try {
            this.selectedMissione = ClientConnector
                    .getMissione(
                            IMissioneSearchBuilder.MissioneSearchBuilder.getMissioneSearchBuilder().withId(selectedMissione.getId()))
                    .getMissioni().get(0);

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
        this.buttonPDF.setEnabled(true);
        this.buttonMissione.setEnabled(true);
    }

    /**
     *
     */
    @Override
    protected void disableButtons() {
        this.buttonDettagli.setEnabled(false);
        this.buttonPDF.setEnabled(false);
        this.buttonMissione.setEnabled(false);
    }

}