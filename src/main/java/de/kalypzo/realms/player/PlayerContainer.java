package de.kalypzo.realms.player;

import java.util.Collection;
import java.util.UUID;

/**
 * Collection of players
 */
public interface PlayerContainer {

    boolean contains(UUID playerUuid);

    Collection<UUID> getPlayerUuids();

    Collection<RealmPlayer> getPlayers();

    boolean addPlayer(UUID playerUuid);

    boolean removePlayer(UUID playerUuid);


}
