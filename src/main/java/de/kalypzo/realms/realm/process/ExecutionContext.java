package de.kalypzo.realms.realm.process;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class ExecutionContext {
    private final ProcessExecutor executor;
}
