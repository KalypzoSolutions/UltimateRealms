package de.kalypzo.realms.world;

import de.kalypzo.realms.loader.WorldLoader;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.function.Consumer;

/**
 * Represents a world handle so different world formats can be supported in the future.
 * When the world is being unloaded the handle loses its reference to the world.
 * <p>Can be obtained by a {@link WorldLoader}</p>
 */
public interface WorldHandle {

    String getWorldName();


    /**
     * @param observer the observer to subscribe
     */
    void subscribe(WorldObserver observer);

    /**
     * @param observer the observer to unsubscribe
     */
    void unsubscribe(WorldObserver observer);

    /**
     * Notifies all observers.
     *
     * @param consumer the consumer to notify
     */
    void notifyObservers(Consumer<WorldObserver> consumer);

    /**
     * Saves the world.
     *
     * @return true if the world was saved successfully, false otherwise.
     */
    boolean save();

    /**
     * Kicks all players from the world.
     * Unloads the world from the server.
     * Saves the world.
     *
     * @return true if the world was unloaded successfully, false otherwise.
     */
    boolean unload();

    /**
     * @return a collection of players in the world
     */
    List<Player> getPlayers();

    /**
     * @return the world loader that loaded this world
     */
    WorldLoader getWorldLoader();

    /**
     * @return the spawn location of the world
     */
    Location getSpawnLocation();

    /**
     * @return the world's name and some additional information
     */
    String toString();

}
