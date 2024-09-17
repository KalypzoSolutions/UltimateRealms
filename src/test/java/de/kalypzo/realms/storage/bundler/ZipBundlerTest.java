package de.kalypzo.realms.storage.bundler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class ZipBundlerTest {

    private ZipBundler zipBundler;
    private Path bundleMeDir;

    @TempDir
    Path tempDir;

    @BeforeEach
    void setUp() throws IOException {
        zipBundler = new ZipBundler(tempDir);
        bundleMeDir = copyFolderToTemp(new File(".idea").toPath());
    }

    private Path copyFolderToTemp(Path path) throws IOException {
        Path tempPath = tempDir.resolve(path.getFileName());
        Files.walk(path)
                .forEach(source -> {
                    Path destination = tempPath.resolve(path.relativize(source));
                    try {

                        Files.copy(source, destination);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
        return tempPath;
    }

    @Test
    void testBundleFolder() throws Exception {
        Path bundledFile = zipBundler.bundleFolder(bundleMeDir, "testBundle");
        assertTrue(Files.exists(bundledFile));
        assertTrue(bundledFile.toString().endsWith(".zip"));
        assertTrue(Files.size(bundledFile) > 0);
    }

    @Test
    void testExtractBundle() throws Exception {
        Path bundledFile = zipBundler.bundleFolder(bundleMeDir, "testExtract");
        Path extractedDir = zipBundler.extractBundle(bundledFile);

        assertTrue(Files.exists(extractedDir));
        assertTrue(Files.isDirectory(extractedDir));

        List<Path> originalFiles = Files.walk(bundleMeDir)
                .filter(Files::isRegularFile)
                .collect(Collectors.toList());

        List<Path> extractedFiles = Files.walk(extractedDir)
                .filter(Files::isRegularFile)
                .collect(Collectors.toList());

        assertEquals(originalFiles.size(), extractedFiles.size());

        for (Path originalFile : originalFiles) {
            Path relativePath = bundleMeDir.relativize(originalFile);
            Path extractedFile = extractedDir.resolve(relativePath);
            assertTrue(Files.exists(extractedFile));
            assertEquals(Files.size(originalFile), Files.size(extractedFile));
        }
    }

    @Test
    void testBundleFolderWithNullFolder() {
        assertThrows(NullPointerException.class, () -> zipBundler.bundleFolder(null, "testNull"));
    }

    @Test
    void testExtractBundleWithNullZipFile() {
        assertThrows(NullPointerException.class, () -> zipBundler.extractBundle(null));
    }

    @Test
    void testBundleFolderWithNonExistentFolder() {
        Path nonExistentFolder = tempDir.resolve("nonExistent");
        assertThrows(BundleException.class, () -> zipBundler.bundleFolder(nonExistentFolder, "testNonExistent"));
    }

    @Test
    void testExtractBundleWithNonExistentZipFile() {
        Path nonExistentZip = tempDir.resolve("nonExistent.zip");
        assertThrows(BundleException.class, () -> zipBundler.extractBundle(nonExistentZip));
    }

    @Test
    void testBundleAndExtractEmptyFolder() throws Exception {
        Path emptyFolder = Files.createDirectory(tempDir.resolve("emptyFolder"));
        Path bundledFile = zipBundler.bundleFolder(emptyFolder, "testEmpty");
        Path extractedDir = zipBundler.extractBundle(bundledFile);

        assertTrue(Files.exists(extractedDir));
        assertTrue(Files.isDirectory(extractedDir));
        assertEquals(0, Files.list(extractedDir).count());
    }
}