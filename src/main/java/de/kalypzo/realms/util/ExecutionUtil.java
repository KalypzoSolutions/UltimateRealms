package de.kalypzo.realms.util;

import com.github.Anon8281.universalScheduler.UniversalScheduler;
import com.github.Anon8281.universalScheduler.scheduling.schedulers.TaskScheduler;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class ExecutionUtil {

    private static TaskScheduler scheduler;

    public static void init(JavaPlugin plugin) {
        scheduler = UniversalScheduler.getScheduler(plugin);
    }

    public static void checkInit() {
        if (scheduler == null) {
            throw new IllegalStateException("Scheduler not initialized");
        }
    }


    public static <T> CompletableFuture<T> supplyAsync(Supplier<T> supplier) {
        checkInit();
        CompletableFuture<T> future = new CompletableFuture<>();
        scheduler.runTaskAsynchronously(() -> {
            try {
                future.complete(supplier.get());
            } catch (Exception e) {
                future.completeExceptionally(e);
            }
        });

        return future;
    }

    /**
     * Runs a runnable asynchronously and returns a CompletableFuture that completes when the runnable is done.
     *
     * @param runnable The runnable to run
     * @return A CompletableFuture that completes when the runnable is done
     */
    public static CompletableFuture<Void> runAsync(Runnable runnable) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        scheduler.runTaskAsynchronously(() -> {
            try {
                runnable.run();
                future.complete(null);
            } catch (Exception e) {
                future.completeExceptionally(e);
            }
        });

        return future;
    }

    /**
     * During an async operation, this method will run the callable on the main thread and return the result.
     *
     * @param callable The callable to run
     * @param <T>      The return type of the callable
     * @return The result of the callable
     */
    public static <T> T runSyncBlocking(Callable<T> callable) {
        if (Bukkit.isPrimaryThread()) {
            try {
                return callable.call();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
        CompletableFuture<T> future = new CompletableFuture<>();
        scheduler.runTask(() -> {
            try {
                callable.call();
                future.complete(null);
            } catch (Exception e) {
                future.completeExceptionally(e);
            }
        });
        return future.join();
    }
}

