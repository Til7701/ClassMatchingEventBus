package de.holube.cmeb;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
        StringSubscriber ts = mock(StringSubscriber.class);
        Event<String> event = (Event<String>) Mockito.mock(Event.class);

        when(event.getEventClass()).thenReturn(String.class);

        EventBus eventBus = new EventBus();
        eventBus.register(ts);

        eventBus.post(event);
        verify(ts, times(1)).onTestEvent(event);
    }

    @Test
    void expected2SubscriberTest() {
        StringSubscriber ss = mock(StringSubscriber.class);
        IntegerSubscriber is = mock(IntegerSubscriber.class);

        Event<String> event = (Event<String>) mock(Event.class);
        when(event.getEventClass()).thenReturn(String.class);

        EventBus eventBus = new EventBus();
        eventBus.register(ss);
        eventBus.register(is);

        eventBus.post(event);
        verify(ss, times(1)).onTestEvent(event);
        verify(is, times(0)).onTestEvent(any());
    }

    @Test
    void expected2SubscriberMethodsTest() {
        IntegerStringSubscriber iss = mock(IntegerStringSubscriber.class);

        Event<String> stringEvent = (Event<String>) mock(Event.class);
        when(stringEvent.getEventClass()).thenReturn(String.class);
        Event<Integer> integerEvent = (Event<Integer>) mock(Event.class);
        when(integerEvent.getEventClass()).thenReturn(Integer.class);

        EventBus eventBus = new EventBus();
        eventBus.register(iss);

        eventBus.post(stringEvent);
        verify(iss, times(1)).onStringEvent(stringEvent);
        verify(iss, times(0)).onIntegerEvent(any());

        eventBus.post(integerEvent);
        verify(iss, times(1)).onStringEvent(stringEvent);
        verify(iss, times(1)).onIntegerEvent(integerEvent);
    }

    @Test
    void deadEventTest() {
        DeadEventSubscriber des = mock(DeadEventSubscriber.class);
        StringSubscriber ss = mock(StringSubscriber.class);
        Event<Integer> event = (Event<Integer>) mock(Event.class);
        when(event.getEventClass()).thenReturn(Integer.class);

        EventBus eventBus = new EventBus();
        eventBus.register(des);
        eventBus.register(ss);

        eventBus.post(event);
        verify(des, times(1)).onTestEvent(any());
        verify(ss, times(0)).onTestEvent(any());
    }

    @Test
    void inheritanceTest() {
        InheritanceSubscriber is = mock(InheritanceSubscriber.class);

        Event<SuperInterface> event = new Event<>(new Impl());

        EventBus eventBus = new EventBus();
        eventBus.register(is);

        eventBus.post(event);
        verify(is, times(1)).onTestEvent(any());
    }

    interface SuperInterface {
    }

    static class StringSubscriber {
        @Subscribe(eventClass = String.class)
        public void onTestEvent(Event<String> event) {
        }
    }

    static class IntegerSubscriber {
        @Subscribe(eventClass = Integer.class)
        public void onTestEvent(Event<Integer> event) {
        }
    }

    static class IntegerStringSubscriber {
        @Subscribe(eventClass = Integer.class)
        public void onIntegerEvent(Event<Integer> event) {
        }

        @Subscribe(eventClass = String.class)
        public void onStringEvent(Event<String> event) {
        }
    }

    static class DeadEventSubscriber {
        @Subscribe(eventClass = DeadEvent.class)
        public void onTestEvent(DeadEvent event) {
        }
    }

    static class Impl implements SuperInterface {
    }

    static class InheritanceSubscriber {
        @Subscribe(eventClass = Impl.class)
        public void onTestEvent(Event<Impl> event) {
        }
    }

}
