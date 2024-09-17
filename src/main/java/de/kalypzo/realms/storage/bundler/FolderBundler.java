package de.kalypzo.realms.storage.bundler;

import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;

/**
 * Interface for bundling and extracting folders.
 * This can be used for compressing folder contents into a single file
 * and extracting the contents back to a folder.
 */
public interface FolderBundler {

    /**
     * Bundles the contents of a specified folder into a single file.
     *
     * @param folder               the folder to bundle
     * @param outputFileNamePrefix the prefix for the output file name without extension
     * @return the path to the bundled file
     */
    Path bundleFolder(@NotNull Path folder, String outputFileNamePrefix) throws BundleException;

    /**
     * Extracts the contents of a bundled file to a specified folder.
     *
     * @param file the bundled file to extract
     * @return the path to the extracted folder
     */
    Path extractBundle(@NotNull Path file) throws BundleException;

}
