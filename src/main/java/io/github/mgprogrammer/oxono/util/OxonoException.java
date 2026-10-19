package io.github.mgprogrammer.oxono.util;

/**
 * Represents a custom exception specific to the application. This exception is a runtime
 * exception used to indicate an error condition that occurs during the application's
 * execution, typically in the context of the Oxono logic or functionality.
 *
 * This exception extends {@link RuntimeException}, allowing it to be thrown during runtime
 * without explicit declaration in a method's throws clause. It provides a mechanism to
 * propagate error information with a descriptive message for debugging or handling purposes.
 *
 * The {@code OxonoException} can be instantiated with a message to provide additional
 * context about the error that occurred. This message can be useful for logging or
 * displaying meaningful error details to the user or developer.
 */
public class OxonoException extends RuntimeException {
    /**
     * Constructs a new OxonoException with the specified detail message.
     * This exception can be used to provide specific error context
     * relevant to the application's Oxono logic or functionality.
     *
     * @param message the detail message, which provides additional context
     *                about the error that occurred.
     */
    public OxonoException(String message) {
        super(message);
    }
}
