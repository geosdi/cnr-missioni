package it.cnr.missioni.notification.bridge.implementor.dev;

import it.cnr.missioni.notification.bridge.implementor.MissioniMailImplementor;

/**
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
abstract class MissioniMailDev implements MissioniMailImplementor<String> {

    @Override
    public String toString() {
        return this.mailImplementorInfo();
    }
}
