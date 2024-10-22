package de.kalypzo.realms.config.impl;

import de.kalypzo.realms.config.WorldFileStorageConfiguration;
import lombok.Getter;
import lombok.Setter;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;
import org.spongepowered.configurate.objectmapping.meta.Setting;

@Getter
@Setter
@ConfigSerializable
public class WorldFileConfigurationImpl implements WorldFileStorageConfiguration {

    @Setting("storage_type")
    private StorageType storageType = StorageType.LOCAL;

    @Setting("storage_folder")
    @Comment("The remote folder where the world files are stored")
    private String remoteFolder = "./realm_worlds/";
}
