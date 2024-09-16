package de.kalypzo.realms.storage.ssh;

import de.kalypzo.realms.realm.world.WorldHandle;
import de.kalypzo.realms.storage.RealmWorldFileStorage;
import net.schmizz.sshj.xfer.scp.SCPFileTransfer;

public class SSHRealmWorldFileStorage implements RealmWorldFileStorage {

    private final SSHClientProvider sshClientProvider;

    public SSHRealmWorldFileStorage(SSHClientProvider sshClientProvider) {
        this.sshClientProvider = sshClientProvider;
    }

    @Override
    public WorldHandle loadWorld(String remoteFileName) {
        SCPFileTransfer fileTransfer = sshClientProvider.getSSHClient().newSCPFileTransfer();
        fileTransfer.upload();
    }

    @Override
    public void saveWorld(String fileName, WorldHandle worldHandle) {

    }
}
