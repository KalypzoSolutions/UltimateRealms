package de.kalypzo.realms.world.templates;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;

/**
 * Represents a template world with  settings
 */
@Getter
@Setter
@AllArgsConstructor
public class TemplateWorld {

    /**
     * The name of the template.
     */
    private final String templateName;


    /**
     * The spawn location of the world.
     */
    private Location spawnLocation;


    /**
     * The name of the world generator to use for the world.
     */
    private String worldGeneratorName = "VOID";
}
