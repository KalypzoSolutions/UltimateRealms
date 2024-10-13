package de.kalypzo.realms.player;

import de.kalypzo.realms.realm.RealmWorld;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

/**
 * Stores some values in memory for faster access instead of loading them all the time.
 */
public class CachedRealmPlayerImpl extends RealmPlayerImpl {
    private @Nullable List<RealmWorld> cachedOwningRealms;

    public CachedRealmPlayerImpl(UUID uuid) {
        super(uuid);
    }

    @Override
    public List<RealmWorld> getCachedOwningRealms() {
        if (cachedOwningRealms == null) {
            cachedOwningRealms = super.getCachedOwningRealms();
        }
        return cachedOwningRealms;
    }


    @Override
    public void removeOwningRealm(RealmWorld realm) {
        super.removeOwningRealm(realm);
        cachedOwningRealms.remove(realm);
    }

    @Override
    public void addOwningRealm(RealmWorld realm) {
        super.addOwningRealm(realm);
        cachedOwningRealms.add(realm);
    }

    public void invalidateCache() {
        cachedOwningRealms = null;
    }
}
