package de.kalypzo.realms.loader;

import de.kalypzo.realms.realm.ActiveRealmManager;
import de.kalypzo.realms.realm.ActiveRealmWorld;
import de.kalypzo.realms.realm.RealmWorld;
import de.kalypzo.realms.world.WorldHandle;

/**
 * Using this interface you can load and unload realms
 */
public interface RealmLoader {

    /**
     * Load a realm
     *
     * @param realmWorld the realm world
     * @return the realm process
     */
    ActiveRealmWorld loadRealm(RealmWorld realmWorld);

    /**
     * Load a realm
     *
     * @param world       the realm world
     * @param worldHandle the world handle
     * @return the realm process
     */
    ActiveRealmWorld loadRealm(RealmWorld world, WorldHandle worldHandle);

    /**
     * Unload a realm
     *
     * @param realm the realm to unload
     */
    void unloadRealm(ActiveRealmWorld realm);


    ActiveRealmManager getActiveRealmManager();

    boolean isRealmLoaded(RealmWorld realmWorld);


}
