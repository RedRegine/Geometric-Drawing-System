package model;
import api.Shape;

public static class Ellipse implements Shape {
    @Override
    public void draw() {
        System.out.println("Drawing a Ellipse");
    }
}
