package ui; // UI-Paket: enthält alle Klassen der grafischen Oberfläche
import api.Shape; // Shape-Interface für alle Zeichenobjekte
import logic.ShapeType; // Enum für die Auswahl des aktuellen Zeichentyps
// Konkrete Implementierungen der Shapes
import model.Ellipse;
import model.Line;
import model.Rectangle;
import javax.swing.*; // Swing-Komponenten (JPanel)
import java.awt.*; // AWT-Grafikklassen (Graphics, Point)
import java.awt.event.*; // Mausereignisse (MouseEvent, MouseAdapter)
// Listen für gespeicherte Shapes
import java.util.ArrayList;
import java.util.List;

// Zeichenpanel, das alle Formen rendert und Mausinteraktionen verarbeitet
public class DrawingPanel extends JPanel {
    // Liste aller dauerhaft gespeicherten Shapes
    private final List<Shape> shapes = new ArrayList<>();
    // Aktuell ausgewählter Zeichentyp (Standard: Linie)
    private ShapeType currentType = ShapeType.LINE;
    // Startpunkt beim Drücken der Maus
    private Point startPoint = null;
    // Aktueller Punkt während des Ziehens
    private Point currentPoint = null;

    // Löscht alle Shapes und aktualisiert die Anzeige
    public void clear() {
        shapes.clear();
        repaint();
    }

    // Gibt die Liste der Shapes zurück (für Speichern)
    public List<Shape> getShapes() {
        return shapes;
    }

    // Setzt Shapes neu (für Laden) und aktualisiert die Anzeige
    public void setShapes(List<Shape> loadedShapes) {
        shapes.clear();
        shapes.addAll(loadedShapes);
        repaint();
    }

    // Konstruktor: richtet Hintergrund und Maussteuerung ein
    public DrawingPanel() {
        // Hintergrundfarbe des Zeichenbereichs
        setBackground(Color.WHITE);
        // Mausadapter verarbeitet Press, Drag und Release
        MouseAdapter mouseHandler = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // Startpunkt setzen, wenn Maus gedrückt wird
                startPoint = e.getPoint();
                // Vorschau beginnt am Startpunkt
                currentPoint = startPoint;
            }
            @Override
            public void mouseDragged(MouseEvent e) {
                // Aktuellen Punkt während des Ziehens aktualisieren
                currentPoint = e.getPoint();
                // Panel neu zeichnen, um Vorschau zu aktualisieren
                repaint();
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                // Beim Loslassen: Endpunkt vorhanden?
                if (startPoint != null && currentPoint != null) {
                    // Endgültiges Shape erzeugen
                    Shape finalShape = createShape(startPoint, currentPoint);
                    // Shape speichern, wenn gültig
                    if (finalShape != null) {
                        shapes.add(finalShape);
                    }
                }
                // Vorschau zurücksetzen
                startPoint = null;
                currentPoint = null;
                // Panel neu zeichnen
                repaint();
            }
        };
        // Mausereignisse registrieren
        addMouseListener(mouseHandler);
        addMouseMotionListener(mouseHandler);
    }

    // Erzeugt ein Shape basierend auf dem aktuellen Typ und den Punkten
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

    // Setzt den aktuellen Zeichentyp (über Menü oder Toolbar)
    public void setCurrentType(ShapeType type) {
        this.currentType = type;
    }

    @Override
    // Zeichnet alle gespeicherten Shapes sowie die Vorschau
    protected void paintComponent(Graphics g) {
        // Hintergrund und Standardverhalten von JPanel
        super.paintComponent(g);
        // Graphics2D für moderne Zeichenfunktionen
        Graphics2D g2 = (Graphics2D) g;
        // Alle gespeicherten Shapes zeichnen
        for (Shape s : shapes) {
            s.draw(g2);
        }
        // Vorschau zeichnen, falls gerade gezeichnet wird
        if (startPoint != null && currentPoint != null) {
            // Temporäres Shape erzeugen
            Shape preview = createShape(startPoint, currentPoint);
            if (preview != null) {
                // Vorschau in Grau zeichnen
                g2.setColor(Color.GRAY);
                preview.draw(g2);
            }
        }
    }

    /* Extra-Menü Farbe setzen
    aktuelle Farbe bestimmen */
    private Color currentColor = Color.BLACK;
    // Getter für aktuelle Farbe
    public Color getCurrentColor() {
        return currentColor;
    }
    // Setter für das Setzen der Farbe
    public void setCurrentColor(Color c) {
        this.currentColor = c;
    }
}