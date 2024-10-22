package de.kalypzo.realms.config.impl;

import de.kalypzo.realms.config.WorldFileStorageConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class TemplateWorldFileStorageConfig implements WorldFileStorageConfiguration {

    private final JavaPlugin plugin;

    public TemplateWorldFileStorageConfig(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public StorageType getStorageType() {
        return StorageType.LOCAL;
    }

    @Override
    public String getRemoteFolder() {
        return plugin.getDataFolder().getAbsolutePath() + "/templates/";
    }
}
