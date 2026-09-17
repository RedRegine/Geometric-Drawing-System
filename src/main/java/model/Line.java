package model; // Modell-Paket: enthält konkrete Shape-Implementierungen
import api.Shape; // Shape-Interface für gemeinsame Zeichenfunktion
import java.awt.*; // AWT-Klassen für Punktkoordinaten und Graphics2D
import java.io.Serializable; // Serializable für Datei-Speicherung

// Klasse für Linienobjekte, implementiert Shape und Serializable
public class Line implements Shape, Serializable {
    // Startpunkt der Linie
    private final Point start;
    // Endpunkt der Linie
    private final Point end;
    // Farbe der Linie
    private final Color color;
    // Strichstärke der Linie
    private final float strokeWidth;

    // alter Konstruktor: setzt Start- und Endpunkt
    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    // neuer Konstruktor: Start- und Endpunkt, Strichfarbe und Strichstärke
    public Line(Point start, Point end, Color color, float strokeWidth) {
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

    @Override
    // Zeichnet die Linie mit Graphics2D
    public void draw(Graphics2D gLine) {
        // Aktuelle Einstellungen sichern
        Color oldColor = gLine.getColor();
        Stroke oldStroke = gLine.getStroke();

        // Farbe und Strichstärke setzen
        gLine.setColor(color);
        gLine.setStroke(new BasicStroke(strokeWidth));

        // Linie zeichnen
        gLine.drawLine(start.x, start.y, end.x, end.y);

        // Einstellungen wiederherstellen
        gLine.setColor(oldColor);
        gLine.setStroke(oldStroke);
    }

    @Override
    // kürzester Abstand zwischen dem tatsächlich gezeichnetem Segment berechnen
    public boolean containsPoint(int x, int y) {
        /* Methode aus Java AWT nutzen mit ptSegDist(...)
        minimalen Abstand eines Punktes zu einem Liniensegment */
        double distance = java.awt.geom.Line2D.ptSegDist(
                // Startpunkt der Linie
                start.x, start.y,
                // Endpunkt der Linie
                end.x, end.y,
                // Angeklickter Punkt
                x, y
        );
        // Toleranz für Klick (Linien sind sehr dünn, aber auch abhängig von der Strichstärke
        return distance <= Math.max(5.0, strokeWidth / 2.0 + 3.0);
    }
}

