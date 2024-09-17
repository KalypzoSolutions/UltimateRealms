package de.kalypzo.realms.config;

public interface WorldFileStorageConfiguration {

    StorageType getStorageType();

    SSHStorageConfiguration getSSHStorageConfiguration();

    interface SSHStorageConfiguration {

    }

    enum StorageType {
        LOCAL,
        SSH
    }
}
