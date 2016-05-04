package it.cnr.missioni.el.backup;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;

public class BackupMapper extends ObjectMapper{


	 /**
	 * 
	 */
	private static final long serialVersionUID = 6367271949305227955L;

		public BackupMapper() {
	        super();
	        registerModule(new JodaModule());
	        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	    }

	
}
