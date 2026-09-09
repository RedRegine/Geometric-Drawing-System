package api; // API-Paket: enthält gemeinsame Schnittstellen für alle Zeichenobjekte
import java.awt.*; // AWT-Grafikklassen, insbesondere Graphics2D für das Zeichnen

// Shape definiert die gemeinsame Zeichenfunktion für alle Formen
public interface Shape {
    // Jede konkrete Form muss eine draw-Methode implementieren,
    // die sich selbst mit Graphics2D zeichnet
    public void draw(Graphics2D g);
}
