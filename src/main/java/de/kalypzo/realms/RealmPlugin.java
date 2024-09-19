package de.kalypzo.realms;

import de.kalypzo.realms.config.RealmPluginConfiguration;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Loads configuration and initializes the correct RealmAPI implementation
 */
@Getter
public class RealmPlugin extends JavaPlugin {
    private RealmPluginConfiguration realmPluginConfiguration;

    @Override
    public void onEnable() {
        RealmAPIProvider.setInstance(new ForwardingRealmAPI(this));

    }

    public boolean isHostingRealms() {
        return !(realmPluginConfiguration.getRealmPluginMode() == RealmPluginMode.FORWARDING);
    }


}
