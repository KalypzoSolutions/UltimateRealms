package de.kalypzo.realms.loader;

import de.kalypzo.realms.realm.ActiveRealmWorld;

import java.util.function.Consumer;


/**
 * Represents a loading process of a realm-world.
 */
public interface RealmProcess {

    /**
     * Post completion action.
     */
    void setOnComplete(Consumer<ActiveRealmWorld> realmWorldConsumer);

    /**
     * Cancels the loading process.
     */
    void cancel();

    /**
     * @return Whether the loading process is completed.
     */
    boolean isCompleted();

    /**
     * @return Progress of the loading process.
     */
    float getProgress();

    public enum LoadingState {
        WAITING_LOAD,
        WAITING_UNLOAD,
        LOADING,
        UNLOADING,
        COMPLETED,
        CREATING,
        DELETING,
    }

}
