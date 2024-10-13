package de.kalypzo.realms;

import de.kalypzo.realms.command.CommandManager;
import de.kalypzo.realms.config.impl.RealmPluginConfigurationImpl;
import io.leangen.geantyref.TypeToken;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.loader.HeaderMode;
import org.spongepowered.configurate.yaml.NodeStyle;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

/**
 * Loads configuration and initializes the correct RealmAPI implementation
 */
@Getter
public class RealmPlugin extends JavaPlugin {
    private RealmPluginConfigurationImpl realmPluginConfiguration;
    private CommandManager commandManager;

    @Override
    public void onLoad() {
        YamlConfigurationLoader configLoader = YamlConfigurationLoader.builder().path(getDataPath().resolve("config.yml"))
                .nodeStyle(NodeStyle.BLOCK).headerMode(HeaderMode.PRESERVE).build();
        try {
            var node = configLoader.load();
            realmPluginConfiguration = node.get(TypeToken.get(RealmPluginConfigurationImpl.class));
            node.set(TypeToken.get(RealmPluginConfigurationImpl.class), realmPluginConfiguration);
            configLoader.save(node);
            getSLF4JLogger().info("Loaded configuration");
        } catch (ConfigurateException e) {
            getSLF4JLogger().error("Failed to load configuration", e);
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onEnable() {
        commandManager = new CommandManager(this);
        RealmAPIProvider.setInstance(new ForwardingRealmAPI(this));

    }


    public boolean isHostingRealms() {
        return !(realmPluginConfiguration.getRealmPluginMode() == RealmPluginMode.FORWARDING);
    }


}
