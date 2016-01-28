package it.cnr.missioni.rest.api.response.geocoder;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
@XmlRootElement(name = "GeocoderResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class GeocoderResponse implements IGeocoderResponse {

    private static final long serialVersionUID = 2054698550628082441L;
    //
    private String formattedAddress;
    private Double lat;
    private Double lon;

    /**
     * <p>Only for JAXB</p>
     */
    public GeocoderResponse() {
    }

    public GeocoderResponse(String formattedAddress, Double lat, Double lon) {
        this.formattedAddress = formattedAddress;
        this.lat = lat;
        this.lon = lon;
    }

    /**
     * @return {@link String}
     */
    @Override
    public String getFormattedAddress() {
        return this.formattedAddress;
    }

    /**
     * @param theFormattedAddress
     */
    @Override
    public void setFormattedAddress(String theFormattedAddress) {
        this.formattedAddress = theFormattedAddress;
    }

    /**
     * @return {@link Double}
     */
    @Override
    public Double getLat() {
        return this.lat;
    }

    /**
     * @param theLat
     */
    @Override
    public void setLat(Double theLat) {
        this.lat = theLat;
    }

    /**
     * @return {@link Double}
     */
    @Override
    public Double getLon() {
        return this.lon;
    }

    /**
     * @param theLon
     */
    @Override
    public void setLon(Double theLon) {
        this.lon = theLon;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " {" +
                "  formattedAddress = '" + formattedAddress + '\'' +
                ", lat = " + lat +
                ", lon = " + lon +
                '}';
    }
}
