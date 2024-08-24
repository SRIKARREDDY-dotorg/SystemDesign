package org.taskmanage;

import java.util.HashSet;
import java.util.Set;

public class Publisher {
    public final Set<Topic> topics;

    public Publisher() {
        this.topics = new HashSet<>();
    }

    public void registerTopic(Topic topic) {
        this.topics.add(topic);
    }

    public void publish(Topic topic, Message message) {
        if(!topics.contains(topic)) {
            System.out.println("This publisher can't publish to the topic " + topic.getName());
            return;
        }
        topic.publish(message);
    }
}
