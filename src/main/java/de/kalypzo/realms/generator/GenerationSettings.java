package de.kalypzo.realms.generator;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.WorldCreator;
import org.jetbrains.annotations.Nullable;

/**
 * Settings for the generation of a realm.
 */
@Getter
@Setter
public class GenerationSettings {

    /**
     * The name of the template file to use for generation or null if no template file should be used.
     */
    private @Nullable String templateFileName = null;
    /**
     * An array of schematic file names allows to paste those schematics in the world after it has been generated.
     * <br>
     * The schematics are pasted in the order they are defined in the array.
     */
    private @Nullable String[] schematicFileNames = null;

    /**
     * The world creator that should be used to create the world.
     * <br>
     * If this is not null, the world creator will be used to create the world instead of the default one.
     */
    private @Nullable WorldCreator overwrittenBukkitWorldCreator = null;



}
