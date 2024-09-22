package de.kalypzo.realms.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class RealmCommand {
    private CommandManager commandManager;


}
