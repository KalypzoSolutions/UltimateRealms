package de.kalypzo.realms.storage.bundler;

import lombok.Getter;

import java.nio.file.Path;

/**
 * Exception thrown when an error occurs during bundling or extracting a folder.
 */
@Getter
public class BundleException extends Exception {
    private final Action failedAction;
    private final Path filePath;

    public BundleException(Action failedAction, Path filePath) {
        super("An error occurred during " + failedAction.toString().toLowerCase() + " " + filePath);
        this.failedAction = failedAction;
        this.filePath = filePath;
    }

    public enum Action {
        BUNDLING,
        EXTRACTING
    }

}
