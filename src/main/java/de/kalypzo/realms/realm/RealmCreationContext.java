package de.kalypzo.realms.realm;

import de.kalypzo.realms.player.RealmPlayer;
import de.kalypzo.realms.realm.generator.RealmGenerator;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Getter
public class RealmCreationContext {
    private final @NotNull RealmPlayer realmOwner;
    private final RealmGenerator generator;

    /**
     * @param owner The owner of the realm
     */
    public RealmCreationContext(@NotNull RealmPlayer owner, RealmGenerator generator) {
        this.realmOwner = owner;
        this.generator = generator;
    }
}
