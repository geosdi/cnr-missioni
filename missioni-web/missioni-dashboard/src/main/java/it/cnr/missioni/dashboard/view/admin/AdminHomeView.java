package it.cnr.missioni.dashboard.view.admin;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.Notification.Type;
import it.cnr.missioni.dashboard.client.ClientConnector;
import it.cnr.missioni.dashboard.component.table.admin.ElencoStatisticheMissioniTable;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.dashboard.view.HomeView;
import it.cnr.missioni.el.model.bean.StatisticheMissioni;
import it.cnr.missioni.el.model.search.builder.IMissioneSearchBuilder;
import it.cnr.missioni.el.model.search.builder.SearchConstants;

public final class AdminHomeView extends HomeView {

    /**
     *
     */
    private static final long serialVersionUID = 6899889980687530074L;
    //protected IMissioneSearchBuilder missioneSearchBuilder = IMissioneSearchBuilder.MissioneSearchBuilder.getMissioneSearchBuilder();

    public AdminHomeView() {
        super();
    }

    @Override
    public void enter(final ViewChangeEvent event) {
        try {
            elencoMissioniTable.aggiornaTableAdmin(ClientConnector.getMissione(IMissioneSearchBuilder.MissioneSearchBuilder.getMissioneSearchBuilder()));

            elencoRimborsiTable.aggiornaTableAdmin(ClientConnector.getMissione(IMissioneSearchBuilder.MissioneSearchBuilder.getMissioneSearchBuilder()
                    .withFieldExist(SearchConstants.MISSIONE_FIELD_RIMBORSO).withRimborsoCompleted(true)));
        } catch (Exception e) {
            Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
                    Type.ERROR_MESSAGE);
        }
    }

    @Override
    protected Component buildPanelBottom() {
        ElencoStatisticheMissioniTable elencoStatisticheMissioniTable = new ElencoStatisticheMissioniTable();
        elencoStatisticheMissioniTable.setCaption("Statistiche Missioni");
        try {
            StatisticheMissioni statisticheMissioni = ClientConnector.getStatisticheMissioni();
            elencoStatisticheMissioniTable.aggiornaTable(statisticheMissioni);
        } catch (Exception e) {
            Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
                    Type.ERROR_MESSAGE);
        }
        Component panel = createContentWrapper(elencoStatisticheMissioniTable);
        panel.setId("statistiche");
        return panel;
    }

}
