package de.kalypzo.realms.storage.bundler;

import com.google.common.base.Preconditions;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.*;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipBundler implements FolderBundler {

    private final Path tempDir;

    /**
     * @param tempDir the temporary directory to use for bundling
     */
    public ZipBundler(Path tempDir) {
        this.tempDir = tempDir;
    }

    @Override
    public Path bundleFolder(@NotNull Path folder, String outputFileName) throws BundleException {
        Preconditions.checkNotNull(folder, "folder must not be null");
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(Files.newOutputStream(tempDir.resolve(outputFileName + ".zip")));
             Stream<Path> stream = Files.walk(folder)) {
            stream.filter(path -> !Files.isDirectory(path))
                    .forEach(path -> {
                        try {
                            var zipEntry = new ZipEntry(folder.relativize(path).toString());
                            zipOutputStream.putNextEntry(zipEntry);
                            Files.copy(path, zipOutputStream);
                            zipOutputStream.closeEntry();
                        } catch (IOException e) {
                            throw new UncheckedIOException(e);
                        }
                    });
            return tempDir.resolve(outputFileName + ".zip");
        } catch (Exception exception) {
            var ex = new BundleException(BundleException.Action.BUNDLING, folder);
            ex.initCause(exception);
            throw ex;
        }
    }

    @Override
    public Path extractBundle(@NotNull Path zipFile) throws BundleException {
        Preconditions.checkNotNull(zipFile, "zipFile must not be null");
        Path extractDir = tempDir.resolve("extracted_" + System.currentTimeMillis());
        try {
            Files.createDirectories(extractDir);
            try (ZipInputStream zis = new ZipInputStream(Files.newInputStream(zipFile))) {
                ZipEntry zipEntry;
                while ((zipEntry = zis.getNextEntry()) != null) {
                    Path resolvedPath = extractDir.resolve(zipEntry.getName());
                    if (zipEntry.isDirectory()) {
                        Files.createDirectories(resolvedPath);
                    } else {
                        Files.createDirectories(resolvedPath.getParent());
                        Files.copy(zis, resolvedPath, StandardCopyOption.REPLACE_EXISTING);
                    }
                    zis.closeEntry();
                }
            }
            return extractDir;
        } catch (Exception exception) {
            var ex = new BundleException(BundleException.Action.EXTRACTING, zipFile);
            ex.initCause(exception);
            throw ex;
        }
    }
}