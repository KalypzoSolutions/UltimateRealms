package de.kalypzo.realms.realm.world;

import java.util.concurrent.CompletableFuture;

/**
 * Represents a world handle for a realm.
 */
public interface WorldHandle {

    /**
     * Saves the world.
     *
     * @return true if the world was saved successfully, false otherwise.
     */
    CompletableFuture<Boolean> saveAsync();

    /**
     * Kicks all players from the world.
     * Unloads the world from the server.
     * Saves the world.
     *
     * @return true if the world was unloaded successfully, false otherwise.
     */
    CompletableFuture<Boolean> unloadAsync();


}
