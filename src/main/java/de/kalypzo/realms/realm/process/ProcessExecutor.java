package de.kalypzo.realms.realm.process;

import de.kalypzo.realms.RealmPlugin;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitScheduler;

/**
 * Executes Realm Processes
 */
@Getter
public class ProcessExecutor {

    private final RealmPlugin plugin;

    public ProcessExecutor(RealmPlugin plugin) {
        this.plugin = plugin;
    }

    public void execute(RealmProcess<?> process) {
        if (process.needsExecutedSynchronously()) {
            executeSynchronously(process, new ExecutionContext(this));
        } else {
            executeAsynchronously(process, new ExecutionContext(this));
        }
    }

    public void executeSynchronously(RealmProcess<?> process, ExecutionContext context) {
        getBukkitScheduler().runTask(getPlugin(), () -> process.run(context));
    }

    public void executeAsynchronously(RealmProcess<?> process, ExecutionContext context) {
        getBukkitScheduler().runTaskAsynchronously(getPlugin(), () -> process.run(context));
    }

    public BukkitScheduler getBukkitScheduler() {
        return Bukkit.getScheduler();
    }


}
