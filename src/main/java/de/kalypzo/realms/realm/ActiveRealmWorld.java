package de.kalypzo.realms.realm;


import de.kalypzo.realms.player.RealmPlayer;
import de.kalypzo.realms.realm.world.WorldHandle;

import java.util.List;

/**
 * Represents an active realm-world, and it provides a handle to interact with the world.
 * A Realm world becomes active when it is loaded on the current host.
 */
public interface ActiveRealmWorld extends RealmWorld, WorldHandle {


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
