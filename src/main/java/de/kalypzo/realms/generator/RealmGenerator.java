package de.kalypzo.realms.generator;

import de.kalypzo.realms.loader.WorldLoader;
import de.kalypzo.realms.world.WorldHandle;

public interface RealmGenerator {

    /**
     * @param worldLoader the loader to load the generated realm
     * @return the generated realm which has been loaded by the worldLoader
     */
    WorldHandle generateRealm(WorldLoader worldLoader, GenerationSettings settings);

}
