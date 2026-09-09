package model; // Modell-Paket: enthält konkrete Shape-Implementierungen
import api.Shape; // Shape-Interface für gemeinsame Zeichenfunktion
import java.awt.*; // AWT-Klassen für Punktkoordinaten und Graphics2D
import java.io.Serializable; // Serializable für Datei-Speicherung

// Klasse für Ellipsenobjekte, implementiert Shape und Serializable
public class Ellipse implements Shape, Serializable {
    // Startpunkt (linke obere Ecke des Begrenzungsrechtecks)
    private final Point start;
    // Endpunkt (rechte untere Ecke des Begrenzungsrechtecks)
    private final Point end;

    // Konstruktor: setzt Start- und Endpunkt
    public Ellipse(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    // Getter für Startpunkt
    public Point getStart() {
        return start;
    }
    // Getter für Endpunkt
    public Point getEnd() {
        return end;
    }

    @Override
    // Zeichnet die Ellipse basierend auf dem Begrenzungsrechteck
    public void draw(Graphics2D gEllipse) {
        // Breite berechnet aus Differenz der x-Koordinaten
        int width = end.x - start.x;
        // Höhe berechnet aus Differenz der y-Koordinaten
        int height = end.y - start.y;
        // Ellipse zeichnen
        gEllipse.drawOval(start.x, start.y, width, height);
    }
}
