package de.kalypzo.realms.realm.process;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Range;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

/**
 * <p>A lot of processes</p>
 *
 * @param <T> final result type
 */
public class RealmProcessSequence<T> implements RealmProcess<T>, RealmProcessObserver, Cancellable {
    private boolean cancelled = false;
    private final List<RealmProcessObserver> observers = new LinkedList<>();
    private int index = 0;
    private final RealmProcess<?>[] processes;
    private final float[] progress = {0f};
    @Getter
    private final CompletableFuture<Optional<T>> future = new CompletableFuture<>();
    @Getter
    @Setter
    private Consumer<RealmProcess<?>> subProcessCompletionHandler;


    /**
     * @param processes the processes to run
     * @throws IllegalArgumentException if no processes are provided
     */
    public RealmProcessSequence(RealmProcess<?>... processes) {
        if (processes.length == 0) {
            throw new IllegalArgumentException("No processes provided");
        }
        this.processes = processes;
    }

    /**
     * Selects the next process and subscribes to it
     */
    public void goNextProcess() {
        if (index >= processes.length) {
            return;
        }
        getCurrentProcess().unsubscribe(this);
        index++;
        getCurrentProcess().subscribe(this);
    }

    /**
     * @param process the process to check
     * @return whether the process is the last one
     */
    public boolean isLastProcess(RealmProcess<?> process) {
        return process == processes[processes.length - 1];
    }

    /**
     * @return whether the current process is the last one
     */
    public boolean isLastProcess() {
        return index == processes.length - 1;
    }

    @Override
    public void run(ExecutionContext executionContext) {
        if (isCancelled()) return;
        getCurrentProcess().run(executionContext);
    }

    @Override
    public void subscribe(RealmProcessObserver observer) {
        observers.add(observer);
    }

    @Override
    public void unsubscribe(RealmProcessObserver observer) {
        observers.remove(observer);
    }

    public RealmProcess<?> getCurrentProcess() {
        return processes[index];
    }


    @Override
    public boolean isCompleted() {
        return future.isDone();
    }

    @Override
    public @Range(from = 0, to = 1) float getProgress() {
        return progress[0];
    }


    @Override
    public void onProgressChange() {
        float currentProgress = getCurrentProcess().getProgress();
        this.progress[0] = Float.min(Float.max((index + currentProgress) / processes.length, 0f), 1f);
        for (RealmProcessObserver observer : observers) {
            observer.onProgressChange();
        }
    }


    @Override
    public void onComplete() {
        subProcessCompletionHandler.accept(getCurrentProcess());
        goNextProcess();
    }

    @Override
    public boolean isCancelled() {
        if (cancelled) {
            return true;
        }
        if (getCurrentProcess() instanceof Cancellable cancellable) {
            cancelled = cancellable.isCancelled();
        }
        return cancelled;
    }

    @Override
    public boolean cancel() {
        if (isCancelled()) {
            return false;
        }
        if (getCurrentProcess() instanceof Cancellable cancellable) {
            boolean success = cancellable.cancel();
            if (success) {
                future.complete(Optional.empty());
                cancelled = true;
            }
            return success;
        }
        return false;
    }

    @Override
    public boolean canCancel() {
        if (getCurrentProcess() instanceof Cancellable cancellable) {
            return cancellable.canCancel();
        } else {
            return false;
        }
    }
}
