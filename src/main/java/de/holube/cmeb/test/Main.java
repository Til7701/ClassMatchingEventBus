package de.holube.cmeb.test;

import de.holube.cmeb.Event;
import de.holube.cmeb.EventBus;

public class Main {

    public static void main(String[] args) {
        EventBus eventBus = new EventBus();

        TestSubscriber testSubscriber = new TestSubscriber(eventBus);
        Event<Integer> eventWrapper = new Event<>(1);

        eventBus.post(eventWrapper);
    }

}