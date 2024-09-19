package de.kalypzo.realms;

import de.kalypzo.realms.loader.RealmLoader;
import de.kalypzo.realms.loader.WorldLoader;
import de.kalypzo.realms.scheduling.RealmScheduler;
import lombok.Getter;


/**
 * When the plugin mode is set to {@link RealmPluginMode#FORWARDING} this class is just reading from database or throwing exceptions
 */
@Getter
public class ForwardingRealmAPI implements RealmAPI {
    /**
     * The plugin instance
     */
    private final RealmPlugin plugin;

    public ForwardingRealmAPI(RealmPlugin plugin) {
        this.plugin = plugin;
    }

    public RealmScheduler getScheduler() {
        throw new ForwardingModeDoesNotSupportThisMethodException();
    }

    public WorldLoader getWorldLoader() {
        throw new ForwardingModeDoesNotSupportThisMethodException();
    }

    public RealmLoader getRealmLoader() {
        throw new ForwardingModeDoesNotSupportThisMethodException();
    }

    public void load() {

    }

    @Override
    public RealmPluginMode getRealmPluginMode() {
        return null;
    }
}
