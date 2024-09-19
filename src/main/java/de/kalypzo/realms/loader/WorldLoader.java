package de.kalypzo.realms.loader;

import de.kalypzo.realms.world.WorldHandle;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Can load a file into a world
 */
public interface WorldLoader {
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
}
