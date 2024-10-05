package de.kalypzo.realms;

import de.kalypzo.realms.loader.RealmLoader;
import de.kalypzo.realms.loader.WorldLoader;
import de.kalypzo.realms.realm.RealmWorldManager;
import de.kalypzo.realms.scheduling.RealmScheduler;
import lombok.Getter;

@Getter
public class HostingRealmAPI implements RealmAPI {

    private final RealmPlugin plugin;
    private final WorldLoader worldLoader;
    private final RealmLoader realmLoader;
    private final RealmScheduler scheduler;
    private final RealmWorldManager realmWorldManager;

    public HostingRealmAPI(RealmPlugin plugin, WorldLoader worldLoader, RealmLoader realmLoader, RealmWorldManager realmWorldManager) {
        this.plugin = plugin;
        this.worldLoader = worldLoader;
        this.realmLoader = realmLoader;
        this.scheduler = new RealmScheduler(plugin);
        this.realmWorldManager = realmWorldManager;
    }

    @Override
    public RealmPluginMode getRealmPluginMode() {
        return plugin.getRealmPluginConfiguration().getRealmPluginMode();
    }


    @Override
    public void load() {

    }

}
