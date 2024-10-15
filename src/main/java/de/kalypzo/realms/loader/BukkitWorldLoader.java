package de.kalypzo.realms.loader;

import com.google.common.base.Preconditions;
import de.kalypzo.realms.RealmPlugin;
import de.kalypzo.realms.world.*;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldUnloadEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.*;

/**
 * Creates a world handle from a world file and prevents the world from being unloaded without "permission".
 */
@Getter
public class BukkitWorldLoader implements WorldLoader, Listener {

    private static final Logger LOGGER = LoggerFactory.getLogger(BukkitWorldLoader.class);
    private final List<WorldObserverFactory> observerFactoryList = new LinkedList<>();
    private final List<WorldHandle> loadedWorlds = new ArrayList<>();
    private final Set<String> unloadingBlacklist = new HashSet<>();
    private final Queue<World> unloadingQueue = new LinkedList<>();

    public BukkitWorldLoader(RealmPlugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }


    public void processUnloadingQueue() { //TODO: not used
        while (!unloadingQueue.isEmpty()) {
            World world = unloadingQueue.poll();
            if (isUnloadingBukkitFailing(world)) {
                unloadingQueue.add(world);
                break;
            }
        }
    }

    @Override
    public boolean isWorldLoaded(@NotNull String fileName) {
        return loadedWorlds.stream().anyMatch(worldHandle -> worldHandle.getWorldName().equals(fileName));
    }

    @Override
    public void addWorldObserverFactory(WorldObserverFactory factory) {
        observerFactoryList.add(factory);
    }

    @Override
    public void removeWorldObserverFactory(WorldObserverFactory factory) {
        observerFactoryList.remove(factory);
    }

    public @Nullable WorldHandle getWorldHandle(@NotNull String worldName) {
        return loadedWorlds.stream().filter(worldHandle -> worldHandle.getWorldName().equals(worldName)).findFirst().orElse(null);
    }

    public @Nullable WorldHandle loadWorld(@NotNull String worldName) {
        Preconditions.checkNotNull(worldName, "worldName must not be null");
        return loadWorld(worldName, getDefaultWorldCreator());
    }

    /**
     * Expects the world to be in the world container of the server.
     */
    public @Nullable WorldHandle loadWorld(@NotNull String worldName, @NotNull WorldCreator worldCreator) {
        Preconditions.checkNotNull(worldName, "worldName must not be null");
        getLogger().info("Loading world: {}", worldName);
        if (isWorldLoaded(worldName)) {
            getLogger().debug("World {} is already loaded, returned existing handle.", worldName);
            return getWorldHandle(worldName);
        }
        File worldFolder = new File(Bukkit.getWorldContainer(), worldName);
        if (!worldFolder.exists()) {
            getLogger().debug("World {} does not exist.", worldName);
            return null;
        }
        if (!worldFolder.isDirectory()) {
            throw new IllegalStateException("World " + worldName + " is not a directory.");
        }

        World world = new WorldCreator(worldName).copy(worldCreator).createWorld();
        if (world == null) {
            throw new IllegalStateException("WorldCreator returned null on: " + worldName);
        }
        BukkitWorldHandle worldHandle = new BukkitWorldHandle(world, this);
        for (WorldObserverFactory observerFactory : observerFactoryList) {
            worldHandle.subscribe(observerFactory.createWorldObserver(worldHandle));
        }
        loadedWorlds.add(worldHandle);
        return worldHandle;
    }

    public FallbackWorld getFallbackWorld() {
        return FallbackWorld.getInstance();
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
        worldHandle.notifyObservers(WorldObserver::onWorldUnload);
        World bukkitWorld = worldHandle.getBukkitWorldOrElseThrow();
        getUnloadingBlacklist().remove(worldHandle.getWorldName());
        loadedWorlds.remove(worldHandle);
        // kick all players from the world
        Location fallbackLocation = getFallbackWorld().getWorldHandle().getSpawnLocation();
        for (Player player : bukkitWorld.getPlayers()) {
            player.teleport(fallbackLocation);
        }
        getLogger().debug("Kicked all players from world: {}", worldHandle.getWorldName());
        for (WorldObserver observer : worldHandle.getObservers()) {
            worldHandle.unsubscribe(observer);
        }
        worldHandle.getBukkitWorldReference().clear();
        if (isUnloadingBukkitFailing(bukkitWorld)) { // if the world can not be unloaded right now, try again later
            unloadingQueue.add(bukkitWorld);
        }
        return true;
    }

    private boolean isUnloadingBukkitFailing(World bukkitWorld) {
        if (Bukkit.isTickingWorlds()) {
            return true;
        }
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

    public Logger getLogger() {
        return LOGGER;
    }

    public WorldCreator getDefaultWorldCreator() {
        return new WorldCreator("world");
    }

}
