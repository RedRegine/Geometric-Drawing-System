package model; // // Modell-Paket: enthält konkrete Shape-Implementierungen
import api.Shape; // Shape-Interface für gemeinsame Zeichenfunktion
import java.awt.*; // AWT-Klassen für Punktkoordinaten und Graphics2D
import java.io.Serializable; // Serializable für Datei-Speicherung

// Klasse für Linienobjekte, implementiert Shape und Serializable
public class Line implements Shape, Serializable {
    // Startpunkt der Linie
    private final Point start;
    // Endpunkt der Linie
    private final Point end;
    // Konstruktor: setzt Start- und Endpunkt
    public Line(Point start, Point end) {
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
    // Zeichnet die Linie mit Graphics2D
    public void draw(Graphics2D gLine) {
        gLine.drawLine(start.x, start.y, end.x, end.y);
    }
}

