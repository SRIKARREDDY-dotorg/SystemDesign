package org.taskmanage;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Topic topic1 = new Topic("Topic 1");
        Topic topic2 = new Topic("Topic 2");

        Publisher publisher1 = new Publisher();
        Publisher publisher2 = new Publisher();

        // Create Subscriber instances
        Subscriber subscriber1 = new PrintSubscriber("Subscriber 1");
        Subscriber subscriber2 = new PrintSubscriber("Subscriber 2");
        Subscriber subscriber3 = new PrintSubscriber("Subscriber 3");

        publisher1.registerTopic(topic1);
        publisher2.registerTopic(topic2);

        topic1.addSubscriber(subscriber1);
        topic1.addSubscriber(subscriber2);
        topic2.addSubscriber(subscriber2);
        topic2.addSubscriber(subscriber3);

        // publish messages
        publisher1.publish(topic1, new Message("Message 1 for Topic 1"));
        publisher1.publish(topic1, new Message("Message 2 for Topic 1"));
        publisher2.publish(topic2, new Message("Message 1 for Topic 2"));

        topic1.removeSubscriber(subscriber1);

        // Publish more messages
        publisher1.publish(topic1, new Message("Message3 for Topic1"));
        publisher2.publish(topic2, new Message("Message2 for Topic2"));
    }
}