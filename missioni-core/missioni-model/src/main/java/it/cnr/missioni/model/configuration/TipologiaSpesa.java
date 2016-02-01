package it.cnr.missioni.model.configuration;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.geosdi.geoplatform.experimental.el.api.model.Document;

/**
 * @author Salvia Vito
 */
@XmlRootElement(name = "tipologiaSpesa")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "id", "value","tipologiaSpesa" })
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
	private TipologiaSpesaEnum tipo;

	public enum TipologiaSpesaEnum {
		ESTERA("Estera"), ITALIA("Italia");

		private final String value;

		TipologiaSpesaEnum(String value) {
			this.value = value;
		}

		@Override
		public String toString() {
			return this.value;
		}

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
	public TipologiaSpesaEnum getTipo() {
		return tipo;
	}

	/**
	 * @param tipo 
	 */
	public void setTipo(TipologiaSpesaEnum tipo) {
		this.tipo = tipo;
	}

	/**
	 * @return
	 */
	@Override
	public String toString() {
		return "TipologiaSpesa [id=" + id + ", value=" + value + ", tipo=" + tipo + "]";
	}




}
