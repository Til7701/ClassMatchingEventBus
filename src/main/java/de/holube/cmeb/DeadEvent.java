package de.holube.cmeb;

public class DeadEvent {

    private final Event<?> event;

    public DeadEvent(Event<?> event) {
        this.event = event;
    }

    public Event<?> getEvent() {
        return event;
    }

}
