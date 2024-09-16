package de.kalypzo.realms.network;


import de.kalypzo.realms.realm.RealmWorld;

import java.util.List;

/**
 * Represents a host that can be connected to.
 */
public interface RealmHost {

    /**
     * @return the host's id
     */
    String getHostId();


    /**
     * @return a list of all loaded worlds on this host
     */
    List<RealmWorld> getLoadedWorlds();

}
