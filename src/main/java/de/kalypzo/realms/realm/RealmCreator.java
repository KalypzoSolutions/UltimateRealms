package de.kalypzo.realms.realm;

import de.kalypzo.realms.RealmPlugin;
import de.kalypzo.realms.player.RealmPlayer;
import de.kalypzo.realms.util.ExecutionUtil;

import java.util.concurrent.CompletableFuture;

public class RealmCreator {
    private final RealmPlayer owner;
    private final RealmPlugin plugin;

    public RealmCreator(RealmCreationContext context, RealmPlugin plugin) {
        this.owner = context.getRealmOwner();
        this.plugin = plugin;
    }


    public CompletableFuture<ActiveRealmWorld> create() {

        return ExecutionUtil.supplyAsync(() -> {
            return null; //TODO
        });
    }

}
