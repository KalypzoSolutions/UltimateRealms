package de.kalypzo.realms.config;

/**
 * Configuration for the SSH connection
 */
public interface SSHConfiguration {

    /**
     * @return the host of the SSH server
     */
    String getHost();

    /**
     * @return the port of the SSH server
     */
    int getPort();

    /**
     * @return the username for the SSH connection
     */
    String getUsername();

    /**
     * @return the password for the SSH connection
     */
    String getPassword();

    /**
     * If the key file path is set, the password will be ignored
     *
     * @return the path to the key file
     */
    String getKeyFilePathString();

}
