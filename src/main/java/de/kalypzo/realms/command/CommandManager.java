package de.kalypzo.realms.command;

import de.kalypzo.realms.RealmPlugin;
import de.kalypzo.realms.command.parser.RealmPlayerParser;
import de.kalypzo.realms.command.realm.RealmCreateCmd;
import de.kalypzo.realms.command.templates.RealmTemplateListCmd;
import de.kalypzo.realms.player.RealmPlayer;
import io.leangen.geantyref.TypeToken;
import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.incendo.cloud.annotations.AnnotationParser;
import org.incendo.cloud.bukkit.CloudBukkitCapabilities;
import org.incendo.cloud.exception.handling.ExceptionController;
import org.incendo.cloud.execution.ExecutionCoordinator;
import org.incendo.cloud.paper.LegacyPaperCommandManager;
import org.incendo.cloud.parser.ParserRegistry;
import org.slf4j.Logger;

@Getter
public class CommandManager {
    private final RealmPlugin plugin;
    protected final LegacyPaperCommandManager<CommandSender> commandManager;

    public CommandManager(RealmPlugin plugin) {
        this.plugin = plugin;
        commandManager = LegacyPaperCommandManager.createNative(plugin,
                ExecutionCoordinator.coordinatorFor(ExecutionCoordinator.nonSchedulingExecutor()));
        if (commandManager.hasCapability(CloudBukkitCapabilities.BRIGADIER)) {
            commandManager.registerBrigadier();
            getLogger().info("Brigadier support enabled.");
        } else {
            getLogger().warn("Brigadier is not supported on this server version.");
            if (commandManager.hasCapability(CloudBukkitCapabilities.ASYNCHRONOUS_COMPLETION)) {
                commandManager.registerAsynchronousCompletions();
                getLogger().info("Asynchronous tab completions enabled.");
            }
        }


        //commandManager.captionRegistry().registerProvider(new RealmCaptionProvider());
        registerParser();
        registerExceptionController();
        registerCommands();
    }


    protected void registerParser() {
        ParserRegistry<CommandSender> parserRegistry = commandManager.parserRegistry();
        parserRegistry.registerParserSupplier(TypeToken.get(RealmPlayer.class), (parameters) -> new RealmPlayerParser<>());

    }

    protected void registerExceptionController() {
        ExceptionController<CommandSender> exceptionController = commandManager.exceptionController();
    }

    protected void registerCommands() {
        AnnotationParser<CommandSender> annotationParser = new AnnotationParser<>(commandManager, CommandSender.class);
        annotationParser.parse(
                new RealmCreateCmd(this),
                new RealmTemplateListCmd(),
                new HelpCmd(this)
        );
    }


    public Logger getLogger() {
        return plugin.getSLF4JLogger();
    }

}
