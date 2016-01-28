package it.cnr.missioni.rest.api.response.geocoder;

import java.io.Serializable;
import java.util.List;

/**
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
public interface IGeocoderStore extends Serializable {

    /**
     * @return {@link List<IGeocoderResponse>}
     */
    List<IGeocoderResponse> getGeocoderResponses();

    /**
     * @param theGeocoderResponses
     */
    void setGeocoderResponses(List<IGeocoderResponse> theGeocoderResponses);

    /**
     * @param geocoderResponse
     */
    void addGeocoderResponse(IGeocoderResponse geocoderResponse);

    /**
     * @return {@link Boolean}
     */
    Boolean isValid();
}
