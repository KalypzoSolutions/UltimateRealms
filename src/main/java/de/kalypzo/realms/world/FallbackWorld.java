package de.kalypzo.realms.world;

import com.google.common.base.Preconditions;
import org.jetbrains.annotations.NotNull;

/**
 * Singleton that holds the world handle for the fallback world.
 */
public class FallbackWorld implements WorldObserver {

    private static FallbackWorld instance;
    private WorldHandle worldHandle;

    private FallbackWorld() {
    }

    public static FallbackWorld getInstance() {
        if (instance == null) {
            instance = new FallbackWorld();
        }
        return instance;
    }

    /**
     * Sets the world handle for the fallback world.
     *
     * @param worldHandle the world handle
     */
    public void setWorldHandle(@NotNull WorldHandle worldHandle) {
        Preconditions.checkNotNull(worldHandle, "worldHandle can not be null");
        if (this.worldHandle != null) {
            this.worldHandle.unsubscribe(this);
        }
        this.worldHandle = worldHandle;
        this.worldHandle.subscribe(this);

    }

    public @NotNull WorldHandle getWorldHandle() {
        if (worldHandle == null) {
            throw new IllegalStateException("WorldHandle has not been set yet.");
        }
        return worldHandle;
    }
}
