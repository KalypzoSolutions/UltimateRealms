package de.kalypzo.realms.world;

import org.bukkit.WorldCreator;

/**
 * Provides a Bukkit {@link org.bukkit.WorldCreator} for a world
 */
public interface BukkitWorldCreatorProvider {

    /**
     * @param worldName the name of the world to get the creator for.
     * @return the world creator for the given world name. The world creator should be used to create or load the world.
     */
    WorldCreator getWorldCreator(String worldName);

}
