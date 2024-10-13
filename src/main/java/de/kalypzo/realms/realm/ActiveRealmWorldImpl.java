package de.kalypzo.realms.realm;

import de.kalypzo.realms.loader.RealmLoader;
import de.kalypzo.realms.player.PlayerContainer;
import de.kalypzo.realms.player.RealmPlayer;
import de.kalypzo.realms.realm.flag.FlagContainer;
import de.kalypzo.realms.world.WorldHandle;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Getter
public class ActiveRealmWorldImpl implements ActiveRealmWorld {
    private final RealmWorld parent;
    private final long loadedTimestamp;
    private final ActiveRealmManager realmStateManager;
    private final RealmLoader loader;
    private boolean loaded;
    private final boolean isLocally = true;
    private WorldHandle worldHandle;

    public ActiveRealmWorldImpl(RealmWorld parent, ActiveRealmManager realmStateManager, RealmLoader loader, WorldHandle worldHandle) {
        this.parent = parent;
        this.loadedTimestamp = System.currentTimeMillis();
        this.realmStateManager = realmStateManager;
        this.loader = loader;
        this.loaded = true;
        this.worldHandle = worldHandle;
    }

    @Override
    public Duration getUptime() {
        return Duration.ofMillis(getUptimeMillis());
    }

    @Override
    public long getUptimeMillis() {
        return System.currentTimeMillis() - loadedTimestamp;
    }

    @Override
    public boolean isIdle() {
        return realmStateManager.isIdle(this);
    }

    @Override
    public List<RealmPlayer> getPlayers() {
        List<RealmPlayer> players = new LinkedList<>();
        for (Player bukkitPlayer : worldHandle.getPlayers()) {
            players.add(RealmPlayer.of(bukkitPlayer.getUniqueId()));
        }
        return players;
    }


    @Override
    public boolean isLoadedLocally() {
        return isLoaded() && isLocally;
    }

    @Override
    public FlagContainer getFlags() {
        return parent.getFlags();
    }

    @Override
    public PlayerContainer getTrustedMembers() {
        return parent.getTrustedMembers();
    }

    @Override
    public PlayerContainer getBannedMembers() {
        return parent.getBannedMembers();
    }

    @Override
    public RealmPlayer getOwner() {
        return parent.getOwner();
    }

    @Override
    public UUID getOwnerUuid() {
        return parent.getOwnerUuid();
    }

    @Override
    public UUID getRealmId() {
        return parent.getRealmId();
    }

    @Override
    public void setOwnerUuid(UUID ownerUuid) {
        parent.setOwnerUuid(ownerUuid);
    }

    @Override
    public void onObserverUnsubscribed() {
        worldHandle = null;
        loaded = false;
    }
}
