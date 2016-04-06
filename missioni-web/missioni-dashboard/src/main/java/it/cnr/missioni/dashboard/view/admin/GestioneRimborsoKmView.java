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
import it.cnr.missioni.dashboard.component.table.admin.ElencoRimborsoKmTable;
import it.cnr.missioni.dashboard.component.window.admin.RimborsoKmWindow;
import it.cnr.missioni.dashboard.event.DashboardEvent.DisableButtonNewEvent;
import it.cnr.missioni.dashboard.event.DashboardEvent.TableRimborsoKmUpdatedEvent;
import it.cnr.missioni.dashboard.event.DashboardEventBus;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.dashboard.view.GestioneTemplateView;
import it.cnr.missioni.el.model.search.builder.IRimborsoKmSearchBuilder;
import it.cnr.missioni.model.configuration.RimborsoKm;
import it.cnr.missioni.rest.api.response.rimborsoKm.RimborsoKmStore;

/**
 * @author Salvia Vito
 */
public class GestioneRimborsoKmView extends GestioneTemplateView<RimborsoKm> {

    /**
     *
     */
    private static final long serialVersionUID = 8210899238444116295L;
    private ElencoRimborsoKmTable elencoRimborsoKmTable;
    private RimborsoKm selectedRimborsoKm;
    private RimborsoKmStore rimborsoKmStore;

    public GestioneRimborsoKmView() {
        super();
        DashboardEventBus.register(this);
    }

    protected void inizialize() {

    }

    /**
     * @return VerticalLayout
     */
    protected VerticalLayout buildTable() {
        VerticalLayout v = new VerticalLayout();
        this.elencoRimborsoKmTable = new ElencoRimborsoKmTable();
        try {
            rimborsoKmStore = ClientConnector.getRimborsoKm(IRimborsoKmSearchBuilder.RimborsoKmSearchBuilder.getRimborsoKmSearchBuilder());
            this.elencoRimborsoKmTable.aggiornaTable(rimborsoKmStore);
        } catch (Exception e) {
            Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
                    Type.ERROR_MESSAGE);
        }
        this.elencoRimborsoKmTable.addItemClickListener(new ItemClickEvent.ItemClickListener() {
            /**
             *
             */
            private static final long serialVersionUID = -3431149668543463964L;

            @Override
            public void itemClick(ItemClickEvent itemClickEvent) {
                selectedRimborsoKm = (RimborsoKm) itemClickEvent.getItemId();
                enableButtons();
            }
        });
        v.addComponent(this.elencoRimborsoKmTable);
        v.setComponentAlignment(elencoRimborsoKmTable,
                new Alignment(Bits.ALIGNMENT_VERTICAL_CENTER | Bits.ALIGNMENT_HORIZONTAL_CENTER));
        return v;
    }

    @Override
    public void enter(final ViewChangeEvent event) {

    }

    protected HorizontalLayout addActionButtons() {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setSpacing(true);

        buttonNew = buildButton("Aggiungi Rimborso Km", "Inserisce un nuovo rimborso km", FontAwesome.PLUS);
        this.buttonNew.addClickListener(new Button.ClickListener() {

            /**
             *
             */
            private static final long serialVersionUID = 4653736027093331024L;

            @Override
            public void buttonClick(ClickEvent event) {
//				RimborsoKmWindow.open(new RimborsoKm(),true,true, false);
                RimborsoKmWindow.getRimborsoKmWindow().withBean(new RimborsoKm()).withIsAdmin(true).withEnabled(true).withModifica(false).build();
            }

        });

        buttonModifica = buildButton("Modifica", "Modifica", FontAwesome.PENCIL);
        buttonModifica.addClickListener(new Button.ClickListener() {

            /**
             *
             */
            private static final long serialVersionUID = -2268707095518228299L;

            @Override
            public void buttonClick(ClickEvent event) {
//				RimborsoKmWindow.open(selectedRimborsoKm, true,true,true);
                RimborsoKmWindow.getRimborsoKmWindow().withBean(selectedRimborsoKm).withIsAdmin(true).withEnabled(true).withModifica(true).build();
            }
        });

        layout.addComponents(buttonNew, buttonModifica);
        if (rimborsoKmStore.getTotale() == 0)
            buttonNew.setVisible(true);
        else
            buttonNew.setVisible(false);
        disableButtons();
        return layout;
    }

//	protected void enableDisableButtons(boolean enabled) {
//		this.buttonModifica.setEnabled(enabled);
//	}

    /**
     *
     */
    @Override
    protected void initPagination() {
        buildPagination(rimborsoKmStore != null ? rimborsoKmStore.getTotale() : 0);
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
     * Disabilita il button al primo inserimento del rimborso km
     *
     * @param disableButtonNew
     */
    @Subscribe
    public void disabledButtonNew(final DisableButtonNewEvent disableButtonNew) {
        this.buttonNew.setVisible(false);
    }

    /**
     *
     */
    @Override
    protected void addListenerPagination() {
    }

    /**
     * Aggiorna la table a seguito di un inserimento o modifica
     */
    @Subscribe
    public void aggiornaTableRimborsoKm(final TableRimborsoKmUpdatedEvent event) {
        elencoRimborsoKmTable.aggiornaTable(event.getRimborsoKmStore());
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