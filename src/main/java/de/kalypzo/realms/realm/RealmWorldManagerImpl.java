package de.kalypzo.realms.realm;

import de.kalypzo.realms.RealmAPI;
import de.kalypzo.realms.event.RealmTransferOwnershipEvent;
import de.kalypzo.realms.loader.RealmLoader;
import de.kalypzo.realms.player.RealmPlayer;
import de.kalypzo.realms.storage.RealmDataStorage;

import java.util.*;

public class RealmWorldManagerImpl implements RealmWorldManager {
    private final RealmAPI api;
    private final RealmDataStorage realmDataStorage;
    private final Map<UUID, RealmWorld> cachedRealms = new HashMap<>();

    public RealmWorldManagerImpl(RealmAPI api, RealmDataStorage realmDataStorage, RealmLoader realmLoader) {
        this.api = api;
        this.realmDataStorage = realmDataStorage;
    }

    @Override
    public void transferOwnership(RealmWorld realmWorld, RealmPlayer newOwner) {
        RealmPlayer oldOwner = realmWorld.getOwner();
        if (new RealmTransferOwnershipEvent(realmWorld, oldOwner, newOwner).callEvent()) {
            return;
        }
        realmWorld.setOwnerUuid(newOwner.getUuid());
        oldOwner.removeOwningRealm(realmWorld);
        newOwner.addOwningRealm(realmWorld);
    }

    @Override
    public void deleteRealm(RealmWorld realmWorld) {
        RealmLoader realmLoader = api.getRealmLoader();
        if (realmLoader.isRealmLoaded(realmWorld)) {
            realmLoader.unloadRealm(realmLoader.getActiveRealmManager().getActiveRealmWorld(realmWorld));
        }
        cachedRealms.remove(realmWorld.getRealmId());
        realmDataStorage.deleteRealm(realmWorld);
    }

    @Override
    public ActiveRealmWorld createRealm(RealmCreationContext context) {

    }

    @Override
    public List<RealmWorld> getRealmsByOwner(UUID owner) {
        return List.of();
    }

    @Override
    public Optional<RealmWorld> getRealmWorldById(UUID realmId) {
        return Optional.empty();
    }
}
