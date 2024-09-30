package de.kalypzo.realms.realm;

import de.kalypzo.realms.player.RealmPlayer;
import de.kalypzo.realms.realm.process.RealmProcess;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RealmWorldManager {

    void transferOwnership(RealmWorld realmWorld, RealmPlayer newOwner);

    void deleteRealm(RealmWorld realmWorld);

    RealmProcess<ActiveRealmWorld> createRealm(RealmCreationContext context);

    List<RealmWorld> getRealmsByOwner(UUID owner);

    Optional<RealmWorld> getRealmWorldById(UUID realmId);
}
