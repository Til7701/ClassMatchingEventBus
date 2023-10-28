package de.holube.cmeb;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class EventBusImplTest {

    @Test
    void registerNullTest() {
        EventBusImpl eventBus = new EventBusImpl();
        assertThrows(NullPointerException.class, () -> eventBus.register(null));
    }

    @Test
    void eventNullTest() {
        EventBusImpl eventBus = new EventBusImpl();
        assertThrows(NullPointerException.class, () -> eventBus.post(null));
    }

}
