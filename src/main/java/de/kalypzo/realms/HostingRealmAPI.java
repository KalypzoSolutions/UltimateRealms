package de.kalypzo.realms;

import de.kalypzo.realms.world.templates.TemplateManager;
import de.kalypzo.realms.loader.BukkitWorldLoader;
import de.kalypzo.realms.loader.RealmLoader;
import de.kalypzo.realms.loader.RealmLoaderImpl;
import de.kalypzo.realms.loader.WorldLoader;
import de.kalypzo.realms.player.RealmPlayerManager;
import de.kalypzo.realms.player.RealmPlayerManagerImpl;
import de.kalypzo.realms.realm.ActiveRealmManager;
import de.kalypzo.realms.realm.RealmWorldManager;
import de.kalypzo.realms.realm.RealmWorldManagerImpl;
import de.kalypzo.realms.scheduling.RealmScheduler;
import de.kalypzo.realms.storage.RealmDataStorage;
import de.kalypzo.realms.storage.WorldFileStorage;
import de.kalypzo.realms.world.DefaultBukkitWorldCreatorProvider;
import lombok.Getter;

@Getter
public class HostingRealmAPI implements RealmAPI {

    private final RealmPlugin plugin;
    private final RealmLoader realmLoader;
    private final RealmScheduler scheduler;
    private final ActiveRealmManager activeRealmManager;
    private final RealmWorldManager realmWorldManager;
    private final RealmPlayerManager realmPlayerManager;
    private final WorldLoader worldLoader;
    private final RealmDataStorage realmDataStorage;
    private final TemplateManager templateManager;

    public HostingRealmAPI(RealmPlugin plugin, WorldFileStorage realmWorldFileStorage, WorldFileStorage templateWorldFileStorage, RealmDataStorage realmDataStorage) {
        this.plugin = plugin;
        this.realmDataStorage = realmDataStorage;
        this.worldLoader = new BukkitWorldLoader(plugin, new DefaultBukkitWorldCreatorProvider());
        this.activeRealmManager = new ActiveRealmManager();
        this.realmLoader = new RealmLoaderImpl(activeRealmManager, worldLoader, realmWorldFileStorage);
        this.scheduler = new RealmScheduler(plugin);
        this.realmWorldManager = new RealmWorldManagerImpl(this, realmDataStorage);
        this.realmPlayerManager = new RealmPlayerManagerImpl();
        this.templateManager = new TemplateManager(templateWorldFileStorage, plugin);
    }


    @Override
    public RealmPluginMode getRealmPluginMode() {
        return plugin.getRealmPluginConfiguration().getRealmPluginMode();
    }


    @Override
    public void load() {

    }

}
