package model; // Modell-Paket: enthält konkrete Shape-Implementierungen
import api.Shape; // Shape-Interface für gemeinsame Zeichenfunktion
import java.awt.*; // AWT für Layouts (BorderLayout)
import java.io.Serializable; // Serializable für Datei-Speicherung

// Klasse für Ellipsenobjekte, implementiert Shape und Serializable
public class Ellipse implements Shape, Serializable {
    // Startpunkt (linke obere Ecke des Begrenzungsrechtecks)
    private final Point start;
    // Endpunkt (rechte untere Ecke des Begrenzungsrechtecks)
    private final Point end;
    // Farbe des Rechtecks
    private final Color color;
    // Strichstärke der Linien des Rechtecks
    private final float strokeWidth;

    // alter Konstruktor: setzt Start- und Endpunkt
    public Ellipse(Point start, Point end) {
        this(start, end, Color.BLACK, 1.0f);
    }

    // neuer Konstruktor: Start- und Endpunkt, Strichfarbe und Strichstärke
    public Ellipse(Point start, Point end, Color color, float strokeWidth) {
        this.start = start;
        this.end = end;
        this.color = color;
        this.strokeWidth = strokeWidth;
    }

    // Getter für Startpunkt
    public Point getStart() {
        return start;
    }
    // Getter für Endpunkt
    public Point getEnd() {
        return end;
    }
    // Getter für die Formfarbe
    @Override
    public Color getColor() {
        return color;
    }
    // Getter für die Strichstärke
    @Override
    public float getStrokeWidth() {
        return strokeWidth;
    }

    @Override
    // Zeichnet die Ellipse basierend auf dem Begrenzungsrechteck
    public void draw(Graphics2D gEllipse) {
        // Aktuelle Einstellungen sichern
        Color oldColor = gEllipse.getColor();
        Stroke oldStroke = gEllipse.getStroke();

        // Farbe und Strichstärke setzen
        gEllipse.setColor(color);
        gEllipse.setStroke(new BasicStroke(strokeWidth));

        // Breite berechnet aus Differenz der x-Koordinaten
        int width = end.x - start.x;
        // Höhe berechnet aus Differenz der y-Koordinaten
        int height = end.y - start.y;
        // Ellipse zeichnen
        gEllipse.drawOval(start.x, start.y, width, height);

        // Einstellungen wiederherstellen
        gEllipse.setColor(oldColor);
        gEllipse.setStroke(oldStroke);
    }

    @Override
    public boolean containsPoint(int x, int y) {
        // Breite und Höhe der Ellipse
        int width = Math.abs(end.x - start.x);
        int height = Math.abs(end.y - start.y);

        // Verhindert Division durch 0
        if (width == 0 || height == 0) {
            return false;
        }

        // Mittelpunkt der Ellipse
        int cx = Math.min(start.x, end.x) + width / 2;
        int cy = Math.min(start.y, end.y) + height / 2;

        // Radien
        double rx = width / 2.0;
        double ry = height / 2.0;

        // Ellipsen-Gleichung prüfen
        double dx = (x - cx) / rx;
        double dy = (y - cy) / ry;

        return dx * dx + dy * dy <= 1.0;
    }
}
