package de.kalypzo.realms.command;

import de.kalypzo.realms.RealmPlugin;
import org.bukkit.command.CommandSender;
import org.incendo.cloud.annotations.AnnotationParser;
import org.incendo.cloud.bukkit.CloudBukkitCapabilities;
import org.incendo.cloud.exception.handling.ExceptionController;
import org.incendo.cloud.execution.ExecutionCoordinator;
import org.incendo.cloud.paper.LegacyPaperCommandManager;
import org.incendo.cloud.parser.ParserRegistry;
import org.slf4j.Logger;

public class CommandManager {
    private final RealmPlugin plugin;
    protected final LegacyPaperCommandManager<CommandSender> commandManager;

    public CommandManager(RealmPlugin plugin) {
        this.plugin = plugin;
        commandManager = LegacyPaperCommandManager.createNative(plugin, ExecutionCoordinator.asyncCoordinator());
        if (commandManager.hasCapability(CloudBukkitCapabilities.BRIGADIER)) {
            commandManager.registerBrigadier();
        } else {
            getLogger().warn("Brigadier is not supported on this server version.");
        }
        registerParser();
        registerExceptionController();
        registerCommands();
    }


    protected void registerParser() {
        ParserRegistry<CommandSender> parserRegistry = commandManager.parserRegistry();
    }

    protected void registerExceptionController() {
        ExceptionController<CommandSender> exceptionController = commandManager.exceptionController();
    }

    protected void registerCommands() {
        AnnotationParser<CommandSender> annotationParser = new AnnotationParser<>(commandManager, CommandSender.class);
    }

    public RealmPlugin getPlugin() {
        return plugin;
    }

    public Logger getLogger() {
        return plugin.getSLF4JLogger();
    }

}
