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
    public DrawingPanel() {
        setBackground(Color.WHITE);

        MouseAdapter mouseHandler = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                startPoint = e.getPoint();
                currentPoint = startPoint;
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                currentPoint = e.getPoint();
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if(startPoint != null && currentPoint != null) {
                    Shape finalShape = createShape(startPoint, currentPoint);
                    if(finalShape != null) {
                        shapes.add(finalShape);
                    }
                }
                startPoint = null;
                currentPoint = null;
                repaint();
            }
        };
        addMouseListener(mouseHandler);
        addMouseMotionListener(mouseHandler);
    }
}
