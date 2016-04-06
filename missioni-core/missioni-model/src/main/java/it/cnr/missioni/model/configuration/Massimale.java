package it.cnr.missioni.model.configuration;

import it.cnr.missioni.model.configuration.Nazione.AreaGeograficaEnum;
import it.cnr.missioni.model.missione.TrattamentoMissioneEsteraEnum;
import it.cnr.missioni.model.user.DatiCNR.LivelloUserEnum;
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
@XmlRootElement(name = "massimale")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"id", "value", "areaGeografica", "livello", "descrizione", "tipo", "valueMezzaGiornata"})
public class Massimale implements Document {

    /**
     *
     */
    private static final long serialVersionUID = 2229956102808552004L;
    /**
     *
     */
    private String id;
    @NotNull
    private Double value;
    @NotNull
    private AreaGeograficaEnum areaGeografica;
    @NotNull
    private LivelloUserEnum livello;
    @NotBlank
    private String descrizione;
    @NotNull
    private TrattamentoMissioneEsteraEnum tipo;
    //	@NotNull
    private Double valueMezzaGiornata;

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
     * @return the livello
     */
    public LivelloUserEnum getLivello() {
        return livello;
    }

    /**
     * @param livello
     */
    public void setLivello(LivelloUserEnum livello) {
        this.livello = livello;
    }

    /**
     * @return the descrizione
     */
    public String getDescrizione() {
        return descrizione;
    }

    /**
     * @param descrizione
     */
    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    /**
     * @return the tipo
     */
    public TrattamentoMissioneEsteraEnum getTipo() {
        return tipo;
    }

    /**
     * @param tipo
     */
    public void setTipo(TrattamentoMissioneEsteraEnum tipo) {
        this.tipo = tipo;
    }

    /**
     * @return the valueMezzaGiornata
     */
    public Double getValueMezzaGiornata() {
        return valueMezzaGiornata;
    }

    /**
     * @param valueMezzaGiornata
     */
    public void setValueMezzaGiornata(Double valueMezzaGiornata) {
        this.valueMezzaGiornata = valueMezzaGiornata;
    }

    /**
     * @return
     */
    @Override
    public String toString() {
        return "Massimale [id=" + id + ", value=" + value + ", areaGeografica=" + areaGeografica + ", livello="
                + livello + ", descrizione=" + descrizione + ", tipo=" + tipo + ", valueMezzaGiornata="
                + valueMezzaGiornata + "]";
    }

}
