package de.kalypzo.realms;

import de.kalypzo.realms.loader.RealmLoader;
import de.kalypzo.realms.realm.RealmWorldManager;
import de.kalypzo.realms.scheduling.RealmScheduler;
import lombok.Getter;

@Getter
public class HostingRealmAPI implements RealmAPI {

    private final RealmPlugin plugin;
    private final RealmLoader realmLoader;
    private final RealmScheduler scheduler;
    private final RealmWorldManager realmWorldManager;

    public HostingRealmAPI(RealmPlugin plugin, RealmLoader realmLoader, RealmWorldManager realmWorldManager) {
        this.plugin = plugin;
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
