package de.kalypzo.realms.realm;


import de.kalypzo.realms.player.RealmPlayer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

/**
 * Realm Creation Context provides metadata about the realm creation.
 */
@Getter
@AllArgsConstructor
public class RealmCreationContext {
    private final @NotNull RealmPlayer realmOwner;


}
