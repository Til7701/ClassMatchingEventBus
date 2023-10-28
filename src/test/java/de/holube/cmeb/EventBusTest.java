package de.holube.cmeb;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

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

    @Test
    void expectedTest() {
        TestSubscriber ts = Mockito.mock(TestSubscriber.class);
        Event<String> event = (Event<String>) Mockito.mock(Event.class);
        Mockito.when(event.getEventClass()).thenReturn(String.class);

        EventBus eventBus = new EventBus();
        eventBus.register(ts);

        eventBus.post(event);
        Mockito.verify(ts, Mockito.times(1)).onTestEvent(event);
    }

    static class TestSubscriber {
        @Subscribe(eventClass = String.class)
        public void onTestEvent(Event<String> event) {
        }
    }

}
