/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.missioni.rest.api.response.user;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import it.cnr.missioni.model.user.User;

/**
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
@JsonRootName(value = "UserStore")
public class UserStore implements Serializable {

    private static final long serialVersionUID = -7592733477679041238L;
    @JsonProperty(value = "users", required = false)
    private List<User> users;

    public UserStore() {
    }

    public UserStore(List<User> users) {
        this.users = users;
    }

    /**
     * @return the users
     */
    public List<User> getUsers() {
        return users;
    }

    /**
     * @param users the users to set
     */
    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " {"
                + "users = " + users + '}';
    }

}
