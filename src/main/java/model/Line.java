package model;
import api.Shape;

public class Line implements Shape {
    @Override
    public void draw() {
        System.out.println("Drawing a Line");
    }
}
