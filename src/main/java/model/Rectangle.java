package model; // // Modell-Paket: enthält konkrete Shape-Implementierungen
import api.Shape; // Shape-Interface für gemeinsame Zeichenfunktion
import java.awt.*; // AWT-Klassen für Punktkoordinaten und Graphics2D
import java.io.Serializable; // Serializable für Datei-Speicherung

// Klasse für Rechteckobjekte, implementiert Shape und Serializable
public class Rectangle implements Shape, Serializable {
    // Startpunkt (linke obere Ecke)
    private final Point start;
    // Endpunkt (rechte untere Ecke)
    private final Point end;

    // Konstruktor: setzt Start- und Endpunkt
    public Rectangle(Point start, Point end) {
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
    // Zeichnet das Rechteck basierend auf Start- und Endpunkt
    public void draw(Graphics2D gRectangle) {
        // Breite berechnet aus Differenz der x-Koordinaten
        int width = end.x - start.x;
        // Höhe berechnet aus Differenz der y-Koordinaten
        int height = end.y - start.y;
        // Rechteck zeichnen
        gRectangle.drawRect(start.x, start.y, width, height);
    }
}
