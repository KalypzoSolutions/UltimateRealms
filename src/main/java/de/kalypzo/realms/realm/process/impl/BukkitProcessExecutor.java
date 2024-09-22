package de.kalypzo.realms.realm.process.impl;

import de.kalypzo.realms.RealmPlugin;
import de.kalypzo.realms.realm.process.ExecutionContext;
import de.kalypzo.realms.realm.process.ProcessExecutor;
import de.kalypzo.realms.realm.process.RealmProcess;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitScheduler;

/**
 * Executes Realm Processes
 */
@Getter
public class BukkitProcessExecutor implements ProcessExecutor {

    private final RealmPlugin plugin;

    public BukkitProcessExecutor(RealmPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(RealmProcess<?> process) {
        ExecutionContext context = new ExecutionContext(this);
        if (process.needsExecutedSynchronously()) {
            getBukkitScheduler().runTask(getPlugin(), () -> process.run(context));
        } else {
            getBukkitScheduler().runTaskAsynchronously(getPlugin(), () -> process.run(context));

        }
    }

    public BukkitScheduler getBukkitScheduler() {
        return Bukkit.getScheduler();
    }


}
