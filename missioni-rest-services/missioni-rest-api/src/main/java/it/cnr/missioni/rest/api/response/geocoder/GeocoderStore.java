package it.cnr.missioni.rest.api.response.geocoder;

import com.google.common.collect.Lists;
import it.cnr.missioni.rest.api.response.geocoder.adapter.GeocoderResponseAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.List;

/**
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
@XmlRootElement(name = "GeocoderStore")
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public class GeocoderStore implements IGeocoderStore {

    private static final long serialVersionUID = 6222591373046634575L;
    //
    @XmlJavaTypeAdapter(value = GeocoderResponseAdapter.class)
    private List<IGeocoderResponse> geocoderResponses;

    /**
     * @return {@link List < IGeocoderResponse >}
     */
    @Override
    public List<IGeocoderResponse> getGeocoderResponses() {
        return this.geocoderResponses;
    }

    /**
     * @param theGeocoderResponses
     */
    @Override
    public void setGeocoderResponses(List<IGeocoderResponse> theGeocoderResponses) {
        this.geocoderResponses = theGeocoderResponses;
    }

    /**
     * @param geocoderResponse
     */
    @Override
    public void addGeocoderResponse(IGeocoderResponse geocoderResponse) {
        if (this.geocoderResponses == null)
            this.geocoderResponses = Lists.newArrayList();
        this.geocoderResponses.add(geocoderResponse);
    }

    /**
     * @return {@link Boolean}
     */
    @Override
    public Boolean isValid() {
        return ((this.geocoderResponses != null) && !(this.geocoderResponses.isEmpty()));
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " {" +
                "  geocoderResponses = " + geocoderResponses +
                ", valid = " + isValid() +
                '}';
    }


}