package de.kalypzo.realms;

public class RealmAPIProvider {

    private static RealmAPI instance;

    private RealmAPIProvider() {}

    public static void setInstance(RealmAPI instance) {
        RealmAPIProvider.instance = instance;
    }


    public static RealmAPI getInstance() {
        if (instance == null) {
            throw new NotInitializedException();
        }
        return instance;
    }

    public static class NotInitializedException extends RuntimeException {
        public NotInitializedException() {
            super("RealmAPIProvider has not been initialized yet!");
        }
    }

}
