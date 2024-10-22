package de.kalypzo.realms.realm;

import de.kalypzo.realms.realm.flag.FlagContainer;
import lombok.Getter;

import java.util.Collection;
import java.util.UUID;
import java.util.function.Supplier;

@Getter
public class RealmWorldFactory {
    private final PlayerContainerFactory playerContainerFactory;

    public RealmWorldFactory(PlayerContainerFactory playerContainerFactory) {
        this.playerContainerFactory = playerContainerFactory;
    }

    public RealmWorld createRealmWorld(RealmCreationContext context) {
        return new RealmWorldImpl(
                UUID.randomUUID(),
                context.getRealmOwner().getUuid(),
                new FlagContainer(),
                playerContainerFactory.createPlayerContainer(),
                playerContainerFactory.createPlayerContainer(),
                () -> context
        );
    }

    public RealmWorld createRealmWorld(UUID realmId, UUID ownerUuid, Collection<UUID> trustedMembers, Collection<UUID> bannedMembers, Supplier<RealmCreationContext> contextSupplier) {
        return new RealmWorldImpl(
                realmId,
                ownerUuid,
                new FlagContainer(),
                playerContainerFactory.createPlayerContainer(trustedMembers),
                playerContainerFactory.createPlayerContainer(bannedMembers),
                contextSupplier
        );
    }

}
