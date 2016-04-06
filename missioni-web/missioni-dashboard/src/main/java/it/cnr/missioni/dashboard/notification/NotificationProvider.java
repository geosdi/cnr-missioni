package it.cnr.missioni.dashboard.notification;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.vaadin.ui.Notification.Type;
import it.cnr.missioni.dashboard.DashboardUI;
import it.cnr.missioni.dashboard.client.ClientConnector;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.el.model.search.builder.IMissioneSearchBuilder;
import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.model.missione.StatoEnum;
import it.cnr.missioni.rest.api.response.missione.MissioniStore;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Salvia Vito
 */
public class NotificationProvider implements INotificationProvider {

    private final List<DashboardNotification> notifications = new ArrayList<DashboardNotification>();

    /**
     * Initialize the data for this application.
     */
    public NotificationProvider() {
    }

    public void check() {
        chechRegistrationComplete();
    }

    public void addPrenotazione(String prenotazione) {
        notifications.add(buildNotification(prenotazione, "Prenotazione"));
    }

    private void chechRegistrationComplete() {
        DashboardNotification n = null;
        List<Missione> lista = getMissioneWithoutRimborso();
        if (!isUserRegistrationComplete()) {
            notifications.add(buildNotification("Dati User non completi", "E' necessario completare la registrazione"));
        }
        lista.forEach(m -> {
            notifications.add(buildNotification("Id Missione: " + m.getId(), "Missione senza Rimborso"));
        });
    }

    /**
     * Verifica che l'user che si è loggato abbia completato la registrazione
     *
     * @return
     */
    private boolean isUserRegistrationComplete() {
        return DashboardUI.getCurrentUser().isRegistrazioneCompletata();
    }

    private DashboardNotification buildNotification(String content, String title) {
        DashboardNotification n = new DashboardNotification();
        n.setRead(false);
        n.setTitle(title);
        n.setContent(content);
        return n;
    }

    /**
     * Verifica che non ci siano missioni inserite più di un mese fa senza alcun rimborso
     *
     * @return
     */
    private List<Missione> getMissioneWithoutRimborso() {
        List<Missione> lista = new ArrayList<Missione>();
        IMissioneSearchBuilder missioneSearchBuilder = IMissioneSearchBuilder.MissioneSearchBuilder.getMissioneSearchBuilder().withIdUser(DashboardUI.getCurrentUser().getId())
                .withFieldNotExist("missione.rimborso")
                .withStato(StatoEnum.INSERITA.name());
        try {
            MissioniStore missioniStore = ClientConnector.getMissione(missioneSearchBuilder);
            if (missioniStore.getTotale() > 0)
                lista = missioniStore.getMissioni().stream().filter(m -> m.getDatiPeriodoMissione().getFineMissione().isBefore(new DateTime().minusDays(15))).collect(Collectors.toList());
        } catch (Exception e) {
            Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
                    Type.ERROR_MESSAGE);
        }
        return lista;
    }

    @Override
    public int getUnreadNotificationsCount() {
        Predicate<DashboardNotification> unreadPredicate = new Predicate<DashboardNotification>() {
            @Override
            public boolean apply(DashboardNotification input) {
                return !input.isRead();
            }
        };
        return Collections2.filter(notifications, unreadPredicate).size();
    }

    @Override
    public Collection<DashboardNotification> getNotifications() {
        for (DashboardNotification notification : notifications) {
            notification.setRead(true);
        }
        return Collections.unmodifiableCollection(notifications);
    }

}