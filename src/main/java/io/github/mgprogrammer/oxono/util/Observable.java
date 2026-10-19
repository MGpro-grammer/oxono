package io.github.mgprogrammer.oxono.util;

import io.github.mgprogrammer.oxono.event.EventType;

/**
 * The Observable interface defines methods for managing a list of observers
 * and notifying them of events. This establishes a publish-subscribe mechanism
 * that allows observers to be informed about changes or updates in the state
 * of an observable object.
 */
public interface Observable {
    /**
     * Registers an observer to the observable object. The observer will be notified
     * of events or state changes in the observable whenever such updates occur.
     *
     * @param o the observer to register. It must implement the {@code Observer} interface
     *          and define the behavior for handling updates through its {@code update} method.
     */
    void registerObserver(Observer o);
    /**
     * Removes the specified observer from the list of observers being managed
     * by this observable. Once removed, the observer will no longer receive
     * notifications about events from this observable.
     *
     * @param o the observer to be removed from the observable's list of observers
     */
    void removeObserver(Observer o);
    /**
     * Notifies all registered observers about an event by passing the specified event type.
     * Each observer in the list will receive the event notification to handle it accordingly.
     *
     * @param eventType the event that occurred, represented as an {@link EventType} object,
     *                  which carries information about the update that the observers need
     *                  to process.
     */
    void notifyObservers(EventType eventType);
}
