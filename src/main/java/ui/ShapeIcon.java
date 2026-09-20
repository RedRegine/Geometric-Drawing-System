package ui; // UI-Paket: enthält alle Klassen der grafischen Oberfläche

import javax.swing.Icon; // Importiert das Interface für Swing-Symbole
import java.awt.Component; // Importiert die Komponente, auf der das Icon dargestellt wird
import java.awt.Graphics; // Grundlegende Zeichenfunktionen
import java.awt.Color; // Ermöglicht das Festlegen der Zeichenfarbe
import java.awt.Graphics2D; // Erweiterte Zeichenfunktionen für Java2D
import java.awt.BasicStroke; // Ermöglicht das Festlegen der Linienbreite und Linienenden
import java.awt.geom.Rectangle2D; // Ermöglicht das Zeichnen eines Rechtecks
import java.awt.geom.Ellipse2D; // Ermöglicht das Zeichnen einer Ellipse
import java.awt.geom.Path2D; // Ermöglicht das Zeichnen eines Pfades für komplexere Symbole
import java.awt.geom.AffineTransform; // Ermöglicht das Drehen einzelner Zeichenoperationen

// Klasse für die Symbole der Zeichenwerkzeuge
// Die Klasse implementiert das Swing-Interface Icon
public class ShapeIcon implements Icon {

    // Aufzählung der verschiedenen Symbole
    public enum Type {
        LINE,
        RECTANGLE,
        ELLIPSE,
        ERASER,
        COLOR,
        EXIT
    }

    // Speichert den Typ des darzustellenden Symbols
    private final Type type;

    // Konstruktor der Klasse
    // Übernimmt den gewünschten Symboltyp
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
    public void paintIcon(Component c, Graphics g, int x, int y) {
        // Erstellt eine Kopie des Graphics-Objekts, damit die ursprünglichen Grafikeinstellungen nicht verändert werden
        Graphics2D g2 = (Graphics2D) g.create();
        // Aktiviert Kantenglättung für ein sauberes und professionelles Erscheinungsbild
        g2.setRenderingHint( java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON );
        // Setzt die Farbe des Symbols auf Schwarz
        g2.setColor(Color.BLACK);
        // Legt die Stärke der gezeichneten Linien auf 2 Pixel fest
        // ROUND sorgt für abgerundete Linienenden & weichere Übergänge bei den Symbolen
        g2.setStroke(new BasicStroke( 2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND ));
        // Mittelpunkt des 24x24 Icons
        int centerX = x + 12;
        int centerY = y + 12;

        // Prüft, welches Symbol für diesen Button dargestellt werden soll
        switch (type) {

            // Symbol für Linie
            case LINE:
                // Zeichnet eine diagonale Linie
                // Die Linie ist bewusst etwas kleiner als das gesamte Icon, sonst liegt sie direkt am Rand
                g2.drawLine( x + 5, y + 19, x + 19, y + 5 );
                break;

            // Symbol für Rechteck
            case RECTANGLE:
                // Durch die Verwendung von Rectangle2D können halbe Pixel vermieden und die Darstellung sauber gehalten werden
                g2.draw(new Rectangle2D.Double( x + 5, y + 5, 14, 14 ));
                break;

            // Symbol für Ellipse
            case ELLIPSE:
                // Zeichnet eine Ellipse mit derselben Außenabmessung (wie Rechteck)
                g2.draw(new Ellipse2D.Double( x + 5, y + 5, 14, 14 ));
                break;


            // Symbol für Radiergummi
            case ERASER:
                // Speichert die aktuelle Transformation
                AffineTransform oldTransform = g2.getTransform();
                // Verschiebt den Ursprung in die Mitte des Icons
                g2.translate(centerX, centerY);
                // Dreht den Radiergummi leicht diagonal, die Darstellung gleicht dadurch mehr bekannten Zeichenprogrammen
                g2.rotate(Math.toRadians(-45));
                // Zeichnet den äußeren Körper des Radiergummis
                g2.drawRoundRect( -8, -5, 16, 10, 3, 3 );
                // Zeichnet eine Trennlinie im Radiergummi
                g2.drawLine( 1, -5, 1, 5 );
                // Stellt die ursprüngliche Transformation wieder her
                g2.setTransform(oldTransform);
                break;

            // Symbol für Farbauswahl
            case COLOR:
                // Zeichnet eine kleine Farbpalette
                Path2D palette = new Path2D.Double();
                // Startpunkt der Palette
                palette.moveTo( x + 7, y + 15 );
                // Linke Seite der Palette
                palette.curveTo( x + 4, y + 12, x + 5, y + 8, x + 8, y + 6 );
                // Oberer Bereich der Palette
                palette.curveTo( x + 12, y + 3, x + 18, y + 5, x + 19, y + 9 );
                // Rechter Bereich der Palette
                palette.curveTo( x + 20, y + 13, x + 17, y + 16, x + 14, y + 17 );
                // Unterer Bereich der Palette
                palette.curveTo( x + 12, y + 18, x + 10, y + 17, x + 8, y + 16 );
                // Schließt die Palette
                palette.closePath();
                // Zeichnet die Außenkontur der Palette
                g2.draw(palette);
                // Zeichnet kleine Farbpunkte auf der Palette
                g2.fillOval( x + 8, y + 8, 3, 3 );
                // Zeichnet zweiten Farbpunkt
                g2.fillOval( x + 13, y + 6, 3, 3 );
                // Zeichnet dritten Farbpunkt
                g2.fillOval( x + 15, y + 11, 3, 3 );
                // Zeichnet vierten Farbpunkt
                g2.fillOval( x + 10, y + 12, 3, 3 );
                break;

            // Symbol für Beenden
            case EXIT:
                // Zeichnet den ersten diagonalen Strich des X
                g2.drawLine( x + 6, y + 6, x + 18, y + 18 );
                // Zeichnet den zweiten diagonalen Strich des X
                g2.drawLine( x + 18, y + 6, x + 6, y + 18 );
                break;

        }
        // Gibt die erzeugte Graphics-Kopie wieder frei
        g2.dispose();
    }
}