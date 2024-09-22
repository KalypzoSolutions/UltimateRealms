package de.kalypzo.realms.realm.process;

import de.kalypzo.realms.realm.process.impl.DummyRealmProcess;
import de.kalypzo.realms.realm.process.impl.RealmProcessSequence;
import de.kalypzo.realms.realm.process.impl.ThreadPoolProcessExecutor;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
public class ProcessExecutorTest {
    private static ThreadPoolProcessExecutor processExecutor;

    @BeforeAll
    static void setUp() {
        processExecutor = new ThreadPoolProcessExecutor(2);
    }

    @AfterAll
    static void tearDown() {
        processExecutor.close();
    }

    @Test
    void testDummy() {
        var proc = createDummyProcess("testDummy");
        processExecutor.execute(proc);
        var res = proc.getFuture().join();
        Assertions.assertFalse(res.isEmpty());
        Assertions.assertInstanceOf(Integer.class, res.get());
    }

    @Test
    void testSequence() {
        var proc = new RealmProcessSequence<Integer>(
                createDummyProcess("Dummy-1"),
                createDummyProcess("Dummy-2"),
                createDummyProcess("Dummy-3")
        );
        Assertions.assertEquals(3, proc.getProcesses().length);
        proc.subscribe(new TestObserver("Sequence", proc));
        proc.setSubProcessCompletionHandler((sub) -> {
            log.info("Sub process completed: {}", sub);
        });
        processExecutor.execute(proc);
        var res = proc.getFuture().join();
        Assertions.assertFalse(res.isEmpty());

    }
    @Test
    void testSequenceWithEmptyProcesses() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new RealmProcessSequence<>();
        });
    }
    @Test
    void testSequenceWithSingleProcess() {
        var proc = new RealmProcessSequence<>(createDummyProcess("Dummy-1"));
        Assertions.assertEquals(1, proc.getProcesses().length);
        proc.subscribe(new TestObserver("Sequence", proc));
        proc.setSubProcessCompletionHandler((sub) -> {
            log.info("Sub process completed: {}", sub);
        });
        processExecutor.execute(proc);
        var res = proc.getFuture().join();
        Assertions.assertFalse(res.isEmpty());
    }

    @Test
    void testInterruptedSequence() {
        var proc = new RealmProcessSequence<>(
                createDummyProcess("Dummy-1"),
                createDummyProcess("Dummy-2"),
                createDummyProcess("Dummy-3")
        );
        proc.subscribe(new TestObserver("Sequence", proc));
        proc.setSubProcessCompletionHandler((sub) -> {
            log.info("Sub process completed: {}", sub);
        });
        processExecutor.execute(proc);
        proc.cancel();
        var res = proc.getFuture().join();
        Assertions.assertTrue(res.isEmpty());
    }

    DummyRealmProcess createDummyProcess(String name) {
        var proc = new DummyRealmProcess();
        proc.subscribe(new TestObserver(name, proc));
        return proc;
    }

    @AllArgsConstructor
    static class TestObserver implements RealmProcessObserver {
        private static final Logger LOGGER = LoggerFactory.getLogger(TestObserver.class);
        private final String name;
        private final RealmProcess<?> observing;

        @Override
        public void onProgressChange() {
            LOGGER.info("[{}] Progress changed {}", name, observing.getProgress());
        }
    }

}
