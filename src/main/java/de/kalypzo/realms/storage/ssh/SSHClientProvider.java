package de.kalypzo.realms.storage.ssh;

import net.schmizz.sshj.SSHClient;

public interface SSHClientProvider {

    SSHClient getSSHClient();

}
