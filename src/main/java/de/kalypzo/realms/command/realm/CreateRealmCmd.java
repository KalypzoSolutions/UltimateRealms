package de.kalypzo.realms.command.realm;

import de.kalypzo.realms.command.CommandManager;
import de.kalypzo.realms.command.RealmCommand;
import de.kalypzo.realms.player.RealmPlayer;
import de.kalypzo.realms.realm.RealmCreationContext;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.incendo.cloud.annotations.Command;
import org.incendo.cloud.annotations.CommandDescription;
import org.incendo.cloud.annotations.Flag;
import org.incendo.cloud.annotations.Permission;

import java.util.concurrent.CompletableFuture;

public class CreateRealmCmd extends RealmCommand {

    public CreateRealmCmd(CommandManager commandManager) {
        super(commandManager);
    }

    @Command("realm create")
    @CommandDescription("Create a new realm")
    @Permission("realms.create")
    public CompletableFuture<Void> createRealm(CommandSender commandSender, @Flag("templateFile") String templateFileName) {
        RealmPlayer realmPlayer;
        if (commandSender instanceof Player player) {
            realmPlayer = RealmPlayer.of(player.getUniqueId());
        } else {
            realmPlayer = RealmPlayer.SERVER;
        }
        return super.runAsync(() -> {
            super.getRealmApi().getRealmWorldManager().createRealm(new RealmCreationContext(realmPlayer, null));
        });
    }

}
