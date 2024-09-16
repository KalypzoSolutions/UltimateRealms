package de.kalypzo.realms.player;

import de.kalypzo.realms.realm.RealmWorld;

import java.util.List;

public interface RealmPlayer {

    /**
     * @return Immutable list of realms that this player owns.
     */
    List<RealmWorld> getOwningRealms();



}
