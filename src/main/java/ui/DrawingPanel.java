package ui;

import api.Shape;
import logic.ShapeType;
import model.Ellipse;
import model.Line;
import model.Rectangle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class DrawingPanel extends JPanel {
    private final List<Shape> shapes = new ArrayList<>();
    private ShapeType currentType = ShapeType.LINE;
    private Point startPoint = null;
    private Point currentPoint = null;
}