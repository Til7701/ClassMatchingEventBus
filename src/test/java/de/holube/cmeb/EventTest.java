package de.holube.cmeb;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EventTest {

    @Test
    void returnClassTest() {
        Event<String> event = new Event<>("test");
        assertEquals(String.class, event.getEventClass());
    }

    @Test
    void returnClassInheritTest() {
        Event<SuperInterface> event = new Event<>(new Impl());
        assertEquals(Impl.class, event.getEventClass());
    }

    interface SuperInterface {
    }

    static class Impl implements SuperInterface {
    }

}
