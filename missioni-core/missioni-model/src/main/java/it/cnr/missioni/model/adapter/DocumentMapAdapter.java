package it.cnr.missioni.model.adapter;

import java.util.Map;

import org.geosdi.geoplatform.response.collection.GenericMapAdapter;
import org.geosdi.geoplatform.response.collection.GenericMapType;

import it.cnr.missioni.model.user.Veicolo;

/**
 * @author Salvia Vito
 */
public class DocumentMapAdapter extends GenericMapAdapter<String, Veicolo>{

    @Override
    public Map<String, Veicolo> unmarshal(GenericMapType<String, Veicolo> v) throws Exception {
        return super.unmarshal(v);
    }

    @Override
    public GenericMapType<String, Veicolo> marshal(Map<String, Veicolo> v) throws Exception {
        return super.marshal(v);
    }

}
