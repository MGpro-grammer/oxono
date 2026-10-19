package io.github.mgprogrammer.oxono.util;

import io.github.mgprogrammer.oxono.event.EventType;

/**
 * The Observer interface defines a contract for objects that need to be notified when
 * a specific event occurs. Observers are commonly used in the Observer design pattern
 * to enable an object (subject) to notify other dependent objects (observers) of state changes.
 *
 * This interface expects implementing classes to define how they handle updates
 * by implementing the {@code update} method. The method takes an {@code EventType}
 * object, which provides context about the event that triggered the notification.
 *
 * Implementations should handle the event appropriately based on the type or details
 * of the provided {@code EventType}. This enables a flexible and decoupled way to
 * propagate changes or interactions within the application.
 */
public interface Observer {
    /**
     * Handles an update triggered by the given event type. This method is called to notify
     * the observer of changes or events that have occurred. The specific implementation
     * should define how the observer processes the event and responds to it.
     *
     * @param eventType the event that occurred, represented as an instance of {@code EventType}.
     *                  It provides context or information about the update that needs to be
     *                  processed by the observer.
     */
    void update(EventType eventType);
}
