package it.cnr.missioni.notification.bridge.implementor;

import java.util.List;

import org.apache.velocity.app.VelocityEngine;
import org.geosdi.geoplatform.support.mail.configuration.detail.GPMailDetail;

import it.cnr.missioni.notification.task.IMissioniMailNotificationTask;

/**
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
public interface MissioniMailImplementor<MESSAGE extends Object> extends Implementor {

	   /**
     * @param message
     * @param velocityEngine
     * @param gpMailDetail
     * @return {@link MESSAGE}
     * @throws Exception
     */
    List<MESSAGE> prepareMessage(IMissioniMailNotificationTask.IMissioneNotificationMessage message,
            VelocityEngine velocityEngine, GPMailDetail gpMailDetail) throws Exception;

    default String mailImplementorInfo() {
        return getClass().getSimpleName() + " {" +
                "  key = '" + getKey() + '\'' +
                ", valid = '" + isImplementorValid() + '\'' +
                '}';
    }

    /**
     *
     */
    enum NotificationMessageType implements ImplementorKey {
        AGGIUNGI_MISSIONE_MAIL_PROD("AGGIUNGI_MISSIONE_MAIL_PROD"),
        AGGIUNGI_MISSIONE_MAIL_DEV("AGGIUNGI_MISSIONE_MAIL_DEV"),
        AGGIUNGI_ANTICIPO_PAGAMENTO_MAIL_DEV("AGGIUNGI_ANTICIPO_PAGAMENTO_MAIL_DEV"),
        AGGIUNGI_ANTICIPO_PAGAMENTO_MAIL_PROD("AGGIUNGI_ANTICIPO_PAGAMENTO_MAIL_PROD"),
        MODIFICA_MISSIONE_MAIL_PROD("MODIFICA_MISSIONE_MAIL_PROD"),
        MODIFICA_MISSIONE_MAIL_DEV("MODIFICA_MISSIONE_MAIL_DEV"),
        MODIFICA_ANTICIPO_PAGAMENTO_MAIL_DEV("MODIFICA_ANTICIPO_PAGAMENTO_MAIL_DEV"),
        MODIFICA_ANTICIPO_PAGAMENTO_MAIL_PROD("MODIFICA_ANTICIPO_PAGAMENTO_MAIL_PROD"),
        RICHIESTA_RIMBORSO_MISSIONE_MAIL_PROD("RICHIESTA_RIMBORSO_MISSIONE_MAIL_PROD"),
        RICHIESTA_RIMBORSO_MISSIONE_MAIL_DEV("RICHIESTA_RIMBORSO_MISSIONE_MAIL_DEV"),
        RIMBORSO_MISSIONE_EFFETTUATO_MAIL_PROD("RIMBORSO_MISSIONE_EFFETTUATO_MAIL_PROD"),
        RIMBORSO_MISSIONE_EFFETTUATO_MAIL_DEV("RIMBORSO_MISSIONE_EFFETTUATO_MAIL_DEV"),
        MODIFICA_RIMBORSO_MISSIONE_MAIL_PROD("MODIFICA_RIMBORSO_MISSIONE_MAIL_PROD"),
        MODIFICA_RIMBORSO_MISSIONE_MAIL_DEV("MODIFICA_RIMBORSO_MISSIONE_MAIL_DEV"),
        RECUPERA_PASSWORD_MAIL_DEV("RECUPERA_PASSWORD_MAIL_DEV"),
        RECUPERA_PASSWORD_MAIL_PROD("RECUPERA_PASSWORD_MAIL_PROD"),
        NOTIFY_USERS_IN_MISSIONE_MAIL_DEV("NOTIFY_USERS_IN_MISSIONE_MAIL_DEV"),
        NOTIFY_USERS_IN_MISSIONE_MAIL_PROD("NOTIFY_USERS_IN_MISSIONE_MAIL_PROD");


        private final String value;

        NotificationMessageType(String theValue) {
            this.value = theValue;
        }

        @Override
        public String toString() {
            return this.value;
        }

        @Override
        public String getImplementorKey() {
            return this.value;
        }

        public static ImplementorKey fromString(String value) {
            for (NotificationMessageType key : NotificationMessageType.values()) {
                if (key.getImplementorKey().equalsIgnoreCase(value)) {
                    return key;
                }
            }
            return null;
        }
    }
}
