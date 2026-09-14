package model; // Modell-Paket: enthält konkrete Shape-Implementierungen
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

    @Override
    public boolean containsPoint(int x, int y) {
        // Bestimme die kleinste und größte X-Koordinate des Rechtecks.
        // Da Start- und Endpunkt beliebig gesetzt sein können (z. B. von rechts nach links),
        // müssen die Werte normalisiert werden.
        int minX = Math.min(start.x, end.x);
        int maxX = Math.max(start.x, end.x);

        // Bestimme die kleinste und größte Y-Koordinate des Rechtecks.
        int minY = Math.min(start.y, end.y);
        int maxY = Math.max(start.y, end.y);

        // Prüft, ob der angeklickte Punkt (x, y) innerhalb des Rechtecks liegt.
        // Ein Punkt liegt im Rechteck, wenn:
        // - seine X-Koordinate zwischen minX und maxX liegt
        // - seine Y-Koordinate zwischen minY und maxY liegt
        return x >= minX && x <= maxX && y >= minY && y <= maxY;
    }
}


