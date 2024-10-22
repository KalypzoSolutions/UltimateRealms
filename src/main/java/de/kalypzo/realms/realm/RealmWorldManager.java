package de.kalypzo.realms.realm;

import de.kalypzo.realms.player.RealmPlayer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface RealmWorldManager {

    void transferOwnership(RealmWorld realmWorld, RealmPlayer newOwner);

    void deleteRealm(RealmWorld realmWorld);

    CompletableFuture<ActiveRealmWorld> createRealm(RealmCreationContext context);

    List<RealmWorld> getRealmsByOwner(UUID owner);

    Optional<RealmWorld> getRealmWorldById(UUID realmId);
}
