package de.kalypzo.realms.loader;

import de.kalypzo.realms.realm.ActiveRealmWorld;
import de.kalypzo.realms.realm.RealmWorld;
import de.kalypzo.realms.realm.process.impl.RealmProcessSequence;
import de.kalypzo.realms.realm.process.impl.UniversalProcessExecutor;
import de.kalypzo.realms.storage.RealmWorldFileStorage;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.Nullable;

@Getter
public class RealmLoaderImpl implements RealmLoader {

    private final WorldLoader worldLoader;
    private final RealmWorldFileStorage realmWorldFileStorage;
    private final UniversalProcessExecutor processExecutor;

    public RealmLoaderImpl(WorldLoader worldLoader,
                           RealmWorldFileStorage realmWorldFileStorage,
                           UniversalProcessExecutor processExecutor) {
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
     * @return the realm process or null if the realm does not exist
     */
    @Nullable
    @Override
    public RealmProcessSequence<ActiveRealmWorld> loadRealm(String realmId) {
        if (worldLoader.isWorldLoaded(realmId)) {
            return null;
        }
        if (!realmWorldFileStorage.isFileExisting(realmId)) {
            return null;
        }


        realmWorldFileStorage.loadFile(realmId, Bukkit.getWorldContainer().toPath());


        //processExecutor.execute(procSeq);
        //return procSeq;
        return null;
    }


    @Override
    public RealmProcessSequence<ActiveRealmWorld> loadRealm(RealmWorld realmWorld) {
        return loadRealm(realmWorld.getRealmId().toString());
    }

    @Override
    public RealmProcessSequence<?> unloadRealm(ActiveRealmWorld realm) {
        return null;
    }
}
