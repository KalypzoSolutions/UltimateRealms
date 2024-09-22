package de.kalypzo.realms.realm.process;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * <p>A lot of processes</p>
 *
 * @param <T> final result type
 */
public class RealmProcessSequence<T> extends AbstractRealmProcess<T> implements RealmProcessObserver, Cancellable {
    private boolean cancelled = false;
    private int index = -1;
    private final RealmProcess<?>[] processes;
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
        goNextProcess();
    }

    /**
     * Selects the next process and subscribes to it
     */
    public void goNextProcess() {
        if (index > processes.length) {
            return;
        }
        if (getCurrentProcess() != null) {
            getCurrentProcess().unsubscribe(this);
        }
        index++;
        if (getCurrentProcess() != null) {
            getCurrentProcess().subscribe(this);
        }
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
        if (getCurrentProcess() != null) {
            getCurrentProcess().run(executionContext);
        }
    }


    public @Nullable RealmProcess<?> getCurrentProcess() {
        if (index < 0 || index >= processes.length) {
            return null;
        }
        return processes[index];
    }


    @Override
    public boolean isCompleted() {
        return future.isDone();
    }


    @Override
    public void onProgressChange() {
        if (getCurrentProcess() == null) return;
        float currentProcessProgress = getCurrentProcess().getProgress();
        setProgress(Float.min(Float.max((index + currentProcessProgress) / processes.length, 0f), 1f));
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
                super.future.complete(Optional.empty());
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
