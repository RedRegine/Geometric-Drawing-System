package model; // Das Testpaket entspricht dem Paket der zu testenden Klasse.

import java.awt.Color; // Importiert die Java-Klasse für Farben.
import java.awt.BasicStroke; // Importiert die Java-Klasse für Striche beziehungsweise Linienbreiten.
import java.awt.Stroke; // Importiert die allgemeine Schnittstelle für Striche.
import java.awt.Point; // Importiert die Klasse für Punktkoordinaten.
import java.awt.Graphics2D; // Importiert die Graphics2D-Klasse zum Zeichnen.
import java.awt.image.BufferedImage; // Importiert BufferedImage, damit wir ohne sichtbare Benutzeroberfläche zeichnen können.
import org.junit.jupiter.api.BeforeEach; // Importiert die JUnit-Annotation für die Vorbereitung vor jedem Test.
import org.junit.jupiter.api.AfterEach; // Importiert die JUnit-Annotation für die Bereinigung nach jedem Test.
import org.junit.jupiter.params.ParameterizedTest; // Importiert die Annotation für parametrisierte Tests.
import org.junit.jupiter.params.provider.MethodSource; // Importiert die Annotation für Testdaten aus einer Methode.
import static org.junit.jupiter.api.Assertions.assertEquals; // Importiert Assertions zum Vergleichen von erwarteten und tatsächlichen Ergebnissen.
import static org.junit.jupiter.api.Assertions.assertSame; // Importiert die Assertion zum Vergleichen von Objektidentitäten.
import static org.junit.jupiter.api.Assertions.assertNotNull; // Importiert die Assertion zum Prüfen auf einen nicht leeren beziehungsweise nicht null-Wert.
import java.util.stream.Stream; // Importiert Stream, um die Testdaten für die parametrisierten Tests bereitzustellen.

// Testklasse für die Klasse Line.
public class TestLine {
    // Referenz auf das aktuell getestete Line-Objekt.
    private Line myLine;

    // Diese Methode wird vor jedem einzelnen Testfall ausgeführt.
    @BeforeEach
    void before() {
        // Zu Beginn jedes Tests wird noch kein Rectangle-Objekt verwendet.
        myLine = null;
    }

    // Diese Methode wird nach jedem einzelnen Testfall ausgeführt.
    @AfterEach
    void after() {
        // Die Referenz wird nach dem Test wieder zurückgesetzt.
        myLine = null;
    }

    // Liefert die Testdaten für die Prüfung der Konstruktoren und Getter.
    static Stream<Object[]> constructorAndGetterCases() {
        // Gibt mehrere Testfälle mit ihren jeweiligen Eingabe- und Erwartungswerten zurück.
        return Stream.<Object[]>of(

                // TL-KG01 prüft den alten Konstruktor mit den automatisch verwendeten Standardwerten.
                new Object[]{
                        "TL-KG01: start=(0,0), end=(10,10), color=null, strokeWidth=null",
                        0, 0, // Startpunkt
                        10, 10, // Endpunkt
                        null, // inputFarbe
                        null, // inputStrichstärke
                        Color.BLACK, // erwartete Farbe
                        1.0f // erwartete Strichstärke
                },

                // TL-KG02 prüft den neuen Konstruktor mit einer roten Linie und einer Strichstärke von 2.0f.
                new Object[]{
                        "TL-KG02: start=(0,0), end=(10,10), color=RED, strokeWidth=2.0f",
                        0, 0, // Startpunkt
                        10, 10, // Endpunkt
                        Color.RED, // inputFarbe
                        2.0f, // inputStrichstärke
                        Color.RED, // erwartete Farbe
                        2.0f // erwartete Strichstärke
                },

                // TL-KG03 prüft den neuen Konstruktor mit einer schwarzen Linie und einer Strichstärke von 1.0f.
                new Object[]{
                        "TL-KG03: start=(0,0), end=(10,10), color=BLACK, strokeWidth=1.0f",
                        0, 0, // Startpunkt
                        10, 10, // Endpunkt
                        Color.BLACK, // inputFarbe
                        1.0f, // inputStrichstärke
                        Color.BLACK, // erwartete Farbe
                        1.0f // erwartete Strichstärke
                }
        );
    }

    // Führt die parametrisierten Tests für Konstruktoren und Getter aus.
    @ParameterizedTest(name = "{0}")
    // Verwendet die oben definierte Methode als Quelle für die Testdaten.
    @MethodSource("constructorAndGetterCases")
    void testConstructorAndGetters(
            String testCase,
            int startX,
            int startY,
            int endX,
            int endY,
            Color inputColor,
            Float inputStrokeWidth,
            Color expectedColor,
            float expectedStrokeWidth
    ) {
        // Erstellt den Startpunkt mit den übergebenen Koordinaten.
        Point start = new Point(startX, startY);
        // Erstellt den Endpunkt mit den übergebenen Koordinaten.
        Point end = new Point(endX, endY);
        // Prüft, ob der Testfall den Konstruktor mit Standardwerten verwenden soll.
        if (inputColor == null && inputStrokeWidth == null) {
            // Erstellt ein Line über den Konstruktor mit Standardfarbe und Standard-Strichstärke.
            myLine = new Line(start, end);
        } else {
            // Erstellt ein Line über den Konstruktor mit den übergebenen Eigenschaften.
            myLine = new Line(
                    start,
                    end,
                    inputColor,
                    inputStrokeWidth
            );
        }
        // Prüft, ob der gespeicherte Startpunkt mit dem erwarteten Startpunkt übereinstimmt.
        assertEquals(start, myLine.getStart());
        // Prüft, ob der gespeicherte Endpunkt mit dem erwarteten Endpunkt übereinstimmt.
        assertEquals(end, myLine.getEnd());
        // Prüft, ob die gespeicherte Farbe mit der erwarteten Farbe übereinstimmt.
        assertEquals(expectedColor, myLine.getColor());
        // Prüft, ob die gespeicherte Strichstärke mit der erwarteten Strichstärke übereinstimmt.
        assertEquals(expectedStrokeWidth, myLine.getStrokeWidth());
    }


    // Liefert die Testdaten für die Prüfung von containsPoint()
    static Stream<Object[]> containsPointCases() {
        // Erstellt einen Stream mit gültigen und ungültigen Punkten.
        return Stream.<Object[]>of(

                // TL-P01: Der Punkt liegt genau in der Mitte der Linie.
                new Object[]{
                        "TL-P01: start=(0,0), (end=(100,0), Punkt=(50,0), strokeWidth=1.0f, erwartet=true",
                        0, 0, // Startpunkt
                        100, 0, // Endpunkt
                        50, 0, // Punkt X, Punkt Y
                        1.0f, // Strichbreite
                        true // Erwartungswert
                },

                // TL-P02: Der Punkt liegt drei Pixel neben der Linie und innerhalb der Toleranz.
                new Object[]{
                        "TL-P02: start=(0,0), (end=(100,0), Punkt=(50,3), strokeWidth=1.0f, erwartet=true",
                        0, 0, // Startpunkt
                        100, 0, // Endpunkt
                        50, 3, // Punkt X, Punkt Y
                        1.0f, // Strichbreite
                        true // Erwartungswert
                },

                // TL-P03: Der Punkt liegt genau fünf Pixel neben der Linie und auf der Toleranzgrenze.
                new Object[]{
                        "TL-P03: start=(0,0), (end=(100,0), Punkt=(50,5), strokeWidth=1.0f, erwartet=true",
                        0, 0, // Startpunkt
                        100, 0, // Endpunkt
                        50, 5, // Punkt X, Punkt Y
                        1.0f, // Strichbreite
                        true // Erwartungswert
                },

                // TL-P04: Der Punkt liegt sechs Pixel neben der Linie und außerhalb der Toleranz.
                new Object[]{
                        "TL-P04: start=(0,0), (end=(100,0), Punkt=(50,6), strokeWidth=1.0f, erwartet=false",
                        0, 0, // Startpunkt
                        100, 0, // Endpunkt
                        50, 6, // Punkt X, Punkt Y
                        1.0f, // Strichbreite
                        false // Erwartungswert
                },

                // TL-P05: Der Punkt liegt deutlich außerhalb der Toleranz.
                new Object[]{
                        "TL-P05: start=(0,0), (end=(100,0), Punkt=(50,20), strokeWidth=1.0f, erwartet=false",
                        0, 0, // Startpunkt
                        100, 0, // Endpunkt
                        50, 20, // Punkt X, Punkt Y
                        1.0f, // Strichbreite
                        false // Erwartungswert
                },

                // TL-P06: Der Punkt liegt genau auf dem Startpunkt der Linie.
                new Object[]{
                        "TL-P06: start=(0,0), (end=(100,0), Punkt=(0,0), strokeWidth=1.0f, erwartet=true",
                        0, 0, // Startpunkt
                        100, 0, // Endpunkt
                        0, 0, // Punkt X, Punkt Y
                        1.0f, // Strichbreite
                        true // Erwartungswert
                },

                // TL-P07: Der Punkt liegt genau auf dem Endpunkt der Linie.
                new Object[]{
                        "TL-P07: start=(0,0), (end=(100,0), Punkt=(100,0), strokeWidth=1.0f, erwartet=true",
                        0, 0, // Startpunkt
                        100, 0, // Endpunkt
                        100, 0, // Punkt X, Punkt Y
                        1.0f, // Strichbreite
                        true // Erwartungswert
                },

                // TL-P08: Der Punkt liegt auf der verlängerten Geraden vor dem Startpunkt.
                new Object[]{
                        "TL-P08: start=(0,0), (end=(100,0), Punkt=(-10,0), strokeWidth=1.0f, erwartet=false",
                        0, 0, // Startpunkt
                        100, 0, // Endpunkt
                        -10, 0, // Punkt X, Punkt Y
                        1.0f, // Strichbreite
                        false // Erwartungswert
                },

                // TL-P09: Der Punkt liegt auf der verlängerten Geraden hinter dem Endpunkt.
                new Object[]{
                        "TL-P09: start=(0,0), (end=(100,0), Punkt=(110,0), strokeWidth=1.0f, erwartet=false",
                        0, 0, // Startpunkt
                        100, 0, // Endpunkt
                        110, 0, // Punkt X, Punkt Y
                        1.0f, // Strichbreite
                        false // Erwartungswert
                },

                // TL-P10: Bei einer größeren Strichstärke liegt der Punkt innerhalb der erweiterten Toleranz.
                new Object[]{
                        "TL-P10: start=(0,0), (end=(100,0), Punkt=(50,7), strokeWidth=10.0f, erwartet=true",
                        0, 0, // Startpunkt
                        100, 0, // Endpunkt
                        50, 7, // Punkt X, Punkt Y
                        10.0f, // Strichbreite
                        true // Erwartungswert
                },

                // TL-P11: Bei einer größeren Strichstärke liegt der Punkt außerhalb der erweiterten Toleranz.
                new Object[]{
                        "TL-P11: start=(0,0), (end=(100,0), Punkt=(50,9), strokeWidth=10.0f, erwartet=false",
                        0, 0, // Startpunkt
                        100, 0, // Endpunkt
                        50, 9, // Punkt X, Punkt Y
                        10.0f, // Strichbreite
                        false // Erwartungswert
                }
        );
    }

    // Führt die parametrisierten Tests für containsPoint() aus.
    @ParameterizedTest(name = "{0}")
    // Verwendet die oben definierten Testdaten.
    @MethodSource("containsPointCases")
    void testContainsPoint(
            String testCase,
            int startX,
            int startY,
            int endX,
            int endY,
            int pointX,
            int pointY,
            float strokeWidth,
            boolean expected
    ) {
        // Erzeugt den Startpunkt der Linie.
        Point start = new Point(startX, startY);
        // Erzeugt den Endpunkt der Linie.
        Point end = new Point(endX, endY);
        // Erzeugt eine Linie mit der angegebenen Strichstärke.
        myLine = new Line(
                start,
                end,
                Color.BLACK,
                strokeWidth
        );
        // Führt die zu testende Methode mit den angegebenen Punktkoordinaten aus.
        boolean actual = myLine.containsPoint(pointX, pointY);
        // Vergleicht das tatsächliche Ergebnis mit dem erwarteten Ergebnis.
        assertEquals(expected, actual);
    }


    // Liefert die Testdaten für die Zeichenprüfung.
    static Stream<Object[]> drawCases() {
        // Gibt die Testfälle für verschiedene Farben und Strichstärken zurück.
        return Stream.<Object[]>of(

                // TL-D01: Eine schwarze Linie mit einer Strichstärke von 1.0f.
                new Object[]{
                        "TL-D01: color=BLACK, strokeWidth=1.0f",
                        Color.BLACK, // Farbe
                        1.0f // Linienstärke
                },

                // TL-D02: Eine rote Linie mit einer Strichstärke von 3.0f.
                new Object[]{
                        "TL-D02: color=RED, strokeWidth=3.0f",
                        Color.RED, // Farbe
                        3.0f // Linienstärke
                }
        );
    }

    // Führt die parametrisierten Tests für die draw()-Methode aus.
    @ParameterizedTest(name = "{0}")
    // Verwendet die oben definierten Zeichen-Testdaten.
    @MethodSource("drawCases")
    void testDraw(
            String testCase,
            Color expectedColor,
            float strokeWidth
    ) {
        // Erzeugt eine kleine transparente Zeichenfläche ohne sichtbare Benutzeroberfläche.
        BufferedImage image = new BufferedImage(
                30,
                30,
                BufferedImage.TYPE_INT_ARGB
        );
        // Erzeugt ein Graphics2D-Objekt für die Zeichenfläche.
        Graphics2D graphics = image.createGraphics();
        try {
            // Erzeugt eine Linie von (5,5) bis (20,5) mit der jeweiligen Farbe und Strichstärke.
            myLine = new Line(
                    new Point(5, 5),
                    new Point(20, 5),
                    expectedColor,
                    strokeWidth
            );
            // Zeichnet die Linie auf die Zeichenfläche.
            myLine.draw(graphics);
            // Liest die Farbe eines Pixels aus der Mitte der gezeichneten Linie aus.
            int actualPixelColor = image.getRGB(12, 5);
            // Prüft, ob der Pixel die erwartete Farbe besitzt.
            assertEquals(expectedColor.getRGB(), actualPixelColor);
            // Prüft zusätzlich, ob die Linie tatsächlich ein sichtbares Pixel erzeugt hat.
            assertNotNull(image);
        } finally {
            // Gibt die von Graphics2D verwendeten Ressourcen wieder frei.
            graphics.dispose();
        }
    }

    // Liefert die Testdaten für die Prüfung der Wiederherstellung des Graphics2D-Zustands.
    static Stream<Object[]> graphicsStateCases() {
        // Gibt den definierten Testfall zurück.
        return Stream.<Object[]>of(

                // TL-D03 prüft die Wiederherstellung der ursprünglichen Farbe und des ursprünglichen Strokes.
                new Object[]{
                        "TL-D03: Line color=BLUE, strokeWidth=2.0f, vorherige Farbe=GREEN, vorheriger Stroke=5.0f",
                        Color.BLUE, // Farbe des Rechtecks.
                        2.0f, // Strichstärke des Rechtecks.
                        Color.GREEN, // Farbe, die vor dem Zeichnen im Graphics2D-Objekt vorhanden ist.
                        5.0f // Strichstärke, die vor dem Zeichnen im Graphics2D-Objekt vorhanden ist.
                }
        );
    }

    // Prüft, ob draw() die ursprünglichen Graphics2D-Einstellungen wiederherstellt.
    @ParameterizedTest(name = "{0}")
    // Verwendet die Testdaten aus graphicsStateCases().
    @MethodSource("graphicsStateCases")
    void testDrawRestoresGraphicsState(
            String testCase,
            Color lineColor,
            float lineStrokeWidth,
            Color oldColor,
            float oldStrokeWidth
    ) {
        // Erstellt eine Linie für den Test.
        myLine = new Line(
                new Point(5, 5),
                new Point(20, 5),
                lineColor,
                lineStrokeWidth
        );
        // Erstellt eine neue Zeichenfläche.
        BufferedImage image = new BufferedImage(
                50,
                50,
                BufferedImage.TYPE_INT_ARGB
        );
        // Erzeugt das Graphics2D-Objekt für die Zeichenfläche.
        Graphics2D graphics = image.createGraphics();
        try {
            // Setzt vor dem Zeichnen bewusst eine andere Farbe.
            graphics.setColor(oldColor);
            // Erstellt einen Stroke mit der vorherigen Strichstärke.
            Stroke oldStroke = new BasicStroke(oldStrokeWidth);
            // Setzt den vorherigen Stroke im Graphics2D-Objekt.
            graphics.setStroke(oldStroke);
            // Zeichnet das Rectangle.
            myLine.draw(graphics);
            // Prüft, ob die vorherige Farbe nach dem Zeichnen wiederhergestellt wurde.
            assertEquals(oldColor, graphics.getColor());
            // Prüft, ob exakt dasselbe Stroke-Objekt wiederhergestellt wurde.
            assertSame(oldStroke, graphics.getStroke());
        } finally {
            // Gibt die Ressourcen des Graphics2D-Objekts frei.
            graphics.dispose();
        }
    }
}