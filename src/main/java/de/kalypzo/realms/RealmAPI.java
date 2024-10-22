package de.kalypzo.realms;

import de.kalypzo.realms.loader.RealmLoader;
import de.kalypzo.realms.loader.WorldLoader;
import de.kalypzo.realms.player.RealmPlayerManager;
import de.kalypzo.realms.realm.RealmWorldManager;
import de.kalypzo.realms.scheduling.RealmScheduler;

public interface RealmAPI {

    RealmPlugin getPlugin();

    RealmPluginMode getRealmPluginMode();

    RealmWorldManager getRealmWorldManager();

    RealmPlayerManager getRealmPlayerManager();

    RealmScheduler getScheduler();

    RealmLoader getRealmLoader();

    WorldLoader getWorldLoader();

    void load();

}
