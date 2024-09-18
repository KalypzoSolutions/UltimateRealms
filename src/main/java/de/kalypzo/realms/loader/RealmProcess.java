package de.kalypzo.realms.loader;

import de.kalypzo.realms.realm.ActiveRealmWorld;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;


/**
 * <p>A Realm process describes an operation that can be observed</p>
 * <p>The implementation might inherit {@link Cancellable}</p>
 */
public interface RealmProcess {
    void subscribe(Observer observer);

    void unsubscribe(Observer observer);

    /**
     * @return Whether the loading process is completed.
     */
    boolean isCompleted();

    /**
     * @return Progress of the loading process as a float between 0 and 1.
     */
    @Range(from = 0, to = 1)
    float getProgress();


    /**
     * The observer should only exist for the duration of the loading process.
     */
    interface Observer {

        /**
         * @return The process associated with this observer.
         */
        @Nullable RealmProcess getProcess();

        void onProgressChange();

        void onComplete();
    }


}
