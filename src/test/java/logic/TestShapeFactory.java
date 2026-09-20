package logic;

import org.junit.jupiter.api.Test; // Importiert die JUnit-Testannotation @Test, kennzeichnen der Methoden als automatisierte Testfälle
import static org.junit.jupiter.api.Assertions.*; // Importiert die JUnit-Prüfmethoden wie assertEquals() und assertNull().

// Testklasse für die Klasse ShapeFactory.
public class TestShapeFactory {
    // Testfall TSF01: Prüft, ob der String "ellipse" korrekt in ShapeType.ELLIPSE umgewandelt wird.
    @Test
    public void TSF01() {
        // Ruft die zu testende Methode mit dem gültigen Eingabewert "ellipse" auf.
        ShapeType result = ShapeFactory.createShape("ellipse");
        // Prüft, ob das tatsächliche Ergebnis ELLIPSE entspricht.
        // Erwartet wird ShapeType.ELLIPSE.
        assertEquals(ShapeType.ELLIPSE, result);
    }

    // Testfall TSF02: Prüft, ob der String "line" korrekt in ShapeType.LINE umgewandelt wird.
    @Test
    public void TSF02() {
        // Ruft die zu testende Methode mit dem gültigen Eingabewert "line" auf.
        ShapeType result = ShapeFactory.createShape("line");
        // Prüft, ob das tatsächliche Ergebnis LINE entspricht.
        // Erwartet wird ShapeType.LINE.
        assertEquals(ShapeType.LINE, result);
    }

    // Testfall TSF03: Prüft, ob der String "rectangle" korrekt in ShapeType.RECTANGLE umgewandelt wird.
    @Test
    public void TSF03() {
        // Ruft die zu testende Methode mit dem gültigen Eingabewert "rectangle" auf.
        ShapeType result = ShapeFactory.createShape("rectangle");
        // Prüft, ob das tatsächliche Ergebnis RECTANGLE entspricht.
        // Erwartet wird ShapeType.RECTANGLE.
        assertEquals(ShapeType.RECTANGLE, result);
    }

    // Testfall TSF04: Prüft, ob die Methode Großbuchstaben akzeptiert.
    // Die Factory verwendet intern toLowerCase().
    @Test
    public void TSF04() {
        // Ruft die zu testende Methode mit dem Großbuchstaben-String "ELLIPSE" auf.
        ShapeType result = ShapeFactory.createShape("ELLIPSE");
        // Prüft, ob trotz der Großschreibung ELLIPSE zurückgegeben wird.
        assertEquals(ShapeType.ELLIPSE, result);
    }

    // Testfall TSF05: Prüft das Verhalten bei der ungültigen Eingabe null.
    @Test
    public void TSF05() {
        // Ruft die zu testende Methode mit null als Eingabe auf.
        ShapeType result = ShapeFactory.createShape(null);
        // Prüft, ob die Methode bei null tatsächlich null zurückgibt.
        assertNull(result);
    }

    // Testfall TSF06: Prüft das Verhalten bei einem unbekannten Shape-Typ.
    @Test
    public void TSF06() {
        // Ruft die zu testende Methode mit dem unbekannten Wert "circle" auf.
        ShapeType result = ShapeFactory.createShape("circle");
        // Prüft, ob ein unbekannter Wert korrekt mit null beantwortet wird.
        assertNull(result);
    }
}