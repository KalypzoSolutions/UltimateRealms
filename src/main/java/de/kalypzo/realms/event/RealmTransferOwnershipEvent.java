package de.kalypzo.realms.event;

import de.kalypzo.realms.player.RealmPlayer;
import de.kalypzo.realms.realm.RealmWorld;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * Called when ownership of a realm is transferred.
 * Can be cancelled to prevent the ownership transfer.
 * The event might be called async.
 */
@Getter
@Setter
public class RealmTransferOwnershipEvent extends RealmEvent implements Cancellable {
    private static final HandlerList HANDLERS = new HandlerList();
    private boolean cancelled;

    public RealmTransferOwnershipEvent(RealmWorld realmWorld, RealmPlayer oldOwner, RealmPlayer newOwner) {
        super(realmWorld, !Bukkit.isPrimaryThread());
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }


}
