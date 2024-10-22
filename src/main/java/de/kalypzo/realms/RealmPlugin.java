package de.kalypzo.realms;

import de.kalypzo.realms.command.CommandManager;
import de.kalypzo.realms.config.WorldFileStorageConfiguration;
import de.kalypzo.realms.config.impl.RealmPluginConfigurationImpl;
import de.kalypzo.realms.config.impl.TemplateWorldFileStorageConfig;
import de.kalypzo.realms.realm.PlayerContainerFactory;
import de.kalypzo.realms.realm.RealmWorldFactory;
import de.kalypzo.realms.storage.LocalWorldFileStorage;
import de.kalypzo.realms.storage.SSHWorldFileStorage;
import de.kalypzo.realms.storage.WorldFileStorage;
import de.kalypzo.realms.storage.bundler.ZipBundler;
import de.kalypzo.realms.storage.mongo.MongoRealmDataStorage;
import de.kalypzo.realms.util.ExecutionUtil;
import io.leangen.geantyref.TypeToken;
import lombok.Getter;
import net.schmizz.sshj.SSHClient;
import org.apache.commons.io.FileUtils;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.loader.HeaderMode;
import org.spongepowered.configurate.yaml.NodeStyle;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Loads configuration and initializes the correct RealmAPI implementation
 */
@Getter
public class RealmPlugin extends JavaPlugin {
    @Getter
    private @Nullable SSHClient sshClient;
    private static RealmPlugin instance;
    private RealmPluginConfigurationImpl realmPluginConfiguration;
    private RealmWorldFactory realmWorldFactory;
    private PlayerContainerFactory playerContainerFactory;
    private CommandManager commandManager;
    private final Path temporaryDirectory = getDataPath().resolve("temp");

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
        try {
            Files.createDirectories(temporaryDirectory);
            getSLF4JLogger().info("Created temporary directory");
        } catch (IOException e) {
            getSLF4JLogger().error("Failed to create temporary directory", e);
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onEnable() {
        instance = this;
        ExecutionUtil.init(this);
        playerContainerFactory = new PlayerContainerFactory();
        realmWorldFactory = new RealmWorldFactory(playerContainerFactory);
        commandManager = new CommandManager(this);

        if (isHostingRealms()) {
            RealmAPIProvider.setInstance(createHostingAPI());
        } else {
            RealmAPIProvider.setInstance(new ForwardingRealmAPI(this));
        }
    }

    public WorldFileStorage createFileStorage(boolean templateStorage) {
        if (templateStorage) {
            return new LocalWorldFileStorage(new TemplateWorldFileStorageConfig(this));
        } else {
            var config = realmPluginConfiguration.getWorldFileConfiguration();
            if (config.getStorageType() == WorldFileStorageConfiguration.StorageType.LOCAL) {
                return new LocalWorldFileStorage(config);
            } else if (config.getStorageType() == WorldFileStorageConfiguration.StorageType.SSH) {
                return new SSHWorldFileStorage(sshClient, config, new ZipBundler(temporaryDirectory), temporaryDirectory);
            } else {
                throw new IllegalArgumentException("Unknown storage type");
            }
        }
    }

    public SSHClient createIfNotExistsSSHClient() {
        if (sshClient == null) {
            sshClient = new SSHClient();
        }
        return sshClient;
    }

    public RealmAPI createHostingAPI() {
        RealmAPI api = new HostingRealmAPI(
                this,
                createFileStorage(false),
                createFileStorage(true),
                new MongoRealmDataStorage(
                        realmPluginConfiguration.getMongoConfiguration(),
                        realmWorldFactory
                )

        );
        playerContainerFactory.setPlayerManager(api.getRealmPlayerManager());
        api.load();
        return api;
    }

    @Override
    public void onDisable() {
        instance = null;
        if (sshClient != null) {
            try {
                sshClient.disconnect();
            } catch (IOException e) {
                getSLF4JLogger().error("Failed to disconnect SSH client", e);
            }
        }
        try {
            FileUtils.cleanDirectory(temporaryDirectory.toFile());
            getSLF4JLogger().info("Cleaned temporary directory");
        } catch (IOException e) {
            getSLF4JLogger().error("Failed to clean temporary directory", e);
        }
    }

    public boolean isHostingRealms() {
        return !(realmPluginConfiguration.getRealmPluginMode() == RealmPluginMode.FORWARDING);
    }


}
