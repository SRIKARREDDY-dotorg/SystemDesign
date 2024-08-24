package org.taskmanage;

public interface Subscriber {
    void onMessage(Message message);
}
