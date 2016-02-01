package it.cnr.missioni.model.configuration;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.geosdi.geoplatform.experimental.el.api.model.Document;

/**
 * @author Salvia Vito
 */
@XmlRootElement(name = "rimborsoKm")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "id", "value"})
public class RimborsoKm implements Document{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4367572751866651885L;
	private String id;
	@NotNull
	@Min(value = 0)
	private Double value;
	
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
	public Double getValue() {
		return value;
	}


	/**
	 * @param value 
	 */
	public void setValue(Double value) {
		this.value = value;
	}


	/**
	 * @return
	 */
	@Override
	public String toString() {
		return "RimborsoKm [id=" + id + ", value=" + value + "]";
	}

}
