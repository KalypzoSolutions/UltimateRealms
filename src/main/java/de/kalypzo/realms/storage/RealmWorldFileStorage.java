package de.kalypzo.realms.storage;

import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;

/**
 * Represents a storage for realm-worlds
 */
public interface RealmWorldFileStorage {


    /**
     * @param remoteFileName the name of the file to load the world from
     * @return path to the world-folder
     * @throws WorldStorageException if the file could not be loaded
     */
    Path loadFile(@NotNull @NonNull String remoteFileName, @NotNull @NonNull Path destinationFolder);

    /**
     * @param worldPath world path to save
     * @throws WorldStorageException if the file could not be saved
     */
    void saveFile(@NotNull @NonNull Path worldPath);

    /**
     * @param remoteFileName the name of the file to check
     * @return true if the file exists
     */
    boolean isFileExisting(@Nullable String remoteFileName);

}
