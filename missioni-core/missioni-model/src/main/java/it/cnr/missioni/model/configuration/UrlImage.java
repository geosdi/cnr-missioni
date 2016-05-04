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
@XmlRootElement(name = "urlImage")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"id", "logoMinistero","logoImaa","logoCnr"})
public class UrlImage implements Document {

    /**
     *
     */
    private static final long serialVersionUID = -6943445899519373384L;
    private String id;
    @NotNull
    @NotBlank
    private String logoMinistero;
    private String logoImaa;
    private String logoCnr;

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
     * 
     * @return
     */
	public String getLogoMinistero() {
		return logoMinistero;
	}

	/**
	 * 
	 * @param logoMinistero
	 */
	public void setLogoMinistero(String logoMinistero) {
		this.logoMinistero = logoMinistero;
	}

	/**
	 * 
	 * @return
	 */
	public String getLogoImaa() {
		return logoImaa;
	}

	/**
	 * 
	 * @param logoImaa
	 */
	public void setLogoImaa(String logoImaa) {
		this.logoImaa = logoImaa;
	}

	/**
	 * 
	 * @return
	 */
	public String getLogoCnr() {
		return logoCnr;
	}

	/**
	 * 
	 * @param logoCnr
	 */
	public void setLogoCnr(String logoCnr) {
		this.logoCnr = logoCnr;
	}

	@Override
	public String toString() {
		return "UrlImage [id=" + id + ", logoMinistero=" + logoMinistero + ", logoImaa=" + logoImaa + ", logoCnr="
				+ logoCnr + "]";
	}



}

