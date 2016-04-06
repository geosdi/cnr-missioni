package it.cnr.missioni.dashboard.view.admin;

import com.google.common.eventbus.Subscribe;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.AlignmentInfo.Bits;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Notification.Type;
import it.cnr.missioni.dashboard.client.ClientConnector;
import it.cnr.missioni.dashboard.component.table.admin.ElencoNazioneTable;
import it.cnr.missioni.dashboard.component.window.admin.NazioneWindow;
import it.cnr.missioni.dashboard.event.DashboardEvent.TableNazioneUpdatedEvent;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.dashboard.view.GestioneTemplateView;
import it.cnr.missioni.el.model.search.builder.INazioneSearchBuilder;
import it.cnr.missioni.model.configuration.Nazione;
import it.cnr.missioni.rest.api.response.nazione.NazioneStore;
import org.vaadin.pagingcomponent.listener.impl.LazyPagingComponentListener;

import java.util.Collection;

/**
 * @author Salvia Vito
 */
public class GestioneNazioneView extends GestioneTemplateView<Nazione> {

    /**
     *
     */
    private static final long serialVersionUID = 8210899238444116295L;
    private ElencoNazioneTable elencoNazioneTable;
    private Nazione selectedNazione;
    private NazioneStore nazioneStore;
    private INazioneSearchBuilder nazioneSearchBuilder;

    public GestioneNazioneView() {
        super();
    }

    protected void inizialize() {
        this.nazioneSearchBuilder = INazioneSearchBuilder.NazioneSearchBuilder.getNazioneSearchBuilder();
    }

    /**
     * @return VerticalLayout
     */
    protected VerticalLayout buildTable() {
        VerticalLayout v = new VerticalLayout();
        this.elencoNazioneTable = new ElencoNazioneTable();
        try {
            nazioneStore = ClientConnector.getNazione(nazioneSearchBuilder);
            this.elencoNazioneTable.aggiornaTable(nazioneStore);
        } catch (Exception e) {
            Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
                    Type.ERROR_MESSAGE);
        }
        this.elencoNazioneTable.addItemClickListener(new ItemClickEvent.ItemClickListener() {
            /**
             *
             */
            private static final long serialVersionUID = -5989135363149394894L;

            @Override
            public void itemClick(ItemClickEvent itemClickEvent) {
                try {
                    selectedNazione = ClientConnector
                            .getNazione(INazioneSearchBuilder.NazioneSearchBuilder.getNazioneSearchBuilder()
                                    .withId(((Nazione) itemClickEvent.getItemId()).getId()))
                            .getNazione().get(0);
                    enableButtons();
                } catch (Exception e) {
                    Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
                            Type.ERROR_MESSAGE);
                }
            }
        });
        v.addComponent(this.elencoNazioneTable);
        v.setComponentAlignment(elencoNazioneTable,
                new Alignment(Bits.ALIGNMENT_VERTICAL_CENTER | Bits.ALIGNMENT_HORIZONTAL_CENTER));
        return v;
    }

    @Override
    public void enter(final ViewChangeEvent event) {
    }


    protected HorizontalLayout addActionButtons() {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setSpacing(true);
        buttonNew = buildButton("Aggiungi Nazione", "Inserisce una nuova nazione", FontAwesome.PLUS);
        buttonNew.addClickListener(new Button.ClickListener() {

            /**
             *
             */
            private static final long serialVersionUID = -9055323128504245147L;

            @Override
            public void buttonClick(ClickEvent event) {
                selectedNazione = null;
                elencoNazioneTable.select(null);
//				NazioneWindow.open(new Nazione(),true,true, false);
                NazioneWindow.getNazioneWindow().withBean(new Nazione()).withIsAdmin(true).withEnabled(true).withModifica(false).build();
            }

        });

        buttonModifica = buildButton("Modifica", "Modifica", FontAwesome.PENCIL);
        buttonModifica.addClickListener(new Button.ClickListener() {

            /**
             *
             */
            private static final long serialVersionUID = 2128352915934713836L;

            @Override
            public void buttonClick(ClickEvent event) {
//				NazioneWindow.open(selectedNazione,true,true, true);
                NazioneWindow.getNazioneWindow().withBean(selectedNazione).withIsAdmin(true).withEnabled(true).withModifica(true).build();
            }
        });
        layout.addComponents(buttonNew, buttonModifica);
        disableButtons();
        return layout;

    }

    /**
     *
     */
    @Override
    protected void initPagination() {
        buildPagination(nazioneStore.getTotale());
        addListenerPagination();
    }

    /**
     * Aggiunge il listener alla paginazione
     */
    protected void addListenerPagination() {

        pagingComponent.addListener(new LazyPagingComponentListener<Nazione>(itemsArea) {

            /**
             *
             */
            private static final long serialVersionUID = 4769881945185530673L;

            @Override
            protected Collection<Nazione> getItemsList(int startIndex, int endIndex) {
                try {
                    nazioneStore = ClientConnector.getNazione(nazioneSearchBuilder.withFrom(startIndex));
                } catch (Exception e) {
                    Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
                            Type.ERROR_MESSAGE);
                }
                elencoNazioneTable.aggiornaTable(nazioneStore);
                return nazioneStore != null ? nazioneStore.getNazione() : null;
            }

            @Override
            protected Component displayItem(int index, Nazione item) {
                return new Label(item.toString());
            }
        });
    }

    /**
     * @return
     */
    @Override
    protected Button createButtonSearch() {
        return null;
    }

    protected Component buildFilter() {
        return null;
    }

    /**
     * Aggiorna la table e la paginazione a seguito di un inserimento o una
     * modifica
     */
    @Subscribe
    public void aggiornaTableNazione(final TableNazioneUpdatedEvent event) {
        try {
            this.nazioneStore = ClientConnector.getNazione(this.nazioneSearchBuilder);
            elencoNazioneTable.aggiornaTable(this.nazioneStore);
            buildPagination(nazioneStore.getTotale());
            addListenerPagination();
            disableButtons();
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
        this.buttonModifica.setEnabled(true);

    }

    /**
     *
     */
    @Override
    protected void disableButtons() {
        this.buttonModifica.setEnabled(false);
    }

}