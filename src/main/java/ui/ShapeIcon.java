package ui; // UI-Paket: enthält alle Klassen der grafischen Oberfläche

import javax.swing.Icon; // Importiert das Interface für Swing-Symbole
import java.awt.Component; // Importiert das Interface für Swing-Symbole
import java.awt.Graphics; // Grundlegende Zeichenfunktionen
import java.awt.Color; // Ermöglicht das Festlegen der Zeichenfarbe
import java.awt.Graphics2D; // Erweiterte Zeichenfunktionen für Java2D
import java.awt.BasicStroke; // Ermöglicht das Festlegen der Linienbreite
import java.awt.Rectangle; // Ermöglicht das Festlegen der Linienbreite
import java.awt.geom.Ellipse2D; // Wird zum Zeichnen einer Ellipse verwendet


// Klasse für die Symbole der Zeichenwerkzeuge
// Die Klasse implementiert das Swing-Interface Icon
public class ShapeIcon implements Icon {

    // Aufzählung der verschiedenen Formen, für die ein Symbol dargestellt werden kann
    public enum Type {
        LINE,
        RECTANGLE,
        ELLIPSE,
        UNDO,
        ERASER,
        COLOR,
        EXIT
    }

    // Speichert den Typ der Form, die durch dieses Icon dargestellt werden soll
    private final Type type;

    // Konstruktor der Klasse
    // Übernimmt den gewünschten Formtyp
    public ShapeIcon(Type type) {
        this.type = type;
    }

    // Gibt die Breite des Icons zurück
    @Override
    public int getIconWidth() {
        return 24;
    }

    // Gibt die Höhe des Icons zurück
    @Override
    public int getIconHeight() {
        return 24;
    }

    // Methode die von Swing aufgerufen wird, wenn das Icon gezeichnet werden soll
    @Override
    public void paintIcon(
            Component c,
            Graphics g,
            int x,
            int y) {

        // Erstellt eine Kopie des Graphics-Objekts, damit die ursprünglichen Grafikeinstellungen nicht verändert werden
        Graphics2D g2 = (Graphics2D) g.create();
        // Setzt die Farbe des Symbols auf Schwarz
        g2.setColor(Color.BLACK);
        // Legt die Stärke der gezeichneten Linien auf 2 Pixel fest
        g2.setStroke(new BasicStroke(2));
        // Abstand des Symbols vom Rand des Icons
        int padding = 4;
        // Breite der eigentlichen Form
        int width = 16;
        // Höhe der eigentlichen Form
        int height = 16;
        // Prüft, welcher Formtyp für dieses Icon ausgewählt wurde
        // Prüft, welches Symbol für diesen Button dargestellt werden soll
        switch (type) {
            // Symbol für eine Linie
            case LINE:
                // Zeichnet eine diagonale Linie
                g2.drawLine(
                        x + padding,
                        y + padding + height,
                        x + padding + width,
                        y + padding
                );
                break;

            // Symbol für ein Rechteck
            case RECTANGLE:
                // Zeichnet ein Rechteck
                g2.draw(new Rectangle(
                        x + padding,
                        y + padding,
                        width,
                        height
                ));
                break;

            // Symbol für eine Ellipse
            case ELLIPSE:
                // Zeichnet eine Ellipse
                g2.draw(new Ellipse2D.Double(
                        x + padding,
                        y + padding,
                        width,
                        height
                ));
                break;

            // Symbol für Undo
            case UNDO:
                // Zeichnet einen gebogenen Pfeil für die Rückgängig-Funktion
                g2.drawArc(
                        x + 5,
                        y + 5,
                        14,
                        14,
                        45,
                        270
                );
                // Zeichnet die Pfeilspitze
                g2.drawLine(
                        x + 5,
                        y + 12,
                        x + 5,
                        y + 5
                );
                g2.drawLine(
                        x + 5,
                        y + 5,
                        x + 12,
                        y + 5
                );
                break;

            // Symbol für den Radiergummi
            case ERASER:
                // Zeichnet den Körper des Radiergummis
                g2.drawRoundRect(
                        x + 5,
                        y + 7,
                        14,
                        9,
                        2,
                        2
                );
                // Zeichnet eine diagonale Linie über den Radiergummi
                g2.drawLine(
                        x + 8,
                        y + 7,
                        x + 17,
                        y + 16
                );
                break;

            // Symbol für die Farbauswahl
            case COLOR:
                // Zeichnet einen Kreis als Symbol für die Farbauswahl
                g2.drawOval(
                        x + 5,
                        y + 5,
                        14,
                        14
                );
                // Zeichnet einen kleinen inneren Punkt
                g2.fillOval(
                        x + 10,
                        y + 10,
                        4,
                        4
                );
                break;

            // Symbol zum Beenden des Programms
            case EXIT:
                // Zeichnet eine Linie von links oben nach rechts unten
                g2.drawLine(
                        x + 6,
                        y + 6,
                        x + 18,
                        y + 18
                );
                // Zeichnet eine Linie von rechts oben nach links unten
                g2.drawLine(
                        x + 18,
                        y + 6,
                        x + 6,
                        y + 18
                );
                break;

        }
        // Gibt die erzeugte Graphics-Kopie wieder frei
        g2.dispose();
    }
}