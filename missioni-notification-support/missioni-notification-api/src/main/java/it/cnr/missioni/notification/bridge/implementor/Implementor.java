package it.cnr.missioni.notification.bridge.implementor;

/**
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
public interface Implementor {

    /**
     * @return {@link ImplementorKey}
     */
    <Key extends ImplementorKey> Key getKey();

    /**
     * <p>
     * Specify if {@link ImplementorKey} is valid or not
     * </p>
     *
     * @return {@link Boolean}
     */
    Boolean isImplementorValid();

    /**
     *
     */
    interface ImplementorKey {

        String getImplementorKey();
    }
}
