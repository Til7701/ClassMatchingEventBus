package de.holube.cmeb;

public interface Event<T> {

    Class<T> getEventClass();

}
