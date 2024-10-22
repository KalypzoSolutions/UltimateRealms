package de.kalypzo.realms.world;

import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;

public class DefaultBukkitWorldCreatorProvider implements BukkitWorldCreatorProvider {

    private static final WorldCreator VOID_WORLD_CREATOR = WorldCreator.name("world_creator")
            .environment(World.Environment.NORMAL)
            .type(WorldType.FLAT)
            .generatorSettings("minecraft:air;minecraft:the_void");


    @Override
    public WorldCreator getWorldCreator(String worldName) {
        return VOID_WORLD_CREATOR;
    }
}
