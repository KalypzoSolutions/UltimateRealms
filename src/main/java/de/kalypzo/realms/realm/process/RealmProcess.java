package de.kalypzo.realms.realm.process;

import org.jetbrains.annotations.Range;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;


/**
 * <p>A Realm process describes an (async) operation that can be observed</p>
 * <p>The implementation might inherit {@link Cancellable}</p>
 *
 * @param <T> The result type of future
 */
public interface RealmProcess<T> {
    void subscribe(RealmProcessObserver observer);

    void unsubscribe(RealmProcessObserver observer);

    /**
     * @return Whether the loading process is completed.
     */
    boolean isCompleted();


    void run(ExecutionContext executionContext);

    /**
     * @return Progress of the loading process as a float between 0 and 1.
     */
    @Range(from = 0, to = 1)
    float getProgress();

    /**
     * @return The future that will be completed when the process is done.
     * <p>
     * The future will contain the result of the process.
     * <p>
     * The Result might be empty if the process was cancelled or failed.
     */
    CompletableFuture<Optional<T>> getFuture();

}
