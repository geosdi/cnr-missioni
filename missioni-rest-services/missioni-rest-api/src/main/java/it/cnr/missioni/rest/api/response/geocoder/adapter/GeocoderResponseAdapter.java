
package it.cnr.missioni.rest.api.response.geocoder.adapter;

import it.cnr.missioni.rest.api.response.geocoder.GeocoderResponse;
import it.cnr.missioni.rest.api.response.geocoder.IGeocoderResponse;

import javax.xml.bind.ValidationEventHandler;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
public class GeocoderResponseAdapter extends XmlAdapter<GeocoderResponse, IGeocoderResponse> {

    /**
     * Convert a value type to a bound type.
     *
     * @param v The value to be converted. Can be null.
     * @throws Exception if there's an error during the conversion. The caller is responsible for
     *                   reporting the error to the user through {@link ValidationEventHandler}.
     */
    @Override
    public IGeocoderResponse unmarshal(GeocoderResponse v) throws Exception {
        return v;
    }

    /**
     * Convert a bound type to a value type.
     *
     * @param v The value to be convereted. Can be null.
     * @throws Exception if there's an error during the conversion. The caller is responsible for
     *                   reporting the error to the user through {@link ValidationEventHandler}.
     */
    @Override
    public GeocoderResponse marshal(IGeocoderResponse v) throws Exception {
        return (GeocoderResponse) v;
    }
}