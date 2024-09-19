package de.kalypzo.realms.storage;

import de.kalypzo.realms.loader.WorldLoader;
import de.kalypzo.realms.world.WorldHandle;

/**
 * Represents a storage for realm-worlds
 */
public interface RealmWorldFileStorage {

    /**
     * @param remoteFileName the name of the file to load the world from
     * @return the loaded world
     */
    WorldHandle loadWorld(WorldLoader worldLoader, String remoteFileName);

    /**
     * @param fileName    the name of the file to save the world to
     * @param worldHandle the world to save
     */
    void saveWorld(String fileName, WorldHandle worldHandle);


}
