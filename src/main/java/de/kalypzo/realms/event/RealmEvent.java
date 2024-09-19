package de.kalypzo.realms.event;


import de.kalypzo.realms.realm.RealmWorld;
import lombok.Getter;
import org.bukkit.event.Event;

/**
 * Event related to a realm
 */
public abstract class RealmEvent extends Event {

    /**
     * @return The realm world related to this event
     */
    @Getter
    private final RealmWorld realmWorld;

    public RealmEvent(RealmWorld realmWorld) {
        this.realmWorld = realmWorld;
    }

    public RealmEvent(RealmWorld realmWorld, boolean async) {
        super(async);
        this.realmWorld = realmWorld;


    }


}
