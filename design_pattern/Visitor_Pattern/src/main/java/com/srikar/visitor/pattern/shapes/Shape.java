package com.srikar.visitor.pattern.shapes;

import com.srikar.visitor.pattern.vistor.Visitor;

public interface Shape {
    void move(int x, int y);
    void draw();
    String accept(Visitor visitor);
}
