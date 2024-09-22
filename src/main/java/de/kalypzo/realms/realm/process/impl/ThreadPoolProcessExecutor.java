package de.kalypzo.realms.realm.process.impl;

import de.kalypzo.realms.realm.process.ExecutionContext;
import de.kalypzo.realms.realm.process.ProcessExecutor;
import de.kalypzo.realms.realm.process.RealmProcess;

import java.io.Closeable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolProcessExecutor implements ProcessExecutor, Closeable {

    private final ExecutorService executorService;

    public ThreadPoolProcessExecutor(int threadCount) {
        executorService = Executors.newFixedThreadPool(threadCount);
    }

    public void execute(RealmProcess<?> process) {
        executorService.submit(() -> {
            process.run(new ExecutionContext(this));
        });
    }

    public void close() {
        try {
            executorService.awaitTermination(1, java.util.concurrent.TimeUnit.SECONDS);
        } catch (InterruptedException ignore) {

        }
    }
}
