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
		// TODO Auto-generated method stub
		
	}

}
