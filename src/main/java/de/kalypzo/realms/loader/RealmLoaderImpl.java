package de.kalypzo.realms.loader;

import de.kalypzo.realms.realm.ActiveRealmWorld;
import de.kalypzo.realms.realm.RealmWorld;
import de.kalypzo.realms.realm.process.impl.BukkitProcessExecutor;
import de.kalypzo.realms.realm.process.impl.DummyRealmProcess;
import de.kalypzo.realms.realm.process.impl.RealmProcessSequence;
import de.kalypzo.realms.storage.RealmWorldFileStorage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class RealmLoaderImpl implements RealmLoader {

    private final WorldLoader worldLoader;
    private final RealmWorldFileStorage realmWorldFileStorage;
    private final BukkitProcessExecutor processExecutor;

    public RealmLoaderImpl(WorldLoader worldLoader,
                           RealmWorldFileStorage realmWorldFileStorage,
                           BukkitProcessExecutor processExecutor) {
        this.worldLoader = worldLoader;
        this.realmWorldFileStorage = realmWorldFileStorage;
        this.processExecutor = processExecutor;
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
    public RealmProcessSequence<?> loadRealm(UUID realmId) {
        var procSeq = new RealmProcessSequence<>(
                new DummyRealmProcess(),
                new DummyRealmProcess()
        );
        processExecutor.execute(procSeq);
        return procSeq;
    }


    @Override
    public RealmProcessSequence<?> loadRealm(RealmWorld realmWorld) {
        return loadRealm(realmWorld.getRealmId());
    }

    @Override
    public RealmProcessSequence<?> unloadRealm(ActiveRealmWorld realm) {
        return null;
    }
}
