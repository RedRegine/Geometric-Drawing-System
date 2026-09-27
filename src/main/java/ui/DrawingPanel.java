package ui; // UI-Paket: enthält alle Klassen der grafischen Oberfläche

import api.Shape; // Shape-Interface für alle Zeichenobjekte
import logic.ShapeType; // Enum für die Auswahl des aktuellen Zeichentyps

// Konkrete Implementierungen der Shapes (Formen)
import model.Ellipse;
import model.Line;
import model.Rectangle;

import javax.swing.*; // Swing-Komponenten (JPanel)
import java.awt.*; // AWT-Grafikklassen (Graphics, Point)
import java.awt.event.*; // Mausereignisse (MouseEvent, MouseAdapter)
import java.awt.image.BufferedImage; // Bildspeicher für die Zeichenfläche

// Listen für gespeicherte Shapes
import java.util.ArrayList;
import java.util.List;

// Zeichenpanel, das alle Formen rendert und Mausinteraktionen verarbeitet
public class DrawingPanel extends JPanel {

    // Liste aller dauerhaft gespeicherten Shapes
    private final List<Shape> shapes = new ArrayList<>();
    // Bild, auf dem tatsächlich gezeichnet wird
    private BufferedImage image;
    // Aktuell ausgewählter Zeichentyp (Standard: Linie)
    private ShapeType currentType = ShapeType.LINE;
    // Aktuelle Farbe (Standard: Schwarz)
    private Color currentColor = Color.BLACK;
    // Aktuelle Strichstärke (Standard: 1.0)
    private float currentStrokeWidth = 1.0f;
    // Startpunkt beim Drücken der Maus
    private Point startPoint = null;
    // Aktueller Punkt während des Ziehens
    private Point currentPoint = null;

    /* Extra Radiergummi: Form anklicken und entfernen
    Flag, ob der Radiergummi-Modus aktiv ist */
    private boolean eraserMode = false;

    // Konstruktor: richtet Hintergrund und Maussteuerung ein
    public DrawingPanel() {
        // Hintergrundfarbe des Zeichenbereichs
        setBackground(Color.WHITE);
        // Bild für die Zeichenfläche erzeugen
        image = new BufferedImage(
                800,
                600,
                BufferedImage.TYPE_INT_RGB
        );
        // Zeichenfläche mit weißem Hintergrund füllen
        Graphics2D g2 = image.createGraphics();
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, image.getWidth(), image.getHeight());
        g2.dispose();
        // Mausadapter verarbeitet Press, Drag und Release
        MouseAdapter mouseHandler = new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                // Startpunkt setzen, wenn Maus gedrückt wird beim zeichnen
                startPoint = e.getPoint();
                // Vorschau beginnt am Startpunkt beim zeichnen
                currentPoint = startPoint;
                // Extra-Funktion Radiergummi: Form anklicken und entfernen
                if (eraserMode) {
                    // Nachverfolgung ob wirklich Formen entfernt werden sollen
                    boolean removed = shapes.removeIf(
                            s -> s.containsPoint(e.getX(), e.getY())
                    );
                    if (removed) {
                        // Bild neu aufbauen
                        redrawImage();
                        // Repaint nur verwenden, wenn etwas wirklich entfernt wurde
                        repaint();
                        // Keine Form gefunden / getroffen
                    } else {
                        // Nachrichtenbox anzeigen
                        showInfoMessage("No shape was erased.");
                    }
                    // Solange Radiergummi-Modus aktiv ist kein Zeichnen starten
                    startPoint = null;
                    currentPoint = null;
                    return;
                }
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                // Aktuellen Punkt während des Ziehens aktualisieren
                currentPoint = e.getPoint();
                // Panel neu zeichnen, um Vorschau zu aktualisieren
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                // Beim Loslassen: Endpunkt vorhanden?
                if (startPoint != null && currentPoint != null) {
                    // Endgültiges Shape erzeugen
                    Shape finalShape = createShape(startPoint, currentPoint);
                    // Shape speichern, wenn gültig
                    if (finalShape != null) {
                        shapes.add(finalShape);
                        // Neues Shape auf das Bild zeichnen
                        Graphics2D g2 = image.createGraphics();
                        finalShape.draw(g2);
                        g2.dispose();
                    }
                }

                // Vorschau zurücksetzen
                startPoint = null;
                currentPoint = null;
                // Panel neu zeichnen
                repaint();
            }
        };

        // Mausereignisse registrieren
        addMouseListener(mouseHandler);
        addMouseMotionListener(mouseHandler);
    }


    // Erzeugt ein Shape basierend auf dem aktuellen Typ und den Punkten mit gültigen Werten
    private Shape createShape(Point start, Point end) {

        switch (currentType) {

            case LINE:
                return new Line(
                        start,
                        end,
                        currentColor,
                        currentStrokeWidth
                );

            case RECTANGLE:
                return new Rectangle(
                        start,
                        end,
                        currentColor,
                        currentStrokeWidth
                );

            case ELLIPSE:
                return new Ellipse(
                        start,
                        end,
                        currentColor,
                        currentStrokeWidth
                );

            default:
                return null;
        }
    }

    // Setzt den aktuellen Zeichentyp (über Menü oder Toolbar)
    public void setCurrentType(ShapeType type) {
        this.currentType = type;
        // Radiergummi Modus wieder schließen
        disableEraserMode();
    }

    @Override
    // Zeichnet das gespeicherte Bild sowie die Vorschau
    protected void paintComponent(Graphics g) {
        // Hintergrund und Standardverhalten von JPanel
        super.paintComponent(g);
        // Gespeichertes Bild anzeigen
        if (image != null) {
            g.drawImage(image, 0, 0, this);
        }
        // Graphics2D für moderne Zeichenfunktionen
        Graphics2D g2 = (Graphics2D) g.create();
        // Vorschau zeichnen, falls gerade gezeichnet wird
        if (startPoint != null && currentPoint != null) {
            // Temporäres Shape erzeugen
            Shape preview = createShape(startPoint, currentPoint);
            if (preview != null) {
                // Vorschau in Grau zeichnen
                g2.setColor(Color.GRAY);
                preview.draw(g2);
            }
        }
        // Graphics2D wieder freigeben
        g2.dispose();
    }

    // Zeichnet alle vorhandenen Shapes neu auf das Bild
    private void redrawImage() {
        // Graphics2D vom Bild holen
        Graphics2D g2 = image.createGraphics();
        // Bild komplett weiß machen
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, image.getWidth(), image.getHeight());
        // Alle Shapes erneut zeichnen
        for (Shape s : shapes) {
            s.draw(g2);
        }
        // Graphics2D wieder freigeben
        g2.dispose();
    }

    /* Extra-Menü Farbe setzen
    Extra aktuelle Farbe bestimmen */
    // Getter für aktuelle Farbe
    public Color getCurrentColor() {
        return currentColor;
    }
    // Setter für das Setzen der Farbe
    public void setCurrentColor(Color color) {
        if (color != null) {
            this.currentColor = color;
        }
    }
    // Getter für die aktuelle Strichstärke
    public float getCurrentStrokeWidth() {
        return currentStrokeWidth;
    }
    // Setter für die aktuelle Strichstärke
    public void setCurrentStrokeWidth(float strokeWidth) {
        if (strokeWidth > 0) {
            this.currentStrokeWidth = strokeWidth;
        }
    }

    /* Extra Radiergummi: Form anklicken und entfernen
    Flag, ob der Radiergummi-Modus aktiv ist */

    // Aktiviert den Radiergummi-Modus
    public void enableEraserMode() {
        eraserMode = true;
    }
    // Deaktiviert den Radiergummi-Modus (optional, z. B. beim Wechsel des Zeichentyps)
    public void disableEraserMode() {
        eraserMode = false;
    }
    // Extra Undo: Entfernt die letzte Form

    public void undoLastShape() {
        // Prüfen ob etwas gezeichnet wurde
        if (!shapes.isEmpty()) {
            shapes.remove(shapes.size() - 1);
            // Bild nach dem Entfernen neu aufbauen
            redrawImage();
            repaint();
            // Keine Zeichnung gefunden
        } else {
            // Nachrichtenbox anzeigen
            showInfoMessage("There is no shape in your painting.");
        }
    }

    // Gibt das aktuelle Bild zurück (für Speichern)
    public BufferedImage getImage() {
        return image;
    }

    // Setzt ein geladenes Bild als aktuelle Zeichenfläche
    public void setImage(BufferedImage loadedImage) {
        if (loadedImage != null) {
            // Geladenes Bild übernehmen
            image = loadedImage;
            // Alte Shape-Liste leeren
            shapes.clear();
            // Anzeige aktualisieren
            repaint();
        }
    }

    // Löscht alle Shapes und erstellt eine neue leere Zeichenfläche
    public void clear() {
        // Alle gespeicherten Shapes löschen
        shapes.clear();
        // Neue weiße Zeichenfläche erstellen
        image = new BufferedImage(
                800,
                600,
                BufferedImage.TYPE_INT_RGB
        );
        // Zeichenfläche weiß füllen
        Graphics2D g2 = image.createGraphics();
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, image.getWidth(), image.getHeight());
        g2.dispose();
        // Anzeige aktualisieren
        repaint();
    }

    // Zeigt eine Info-Nachricht an
    protected void showInfoMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Info", JOptionPane.INFORMATION_MESSAGE);
    }
}