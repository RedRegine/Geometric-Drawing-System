package ui; // UI-Paket: enthält alle Klassen der grafischen Oberfläche

import api.Shape; // Shape-Interface für alle Zeichenobjekte
import java.io.*; // für Dateioperationen und Serialisierung
import java.util.List; // Listen für die Shape-Sammlung

// FileManager übernimmt das Speichern und Laden von Shapes über Serialisierung
public class FileManager {
    // Speichert eine Liste von Shapes in einer Datei
    public static void save(File file, List<Shape> shapes) {
        // ObjectOutputStream schreibt Objekte in eine Datei
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
            // Liste der Shapes serialisiert in die Datei schreiben
            out.writeObject(shapes);
        } catch (Exception e) {
            // Fehlerausgabe, falls Speichern fehlschlägt
            e.printStackTrace();
        }
    }

    // Lädt eine Liste von Shapes aus einer Datei
    public static List<Shape> load(File file) {
        // ObjectInputStream liest Objekte aus einer Datei
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            // Dateiinhalt zurück in eine Shape-Liste konvertieren
            return (List<Shape>) in.readObject();
        } catch (Exception e) {
            // Fehlerausgabe, falls Laden fehlschlägt
            e.printStackTrace();
            // Leere Liste zurückgeben, damit das Programm stabil bleibt
            return List.of();
        }
    }
}

