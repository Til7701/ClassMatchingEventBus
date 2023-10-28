package de.holube.cmeb;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class EventBusImpl implements EventBus {

    private final Map<Class<?>, Collection<Entry>> methodMap = new HashMap<>();

    private void putEntry(Class<?> clazz, Entry entry) {
        Collection<Entry> coll = methodMap.getOrDefault(clazz, new ArrayList<>());
        coll.add(entry);
        methodMap.put(clazz, coll);
    }

    private Optional<Collection<Entry>> getEntries(Class<?> clazz) {
        return Optional.ofNullable(methodMap.get(clazz));
    }

    @Override
    public void register(Object obj) {
        Class<?> clazz = obj.getClass();
        while (clazz != Object.class) {
            for (Method method : clazz.getDeclaredMethods()) {
                if (method.isAnnotationPresent(Subscribe.class)) {
                    Subscribe annotation = method.getAnnotation(Subscribe.class);
                    if (annotation.eventClass() != null)
                        putEntry(annotation.eventClass(), new Entry(obj, method));
                }
            }
            clazz = clazz.getSuperclass();
        }
    }

    @Override
    public void post(Event<?> event) {
        Class<?> eventClass = event.getEventClass();
        boolean foundSubscriber = false;

        do {
            Collection<Entry> coll = getEntries(eventClass).orElse(Collections.emptyList());
            if (!coll.isEmpty()){
                foundSubscriber = true;
                for (Entry entry : coll) {
                    invokeMethod(entry, event);
                }
            }
            eventClass = eventClass.getSuperclass();
        }
        while (eventClass != Object.class);

        if (!foundSubscriber) {
            System.out.println("empty");
        }
    }

    private void invokeMethod(Entry entry, Event<?> event) {
        try {
            entry.method().invoke(entry.object, event);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private record Entry(Object object, Method method) {
    }

}
