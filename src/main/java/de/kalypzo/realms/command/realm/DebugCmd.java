package de.kalypzo.realms.command.realm;

import de.kalypzo.realms.command.CommandManager;
import org.incendo.cloud.annotations.Command;

public class DebugCmd {

    private final CommandManager commandManager;

    public DebugCmd(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    @Command("realms|realm debug")
    public void debug() {

    }

}
