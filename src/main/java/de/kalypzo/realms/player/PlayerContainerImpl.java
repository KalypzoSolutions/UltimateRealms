package de.kalypzo.realms.player;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class PlayerContainerImpl implements PlayerContainer {
    private final Set<UUID> playerUuids = new HashSet<>();
    private final RealmPlayerManager realmPlayerManager;

    public PlayerContainerImpl(Collection<UUID> playerUuids, RealmPlayerManager realmPlayerManager) {
        this.playerUuids.addAll(playerUuids);
        this.realmPlayerManager = realmPlayerManager;
    }

    @Override
    public boolean contains(UUID playerUuid) {
        return playerUuids.contains(playerUuid);
    }

    @Override
    public Collection<UUID> getPlayerUuids() {
        return playerUuids;
    }

    @Override
    public Collection<RealmPlayer> getPlayers() {
        return realmPlayerManager.getPlayersByUuids(playerUuids);
    }

    @Override
    public boolean addPlayer(UUID playerUuid) {
        return playerUuids.add(playerUuid);
    }

    @Override
    public boolean removePlayer(UUID playerUuid) {
        return playerUuids.remove(playerUuid);
    }
}
