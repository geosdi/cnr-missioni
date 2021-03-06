package it.cnr.missioni.notification.bridge.implementor.dev;

import it.cnr.missioni.notification.task.IMissioniMailNotificationTask;
import org.apache.velocity.app.VelocityEngine;
import org.geosdi.geoplatform.support.mail.configuration.detail.GPMailDetail;
import org.springframework.ui.velocity.VelocityEngineUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
public class UpdateRimborsoMissioneMailDev extends MissioniMailDev {

    /**
     * @param message
     * @param velocityEngine
     * @param gpMailDetail
     * @return {@link String}
     * @throws Exception
     */
    @Override
    public List<String> prepareMessage(IMissioniMailNotificationTask.IMissioneNotificationMessage message,
            VelocityEngine velocityEngine, GPMailDetail gpMailDetail) throws Exception {
        String userName = (String) message.getMessageParameters().get("userName");
        String userSurname = (String) message.getMessageParameters().get("userSurname");
        String rimborsoID = (String) message.getMessageParameters().get("rimborsoID");
        String pagata = (String) message.getMessageParameters().get("pagata");
        String mandatoPagamento = (String) message.getMessageParameters().get("mandatoPagamento");
        Double totaleDovuto = (Double) message.getMessageParameters().get("totaleDovuto");


        Map model = new HashMap();
        model.put("userName", userName);
        model.put("userSurname", userSurname);
        model.put("rimborsoID", rimborsoID);
        model.put("stato", pagata);
        model.put("mandatoPagamento", mandatoPagamento);
        model.put("totaleDovuto", totaleDovuto);
        List<String> lista = new ArrayList<String>();
        lista.add( VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,
                "template/updateRimborsoMissioneMailNotification.html.vm", "UTF-8", model));
        return lista;
    }

    /**
     * @return {@link ImplementorKey}
     */
    @Override
    public ImplementorKey getKey() {
        return NotificationMessageType.MODIFICA_RIMBORSO_MISSIONE_MAIL_DEV;
    }

    /**
     * <p>
     * Specify if {@link ImplementorKey} is valid or not
     * </p>
     *
     * @return {@link Boolean}
     */
    @Override
    public Boolean isImplementorValid() {
        return Boolean.TRUE;
    }
}
