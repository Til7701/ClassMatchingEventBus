package de.holube.cmeb;

/**
 * Events posted to the {@link EventBus}. T is the type the event is identified by and the type of the value carried.
 *
 * @param <T> type of event
 */
public class Event<T> {

    private final Class<T> clazz;

    private final T value;

    /**
     * Creates an event with the given value and T.getClass() as the class to identify the event by.
     *
     * @param value the value to be given to the subscriber
     */
    public Event(T value) {
        this.value = value;
        this.clazz = (Class<T>) value.getClass();
    }

    /**
     * Returns the {@link Class} this event is identified by.
     *
     * @return Class of the event
     */
    public Class<T> getEventClass() {
        return clazz;
    }

    /**
     * The value this event provides to the subscriber.
     *
     * @return the value
     */
    public T getValue() {
        return value;
    }

}
