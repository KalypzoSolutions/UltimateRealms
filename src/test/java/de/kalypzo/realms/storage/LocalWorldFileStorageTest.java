package de.kalypzo.realms.storage;

import de.kalypzo.realms.config.WorldFileStorageConfiguration;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class LocalWorldFileStorageTest {

    private static LocalWorldFileStorage localWorldFileStorage;

    @BeforeAll
    public static void setUp() throws IOException {
        localWorldFileStorage = new LocalWorldFileStorage(new WorldFileStorageConfiguration() {
            @Override
            public StorageType getStorageType() {
                return StorageType.LOCAL;
            }

            @Override
            public String getRemoteFolder() {
                return "./run/test";
            }
        });
        Files.createDirectories(localWorldFileStorage.getLocalFolder());

    }


    @Test
    public void testGetEmptyFiles() throws IOException {
        FileUtils.cleanDirectory(localWorldFileStorage.getLocalFolder().toFile());
        List<String> files = localWorldFileStorage.getFiles();
        assertNotNull(files);
        assertEquals(0, files.size());
    }

    @Test
    public void testGetFiles() throws IOException {
        FileUtils.cleanDirectory(localWorldFileStorage.getLocalFolder().toFile());
        Files.createFile(localWorldFileStorage.getLocalFolder().resolve("test.txt"));
        List<String> files = localWorldFileStorage.getFiles();
        assertNotNull(files);
        assertEquals(1, files.size());
        assertEquals("test.txt", files.get(0));
    }


    @Test
    public void testLoadFile() throws IOException {
        FileUtils.cleanDirectory(localWorldFileStorage.getLocalFolder().toFile());
        Files.createFile(localWorldFileStorage.getLocalFolder().resolve("test.txt"));
        Path destination = Files.createTempDirectory("test");
        localWorldFileStorage.loadFile("test.txt", destination);
        assertEquals(1, Files.list(destination).count());
        

    }


}
