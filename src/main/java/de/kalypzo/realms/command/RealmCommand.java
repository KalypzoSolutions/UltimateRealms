package de.kalypzo.realms.command;

import de.kalypzo.realms.RealmAPI;
import de.kalypzo.realms.RealmAPIProvider;
import de.kalypzo.realms.util.ExecutionUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.concurrent.CompletableFuture;

@Getter
@AllArgsConstructor
public abstract class RealmCommand {
    private CommandManager commandManager;

    /**
     * Uses bukkit scheduler to execute a runnable asynchronously
     *
     * @param runnable the runnable to execute
     * @return a completable future that completes when the runnable is done
     */
    public CompletableFuture<Void> runAsync(Runnable runnable) {
        return ExecutionUtil.runAsync(getCommandManager().getPlugin(), runnable);
    }

    public RealmAPI getRealmApi() {
        return RealmAPIProvider.getInstance();
    }

}
