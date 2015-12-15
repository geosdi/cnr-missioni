package it.cnr.missioni.model.rimborso;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

/**
 * @author Salvia Vito
 */
public class Rimborso  {

	private DateTime dataRimborso;
	private List<Fattura> listaFatture = new ArrayList<Fattura>();


	/**
	 * @return the dataRimborso
	 */
	public DateTime getDataRimborso() {
		return dataRimborso;
	}

	/**
	 * @param dataRimborso
	 */
	public void setDataRimborso(DateTime dataRimborso) {
		this.dataRimborso = dataRimborso;
	}

	/**
	 * @return the listaFatture
	 */
	public List<Fattura> getListaFatture() {
		return listaFatture;
	}

	/**
	 * @param listaFatture
	 */
	public void setListaFatture(List<Fattura> listaFatture) {
		this.listaFatture = listaFatture;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Rimborso [listaFatture=" + listaFatture + "]";
	}

}
