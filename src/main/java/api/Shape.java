package api; // Shape-Interface für alle Zeichenobjekte
import java.awt.*; // AWT für Layouts (BorderLayout)

// Gemeinsame Schnittstelle für alle Zeichenobjekte
public interface Shape {
    // Zeichnet die Form
    void draw(Graphics2D g);
    // Prüft, ob ein Punkt innerhalb der Form liegt (für Radiergummi)
    boolean containsPoint(int x, int y);
    // Farbe der Form
    Color getColor();
    // Strichstärke der Form
    float getStrokeWidth();
}