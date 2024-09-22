package de.kalypzo.realms.realm.process.impl;

import de.kalypzo.realms.realm.process.Cancellable;
import de.kalypzo.realms.realm.process.ExecutionContext;

import java.util.Optional;

public class DummyRealmProcess extends AbstractRealmProcess<Integer> implements Cancellable {

    public DummyRealmProcess() {
    }

    public DummyRealmProcess(boolean sync) {
        super(sync);
    }


    @Override
    public void run(ExecutionContext executionContext) {
        try {
            Thread.sleep(200);
            setProgress(0.2f);
            Thread.sleep(200);
            setProgress(0.4f);
            Thread.sleep(200);
            setProgress(0.6f);
            Thread.sleep(200);
            setProgress(0.8f);
            Thread.sleep(200);
            setProgress(1f);
        } catch (InterruptedException ignore) {
        }
        getFuture().complete(Optional.of(399));
    }


    @Override
    public boolean isCancelled() {
        return getFuture().isCancelled();
    }

    @Override
    public boolean cancel() {
        if (isCancelled()) {
            return false;
        }
        Thread.currentThread().interrupt();
        getFuture().cancel(true);
        return true;
    }

    @Override
    public boolean canCancel() {
        return !isCancelled();
    }
}
