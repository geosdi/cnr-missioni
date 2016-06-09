package it.cnr.missioni.dashboard.view.admin;

import com.google.common.eventbus.Subscribe;
import com.vaadin.ui.Notification.Type;
import it.cnr.missioni.dashboard.client.ClientConnector;
import it.cnr.missioni.dashboard.component.window.WizardSetupWindow;
import it.cnr.missioni.dashboard.component.window.admin.RimborsoWindowAdmin;
import it.cnr.missioni.dashboard.component.wizard.rimborso.WizardRimborso;
import it.cnr.missioni.dashboard.event.DashboardEvent.ResetSelectedMissioneRimborsoAdminEvent;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.dashboard.view.GestioneRimborsoView;
import it.cnr.missioni.el.model.search.builder.IMissioneSearchBuilder;
import it.cnr.missioni.el.model.search.builder.IUserSearchBuilder;
import it.cnr.missioni.el.model.search.builder.SearchConstants;
import it.cnr.missioni.model.missione.StatoEnum;
import it.cnr.missioni.model.user.User;

/**
 * @author Salvia Vito
 */
public class GestioneRimborsoAdminView extends GestioneRimborsoView {

    /**
     *
     */
    private static final long serialVersionUID = 8249728987254454501L;

    public GestioneRimborsoAdminView() {
        super();
    }

    protected void inizialize() {
        this.missioneSearchBuilder = IMissioneSearchBuilder.MissioneSearchBuilder.getMissioneSearchBuilder()
                .withFieldExist("missione.rimborso");
    }

    protected User getUser() {
        try {
            return ClientConnector
                    .getUser(IUserSearchBuilder.UserSearchBuilder.getUserSearchBuilder().withId(selectedMissione.getIdUser())).getUsers()
                    .get(0);
        } catch (Exception e) {
            Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
                    Type.ERROR_MESSAGE);
        }
        return null;
    }
    
    protected void resetSearchBuilder(){
        this.missioneSearchBuilder = IMissioneSearchBuilder.MissioneSearchBuilder.getMissioneSearchBuilder()
                .withFieldExist("missione.rimborso").withMultiMatch(multiMatchField.getValue());;
    }

    protected void openRimborsoDettgali() {
        if (selectedMissione.getStato() == StatoEnum.PAGATA)
//			RimborsoWindowAdmin.open(selectedMissione, true, false, true);
            RimborsoWindowAdmin.getRimborsoWindowAdmin().withBean(selectedMissione).withIsAdmin(true).withEnabled(false).withModifica(true).build();

        else
            WizardSetupWindow.getWizardSetup().withTipo(new WizardRimborso()).withMissione(selectedMissione).withUser(getUser()).withIsAdmin(true).withEnabled(true).withModifica(true).withEvent(new ResetSelectedMissioneRimborsoAdminEvent())
                    .build();
    }

    protected void aggiornaTable() {
        this.elencoRimborsiTable.aggiornaTableAdmin(missioniStore);
    }

    @Subscribe
    public void resetSelectedMissione(final ResetSelectedMissioneRimborsoAdminEvent event) {
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
}