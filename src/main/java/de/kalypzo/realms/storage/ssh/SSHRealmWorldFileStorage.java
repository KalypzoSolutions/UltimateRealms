package de.kalypzo.realms.storage.ssh;

import de.kalypzo.realms.config.WorldFileStorageConfiguration;
import de.kalypzo.realms.storage.RealmWorldFileStorage;
import de.kalypzo.realms.storage.WorldStorageException;
import de.kalypzo.realms.storage.bundler.FolderBundler;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.sftp.SFTPClient;
import net.schmizz.sshj.xfer.FileSystemFile;
import net.schmizz.sshj.xfer.scp.SCPFileTransfer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class SSHRealmWorldFileStorage implements RealmWorldFileStorage {
    private static final Logger LOGGER = LoggerFactory.getLogger(SSHRealmWorldFileStorage.class);
    private final SSHClient sshClient;
    private final WorldFileStorageConfiguration worldFileStorageConfiguration;
    private final FolderBundler folderBundler;

    public SSHRealmWorldFileStorage(SSHClient sshClient,
                                    WorldFileStorageConfiguration worldFileStorageConfiguration,
                                    FolderBundler folderBundler) {
        this.sshClient = sshClient;
        this.worldFileStorageConfiguration = worldFileStorageConfiguration;
        this.folderBundler = folderBundler;
        checkConnection();
        checkRemoteFolderStructure();
    }

    @Override
    public Path loadFile(String remoteFileNameWithoutExtension, Path destinationFolder) {
        SCPFileTransfer fileTransfer = sshClient.newSCPFileTransfer();
        try {
            String fileName = remoteFileNameWithoutExtension + folderBundler.getFileNameExtension();
            String remotePath = new File(worldFileStorageConfiguration.getRemoteFolder(), fileName).toString();
            LOGGER.info("Downloading world via SSH from {} to {}", fileName, destinationFolder);
            fileTransfer.download(remotePath, destinationFolder.toAbsolutePath().toString());
            Path bundledFile = destinationFolder.resolve(fileName);
            return folderBundler.extractBundle(bundledFile);
        } catch (Exception e) {
            throw new WorldStorageException("TODO: " + e.getMessage(), e);
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
    public void saveFile(Path worldPath) {
        SCPFileTransfer fileTransfer = sshClient.newSCPFileTransfer();
        try {
            FileSystemFile destination = new FileSystemFile(worldFileStorageConfiguration.getRemoteFolder());
            LOGGER.info("Uploading world via SSH from {} to {}", worldPath, destination);
            Path bundledFile = folderBundler.bundleFolder(worldPath, worldPath.getFileName().toString());
            LOGGER.info("Bundled world {} to {}", worldPath, bundledFile);
            fileTransfer.upload(bundledFile.toAbsolutePath().toString(), new File(worldFileStorageConfiguration.getRemoteFolder(), bundledFile.getFileName().toString()).toString());
            ;
            ;
        } catch (Exception e) {
            throw new WorldStorageException("TODO: Implement exception message");
        }
    }


}
