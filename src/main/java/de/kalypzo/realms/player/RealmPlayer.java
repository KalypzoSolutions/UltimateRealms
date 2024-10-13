package de.kalypzo.realms.player;

import de.kalypzo.realms.Constants;
import de.kalypzo.realms.realm.RealmWorld;
import org.jetbrains.annotations.Blocking;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface RealmPlayer {
    RealmPlayer SERVER = new RealmPlayerImpl(Constants.REALM_OWNER_UUID_SERVER);
    RealmPlayer PRE_GEN = new RealmPlayerImpl(Constants.REALM_OWNER_UUID_PRE_GEN);

    /**
     * @return UUID of the player.
     */
    UUID getUuid();

    /**
     * @return Immutable list of realms that this player owns.
     */
    @Blocking
    List<RealmWorld> getCachedOwningRealms();

    /**
     * @return CompletableFuture of an immutable list of realms that this player owns.
     */
    CompletableFuture<List<RealmWorld>> getOwningRealmsAsync();

    /**
     * Removes the realm from the list of realms that this player owns.
     *
     * @param realm RealmWorld instance.
     */
    void removeOwningRealm(RealmWorld realm);

    /**
     * Adds the realm to the list of realms that this player owns.
     *
     * @param realm RealmWorld instance.
     */
    void addOwningRealm(RealmWorld realm);


    /**
     * @param uuid UUID of the player.
     * @return RealmPlayer instance.
     */
    static RealmPlayer of(UUID uuid) {
        return new RealmPlayerImpl(uuid);
    }
}
