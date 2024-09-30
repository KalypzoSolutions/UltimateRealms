package de.kalypzo.realms.realm.process;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExecutionContext {
    /**
     * The executor that is executing the process.
     */
    private final ProcessExecutor executor;
}
