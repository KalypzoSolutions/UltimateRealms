package de.kalypzo.realms.realm;

import de.kalypzo.realms.RealmAPI;
import de.kalypzo.realms.event.RealmTransferOwnershipEvent;
import de.kalypzo.realms.loader.RealmLoader;
import de.kalypzo.realms.player.RealmPlayer;
import de.kalypzo.realms.storage.RealmDataStorage;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.CompletableFuture;

@Slf4j
public class RealmWorldManagerImpl implements RealmWorldManager {
    private final RealmAPI api;
    private final RealmDataStorage realmDataStorage;
    private final Map<UUID, RealmWorld> cachedRealms = new HashMap<>();

    public RealmWorldManagerImpl(RealmAPI api, RealmDataStorage realmDataStorage) {
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
        RealmLoader realmLoader = getRealmLoader();
        if (realmLoader.isRealmLoaded(realmWorld)) {
            realmLoader.unloadRealm(realmLoader.getActiveRealmManager().getActiveRealmWorld(realmWorld));
        }
        cachedRealms.remove(realmWorld.getRealmId());
        realmDataStorage.deleteRealm(realmWorld);
    }

    @Override
    public CompletableFuture<ActiveRealmWorld> createRealm(RealmCreationContext context) {
        return new RealmCreator(context, api.getPlugin()).create();
    }

    public RealmLoader getRealmLoader() {
        return api.getRealmLoader();
    }

    @Override
    public List<RealmWorld> getRealmsByOwner(UUID owner) {
        return List.of(); //TODO
    }

    @Override
    public Optional<RealmWorld> getRealmWorldById(UUID realmId) {
        for (RealmWorld realmWorld : cachedRealms.values()) {
            if (realmWorld.getRealmId().equals(realmId)) {
                return Optional.of(realmWorld);
            }
        }
        return realmDataStorage.loadRealmById(realmId);
    }
}
