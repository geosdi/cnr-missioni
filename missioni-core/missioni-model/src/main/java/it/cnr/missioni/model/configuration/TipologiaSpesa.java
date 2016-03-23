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
@XmlType(propOrder = { "id", "value","occorrenzeGiornaliere","tipoTrattamento","voceSpesa","estera","italia","checkData" })
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
	private TrattamentoMissioneEsteraEnum tipoTrattamento;
	@NotNull
	private VoceSpesaEnum voceSpesa;
	private boolean estera;
	private boolean italia;
	private boolean noCheckData;

	public enum VoceSpesaEnum{
		PASTO,TRASPORTO,ALLOGGIO,RIMBORSO_KM,ALTRO;
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
	 * @return the voceSpesa
	 */
	public VoceSpesaEnum getVoceSpesa() {
		return voceSpesa;
	}

	/**
	 * @param voceSpesa 
	 */
	public void setVoceSpesa(VoceSpesaEnum voceSpesa) {
		this.voceSpesa = voceSpesa;
	}

	/**
	 * @return the estera
	 */
	public boolean isEstera() {
		return estera;
	}

	/**
	 * @param estera 
	 */
	public void setEstera(boolean estera) {
		this.estera = estera;
	}

	/**
	 * @return the italia
	 */
	public boolean isItalia() {
		return italia;
	}

	/**
	 * @param italia 
	 */
	public void setItalia(boolean italia) {
		this.italia = italia;
	}

	/**
	 * @return the checkData
	 */
	public boolean isNoCheckData() {
		return noCheckData;
	}

	/**
	 * @param checkData 
	 */
	public void setNoCheckData(boolean noCheckData) {
		this.noCheckData = noCheckData;
	}

	/**
	 * @return
	 */
	@Override
	public String toString() {
		return "TipologiaSpesa [id=" + id + ", value=" + value + ", tipoTrattamento=" + tipoTrattamento + ", voceSpesa="
				+ voceSpesa + ", estera=" + estera + ", italia=" + italia + ", checkData=" + noCheckData + "]";
	}










}
