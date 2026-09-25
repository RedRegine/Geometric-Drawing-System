package model; // Das Testpaket entspricht dem Paket der zu testenden Klasse.

import org.junit.jupiter.api.AfterEach; // Importiert die JUnit-Annotation für die Nachbereitung nach jedem Test.
import org.junit.jupiter.api.Assertions; // Importiert die JUnit-Assertions für die Testüberprüfungen.
import org.junit.jupiter.api.BeforeEach; // Importiert die JUnit-Annotation für die Vorbereitung vor jedem Test.
import org.junit.jupiter.params.ParameterizedTest; // Importiert die Annotation für parametrisierte Tests.
import org.junit.jupiter.params.provider.MethodSource; // Importiert die Annotation für Testdaten aus Methoden.
import java.awt.BasicStroke; // Importiert BasicStroke für die Prüfung der Strichstärke.
import java.awt.Color; // Importiert Color für die Farbprüfung.
import java.awt.Graphics2D; // Importiert Graphics2D für die draw()-Tests.
import java.awt.Point; // Importiert Point für Start- und Endpunkte.
import java.awt.Stroke; // Importiert Stroke für die Prüfung des Graphics-Zustands.
import java.awt.image.BufferedImage; // Importiert BufferedImage als Zeichenfläche.

import java.util.stream.Stream; // Importiert Stream für die Bereitstellung der Testdaten.

// Testklasse für die Klasse Ellipse.
public class TestEllipse {

    // Speichert eine Ellipse für die einzelnen Testfälle.
    private Ellipse ellipse;
    // Speichert die Graphics2D-Instanz für die draw()-Tests.
    private Graphics2D graphics;
    // Speichert das Bild, auf dem die Ellipse gezeichnet wird.
    private BufferedImage image;

    // Diese Methode wird vor jedem einzelnen Testfall ausgeführt.
    @BeforeEach
    void setUp() {
        // Erstellt ein neues Bild als Zeichenfläche.
        image = new BufferedImage(
                150,
                100,
                BufferedImage.TYPE_INT_ARGB
        );
        // Erstellt eine Graphics2D-Instanz für das Bild.
        graphics = image.createGraphics();
    }

    // Diese Methode wird nach jedem einzelnen Testfall ausgeführt.
    @AfterEach
    void tearDown() {
        // Gibt die Graphics2D-Ressourcen nach dem Test frei.
        graphics.dispose();
    }


    // Liefert die Testdaten für die Prüfung der Konstruktoren und Getter.
    static Stream<Object[]> constructorAndGetterCases() {
        // Erstellt einen Stream mit allen Testfällen für Konstruktoren und Getter.
        return Stream.<Object[]>of(

                // TE-KG01 prüft den alten Konstruktor mit den Standardwerten.
                new Object[]{
                        "TE-KG01: start=(0,0), end=(10,10), Standardfarbe=BLACK, Standard strokeWidth=1.0f",
                        new Point(0, 0), // Startpunkt
                        new Point(10, 10), // Endpunkt
                        Color.BLACK, // erwartete Farbe
                        1.0f, // erwartete Strichstärke
                        false // alter Konstruktor
                },

                // TE-KG02 prüft den Konstruktor mit eigener Farbe und eigener Strichstärke.
                new Object[]{
                        "TE-KG02: start=(0,0), end=(10,10), Standardfarbe=RED, Standard strokeWidth=2.0f",
                        new Point(0, 0), // Startpunkt
                        new Point(10, 10), // Endpunkt
                        Color.RED, // inputFarbe
                        2.0f, // inputStrichstärke
                        true // neuer Konstruktor
                },

                // TE-KG03 prüft den Konstruktor mit den expliziten Standardwerten.
                new Object[]{
                        "TE-KG03: start=(0,0), end=(10,10), Standardfarbe=BLACK, Standard strokeWidth=1.0f",
                        new Point(0, 0), // Startpunkt
                        new Point(10, 10), // Endpunkt
                        Color.BLACK, // inputFarbe
                        1.0f, // inputStrichstärke
                        true // neuer Konstruktor
                }
        );
    }

    // Führt die parametrisierten Tests für Konstruktoren und Getter aus.
    @ParameterizedTest(name = "{0}")
    // Verwendet die oben definierte Methode als Quelle für die Testdaten.
    @MethodSource("constructorAndGetterCases")
    void testConstructorAndGetters(
            String testId,
            Point start,
            Point end,
            Color expectedColor,
            float expectedStrokeWidth,
            boolean useExtendedConstructor) {

        // Erstellt die Ellipse mit dem alten Konstruktor.
        if (!useExtendedConstructor) {
            ellipse = new Ellipse(start, end);
        }

        // Erstellt die Ellipse mit dem erweiterten Konstruktor.
        else {
            ellipse = new Ellipse(
                    start,
                    end,
                    expectedColor,
                    expectedStrokeWidth
            );
        }

        // Überprüft den gespeicherten Startpunkt.
        Assertions.assertEquals(start, ellipse.getStart());
        // Überprüft den gespeicherten Endpunkt.
        Assertions.assertEquals(end, ellipse.getEnd());
        // Überprüft die gespeicherte Farbe.
        Assertions.assertEquals(expectedColor, ellipse.getColor());
        // Überprüft die gespeicherte Strichstärke.
        Assertions.assertEquals(expectedStrokeWidth, ellipse.getStrokeWidth());
    }


    // Liefert die Testdaten für die Prüfung von containsPoint() bei normaler Koordinatenrichtung.
    static Stream<Object[]> containsPointCases() {
        // Erstellt einen Stream mit gültigen und ungültigen Punkten.
        return Stream.<Object[]>of(

                // TE-P01 prüft Mittelpunkt der Ellipse.
                new Object[]{
                        "TE-P01: start=(0,0), end=(100,60), Punkt=(50,30), erwartet=true",
                        new Point(0, 0), // Startpunkt
                        new Point(100, 60), // Endpunkt
                        50, 30, // Punkt X, Punkt Y
                        true // gültig = true | ungültig = false
                },

                // TE-P02 prüft Punkt innerhalb der Ellipse.
                new Object[]{
                        "TE-P02: start=(0,0), end=(100,60), Punkt=(50,15), erwartet=true",
                        new Point(0, 0), // Startpunkt
                        new Point(100, 60), // Endpunkt
                        50, 15, // Punkt X, Punkt Y
                        true // gültig = true | ungültig = false
                },

                // TE-P03 prüft Punkt innerhalb der Ellipse.
                new Object[]{
                        "TE-P03: start=(0,0), end=(100,60), Punkt=(25,30), erwartet=true",
                        new Point(0, 0), // Startpunkt
                        new Point(100, 60), // Endpunkt
                        25, 30, // Punkt X, Punkt Y
                        true // gültig = true | ungültig = false
                },

                // TE-P04 prüft Punkt innerhalb der Ellipse.
                new Object[]{
                        "TE-P04: start=(0,0), end=(100,60), Punkt=(75,30), erwartet=true",
                        new Point(0, 0), // Startpunkt
                        new Point(100, 60), // Endpunkt
                        75, 30, // Punkt X, Punkt Y
                        true // gültig = true | ungültig = false
                },

                // TE-P05 prüft Linker Randpunkt der Ellipse.
                new Object[]{
                        "TE-P05: start=(0,0), end=(100,60), Punkt=(0,30), erwartet=true",
                        new Point(0, 0), // Startpunkt
                        new Point(100, 60), // Endpunkt
                        0, 30, // Punkt X, Punkt Y
                        true // gültig = true | ungültig = false
                },

                // TE-P06 prüft Rechter Randpunkt der Ellipse.
                new Object[]{
                        "TE-P06: start=(0,0), end=(100,60), Punkt=(100,30), erwartet=true",
                        new Point(0, 0), // Startpunkt
                        new Point(100, 60), // Endpunkt
                        100, 30, // Punkt X, Punkt Y
                        true // gültig = true | ungültig = false
                },

                // TE-P07 prüft Oberer Randpunkt der Ellipse.
                new Object[]{
                        "TE-P07: start=(0,0), end=(100,60), Punkt=(50,0), erwartet=true",
                        new Point(0, 0), // Startpunkt
                        new Point(100, 60), // Endpunkt
                        50, 0, // Punkt X, Punkt Y
                        true // gültig = true | ungültig = false
                },

                // TE-P08 prüft Unterer Randpunkt der Ellipse.
                new Object[]{
                        "TE-P08: start=(0,0), end=(100,60), Punkt=(50,60), erwartet=true",
                        new Point(0, 0), // Startpunkt
                        new Point(100, 60), // Endpunkt
                        50, 60, // Punkt X, Punkt Y
                        true // gültig = true | ungültig = false
                },

                // TE-P09 prüft Obere linke Ecke des Begrenzungsrechtecks.
                new Object[]{
                        "TE-P09: start=(0,0), end=(100,60), Punkt=(0,0), erwartet=false",
                        new Point(0, 0), // Startpunkt
                        new Point(100, 60), // Endpunkt
                        0, 0, // Punkt X, Punkt Y
                        false // gültig = true | ungültig = false
                },

                // TE-P10 prüft Untere rechte Ecke des Begrenzungsrechtecks.
                new Object[]{
                        "TE-P10: start=(0,0), end=(100,60), Punkt=(100,60), erwartet=false",
                        new Point(0, 0), // Startpunkt
                        new Point(100, 60), // Endpunkt
                        100, 60, // Punkt X, Punkt Y
                        false // gültig = true | ungültig = false
                },

                // TE-P11 prüft Punkt außerhalb der Ellipse.
                new Object[]{
                        "TE-P11: start=(0,0), end=(100,60), Punkt=(-1,30), erwartet=false",
                        new Point(0, 0), // Startpunkt
                        new Point(100, 60), // Endpunkt
                        -1, 30, // Punkt X, Punkt Y
                        false // gültig = true | ungültig = false
                }
        );
    }

    // Führt die parametrisierten Tests für containsPoint() aus.
    @ParameterizedTest(name = "{0}")
    // Verwendet die oben definierten Testdaten.
    @MethodSource("containsPointCases")
    void testContainsPoint(
            String testId,
            Point start,
            Point end,
            int x,
            int y,
            boolean expected) {
        // Erstellt die zu testende Ellipse mit Standardfarbe und Standard-Strichstärke.
        ellipse = new Ellipse(start, end);
        // Prüft, ob containsPoint() das erwartete Ergebnis liefert.
        Assertions.assertEquals(
                expected,
                ellipse.containsPoint(x, y)
        );
    }


    // Liefert die Testdaten für Ellipse mit umgekehrter Koordinatenrichtung.
    static Stream<Object[]> reversedContainsPointCases() {
        // Erstellt einen Stream mit Punkten für die Prüfung der Koordinatennormalisierung.
        return Stream.<Object[]>of(

                // TE-P12 prüft Mittelpunkt der umgekehrten Ellipse.
                new Object[]{
                        "TE-P12: start=(100,60), end=(0,0), Punkt=(50,30), erwartet=true",
                        new Point(100, 60), // Startpunkt
                        new Point(0, 0), // Endpunkt
                        50, 30, // Punkt X, Punkt Y
                        true // gültig = true | ungültig = false
                },

                // TE-P13: Punkt innerhalb der Ellipse.
                new Object[]{
                        "TE-P13: start=(100,60), end=(0,0), Punkt=(50,15), erwartet=true",
                        new Point(100, 60), // Startpunkt
                        new Point(0, 0), // Endpunkt
                        50, 15, // Punkt X, Punkt Y
                        true // gültig = true | ungültig = false
                },

                // TE-P14: Linker Randpunkt der Ellipse.
                new Object[]{
                        "TE-P14: start=(100,60), end=(0,0), Punkt=(0,30), erwartet=true",
                        new Point(100, 60), // Startpunkt
                        new Point(0, 0), // Endpunkt
                        0, 30, // Punkt X, Punkt Y
                        true // gültig = true | ungültig = false
                },

                // TE-P15: Rechter Randpunkt der Ellipse.
                new Object[]{
                        "TE-P15: start=(100,60), end=(0,0), Punkt=(100,30), erwartet=true",
                        new Point(100, 60), // Startpunkt
                        new Point(0, 0), // Endpunkt
                        100, 30, // Punkt X, Punkt Y
                        true // gültig = true | ungültig = false
                },

                // TE-P16: Punkt außerhalb der Ellipse.
                new Object[]{
                        "TE-P16: start=(100,60), end=(0,0), Punkt=(101,30), erwartet=false",
                        new Point(100, 60), // Startpunkt
                        new Point(0, 0), // Endpunkt
                        101, 30, // Punkt X, Punkt Y
                        false // gültig = true | ungültig = false
                }
        );
    }

    // Führt die parametrisierten Tests für umgekehrte Koordinaten aus.
    @ParameterizedTest(name = "{0}")
    // Verwendet die Testdaten aus reversedContainsPointCases().
    @MethodSource("reversedContainsPointCases")
    void testContainsPointWithReversedCoordinates(
            String testId,
            Point start,
            Point end,
            int x,
            int y,
            boolean expected) {

        // Erstellt die Ellipse mit umgekehrten Koordinaten.
        ellipse = new Ellipse(start, end);

        // Prüft, ob reverseContainsPoint() das erwartete Ergebnis liefert.
        Assertions.assertEquals(
                expected,
                ellipse.containsPoint(x, y)
        );
    }


    // Liefert die Testdaten für Breite oder Höhe 0.
    static Stream<Object[]> containsPointZeroDimensionCases() {
        // Gibt die definierten Testfälle TE-P17 und TE-P18 zurück.
        return Stream.<Object[]>of(

                // TE-P17 prüft Breite ist 0.
                new Object[]{
                        "TE-P17: start=(10,10), end=(10,50), Punkt=(10,30), erwartet=false",
                        new Point(10, 10), // Startpunkt
                        new Point(10, 50), // Endpunkt
                        10, 30 // Punkt X, Punkt Y
                },

                // TE-P18 prüft Höhe ist 0.
                new Object[]{
                        "TE-P18: start=(10,10), end=(50,10), Punkt=(30,10), erwartet=false",
                        new Point(10, 10), // Startpunkt
                        new Point(50, 10), // Endpunkt
                        30, 10 // Punkt X, Punkt Y
                }
        );
    }

    // Prüft die Sicherheitsprüfung bei Breite oder Höhe 0.
    @ParameterizedTest(name = "{0}")
    // Verwendet die Testdaten aus reversedContainsPointCases().
    @MethodSource("containsPointZeroDimensionCases")
    void testContainsPointZeroDimension(
            String testId,
            Point start,
            Point end,
            int x,
            int y) {
        // Erstellt die Ellipse mit einer Dimension von 0.
        ellipse = new Ellipse(start, end);
        // Bei Breite oder Höhe 0 muss containsPoint() false liefern.
        Assertions.assertFalse(
                ellipse.containsPoint(x, y)
        );
    }


    // Liefert die Testdaten für die draw()-Tests.
    static Stream<Object[]> drawCases() {
        // Gibt die definierten Testfälle TE-D01 bis TE-D03 zurück.
        return Stream.<Object[]>of(

                // TE-D01 prüft Schwarze Ellipse mit Strichstärke 1.0f.
                new Object[]{
                        "TE-D01: start=(5,5), end=(20,20), color=BLACK, strokeWidth=1.0f",
                        new Point(5, 5), // Startpunkt
                        new Point(20, 20), // Endpunkt
                        Color.BLACK, // Farbe
                        1.0f // Linienstärke
                },

                // TE-D02 prüft Rote Ellipse mit Strichstärke 3.0f.
                new Object[]{
                        "TE-D02: start=(5,5), end=(20,20), color=RED, strokeWidth=3.0f",
                        new Point(5, 5), // Startpunkt
                        new Point(20, 20), // Endpunkt
                        Color.RED, // Farbe
                        3.0f // Linienstärke
                },

                // TE-D03 prüft Blaue Ellipse mit Strichstärke 2.0f.
                new Object[]{
                        "TE-D03: start=(10,5), end=(40,30), color=BLUE, strokeWidth=2.0f",
                        new Point(10, 5), // Startpunkt
                        new Point(40, 30), // Endpunkt
                        Color.BLUE, // Farbe
                        2.0f // Linienstärke
                }
        );
    }

    // Führt die parametrisierten Tests für die draw()-Methode aus.
    @ParameterizedTest(name = "{0}")
    // Verwendet die oben definierten Zeichen-Testdaten.
    @MethodSource("drawCases")
    void testDraw(
            String testId,
            Point start,
            Point end,
            Color color,
            float strokeWidth) {
        // Erstellt die Ellipse mit den angegebenen Eigenschaften.
        ellipse = new Ellipse(
                start,
                end,
                color,
                strokeWidth
        );
        // Zeichnet die Ellipse auf die Testfläche.
        ellipse.draw(graphics);
        // Zählt die Pixel mit der erwarteten Farbe.
        int matchingPixels = countColorPixels(
                image,
                color
        );
        // Prüft, ob mindestens ein Pixel mit der erwarteten Farbe vorhanden ist.
        Assertions.assertTrue(
                matchingPixels > 0,
                "Die Ellipse wurde nicht mit der erwarteten Farbe gezeichnet."
        );
    }


    // Liefert die Testdaten für die Prüfung des Graphics2D-Zustands.
    static Stream<Object[]> graphicsStateCases() {
        // Gibt den definierten Testfall TE-D04 zurück.
        return Stream.<Object[]>of(

                // TE-D04: Die Ellipse verwendet Blau und 2.0f, der vorherige Graphics2D-Zustand ist Grün und 5.0f.
                new Object[]{
                        "TE-D04 (BLUE,2.0f,GREEN,5.0f)",
                        Color.BLUE, // verwendete Farbe
                        2.0f, // verwendete Linienstärke
                        Color.GREEN, // vorherige Farbe
                        5.0f // vorherige Linienstärke
                }
        );
    }

    // Prüft, ob draw() den vorherigen Graphics2D-Zustand wiederherstellt.
    @ParameterizedTest(name = "{0}")
    // Verwendet die Testdaten aus graphicsStateCases().
    @MethodSource("graphicsStateCases")
    void testDrawRestoresGraphicsState(
            String testId,
            Color ellipseColor,
            float ellipseStrokeWidth,
            Color previousColor,
            float previousStrokeWidth) {
        // Erstellt die Ellipse mit den zu testenden Eigenschaften.
        ellipse = new Ellipse(
                new Point(5, 5),
                new Point(20, 20),
                ellipseColor,
                ellipseStrokeWidth
        );
        // Setzt die vorherige Farbe des Graphics2D-Objekts.
        graphics.setColor(previousColor);
        // Setzt die vorherige Strichstärke des Graphics2D-Objekts.
        graphics.setStroke(
                new BasicStroke(previousStrokeWidth)
        );
        // Speichert die Farbe vor dem Zeichnen.
        Color oldColor = graphics.getColor();
        // Speichert den Stroke vor dem Zeichnen.
        Stroke oldStroke = graphics.getStroke();
        // Zeichnet die Ellipse.
        ellipse.draw(graphics);
        // Überprüft, dass die ursprüngliche Farbe wiederhergestellt wurde.
        Assertions.assertEquals(
                oldColor,
                graphics.getColor()
        );
        // Überprüft, dass der ursprüngliche Stroke wiederhergestellt wurde.
        Assertions.assertEquals(
                oldStroke,
                graphics.getStroke()
        );
    }


    // Zählt Pixel mit der angegebenen Farbe im Testbild.
    private int countColorPixels(
            BufferedImage image,
            Color color) {
        // Speichert die Anzahl der gefundenen Pixel.
        int count = 0;
        // Durchläuft die gesamte Breite des Bildes.
        for (int x = 0; x < image.getWidth(); x++) {
            // Durchläuft die gesamte Höhe des Bildes.
            for (int y = 0; y < image.getHeight(); y++) {
                // Liest den aktuellen Pixel aus dem Bild.
                int pixel = image.getRGB(x, y);
                // Vergleicht den Pixel mit der erwarteten Farbe.
                if (pixel == color.getRGB()) {
                    // Erhöht die Anzahl der gefundenen Pixel.
                    count++;
                }
            }
        }
        // Gibt die Anzahl der gefundenen Pixel zurück.
        return count;
    }
}