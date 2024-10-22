package de.kalypzo.realms.storage;

import de.kalypzo.realms.config.WorldFileStorageConfiguration;
import de.kalypzo.realms.storage.bundler.FolderBundler;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.sftp.FileAttributes;
import net.schmizz.sshj.sftp.RemoteResourceInfo;
import net.schmizz.sshj.sftp.SFTPClient;
import net.schmizz.sshj.xfer.FileSystemFile;
import net.schmizz.sshj.xfer.TransferListener;
import net.schmizz.sshj.xfer.scp.SCPFileTransfer;
import net.schmizz.sshj.xfer.scp.SCPRemoteException;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

@Getter
public class SSHWorldFileStorage implements WorldFileStorage {
    private static final Logger LOGGER = LoggerFactory.getLogger(SSHWorldFileStorage.class);
    private final SSHClient sshClient;
    private final WorldFileStorageConfiguration worldFileStorageConfiguration;
    private final FolderBundler folderBundler;
    private final Path tempFolder;
    @Setter
    private @Nullable TransferListener sshTransferListener;

    public SSHWorldFileStorage(SSHClient sshClient,
                               WorldFileStorageConfiguration worldFileStorageConfiguration,
                               FolderBundler folderBundler, Path tempFolder) {
        this.sshClient = sshClient;
        this.worldFileStorageConfiguration = worldFileStorageConfiguration;
        this.folderBundler = folderBundler;
        this.tempFolder = tempFolder;
        checkConnection();
        checkRemoteFolderStructure();
    }

    /**
     * @return files names only (no directories) in the remote folder
     */
    @Override
    public List<String> getFiles() {
        List<String> files = new LinkedList<>();
        try (SFTPClient sftpClient = sshClient.newSFTPClient()) {
            List<RemoteResourceInfo> infoList = sftpClient.ls(worldFileStorageConfiguration.getRemoteFolder());
            for (RemoteResourceInfo info : infoList) {
                if (info.isRegularFile()) {
                    files.add(info.getName());
                }
            }
        } catch (IOException e) {
            throw new WorldStorageException("Could not discover files", e); //TODO
        }
        return files;
    }

    @Override
    public Path loadFile(@NotNull @NonNull String remoteFileNameWithoutExtension, @NotNull @NonNull Path destinationFolderName) {
        if (Files.exists(destinationFolderName)) {
            throw new WorldStorageException("Destination folder already exists: " + destinationFolderName);
        }
        SCPFileTransfer fileTransfer = sshClient.newSCPFileTransfer();
        if (sshTransferListener != null) {
            fileTransfer.setTransferListener(sshTransferListener);
        }
        try {
            String fileName;
            if (!remoteFileNameWithoutExtension.endsWith(folderBundler.getFileNameExtension())) { // just to be sure
                fileName = remoteFileNameWithoutExtension + folderBundler.getFileNameExtension();
            } else {
                fileName = remoteFileNameWithoutExtension;
            }
            String remotePath = new File(worldFileStorageConfiguration.getRemoteFolder(), fileName).toString();
            LOGGER.info("Downloading world via SSH from {} to {}", fileName, tempFolder);
            fileTransfer.download(remotePath, tempFolder.toAbsolutePath().toString());
            Path bundledFile = tempFolder.resolve(fileName);
            var extracted = folderBundler.extractBundle(bundledFile);
            LOGGER.info("Extracted world {} to {}", bundledFile, extracted);
            FileUtils.copyDirectoryToDirectory(extracted.toFile(), destinationFolderName.toFile());
            LOGGER.info("Copied world {} to {}", extracted, destinationFolderName.toFile());
            return destinationFolderName;
        } catch (SCPRemoteException remoteException) {
            throw new WorldStorageException(remoteException.getRemoteMessage(), remoteException);
        } catch (Exception e) {
            throw new WorldStorageException("Could not load file from storage: " + e.getMessage(), e); //TODO
        }
    }

    public void checkRemoteFolderStructure() {
        try {
            SFTPClient client = sshClient.newSFTPClient();
            client.mkdirs(worldFileStorageConfiguration.getRemoteFolder());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void checkConnection() {
        if (!sshClient.isConnected()) {
            throw new WorldStorageException("SSH connection is not established");
        }
    }


    @Override
    public void saveFile(@NotNull @NonNull Path worldPath) {
        SCPFileTransfer fileTransfer = sshClient.newSCPFileTransfer();
        try {
            FileSystemFile destination = new FileSystemFile(worldFileStorageConfiguration.getRemoteFolder());
            LOGGER.info("Uploading world via SSH from {} to {}", worldPath, destination);
            Path bundledFile = folderBundler.bundleFolder(worldPath, worldPath.getFileName().toString());
            LOGGER.info("Bundled world {} to {}", worldPath, bundledFile);
            fileTransfer.upload(bundledFile.toAbsolutePath().toString(), new File(worldFileStorageConfiguration.getRemoteFolder(), bundledFile.getFileName().toString()).toString());


        } catch (Exception e) {
            throw new WorldStorageException("TODO: Implement exception message");
        }
    }

    @Override
    public boolean isFileExisting(@Nullable String remoteFileName) {
        if (remoteFileName == null || remoteFileName.isEmpty()) {
            return false;
        }
        if (!remoteFileName.endsWith(folderBundler.getFileNameExtension())) {
            remoteFileName += folderBundler.getFileNameExtension();
        }
        try (SFTPClient sftpClient = sshClient.newSFTPClient()) {
            FileAttributes attributes = sftpClient.statExistence(worldFileStorageConfiguration.getRemoteFolder() + remoteFileName);
            return attributes != null;
        } catch (IOException e) {
            throw new WorldStorageException("TODO: Implement exception message", e); //TODO
        }
    }


}
