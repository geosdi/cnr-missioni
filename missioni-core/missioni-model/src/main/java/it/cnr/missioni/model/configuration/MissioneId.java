package it.cnr.missioni.model.configuration;

import org.geosdi.geoplatform.experimental.el.api.model.Document;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author Salvia Vito
 */
@XmlRootElement(name = "missioneId")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"id", "value"})
public class MissioneId implements Document {

    /**
     *
     */
    private static final long serialVersionUID = -6943445899519373384L;
    private String id;
    @NotNull
    @NotBlank
    private String value;

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

	@Override
	public String toString() {
		return "MissioneId [id=" + id + ", value=" + value + "]";
	}

}
