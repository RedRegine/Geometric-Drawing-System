// Klasse liegt im ui Paket
package ui;
// Import der Shape Schnittstellen
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
    // Klasse ist eine Swing-Komponente, die selbst zeichnet
    public class DrawingPanel extends JPanel {
        // Liste aller fertigen Shapes die dauerhaft angezeigt werden
        private final List<Shape> shapes = new ArrayList<>();
        // Jede Form ist ein Objekt seiner eigenen Klasse (Line, Ellipse oder Rectangle)
        // Standardform ist Linie >> später über Menü anpassen
        private ShapeType currentType = ShapeType.LINE;
        // startPoint wo der User die Maus gedrückt hat
        private Point startPoint = null;
        // Wo der User mit der Maus aktuell ist
        private Point currentPoint = null;

        // Konstruktor
        public DrawingPanel() {
            // Hintergrund des Panels wird weiß gesetzt
            setBackground(Color.WHITE);
            // Ist eine Klasse die Mausereignisse verarbeitet
            MouseAdapter mouseHandler = new MouseAdapter() {

                @Override
                public void mousePressed(MouseEvent e) {
                    // User drückt die Maus >> Startpunkt gespeichert
                    startPoint = e.getPoint();
                    // aktueller Punkt erstmal auf Start setzen, damit die Vorschau funktioniert
                    currentPoint = startPoint;
                }

                @Override
                public void mouseDragged(MouseEvent e) {
                    // User zieht die Maus >> aktueller Punkt wird ständig aktuallisiert
                    currentPoint = e.getPoint();
                    // Swing das Signal geben um das Panel neu zu zeichnen
                    repaint();
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    // User lässt die Maus los >> Endpunkt gesetzt
                    if (startPoint != null && currentPoint != null) {
                        Shape finalShape = createShape(startPoint, currentPoint);
                        if (finalShape != null) {
                            // endgültige Form wird erzeugt un din shapes gespeichert
                            shapes.add(finalShape);
                        }
                    }
                    // Vorschaupunkte löschen
                    startPoint = null;
                    currentPoint = null;
                    repaint();
                }
            };
            // MausHandler reagiert jetzt auf Klicks, Drags und Loslassen
            addMouseListener(mouseHandler);
            addMouseMotionListener(mouseHandler);
        }
    }

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
}