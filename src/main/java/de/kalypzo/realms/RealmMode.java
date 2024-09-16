package de.kalypzo.realms;

/**
 * The RealmMode enum is used to determine the mode of the realm server.
 */
public enum RealmMode {
    /**
     * Single server mode is used when only one server is running and no synchronization is needed.
     */
    SINGLE_SERVER,
    /**
     * Multi-Server mode is used when multiple servers are running.
     * Running in slave mode means that the server is not the main server and will not handle requests that are broadcasted to all server.
     * It is used to synchronize the realm-data between the servers.
     */
    MULTI_SERVER_SLAVE,
    /**
     * Multi-Server mode is used when multiple servers are running.
     * Running in master mode means that the server is the main server and will handle requests that are broadcasted to all server.
     * It is used to synchronize the realm-data between the servers.
     */
    MULTI_SERVER_MASTER,
    /**
     * Forwarding mode is used when multiple servers are running.
     * It is used to synchronize the realm-data between the servers.
     * In this mode, all actions are forwarded to a realm server.
     */
    FORWARDING
}
