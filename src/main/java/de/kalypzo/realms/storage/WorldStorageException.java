package de.kalypzo.realms.storage;

public class WorldStorageException extends StorageException {
    public WorldStorageException(String message) {
        super(message);
    }

    public WorldStorageException(String message, Exception e) {
        super(message, e);
    }
}
