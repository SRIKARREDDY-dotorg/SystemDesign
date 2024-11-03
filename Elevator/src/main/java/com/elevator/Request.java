package com.elevator;

public class Request {
    private final int source;
    private final int destination;

    public Request(int source, int destination) {
        this.source = source;
        this.destination = destination;
    }

    public int getSource() {
        return source;
    }
    public int getDestination() {
        return destination;
    }
}
