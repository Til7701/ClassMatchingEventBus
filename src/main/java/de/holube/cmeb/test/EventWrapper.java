package de.holube.cmeb.test;

import de.holube.cmeb.Event;

public class EventWrapper<T> implements Event<T> {

    private final Class<T> clazz;

    private final T value;

    public EventWrapper(T value) {
        this.value = value;
        this.clazz = (Class<T>) value.getClass();
    }

    @Override
    public Class<T> getEventClass() {
        return clazz;
    }

    public T getValue() {
        return value;
    }

}
