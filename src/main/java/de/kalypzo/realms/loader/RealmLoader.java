package de.kalypzo.realms.loader;

import de.kalypzo.realms.realm.ActiveRealmWorld;
import de.kalypzo.realms.realm.RealmWorld;
import de.kalypzo.realms.realm.process.RealmProcess;

/**
 * Using this interface you can load and unload realms
 */
public interface RealmLoader {

    /**
     * Load a realm by its id
     *
     * @param realmId the id of the realm
     * @return the realm process
     */
    RealmProcess<ActiveRealmWorld> loadRealm(String realmId);

    /**
     * Load a realm
     *
     * @param realmWorld the realm world
     * @return the realm process
     */
    RealmProcess<ActiveRealmWorld> loadRealm(RealmWorld realmWorld);

    /**
     * Unload a realm
     *
     * @param realm the realm to unload
     * @return the realm process
     */
    RealmProcess<?> unloadRealm(ActiveRealmWorld realm);
}
