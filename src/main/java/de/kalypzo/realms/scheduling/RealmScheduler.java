package de.kalypzo.realms.scheduling;

import de.kalypzo.realms.RealmPlugin;
import lombok.Getter;
import org.bukkit.scheduler.BukkitScheduler;

/**
 * Allows scheduling per realm world.
 */
public class RealmScheduler {
    private final RealmPlugin plugin;
    @Getter
    private BukkitScheduler bukkitScheduler;


    public RealmScheduler(RealmPlugin plugin) {
        this.plugin = plugin;
        bukkitScheduler = plugin.getServer().getScheduler();
    }


}
