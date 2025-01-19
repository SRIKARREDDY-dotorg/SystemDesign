package com.srikar.visitor.pattern;

import com.srikar.visitor.pattern.shapes.Circle;
import com.srikar.visitor.pattern.shapes.CompoundShape;
import com.srikar.visitor.pattern.shapes.Dot;
import com.srikar.visitor.pattern.shapes.Rectangle;
import com.srikar.visitor.pattern.shapes.Shape;
import com.srikar.visitor.pattern.vistor.XMLExportVisitor;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        Dot dot = new Dot(1, 10, 55);
        Circle circle = new Circle(2, 23, 15, 10);
        Rectangle rectangle = new Rectangle(3, 10, 17, 20, 30);

        CompoundShape compoundShape = new CompoundShape(4);
        compoundShape.add(dot);
        compoundShape.add(circle);
        compoundShape.add(rectangle);

        CompoundShape compoundShape1 = new CompoundShape(5);
        compoundShape1.add(dot);

        compoundShape.add(compoundShape1);

        export(circle, compoundShape);
    }

    private static void export(Shape... shapes) {
        XMLExportVisitor xmlExportVisitor = new XMLExportVisitor();
        System.out.println(xmlExportVisitor.export(shapes));
    }
}