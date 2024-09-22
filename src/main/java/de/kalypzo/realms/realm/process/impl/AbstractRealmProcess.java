package de.kalypzo.realms.realm.process.impl;

import de.kalypzo.realms.realm.process.RealmProcess;
import de.kalypzo.realms.realm.process.RealmProcessObserver;
import lombok.Getter;
import org.jetbrains.annotations.Range;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;


public abstract class AbstractRealmProcess<T> implements RealmProcess<T> {
    protected final List<RealmProcessObserver> observers = new LinkedList<>();
    @Getter
    protected final CompletableFuture<Optional<T>> future = new CompletableFuture<>();
    private final boolean needsExecutionSync;
    @Getter
    @Range(from = 0, to = 1)
    protected float progress = 0;

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
        this.progress = Float.max(0, Float.min(1, progress));
        observers.forEach(RealmProcessObserver::onProgressChange);
    }

    @Override
    public boolean needsExecutedSynchronously() {
        return needsExecutionSync;
    }

    @Override
    public boolean isCompleted() {
        return future.isDone();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractRealmProcess<?> that = (AbstractRealmProcess<?>) o;
        return needsExecutionSync == that.needsExecutionSync && Objects.equals(future, that.future);
    }

    @Override
    public int hashCode() {
        return Objects.hash(future, needsExecutionSync);
    }

    @Override
    public String toString() {
        return "AbstractRealmProcess{" +
                "observers=" + observers +
                ", future=" + future +
                ", progress=" + progress +
                ", needsExecutionSync=" + needsExecutionSync +
                '}';
    }
}
