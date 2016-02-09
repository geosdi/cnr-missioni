package it.cnr.missioni.el.index;

import org.elasticsearch.common.xcontent.XContentBuilder;
import org.geosdi.geoplatform.experimental.el.index.GPAbstractIndexCreator;
import org.geosdi.geoplatform.experimental.el.index.GPIndexCreator;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import static org.elasticsearch.common.xcontent.XContentFactory.*;

import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;

import it.cnr.missioni.el.index.settings.UserIndexSettings;


/**
 * @author Salvia Vito
 */
@Component(value = "userIndexCreator")
public class UserIndexCreator extends GPAbstractIndexCreator {

    @Override
    public GPIndexCreator.GPIndexSettings getIndexSettings() {
        return UserIndexSettings.USER_DOC_INDEX_SETTINGS.getValue();
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
    
    protected void preparePutMapping() throws Exception {
    	XContentBuilder xContentBuilder = jsonBuilder()
    	.startObject()
    	.startObject(super.getIndexType())
    	.startObject("properties")
    	.startObject("user")
    	.startObject("properties")
    	
       	.startObject("id")
    	.field("type", "string")
    	.endObject()
       	.startObject("registrazioneCompletata")
    	.field("type", "boolean")
    	.endObject()
       	.startObject("onlyAdmin")
    	.field("type", "boolean")
    	.endObject()
       	.startObject("responsabileGruppo")
    	.field("type", "boolean")
    	.endObject()
    	.startObject("dataRegistrazione")
    	.field("type", "date")
    	.field("format", "strict_date_optional_time||epoch_millis")
    	.endObject()
    	.startObject("dateLastModified")
    	.field("type", "date")
    	.field("format", "strict_date_optional_time||epoch_millis")
    	.endObject()
    	
    	//ANAGRAFICA
    	.startObject("anagrafica")
    	.startObject("properties")
    	.startObject("codiceFiscale")
    	.field("type", "string")
    	.endObject()
    	.startObject("nome")
    	.field("type", "string")
    	.endObject()
    	.startObject("cognome")
    	.field("type", "string")
    	.endObject()
    	.startObject("dataNascita")
    	.field("type", "date")
    	.field("format", "strict_date_optional_time||epoch_millis")
    	.endObject()
    	.endObject()
    	.endObject()
    	
    	//CREDENZIALI
    	.startObject("credenziali")
    	.startObject("properties")
    	.startObject("password")
    	.field("type", "string")
    	.endObject()
    	.startObject("username")
    	.field("type", "string")
    	.endObject()
    	.startObject("ruoloUtente")
    	.field("type", "string")
    	.endObject()
    	.endObject()
    	.endObject()
    	
    	//DATI CNR
    	.startObject("datiCNR")
    	.startObject("properties")
    	.startObject("datoreLavoro")
    	.field("type", "string")
    	.endObject()
    	.startObject("descrizioneQualifica")
    	.field("type", "string")
    	.endObject()
    	.startObject("iban")
    	.field("type", "string")
    	.endObject()
    	.startObject("idQualifica")
    	.field("type", "string")
    	.endObject()
    	.startObject("livello")
    	.field("type", "string")
    	.endObject()
    	.startObject("mail")
    	.field("type", "string")
    	.endObject()
    	.startObject("matricola")
    	.field("type", "string")
    	.endObject()
    	.startObject("shortDescriptionDatoreLavoro")
    	.field("type", "string")
    	.endObject()
    	.endObject()
    	.endObject()
    	
    	//PATENTE
    	.startObject("patente")
    	.startObject("properties")
    	.startObject("dataRilascio")
    	.field("type", "date")
    	.field("format", "strict_date_optional_time||epoch_millis")
    	.endObject()
    	.startObject("numeroPatente")
    	.field("type", "string")
    	.endObject()
    	.startObject("rilasciataDa")
    	.field("type", "string")
    	.endObject()
    	.startObject("validaFinoAl")
    	.field("type", "date")
    	.field("format", "strict_date_optional_time||epoch_millis")
    	.endObject()
    	.endObject()
    	.endObject()
    	
    	//RESIDENZA
    	.startObject("residenza")
    	.startObject("properties")
    	.startObject("comune")
    	.field("type", "string")
    	.endObject()
    	.startObject("domicilioFiscale")
    	.field("type", "string")
    	.endObject()
    	.startObject("indirizzo")
    	.field("type", "string")
    	.endObject()
    	.endObject()
    	.endObject()
    	
    	//MAPPA VEICOLO
    	.startObject("mappaVeicolo")
    	.startObject("properties")
    	.startObject("entry")
    	.startObject("properties")
    	.startObject("key")
    	.field("type", "string")
    	.endObject()
    	.startObject("value")
    	.startObject("properties")
    	.startObject("cartaCircolazione")
    	.field("type", "string")
    	.endObject()
    	.startObject("polizzaAssicurativa")
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
    	.endObject()
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
	
}
