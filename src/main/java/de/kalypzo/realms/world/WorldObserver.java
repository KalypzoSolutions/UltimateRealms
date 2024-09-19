package de.kalypzo.realms.world;

public interface WorldObserver {

    /**
     * Called before the world is saved
     */
    default void onWorldSave() {
    }

    /**
     * Called when the world is unloaded before kicking all players and saving.
     */
    default void onWorldUnload() {
    }

    /**
     * Observers should dereference any resources they have.
     * Called when the observer is unsubscribed.
     */
    void onObserverUnsubscribed();

}
