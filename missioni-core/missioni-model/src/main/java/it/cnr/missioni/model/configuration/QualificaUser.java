package it.cnr.missioni.model.configuration;

import org.geosdi.geoplatform.experimental.el.api.model.Document;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author Salvia Vito
 */
@XmlRootElement(name = "qualificaUser")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"id", "value"})
public class QualificaUser implements Document {


    /**
     *
     */
    private static final long serialVersionUID = -1723673255852798697L;
    private String id;
    @NotNull
    @NotBlank
    private String value;

    public QualificaUser(String value) {
        this.value = value;
    }

    /**
     *
     */
    public QualificaUser() {
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
     * @return
     */
    @Override
    public String toString() {
        return "QualificaUser [id=" + id + ", value=" + value + "]";
    }

}
