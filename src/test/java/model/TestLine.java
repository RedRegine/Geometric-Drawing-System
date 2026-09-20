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
import org.junit.jupiter.params.provider.Arguments; // Importiert Arguments, um mehrere Parameter an einen Testfall zu übergeben.
import static org.junit.jupiter.api.Assertions.assertEquals; // Importiert Assertions zum Vergleichen von erwarteten und tatsächlichen Ergebnissen.
import static org.junit.jupiter.api.Assertions.assertSame; // Importiert die Assertion zum Vergleichen von Objektidentitäten.
import static org.junit.jupiter.api.Assertions.assertNotNull; // Importiert die Assertion zum Prüfen auf einen nicht leeren beziehungsweise nicht null-Wert.
import java.util.stream.Stream; // Importiert Stream, um die Testdaten für die parametrisierten Tests bereitzustellen.

// Testklasse für die Klasse Line.
public class TestLine {
    // Enthält das Line-Objekt, das in den einzelnen Tests geprüft wird.
    private Line myLine;

    // Wird vor jedem Testfall ausgeführt.
    @BeforeEach
    void before() {
        // Zu Beginn jedes Testfalls wird die Testvariable zurückgesetzt.
        myLine = null;
    }

    // Wird nach jedem Testfall ausgeführt.
    @AfterEach
    void after() {
        // Nach jedem Testfall wird die Referenz auf das Testobjekt entfernt.
        myLine = null;
    }

    // Bereich 1: Konstruktoren und Getter
    // Liefert die Testdaten für die Prüfung der Konstruktoren und Getter, Testdaten werden als einzelne Werte übergeben
    static Stream<Arguments> constructorAndGetterCases() {
        // Gibt mehrere Testfälle mit ihren jeweiligen Eingabe- und Erwartungswerten zurück.
        return Stream.of(

                // TL-KG01 prüft den alten Konstruktor mit den automatisch verwendeten Standardwerten.
                Arguments.of(
                        "TL-KG01: start=(0,0), end=(10,10), color=null, strokeWidth=null",
                        // startX
                        0,
                        // startY
                        0,
                        // endX
                        10,
                        // endY
                        10,
                        // inputColor
                        null,
                        // inputStrokeWidth
                        null,
                        // erwartete Farbe
                        Color.BLACK,
                        // erwartete Strichstärke
                        1.0f
                ),

                // TL-KG02 prüft den neuen Konstruktor mit einer roten Linie und einer Strichstärke von 2.0f.
                Arguments.of(
                        "TL-KG02: start=(0,0), end=(10,10), color=RED, strokeWidth=2.0f",
                        // startX
                        0,
                        // startY
                        0,
                        // endX
                        10,
                        // endY
                        10,
                        // inputColor
                        Color.RED,
                        // inputStrokeWidth
                        2.0f,
                        // erwartete Farbe
                        Color.RED,
                        // erwartete Strichstärke
                        2.0f
                ),

                // TL-KG03 prüft den neuen Konstruktor mit einer schwarzen Linie und einer Strichstärke von 1.0f.
                Arguments.of(
                        "TL-KG03: start=(0,0), end=(10,10), color=BLACK, strokeWidth=1.0f",
                        // startX
                        0,
                        // startY
                        0,
                        // endX
                        10,
                        // endY
                        10,
                        // inputColor
                        Color.BLACK,
                        // inputStrokeWidth
                        1.0f,
                        // erwartete Farbe
                        Color.BLACK,
                        // erwartete Strichstärke
                        1.0f
                )
        );
    }

    // Prüft die Konstruktoren sowie getStart(), getEnd(), getColor() und getStrokeWidth().
    @ParameterizedTest(name = "{0}")
    // Aufbau zur Wiedergabe der Testergebnisse laut dokumentierter Tabelle
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
        // Erzeugt den Startpunkt aus den übergebenen Koordinaten.
        Point start = new Point(startX, startY);
        // Erzeugt den Endpunkt aus den übergebenen Koordinaten.
        Point end = new Point(endX, endY);
        // Prüft, ob der alte Konstruktor verwendet werden soll.
        if (inputColor == null && inputStrokeWidth == null) {
            // Erzeugt die Linie mit dem alten Konstruktor.
            myLine = new Line(start, end);
        } else {
            // Erzeugt eine Linie mit dem neuen Konstruktor und allen Eingabeparametern.
            myLine = new Line(
                    start,
                    end,
                    inputColor,
                    inputStrokeWidth
            );
        }
        // Prüft den Startpunkt.
        assertEquals(start, myLine.getStart());
        // Prüft den Endpunkt.
        assertEquals(end, myLine.getEnd());
        // Prüft die Farbe.
        assertEquals(expectedColor, myLine.getColor());
        // Prüft die Strichstärke.
        assertEquals(expectedStrokeWidth, myLine.getStrokeWidth());
    }

    // Bereich 2: containsPoint()
    // Liefert die Testdaten für die Prüfung von containsPoint().
    static Stream<Arguments> containsPointCases() {
        // Gibt Testfälle für Punkte auf, nahe an und außerhalb des Liniensegments zurück.
        return Stream.of(

                // TL-P01: Der Punkt liegt genau in der Mitte der Linie.
                Arguments.of(
                        "TL-P01: (0,0) -> (100,0), Punkt=(50,0), strokeWidth=1.0, erwartet=true",
                        // Startpunkt
                        0, 0,
                        // Endpunkt
                        100, 0,
                        // Punkt X, Punkt Y
                        50, 0,
                        // Strichbreite
                        1.0f,
                        // gültig = true | ungültig = false
                        true
                ),
                // TL-P02: Der Punkt liegt drei Pixel neben der Linie und innerhalb der Toleranz.
                Arguments.of(
                        "TL-P02: (0,0) -> (100,0), Punkt=(50,3), strokeWidth=1.0, erwartet=true",
                        // Startpunkt
                        0, 0,
                        // Endpunkt
                        100, 0,
                        // Punkt X, Punkt Y
                        50, 3,
                        // Strichbreite
                        1.0f,
                        // gültig = true | ungültig = false
                        true
                ),
                // TL-P03: Der Punkt liegt genau fünf Pixel neben der Linie und auf der Toleranzgrenze.
                Arguments.of(
                        "TL-P03: (0,0) -> (100,0), Punkt=(50,5), strokeWidth=1.0, erwartet=true",
                        // Startpunkt
                        0, 0,
                        // Endpunkt
                        100, 0,
                        // Punkt X, Punkt Y
                        50, 5,
                        // Strichbreite
                        1.0f,
                        // gültig = true | ungültig = false
                        true
                ),
                // TL-P04: Der Punkt liegt sechs Pixel neben der Linie und außerhalb der Toleranz.
                Arguments.of(
                        "TL-P04: (0,0) -> (100,0), Punkt=(50,6), strokeWidth=1.0, erwartet=false",
                        // Startpunkt
                        0, 0,
                        // Endpunkt
                        100, 0,
                        // Punkt X, Punkt Y
                        50, 6,
                        // Strichbreite
                        1.0f,
                        // gültig = true | ungültig = false
                        false
                ),
                // TL-P05: Der Punkt liegt deutlich außerhalb der Toleranz.
                Arguments.of(
                        "TL-P05: (0,0) -> (100,0), Punkt=(50,20), strokeWidth=1.0, erwartet=false",
                        // Startpunkt
                        0, 0,
                        // Endpunkt
                        100, 0,
                        // Punkt X, Punkt Y
                        50, 20,
                        // Strichbreite
                        1.0f,
                        // gültig = true | ungültig = false
                        false
                ),
                // TL-P06: Der Punkt liegt genau auf dem Startpunkt der Linie.
                Arguments.of(
                        "TL-P06: (0,0) -> (100,0), Punkt=(0,0), strokeWidth=1.0, erwartet=true",
                        // Startpunkt
                        0, 0,
                        // Endpunkt
                        100, 0,
                        // Punkt X, Punkt Y
                        0, 0,
                        // Strichbreite
                        1.0f,
                        // gültig = true | ungültig = false
                        true
                ),
                // TL-P07: Der Punkt liegt genau auf dem Endpunkt der Linie.
                Arguments.of(
                        "TL-P07: (0,0) -> (100,0), Punkt=(100,0), strokeWidth=1.0, erwartet=true",
                        // Startpunkt
                        0, 0,
                        // Endpunkt
                        100, 0,
                        // Punkt X, Punkt Y
                        100, 0,
                        // Strichbreite
                        1.0f,
                        // gültig = true | ungültig = false
                        true
                ),
                // TL-P08: Der Punkt liegt auf der verlängerten Geraden vor dem Startpunkt.
                Arguments.of(
                        "TL-P08: (0,0) -> (100,0), Punkt=(-10,0), strokeWidth=1.0, erwartet=false",
                        0, 0,
                        100, 0,
                        -10, 0,
                        1.0f,
                        false
                ),
                // TL-P09: Der Punkt liegt auf der verlängerten Geraden hinter dem Endpunkt.
                Arguments.of(
                        "TL-P09: (0,0) -> (100,0), Punkt=(110,0), strokeWidth=1.0, erwartet=false",
                        0, 0,
                        100, 0,
                        110, 0,
                        1.0f,
                        false
                ),
                // TL-P10: Bei einer größeren Strichstärke liegt der Punkt innerhalb der erweiterten Toleranz.
                Arguments.of(
                        "TL-P10: (0,0) -> (100,0), Punkt=(50,7), strokeWidth=10.0, erwartet=true",
                        0, 0,
                        100, 0,
                        50, 7,
                        10.0f,
                        true
                ),
                // TL-P11: Bei einer größeren Strichstärke liegt der Punkt außerhalb der erweiterten Toleranz.
                Arguments.of(
                        "TL-P11: (0,0) -> (100,0), Punkt=(50,9), strokeWidth=10.0, erwartet=false",
                        0, 0,
                        100, 0,
                        50, 9,
                        10.0f,
                        false
                )
        );
    }

    // Prüft, ob containsPoint() für die verschiedenen Punktpositionen das erwartete Ergebnis liefert.
    @ParameterizedTest(name = "{0}")
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

        // Erzeugt den Startpunkt der zu testenden Linie.
        Point start = new Point(startX, startY);
        // Erzeugt den Endpunkt der zu testenden Linie.
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

    // Bereich 3: draw()
    // Liefert die Testdaten für die Prüfung der Zeichenfunktion.
    static Stream<Arguments> drawCases() {
        // Gibt die Testfälle für verschiedene Farben und Strichstärken zurück.
        return Stream.of(

                // TL-D01: Eine schwarze Linie mit einer Strichstärke von 1.0f.
                Arguments.of(
                        "TL-D01: color=BLACK, strokeWidth=1.0f",
                        Color.BLACK,
                        1.0f
                ),

                // TL-D02: Eine rote Linie mit einer Strichstärke von 3.0f.
                Arguments.of(
                        "TL-D02: color=RED, strokeWidth=3.0f",
                        Color.RED,
                        3.0f
                )
        );
    }

    // Prüft, ob draw() eine Linie mit der erwarteten Farbe auf ein Bild zeichnet.
    @ParameterizedTest(name = "{0}")
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
    static Stream<Arguments> graphicsStateCases() {
        // Gibt den definierten Testfall TL-D03 zurück.
        return Stream.of(
                // TL-D03 prüft die Wiederherstellung der ursprünglichen Farbe und des ursprünglichen Strokes.
                Arguments.of(
                        "TL-D03: color=BLUE, strokeWidth=2.0f, vorherige Farbe=GREEN, vorheriger Stroke=5.0f",
                        Color.BLUE,
                        2.0f
                )
        );
    }

    // Prüft, ob draw() die ursprünglichen Graphics2D-Einstellungen wiederherstellt.
    @ParameterizedTest(name = "{0}")
    @MethodSource("graphicsStateCases")
    void testDrawRestoresGraphicsState(
            String testCase,
            Color lineColor,
            float lineStrokeWidth
    ) {
        // Erzeugt eine Zeichenfläche für den Test.
        BufferedImage image = new BufferedImage(
                30,
                30,
                BufferedImage.TYPE_INT_ARGB
        );
        // Erzeugt das Graphics2D-Objekt für die Zeichenfläche.
        Graphics2D graphics = image.createGraphics();
        try {
            // Legt eine vorherige Farbe des Graphics2D-Objekts fest.
            Color oldColor = Color.GREEN;
            // Legt einen vorherigen Stroke des Graphics2D-Objekts fest.
            Stroke oldStroke = new BasicStroke(5.0f);
            // Setzt die vorherige Farbe im Graphics2D-Objekt.
            graphics.setColor(oldColor);
            // Setzt den vorherigen Stroke im Graphics2D-Objekt.
            graphics.setStroke(oldStroke);
            // Erzeugt die Linie, die mit eigenen Einstellungen gezeichnet werden soll.
            myLine = new Line(
                    new Point(5, 5),
                    new Point(20, 5),
                    lineColor,
                    lineStrokeWidth
            );
            // Zeichnet die Linie und verändert dabei vorübergehend den Graphics2D-Zustand.
            myLine.draw(graphics);
            // Prüft, ob nach draw() die ursprüngliche Farbe wiederhergestellt wurde.
            assertEquals(oldColor, graphics.getColor());
            // Prüft, ob nach draw() dasselbe Stroke-Objekt wiederhergestellt wurde.
            assertSame(oldStroke, graphics.getStroke());
        } finally {
            // Gibt die Ressourcen des Graphics2D-Objekts frei.
            graphics.dispose();
        }
    }
}