package ui;
// import der Formtypen
import logic.ShapeType;
// import der Swing Komponenten JFrame, JMenu, JMenuItem, JMenuBar
import javax.swing.*;
// Startklasse und Einstiegspunkt
public class Main {
    public static void main(String[] args) {
        // Fenster erzeugen (Titel oben anzeigen)
        JFrame frame = new JFrame("Geometric Drawing System");
        // Zeichenpanel erzeugen (Dieses Panel ist der zentrale Zeichenbereich)
        DrawingPanel panel = new DrawingPanel();
        // Menüleiste erstellen (oben im Fenster)
        // Menüpunkt mit dem Namen "Formen"
        JMenuBar menuBar = new JMenuBar();
        JMenu shapeMenu = new JMenu("Shapes");
        // 3 auswählbare Menüeinträge (Je eine Form die der Benutzer dann zeichnen kann)
        JMenuItem lineItem = new JMenuItem("Line");
        JMenuItem rectItem = new JMenuItem("Rectangle");
        JMenuItem ellipseItem = new JMenuItem("Ellipse");
        // Aktionen vom User für die Menüeinträge (Lambda)
        lineItem.addActionListener(e -> panel.setCurrentType(ShapeType.LINE));
        rectItem.addActionListener(e -> panel.setCurrentType(ShapeType.RECTANGLE));
        ellipseItem.addActionListener(e -> panel.setCurrentType(ShapeType.ELLIPSE));
        // Menüeinträge
        shapeMenu.add(lineItem);
        shapeMenu.add(rectItem);
        shapeMenu.add(ellipseItem);
        // Menü ins Fenster einfügen
        menuBar.add(shapeMenu);
        frame.setJMenuBar(menuBar);
        // Zeichenpanel ins Fenster einfügen
        frame.add(panel);
        // Fenster konfigurieren
        frame.setSize(800, 600);
        // Programm beenden
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // beim schließen
        frame.setVisible(true);
    }
}