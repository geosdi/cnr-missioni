/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.missioni.dropwizard.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
public class CNRMissioniServiceConfig extends Configuration {

    @Valid
    @NotNull
    @JsonProperty(value = "config")
    private CNRMissioniConfigApp config;

    public CNRMissioniConfigApp getConfig() {
        return this.config;
    }

    /**
     * @param theConfig the authConfig to set
     */
    public void setConfig(CNRMissioniConfigApp theConfig) {
        this.config = theConfig;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " {"
                + "config = " + config + '}';
    }

}
