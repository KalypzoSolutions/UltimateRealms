package de.kalypzo.realms.config.impl;

import de.kalypzo.realms.RealmPluginMode;
import de.kalypzo.realms.config.RealmPluginConfiguration;
import lombok.Getter;
import lombok.Setter;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Setting;

/**
 * the config.yml
 */
@Getter
@ConfigSerializable
@Setter
public class RealmPluginConfigurationImpl implements RealmPluginConfiguration {


    @Setting("clear_temp")
    private boolean clearTemp;

    @Setting("plugin_mode")
    private RealmPluginMode realmPluginMode;

    @Setting("mongo")
    private MongoConfigurationImpl mongoConfiguration;

    @Setting("ssh")
    private SSHConfigurationImpl sshConfiguration;


    @Setting("worlds")
    private WorldFileConfigurationImpl worldFileConfiguration;


}
