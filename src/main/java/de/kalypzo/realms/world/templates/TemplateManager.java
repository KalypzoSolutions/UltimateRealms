package de.kalypzo.realms.world.templates;

import de.kalypzo.realms.config.impl.TemplateWorldFileStorageConfig;
import de.kalypzo.realms.storage.LocalWorldFileStorage;
import de.kalypzo.realms.storage.WorldFileStorage;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collection;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TemplateManager {
    private final Map<String, TemplateWorld> templateWorldMap = new ConcurrentHashMap<>();
    private final Map<String, TemplateSchematic> templateSchematicMap = new ConcurrentHashMap<>();
    private final LocalWorldFileStorage localTemplateStorage;
    private final WorldFileStorage fileStorage;

    public TemplateManager(WorldFileStorage fileStorage, JavaPlugin plugin) {
        this.localTemplateStorage = new LocalWorldFileStorage(new TemplateWorldFileStorageConfig(plugin));
        this.fileStorage = fileStorage;
    }

    public void loadTemplates() {
        for (String fileName : fileStorage.getFiles()) {
            int lastDotIndex = fileName.lastIndexOf('.');
            String fileNameWithoutExtension = fileName.substring(0, lastDotIndex);
            String extension = fileName.substring(lastDotIndex + 1);

            if ("schem".equals(extension.toUpperCase(Locale.ROOT))) {

            }

        }
    }

    public void loadTemplate(String remoteFileStorageFileName) {

    }

    public Collection<TemplateWorld> getTemplates() {
        return templateWorldMap.values();
    }

    public TemplateWorld getTemplate(String templateName) {
        return templateWorldMap.get(templateName);
    }

}
