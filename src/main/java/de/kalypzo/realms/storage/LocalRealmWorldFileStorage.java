package de.kalypzo.realms.storage;

import de.kalypzo.realms.config.WorldFileStorageConfiguration;
import lombok.NonNull;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Does not bundle the world files and stores them in a local folder on the system based on config.
 */
public class LocalRealmWorldFileStorage implements RealmWorldFileStorage {
    private final WorldFileStorageConfiguration worldFileStorageConfiguration;

    /**
     * @param worldFileStorageConfiguration the configuration for the storage of world files
     */
    public LocalRealmWorldFileStorage(WorldFileStorageConfiguration worldFileStorageConfiguration) {
        this.worldFileStorageConfiguration = worldFileStorageConfiguration;
    }


    @Override
    public Path loadFile(@NotNull @NonNull String remoteFileName, @NotNull @NonNull Path destinationFolder) {
        if (Files.exists(destinationFolder)) {
            throw new WorldStorageException("Destination folder already exists: " + destinationFolder);
        }
        Path remote = Path.of(worldFileStorageConfiguration.getRemoteFolder(), remoteFileName);
        try {
            FileUtils.copyDirectoryToDirectory(remote.toFile(), destinationFolder.toFile());
        } catch (IOException e) {
            throw new WorldStorageException("Could not copy directory to destination", e);
        }
        return destinationFolder.resolve(remoteFileName);
    }

    @Override
    public void saveFile(@NotNull @NonNull Path worldPath) {
        String remoteFolder = worldFileStorageConfiguration.getRemoteFolder();
        if (!remoteFolder.endsWith("/")) {
            remoteFolder += "/";
        }
        try {
            FileUtils.copyDirectory(worldPath.toFile(), new File(remoteFolder));
        } catch (IOException e) {
            throw new WorldStorageException("Could not copy directory to remote folder", e);
        }
    }

    @Override
    public boolean isFileExisting(@Nullable String remoteFileName) {
        return Files.exists(Path.of(worldFileStorageConfiguration.getRemoteFolder(), remoteFileName));
    }
}
