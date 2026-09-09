package logic; // Logic-Paket: enthält logische Komponenten des Programms


// Factory-Klasse zur Umwandlung eines String-Wertes in einen ShapeType
public class ShapeFactory {

    // Erzeugt einen ShapeType basierend auf einem String
    public static ShapeType createShape(String type) {

        // Falls kein Typ angegeben wurde: kein Ergebnis
        if (type == null) {
            return null;
        }

        // String in Kleinbuchstaben umwandeln und passenden Enum zurückgeben
        switch (type.toLowerCase()) {

            // Ellipse auswählen
            case "ellipse":
                return ShapeType.ELLIPSE;

            // Linie auswählen
            case "line":
                return ShapeType.LINE;

            // Rechteck auswählen
            case "rectangle":
                return ShapeType.RECTANGLE;

            // Unbekannter Typ = kein Ergebnis
            default:
                return null;
        }
    }
}