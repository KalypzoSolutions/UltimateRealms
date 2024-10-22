package de.kalypzo.realms.command.realm;

import de.kalypzo.realms.command.CommandManager;
import de.kalypzo.realms.command.RealmCommand;
import de.kalypzo.realms.player.RealmPlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.incendo.cloud.annotations.Command;
import org.incendo.cloud.annotations.CommandDescription;
import org.incendo.cloud.annotations.Flag;
import org.incendo.cloud.annotations.Permission;

import java.util.concurrent.CompletableFuture;

public class RealmCreateCmd extends RealmCommand {

    public RealmCreateCmd(CommandManager commandManager) {
        super(commandManager);
    }


    @Command("realm|realms realms create")
    @CommandDescription("Admin command for to create a new realm. Provides more options than the player command.")
    @Permission("realms.realms.admin-create")
    public CompletableFuture<Void> createRealm(CommandSender commandSender, @Flag("templateFile") String flagTemplateFileName, @Flag("owner") RealmPlayer realmPlayer) {
        RealmPlayer realmOwner;
        if (realmPlayer != null) {
            realmOwner = realmPlayer;
        } else if (commandSender instanceof Player player) {
            realmOwner = RealmPlayer.of(player.getUniqueId());
        } else {
            realmOwner = RealmPlayer.SERVER;
        }
        return super.runAsync(() -> {
            commandSender.sendMessage("This command is not yet implemented.");
        });
    }

    @Command("realm|realms auto")
    @CommandDescription("Create a new realm.")
    @Permission("realms.auto")
    public CompletableFuture<Void> createRealm(CommandSender commandSender) {
        RealmPlayer realmPlayer;
        if (commandSender instanceof Player player) {
            realmPlayer = RealmPlayer.of(player.getUniqueId());
        } else {
            realmPlayer = RealmPlayer.SERVER;
        }
        return super.runAsync(() -> {
            commandSender.sendMessage("This command is not yet implemented.");
        });
    }


}
