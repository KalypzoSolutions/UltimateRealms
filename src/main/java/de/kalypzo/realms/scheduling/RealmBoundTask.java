package de.kalypzo.realms.scheduling;

import de.kalypzo.realms.realm.ActiveRealmWorld;
import de.kalypzo.realms.world.WorldObserver;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RealmBoundTask implements WorldObserver {

    private static final Logger LOGGER = LoggerFactory.getLogger(RealmBoundTask.class);
    private ActiveRealmWorld world;
    private BukkitTask parent;

    public RealmBoundTask(ActiveRealmWorld world, BukkitTask parent) {
        world.getWorldHandle().subscribe(this);
        this.parent = parent;
        this.world = world;
    }


    public Logger getLogger() {
        return LOGGER;
    }


    @Override
    public String toString() {
        return "Realm-" + world.getRealmId() + "#Task-" + parent.getTaskId();
    }

    int getBukkitTaskId() {
        return parent.getTaskId();
    }

    public int getTaskId() {
        return parent.getTaskId();
    }

    public ActiveRealmWorld getOwningWorld() {
        return world;
    }

    public @NotNull Plugin getOwningPlugin() {
        return parent.getOwner();
    }

    public boolean isSync() {
        return parent.isSync();
    }

    public boolean isCancelled() {
        return parent.isCancelled();
    }

    public void cancel() {
        parent.cancel();
    }

    @Override
    public void onObserverUnsubscribed() {
        if (!isCancelled()) {
            cancel();
        }
        // dereference to prevent memory leaks
        world = null;
        parent = null;
    }
}
