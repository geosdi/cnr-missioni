package it.cnr.missioni.model.configuration;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.geosdi.geoplatform.experimental.el.api.model.Document;

import it.cnr.missioni.model.missione.TrattamentoMissioneEsteraEnum;

/**
 * @author Salvia Vito
 */
@XmlRootElement(name = "tipologiaSpesa")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "id", "value","tipo","checkMassimale","occorrenzeGiornaliere","tipoTrattamento" })
public class TipologiaSpesa implements Document {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8287063179690294163L;
	/**
	 * 
	 */
	private String id;
	@NotNull
	private String value;
	@NotNull
	private TipoSpesaEnum tipo;
	private boolean checkMassimale;
	private int occorrenzeGiornaliere;
	@NotNull
	private TrattamentoMissioneEsteraEnum tipoTrattamento;
	
	public enum TipoSpesaEnum{
		ESTERA,ITALIA;
	}

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
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the tipo
	 */
	public TipoSpesaEnum getTipo() {
		return tipo;
	}

	/**
	 * @param tipo 
	 */
	public void setTipo(TipoSpesaEnum tipo) {
		this.tipo = tipo;
	}

	/**
	 * @return the checkMassimale
	 */
	public boolean isCheckMassimale() {
		return checkMassimale;
	}

	/**
	 * @param checkMassimale 
	 */
	public void setCheckMassimale(boolean checkMassimale) {
		this.checkMassimale = checkMassimale;
	}

	/**
	 * @return the occorrenzeGiornaliere
	 */
	public int getOccorrenzeGiornaliere() {
		return occorrenzeGiornaliere;
	}

	/**
	 * @param occorrenzeGiornaliere 
	 */
	public void setOccorrenzeGiornaliere(int occorrenzeGiornaliere) {
		this.occorrenzeGiornaliere = occorrenzeGiornaliere;
	}

	/**
	 * @return the tipoTrattamento
	 */
	public TrattamentoMissioneEsteraEnum getTipoTrattamento() {
		return tipoTrattamento;
	}

	/**
	 * @param tipoTrattamento 
	 */
	public void setTipoTrattamento(TrattamentoMissioneEsteraEnum tipoTrattamento) {
		this.tipoTrattamento = tipoTrattamento;
	}

	/**
	 * @return
	 */
	@Override
	public String toString() {
		return "TipologiaSpesa [id=" + id + ", value=" + value + ", tipo=" + tipo + ", checkMassimale=" + checkMassimale
				+ ", occorrenzeGiornaliere=" + occorrenzeGiornaliere + ", tipoTrattamento=" + tipoTrattamento + "]";
	}






}
