package de.kalypzo.realms.realm.generator;


import de.kalypzo.realms.Constants;

import java.util.UUID;

/**
 * Idea by @Ozan
 * > In order to work fast, it needs [...] to create realm with schematic in advance.
 * <p>
 * for example: It creates n-Realms in [...] [advance] without handing them over to the players, and allows the players to own the existing realms on demand at an instant.
 */
public class PreGeneratedRealmPool implements RealmGenerator {
    private final RealmGenerator fallbackGenerator;
    public static final UUID OWNER_UUID = Constants.REALM_OWNER_UUID_PRE_GEN;

    /**
     * @param fallback if the pool is empty, this generator will be used to create a new realm
     */
    public PreGeneratedRealmPool(RealmGenerator fallback) {
        this.fallbackGenerator = fallback;
    }
}
