package it.cnr.missioni.model.user;

import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * @author Salvia Vito
 */
public class Credenziali implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 5504155444424046718L;
    @NotBlank
    private String username = "";
    @NotBlank
    private String password = "";
    private RuoloUserEnum ruoloUtente;

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the ruoloUtente
     */
    public RuoloUserEnum getRuoloUtente() {
        return ruoloUtente;
    }

    /**
     * @param ruoloUtente
     */
    public void setRuoloUtente(RuoloUserEnum ruoloUtente) {
        this.ruoloUtente = ruoloUtente;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Credenziali [username=" + username + ", password=" + password + ", ruoloUtente=" + ruoloUtente + "]";
    }

}
