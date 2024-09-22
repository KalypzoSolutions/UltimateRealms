package de.kalypzo.realms.config;

import org.jetbrains.annotations.Nullable;

public interface MongoConfiguration {

    boolean getUseMongo();

    @Nullable String getConnectionUri();

    String getHost();

    int getPort();

    String getUsername();

    String getPassword();

    String getDatabase();

    String getCollectionPrefix();


}
