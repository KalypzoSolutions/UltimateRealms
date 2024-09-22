package de.kalypzo.realms.realm.process.impl;

import de.kalypzo.realms.realm.process.AbstractRealmProcess;
import de.kalypzo.realms.realm.process.ExecutionContext;
import org.jetbrains.annotations.Range;

public class DummyRealmProcess extends AbstractRealmProcess<Integer> {

    public DummyRealmProcess() {
        super();
    }

    @Override
    public boolean isCompleted() {
        return false;
    }

    @Override
    public void run(ExecutionContext executionContext) {

    }

    @Override
    public @Range(from = 0, to = 1) float getProgress() {
        return 0;
    }

}
