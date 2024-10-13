package de.kalypzo.realms.config.impl;

import de.kalypzo.realms.config.MongoConfiguration;
import lombok.Getter;
import lombok.Setter;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;
import org.spongepowered.configurate.objectmapping.meta.Setting;

@Getter
@Setter
@ConfigSerializable
public class MongoConfigurationImpl implements MongoConfiguration {

    @Setting("enabled")
    private boolean useMongo;

    @Setting("connectionUri")
    @Comment("If provided, all other settings will be ignored")
    private String connectionUri;

    @Setting("host")
    private String host;

    @Setting("port")
    private int port;

    @Setting("username")
    private String username;

    @Setting("password")
    private String password;

    @Setting("database")
    private String database;

    @Setting("collectionPrefix")
    private String collectionPrefix;

}
