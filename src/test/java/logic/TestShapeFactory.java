package logic;

import org.junit.jupiter.api.BeforeEach; // Importiert die Annotation für die Vorbereitung vor jedem Testfall.
import org.junit.jupiter.api.AfterEach; // Importiert die Annotation für die Nachbereitung nach jedem Testfall.
import org.junit.jupiter.params.ParameterizedTest; // Importiert die Annotation für einen parametrisierten Test.
import org.junit.jupiter.params.provider.CsvSource; // Importiert CsvSource für die Übergabe mehrerer Testfälle.
import static org.junit.jupiter.api.Assertions.assertEquals; // Importiert die Prüfung auf Gleichheit.
import static org.junit.jupiter.api.Assertions.assertNull; // Importiert die Prüfung auf null.

// Testklasse für die ShapeFactory.
public class TestShapeFactory {

    // Wird vor jedem einzelnen Testfall ausgeführt.
    @BeforeEach
    // ShapeFactory besitzt ausschließlich statische Methoden. Deshalb muss hier kein Objekt erzeugt werden.
    void before() {
    }

    // Wird nach jedem einzelnen Testfall ausgeführt.
    @AfterEach
    // ShapeFactory besitzt keinen veränderbaren Zustand. Deshalb ist hier keine Bereinigung notwendig.
    void after() {
    }

    // Führt die Testmethode mit allen angegebenen Testfällen aus.
    @ParameterizedTest(name = "{0}: {1} -> {2}")
    // Übergibt Testfall-ID, Eingabewert und erwartetes Ergebnis.
    @CsvSource(
            value = {
                    "TSF01, 'ellipse', ELLIPSE",
                    "TSF02, 'line', LINE",
                    "TSF03, 'rectangle', RECTANGLE",
                    "TSF04, 'ELLIPSE', ELLIPSE",
                    "TSF05, null, null",
                    "TSF06, 'circle', null"
            },
            nullValues = "null"
    )

    // Testet die Umwandlung eines Strings in einen ShapeType.
    void testCreateShape(
            String testCase,
            String input,
            String expected
    ) {
        // Ruft die zu testende Methode mit dem aktuellen Eingabewert auf.
        ShapeType actual = ShapeFactory.createShape(input);
        // Prüft bei ungültigen Eingaben, ob null zurückgegeben wurde.
        if (expected == null) {
            // Erwartetes Ergebnis ist null.
            assertNull(actual);
        } else {
            // Wandelt den erwarteten String in den entsprechenden ShapeType um.
            ShapeType expectedType = ShapeType.valueOf(expected);
            // Vergleicht das tatsächliche mit dem erwarteten Ergebnis.
            assertEquals(expectedType, actual);
        }
    }
}