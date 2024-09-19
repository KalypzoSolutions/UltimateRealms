package de.kalypzo.realms;

import de.kalypzo.realms.loader.RealmLoader;
import de.kalypzo.realms.loader.WorldLoader;
import de.kalypzo.realms.scheduling.RealmScheduler;

public interface RealmAPI {

    RealmPlugin getPlugin();

    RealmPluginMode getRealmPluginMode();

    RealmScheduler getScheduler();

    WorldLoader getWorldLoader();

    RealmLoader getRealmLoader();

    void load();

}
