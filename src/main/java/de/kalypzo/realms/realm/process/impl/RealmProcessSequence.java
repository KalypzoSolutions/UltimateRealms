package de.kalypzo.realms.realm.process.impl;

import de.kalypzo.realms.realm.process.Cancellable;
import de.kalypzo.realms.realm.process.ExecutionContext;
import de.kalypzo.realms.realm.process.RealmProcess;
import de.kalypzo.realms.realm.process.RealmProcessObserver;
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
    @Getter
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
     *
     * @throws IllegalStateException if the current process is not done yet
     */
    public void goNextProcess() {
        if (index > processes.length) {
            return;
        }
        RealmProcess<?> currentProcess = getCurrentProcess();
        if (currentProcess != null) {
            if (!currentProcess.getFuture().isDone()) {
                throw new IllegalStateException("Current process is not done yet.");
            }
            currentProcess.unsubscribe(this);
        }
        index++;
        if ((currentProcess = getCurrentProcess()) != null) {
            currentProcess.subscribe(this);
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
        var proc = getCurrentProcess();
        if (proc != null) {
            proc.getFuture().whenComplete((result, throwable) -> {
                if (isCancelled()) return;
                if (throwable != null) {
                    future.completeExceptionally(throwable);
                    return;
                }
                if (isLastProcess()) {
                    try {
                        future.complete(Optional.ofNullable((T) result));
                    } catch (ClassCastException ex) {
                        throw new IllegalStateException("Invalid result type in final process", ex);
                    }
                    return;
                }
                if (subProcessCompletionHandler != null) {
                    subProcessCompletionHandler.accept(proc);
                }
                goNextProcess();
                run(executionContext);
            });
            executionContext.getExecutor().execute(proc);
        }
    }


    public @Nullable RealmProcess<?> getCurrentProcess() {
        if (index < 0 || index >= processes.length) {
            return null;
        }
        return processes[index];
    }


    @Override
    public void onProgressChange() {
        if (getCurrentProcess() == null) return;
        float currentProcessProgress = getCurrentProcess().getProgress();
        setProgress(((index + currentProcessProgress) / processes.length));
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
