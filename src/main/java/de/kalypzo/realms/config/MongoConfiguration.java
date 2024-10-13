package de.kalypzo.realms.config;

import org.jetbrains.annotations.Nullable;

/**
 * Configuration for the MongoDB connection
 */
public interface MongoConfiguration {

    boolean isUseMongo();

    @Nullable String getConnectionUri();

    String getHost();

    int getPort();

    String getUsername();

    String getPassword();

    String getDatabase();

    String getCollectionPrefix();


}
