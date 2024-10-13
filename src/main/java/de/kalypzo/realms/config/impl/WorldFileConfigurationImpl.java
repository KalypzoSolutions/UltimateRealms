package de.kalypzo.realms.config.impl;

import de.kalypzo.realms.config.WorldFileStorageConfiguration;
import lombok.Getter;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;
import org.spongepowered.configurate.objectmapping.meta.Setting;

@Getter
@ConfigSerializable
public class WorldFileConfigurationImpl implements WorldFileStorageConfiguration {

    @Setting("storage_type")
    StorageType storageType;

    @Setting("storage_folder")
    @Comment("The remote folder where the world files are stored")
    String remoteFolder;
}
