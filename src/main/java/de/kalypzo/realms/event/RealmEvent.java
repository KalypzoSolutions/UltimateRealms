package de.kalypzo.realms.event;


import de.kalypzo.realms.realm.RealmWorld;
import org.bukkit.event.Event;

/**
 * Event related to a realm
 */
public abstract class RealmEvent extends Event {

    private final RealmWorld realmWorld;

    public RealmEvent(RealmWorld realmWorld) {
        this.realmWorld = realmWorld;
    }

    public RealmEvent(RealmWorld realmWorld, boolean async) {
        super(async);
        this.realmWorld = realmWorld;
    }

    public RealmWorld getRealmWorld() {
        return realmWorld;
    }

}
