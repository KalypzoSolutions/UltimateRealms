package de.kalypzo.realms.player;

import de.kalypzo.realms.Constants;
import de.kalypzo.realms.RealmAPI;
import de.kalypzo.realms.RealmAPIProvider;
import de.kalypzo.realms.realm.RealmWorld;
import de.kalypzo.realms.util.ExecutionUtil;
import lombok.Getter;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Getter
public class RealmPlayerImpl implements RealmPlayer {
    private final UUID uuid;

    public RealmPlayerImpl(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public List<RealmWorld> getCachedOwningRealms() {
        return getRealmAPI().getRealmWorldManager().getRealmsByOwner(getUuid());
    }

    @Override
    public CompletableFuture<List<RealmWorld>> getOwningRealmsAsync() {
        return ExecutionUtil.supplyAsync(this::getCachedOwningRealms);
    }

    @Override
    public void removeOwningRealm(RealmWorld realm) {
        if (!realm.getOwner().equals(this)) {
            return;
        }
        realm.setOwnerUuid(Constants.REALM_OWNER_UUID_SERVER);
    }

    @Override
    public void addOwningRealm(RealmWorld realm) {
        if (realm.getOwner().equals(this)) {
            return;
        }
        realm.setOwnerUuid(getUuid());
    }

    public RealmAPI getRealmAPI() {
        return RealmAPIProvider.getInstance();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RealmPlayerImpl that = (RealmPlayerImpl) o;
        return Objects.equals(uuid, that.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(uuid);
    }
}
