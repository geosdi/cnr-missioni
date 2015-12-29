package it.cnr.missioni.rest.api.response.missione;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;
import it.cnr.missioni.model.missione.Missione;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
@JsonRootName(value = "MissioniStore")
@JsonPropertyOrder(value = {"userID", "missioni"})
public class MissioniStore implements Serializable {

    private static final long serialVersionUID = -1456652972409122182L;
    //
    @JsonProperty(value = "userID", required = true)
    private String userID;
    @JsonProperty(value = "missioni", required = false)
    private List<Missione> missioni;

    public MissioniStore() {
    }

    public MissioniStore(String theUserID, List<Missione> theMissioni) {
        this.setUserID(theUserID);
        this.setMissioni(theMissioni);
    }


    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public List<Missione> getMissioni() {
        return missioni;
    }

    public void setMissioni(List<Missione> missioni) {
        this.missioni = missioni;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MissioniStore that = (MissioniStore) o;
        return Objects.equals(userID, that.userID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userID);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " {" +
                "userID='" + userID + '\'' +
                ", missioni=" + missioni +
                '}';
    }
}
