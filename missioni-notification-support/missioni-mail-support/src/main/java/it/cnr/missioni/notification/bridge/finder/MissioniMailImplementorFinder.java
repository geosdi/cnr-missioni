package it.cnr.missioni.notification.bridge.finder;

import it.cnr.missioni.notification.bridge.ImplementorArraySet;
import it.cnr.missioni.notification.bridge.implementor.MissioniMailImplementor;
import net.jcip.annotations.ThreadSafe;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ServiceLoader;
import java.util.Set;

/**
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
@ThreadSafe
public final class MissioniMailImplementorFinder {

    private static final ServiceLoader<MissioniMailImplementor> loader = ServiceLoader.<MissioniMailImplementor>load(MissioniMailImplementor.class);

    private MissioniMailImplementorFinder() {
    }

    private static final synchronized <I extends MissioniMailImplementor> Set<I> getMissioniMailImplementors(
            final Class<I> type, final Boolean all) {

        final Iterator<MissioniMailImplementor> missioniMailImplementors = loader.iterator();

        return new ImplementorArraySet<>(new Iterator<I>() {

            private I next;

            @Override
            public boolean hasNext() {
                if (next != null) {
                    return true;
                }
                synchronized (MissioniMailImplementorFinder.class) {
                    while (missioniMailImplementors.hasNext()) {
                        final MissioniMailImplementor missioniMailImplementor = missioniMailImplementors.next();
                        if ((type == null) || (type.isInstance(missioniMailImplementor))) {
                            if (all || missioniMailImplementor.isImplementorValid()) {
                                next = (I) missioniMailImplementor;
                                return true;
                            }
                        }
                    }
                }
                return false;
            }

            @Override
            public I next() {
                if (hasNext()) {
                    final I s = next;
                    next = null;
                    return s;
                }
                throw new NoSuchElementException("No more elements");
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException(
                        "Cannot remove elements "
                                + "from this Iterator");
            }

        });
    }

    public static <I extends MissioniMailImplementor> Set<I> getAllMissioniMailImplementors() {
        return getMissioniMailImplementors(null, Boolean.TRUE);
    }

    public static Set<MissioniMailImplementor> getValidMissioniMailImplementors() {
        return getMissioniMailImplementors(MissioniMailImplementor.class, Boolean.FALSE);
    }

    public static synchronized void reload() {
        loader.reload();
    }
}
