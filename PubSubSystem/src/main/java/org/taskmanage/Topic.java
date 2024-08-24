package org.taskmanage;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class Topic {
    private final String name;
    private final Set<Subscriber> subscribers = new CopyOnWriteArraySet<>();
    public Topic(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public void addSubscriber(Subscriber subscriber) {
        this.subscribers.add(subscriber);
    }
    public void removeSubscriber(Subscriber subscriber) {
        this.subscribers.remove(subscriber);
    }
    public void publish(Message message) {
        for (Subscriber subscriber : subscribers) {
            subscriber.onMessage(message);
        }
    }
}
