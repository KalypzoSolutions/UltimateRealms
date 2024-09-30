package de.kalypzo.realms.realm;

import de.kalypzo.realms.player.RealmPlayer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class RealmWorldManagerImpl implements RealmWorldManager {
    @Override
    public void transferOwnership(RealmWorld realmWorld, RealmPlayer newOwner) {

    }

    @Override
    public void deleteRealm(RealmWorld realmWorld) {

    }

    @Override
    public void createRealm(RealmCreationContext context) {

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
