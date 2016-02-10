package it.cnr.missioni.el.index;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.geosdi.geoplatform.experimental.el.index.GPAbstractIndexCreator;
import org.geosdi.geoplatform.experimental.el.index.GPIndexCreator;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import it.cnr.missioni.el.index.settings.MissioneIndexSettings;
import it.cnr.missioni.el.index.settings.UserIndexSettings;


/**
 * @author Salvia Vito
 */
@Component(value = "missioneIndexCreator")
public class MissioneIndexCreator extends GPAbstractIndexCreator {

    @Override
    public GPIndexCreator.GPIndexSettings getIndexSettings() {
        return MissioneIndexSettings.MISSIONE_DOC_INDEX_SETTINGS.getValue();
    }
    
    protected void preparePutMapping() throws Exception {
    	XContentBuilder xContentBuilder = jsonBuilder()
    	.startObject()
    	.startObject(super.getIndexType())
    	.startObject("properties")
    	.startObject("missione")
    	.startObject("properties")
    	
       	.startObject("id")
    	.field("type", "string")
    	.endObject()
    	.startObject("GAE")
    	.field("type", "string")
    	.endObject()
    	.startObject("dataInserimento")
    	.field("type", "date")
    	.field("format", "strict_date_optional_time||epoch_millis")
    	.endObject()
    	.startObject("dateLastModified")
    	.field("type", "date")
    	.field("format", "strict_date_optional_time||epoch_millis")
    	.endObject()
    	.startObject("distanza")
    	.field("type", "string")
    	.endObject()
    	.startObject("fondo")
    	.field("type", "string")
    	.endObject()
    	.startObject("idNazione")
    	.field("type", "string")
    	.endObject()
    	.startObject("idUser")
    	.field("type", "string")
    	.endObject()
    	.startObject("idVeicolo")
    	.field("type", "string")
    	.endObject()
    	.startObject("localita")
    	.field("type", "string")
    	.endObject()
    	.startObject("mezzoProprio")
    	.field("type", "boolean")
    	.endObject()
    	.startObject("missioneEstera")
    	.field("type", "boolean")
    	.endObject()
    	.startObject("motivazioneMezzoProprio")
    	.field("type", "string")
    	.endObject()
    	.startObject("oggetto")
    	.field("type", "string")
    	.endObject()
    	.startObject("responsabileGruppo")
    	.field("type", "string")
    	.endObject()
    	.startObject("shortDescriptionNazione")
    	.field("type", "string")
    	.endObject()
    	.startObject("shortDescriptionVeicolo")
    	.field("type", "string")
    	.endObject()
    	.startObject("shortResponsabileGruppo")
    	.field("type", "string")
    	.endObject()
    	.startObject("stato")
    	.field("type", "string")
    	.endObject()
    	.startObject("geoPoint")
    	.startObject("properties") 	
    	.startObject("geohash")
    	.field("type", "string")
    	.endObject() 	
    	.startObject("lat")
    	.field("type", "double")
    	.endObject()
    	.startObject("lon")
    	.field("type", "double")
    	.endObject()
    	.endObject() 	
    	.endObject()
    	
    	//DATI ANTICIPO PAGAMENTI
    	.startObject("datiAnticipoPagamenti")
    	.startObject("properties")
    	.startObject("anticipazioniMonetarie")
    	.field("type", "boolean")
    	.endObject()
    	.startObject("rimborsoDaTerzi")
    	.field("type", "boolean")
    	.endObject()
    	.startObject("importoDaTerzi")
    	.field("type", "double")
    	.endObject()
    	.startObject("mandatoCNR")
    	.field("type", "string")
    	.endObject()
    	.startObject("speseMissioniAnticipate")
    	.field("type", "double")
    	.endObject()
    	.endObject()
    	.endObject()
    	
    	//DATI MISSIONE ESTERA
    	.startObject("datiMissioneEstera")
    	.startObject("properties")
    	.startObject("attraversamentoFrontieraAndata")
    	.field("type", "date")
    	.field("format", "strict_date_optional_time||epoch_millis")
    	.endObject()
    	.startObject("attraversamentoFrontieraRitorno")
    	.field("type", "date")
    	.field("format", "strict_date_optional_time||epoch_millis")
    	.endObject()
    	.startObject("trattamentoMissioneEsteraEnum")
    	.field("type", "string")
    	.endObject()
    	.endObject()
    	.endObject()
    	
    	//DATI PERIODO MISSIONE
    	.startObject("datiPeriodoMissione")
    	.startObject("properties")
    	.startObject("fineMissione")
    	.field("type", "date")
    	.field("format", "strict_date_optional_time||epoch_millis")
    	.endObject()
    	.startObject("inizioMissione")
    	.field("type", "date")
    	.field("format", "strict_date_optional_time||epoch_millis")
    	.endObject()
    	.endObject()
    	.endObject()
    	
    	//RIMBORSO
    	.startObject("rimborso")
    	.startObject("properties")
    	.startObject("anticipazionePagamento")
    	.field("type", "double")
    	.endObject()
    	.startObject("avvisoPagamento")
    	.field("type", "string")
    	.endObject()
    	.startObject("dataRimborso")
    	.field("type", "date")
    	.field("format", "strict_date_optional_time||epoch_millis")
    	.endObject()
    	.startObject("dateLastModified")
    	.field("type", "date")
    	.field("format", "strict_date_optional_time||epoch_millis")
    	.endObject()
    	.startObject("numeroOrdine")
    	.field("type", "long")
    	.endObject()
    	.startObject("rimborsoKm")
    	.field("type", "double")
    	.endObject()
    	.startObject("totKm")
    	.field("type", "double")
    	.endObject()
    	.startObject("totale")
    	.field("type", "double")
    	.endObject()
    	.startObject("totaleTAM")
    	.field("type", "double")
    	.endObject()
    	.endObject()
    	.endObject()
    	
    	//MAPPA FATTURA
    	.startObject("mappaFattura")
    	.startObject("properties")
    	.startObject("entry")
    	.startObject("properties")
    	.startObject("key")
    	.field("type", "string")
    	.endObject()
    	.startObject("value")
    	.startObject("properties")
    	.startObject("data")
    	.field("type", "date")
    	.field("format", "strict_date_optional_time||epoch_millis")
    	.endObject()
    	.startObject("id")
    	.field("type", "string")
    	.endObject()
    	.startObject("idTipologiaSpesa")
    	.field("type", "string")
    	.endObject()
    	.startObject("importo")
    	.field("type", "double")
    	.endObject()
    	.startObject("numeroFattura")
    	.field("type", "double")
    	.endObject()
    	.startObject("shortDescriptionTipologiaSpesa")
    	.field("type", "string")
    	.endObject()
    	.startObject("valuta")
    	.field("type", "string")
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

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
	
}
