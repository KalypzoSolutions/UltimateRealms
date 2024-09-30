package de.kalypzo.realms.player;

import de.kalypzo.realms.RealmAPI;
import de.kalypzo.realms.RealmAPIProvider;
import de.kalypzo.realms.realm.RealmWorld;
import de.kalypzo.realms.util.ExecutionUtil;
import lombok.Getter;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Getter
public class RealmPlayerImpl implements RealmPlayer {
    private final UUID uuid;

    public RealmPlayerImpl(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public List<RealmWorld> getOwningRealms() {
        return getRealmAPI().getRealmWorldManager().getRealmsByOwner(getUuid());
    }

    @Override
    public CompletableFuture<List<RealmWorld>> getOwningRealmsAsync() {
        return ExecutionUtil.supplyAsync(getRealmAPI().getPlugin(), this::getOwningRealms);
    }

    public RealmAPI getRealmAPI() {
        return RealmAPIProvider.getInstance();
    }


}
