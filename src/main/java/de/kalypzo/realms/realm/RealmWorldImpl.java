package de.kalypzo.realms.realm;

import de.kalypzo.realms.player.PlayerContainer;
import de.kalypzo.realms.player.RealmPlayer;
import de.kalypzo.realms.realm.flag.FlagContainer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

@Getter
@AllArgsConstructor
@Setter
public class RealmWorldImpl implements RealmWorld {

    private final UUID realmId;
    private UUID ownerUuid;
    private final FlagContainer flags;
    private final PlayerContainer trustedMembers;
    private final PlayerContainer bannedMembers;
    private final Supplier<RealmCreationContext> realmCreationContextSupplier;

    @Override
    public boolean isLoaded() {
        return false;
    }

    @Override
    public boolean isLoadedLocally() {
        return false;
    }

    @Override
    public RealmPlayer getOwner() { //TODO
        return null;
    }

    public CompletableFuture<RealmCreationContext> getRealmCreationContextAsync() {
        return CompletableFuture.supplyAsync(realmCreationContextSupplier);
    }

}
