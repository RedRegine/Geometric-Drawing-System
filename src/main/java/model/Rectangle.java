package model;
import api.Shape;
import java.awt.*;

public class Rectangle implements Shape {
    private final Point start;
    private final Point end;
    public Rectangle(Point start, Point end) {
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
    public void draw(Graphics2D gRectangle) {
        int width = end.x - start.x;
        int height = end.y - start.y;
        gRectangle.drawRect(start.x, start.y, width, height);
    }
}
