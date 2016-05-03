package it.cnr.missioni.el.restore;

import java.util.ArrayList;
import java.util.List;

import org.geosdi.geoplatform.experimental.el.api.model.Document;

public class NazioneConf {

	private List<? extends Document> list = new ArrayList();

	public List<? extends Document> getList() {
		return list;
	}

	public void setList(List<? extends Document> list) {
		this.list = list;
	}
	
}
