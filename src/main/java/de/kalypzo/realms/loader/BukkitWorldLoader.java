package de.kalypzo.realms.loader;

import de.kalypzo.realms.world.BukkitWorldHandle;
import de.kalypzo.realms.world.FallbackWorld;
import de.kalypzo.realms.world.WorldHandle;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldUnloadEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Creates a world handle from a world file and prevents the world from being unloaded without "permission".
 */
public class BukkitWorldLoader implements WorldLoader, Listener {

    private static final Logger LOGGER = LoggerFactory.getLogger(BukkitWorldLoader.class);
    private final List<WorldHandle> loadedWorlds = new ArrayList<>();
    private final Set<String> unloadingWhitelist = new HashSet<>();

    public BukkitWorldLoader(JavaPlugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    /**
     * Expects the world to be in the world container of the server.
     */
    public WorldHandle loadWorld(@NotNull String worldName) {
        return null;
    }

    /**
     * @param unknownHandle a handle to the world to unload.
     * @return true if the world was unloaded successfully, false otherwise.
     * @throws IllegalArgumentException if the world is not instanceof {@link BukkitWorldHandle}
     */
    @Override
    public boolean unloadWorld(@NotNull WorldHandle unknownHandle) {
        if (!(unknownHandle instanceof BukkitWorldHandle worldHandle)) {
            throw new IllegalArgumentException("WorldHandle (" + unknownHandle + ") is not an instance of BukkitWorldHandle");
        }
        getLogger().info("Unloading world: {}", worldHandle.getWorldName());
        getUnloadingWhitelist().add(worldHandle.getWorldName());
        World bukkitWorld = worldHandle.getBukkitWorldOrElseThrow();
        Location fallbackLocation = FallbackWorld.getInstance().getWorldHandle().getSpawnLocation();
        for (Player player : bukkitWorld.getPlayers()) {
            player.teleport(fallbackLocation);
        }
        getUnloadingWhitelist().remove(worldHandle.getWorldName());
        worldHandle.getBukkitWorldReference().clear();
        worldHandle.getObservers().clear();

        return false;
    }

    @EventHandler
    public void preventUnloadingIfNotDoneByLoader(WorldUnloadEvent event) {
        String worldName = event.getWorld().getName();
        for (WorldHandle loadedWorld : getLoadedWorlds()) {
            if (loadedWorld.getWorldName().equals(worldName)) {
                if (!getUnloadingWhitelist().contains(worldName)) {
                    event.setCancelled(true);
                    getLogger().warn("World {} is not allowed to be unloaded by BukkitWorldLoader.", worldName);
                }
                return;
            }
        }
    }

    public List<WorldHandle> getLoadedWorlds() {
        return loadedWorlds;
    }

    public Logger getLogger() {
        return LOGGER;
    }

    /**
     * @return set of world names that are allowed to be unloaded.
     */
    public Set<String> getUnloadingWhitelist() {
        return unloadingWhitelist;
    }
}
