package de.kalypzo.realms.realm.process;

import lombok.Getter;
import org.jetbrains.annotations.Range;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;


public abstract class AbstractRealmProcess<T> implements RealmProcess<T> {
    protected final List<RealmProcessObserver> observers = new LinkedList<>();
    @Getter
    protected final CompletableFuture<Optional<T>> future = new CompletableFuture<>();
    @Getter
    @Range(from = 0, to = 1)
    protected float progress = 0;
    private final boolean needsExecutionSync;

    protected AbstractRealmProcess() {
        this(false);
    }

    protected AbstractRealmProcess(boolean needsExecutionSync) {
        this.needsExecutionSync = needsExecutionSync;
    }


    @Override
    public void unsubscribe(RealmProcessObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void subscribe(RealmProcessObserver observer) {
        observers.add(observer);
    }

    public void setProgress(float progress) {
        this.progress = progress;
        observers.forEach(RealmProcessObserver::onProgressChange);
    }

    @Override
    public boolean needsExecutedSynchronously() {
        return needsExecutionSync;
    }


}
