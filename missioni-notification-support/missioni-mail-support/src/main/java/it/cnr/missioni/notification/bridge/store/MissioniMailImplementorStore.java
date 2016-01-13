package it.cnr.missioni.notification.bridge.store;

import com.google.common.collect.Maps;
import it.cnr.missioni.notification.bridge.finder.MissioniMailImplementorFinder;
import it.cnr.missioni.notification.bridge.implementor.Implementor;
import it.cnr.missioni.notification.bridge.implementor.MissioniMailImplementor;
import net.jcip.annotations.Immutable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
@Immutable
public class MissioniMailImplementorStore implements ImplementorStore<MissioniMailImplementor> {

    private static final long serialVersionUID = 5246404707314559690L;
    //
    private static final Logger logger = LoggerFactory.getLogger(MissioniMailImplementorStore.class);
    private static final Map<MissioniMailImplementor.ImplementorKey, MissioniMailImplementor> missioniMailImplementors = Maps.<MissioniMailImplementor.ImplementorKey, MissioniMailImplementor>newHashMap();

    static {
        for (MissioniMailImplementor notificationMailImplementor : MissioniMailImplementorFinder.getValidMissioniMailImplementors()) {
            missioniMailImplementors.put(notificationMailImplementor.getKey(), notificationMailImplementor);
        }

        logger.debug("@@@@@@@@@@@@@@@@@@@@@@{} up with {} values.\n\n", MissioniMailImplementorStore.class.getSimpleName(),
                missioniMailImplementors.size());
    }

    /**
     * @param key
     * @return {@link MissioniMailImplementor} Implementor
     * @throws Exception
     */
    @Override
    public MissioniMailImplementor getImplementorByKey(Implementor.ImplementorKey key) throws Exception {
        if (key == null) {
            throw new IllegalArgumentException("The Key must not be null.");
        }

        return missioniMailImplementors.get(key);
    }

    /**
     * @return {@link Set<MissioniMailImplementor>}
     */
    @Override
    public Set<MissioniMailImplementor> getAllImplementors() {
        return Collections.unmodifiableSet(MissioniMailImplementorFinder.getAllMissioniMailImplementors());
    }

    /**
     * @return {@link Collection<MissioniMailImplementor>}
     */
    @Override
    public Collection<MissioniMailImplementor> getAllValidImplementors() {
        return Collections.unmodifiableCollection(missioniMailImplementors.values());
    }
}
