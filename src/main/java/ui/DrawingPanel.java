package ui;

import api.Shape;
import logic.ShapeType;
import model.Ellipse;
import model.Line;
import model.Rectangle;
import javax.swing.*; // Swing-Komponenten
import java.awt.*; // AWT-Grafikklassen
import java.awt.event.*; // Maus-Events
import java.util.ArrayList; // Listen für gespeicherte Formen
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
                if (startPoint != null && currentPoint != null) {
                    Shape finalShape = createShape(startPoint, currentPoint);
                    if (finalShape != null) {
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
    }   // <--- WICHTIG: Konstruktor sauber geschlossen


    private Shape createShape(Point start, Point end) {
        switch (currentType) {
            case LINE:
                return new Line(start, end);
            case RECTANGLE:
                return new Rectangle(start, end);
            case ELLIPSE:
                return new Ellipse(start, end);
            default:
                return null;
        }
    }

    public void setCurrentType(ShapeType type) {
        this.currentType = type;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        for (Shape s : shapes) {
            s.draw(g2);
        }

        if (startPoint != null && currentPoint != null) {
            Shape preview = createShape(startPoint, currentPoint);
            if (preview != null) {
                g2.setColor(Color.GRAY);
                preview.draw(g2);
            }
        }
    }
}