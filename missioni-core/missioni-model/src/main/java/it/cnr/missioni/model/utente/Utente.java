package it.cnr.missioni.model.utente;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.geosdi.geoplatform.experimental.el.api.model.Document;
import org.joda.time.DateTime;

import it.cnr.missioni.model.adapter.DocumentMapAdapter;

/**
 * @author Salvia Vito
 */
@XmlRootElement(name = "utente")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "id", "dataRegistrazione", "anagrafica", "residenza", "patente", "datiCNR", "credenziali",
		"mappaVeicolo" })
public class Utente implements Document {

	/**
	 * 
	 */
	private static final long serialVersionUID = -479690150958936950L;
	private String id;
	private DateTime dataRegistrazione;
	private Anagrafica anagrafica;
	private Residenza residenza;
	private Patente patente;
	private DatiCNR datiCNR;
	private Credenziali credenziali;
	@XmlJavaTypeAdapter(value = DocumentMapAdapter.class)
	private Map<String, Veicolo> mappaVeicolo = new HashMap<String, Veicolo>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.geosdi.geoplatform.experimental.el.api.model.Document#isIdSetted()
	 */
	@Override
	public Boolean isIdSetted() {
		return ((this.id != null) && !(this.id.isEmpty()));
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the dataRegistrazione
	 */
	public DateTime getDataRegistrazione() {
		return dataRegistrazione;
	}

	/**
	 * @param dataRegistrazione
	 */
	public void setDataRegistrazione(DateTime dataRegistrazione) {
		this.dataRegistrazione = dataRegistrazione;
	}

	/**
	 * @return the anagrafica
	 */
	public Anagrafica getAnagrafica() {
		return anagrafica;
	}

	/**
	 * @param anagrafica
	 */
	public void setAnagrafica(Anagrafica anagrafica) {
		this.anagrafica = anagrafica;
	}

	/**
	 * @return the datiCNR
	 */
	public DatiCNR getDatiCNR() {
		return datiCNR;
	}

	/**
	 * @param datiCNR
	 */
	public void setDatiCNR(DatiCNR datiCNR) {
		this.datiCNR = datiCNR;
	}

	/**
	 * @return the residenza
	 */
	public Residenza getResidenza() {
		return residenza;
	}

	/**
	 * @param residenza
	 */
	public void setResidenza(Residenza residenza) {
		this.residenza = residenza;
	}

	/**
	 * @return the patente
	 */
	public Patente getPatente() {
		return patente;
	}

	/**
	 * @param patente
	 */
	public void setPatente(Patente patente) {
		this.patente = patente;
	}

	/**
	 * @return the credenziali
	 */
	public Credenziali getCredenziali() {
		return credenziali;
	}

	/**
	 * @param credenziali
	 */
	public void setCredenziali(Credenziali credenziali) {
		this.credenziali = credenziali;
	}

	/**
	 * @return the mappaVeicolo
	 */
	public Map<String, Veicolo> getMappaVeicolo() {
		return mappaVeicolo;
	}

	/**
	 * @param mappaVeicolo
	 */
	public void setMappaVeicolo(Map<String, Veicolo> mappaVeicolo) {
		this.mappaVeicolo = mappaVeicolo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Utente [id=" + id + ", anagrafica=" + anagrafica + ", datiCNR=" + datiCNR + ", residenza=" + residenza
				+ ", patente=" + patente + ", credenziali=" + credenziali + ", mappaVeicolo=" + mappaVeicolo + "]";
	}

}
