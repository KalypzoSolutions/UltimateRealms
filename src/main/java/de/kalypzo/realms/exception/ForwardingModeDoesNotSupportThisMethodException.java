package de.kalypzo.realms.exception;

public class ForwardingModeDoesNotSupportThisMethodException extends UnsupportedOperationException {
    public ForwardingModeDoesNotSupportThisMethodException() {
        super("The forwarding mode does not support the method.");
    }
}
