package de.kalypzo.realms.world;

import de.kalypzo.realms.loader.WorldLoader;

/**
 * Factory for creating world observers
 *
 * @see WorldObserver
 * @see WorldHandle
 * @see WorldLoader
 */
public interface WorldObserverFactory {

    WorldObserver createWorldObserver(WorldHandle worldHandle);

}
