package it.cnr.missioni.el.backup;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;

public class BackupMapper extends ObjectMapper{


	    public BackupMapper() {
	        super();
	        registerModule(new JodaModule());
	        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	    }

	
}
