package de.kalypzo.realms.realm.process.impl;

import com.github.Anon8281.universalScheduler.scheduling.schedulers.TaskScheduler;
import de.kalypzo.realms.realm.process.ExecutionContext;
import de.kalypzo.realms.realm.process.ProcessExecutor;
import de.kalypzo.realms.realm.process.RealmProcess;
import lombok.Getter;

/**
 * Executes Realm Processes
 */
@Getter
public class UniversalProcessExecutor implements ProcessExecutor {

    private final TaskScheduler scheduler;

    public UniversalProcessExecutor(TaskScheduler scheduler) {
        this.scheduler = scheduler;
    }

    @Override
    public void execute(RealmProcess<?> process) {
        ExecutionContext context = new ExecutionContext(this);
        if (process.needsExecutedSynchronously()) {
            scheduler.runTask(() -> process.run(context));
        } else {
            scheduler.runTaskAsynchronously(() -> process.run(context));

        }
    }


}
