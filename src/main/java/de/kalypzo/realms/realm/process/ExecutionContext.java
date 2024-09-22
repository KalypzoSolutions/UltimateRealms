package de.kalypzo.realms.realm.process;

import lombok.Getter;

public class ExecutionContext {
    @Getter
    private final ProcessExecutor executor;

    public ExecutionContext(ProcessExecutor executor) {
        this.executor = executor;
    }
}
