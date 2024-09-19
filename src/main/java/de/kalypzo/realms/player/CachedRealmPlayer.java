package de.kalypzo.realms.player;

import de.kalypzo.realms.realm.RealmWorld;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Caches the realms and player data of a player.
 */
public class CachedRealmPlayer implements RealmPlayer {

    private final UUID uuid;
    private final List<RealmWorld> cachedRealmWorlds = new ArrayList<>();

    public CachedRealmPlayer(UUID uuid) {
        this.uuid = uuid;
    }

    public void invalidate() {
        cachedRealmWorlds.clear();
    }

    @Override
    public UUID getUuid() {
        return uuid;
    }

    @Override
    public List<RealmWorld> getOwningRealms() {
        if (cachedRealmWorlds.isEmpty()) {
            // Load realms from storage
        }
        return cachedRealmWorlds;
    }
}
