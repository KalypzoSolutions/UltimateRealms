package de.kalypzo.realms.scheduling;

import com.github.Anon8281.universalScheduler.UniversalScheduler;
import com.github.Anon8281.universalScheduler.scheduling.schedulers.TaskScheduler;
import de.kalypzo.realms.RealmPlugin;
import lombok.Getter;

/**
 * Allows scheduling per realm world.
 */
public class RealmScheduler {
    private final RealmPlugin plugin;
    @Getter
    private TaskScheduler scheduler;


    public RealmScheduler(RealmPlugin plugin) {
        this.plugin = plugin;
        scheduler = UniversalScheduler.getScheduler(plugin);
    }


}
