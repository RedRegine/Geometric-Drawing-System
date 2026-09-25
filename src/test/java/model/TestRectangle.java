package model; // Das Testpaket entspricht dem Paket der zu testenden Klasse.

import org.junit.jupiter.api.BeforeEach; // Importiert die JUnit-Annotation für die Vorbereitung vor jedem Test.
import org.junit.jupiter.api.AfterEach; // Importiert die JUnit-Annotation für die Nachbereitung nach jedem Test.
import org.junit.jupiter.params.ParameterizedTest; // Importiert den JUnit-Typ für parametrisierte Tests.
import org.junit.jupiter.params.provider.MethodSource; // Importiert die MethodSource für die Bereitstellung von Testdaten.
import java.awt.Point; // Importiert Point für die Start- und Endpunkte.
import java.awt.Color; // Importiert Color für die Prüfung der Rechteckfarbe.
import java.awt.BasicStroke; // Importiert BasicStroke für die Prüfung der Strichstärke.
import java.awt.Stroke; // Importiert Stroke für die Prüfung des Graphics2D-Zustands.
import java.awt.Graphics2D; // Importiert Graphics2D für die Zeichenoperationen.
import java.awt.image.BufferedImage; // Importiert BufferedImage, um das Zeichnen des Rechtecks auf einer Testfläche zu prüfen.
import java.util.stream.Stream; // Importiert Stream für die Bereitstellung der parametrisierten Testfälle.
import static org.junit.jupiter.api.Assertions.assertEquals; // Importiert Assertions zum Vergleichen von erwarteten und tatsächlichen Ergebnissen.
import static org.junit.jupiter.api.Assertions.assertSame; // Importiert die Assertion zum Vergleichen von Objektidentitäten.
import static org.junit.jupiter.api.Assertions.assertTrue; // Import die Assertion zur true Übergabe eines Ausdrucks
import static org.junit.jupiter.api.Assertions.assertFalse; // Import die Assertion zur false Übergabe eines Ausdrucks

// Testklasse für die Klasse Rectangle.
public class TestRectangle {
    // Referenz auf das aktuell getestete Rectangle-Objekt.
    private Rectangle myRectangle;

    // Diese Methode wird vor jedem einzelnen Testfall ausgeführt.
    @BeforeEach
    void before() {
        // Zu Beginn jedes Tests wird noch kein Rectangle-Objekt verwendet.
        myRectangle = null;
    }

    // Diese Methode wird nach jedem einzelnen Testfall ausgeführt.
    @AfterEach
    void after() {
        // Die Referenz wird nach dem Test wieder zurückgesetzt.
        myRectangle = null;
    }


    // Liefert die Testdaten für die Prüfung der Konstruktoren und Getter.
    static Stream<Object[]> constructorAndGetterCases() {
        // Erstellt einen Stream mit allen Testfällen für Konstruktoren und Getter.
        return Stream.<Object[]>of(

                // TR-KG01 prüft den alten Konstruktor mit den Standardwerten.
                new Object[]{
                        "TR-KG01: start=(0,0), end=(10,10), Standardfarbe=BLACK, Standard strokeWidth=1.0f",
                        0, 0, // Startpunkt
                        10, 10, // Endpunkt
                        null, // inputFarbe
                        null, // inputStrichstärke
                        Color.BLACK, // erwartete Farbe
                        1.0f // erwartete Strichstärke
                },

                // TR-KG02 prüft den Konstruktor mit eigener Farbe und eigener Strichstärke.
                new Object[]{
                        "TR-KG02: start=(0,0), end=(10,10), color=RED, strokeWidth=2.0f",
                        0, 0, // Startpunkt
                        10, 10, // Endpunkt
                        Color.RED, // inputFarbe
                        2.0f, // inputStrichstärke
                        Color.RED, // erwartete Farbe
                        2.0f // erwartete Strichstärke
                },

                // TR-KG03 prüft den Konstruktor mit den expliziten Standardwerten.
                new Object[]{
                        "TR-KG03: start=(0,0), end=(10,10), color=BLACK, strokeWidth=1.0f",
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
            Color color,
            Float strokeWidth,
            Color expectedColor,
            float expectedStrokeWidth
    ) {
        // Erstellt den Startpunkt mit den übergebenen Koordinaten.
        Point start = new Point(startX, startY);
        // Erstellt den Endpunkt mit den übergebenen Koordinaten.
        Point end = new Point(endX, endY);
        // Prüft, ob der Testfall den Konstruktor mit Standardwerten verwenden soll.
        if (color == null && strokeWidth == null) {
            // Erstellt ein Rectangle über den Konstruktor mit Standardfarbe und Standard-Strichstärke.
            myRectangle = new Rectangle(start, end);
        } else {
            // Erstellt ein Rectangle über den Konstruktor mit den übergebenen Eigenschaften.
            myRectangle = new Rectangle(start, end, color, strokeWidth);
        }
        // Prüft, ob der gespeicherte Startpunkt mit dem erwarteten Startpunkt übereinstimmt.
        assertEquals(start, myRectangle.getStart());
        // Prüft, ob der gespeicherte Endpunkt mit dem erwarteten Endpunkt übereinstimmt.
        assertEquals(end, myRectangle.getEnd());
        // Prüft, ob die gespeicherte Farbe mit der erwarteten Farbe übereinstimmt.
        assertEquals(expectedColor, myRectangle.getColor());
        // Prüft, ob die gespeicherte Strichstärke mit der erwarteten Strichstärke übereinstimmt.
        assertEquals(expectedStrokeWidth, myRectangle.getStrokeWidth());
    }


    // Liefert die Testdaten für die Prüfung von containsPoint() bei normaler Koordinatenrichtung.
    static Stream<Object[]> containsPointCases() {
        // Erstellt einen Stream mit gültigen und ungültigen Punkten.
        return Stream.<Object[]>of(

                // TR-P01 prüft einen Punkt innerhalb des Rechtecks.
                new Object[]{
                        "TR-P01: start=(0,0), end=(100,100), Punkt=(50,50), erwartet=true",
                        0, 0, // Startpunkt
                        100, 100, // Endpunkt
                        50, 50, // Punkt X, Punkt Y
                        true // gültig = true | ungültig = false
                },

                // TR-P02 prüft die obere linke Ecke des Rechtecks.
                new Object[]{
                        "TR-P02: start=(0,0), end=(100,100), Punkt=(0,0), erwartet=true",
                        0, 0, // Startpunkt
                        100, 100, // Endpunkt
                        0, 0, // Punkt X, Punkt Y
                        true // gültig = true | ungültig = false
                },

                // TR-P03 prüft die untere rechte Ecke des Rechtecks.
                new Object[]{
                        "TR-P03: start=(0,0), end=(100,100), Punkt=(100,100), erwartet=true",
                        0, 0, // Startpunkt
                        100, 100, // Endpunkt
                        100, 100, // Punkt X, Punkt Y
                        true // gültig = true | ungültig = false
                },

                // TR-P04 prüft einen Punkt auf der linken Begrenzung.
                new Object[]{
                        "TR-P04: start=(0,0), end=(100,100), Punkt=(0,50), erwartet=true",
                        0, 0, // Startpunkt
                        100, 100, // Endpunkt
                        0, 50, // Punkt X, Punkt Y
                        true // gültig = true | ungültig = false
                },

                // TR-P05 prüft einen Punkt auf der rechten Begrenzung.
                new Object[]{
                        "TR-P05: start=(0,0), end=(100,100), Punkt=(100,50), erwartet=true",
                        0, 0, // Startpunkt
                        100, 100, // Endpunkt
                        100, 50, // Punkt X, Punkt Y
                        true // gültig = true | ungültig = false
                },

                // TR-P06 prüft einen Punkt auf der oberen Begrenzung.
                new Object[]{
                        "TR-P06: start=(0,0), end=(100,100), Punkt=(50,0), erwartet=true",
                        0, 0, // Startpunkt
                        100, 100, // Endpunkt
                        50, 0, // Punkt X, Punkt Y
                        true // gültig = true | ungültig = false
                },

                // TR-P07 prüft einen Punkt auf der unteren Begrenzung.
                new Object[]{
                        "TR-P07: start=(0,0), end=(100,100), Punkt=(50,100), erwartet=true",
                        0, 0, // Startpunkt
                        100, 100, // Endpunkt
                        50, 100, // Punkt X, Punkt Y
                        true // gültig = true | ungültig = false
                },

                // TR-P08 prüft einen Punkt knapp außerhalb der linken Begrenzung.
                new Object[]{
                        "TR-P08: start=(0,0), end=(100,100), Punkt=(-1,50), erwartet=false",
                        0, 0, // Startpunkt
                        100, 100, // Endpunkt
                        -1, 50, // Punkt X, Punkt Y
                        false // gültig = true | ungültig = false
                },

                // TR-P09 prüft einen Punkt knapp außerhalb der rechten Begrenzung.
                new Object[]{
                        "TR-P09: start=(0,0), end=(100,100), Punkt=(101,50), erwartet=false",
                        0, 0, // Startpunkt
                        100, 100, // Endpunkt
                        101, 50, // Punkt X, Punkt Y
                        false // gültig = true | ungültig = false
                },

                // TR-P10 prüft einen Punkt knapp oberhalb des Rechtecks.
                new Object[]{
                        "TR-P10: start=(0,0), end=(100,100), Punkt=(50,-1), erwartet=false",
                        0, 0, // Startpunkt
                        100, 100, // Endpunkt
                        50, -1, // Punkt X, Punkt Y
                        false // gültig = true | ungültig = false
                },

                // TR-P11 prüft einen Punkt knapp unterhalb des Rechtecks.
                new Object[]{
                        "TR-P11: start=(0,0), end=(100,100), Punkt=(50,101), erwartet=false",
                        0, 0, // Startpunkt
                        100, 100, // Endpunkt
                        50, 101, // Punkt X, Punkt Y
                        false // gültig = true | ungültig = false
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
            boolean expected
    ) {
        // Erstellt den Startpunkt des Rechtecks.
        Point start = new Point(startX, startY);
        // Erstellt den Endpunkt des Rechtecks.
        Point end = new Point(endX, endY);
        // Erstellt das zu testende Rectangle mit Standardfarbe und Standard-Strichstärke.
        myRectangle = new Rectangle(start, end);
        // Prüft, ob containsPoint() das erwartete Ergebnis liefert.
        if (expected) {
            // Erwartet wird, dass der Punkt innerhalb des Rechtecks liegt.
            assertTrue(myRectangle.containsPoint(pointX, pointY));
        } else {
            // Erwartet wird, dass der Punkt außerhalb des Rechtecks liegt.
            assertFalse(myRectangle.containsPoint(pointX, pointY));
        }
    }


    // Liefert die Testdaten für Rechtecke mit umgekehrter Koordinatenrichtung.
    static Stream<Object[]> reversedContainsPointCases() {
        // Erstellt einen Stream mit Punkten für die Prüfung der Koordinatennormalisierung.
        return Stream.<Object[]>of(

                // TR-P12 prüft einen Punkt innerhalb eines umgekehrt definierten Rechtecks.
                new Object[]{
                        "TR-P12: start=(100,100), end=(0,0), Punkt=(50,50), erwartet=true",
                        100, 100, // Startpunkt
                        0, 0, // Endpunkt
                        50, 50, // Punkt X, Punkt Y
                        true // gültig = true | ungültig = false
                },

                // TR-P13 prüft die Ecke (0,0) bei umgekehrter Koordinatenrichtung.
                new Object[]{
                        "TR-P13: start=(100,100), end=(0,0), Punkt=(0,0), erwartet=true",
                        100, 100, // Startpunkt
                        0, 0, // Endpunkt
                        0, 0, // Punkt X, Punkt Y
                        true // gültig = true | ungültig = false
                },

                // TR-P14 prüft die Ecke (100,100) bei umgekehrter Koordinatenrichtung.
                new Object[]{
                        "TR-P14: start=(100,100), end=(0,0), Punkt=(100,100), erwartet=true",
                        100, 100, // Startpunkt
                        0, 0, // Endpunkt
                        100, 100, // Punkt X, Punkt Y
                        true // gültig = true | ungültig = false
                },

                // TR-P15 prüft einen Punkt außerhalb der linken Begrenzung.
                new Object[]{
                        "TR-P15: start=(100,100), end=(0,0), Punkt=(-1,50), erwartet=false",
                        100, 100, // Startpunkt
                        0, 0, // Endpunkt
                        -1, 50, // Punkt X, Punkt Y
                        false // gültig = true | ungültig = false
                },

                // TR-P16 prüft einen Punkt außerhalb der rechten Begrenzung.
                new Object[]{
                        "TR-P16: start=(100,100), end=(0,0), Punkt=(101,50), erwartet=false",
                        100, 100, // Startpunkt
                        0, 0, // Endpunkt
                        101, 50, // Punkt X, Punkt Y
                        false // gültig = true | ungültig = false
                }
        );
    }

    // Führt die parametrisierten Tests für umgekehrte Koordinaten aus.
    @ParameterizedTest(name = "{0}")
    // Verwendet die Testdaten aus reversedContainsPointCases().
    @MethodSource("reversedContainsPointCases")
    void testContainsPointWithReversedCoordinates(
            String testCase,
            int startX,
            int startY,
            int endX,
            int endY,
            int pointX,
            int pointY,
            boolean expected
    ) {
        // Erstellt den Startpunkt des Rechtecks.
        Point start = new Point(startX, startY);
        // Erstellt den Endpunkt des Rechtecks.
        Point end = new Point(endX, endY);
        // Erstellt das Rectangle mit umgekehrter Koordinatenrichtung.
        myRectangle = new Rectangle(start, end);
        // Ruft die zu testende Methode auf.
        boolean actual = myRectangle.containsPoint(pointX, pointY);
        // Vergleicht das tatsächliche Ergebnis mit dem erwarteten Ergebnis.
        assertEquals(expected, actual);
    }

    // Liefert die Testdaten für die Zeichenprüfung.
    static Stream<Object[]> drawCases() {
        // Erstellt einen Stream mit verschiedenen Farben und Strichstärken.
        return Stream.<Object[]>of(

                // TR-D01 prüft das Zeichnen mit den Standardwerten.
                new Object[]{
                        "TR-D01: start=(5,5), end=(20,20), color=BLACK, strokeWidth=1.0f",
                        5, 5, // Startpunkt
                        20, 20, // Endpunkt
                        Color.BLACK, // Farbe
                        1.0f // Linienstärke
                },

                // TR-D02 prüft das Zeichnen mit roter Farbe und stärkerem Strich.
                new Object[]{
                        "TR-D02: start=(5,5), end=(20,20), color=RED, strokeWidth=3.0f",
                        5, 5, // Startpunkt
                        20, 20, // Endpunkt
                        Color.RED, // Farbe
                        3.0f // Linienstärke
                },

                // TR-D03 prüft das Zeichnen mit umgekehrter Koordinatenrichtung.
                new Object[]{
                        "TR-D03: start=(20,20), end=(5,5), color=BLUE, strokeWidth=2.0f",
                        20, 20, // Startpunkt
                        5, 5, // Endpunkt
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
            String testCase,
            int startX,
            int startY,
            int endX,
            int endY,
            Color color,
            float strokeWidth
    ) {
        // Erstellt den Startpunkt des Rechtecks.
        Point start = new Point(startX, startY);
        // Erstellt den Endpunkt des Rechtecks.
        Point end = new Point(endX, endY);
        // Erstellt das zu testende Rectangle mit den angegebenen Eigenschaften.
        myRectangle = new Rectangle(start, end, color, strokeWidth);

        // Erstellt ein Bild mit 50 x 50 Pixeln als Testfläche.
        BufferedImage image = new BufferedImage(
                50,
                50,
                BufferedImage.TYPE_INT_ARGB
        );
        // Erzeugt ein Graphics2D-Objekt für die Zeichenfläche.
        Graphics2D graphics = image.createGraphics();
        try {
            // Zeichnet das Rechteck auf die Testfläche.
            myRectangle.draw(graphics);
            // Liest die Farbe eines Pixels auf der oberen Rechteckkante aus.
            int actualRGB = image.getRGB(10, 5);
            // Ermittelt die erwartete RGB-Farbe des Rechtecks.
            int expectedRGB = color.getRGB();
            // Prüft, ob der Pixel tatsächlich mit der Rechteckfarbe gezeichnet wurde.
            assertEquals(expectedRGB, actualRGB);
        } finally {
            // Gibt die verwendeten Grafikressourcen wieder frei.
            graphics.dispose();
        }
    }
    // Liefert die Testdaten für die Prüfung der Wiederherstellung des Graphics2D-Zustands.
    static Stream<Object[]> graphicsStateCases() {
        // Gibt den definierten Testfall zurück.
        return Stream.<Object[]>of(

                // TR-D04 prüft, ob Farbe und Stroke nach dem Zeichnen wiederhergestellt werden.
                new Object[]{
                        "TR-D04: Rectangle color=BLUE, strokeWidth=2.0f, vorherige Farbe=GREEN, vorheriger Stroke=5.0f",
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
            Color rectangleColor,
            float rectangleStrokeWidth,
            Color oldColor,
            float oldStrokeWidth
    ) {
        // Erstellt ein Rechteck für den Test.
        myRectangle = new Rectangle(
                new Point(5, 5),
                new Point(20, 20),
                rectangleColor,
                rectangleStrokeWidth
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
            myRectangle.draw(graphics);
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