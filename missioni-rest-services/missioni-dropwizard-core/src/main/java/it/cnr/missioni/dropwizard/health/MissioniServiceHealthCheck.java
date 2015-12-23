/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.missioni.dropwizard.health;

import com.codahale.metrics.health.HealthCheck;

/**
 *
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
public class MissioniServiceHealthCheck extends HealthCheck {

    @Override
    protected Result check() throws Exception {
        return Result.healthy("IMAA Missioni OAUTH2 Service check DONE.");
    }

}
