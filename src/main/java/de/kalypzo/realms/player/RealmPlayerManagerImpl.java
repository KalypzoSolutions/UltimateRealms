package de.kalypzo.realms.player;

import de.kalypzo.realms.realm.RealmWorld;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class RealmPlayerManagerImpl implements RealmPlayerManager{
    @Override
    public Collection<RealmPlayer> getPlayersByUuids(Collection<UUID> playerUuids) {
        return List.of();
    }

    @Override
    public CompletableFuture<List<RealmWorld>> getOwningRealmsAsync(UUID playerUuid) {
        return null;
    }
}
