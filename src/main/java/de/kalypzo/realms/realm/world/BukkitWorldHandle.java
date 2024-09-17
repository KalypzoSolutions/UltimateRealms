package de.kalypzo.realms.realm.world;

import java.util.concurrent.CompletableFuture;

public class BukkitWorldHandle implements WorldHandle{
    @Override
    public CompletableFuture<Boolean> saveAsync() {
        return null;
    }

    @Override
    public CompletableFuture<Boolean> unloadAsync() {
        return null;
    }
}
