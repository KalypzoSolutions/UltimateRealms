package de.kalypzo.realms;

import java.util.UUID;

public class Constants {
    /**
     * If the realm is created by the server, this UUID will be used as the owner.
     */
    public static final UUID REALM_OWNER_UUID_PRE_GEN = UUID.fromString("10000000-0000-0000-0000-000000000000");
    /**
     * If the realm is owned by the server, this UUID will be used as the owner reference.
     */
    public static final UUID REALM_OWNER_UUID_SERVER = UUID.fromString("10000000-0000-0000-0000-000000000001");


}
