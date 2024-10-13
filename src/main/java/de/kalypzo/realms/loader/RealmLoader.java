package de.kalypzo.realms.loader;

import de.kalypzo.realms.realm.ActiveRealmManager;
import de.kalypzo.realms.realm.ActiveRealmWorld;
import de.kalypzo.realms.realm.RealmWorld;

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
     * Unload a realm
     *
     * @param realm the realm to unload
     */
    void unloadRealm(ActiveRealmWorld realm);


    ActiveRealmManager getActiveRealmManager();

    boolean isRealmLoaded(RealmWorld realmWorld);


}
