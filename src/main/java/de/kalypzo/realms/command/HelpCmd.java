package de.kalypzo.realms.command;

import org.bukkit.command.CommandSender;
import org.incendo.cloud.annotations.Argument;
import org.incendo.cloud.annotations.Command;
import org.incendo.cloud.annotations.CommandDescription;
import org.incendo.cloud.minecraft.extras.MinecraftHelp;
import org.jetbrains.annotations.Nullable;

public class HelpCmd extends RealmCommand {

    private final MinecraftHelp<CommandSender> help;

    public HelpCmd(CommandManager commandManager) {
        super(commandManager);
        this.help = MinecraftHelp.createNative("/realm help", commandManager.getCommandManager());
    }

    @Command("realm help [query]")
    @CommandDescription("Displays the help message.")
    public void help(CommandSender sender, @Argument(value = "query") @Nullable String[] query) {
        String parsedQuery = query == null ? "" : String.join(" ", query);
        help.queryCommands(parsedQuery, sender);
    }
}
