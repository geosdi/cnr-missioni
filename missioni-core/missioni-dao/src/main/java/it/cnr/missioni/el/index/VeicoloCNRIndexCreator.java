package it.cnr.missioni.el.index;

import it.cnr.missioni.el.index.settings.VeicoloCNRIndexSettings;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.geosdi.geoplatform.experimental.el.index.GPAbstractIndexCreator;
import org.geosdi.geoplatform.experimental.el.index.GPIndexCreator;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;


/**
 * @author Salvia Vito
 */
@Component(value = "veicoloCNRIndexCreator")
public class VeicoloCNRIndexCreator extends GPAbstractIndexCreator {

    @Override
    public GPIndexCreator.GPIndexSettings getIndexSettings() {
        return VeicoloCNRIndexSettings.VEICOLO_CNR_DOC_INDEX_SETTINGS.getValue();
    }

    protected void preparePutMapping() throws Exception {
        XContentBuilder xContentBuilder = jsonBuilder()
                .startObject()
                .startObject(super.getIndexType())
                .startObject("properties")
                .startObject("veicoloCNR")
                .startObject("properties")

                .startObject("id")
                .field("type", "string")
                .endObject()
                .startObject("cartaCircolazione")
                .field("type", "string")
                .endObject()
                .startObject("polizzaAssicurativa")
                .field("type", "string")
                .endObject()
                .startObject("stato")
                .field("type", "string")
                .endObject()
                .startObject("targa")
                .field("type", "string")
                .endObject()
                .startObject("tipo")
                .field("type", "string")
                .endObject()
                .startObject("veicoloPrincipale")
                .field("type", "boolean")
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
