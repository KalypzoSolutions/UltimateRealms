package de.kalypzo.realms.storage;

import de.kalypzo.realms.config.WorldFileStorageConfiguration;
import de.kalypzo.realms.storage.bundler.ZipBundler;
import de.kalypzo.realms.storage.ssh.SSHRealmWorldFileStorage;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.sftp.FileAttributes;
import net.schmizz.sshj.sftp.SFTPClient;
import net.schmizz.sshj.transport.verification.HostKeyVerifier;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.PublicKey;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//@EnabledIfEnvironmentVariable(named = "SSH_TESTS", matches = "true", disabledReason = "SSH tests are disabled")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SSHRealmWorldFileStorageTest {

    @TempDir
    Path tempDir;

    private SSHClient sshClient;
    private WorldFileStorageConfiguration config;
    private SSHRealmWorldFileStorage storage;

    private static final String TEST_HOST = "127.0.0.1";
    private static final int TEST_PORT = 22;
    private static final String TEST_USER = "junit";
    private static final String TEST_PASSWORD = "junit";

    @BeforeEach
    void setUp() throws IOException {
        sshClient = createSSHClient();
        config = createTestConfig();
        storage = new SSHRealmWorldFileStorage(sshClient, config, new ZipBundler(tempDir), tempDir);
    }

    @AfterEach
    void tearDown() throws IOException {
        sshClient.disconnect();
    }

    @Test
    @Order(1)
    void testSaveFile() throws Exception {
        Path ideaFolder = Path.of("./.idea/");
        storage.saveFile(ideaFolder);

        try (SFTPClient sftpClient = sshClient.newSFTPClient()) {
            FileAttributes attributes = sftpClient.statExistence(config.getRemoteFolder() + ".idea" + storage.getFolderBundler().getFileNameExtension());
            assertNotNull(attributes, "Saved file should exist on remote server");
        }
    }


    @Test
    void testLoadNonExistent() {
        assertThrows(WorldStorageException.class, () -> storage.loadFile("nonExistent", tempDir));
    }

    @Test
    @Order(2)
    void testExistingFileTrue() {
        boolean exists = storage.isFileExisting(".idea");
        assertTrue(exists);
    }

    @Test
    void testExistingFileFalse() {
        boolean exists = storage.isFileExisting("nonExistent");
        assertFalse(exists);
    }


    @Test
    @Order(2)
    void testLoadFile() {
        Path destination = tempDir.resolve("workspace/my_realm");
        Path loaded = storage.loadFile(".idea", destination);
        assertNotNull(loaded, "Loaded file should not be null");
    }

    @Test
    void testLoadingWithExistingDestinationFolder() throws IOException {
        var existing = tempDir.resolve("existing_realm");
        Files.createDirectories(existing);
        assertThrows(WorldStorageException.class, () -> storage.loadFile(".idea", existing));
    }

    private SSHClient createSSHClient() throws IOException {
        SSHClient client = new SSHClient();
        client.addHostKeyVerifier(createPermissiveHostKeyVerifier());
        client.connect(TEST_HOST, TEST_PORT);
        client.authPassword(TEST_USER, TEST_PASSWORD);
        return client;
    }

    private HostKeyVerifier createPermissiveHostKeyVerifier() {
        return new HostKeyVerifier() {
            @Override
            public boolean verify(String hostname, int port, PublicKey key) {
                return true; // Note: This is not secure and should only be used for testing
            }

            @Override
            public List<String> findExistingAlgorithms(String hostname, int port) {
                return List.of();
            }
        };
    }

    private WorldFileStorageConfiguration createTestConfig() {
        return new WorldFileStorageConfiguration() {
            @Override
            public StorageType getStorageType() {
                return StorageType.SSH;
            }

            @Override
            public String getLocalPath() {
                return ".";
            }

            @Override
            public String getRemoteFolder() {
                return "./remote/folder/";
            }
        };
    }
}