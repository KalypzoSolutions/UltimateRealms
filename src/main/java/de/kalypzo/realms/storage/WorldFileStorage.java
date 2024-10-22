package de.kalypzo.realms.storage;

import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;
import java.util.List;

/**
 * Represents a storage for realm-worlds and templates
 */
public interface WorldFileStorage {


    /**
     * @return a list of all files in the storage
     */
    List<String> getFiles();


    /**
     * @param remoteFileNameWithoutExtension the name of the file to load the world from
     * @return path to the worlds-container-folder
     * @throws WorldStorageException if the file could not be loaded
     */
    Path loadFile(@NotNull @NonNull String remoteFileNameWithoutExtension, @NotNull @NonNull Path destinationFolder);

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
