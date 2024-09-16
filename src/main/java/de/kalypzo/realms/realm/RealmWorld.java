package de.kalypzo.realms.realm;

import de.kalypzo.realms.player.PlayerContainer;
import de.kalypzo.realms.player.RealmPlayer;
import de.kalypzo.realms.realm.flag.FlagContainer;

import java.util.UUID;

/**
 * Represents a realm-world. It is not bound to a specific host.
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

    /**
     * @return trusted members of this world.
     */
    PlayerContainer getTrustedMembers();

    /**
     * @return banned members of this world.
     */
    PlayerContainer getBannedMembers();

    /**
     * @return the owner of this world.
     */
    RealmPlayer getOwner();

    /**
     * @return the unique identifier of the owner of this world.
     */
    UUID getOwnerUuid();

    /**
     * @return the unique identifier of this world.
     */
    UUID getRealmId();

}
