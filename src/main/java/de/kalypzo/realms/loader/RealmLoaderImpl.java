package de.kalypzo.realms.loader;

import de.kalypzo.realms.realm.ActiveRealmManager;
import de.kalypzo.realms.realm.ActiveRealmWorld;
import de.kalypzo.realms.realm.ActiveRealmWorldImpl;
import de.kalypzo.realms.realm.RealmWorld;
import de.kalypzo.realms.storage.WorldFileStorage;
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
    private final WorldFileStorage worldFileStorage;

    public RealmLoaderImpl(ActiveRealmManager realmStateManager, WorldLoader worldLoader,
                           WorldFileStorage worldFileStorage) {
        this.activeRealmManager = realmStateManager;
        this.worldLoader = worldLoader;
        this.worldFileStorage = worldFileStorage;
    }


    @Override
    public ActiveRealmWorld loadRealm(RealmWorld realmWorld) {
        String realmId = realmWorld.getRealmId().toString();
        if (worldLoader.isWorldLoaded(realmId)) {
            return null;
        }
        if (!worldFileStorage.isFileExisting(realmId)) {
            return null;
        }

        worldFileStorage.loadFile(realmId, Bukkit.getWorldContainer().toPath().resolve(realmId));
        WorldHandle worldHandle = worldLoader.loadWorld(realmId);
        return loadRealm(realmWorld, worldHandle);
    }

    @Override
    public ActiveRealmWorld loadRealm(RealmWorld realmWorld, WorldHandle worldHandle) {
        var activeRealm = new ActiveRealmWorldImpl(realmWorld, activeRealmManager, this, worldHandle);
        LOGGER.info("Loaded realm {}", realmWorld.getRealmId());
        activeRealmManager.register(activeRealm);
        return activeRealm;
    }

    @Override
    public boolean isRealmLoaded(RealmWorld realmWorld) {
        return false;
    }

    @Override
    public void unloadRealm(ActiveRealmWorld realm) {
        if (!realm.isLoaded()) {
            return;
        }
        activeRealmManager.unregister(realm);
        realm.getWorldHandle().unload();
        worldFileStorage.saveFile(Bukkit.getWorldContainer().toPath().resolve(realm.getRealmId().toString()));
        LOGGER.info("Unloaded realm {}", realm.getRealmId());
    }
}
