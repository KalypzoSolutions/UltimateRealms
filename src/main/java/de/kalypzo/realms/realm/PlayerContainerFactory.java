package de.kalypzo.realms.realm;

import de.kalypzo.realms.player.PlayerContainer;
import de.kalypzo.realms.player.PlayerContainerImpl;
import de.kalypzo.realms.player.RealmPlayerManager;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.UUID;

@Getter
@Setter
public class PlayerContainerFactory {
    private RealmPlayerManager playerManager;

    public PlayerContainerFactory() {
    }

    public PlayerContainer createPlayerContainer() {
        checkPlayerManagerIsNotNull();
        return new PlayerContainerImpl(playerManager);
    }

    public PlayerContainer createPlayerContainer(Collection<UUID> uuids) {
        checkPlayerManagerIsNotNull();
        return new PlayerContainerImpl(uuids, playerManager);
    }

    protected void checkPlayerManagerIsNotNull() {
        if (playerManager == null) {
            throw new IllegalStateException("PlayerManager is not set");
        }
    }

}
