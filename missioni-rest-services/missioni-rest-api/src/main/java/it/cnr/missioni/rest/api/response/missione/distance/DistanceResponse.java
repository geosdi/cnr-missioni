package it.cnr.missioni.rest.api.response.missione.distance;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
public interface DistanceResponse extends Serializable {

    /**
     * @return {@link String}
     */
    String getDistance();

    /**
     * @param theDistance
     */
    void setDistance(String theDistance);

    /**
     * @return {@link String}
     */
    String getDuration();

    /**
     * @param theDuration
     */
    void setDuration(String theDuration);

    /**
     *
     */
    @XmlRootElement(name = "MissioneDistanceResponse")
    @XmlAccessorType(XmlAccessType.FIELD)
    class MissioneDistanceResponse implements DistanceResponse {

        private static final long serialVersionUID = -1166804837229700778L;
        //
        private String distance;
        private String duration;

        public MissioneDistanceResponse() {
        }

        public MissioneDistanceResponse(String theDistance, String theDuration) {
            this.distance = theDistance;
            this.duration = theDuration;
        }

        /**
         * @return {@link String}
         */
        @Override
        public String getDistance() {
            return this.distance;
        }

        /**
         * @param theDistance
         */
        @Override
        public void setDistance(String theDistance) {
            this.distance = theDistance;
        }

        /**
         * @return {@link String}
         */
        @Override
        public String getDuration() {
            return this.duration;
        }

        /**
         * @param theDuration
         */
        @Override
        public void setDuration(String theDuration) {
            this.duration = theDuration;
        }

        @Override
        public String toString() {
            return getClass().getSimpleName() + " {" +
                    "  distance = '" + distance + '\'' +
                    ", duration = '" + duration + '\'' +
                    '}';
        }
    }
}
