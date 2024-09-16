package de.kalypzo.realms.loader;

import de.kalypzo.realms.realm.world.WorldHandle;

import java.nio.file.Path;

/**
 * Can load a file into a world
 */
public interface WorldLoader {

    WorldHandle loadWorld(Path worldPath);

    void unloadWorld(WorldHandle worldHandle);
}
