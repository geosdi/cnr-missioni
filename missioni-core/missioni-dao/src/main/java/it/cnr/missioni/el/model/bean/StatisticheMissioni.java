package it.cnr.missioni.el.model.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Salvia Vito
 */
@XmlRootElement(name = "statisticheMissioni")
@XmlAccessorType(XmlAccessType.FIELD)
public class StatisticheMissioni {

    private Map<String, Long> mappaStatistiche = new HashMap<String, Long>();

    /**
     * @return the mappaStatistiche
     */
    public Map<String, Long> getMappaStatistiche() {
        return mappaStatistiche;
    }

    /**
     * @param mappaStatistiche
     */
    public void setMappaStatistiche(Map<String, Long> mappaStatistiche) {
        this.mappaStatistiche = mappaStatistiche;
    }

}
