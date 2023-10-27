package de.holube.cmeb;

public interface EventBus {

    void register(Object obj);

    void post(Event<?> event);

}
