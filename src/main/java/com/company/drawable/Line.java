package com.company.drawable;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;

public class Line extends DrawableShape {

    public Line(Shape shapeToDraw) {
        super(shapeToDraw);
    }

    @Override
    public void draw(Graphics2D gd) {
        gd.draw(shapeToDraw);
    }
}
