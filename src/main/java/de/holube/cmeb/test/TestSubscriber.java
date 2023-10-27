package de.holube.cmeb.test;

import de.holube.cmeb.EventBus;
import de.holube.cmeb.Subscribe;

public class TestSubscriber {

    public TestSubscriber(EventBus eventBus) {
        eventBus.register(this);
    }

    @Subscribe(clazz = Integer.class)
    public void onTestEvent(EventWrapper<Integer> event) {
        System.out.println("method called, content: " + event.getValue());
    }

}
