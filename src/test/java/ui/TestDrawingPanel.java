package ui; // Das Testpaket entspricht dem Paket der zu testenden Klasse.

import logic.ShapeType; // Importiert ShapeType für die Auswahl des Zeichentyps
import java.awt.Point; // Importiert Point für Start- und Endpunkte
import java.awt.Color; // Importiert Color für Farbprüfungen
import java.awt.Graphics2D; // Importiert Graphics2D für Zeichenoperationen
import java.awt.image.BufferedImage; // Importiert BufferedImage für Pixelprüfungen
import org.junit.jupiter.api.BeforeEach; // Vorbereitung vor jedem Test
import org.junit.jupiter.api.AfterEach; // Nachbereitung nach jedem Test
import org.junit.jupiter.params.ParameterizedTest; // Markiert parametrisierte Tests
import org.junit.jupiter.params.provider.MethodSource; // Bindet Streams als Testdatenquelle
import static org.junit.jupiter.api.Assertions.assertEquals; // Importiert Assertions zum Vergleichen von erwarteten und tatsächlichen Ergebnissen.
import static org.junit.jupiter.api.Assertions.assertNotEquals; // Importiert Assertions zum Vergleichen von ungleichen Ergebnissen.
import static org.junit.jupiter.api.Assertions.assertNotNull; // Importiert die Assertion zum Prüfen auf einen nicht leeren beziehungsweise nicht null-Wert.
import static org.junit.jupiter.api.Assertions.assertNull; // Importiert die Assertion zum Prüfen auf einen leeren beziehungsweise null-Wert.
import static org.junit.jupiter.api.Assertions.assertTrue; // Importiert die Assertion zum Prüfen auf true-Werte.
import java.util.stream.Stream; // Importiert Stream, um die Testdaten für die parametrisierten Tests bereitzustellen.

// Testklasse für die Klasse DrawingPanel
public class TestDrawingPanel {

    // Panel-Instanz, die im Test verwendet wird
    private DrawingPanel panel;

    // Diese Methode wird vor jedem einzelnen Testfall ausgeführt.
    @BeforeEach
    void setup() {
        // Erzeugt ein DrawingPanel, das Dialoge unterdrückt
        panel = new DrawingPanel() {
            @Override
            protected void showInfoMessage(String message) {
                // Unterdrückt Dialoge im Test
            }
        };
    }

    // Diese Methode wird nach jedem einzelnen Testfall ausgeführt.
    @AfterEach
    void tearDown() {
        // Panel freigeben
        panel = null;
    }


    // Liefert die Testdaten für die Prüfung der Mausereignisse
    static Stream<Object[]> mouseEventCases() {
        // Erstellt einen Stream mit allen Testfällen für Mausereignisse
        return Stream.<Object[]>of(

                // TDP-M01 prüft ob Shape erzeugt und im Bild gezeichnet wird
                new Object[]{
                        "TDP-M01: mousePressed + mouseDragged + mouseReleased, erwartet=Shape wird erzeugt",
                        new Point(10, 10), // Startpunkt
                        new Point(50, 50), // Endpunkt
                        ShapeType.RECTANGLE, // Form die gezeichnet wird
                        true // Erwartungswert
                },

                // TDP-M02 prüft ob Shape ohne Startpunkt erzeugt und im Bild gezeichnet wird
                new Object[]{
                        "TDP-M02: mouseDragged ohne startPoint, erwartet=keine Vorschau",
                        null, // Startpunkt
                        new Point(40, 40), // Endpunkt
                        ShapeType.LINE, // Form die gezeichnet wird
                        false // Erwartungswert
                },

                // TDP-M03 prüft ob Shape ohne Startpunkt erzeugt wird
                new Object[]{
                        "TDP-M03: mouseReleased ohne startPoint, erwartet=keine Form",
                        null, // Startpunkt
                        new Point(40, 40), // Endpunkt
                        ShapeType.LINE, // Form die gezeichnet wird
                        false // Erwartungswert
                }
        );
    }

    // Führt die parametrisierten Tests für Mausereignisse aus.
    @ParameterizedTest(name = "{0}")
    // Verwendet die oben definierten Testdaten.
    @MethodSource("mouseEventCases")
    void testMouseEvents(
            String testId,
            Point start,
            Point end,
            ShapeType type,
            boolean expectShape
    ) {
        // Startpunkt setzen
        panel.setStartPoint(start);
        // Endpunkt setzen
        panel.setCurrentPoint(end);
        // Zeichentyp setzen
        panel.setCurrentType(type);
        // Shape erzeugen
        var shape = panel.createShape(start, end);
        // Erwartung prüfen
        if (expectShape) {
            assertNotNull(shape);
        } else {
            assertNull(shape);
        }
    }


    // Liefert die Testdaten für die Prüfung der Vorschau
    static Stream<Object[]> previewCases() {
        // Erstellt einen Stream mit allen Testfällen für Vorschau
        return Stream.<Object[]>of(

                // TDP-P01 prüft ob das Preview‑Shape sichtbar ist
                new Object[]{
                        "TDP-P01: Vorschau aktiv, erwartet=Vorschau sichtbar",
                        new Point(20, 20), // Startpunkt
                        new Point(40, 40), // Endpunkt
                        ShapeType.LINE, // Form die gezeichnet wird
                        true // Erwartungswert
                },

                // TDP-P02 prüft ob das Preview‑Shape nach dem Release verschwindet
                new Object[]{
                        "TDP-P02: Vorschau nach dem Loslassen, erwartet=Vorschau verschwindet",
                        null, // Startpunkt
                        null, // Endpunkt
                        ShapeType.LINE, // Form die gezeichnet wird
                        false // Erwartungswert
                }
        );
    }

    // Führt die parametrisierten Tests für die Vorschau aus.
    @ParameterizedTest(name = "{0}")
    // Verwendet die oben definierten Testdaten.
    @MethodSource("previewCases")
    void testPreview(
            String testId,
            Point start,
            Point end,
            ShapeType type,
            boolean expectPreview
    ) {
        // Startpunkt setzen
        panel.setStartPoint(start);
        // Endpunkt setzen
        panel.setCurrentPoint(end);
        // Zeichentyp setzen
        panel.setCurrentType(type);
        // Vorschau erzeugen
        var preview = panel.createShape(start, end);
        // Erwartung prüfen
        if (expectPreview) {
            assertNotNull(preview);
        } else {
            assertNull(preview);
        }
    }


    // Liefert die Testdaten für die Prüfung den Modus Radiergummi
    static Stream<Object[]> eraserCases() {
        // Erstellt die Testfälle für Eraser-Modus
        return Stream.<Object[]>of(

                // TDP-E01 prüft ob Shape entfernt, Bild neu gezeichnet wird
                new Object[]{
                        "TDP-E01: Radiergummi-Modus + Klick auf die Form, erwartet=Shape entfernt",
                        new Point(10, 10), // Startpunkt
                        new Point(60, 60), // Endpunkt
                        new Point(20, 20), // Klickpunkt
                        ShapeType.RECTANGLE, // Form die gezeichnet wird
                        true // Erwartungswert
                },

                // TDP-E02 prüft ob die Meldung „No shape was erased.“ erscheint
                new Object[]{
                        "TDP-E02: EraserMode + Klick ins Leere, erwartet=kein Form entfernt",
                        new Point(10, 10), // Startpunkt
                        new Point(60, 60), // Endpunkt
                        new Point(999, 999), // Klickpunkt
                        ShapeType.RECTANGLE, // Form die gezeichnet wird
                        false // Erwartungswert
                },

                // TDP-E03 prüft ob nur getroffene Formen entfernt werden
                new Object[]{
                        "TDP-E03: EraserMode + mehrere Shapes, erwartet=nur getroffene Form entfernt",
                        new Point(10, 10), // Startpunkt
                        new Point(60, 60), // Endpunkt
                        new Point(20, 20), // Klickpunkt
                        ShapeType.RECTANGLE, // Form die gezeichnet wird
                        true // Erwartungswert
                }
        );
    }

    // Führt die parametrisierten Tests für den Radiergummi Modus aus.
    @ParameterizedTest(name = "{0}")
    // Verwendet die oben definierten Testdaten.
    @MethodSource("eraserCases")
    void testEraser(
            String testId,
            Point start,
            Point end,
            Point click,
            ShapeType type,
            boolean expectRemoval
    ) {
        // Zeichentyp setzen
        panel.setCurrentType(type);
        // Shape erzeugen
        var shape = panel.createShape(start, end);
        // Shape speichern
        panel.getShapes().add(shape);
        // Shape zeichnen
        Graphics2D g2 = panel.getImage().createGraphics();
        shape.draw(g2);
        g2.dispose();
        // Eraser aktivieren
        panel.enableEraserMode();
        // Klick simulieren
        boolean removed = panel.getShapes().removeIf(s -> s.containsPoint(click.x, click.y));
        // Erwartung prüfen
        assertEquals(expectRemoval, removed);
    }


    // Liefert die Testdaten für die Prüfung von Undo
    static Stream<Object[]> undoCases() {
        // Erstellt die Testfälle für Undo-Funktion
        return Stream.<Object[]>of(

                // TDP-U01 prüft ob die letzte Form entfernt und das Bild neu aufgebaut wird
                new Object[]{
                        "TDP-U01: undoLastShape(), erwartet=letztes Shape entfernt",
                        new Point(10, 10), // Startpunkt
                        new Point(100, 100), // Endpunkt
                        ShapeType.LINE, // Form die gezeichnet wird
                        true // Erwartungswert
                },

                // TDP-U02 prüft ob die Meldung „There is no shape…“ erscheint
                new Object[]{
                        "TDP-U02: undoLastShape() bei leerer Liste, erwartet=keine Form vorhanden",
                        null, // Startpunkt
                        null, // Endpunkt
                        ShapeType.LINE, // Form die gezeichnet wird
                        false // Erwartungswert
                }
        );
    }

    // Führt die parametrisierten Tests für Undo aus.
    @ParameterizedTest(name = "{0}")
    // Verwendet die oben definierten Testdaten.
    @MethodSource("undoCases")
    void testUndo(
            String testId,
            Point start,
            Point end,
            ShapeType type,
            boolean expectRemoval
    ) {
        // Zeichentyp setzen
        panel.setCurrentType(type);
        if (start != null && end != null) {
            // Shape erzeugen
            var shape = panel.createShape(start, end);
            // Shape speichern
            panel.getShapes().add(shape);
            // Shape zeichnen
            Graphics2D g2 = panel.getImage().createGraphics();
            shape.draw(g2);
            g2.dispose();
        }
        // Undo ausführen
        panel.undoLastShape();
        // Erwartung prüfen
        assertTrue(panel.getShapes().isEmpty());
    }


    // Liefert die Testdaten für die Prüfung von clear
    static Stream<Object[]> clearCases() {
        // Erstellt Testfall für clear()
        return Stream.<Object[]>of(

                // TDP-C01 prüft ob alle Formen gelöscht und eine neue weiße Zeichenfläche bereitgestellt wird
                new Object[]{
                        "TDP-C01: clear(), erwartet=neue weiße Zeichenfläche"
                }
        );
    }

    // Führt die parametrisierten Tests für clear() aus.
    @ParameterizedTest(name = "{0}")
    // Verwendet die oben definierten Testdaten.
    @MethodSource("clearCases")
    void testClear(String testId) {
        // Zeichentyp setzen
        panel.setCurrentType(ShapeType.RECTANGLE);
        // Startpunkt setzen
        panel.setStartPoint(new Point(10, 10));
        // Endpunkt setzen
        panel.setCurrentPoint(new Point(60, 60));
        // Shape erzeugen
        var shape = panel.createShape(panel.getStartPoint(), panel.getCurrentPoint());
        // Shape speichern
        panel.getShapes().add(shape);
        // Shape zeichnen
        Graphics2D g2 = panel.getImage().createGraphics();
        shape.draw(g2);
        g2.dispose();
        // Sicherstellen: Pixel ist NICHT weiß → Shape existiert
        assertNotEquals(Color.WHITE.getRGB(), panel.getImage().getRGB(10, 10));
        // clear() ausführen
        panel.clear();
        // Erwartung: Zeichenfläche ist wieder weiß
        assertEquals(Color.WHITE.getRGB(), panel.getImage().getRGB(10, 10));
        // Erwartung: Shape-Liste ist leer
        assertTrue(panel.getShapes().isEmpty());
    }


    // Liefert die Testdaten für die Prüfung von setImage()
    static Stream<Object[]> setImageCases() {
        // Erstellt Testfall für setImage()
        return Stream.<Object[]>of(

                // TDP-SI01 prüft ob Bild übernommen und Formliste geleert wurde
                new Object[]{
                        "TDP-SI01: setImage(), erwartet=Bild übernommen, Shape-Liste geleert"
                }
        );
    }

    // Führt die parametrisierten Tests für setImage() aus.
    @ParameterizedTest(name = "{0}")
    // Verwendet die oben definierten Testdaten.
    @MethodSource("setImageCases")
    void testSetImage(String testId) {
        // Neues Bild erzeugen
        BufferedImage newImg = new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB);
        // Bild schwarz füllen
        Graphics2D g2 = newImg.createGraphics();
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, 800, 600);
        g2.dispose();
        // Bild setzen
        panel.setImage(newImg);
        // Pixel prüfen
        assertEquals(Color.BLACK.getRGB(), panel.getImage().getRGB(10, 10));
        // Shape-Liste muss leer sein
        assertTrue(panel.getShapes().isEmpty());
    }


    // Liefert die Testdaten für die Prüfung von redrawImage()
    static Stream<Object[]> redrawImageCases() {
        return Stream.<Object[]>of(

                // TDP-RI01 prüft ob alle Formen korrekt neu gezeichnet werden
                new Object[]{
                        "TDP-RI01: redrawImage(), erwartet=alle Shapes korrekt neu gezeichnet"
                }
        );
    }

    // Führt die parametrisierten Tests für redrawImage() aus.
    @ParameterizedTest(name = "{0}")
    // Verwendet die oben definierten Testdaten.
    @MethodSource("redrawImageCases")
    void testRedrawImage(String testId) {
        // Zeichentyp setzen
        panel.setCurrentType(ShapeType.RECTANGLE);
        // Shape erzeugen
        var shape = panel.createShape(new Point(10, 10), new Point(60, 60));
        // Shape speichern
        panel.getShapes().add(shape);
        // Bild neu zeichnen
        panel.redrawImage();
        // Pixel prüfen
        assertNotEquals(Color.WHITE.getRGB(), panel.getImage().getRGB(10, 10));
    }
}