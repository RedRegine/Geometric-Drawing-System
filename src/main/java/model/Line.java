package model;
import api.Shape;
import java.awt.*;

public class Line implements Shape {
    private final Point start;
    private final Point end;

    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    public Point getStart() {
        return start;
    }

    public Point getEnd() {
        return end;
    }

    @Override
    public void draw(Graphics2D gLine) {
        gLine.drawLine(start.x, start.y, end.x, end.y);
    }
}
