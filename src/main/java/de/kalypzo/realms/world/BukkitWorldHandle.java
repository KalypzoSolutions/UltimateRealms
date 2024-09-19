package de.kalypzo.realms.world;

import com.google.common.collect.ImmutableList;
import de.kalypzo.realms.loader.BukkitWorldLoader;
import lombok.Getter;
import lombok.NonNull;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

/**
 * @see BukkitWorldLoader
 */
public class BukkitWorldHandle implements WorldHandle {
    private final LinkedList<WorldObserver> observers = new LinkedList<>();
    private final String worldName;
    private final WeakReference<World> bukkitWorldReference;
    private final BukkitWorldLoader worldLoader;
    /**
     * -- GETTER --
     *
     * @return true if the world was unloaded by the loader.
     */
    @Getter
    private boolean unloadedByLoader = false;

    public BukkitWorldHandle(@NotNull @NonNull World world, @NotNull @NonNull BukkitWorldLoader worldLoader) {
        this.bukkitWorldReference = new WeakReference<>(world);
        this.worldName = world.getName();
        this.worldLoader = worldLoader;
    }

    public List<WorldObserver> getObservers() {
        return ImmutableList.copyOf(observers);
    }

    @Override
    public void subscribe(WorldObserver observer) {
        observers.add(observer);
    }

    @Override
    public void unsubscribe(WorldObserver observer) {
        observer.onObserverUnsubscribed();
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(Consumer<WorldObserver> consumer) {
        observers.forEach(consumer);
    }

    @Override
    public boolean save() {
        getBukkitWorldOrElseThrow().save();
        notifyObservers(WorldObserver::onWorldSave);
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
        return "BukkitWorldHandle{" + "worldName='" + worldName + '\'' + ", unloadedByLoader=" + unloadedByLoader + '}';
    }
}
