package it.cnr.missioni.el.index;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.geosdi.geoplatform.experimental.el.index.GPAbstractIndexCreator;
import org.geosdi.geoplatform.experimental.el.index.GPIndexCreator;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import it.cnr.missioni.el.index.settings.MassimaleIndexSettings;


/**
 * @author Salvia Vito
 */
@Component(value = "massimaleIndexCreator")
public class MassimaleIndexCreator extends GPAbstractIndexCreator {

    @Override
    public GPIndexCreator.GPIndexSettings getIndexSettings() {
        return MassimaleIndexSettings.MASSIMALE_DOC_INDEX_SETTINGS.getValue();
    }
    
    protected void preparePutMapping() throws Exception {
    	XContentBuilder xContentBuilder = jsonBuilder()
    	.startObject()
    	.startObject(super.getIndexType())
    	.startObject("properties")
    	.startObject("massimale")
    	.startObject("properties")
    	
       	.startObject("id")
    	.field("type", "string")
    	.endObject()
    	.startObject("areaGeografica")
    	.field("type", "string")
    	.endObject()
    	.startObject("descrizione")
    	.field("type", "string")
    	.endObject()
    	.startObject("livello")
    	.field("type", "string")
    	.endObject()
    	.startObject("tipo")
    	.field("type", "string")
    	.endObject()
    	.startObject("value")
    	.field("type", "double")
    	.endObject()
    	.startObject("valueMezzaGiornata")
    	.field("type", "double")
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
