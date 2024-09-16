package de.kalypzo.realms.realm;

import de.kalypzo.realms.player.RealmPlayer;

public interface RealmWorldManager {


    void transferOwnership(RealmWorld realmWorld, RealmPlayer newOwner);

    void deleteRealm(RealmWorld realmWorld);

    void createRealm(RealmPlayer owner);
}
