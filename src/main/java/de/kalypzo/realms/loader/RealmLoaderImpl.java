package de.kalypzo.realms.loader;

import de.kalypzo.realms.realm.ActiveRealmManager;
import de.kalypzo.realms.realm.ActiveRealmWorld;
import de.kalypzo.realms.realm.ActiveRealmWorldImpl;
import de.kalypzo.realms.realm.RealmWorld;
import de.kalypzo.realms.realm.process.impl.UniversalProcessExecutor;
import de.kalypzo.realms.storage.RealmWorldFileStorage;
import de.kalypzo.realms.world.WorldHandle;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Getter
public class RealmLoaderImpl implements RealmLoader {
    private static final Logger LOGGER = LoggerFactory.getLogger(RealmLoaderImpl.class);
    private final ActiveRealmManager activeRealmManager;
    private final WorldLoader worldLoader;
    private final RealmWorldFileStorage realmWorldFileStorage;
    private final UniversalProcessExecutor processExecutor;

    public RealmLoaderImpl(ActiveRealmManager realmStateManager, WorldLoader worldLoader,
                           RealmWorldFileStorage realmWorldFileStorage,
                           UniversalProcessExecutor processExecutor) {
        this.activeRealmManager = realmStateManager;
        this.worldLoader = worldLoader;
        this.realmWorldFileStorage = realmWorldFileStorage;
        this.processExecutor = processExecutor;
    }


    @Override
    public ActiveRealmWorld loadRealm(RealmWorld realmWorld) {
        String realmId = realmWorld.getRealmId().toString();
        if (worldLoader.isWorldLoaded(realmId)) {
            return null;
        }
        if (!realmWorldFileStorage.isFileExisting(realmId)) {
            return null;
        }

        realmWorldFileStorage.loadFile(realmId, Bukkit.getWorldContainer().toPath().resolve(realmId));
        WorldHandle worldHandle = worldLoader.loadWorld(realmId);
        var activeRealm = new ActiveRealmWorldImpl(realmWorld, activeRealmManager, this, worldHandle);
        LOGGER.info("Loaded realm {}", realmWorld.getRealmId());
        activeRealmManager.register(activeRealm);
        return activeRealm;
    }

    @Override
    public void unloadRealm(ActiveRealmWorld realm) {
        if (!realm.isLoaded()) {
            return;
        }
        activeRealmManager.unregister(realm);
        realm.getWorldHandle().unload();
        realmWorldFileStorage.saveFile(Bukkit.getWorldContainer().toPath().resolve(realm.getRealmId().toString()));
        LOGGER.info("Unloaded realm {}", realm.getRealmId());
    }
}
