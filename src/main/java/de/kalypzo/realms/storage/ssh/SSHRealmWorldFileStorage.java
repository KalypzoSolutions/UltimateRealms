package de.kalypzo.realms.storage.ssh;

import de.kalypzo.realms.config.WorldFileStorageConfiguration;
import de.kalypzo.realms.loader.WorldLoader;
import de.kalypzo.realms.world.WorldHandle;
import de.kalypzo.realms.storage.RealmWorldFileStorage;
import de.kalypzo.realms.storage.bundler.FolderBundler;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.xfer.scp.SCPFileTransfer;

public class SSHRealmWorldFileStorage implements RealmWorldFileStorage {

    private final SSHClient sshClient;
    private final WorldFileStorageConfiguration worldFileStorageConfiguration;
    private final FolderBundler worldBundler;

    public SSHRealmWorldFileStorage(SSHClient sshClient,
                                    WorldFileStorageConfiguration worldFileStorageConfiguration,
                                    FolderBundler folderBundler) {
        this.sshClient = sshClient;
        this.worldFileStorageConfiguration = worldFileStorageConfiguration;
        this.worldBundler = folderBundler;
    }

    @Override
    public WorldHandle loadWorld(WorldLoader worldLoader, String remoteFileName) {
        SCPFileTransfer fileTransfer = sshClient.newSCPFileTransfer();
        return null;
    }

    @Override
    public void saveWorld(String fileName, WorldHandle worldHandle) {

    }
}
