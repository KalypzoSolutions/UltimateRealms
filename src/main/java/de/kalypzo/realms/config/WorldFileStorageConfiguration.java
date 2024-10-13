package de.kalypzo.realms.config;

/**
 * User configuration for the storage of world files
 */
public interface WorldFileStorageConfiguration {

    StorageType getStorageType();

    /**
     * @return the remote folder where the world files are stored
     */
    String getRemoteFolder();

    enum StorageType {
        LOCAL,
        SSH
    }
}
