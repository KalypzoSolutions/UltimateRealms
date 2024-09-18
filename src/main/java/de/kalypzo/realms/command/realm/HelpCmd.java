package de.kalypzo.realms.command.realm;

import de.kalypzo.realms.command.CommandManager;
import org.bukkit.command.CommandSender;
import org.incendo.cloud.annotations.Argument;
import org.incendo.cloud.annotations.Command;
import org.incendo.cloud.annotations.CommandDescription;
import org.incendo.cloud.minecraft.extras.MinecraftHelp;
import org.jetbrains.annotations.Nullable;

public class HelpCmd {

    private final CommandManager commandManager;
    private final MinecraftHelp<CommandSender> help;

    public HelpCmd(CommandManager commandManager) {
        this.commandManager = commandManager;
        this.help = MinecraftHelp.createNative("/realms help", commandManager.getCommandManager());
    }

    @Command("realms help [query]")
    @CommandDescription("Displays the help message.")
    public void help(CommandSender sender, @Argument(value = "query") @Nullable String[] query) {
        String parsedQuery = query == null ? "" : String.join(" ", query);
        help.queryCommands(parsedQuery, sender);
    }
}
