package ui; // Paket für alle UI-bezogenen Klassen

import logic.ShapeType; // Import des Enums für die Formtypen

import javax.swing.*; // Swing-Komponenten: JFrame, JMenu, JMenuItem, JMenuBar, JButton, JToolBar
import javax.swing.filechooser.FileNameExtensionFilter; // nur Dateien vom Typ JPG anzeigen
import javax.imageio.ImageIO; // zum Laden und Speichern von Bildern

import java.awt.*; // AWT für Layouts (BorderLayout)
import java.awt.event.KeyEvent; // für Tastaturkürzel
import java.awt.event.InputEvent; // für Tastaturkürzel mit STRG
import java.awt.image.BufferedImage; // Bild, das geladen und gespeichert wird
import java.io.File; // File-Klasse für Laden/Speichern
import java.io.IOException; // für mögliche Fehler beim Laden und Speichern

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
        // Menü kann über die Tastatur mit ALT + F geöffnet werden
        fileMenu.setMnemonic(KeyEvent.VK_F);
        // Menüpunkt: neue Datei (Canvas leeren)
        JMenuItem newItem = new JMenuItem("New");
        // Tastaturkürzel für "New"
        newItem.setAccelerator(
                KeyStroke.getKeyStroke(
                        KeyEvent.VK_N,
                        InputEvent.CTRL_DOWN_MASK
                )
        );

        // Menüpunkt: Datei laden
        JMenuItem loadItem = new JMenuItem("Load");
        // Tastaturkürzel für "Load"
        loadItem.setAccelerator(
                KeyStroke.getKeyStroke(
                        KeyEvent.VK_O,
                        InputEvent.CTRL_DOWN_MASK
                )
        );

        // Menüpunkt: Datei speichern
        JMenuItem saveItem = new JMenuItem("Save");
        // Tastaturkürzel für "Save"
        saveItem.setAccelerator(
                KeyStroke.getKeyStroke(
                        KeyEvent.VK_S,
                        InputEvent.CTRL_DOWN_MASK
                )
        );

        // Menüpunkt: Programm beenden
        JMenuItem exitItem = new JMenuItem("Exit");
        // Aktion: Canvas löschen mit Abfrage an den User
        newItem.addActionListener(e -> newFile());
        // Aktion: Datei laden
        loadItem.addActionListener(e -> loadFile());
        // Aktion: Datei speichern
        saveItem.addActionListener(e -> saveFile());
        // Aktion: Programm beenden
        exitItem.addActionListener(e -> System.exit(0));

        // Menüpunkt hinzufügen neue Datei
        fileMenu.add(newItem);
        // Menüpunkt hinzufügen laden
        fileMenu.add(loadItem);
        // Menüpunkt hinzufügen sichern
        fileMenu.add(saveItem);
        // Menüpunkt hinzufügen beenden
        fileMenu.add(exitItem);


        // Menü für Formauswahl
        JMenu shapeMenu = new JMenu("Shapes");
        // Menü kann über die Tastatur mit ALT + S geöffnet werden
        shapeMenu.setMnemonic(KeyEvent.VK_S);
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
        // Menü kann über die Tastatur mit ALT + E geöffnet werden
        extraMenu.setMnemonic(KeyEvent.VK_E);

        // Farbauswahl für die Formen (Color Picker)
        JMenuItem colorItem = new JMenuItem("Choose Color");
        // Auswahl durch User verarbeiten
        colorItem.addActionListener(e -> {
            Color c = JColorChooser.showDialog(
                    this,
                    "Choose Shape Color",
                    drawingPanel.getCurrentColor()
            );
            // User hat keine Auswahl getroffen
            if (c != null) {
                drawingPanel.setCurrentColor(c);
            }
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
        // Extra-Menü zur Menüleiste hinzufügen
        bar.add(extraMenu);
        // Menüleiste zurückgeben
        return bar;
    }


    // Methode erzeugt die Symbolleiste
    private JToolBar createToolBar() {

        // Symbolleiste erzeugen
        JToolBar tb = new JToolBar();

        // Datei
        // Button mit Symbolen erzeugen: Neu, Laden, Speichern und Beenden
        // Neue Datei: mit Symbol aus jlfgr-1_0.jar
        JButton newBtn = new JButton();
        newBtn.setIcon(new ImageIcon(
                getClass().getResource(
                        "/toolbarButtonGraphics/general/New16.gif"
                )
        ));
        newBtn.setToolTipText("New");
        // Laden: mit Symbol aus jlfgr-1_0.jar
        JButton loadBtn = new JButton();
        loadBtn.setIcon(new ImageIcon(
                getClass().getResource(
                        "/toolbarButtonGraphics/general/Open16.gif"
                )
        ));
        loadBtn.setToolTipText("Load");
        // Speichern: mit Symbol aus jlfgr-1_0.jar
        JButton saveBtn = new JButton();
        saveBtn.setIcon(new ImageIcon(
                getClass().getResource(
                        "/toolbarButtonGraphics/general/Save16.gif"
                )
        ));
        saveBtn.setToolTipText("Save");

        // Exit bleibt zunächst ein Text-Button
        JButton exitBtn = new JButton("Exit");
        exitBtn.setToolTipText("Exit");

        // Aktionen ausführen (Klick = Trigger) : Neu, Laden, Speichern und Beenden
        newBtn.addActionListener(e -> newFile());
        loadBtn.addActionListener(e -> loadFile());
        saveBtn.addActionListener(e -> saveFile());
        exitBtn.addActionListener(e -> System.exit(0));

        // Btn (Button) hinzufügen: Neu, Laden, Speichern und Beenden
        tb.add(newBtn);
        tb.add(loadBtn);
        tb.add(saveBtn);
        tb.add(exitBtn);

        // Trennlinie zwischen den Kategorien
        tb.addSeparator();


        // Formen
        // Button erzeugen: Linie, Rechteck, Ellipse
        JButton lineBtn = new JButton("Line");
        JButton rectBtn = new JButton("Rectangle");
        JButton ellipseBtn = new JButton("Ellipse");

        // Aktionen ausführen (Klick = Trigger) : Linie, Rechteck oder Ellipse setzen
        lineBtn.addActionListener(e -> drawingPanel.setCurrentType(ShapeType.LINE));
        rectBtn.addActionListener(e -> drawingPanel.setCurrentType(ShapeType.RECTANGLE));
        ellipseBtn.addActionListener(e -> drawingPanel.setCurrentType(ShapeType.ELLIPSE));

        // Btn (Button) hinzufügen: Linie, Rechteck, Ellipse
        tb.add(lineBtn);
        tb.add(rectBtn);
        tb.add(ellipseBtn);

        // Trennlinie zwischen den Formen und Extras
        tb.addSeparator();

        // EXTRAS
        // Aktion: Farbe für die Formen setzen
        JButton colorBtn = new JButton("Color");

        // Farbe setzen
        colorBtn.addActionListener(e -> {

            // Dialog anzeigen und User Farbe bestimmen lassen
            Color c = JColorChooser.showDialog(
                    this,
                    "Choose Shape Color",
                    drawingPanel.getCurrentColor()
            );

            // Hat der User ausgewählt, dann setzen
            if (c != null) {
                drawingPanel.setCurrentColor(c);
            }
        });

        // Aktion: Undo setzen
        JButton undoBtn = new JButton("Undo");

        // Letzte Form zurücknehmen
        undoBtn.addActionListener(e -> drawingPanel.undoLastShape());

        // Aktion: Radiergummi setzen
        JButton eraserBtn = new JButton("Eraser");

        eraserBtn.addActionListener(e -> drawingPanel.enableEraserMode());

        // Btn (Button) hinzufügen: Farbauswahl, Rückkängig und Radiergummi
        tb.add(colorBtn);
        tb.add(undoBtn);
        tb.add(eraserBtn);

        // Symbolleiste zurückgeben
        return tb;
    }


    // Methode zum Speichern der Shapes
    private void saveFile() {

        // Datei-Dialog erzeugen
        JFileChooser chooser = new JFileChooser();

        // Nur JPG-Dateien anzeigen
        FileNameExtensionFilter filter =
                new FileNameExtensionFilter(
                        "JPG-Files (*.jpg)",
                        "jpg",
                        "jpeg"
                );

        // Filter für .jpg setzen
        chooser.setFileFilter(filter);
        // Andere Dateitypen im Dialog ausblenden (nur .jpg anzeigen)
        chooser.setAcceptAllFileFilterUsed(false);
        // Wenn der Benutzer eine Datei ausgewählt hat
        if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            // Dateiobjekt holen
            File file = chooser.getSelectedFile();
            // Falls der Benutzer keine Endung angegeben hat, .jpg ergänzen
            if (!file.getName().toLowerCase().endsWith(".jpg")
                    && !file.getName().toLowerCase().endsWith(".jpeg")) {

                file = new File(file.getAbsolutePath() + ".jpg");
            }

            // Prüfen, ob die Datei bereits existiert
            if (file.exists()) {
                // User die Chance geben, die Datei zu überschreiben
                int result = JOptionPane.showConfirmDialog(
                        this,
                        "The file already exists.\n"
                                + "Would you like to overwrite them?",
                        "Overwrite file.",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );

                // Wenn "Nein" gewählt wurde, Speichern abbrechen
                if (result != JOptionPane.YES_OPTION) {
                    return;
                }
            }

            try {
                BufferedImage image = drawingPanel.getImage();
                // Prüfen, ob überhaupt ein Bild vorhanden ist
                if (image == null) {
                    JOptionPane.showMessageDialog(
                            this,
                            "There is no image to save.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }

                // Bild speichern
                boolean success = ImageIO.write(image, "jpg", file);
                // Prüfen, ob das Speichern tatsächlich erfolgreich war
                if (!success) {
                    JOptionPane.showMessageDialog(
                            this,
                            "The image could not be saved.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                }

            } catch (IOException ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Error saving the file:\n"
                                + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }


    // Methode zum Laden der Shapes
    private void loadFile() {

        // Datei-Dialog erzeugen
        JFileChooser chooser = new JFileChooser();
        // Nur JPG-Dateien anzeigen
        FileNameExtensionFilter filter =
                new FileNameExtensionFilter(
                        "JPG-Dateien (*.jpg)",
                        "jpg",
                        "jpeg"
                );

        // Filter für .jpg setzen
        chooser.setFileFilter(filter);
        // Andere Dateitypen im Dialog ausblenden
        chooser.setAcceptAllFileFilterUsed(false);
        // Wenn der Benutzer eine Datei ausgewählt hat
        // Wenn der Benutzer eine Datei ausgewählt hat
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            // Ausgewählte Datei holen
            File file = chooser.getSelectedFile();
            // Prüfen, ob die Datei existiert
            if (!file.exists() || !file.isFile()) {
                JOptionPane.showMessageDialog(
                        this,
                        "The selected file does not exist.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            try {
                // Bild laden
                BufferedImage image = ImageIO.read(file);
                // Prüfen, ob die Datei tatsächlich ein gültiges Bild enthält
                if (image == null) {
                    JOptionPane.showMessageDialog(
                            this,
                            "The file does not contain a valid .jpg image.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }
                // Erst nach erfolgreichem Laden das aktuelle Bild ersetzen
                drawingPanel.setImage(image);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(
                        this,
                        "Error loading file:\n"
                                + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    // Methode zum Anlegen einer neuen Datei
    private void newFile() {
        // Ergebnisabfrage für den User
        int result = JOptionPane.showConfirmDialog(
                this,
                "Do you really want to create a new file??\n"
                        + "The current content will be deleted.",
                "New File",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        // Nur wenn der Benutzer "Ja" auswählt, wird die Zeichenfläche geleert
        if (result == JOptionPane.YES_OPTION) {
            drawingPanel.clear();
        }
    }
}