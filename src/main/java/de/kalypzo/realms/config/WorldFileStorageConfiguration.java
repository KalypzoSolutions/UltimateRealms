package de.kalypzo.realms.config;

public interface WorldFileStorageConfiguration {

    StorageType getStorageType();

    String getLocalPath();

    /**
     * @return the remote folder where the world files are stored
     */
    String getRemoteFolder();

    enum StorageType {
        LOCAL,
        SSH
    }
}
