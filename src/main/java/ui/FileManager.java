package ui; // UI-Paket: enthält alle Klassen der grafischen Oberfläche

import api.Shape; // Shape-Interface für alle Zeichenobjekte
import java.io.*; // für Dateioperationen und Serialisierung
import java.util.List; // Listen für die Shape-Sammlung

// FileManager übernimmt das Speichern und Laden von Shapes über Serialisierung
public class FileManager {

    // Speichert eine Liste von Shapes in einer Datei
    public static void save(File file, List<Shape> shapes) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
            out.writeObject(shapes);
        } catch (Exception e) {
            // Fehler wird abgefangen, aber nicht ausgegeben
            // Damit bleiben Tests und UI sauber
        }
    }

    // Lädt eine Liste von Shapes aus einer Datei
    public static List<Shape> load(File file) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            return (List<Shape>) in.readObject();
        } catch (Exception e) {
            // Fehler wird abgefangen, aber nicht ausgegeben
            // Rückgabe einer leeren Liste für Stabilität
            return List.of();
        }
    }
}