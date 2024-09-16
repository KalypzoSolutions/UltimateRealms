package de.kalypzo.realms.realm.flag;

/**
 * Wrapper for flag value types
 */
public abstract class FlagValueType<T> {

    public abstract FlagValueType<T> fromString(String string);

    public abstract String toString();

}
