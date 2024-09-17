package de.kalypzo.realms.loader;

import de.kalypzo.realms.realm.world.WorldHandle;

import java.nio.file.Path;

/**
 * Can load a file into a world
 */
public interface WorldLoader {
    /**
     * @param fileName the name of the world folder to load.
     * @return the loaded world handle
     */
    WorldHandle loadWorld(String fileName);
    void unloadWorld(WorldHandle worldHandle);
}
