package it.cnr.missioni.el.index;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.geosdi.geoplatform.experimental.el.index.GPAbstractIndexCreator;
import org.geosdi.geoplatform.experimental.el.index.GPIndexCreator;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import it.cnr.missioni.el.index.settings.PrenotazioneIndexSettings;
import it.cnr.missioni.el.index.settings.UserIndexSettings;


/**
 * @author Salvia Vito
 */
@Component(value = "prenotazioneIndexCreator")
public class PrenotazioneIndexCreator extends GPAbstractIndexCreator {

    @Override
    public GPIndexCreator.GPIndexSettings getIndexSettings() {
        return PrenotazioneIndexSettings.PRENOTAZIONE_DOC_INDEX_SETTINGS.getValue();
    }
    
    protected void preparePutMapping() throws Exception {
    	XContentBuilder xContentBuilder = jsonBuilder()
    	.startObject()
    	.startObject(super.getIndexType())
    	.startObject("properties")
    	.startObject("prenotazione")
    	.startObject("properties")
    	
       	.startObject("id")
    	.field("type", "string")
    	.endObject()
    	.startObject("allDay")
    	.field("type", "boolean")
    	.endObject()
    	.startObject("dataFrom")
    	.field("type", "date")
    	.field("format", "strict_date_optional_time||epoch_millis")
    	.endObject()
    	.startObject("dataTo")
    	.field("type", "date")
    	.field("format", "strict_date_optional_time||epoch_millis")
    	.endObject()
       	.startObject("descrizione")
    	.field("type", "string")
    	.endObject()
       	.startObject("idUser")
    	.field("type", "string")
    	.endObject()
       	.startObject("idVeicoloCNR")
    	.field("type", "string")
    	.endObject()

    	.endObject()
    	.endObject()
    	.endObject()
    	.endObject()
    	.endObject();
    	logger.debug("#####################TRYING TO PUT MAPPING with SOURCE : \n{}\n",
    	xContentBuilder.string());
    	PutMappingResponse putMappingResponse = super.putMapping(xContentBuilder);
    	logger.debug("##########################" + ((putMappingResponse.isAcknowledged())
    	? "PUT_MAPPING_STATUS IS OK.\n" : "PUT_MAPPING NOT CREATED.\n"));
    }


    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
	
}
