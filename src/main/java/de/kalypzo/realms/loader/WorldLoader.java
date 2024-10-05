package de.kalypzo.realms.loader;

import de.kalypzo.realms.generator.GenerationSettings;
import de.kalypzo.realms.generator.RealmGenerator;
import de.kalypzo.realms.world.WorldHandle;
import de.kalypzo.realms.world.WorldObserverFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Can load a file into a world
 */
public interface WorldLoader {

    /**
     * Creates a new world with the given generator and settings.
     *
     * @param generator the generator to use.
     * @param settings  the settings to use.
     * @return the created world handle.
     */
    WorldHandle createWorld(RealmGenerator generator, GenerationSettings settings);

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
