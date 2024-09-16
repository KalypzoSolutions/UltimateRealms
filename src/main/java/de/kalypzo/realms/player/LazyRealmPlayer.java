package de.kalypzo.realms.player;

import de.kalypzo.realms.realm.RealmWorld;

import java.util.List;
import java.util.UUID;

/**
 * Represents a player that is not loaded into memory.
 */
public class LazyRealmPlayer implements RealmPlayer {

    private final UUID uuid;
    private final RealmPlayerManager playerManager;

    public LazyRealmPlayer(UUID uuid, RealmPlayerManager playerManager) {
        this.uuid = uuid;
        this.playerManager = playerManager;
    }

    @Override
    public UUID getUuid() {
        return uuid;
    }

    @Override
    public List<RealmWorld> getOwningRealms() {
        return List.of();
    }
}
