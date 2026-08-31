package model;
import api.Shape;
import java.awt.*;

public class Ellipse implements Shape {
    private final Point start;
    private final Point end;
    public Ellipse(Point start; Point end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public void draw() {
        System.out.println("Drawing a Ellipse");
    }
}
