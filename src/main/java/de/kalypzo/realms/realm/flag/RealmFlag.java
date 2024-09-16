package de.kalypzo.realms.realm.flag;

/**
 * Abstract flag class
 *
 * @param <V> The value type
 */
public abstract class RealmFlag<V extends FlagValueType> {
    private final String name;
    private final V value;

    /**
     * Creates a new flag
     *
     * @param value The default value of the flag
     */
    public RealmFlag(V value) {
        this.name = getName(this.getClass());
        this.value = value;
    }

    /**
     * Return the name of the flag.
     *
     * @param flagClass Flag class
     * @param <T>       Value type
     * @return The name of the flag implemented by the given class
     */
    public static <V extends FlagValueType, T extends RealmFlag<V>> String getName(Class<T> flagClass) {
        final StringBuilder flagName = new StringBuilder();
        final char[] chars = flagClass.getSimpleName().replace("Flag", "").toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (i == 0) {
                flagName.append(Character.toLowerCase(chars[i]));
            } else if (Character.isUpperCase(chars[i])) {
                flagName.append('-').append(Character.toLowerCase(chars[i]));
            } else {
                flagName.append(chars[i]);
            }
        }
        return flagName.toString();
    }

    /**
     * Returns the name of the flag
     *
     * @return The name of the flag
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the value of the flag
     *
     * @return The value of the flag
     */
    public V getValue() {
        return value;
    }


    /**
     * Sets the value of the flag
     *
     * @param value The value of the flag
     */
    public abstract void setValue(V value);
}
