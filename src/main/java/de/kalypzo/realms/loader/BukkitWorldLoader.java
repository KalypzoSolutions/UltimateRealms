package de.kalypzo.realms.loader;

import de.kalypzo.realms.world.BukkitWorldHandle;
import de.kalypzo.realms.world.FallbackWorld;
import de.kalypzo.realms.world.WorldHandle;
import org.bukkit.Bukkit;
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

import java.util.*;

/**
 * Creates a world handle from a world file and prevents the world from being unloaded without "permission".
 */
public class BukkitWorldLoader implements WorldLoader, Listener {

    private static final Logger LOGGER = LoggerFactory.getLogger(BukkitWorldLoader.class);
    private final List<WorldHandle> loadedWorlds = new ArrayList<>();
    private final Set<String> unloadingBlacklist = new HashSet<>();
    private final Queue<World> unloadingQueue = new LinkedList<>();

    public BukkitWorldLoader(JavaPlugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public void processUnloadingQueue() {
        while (!unloadingQueue.isEmpty()) {
            World world = unloadingQueue.poll();
            if (isUnloadingBukkitFailing(world)) {
                unloadingQueue.add(world);
                break;
            }
        }
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
        World bukkitWorld = worldHandle.getBukkitWorldOrElseThrow();
        getUnloadingBlacklist().remove(worldHandle.getWorldName());
        loadedWorlds.remove(worldHandle);
        // kick all players from the world
        Location fallbackLocation = FallbackWorld.getInstance().getWorldHandle().getSpawnLocation();
        for (Player player : bukkitWorld.getPlayers()) {
            player.teleport(fallbackLocation);
        }
        getLogger().debug("Kicked all players from world: {}", worldHandle.getWorldName());
        worldHandle.getBukkitWorldReference().clear();
        worldHandle.getObservers().clear();
        if (isUnloadingBukkitFailing(bukkitWorld)) { // if the world can not be unloaded right now, try again later
            unloadingQueue.add(bukkitWorld);
        }
        return true;
    }

    private boolean isUnloadingBukkitFailing(World bukkitWorld) {
        if (Bukkit.isTickingWorlds()) {
            return true;
        };
        return !Bukkit.unloadWorld(bukkitWorld, true);
    }

    @EventHandler
    public void preventUnloadingIfNotDoneByLoader(WorldUnloadEvent event) {
        String worldName = event.getWorld().getName();
        if (getUnloadingBlacklist().contains(worldName)) {
            event.setCancelled(true);
            getLogger().warn("World {} is not allowed to be unloaded by BukkitWorldLoader.", worldName);
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
    public Set<String> getUnloadingBlacklist() {
        return unloadingBlacklist;
    }
}
