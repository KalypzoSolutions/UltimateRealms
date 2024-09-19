package de.kalypzo.realms.player;

import de.kalypzo.realms.realm.RealmWorld;

import java.util.List;
import java.util.UUID;

/**
 * Represents a player that is not loaded into memory.
 */
public class LazyRealmPlayer extends CachedRealmPlayer {
    private final RealmPlayerManager playerManager;

    public LazyRealmPlayer(UUID uuid, RealmPlayerManager playerManager) {
        super(uuid);
        this.playerManager = playerManager;
    }


    @Override
    public List<RealmWorld> getOwningRealms() {
        return null;
    }

}
