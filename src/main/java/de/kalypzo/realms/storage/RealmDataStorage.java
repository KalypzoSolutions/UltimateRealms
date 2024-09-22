package de.kalypzo.realms.storage;

import de.kalypzo.realms.realm.RealmWorld;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RealmDataStorage {

    void init();

    void shutdown();

    void saveRealm(RealmWorld realmWorld);

    void deleteRealm(RealmWorld realmWorld);

    Optional<RealmWorld> loadRealmById(UUID realmId);

    List<RealmWorld> loadAllRealms();

    List<RealmWorld> loadRealmsByOwner(UUID ownerId);


}
