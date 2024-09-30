package de.kalypzo.realms.realm.process;

/**
 * Executes a process.
 */
public interface ProcessExecutor {
    void execute(RealmProcess<?> process);
}
