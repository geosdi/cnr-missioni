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
    private long totale;

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

    /**
	 * @return the totale
	 */
	public long getTotale() {
		return totale;
	}

	/**
	 * @param totale 
	 */
	public void setTotale(long totale) {
		this.totale = totale;
	}

	/**
	 * @return
	 */
	@Override
	public String toString() {
		return "UserStore [users=" + users + ", totale=" + totale + "]";
	}

}
