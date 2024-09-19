package de.kalypzo.realms;

public class ForwardingModeDoesNotSupportThisMethodException extends UnsupportedOperationException {
    public ForwardingModeDoesNotSupportThisMethodException() {
        super("The forwarding mode does not support the method.");
    }
}
