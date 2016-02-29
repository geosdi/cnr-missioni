package it.cnr.missioni.model.configuration;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.geosdi.geoplatform.experimental.el.api.model.Document;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author Salvia Vito
 */
@XmlRootElement(name = "nazione")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "id", "value","areaGeografica" })
public class Nazione implements Document {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4941402788908984171L;
	private String id;
	@NotBlank
	private String value;
	@NotNull
	private AreaGeograficaEnum areaGeografica;

	public enum AreaGeograficaEnum {
		ITALIA,A,B,C,D,E,F,G;
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
	 * @return the areaGeografica
	 */
	public AreaGeograficaEnum getAreaGeografica() {
		return areaGeografica;
	}

	/**
	 * @param areaGeografica 
	 */
	public void setAreaGeografica(AreaGeograficaEnum areaGeografica) {
		this.areaGeografica = areaGeografica;
	}

	/**
	 * @return
	 */
	@Override
	public String toString() {
		return "Nazione [id=" + id + ", value=" + value + ", areaGeografica=" + areaGeografica + "]";
	}






}
