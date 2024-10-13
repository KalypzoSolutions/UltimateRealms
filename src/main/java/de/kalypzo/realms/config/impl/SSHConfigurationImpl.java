package de.kalypzo.realms.config.impl;

import de.kalypzo.realms.config.SSHConfiguration;
import lombok.Getter;
import lombok.Setter;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;
import org.spongepowered.configurate.objectmapping.meta.Setting;

@ConfigSerializable
@Getter
@Setter

public class SSHConfigurationImpl implements SSHConfiguration {
    @Setting("host")
    private String host = "localhost";

    @Setting("port")
    private int port = 22;

    @Setting(value = "root")
    private String username = "root";

    @Setting(value = "password")
    @Comment("If the key file path is set, the password will be ignored")
    private String password = "password";

    @Comment("E.g. /home/user/.ssh/id_rsa")
    @Setting(value = "keyFilePath")
    private String keyFilePathString = "";
}
