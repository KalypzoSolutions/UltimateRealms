package de.kalypzo.realms.player;

import de.kalypzo.realms.realm.RealmWorld;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface RealmPlayerManager {


    Collection<RealmPlayer> getPlayersByUuids(Collection<UUID> playerUuids);

    CompletableFuture<List<RealmWorld>> getOwningRealmsAsync(UUID playerUuid);


}
