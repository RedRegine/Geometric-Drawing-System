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


    // Wird vor jedem Test ausgeführt.
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


    // Wird nach jedem Test ausgeführt.
    @AfterEach
    void tearDown() {

        // Gibt die Graphics2D-Ressourcen nach dem Test frei.
        graphics.dispose();
    }


    // =========================================================
    // 1. KONSTRUKTOREN UND GETTER
    // =========================================================

    // Testet die beiden Konstruktoren und die zugehörigen Getter.
    @ParameterizedTest(name = "{0}")
    @MethodSource("constructorCases")
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
        Assertions.assertEquals(
                start,
                ellipse.getStart()
        );

        // Überprüft den gespeicherten Endpunkt.
        Assertions.assertEquals(
                end,
                ellipse.getEnd()
        );

        // Überprüft die gespeicherte Farbe.
        Assertions.assertEquals(
                expectedColor,
                ellipse.getColor()
        );

        // Überprüft die gespeicherte Strichstärke.
        Assertions.assertEquals(
                expectedStrokeWidth,
                ellipse.getStrokeWidth()
        );
    }


    // Liefert die Testdaten für die Konstruktor- und Getter-Tests.
    static Stream<Object[]> constructorCases() {

        // Gibt die definierten Testfälle TE-KG01 bis TE-KG03 zurück.
        return Stream.<Object[]>of(

                // TE-KG01: Alter Konstruktor mit Standardwerten.
                new Object[]{
                        "TE-KG01 ((0,0),(10,10),BLACK,1.0f)",
                        new Point(0, 0),
                        new Point(10, 10),
                        Color.BLACK,
                        1.0f,
                        false
                },

                // TE-KG02: Erweiterter Konstruktor mit Rot und 2.0f.
                new Object[]{
                        "TE-KG02 ((0,0),(10,10),RED,2.0f)",
                        new Point(0, 0),
                        new Point(10, 10),
                        Color.RED,
                        2.0f,
                        true
                },

                // TE-KG03: Erweiterter Konstruktor mit Schwarz und 1.0f.
                new Object[]{
                        "TE-KG03 ((0,0),(10,10),BLACK,1.0f)",
                        new Point(0, 0),
                        new Point(10, 10),
                        Color.BLACK,
                        1.0f,
                        true
                }
        );
    }


    // =========================================================
    // 2. containsPoint() – NORMALE KOORDINATEN
    // =========================================================

    // Testet Punkte innerhalb, auf dem Rand und außerhalb der Ellipse.
    @ParameterizedTest(name = "{0}")
    @MethodSource("normalCoordinateCases")
    void testContainsPointNormalCoordinates(
            String testId,
            Point start,
            Point end,
            int x,
            int y,
            boolean expected) {

        // Erstellt die zu testende Ellipse.
        ellipse = new Ellipse(start, end);

        // Prüft, ob containsPoint() das erwartete Ergebnis liefert.
        Assertions.assertEquals(
                expected,
                ellipse.containsPoint(x, y)
        );
    }


    // Liefert die Testdaten für normale Koordinaten.
    static Stream<Object[]> normalCoordinateCases() {

        // Gibt die definierten Testfälle TE-P01 bis TE-P11 zurück.
        return Stream.<Object[]>of(

                // TE-P01: Mittelpunkt der Ellipse.
                new Object[]{
                        "TE-P01 ((0,0),(100,60),(50,30))",
                        new Point(0, 0),
                        new Point(100, 60),
                        50,
                        30,
                        true
                },

                // TE-P02: Punkt innerhalb der Ellipse.
                new Object[]{
                        "TE-P02 ((0,0),(100,60),(50,15))",
                        new Point(0, 0),
                        new Point(100, 60),
                        50,
                        15,
                        true
                },

                // TE-P03: Punkt innerhalb der Ellipse.
                new Object[]{
                        "TE-P03 ((0,0),(100,60),(25,30))",
                        new Point(0, 0),
                        new Point(100, 60),
                        25,
                        30,
                        true
                },

                // TE-P04: Punkt innerhalb der Ellipse.
                new Object[]{
                        "TE-P04 ((0,0),(100,60),(75,30))",
                        new Point(0, 0),
                        new Point(100, 60),
                        75,
                        30,
                        true
                },

                // TE-P05: Linker Randpunkt der Ellipse.
                new Object[]{
                        "TE-P05 ((0,0),(100,60),(0,30))",
                        new Point(0, 0),
                        new Point(100, 60),
                        0,
                        30,
                        true
                },

                // TE-P06: Rechter Randpunkt der Ellipse.
                new Object[]{
                        "TE-P06 ((0,0),(100,60),(100,30))",
                        new Point(0, 0),
                        new Point(100, 60),
                        100,
                        30,
                        true
                },

                // TE-P07: Oberer Randpunkt der Ellipse.
                new Object[]{
                        "TE-P07 ((0,0),(100,60),(50,0))",
                        new Point(0, 0),
                        new Point(100, 60),
                        50,
                        0,
                        true
                },

                // TE-P08: Unterer Randpunkt der Ellipse.
                new Object[]{
                        "TE-P08 ((0,0),(100,60),(50,60))",
                        new Point(0, 0),
                        new Point(100, 60),
                        50,
                        60,
                        true
                },

                // TE-P09: Obere linke Ecke des Begrenzungsrechtecks.
                new Object[]{
                        "TE-P09 ((0,0),(100,60),(0,0))",
                        new Point(0, 0),
                        new Point(100, 60),
                        0,
                        0,
                        false
                },

                // TE-P10: Untere rechte Ecke des Begrenzungsrechtecks.
                new Object[]{
                        "TE-P10 ((0,0),(100,60),(100,60))",
                        new Point(0, 0),
                        new Point(100, 60),
                        100,
                        60,
                        false
                },

                // TE-P11: Punkt außerhalb der Ellipse.
                new Object[]{
                        "TE-P11 ((0,0),(100,60),(-1,30))",
                        new Point(0, 0),
                        new Point(100, 60),
                        -1,
                        30,
                        false
                }
        );
    }


    // =========================================================
    // 3. containsPoint() – UMGEKEHRTE KOORDINATEN
    // =========================================================

    // Testet Ellipsen mit vertauschten Start- und Endpunkten.
    @ParameterizedTest(name = "{0}")
    @MethodSource("reversedCoordinateCases")
    void testContainsPointReversedCoordinates(
            String testId,
            Point start,
            Point end,
            int x,
            int y,
            boolean expected) {

        // Erstellt die Ellipse mit umgekehrten Koordinaten.
        ellipse = new Ellipse(start, end);

        // Prüft das erwartete Ergebnis von containsPoint().
        Assertions.assertEquals(
                expected,
                ellipse.containsPoint(x, y)
        );
    }


    // Liefert die Testdaten für umgekehrte Koordinaten.
    static Stream<Object[]> reversedCoordinateCases() {

        // Gibt die definierten Testfälle TE-P12 bis TE-P16 zurück.
        return Stream.<Object[]>of(

                // TE-P12: Mittelpunkt der umgekehrten Ellipse.
                new Object[]{
                        "TE-P12 ((100,60),(0,0),(50,30))",
                        new Point(100, 60),
                        new Point(0, 0),
                        50,
                        30,
                        true
                },

                // TE-P13: Punkt innerhalb der Ellipse.
                new Object[]{
                        "TE-P13 ((100,60),(0,0),(50,15))",
                        new Point(100, 60),
                        new Point(0, 0),
                        50,
                        15,
                        true
                },

                // TE-P14: Linker Randpunkt der Ellipse.
                new Object[]{
                        "TE-P14 ((100,60),(0,0),(0,30))",
                        new Point(100, 60),
                        new Point(0, 0),
                        0,
                        30,
                        true
                },

                // TE-P15: Rechter Randpunkt der Ellipse.
                new Object[]{
                        "TE-P15 ((100,60),(0,0),(100,30))",
                        new Point(100, 60),
                        new Point(0, 0),
                        100,
                        30,
                        true
                },

                // TE-P16: Punkt außerhalb der Ellipse.
                new Object[]{
                        "TE-P16 ((100,60),(0,0),(101,30))",
                        new Point(100, 60),
                        new Point(0, 0),
                        101,
                        30,
                        false
                }
        );
    }


    // =========================================================
    // 4. containsPoint() – BREITE ODER HÖHE = 0
    // =========================================================

    // Prüft die Sicherheitsprüfung bei Breite oder Höhe 0.
    @ParameterizedTest(name = "{0}")
    @MethodSource("zeroDimensionCases")
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


    // Liefert die Testdaten für Breite oder Höhe 0.
    static Stream<Object[]> zeroDimensionCases() {

        // Gibt die definierten Testfälle TE-P17 und TE-P18 zurück.
        return Stream.<Object[]>of(

                // TE-P17: Breite ist 0.
                new Object[]{
                        "TE-P17 ((10,10),(10,50),(10,30))",
                        new Point(10, 10),
                        new Point(10, 50),
                        10,
                        30
                },

                // TE-P18: Höhe ist 0.
                new Object[]{
                        "TE-P18 ((10,10),(50,10),(30,10))",
                        new Point(10, 10),
                        new Point(50, 10),
                        30,
                        10
                }
        );
    }


    // =========================================================
    // 5. draw()
    // =========================================================

    // Testet das Zeichnen der Ellipse mit unterschiedlichen Eigenschaften.
    @ParameterizedTest(name = "{0}")
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


    // Liefert die Testdaten für die draw()-Tests.
    static Stream<Object[]> drawCases() {

        // Gibt die definierten Testfälle TE-D01 bis TE-D03 zurück.
        return Stream.<Object[]>of(

                // TE-D01: Schwarze Ellipse mit Strichstärke 1.0f.
                new Object[]{
                        "TE-D01 ((5,5),(20,20),BLACK,1.0f)",
                        new Point(5, 5),
                        new Point(20, 20),
                        Color.BLACK,
                        1.0f
                },

                // TE-D02: Rote Ellipse mit Strichstärke 3.0f.
                new Object[]{
                        "TE-D02 ((5,5),(20,20),RED,3.0f)",
                        new Point(5, 5),
                        new Point(20, 20),
                        Color.RED,
                        3.0f
                },

                // TE-D03: Blaue Ellipse mit Strichstärke 2.0f.
                new Object[]{
                        "TE-D03 ((10,5),(40,30),BLUE,2.0f)",
                        new Point(10, 5),
                        new Point(40, 30),
                        Color.BLUE,
                        2.0f
                }
        );
    }


    // =========================================================
    // 6. GRAPHICS-ZUSTAND
    // =========================================================

    // Prüft, ob draw() den vorherigen Graphics2D-Zustand wiederherstellt.
    @ParameterizedTest(name = "{0}")
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


    // Liefert die Testdaten für die Prüfung des Graphics2D-Zustands.
    static Stream<Object[]> graphicsStateCases() {

        // Gibt den definierten Testfall TE-D04 zurück.
        return Stream.<Object[]>of(

                // TE-D04: Die Ellipse verwendet Blau und 2.0f.
                // Der vorherige Graphics2D-Zustand ist Grün und 5.0f.
                new Object[]{
                        "TE-D04 (BLUE,2.0f,GREEN,5.0f)",
                        Color.BLUE,
                        2.0f,
                        Color.GREEN,
                        5.0f
                }
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