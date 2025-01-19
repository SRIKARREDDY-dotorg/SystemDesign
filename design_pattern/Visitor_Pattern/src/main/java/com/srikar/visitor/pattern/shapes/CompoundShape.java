package com.srikar.visitor.pattern.shapes;

import com.srikar.visitor.pattern.vistor.Visitor;

import java.util.ArrayList;
import java.util.List;

public class CompoundShape implements Shape {
    private int id;
    private List<Shape> children = new ArrayList<>();

    public CompoundShape(int id) {
        this.id = id;
    }
    @Override
    public void move(int x, int y) {
        // move shape
    }

    @Override
    public void draw() {
        // draw shape
    }

    @Override
    public String accept(Visitor visitor) {
        return visitor.visitCompoundGraphic(this);
    }
    public int getId() {
        return id;
    }
    public void add(Shape shape) {
        children.add(shape);
    }

    public Shape[] getChildren() {
        return children.toArray(new Shape[children.size()]);
    }
}
