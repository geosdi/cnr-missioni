/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.missioni.rest.api.response.users;

import it.cnr.missioni.model.utente.Utente;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

/**
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class UserStore implements Serializable {

    private static final long serialVersionUID = -7592733477679041238L;
    //
    private List<Utente> users;

    public UserStore() {
    }

    public UserStore(List<Utente> users) {
        this.users = users;
    }

    /**
     * @return the users
     */
    public List<Utente> getUsers() {
        return users;
    }

    /**
     * @param users the users to set
     */
    public void setUsers(List<Utente> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " {"
                + "users = " + users + '}';
    }

}
