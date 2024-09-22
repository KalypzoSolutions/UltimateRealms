package de.kalypzo.realms.command.realm;

import de.kalypzo.realms.command.CommandManager;
import de.kalypzo.realms.command.RealmCommand;
import org.bukkit.command.CommandSender;
import org.incendo.cloud.annotations.Command;
import org.incendo.cloud.annotations.CommandDescription;
import org.incendo.cloud.annotations.Permission;

public class CreateRealmCmd extends RealmCommand {

    public CreateRealmCmd(CommandManager commandManager) {
        super(commandManager);
    }

    @Command("realm|realms create|auto|new")
    @CommandDescription("Create a new realm")
    @Permission("realms.create")
    public void createRealm(CommandSender commandSender) {

    }

}
