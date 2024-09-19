package de.kalypzo.realms.loader;

import de.kalypzo.realms.realm.ActiveRealmWorld;
import de.kalypzo.realms.realm.RealmWorld;
import de.kalypzo.realms.realm.process.RealmProcess;

/**
 * Using this interface you can load and unload realms
 */
public interface RealmLoader {

    RealmProcess load(RealmWorld realmWorld);

    RealmProcess unload(ActiveRealmWorld realm);
}
