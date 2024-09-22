package de.kalypzo.realms.realm.process.impl;

import de.kalypzo.realms.realm.process.ExecutionContext;
import org.jetbrains.annotations.Range;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class DummyRealmProcess extends AbstractRealmProcess<Integer> {


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

    @Override
    public CompletableFuture<Optional<Integer>> getFuture() {
        return null;
    }
}
