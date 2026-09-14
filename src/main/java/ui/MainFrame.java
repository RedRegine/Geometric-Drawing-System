MainFrame.java
        package ui; // Paket für alle UI-bezogenen Klassen
import logic.ShapeType; // Import des Enums für die Formtypen
import javax.swing.*; // Swing-Komponenten: JFrame, JMenu, JMenuItem, JMenuBar, JButton, JToolBar
import java.awt.*; // AWT für Layouts (BorderLayout)
import java.io.File; // File-Klasse für Laden/Speichern
// MainFrame ist das Hauptfenster der Anwendung
public class MainFrame extends JFrame {
    // Referenz auf das Zeichenpanel (zentraler Zeichenbereich)
    private final DrawingPanel drawingPanel;
    // Konstruktor: baut das komplette Fenster
    public MainFrame() {
        // Titel des Fensters setzen
        super("Geometric Drawing System");
        // Zeichenpanel erzeugen
        drawingPanel = new DrawingPanel();
        // Zeichenpanel in die Mitte des Fensters einfügen
        add(drawingPanel, BorderLayout.CENTER);
        // Menüleiste oben hinzufügen
        setJMenuBar(createMenuBar());
        // Symbolleiste direkt unter der Menüleiste hinzufügen
        add(createToolBar(), BorderLayout.NORTH);
        // Fenstergröße festlegen
        setSize(900, 700);
        // Anwendung beenden, wenn Fenster geschlossen wird
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Fenster sichtbar machen
        setVisible(true);
    }

    // Methode erzeugt die komplette Menüleiste
    private JMenuBar createMenuBar() {
        // Menüleiste erzeugen
        JMenuBar bar = new JMenuBar();
        // Datei-Menü (New, Load, Save)
        JMenu fileMenu = new JMenu("File");
        // Menüpunkt: neue Datei (Canvas leeren)
        JMenuItem newItem = new JMenuItem("New");
        // Menüpunkt: Datei laden
        JMenuItem loadItem = new JMenuItem("Load");
        // Menüpunkt: Datei speichern
        JMenuItem saveItem = new JMenuItem("Save");

        // Aktion: Canvas löschen
        newItem.addActionListener(e -> drawingPanel.clear());
        // Aktion: Datei laden
        loadItem.addActionListener(e -> loadFile());
        // Aktion: Datei speichern
        saveItem.addActionListener(e -> saveFile());

        // Menüpunkt hinzufügen
        fileMenu.add(newItem);
        // Menüpunkt hinzufügen
        fileMenu.add(loadItem);
        // Menüpunkt hinzufügen
        fileMenu.add(saveItem);

        // Menü für Formauswahl
        JMenu shapeMenu = new JMenu("Shapes");
        // Menüpunkt: Linie
        JMenuItem lineItem = new JMenuItem("Line");
        // Menüpunkt: Rechteck
        JMenuItem rectItem = new JMenuItem("Rectangle");
        // Menüpunkt: Ellipse
        JMenuItem ellipseItem = new JMenuItem("Ellipse");

        // Aktion: Formtyp Linie setzen
        lineItem.addActionListener(e -> drawingPanel.setCurrentType(ShapeType.LINE));
        // Aktion: Formtyp Rechteck setzen
        rectItem.addActionListener(e -> drawingPanel.setCurrentType(ShapeType.RECTANGLE));
        // Aktion: Formtyp Ellipse setzen
        ellipseItem.addActionListener(e -> drawingPanel.setCurrentType(ShapeType.ELLIPSE));

        // Menüpunkt Line hinzufügen
        shapeMenu.add(lineItem);
        // Menüpunkt Rectangle hinzufügen
        shapeMenu.add(rectItem);
        // Menüpunkt Ellipse hinzufügen
        shapeMenu.add(ellipseItem);
        // Extra Funktionen Menü
        JMenu extraMenu = new JMenu("Extras");
        // Farbauswahl für die Formen (Color Picker)
        JMenuItem colorItem = new JMenuItem("Choose Color");
        colorItem.addActionListener(e -> {
            Color c = JColorChooser.showDialog(this, "Choose Shape Color", drawingPanel.getCurrentColor());
            if (c != null) drawingPanel.setCurrentColor(c);
        });
        // Undo-Funktion: letzte Form löschen
        JMenuItem undoItem = new JMenuItem("Undo Last Shape");
        undoItem.addActionListener(e -> drawingPanel.undoLastShape());

        // Radiergummi: Durch Anklicken Formen löschen
        JMenuItem eraserItem = new JMenuItem("Eraser Mode");
        eraserItem.addActionListener(e -> drawingPanel.enableEraserMode());
        // Extra-Menüpunkt Farbauswahl hinzufügen
        extraMenu.add(colorItem);
        // Extra-Menüpunkt Undo hinzufügen
        extraMenu.add(undoItem);
        // Extra-Menüpunkt Radiergummi hinzufügen
        extraMenu.add(eraserItem);
        // Datei-Menü zur Menüleiste hinzufügen
        bar.add(fileMenu);
        // Shapes-Menü zur Menüleiste hinzufügen
        bar.add(shapeMenu);
        // Menüleiste zurückgeben
        return bar;
    }
    // Methode erzeugt die Symbolleiste
    private JToolBar createToolBar() {
        // Symbolleiste erzeugen
        JToolBar tb = new JToolBar();
        // Button für Linie
        JButton lineBtn = new JButton("Line");
        // Button für Rechteck
        JButton rectBtn = new JButton("Rect");
        // Button für Ellipse
        JButton ellipseBtn = new JButton("Ellipse");
        // Aktion: Linie setzen
        lineBtn.addActionListener(e -> drawingPanel.setCurrentType(ShapeType.LINE));
        // Aktion: Rechteck setzen
        rectBtn.addActionListener(e -> drawingPanel.setCurrentType(ShapeType.RECTANGLE));
        // Aktion: Ellipse setzen
        ellipseBtn.addActionListener(e -> drawingPanel.setCurrentType(ShapeType.ELLIPSE));
        // Line Button hinzufügen
        tb.add(lineBtn);
        // Rectangle Button hinzufügen
        tb.add(rectBtn);
        // Ellipse Button hinzufügen
        tb.add(ellipseBtn);
        // Symbolleiste zurückgeben
        return tb;
    }
    // Methode zum Speichern der Shapes
    private void saveFile() {
        // Datei-Dialog erzeugen
        JFileChooser chooser = new JFileChooser();
        // Wenn der Benutzer eine Datei ausgewählt hat
        if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            // Dateiobjekt holen
            File file = chooser.getSelectedFile();
            // Shapes speichern
            FileManager.save(file, drawingPanel.getShapes());
        }
    }
    // Methode zum Laden der Shapes
    private void loadFile() {
        // Datei-Dialog erzeugen
        JFileChooser chooser = new JFileChooser();
        // Wenn der Benutzer eine Datei ausgewählt hat
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            // Dateiobjekt holen
            File file = chooser.getSelectedFile();
            // Shapes laden und ins Panel setzen
            drawingPanel.setShapes(FileManager.load(file));
        }
    }
}
