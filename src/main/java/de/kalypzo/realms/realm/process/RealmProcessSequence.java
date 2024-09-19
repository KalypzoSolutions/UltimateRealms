package de.kalypzo.realms.realm.process;

import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

import java.util.LinkedList;
import java.util.List;

/**
 * <p>A lot of processes</p>
 */
public class RealmProcessSequence implements RealmProcess, RealmProcess.Observer {
    private final List<RealmProcess.Observer> observers = new LinkedList<>();
    private int index = 0;
    private final RealmProcess[] processes;
    private float progress = 0;

    public RealmProcessSequence(RealmProcess[] processes) {
        if (processes.length == 0) {
            throw new IllegalArgumentException("No processes provided");
        }
        this.processes = processes;
    }

    @Override
    public void subscribe(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void unsubscribe(Observer observer) {
        observers.remove(observer);
    }

    public RealmProcess getCurrentProcess() {
        return processes[index];
    }

    public void nextProcess() {
        getCurrentProcess().unsubscribe(this);
        index++;
        getCurrentProcess().subscribe(this);
    }

    @Override
    public boolean isCompleted() {
        return false;
    }

    @Override
    public @Range(from = 0, to = 1) float getProgress() {
        return progress;
    }

    @Override
    public @Nullable RealmProcess getProcess() {
        return null;
    }

    @Override
    public void onProgressChange() {
        float currentProgress = getCurrentProcess().getProgress();
        this.progress = Float.min(Float.max((index + currentProgress) / processes.length, 0f), 1f);
        for (RealmProcess.Observer observer : observers) {
            observer.onProgressChange();
        }
    }


    @Override
    public void onComplete() {

    }
}
