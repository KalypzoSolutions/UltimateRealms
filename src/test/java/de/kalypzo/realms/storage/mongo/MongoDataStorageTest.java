package de.kalypzo.realms.storage.mongo;

import de.kalypzo.realms.config.MongoConfiguration;
import de.kalypzo.realms.player.PlayerContainerImpl;
import de.kalypzo.realms.realm.RealmWorldImpl;
import de.kalypzo.realms.realm.flag.FlagContainer;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@EnabledIfEnvironmentVariable(named = "MONGO_TESTS", matches = "true")
public class MongoDataStorageTest {
    private static UUID realmId = UUID.randomUUID();
    private static UUID ownerUuid = UUID.randomUUID();
    private static MongoRealmDataStorage mongoRealmDataStorage;

    @BeforeAll
    public static void setup() {
        mongoRealmDataStorage = new MongoRealmDataStorage(new MongoConfiguration() {
            @Override
            public boolean getUseMongo() {
                return true;
            }

            @Override
            public @Nullable String getConnectionUri() {
                return System.getenv("MONGO_URI");
            }

            @Override
            public String getHost() { // fL0WJ1at3jsiND
                String host = System.getenv("MONGO_HOST");
                return Objects.requireNonNullElse(host, "localhost");
            }

            @Override
            public int getPort() {
                String port = System.getenv("MONGO_PORT");
                return Integer.parseInt(Objects.requireNonNullElse(port, "27017"));
            }

            @Override
            public String getUsername() {
                String username = System.getenv("MONGO_USERNAME");
                return Objects.requireNonNullElse(username, "admin");
            }

            @Override
            public String getPassword() {
                String password = System.getenv("MONGO_PASSWORD");
                return Objects.requireNonNullElse(password, "admin");
            }

            @Override
            public String getDatabase() {
                String database = System.getenv("MONGO_DATABASE");
                return Objects.requireNonNullElse(database, "admin");
            }

            @Override
            public String getCollectionPrefix() {
                return "ultimate_realms_";
            }
        });
        mongoRealmDataStorage.init();
    }


    @Test
    @Order(1)
    public void testRealmSave() {
        var world = new RealmWorldImpl(realmId, ownerUuid, new FlagContainer(), new PlayerContainerImpl(List.of(), null), new PlayerContainerImpl(List.of(), null));
        mongoRealmDataStorage.saveRealm(world);
    }

    @Test
    @Order(2)
    public void load() {
        var world = mongoRealmDataStorage.loadRealmById(realmId);
        Assertions.assertTrue(world.isPresent());
    }

    @Test
    @Order(3)
    public void testUpdate() {
        var world = mongoRealmDataStorage.loadRealmById(realmId);
        Assertions.assertTrue(world.isPresent());
        var realmWorld = world.get();
        var newOwner = UUID.randomUUID();
        realmWorld.setOwnerUuid(newOwner);
        realmWorld.getBannedMembers().addPlayer(UUID.randomUUID());
        mongoRealmDataStorage.saveRealm(realmWorld);
        var updatedWorld = mongoRealmDataStorage.loadRealmById(realmId).get();
        Assertions.assertEquals(updatedWorld.getOwnerUuid(), newOwner);
        Assertions.assertEquals(1, updatedWorld.getBannedMembers().getPlayerUuids().size());


    }


    @AfterAll
    public static void tearDown() {
        mongoRealmDataStorage.shutdown();
        log.info("MongoClient closed");
    }

}
