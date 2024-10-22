package de.kalypzo.realms.loader;

import de.kalypzo.realms.world.WorldHandle;
import de.kalypzo.realms.world.WorldObserverFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Can load a file into a world
 */
public interface WorldLoader {

    boolean isWorldLoaded(@NotNull String fileName);


    /**
     * @param fileName the name of the world folder to load.
     * @return the loaded world handle or null if the world could not be loaded.
     */
    @Nullable WorldHandle loadWorld(@NotNull String fileName);


    /**
     * @param worldHandle a handle to the world to unload.
     * @return true if the world was unloaded successfully, false otherwise.
     */
    boolean unloadWorld(@NotNull WorldHandle worldHandle);

    /**
     * Adds a factory for creating world observers.
     * <p>
     * All {@link WorldHandle} instances that will be created by this loader will be automatically subscribed.
     *
     * @param factory the factory to add.
     */
    void addWorldObserverFactory(WorldObserverFactory factory);

    /**
     * Removes a factory for creating world observers.
     *
     * @param factory the factory to remove.
     */
    void removeWorldObserverFactory(WorldObserverFactory factory);

}
