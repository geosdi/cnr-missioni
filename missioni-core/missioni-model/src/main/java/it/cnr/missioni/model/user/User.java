package it.cnr.missioni.model.user;

import it.cnr.missioni.model.adapter.VeicoloMapAdapter;
import org.geosdi.geoplatform.experimental.el.api.model.Document;
import org.joda.time.DateTime;

import javax.validation.Valid;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Salvia Vito
 */
@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"id", "dataRegistrazione", "dateLastModified", "registrazioneCompletata", "responsabileGruppo",
        "anagrafica", "residenza", "patente", "datiCNR", "credenziali", "mappaVeicolo"})
public class User implements Document {

    /**
     *
     */
    private static final long serialVersionUID = -479690150958936950L;

    private String id;
    private DateTime dataRegistrazione;
    private DateTime dateLastModified;
    private boolean registrazioneCompletata;
    private boolean responsabileGruppo;
    @Valid
    private Anagrafica anagrafica = new Anagrafica();
    @Valid
    private Residenza residenza = new Residenza();
    @Valid
    private Patente patente = new Patente();
    @Valid
    private DatiCNR datiCNR = new DatiCNR();
    @Valid
    private Credenziali credenziali = new Credenziali();
    @XmlJavaTypeAdapter(value = VeicoloMapAdapter.class)
    private Map<String, Veicolo> mappaVeicolo = new HashMap<String, Veicolo>();

    public boolean isVeicoloPrincipaleSettato() {
        for (Veicolo v : mappaVeicolo.values()) {
            if (v.isVeicoloPrincipale())
                return true;
        }
        return false;
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
     * @return the dataRegistrazione
     */
    public DateTime getDataRegistrazione() {
        return dataRegistrazione;
    }

    /**
     * @param dataRegistrazione
     */
    public void setDataRegistrazione(DateTime dataRegistrazione) {
        this.dataRegistrazione = dataRegistrazione;
    }

    /**
     * @return the dateLastModified
     */
    public DateTime getDateLastModified() {
        return dateLastModified;
    }

    /**
     * @param dateLastModified
     */
    public void setDateLastModified(DateTime dateLastModified) {
        this.dateLastModified = dateLastModified;
    }

    /**
     * @return the registrazioneCompletata
     */
    public boolean isRegistrazioneCompletata() {
        return registrazioneCompletata;
    }

    /**
     * @param registrazioneCompletata
     */
    public void setRegistrazioneCompletata(boolean registrazioneCompletata) {
        this.registrazioneCompletata = registrazioneCompletata;
    }

    /**
     * @return the responsabileGruppo
     */
    public boolean isResponsabileGruppo() {
        return responsabileGruppo;
    }

    /**
     * @param responsabileGruppo
     */
    public void setResponsabileGruppo(boolean responsabileGruppo) {
        this.responsabileGruppo = responsabileGruppo;
    }

    /**
     * @return the anagrafica
     */
    public Anagrafica getAnagrafica() {
        return anagrafica;
    }

    /**
     * @param anagrafica
     */
    public void setAnagrafica(Anagrafica anagrafica) {
        this.anagrafica = anagrafica;
    }

    /**
     * @return the datiCNR
     */
    public DatiCNR getDatiCNR() {
        return datiCNR;
    }

    /**
     * @param datiCNR
     */
    public void setDatiCNR(DatiCNR datiCNR) {
        this.datiCNR = datiCNR;
    }

    /**
     * @return the residenza
     */
    public Residenza getResidenza() {
        return residenza;
    }

    /**
     * @param residenza
     */
    public void setResidenza(Residenza residenza) {
        this.residenza = residenza;
    }

    /**
     * @return the patente
     */
    public Patente getPatente() {
        return patente;
    }

    /**
     * @param patente
     */
    public void setPatente(Patente patente) {
        this.patente = patente;
    }

    /**
     * @return the credenziali
     */
    public Credenziali getCredenziali() {
        return credenziali;
    }

    /**
     * @param credenziali
     */
    public void setCredenziali(Credenziali credenziali) {
        this.credenziali = credenziali;
    }

    /**
     * @return the mappaVeicolo
     */
    public Map<String, Veicolo> getMappaVeicolo() {
        return mappaVeicolo;
    }

    /**
     * @param mappaVeicolo
     */
    public void setMappaVeicolo(Map<String, Veicolo> mappaVeicolo) {
        this.mappaVeicolo = mappaVeicolo;
    }

    public Veicolo getVeicoloPrincipale() {
        if (!mappaVeicolo.isEmpty()){
        	List<Veicolo> list = this.mappaVeicolo.values().stream().filter(v -> v.isVeicoloPrincipale())
                    .collect(Collectors.toList());
        	return !list.isEmpty() ? list.get(0) : null;
        }
        return null;
    }

    public Veicolo getVeicoloWithTarga(String targa, String id) {
        if (!mappaVeicolo.isEmpty()) {

            List<Veicolo> lista = this.mappaVeicolo.values().stream().filter(v -> v.getTarga().equalsIgnoreCase(targa)).collect(Collectors.toList());

            if (id != null) {
                lista = lista.stream().filter(v -> !v.getId().equals(id)).collect(Collectors.toList());
            }

            return lista.isEmpty() ? null : lista.get(0);
        }
        return null;

    }

    public Veicolo getVeicoloWithCartaCircolazione(String cartaCircolazione, String id) {

        if (!mappaVeicolo.isEmpty()) {

            List<Veicolo> lista = this.mappaVeicolo.values().stream().filter(v -> v.getCartaCircolazione().equalsIgnoreCase(cartaCircolazione)).collect(Collectors.toList());

            if (id != null) {
                lista = lista.stream().filter(v -> !v.getId().equals(id)).collect(Collectors.toList());
            }

            return lista.isEmpty() ? null : lista.get(0);
        }
        return null;
    }

    public Veicolo getVeicoloWithPolizzaAssicurativa(String polizzaAssicurativa, String id) {
        if (!mappaVeicolo.isEmpty()) {

            List<Veicolo> lista = this.mappaVeicolo.values().stream().filter(v -> v.getPolizzaAssicurativa().equalsIgnoreCase(polizzaAssicurativa)).collect(Collectors.toList());

            if (id != null) {
                lista = lista.stream().filter(v -> !v.getId().equals(id)).collect(Collectors.toList());
            }

            return lista.isEmpty() ? null : lista.get(0);
        }
        return null;
    }

    /**
     * @return
     */
    @Override
    public String toString() {
        return "User [id=" + id + ", dataRegistrazione=" + dataRegistrazione + ", dateLastModified=" + dateLastModified
                + ", registrazioneCompletata=" + registrazioneCompletata + ", responsabileGruppo=" + responsabileGruppo
                + ", anagrafica=" + anagrafica + ", residenza=" + residenza + ", patente="
                + patente + ", datiCNR=" + datiCNR + ", credenziali=" + credenziali + ", mappaVeicolo=" + mappaVeicolo
                + "]";
    }

}
