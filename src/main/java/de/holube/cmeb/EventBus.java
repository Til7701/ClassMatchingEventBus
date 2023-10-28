package de.holube.cmeb;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Dispatches {@link Event}s to subscriber methods.
 * <p>
 * # Usage
 * To register an object for events: {@code eventbus.register(obj)}
 * To listen to Events of type {@link String} in that object create a method as follows.
 * <pre>{@code
 * @Subscribe(eventClass = String.class)
 * public void onTestEvent(Event<String> event) {
 *     // do stuff
 * }
 * }</pre>
 */
public class EventBus {

    private final Map<Class<?>, Collection<Entry>> methodMap = new HashMap<>();

    private void putEntry(Class<?> clazz, Entry entry) {
        Collection<Entry> coll = methodMap.getOrDefault(clazz, new ArrayList<>());
        coll.add(entry);
        methodMap.put(clazz, coll);
    }

    private Optional<Collection<Entry>> getEntries(Class<?> clazz) {
        return Optional.ofNullable(methodMap.get(clazz));
    }

    public void register(Object obj) {
        Class<?> clazz = obj.getClass();
        while (clazz != Object.class) {
            for (Method method : clazz.getDeclaredMethods()) {
                if (method.isAnnotationPresent(Subscribe.class)) { // TODO test for method args
                    Subscribe annotation = method.getAnnotation(Subscribe.class);
                    if (annotation.eventClass() != null)
                        putEntry(annotation.eventClass(), new Entry(obj, method));
                }
            }
            clazz = clazz.getSuperclass();
        }
    }

    public void post(Event<?> event) {
        Class<?> eventClass = event.getEventClass();
        boolean foundSubscriber = false;

        do {
            Collection<Entry> coll = getEntries(eventClass).orElse(Collections.emptyList());
            foundSubscriber = foundSubscriber || invokeAll(coll, event);
            eventClass = eventClass.getSuperclass();
        }
        while (eventClass != Object.class);

        if (!foundSubscriber) {
            Collection<Entry> coll = getEntries(DeadEvent.class).orElse(Collections.emptyList());
            invokeAll(coll, new DeadEvent(event));
        }
    }

    /**
     * Sends the given event to all given subscribers.
     *
     * @param entries  the entries
     * @param argument the argument
     * @return true, if a subscriber was found
     */
    private boolean invokeAll(Collection<Entry> entries, Object argument) {
        boolean foundSubscriber = false;
        if (!entries.isEmpty()) {
            foundSubscriber = true;
            for (Entry entry : entries) {
                invokeMethod(entry, argument);
            }
        }
        return foundSubscriber;
    }

    /**
     * Invokes the method on the object in the {@link Entry} giving the event as an argument.
     *
     * @param entry    the entry with the Object and Method
     * @param argument the object to give as an argument
     */
    private void invokeMethod(Entry entry, Object argument) {
        try {
            entry.method().invoke(entry.object, argument);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private record Entry(Object object, Method method) {
    }

}
