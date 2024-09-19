package de.kalypzo.realms.player;

import de.kalypzo.realms.realm.RealmWorld;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface RealmPlayerManager {


    CompletableFuture<List<RealmWorld>> getOwningRealmsAsync(UUID playerUuid);


}
