package com.srikar.visitor.pattern.vistor;

import com.srikar.visitor.pattern.shapes.Circle;
import com.srikar.visitor.pattern.shapes.CompoundShape;
import com.srikar.visitor.pattern.shapes.Dot;
import com.srikar.visitor.pattern.shapes.Rectangle;
import com.srikar.visitor.pattern.shapes.Shape;

public class XMLExportVisitor implements Visitor {
    public String export(Shape... args) {
        StringBuilder stringBuilder = new StringBuilder();
        for(Shape shape: args) {
            stringBuilder.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "\n");
            stringBuilder.append(shape.accept(this)).append("\n");
        }
        return stringBuilder.toString();
    }

    @Override
    public String visitDot(Dot dot) {
        return "<dot>" + "\n" +
                "    <id>" + dot.getId() + "</id>" + "\n" +
                "    <x>" + dot.getX() + "</x>" + "\n" +
                "    <y>" + dot.getY() + "</y>" + "\n" +
                "</dot>";
    }

    @Override
    public String visitCircle(Circle circle) {
        return "<circle>" + "\n" +
                "    <id>" + circle.getId() + "</id>" + "\n" +
                "    <x>" + circle.getX() + "</x>" + "\n" +
                "    <y>" + circle.getY() + "</y>" + "\n" +
                "    <radius>" + circle.getRadius() + "</radius>" + "\n" +
                "</circle>";
    }

    @Override
    public String visitRectangle(Rectangle rectangle) {
        return "<rectangle>" + "\n" +
                "    <id>" + rectangle.getId() + "</id>" + "\n" +
                "    <x>" + rectangle.getX() + "</x>" + "\n" +
                "    <y>" + rectangle.getY() + "</y>" + "\n" +
                "    <width>" + rectangle.getWidth() + "</width>" + "\n" +
                "    <height>" + rectangle.getHeight() + "</height>" + "\n" +
                "</rectangle>";
    }

    @Override
    public String visitCompoundGraphic(CompoundShape compoundShape) {
        return "<compound_graphic>" + "\n" +
                "   <id>" + compoundShape.getId() + "</id>" + "\n" +
                _visitCompoundGraphic(compoundShape) + "</compound_graphic>";
    }

    private String _visitCompoundGraphic(CompoundShape compoundShape) {
        StringBuilder stringBuilder = new StringBuilder();
        for(Shape shape : compoundShape.getChildren()) {
            String object = shape.accept(this);
            object = "    " + object.replace("\n", "\n    ") + "\n";
            stringBuilder.append(object);
        }
        return stringBuilder.toString();
    }
}
