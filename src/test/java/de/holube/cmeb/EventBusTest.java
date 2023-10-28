package de.holube.cmeb;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class EventBusTest {

    @Test
    void registerNullTest() {
        EventBus eventBus = new EventBus();
        assertThrows(NullPointerException.class, () -> eventBus.register(null));
    }

    @Test
    void eventNullTest() {
        EventBus eventBus = new EventBus();
        assertThrows(NullPointerException.class, () -> eventBus.post(null));
    }

}
