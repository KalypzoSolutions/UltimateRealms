package de.kalypzo.realms.storage;

import de.kalypzo.realms.config.WorldFileStorageConfiguration;
import lombok.Getter;
import lombok.NonNull;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Does not bundle the world files and stores them in a local folder on the system based on config.
 */
@Getter
//TODO introduce local bundling
public class LocalWorldFileStorage implements WorldFileStorage {
    private final WorldFileStorageConfiguration worldFileStorageConfiguration;
    private final Path localFolder;

    /**
     * @param worldFileStorageConfiguration the configuration for the storage of world files
     */
    public LocalWorldFileStorage(WorldFileStorageConfiguration worldFileStorageConfiguration) {
        this.worldFileStorageConfiguration = worldFileStorageConfiguration;
        localFolder = Path.of(worldFileStorageConfiguration.getRemoteFolder());
    }


    @Override
    public List<String> getFiles() {
        File folder = new File(worldFileStorageConfiguration.getRemoteFolder());
        if (!folder.exists()) {
            throw new WorldStorageException("local folder does not exist: " + folder.getAbsolutePath());
        }
        if (!folder.isDirectory()) {
            throw new WorldStorageException("local folder is not a directory: " + folder.getAbsolutePath());
        }
        String[] files = folder.list();
        if (files == null) {
            throw new WorldStorageException("Could not list files in remote folder: " + folder.getAbsolutePath());
        }
        return List.of(files);
    }

    @Override
    public Path loadFile(@NotNull @NonNull String remoteFileNameWithoutExtension, @NotNull @NonNull Path destinationFolder) {

        Path localFile = localFolder.resolve(remoteFileNameWithoutExtension);
        try {
            if (!Files.exists(localFile)) {
                throw new WorldStorageException("Remote file does not exist: " + localFile);
            }
            if (!Files.isDirectory(localFile)) {
                FileUtils.copyFile(localFile.toFile(), destinationFolder.resolve(remoteFileNameWithoutExtension).toFile());
            } else {
                if (Files.exists(destinationFolder)) {
                    throw new WorldStorageException("Destination folder already exists: " + destinationFolder);
                }
                FileUtils.copyDirectoryToDirectory(localFile.toFile(), destinationFolder.toFile());
            }
        } catch (IOException e) {
            throw new WorldStorageException("Could not copy directory to destination", e);
        }
        return destinationFolder.resolve(remoteFileNameWithoutExtension);
    }

    @Override
    public void saveFile(@NotNull @NonNull Path worldPath) {
        String remoteFolder = worldFileStorageConfiguration.getRemoteFolder();
        if (!remoteFolder.endsWith("/")) {
            remoteFolder += "/";
        }
        if (!Files.exists(worldPath)) {
            throw new WorldStorageException("World path does not exist: " + worldPath);
        }
        if (!Files.isDirectory(worldPath)) {
            throw new WorldStorageException("World path is not a directory: " + worldPath);
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
