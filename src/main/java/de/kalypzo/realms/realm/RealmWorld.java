package de.kalypzo.realms.realm;

import de.kalypzo.realms.realm.flag.FlagContainer;

/**
 * Represents a stateless realm-world.
 */
public interface RealmWorld {

    /**
     * @return whether the world is loaded or not on any host.
     */
    boolean isLoaded();

    /**
     * @return whether the world is loaded on the current host.
     */
    boolean isLoadedLocally();

    /**
     * @return the flags of this world.
     */
    FlagContainer getFlags();




}
