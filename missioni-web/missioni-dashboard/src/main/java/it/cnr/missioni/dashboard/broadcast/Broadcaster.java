package it.cnr.missioni.dashboard.broadcast;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Salvia Vito
 */
public class Broadcaster implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 4894282195273039938L;

    private static final List<BroadcastListener> listeners = new CopyOnWriteArrayList<BroadcastListener>();

    public static void register(BroadcastListener listener) {
        listeners.add(listener);
    }

    public static void unregister(BroadcastListener listener) {
        listeners.remove(listener);
    }

    public static void broadcast(final String message) {
        for (BroadcastListener listener : listeners) {
            listener.receiveBroadcast(message);
        }
    }

    public interface BroadcastListener {
        public void receiveBroadcast(String message);
    }
}
