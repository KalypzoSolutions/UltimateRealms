package de.kalypzo.realms.player;

import de.kalypzo.realms.realm.RealmWorld;

import java.util.List;
import java.util.UUID;

public interface RealmPlayer {

    /**
     * @return UUID of the player.
     */
    UUID getUuid();

    /**
     * @return Immutable list of realms that this player owns.
     */
    List<RealmWorld> getOwningRealms();


}
