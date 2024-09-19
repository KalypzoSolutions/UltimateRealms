package de.kalypzo.realms.loader;

import de.kalypzo.realms.realm.ActiveRealmWorld;
import de.kalypzo.realms.realm.RealmWorld;
import de.kalypzo.realms.realm.process.RealmProcess;
import de.kalypzo.realms.storage.RealmWorldFileStorage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class RealmLoaderImpl implements RealmLoader {

    private final WorldLoader worldLoader;
    private final RealmWorldFileStorage realmWorldFileStorage;

    public RealmLoaderImpl(WorldLoader worldLoader, RealmWorldFileStorage realmWorldFileStorage) {
        this.worldLoader = worldLoader;
        this.realmWorldFileStorage = realmWorldFileStorage;
    }


    /**
     * Create RealmProcess
     * Get Realm from Storage
     * Load the world with WorldLoader
     *
     * @param realmId the id of the realm
     * @return the realm process
     */
    @Override
    public RealmProcess loadRealm(UUID realmId) {
        return null;
    }

    @Override
    public RealmProcess loadRealm(RealmWorld realmWorld) {
        return loadRealm(realmWorld.getRealmId());
    }

    @Override
    public RealmProcess unloadRealm(ActiveRealmWorld realm) {
        return null;
    }
}
