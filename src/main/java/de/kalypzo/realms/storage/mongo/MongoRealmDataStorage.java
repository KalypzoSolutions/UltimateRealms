package de.kalypzo.realms.storage.mongo;

import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import de.kalypzo.realms.config.MongoConfiguration;
import de.kalypzo.realms.player.PlayerContainer;
import de.kalypzo.realms.realm.RealmCreationContext;
import de.kalypzo.realms.realm.RealmWorld;
import de.kalypzo.realms.realm.RealmWorldFactory;
import de.kalypzo.realms.realm.flag.FlagContainer;
import de.kalypzo.realms.storage.RealmDataStorage;
import lombok.Getter;
import lombok.NonNull;
import org.bson.Document;
import org.bson.UuidRepresentation;
import org.jetbrains.annotations.NotNull;

import java.util.*;

@Getter
public class MongoRealmDataStorage implements RealmDataStorage {
    private final MongoConfiguration configuration;
    private final RealmWorldFactory realmWorldFactory;
    private MongoClient mongoClient;
    private MongoDatabase database;

    public MongoRealmDataStorage(@NotNull @NonNull MongoConfiguration configuration, RealmWorldFactory realmWorldFactory) {
        this.configuration = configuration;
        this.realmWorldFactory = realmWorldFactory;
    }

    @Override
    public void init() {
        if (configuration.getConnectionUri() != null) {
            mongoClient = MongoClients.create(MongoClientSettings.builder()
                    .uuidRepresentation(UuidRepresentation.JAVA_LEGACY)
                    .applyConnectionString(new com.mongodb.ConnectionString(configuration.getConnectionUri())).build());
        } else {
            mongoClient = MongoClients.create(MongoClientSettings.builder()
                    .uuidRepresentation(UuidRepresentation.JAVA_LEGACY)
                    .applyToClusterSettings(builder -> {
                        builder.hosts(List.of(new ServerAddress(configuration.getHost(), configuration.getPort())));
                    }).credential(MongoCredential.createCredential(configuration.getUsername(), configuration.getDatabase(), configuration.getPassword().toCharArray())).build());
        }
        database = mongoClient.getDatabase(configuration.getDatabase());
    }

    public String getCollectionPrefix() {
        return configuration.getCollectionPrefix();
    }


    @Override
    public void shutdown() {
        mongoClient.close();
    }


    @Override
    public void saveRealm(RealmWorld realmWorld) {
        var collection = database.getCollection(getCollectionPrefix() + "worlds");
        collection.replaceOne(Filters.eq("_id", realmWorld.getRealmId()), realmWorldToDocument(realmWorld), new ReplaceOptions().upsert(true));
    }

    @Override
    public void deleteRealm(RealmWorld realmWorld) {
        var collection = database.getCollection(getCollectionPrefix() + "worlds");
        collection.deleteOne(Filters.eq("_id", realmWorld.getRealmId()));
    }

    @Override
    public Optional<RealmWorld> loadRealmById(UUID realmId) {
        var collection = database.getCollection(getCollectionPrefix() + "worlds");
        var document = collection.find(Filters.eq("_id", realmId)).first();
        if (document == null) {
            return Optional.empty();
        }
        return Optional.of(documentToRealmWorld(document));
    }

    @Override
    public List<RealmWorld> loadAllRealms() {
        var collection = database.getCollection(getCollectionPrefix() + "worlds");
        var documents = collection.find();
        var realms = new LinkedList<RealmWorld>();
        for (Document document : documents) {
            realms.add(documentToRealmWorld(document));
        }
        return realms;
    }

    public RealmCreationContext loadContext(UUID realmId) {
        throw new UnsupportedOperationException("Not implemented yet"); //TODO
    }

    @Override
    public List<RealmWorld> loadRealmsByOwner(UUID ownerId) {
        var collection = database.getCollection(getCollectionPrefix() + "worlds");
        var documents = collection.find(Filters.eq("owner_uuid", ownerId));
        var realms = new LinkedList<RealmWorld>();
        for (Document document : documents) {
            realms.add(documentToRealmWorld(document));
        }
        return realms;
    }

    public RealmWorld documentToRealmWorld(Document document) {
        UUID realmId = document.get("_id", UUID.class);
        UUID ownerUuid = document.get("owner_uuid", UUID.class);
        List<UUID> trustedMembers = (document.getList("trusted", UUID.class));
        List<UUID> bannedMembers = (document.getList("banned", UUID.class));
        return realmWorldFactory.createRealmWorld(realmId, ownerUuid, trustedMembers, bannedMembers, () -> loadContext(realmId));
    }


    public Document realmWorldToDocument(RealmWorld realmWorld) {
        return new Document()
                .append("_id", realmWorld.getRealmId())
                .append("owner_uuid", realmWorld.getOwnerUuid())
                .append("flags", flagContainerToDocument(realmWorld.getFlags()))
                .append("trusted", playerContainerToDocument(realmWorld.getTrustedMembers())).append("banned", playerContainerToDocument(realmWorld.getBannedMembers()));
    }

    public Collection<Document> flagContainerToDocument(FlagContainer flagContainer) {
        return List.of(); //TODO
    }

    public Collection<UUID> playerContainerToDocument(PlayerContainer playerContainer) {
        return playerContainer.getPlayerUuids();
    }
}
