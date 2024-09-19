package de.kalypzo.realms.realm.process;

/**
 * Represents an operation that can be cancelled.
 */
public interface Cancellable {

    /**
     * Checks if the operation has been cancelled.
     *
     * @return true if the operation is cancelled, false otherwise.
     */
    boolean isCancelled();

    /**
     * Cancels the operation.
     *
     * @return true if the operation was successfully cancelled, false otherwise.
     */
    boolean cancel();

    /**
     * Checks if the operation can be cancelled.
     *
     * @return true if the operation can be cancelled, false otherwise.
     */
    boolean canCancel();


}
