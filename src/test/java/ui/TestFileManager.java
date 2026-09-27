package ui; // Testpaket entspricht dem Paket der zu testenden Klasse

import api.Shape; // Shape-Interface für alle Zeichenobjekte
import model.Rectangle; // Beispielhafte konkrete Shape-Klasse
import org.junit.jupiter.api.BeforeEach; // Vorbereitung vor jedem Test
import org.junit.jupiter.api.AfterEach; // Nachbereitung nach jedem Test
import org.junit.jupiter.params.ParameterizedTest; // Markiert parametrisierte Tests
import org.junit.jupiter.params.provider.MethodSource; // Bindet Streams als Testdatenquelle
import java.awt.Point; // Importiert Point für Start- und Endpunkte
import java.io.File; // für Dateioperationen und Serialisierung
import java.util.List; // Für Shape-Listen
import static org.junit.jupiter.api.Assertions.assertEquals; // Importiert Assertions zum Vergleichen von erwarteten und tatsächlichen Ergebnissen.
import static org.junit.jupiter.api.Assertions.assertNotNull; // Importiert die Assertion zum Prüfen auf einen nicht leeren beziehungsweise nicht null-Wert.
import static org.junit.jupiter.api.Assertions.assertTrue; // Importiert die Assertion zum Prüfen auf true-Werte.
import java.util.stream.Stream; // Importiert Stream, um die Testdaten für die parametrisierten Tests bereitzustellen.

// Testklasse für die Klasse FileManager
public class TestFileManager {
    // Temporäre Datei für jeden Test
    private File tempFile;

    // Diese Methode wird vor jedem einzelnen Testfall ausgeführt.
    @BeforeEach
    void setup() throws Exception {
        // Temporäre Datei erzeugen
        tempFile = File.createTempFile("test_shapes", ".dat");
        // Datei nach Test automatisch löschen
        tempFile.deleteOnExit();
    }

    // Diese Methode wird nach jedem einzelnen Testfall ausgeführt.
    @AfterEach
    void tearDown() {
        // Datei freigeben
        tempFile = null;
    }


    // Liefert die Testdaten für die Prüfung von safe()
    static Stream<Object[]> saveCases() {
        // Erstellt einen Stream mit allen Testfällen für safe()
        return Stream.<Object[]>of(

                // TFM-S01 prüft ob eine gültige Liste gespeichert werden kann
                new Object[]{
                        "TFM-S01: save() mit gültiger Shape-Liste, erwartet=Datei wird geschrieben",
                        List.of(new Rectangle(new Point(10, 10), new Point(50, 50))), // Shape-Liste
                        true // Erwartung: Datei enthält Formen
                },

                // TFM-S02 prüft ob eine leere Liste gespeichert werden kann
                new Object[]{
                        "TFM-S02: save() mit leerer Liste, erwartet=Datei enthält leere Liste",
                        List.of(), // Leere Liste
                        true // Erwartung: Datei enthält leere Liste
                },

                // TFM-S03 prüft ob eine Datei überschreibbar ist
                new Object[]{
                        "TFM-S03: save() mit nicht beschreibbarer Datei, erwartet=Fehler abgefangen",
                        List.of(new Rectangle(new Point(10, 10), new Point(50, 50))), // Shape-Liste
                        false // Erwartung: Datei bleibt leer
                }
        );
    }

    // Führt die parametrisierten Tests für safe() aus.
    @ParameterizedTest(name = "{0}")
    // Verwendet die oben definierten Testdaten.
    @MethodSource("saveCases")
    void testSave(String testId, List<Shape> shapes, boolean expectSuccess) throws Exception {
        // Falls Testfall TFM-S03: Datei unbeschreibbar machen
        if (!expectSuccess) {
            tempFile.setWritable(false);
        }
        // Speichern ausführen
        FileManager.save(tempFile, shapes);
        // Datei wieder beschreibbar machen (für Cleanup)
        tempFile.setWritable(true);
        // Datei laden
        List<Shape> loaded = FileManager.load(tempFile);
        // Erwartung prüfen
        if (expectSuccess) {
            assertEquals(shapes.size(), loaded.size());
        } else {
            assertTrue(loaded.isEmpty());
        }
    }


    // Liefert die Testdaten für die Prüfung von load()
    static Stream<Object[]> loadCases() {
        return Stream.<Object[]>of(

                // TFM-L01 prüft ob eine gültige Datei geladen werden kann
                new Object[]{
                        "TFM-L01: load() mit gültiger Datei, erwartet=Shapes werden geladen",
                        List.of(new Rectangle(new Point(10, 10), new Point(50, 50))), // Shape-Liste
                        true // Erwartung: Liste nicht leer
                },

                // TFM-L02 prüft ob eine leere Liste geladen werden kann
                new Object[]{
                        "TFM-L02: load() mit leerer Liste, erwartet=leere Liste zurückgegeben",
                        List.of(),
                        true // Erwartung: Liste leer
                },

                // TFM-L03 prüft ob eine Datei geladen werden kann die nicht existiert
                new Object[]{
                        "TFM-L03: load() mit nicht existierender Datei, erwartet=leere Liste",
                        null, // Datei wird später gelöscht
                        false // Erwartung: leere Liste
                },

                // TFM-L04 prüft ob eine Datei geladen werden kann, wenn diese beschädigte ist
                new Object[]{
                        "TFM-L04: load() mit beschädigter Datei, erwartet=leere Liste",
                        "INVALID_DATA", // Datei wird überschrieben
                        false // Erwartung: leere Liste
                },

                // TFM-L05 prüft ob eine Datei geladen werden kann, wenn diese einen falschen Datentyp enthält
                new Object[]{
                        "TFM-L05: load() mit falschem Datentyp, erwartet=leere Liste",
                        12345, // ungültiges Objekt
                        false // Erwartung: leere Liste
                }
        );
    }

    // Führt die parametrisierten Tests für load() aus.
    @ParameterizedTest(name = "{0}")
    // Verwendet die oben definierten Testdaten.
    @MethodSource("loadCases")
    void testLoad(String testId, Object content, boolean expectValid) throws Exception {
        // Fall: Datei existiert nicht
        if (content == null) {
            tempFile.delete(); // Datei löschen
        }
        // Fall: Datei enthält gültige Shape-Liste
        else if (content instanceof List<?>) {
            FileManager.save(tempFile, (List<Shape>) content);
        }
        // Fall: Datei enthält ungültige Daten
        else {
            try (var out = new java.io.FileWriter(tempFile)) {
                out.write(content.toString());
            }
        }
        // Laden ausführen
        List<Shape> loaded = FileManager.load(tempFile);
        // Erwartung prüfen
        if (expectValid) {
            assertNotNull(loaded);
        } else {
            assertTrue(loaded.isEmpty());
        }
    }
}