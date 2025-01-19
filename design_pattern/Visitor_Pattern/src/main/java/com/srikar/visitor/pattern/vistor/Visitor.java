package com.srikar.visitor.pattern.vistor;

import com.srikar.visitor.pattern.shapes.Circle;
import com.srikar.visitor.pattern.shapes.CompoundShape;
import com.srikar.visitor.pattern.shapes.Dot;
import com.srikar.visitor.pattern.shapes.Rectangle;

public interface Visitor {
    String visitDot(Dot dot);
    String visitCircle(Circle circle);
    String visitRectangle(Rectangle rectangle);
    String visitCompoundGraphic(CompoundShape compoundShape);
}
