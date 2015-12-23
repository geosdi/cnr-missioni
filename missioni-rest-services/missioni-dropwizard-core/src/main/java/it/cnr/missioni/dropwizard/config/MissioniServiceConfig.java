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
public class MissioniServiceConfig extends Configuration {

    @Valid
    @NotNull
    @JsonProperty(value = "config")
    private MissioniConfigApp config;

    public MissioniConfigApp getConfig() {
        return this.config;
    }

    /**
     * @param theConfig the authConfig to set
     */
    public void setConfig(MissioniConfigApp theConfig) {
        this.config = theConfig;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " {"
                + "config = " + config + '}';
    }

}
