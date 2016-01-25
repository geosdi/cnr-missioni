package it.cnr.missioni.model.prenotazione;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.geosdi.geoplatform.experimental.el.api.model.Document;

import it.cnr.missioni.model.user.Veicolo;

/**
 * @author Salvia Vito
 */
@XmlRootElement(name = "veicoloCNR")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"id", "tipo","targa","cartaCircolazione","polizzaAssicurativa"})
public class VeicoloCNR extends Veicolo implements Document{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1405399099831810547L;
	private String id;
	private StatoVeicoloEnum stato;

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
	 * @param theID
	 */
	@Override
	public void setId(String theID) {
		this.id = theID;
		
	}

	/**
	 * @return the stato
	 */
	public StatoVeicoloEnum getStato() {
		return stato;
	}

	/**
	 * @param stato 
	 */
	public void setStato(StatoVeicoloEnum stato) {
		this.stato = stato;
	}

	/**
	 * @return
	 */
	@Override
	public String toString() {
		return "VeicoloCNR [id=" + id + ", stato=" + stato + ", getTipo()=" + getTipo() + ", getTarga()=" + getTarga()
				+ ", getCartaCircolazione()=" + getCartaCircolazione() + ", getPolizzaAssicurativa()="
				+ getPolizzaAssicurativa() + "]";
	}

}
