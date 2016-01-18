package it.cnr.missioni.rest.api.request;

import org.hibernate.validator.constraints.NotBlank;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
@XmlRootElement(name = "NotificationMissionRequest")
@XmlAccessorType(XmlAccessType.FIELD)
public class NotificationMissionRequest implements Serializable {

    private static final long serialVersionUID = -6172942577080715788L;
    //
    @NotBlank(message = "Il Parametro missionID non deve essere vuoto.")
    private String missionID;

    public NotificationMissionRequest() {
    }

    /**
     * @return {@link String}
     */
    public String getMissionID() {
        return missionID;
    }

    /**
     * @param missionID
     */
    public void setMissionID(String missionID) {
        this.missionID = missionID;
    }


    @Override
    public String toString() {
        return getClass().getSimpleName() + " {" +
                "  missionID = '" + missionID + '\'' +
                '}';
    }
}
