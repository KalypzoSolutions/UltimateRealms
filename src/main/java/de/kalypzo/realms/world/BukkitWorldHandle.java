package de.kalypzo.realms.world;

import com.google.common.base.Preconditions;
import de.kalypzo.realms.loader.BukkitWorldLoader;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.List;

public class BukkitWorldHandle implements WorldHandle {
    private final String worldName;
    private final WeakReference<World> bukkitWorldReference;
    private final LinkedList<WorldObserver> observers;
    private final BukkitWorldLoader worldLoader;
    private boolean unloadedByLoader = false;

    public BukkitWorldHandle(@NotNull World world, @NotNull LinkedList<WorldObserver> observers, @NotNull BukkitWorldLoader worldLoader) {
        Preconditions.checkNotNull(world, "world can not be null");
        Preconditions.checkNotNull(observers, "observers can not be null");
        Preconditions.checkNotNull(worldLoader, "worldLoader can not be null");
        this.bukkitWorldReference = new WeakReference<>(world);
        this.worldName = world.getName();
        this.observers = observers;
        this.worldLoader = worldLoader;
    }

    @Override
    public LinkedList<WorldObserver> getObservers() {
        return observers;
    }

    @Override
    public boolean save() {
        getBukkitWorldOrElseThrow().save();
        return true;
    }

    public @NotNull World getBukkitWorldOrElseThrow() {
        World world = bukkitWorldReference.get();
        if (world == null) {
            throw new IllegalStateException(getWorldName() + "'s Handle no longer available since reference to world is null.");
        }
        return world;
    }

    @Override
    public boolean unload() {
        return getWorldLoader().unloadWorld(this);
    }


    @Override
    public @NotNull String getWorldName() {
        return worldName;
    }

    /**
     * @return true if the world was unloaded by the loader, false otherwise.
     */
    public boolean isUnloadedByLoader() {
        return unloadedByLoader;
    }

    @Override
    public @NotNull BukkitWorldLoader getWorldLoader() {
        return worldLoader;
    }

    @Override
    public @NotNull Location getSpawnLocation() {
        return new Location(getBukkitWorldOrElseThrow(), 0, 64, 0);
    }

    public @NotNull WeakReference<World> getBukkitWorldReference() {
        return bukkitWorldReference;
    }

    public void markAsUnloaded() {
        this.unloadedByLoader = true;
    }

    /**
     * @return a collection of players in the world
     * @throws IllegalStateException if the world is disposed by the garbage collector or handle expired.
     */
    @Override
    public @NotNull List<Player> getPlayers() {
        if (isUnloadedByLoader()) {
            throw new IllegalStateException("BukkitWorld (" + getWorldName() + ") has been unloaded by the loader. WorldHandle expired.");
        }
        return getBukkitWorldOrElseThrow().getPlayers();
    }

    @Override
    public String toString() {
        return "BukkitWorldHandle{" +
                "worldName='" + worldName + '\'' +
                ", unloadedByLoader=" + unloadedByLoader +
                '}';
    }
}
