package de.kalypzo.realms.realm.process;

/**
 * The observer should only exist for the duration of the loading process.
 */
public interface RealmProcessObserver {

    /**
     * Called when the percentage of the process changes.
     */
    void onProgressChange();

    /**
     * Called when the process is completed.
     */
    void onComplete();
}
