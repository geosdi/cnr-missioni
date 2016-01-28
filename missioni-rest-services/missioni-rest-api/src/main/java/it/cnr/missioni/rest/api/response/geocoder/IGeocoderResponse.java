package it.cnr.missioni.rest.api.response.geocoder;

import java.io.Serializable;

/**
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
public interface IGeocoderResponse extends Serializable {

    /**
     * @return {@link String}
     */
    String getFormattedAddress();

    /**
     * @param theFormattedAddress
     */
    void setFormattedAddress(String theFormattedAddress);

    /**
     * @return {@link Double}
     */
    Double getLat();

    /**
     * @param theLat
     */
    void setLat(Double theLat);


    /**
     * @return {@link Double}
     */
    Double getLon();

    /**
     * @param theLon
     */
    void setLon(Double theLon);
}
