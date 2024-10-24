package de.kalypzo.realms.realm;


import de.kalypzo.realms.player.RealmPlayer;
import de.kalypzo.realms.world.WorldHandle;
import de.kalypzo.realms.world.WorldObserver;

import java.time.Duration;
import java.util.List;

/**
 * Represents an active realm-world, and it provides a handle to interact with the world.
 * A Realm world becomes active when it is loaded on the current host.
 */
public interface ActiveRealmWorld extends RealmWorld, WorldObserver {

    /**
     * @return the handle to interact with the world.
     */
    WorldHandle getWorldHandle();

    /**
     * Returns the timestamp when the realm-world was loaded.
     * The timestamp is in milliseconds since the epoch.
     *
     * @return the timestamp when the realm-world was loaded.
     */
    long getLoadedTimestamp();

    /**
     * @return the duration since the realm-world was loaded.
     */
    Duration getUptime();

    /**
     * Returns the uptime of the realm-world in milliseconds.
     *
     * @return the uptime of the realm-world in milliseconds.
     */
    long getUptimeMillis();

    /**
     * Returns whether the realm-world is currently idle.
     * An idle realm-world is not being used by any player.
     *
     * @return true if the realm-world is idle, false otherwise
     */
    boolean isIdle();

    /**
     * @return a list of players in the realm-world
     */
    List<RealmPlayer> getPlayers();


}
