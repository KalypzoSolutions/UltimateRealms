package de.kalypzo.realms.storage;

import java.nio.file.Path;

/**
 * Represents a storage for realm-worlds
 */
public interface RealmWorldFileStorage {


    /**
     * @param remoteFileName the name of the file to load the world from
     * @return path to the world-folder
     */
    Path loadFile(String remoteFileName, Path destinationFolder);

    /**
     * @param worldPath world path to save
     */
    void saveFile(Path worldPath);


}
