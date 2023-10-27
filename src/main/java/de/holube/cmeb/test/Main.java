package de.holube.cmeb.test;

import de.holube.cmeb.EventBus;
import de.holube.cmeb.EventBusImpl;

public class Main {

    public static void main(String[] args) {
        EventBus eventBus = new EventBusImpl();

        TestSubscriber testSubscriber = new TestSubscriber(eventBus);
        EventWrapper<Integer> eventWrapper = new EventWrapper<>(1);

        eventBus.post(eventWrapper);
    }

}