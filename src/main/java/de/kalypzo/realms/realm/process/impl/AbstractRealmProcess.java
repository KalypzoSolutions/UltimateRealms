package de.kalypzo.realms.realm.process.impl;

import de.kalypzo.realms.realm.process.RealmProcess;
import de.kalypzo.realms.realm.process.RealmProcessObserver;

import java.util.LinkedList;
import java.util.List;

public abstract class AbstractRealmProcess<T> implements RealmProcess<T> {
    private final List<RealmProcessObserver> observers = new LinkedList<>();
    protected float progress = 0;

    @Override
    public void unsubscribe(RealmProcessObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void subscribe(RealmProcessObserver observer) {
        observers.add(observer);
    }


}
