package it.cnr.missioni.model.adapter;

import java.util.Map;

import org.geosdi.geoplatform.response.collection.GenericMapAdapter;
import org.geosdi.geoplatform.response.collection.GenericMapType;

import it.cnr.missioni.model.rimborso.Fattura;

/**
 * @author Salvia Vito
 */
public class FatturaMapAdapter extends GenericMapAdapter<String, Fattura>{

    @Override
    public Map<String, Fattura> unmarshal(GenericMapType<String, Fattura> f) throws Exception {
        return super.unmarshal(f);
    }

    @Override
    public GenericMapType<String, Fattura> marshal(Map<String, Fattura> f) throws Exception {
        return super.marshal(f);
    }

}
