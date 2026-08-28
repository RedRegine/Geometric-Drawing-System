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

    public void getStart() {
        return start;
    }

    public void getEnd() {
        return end;
    }

    @Override
    public void draw(Graphics2D gLine) {
        gLine.drawLine(start.x, start.y, end.x, end.y);
    }
}
