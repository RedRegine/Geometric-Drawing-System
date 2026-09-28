# Geometric Drawing System

## 1. Description:
### The Geometric Drawing System is a Java Swing application that allows users to draw geometric shapes using the mouse.
The program supports:
- lines
- rectangles
- ellipses

Shapes can be created interactively, displayed on a drawing canvas, saved as an image, and loaded again.
Additional features such as undo, eraser mode, and color selection enhance the drawing experience.

## 2. Features
### Drawing
- Draw lines, rectangles, and ellipses using mouse press → drag → release.
- Live preview while dragging.
- Adjustable stroke width and color.
### Editing
- Undo last shape.
- Eraser mode: remove shapes by clicking on them.
- Clear canvas (New File).
### Saving & Loading
- Save the current drawing as a JPG image.
- Load JPG images into the canvas.
- Automatic file extension handling.
- Overwrite protection when saving.
### User Interface
- Menu bar with File, Shapes, and Extras menus.
- Toolbar with icons for quick access to all functions.
- Custom vector-based icons for shapes and tools.
- Keyboard shortcuts (Ctrl+N, Ctrl+O, Ctrl+S, Alt+F/S/E).

## 3. Installation
### Requirements
- Java 17 or higher
- Any operating system supporting Java (Windows, macOS, Linux)
### Steps
1. Clone or download the project repository:
- https://github.com/RedRegine/Geometric-Drawing-System.git
2. Ensure the folder structure matches the package layout (api, model, logic, ui).
3. Compile the project:
- cmd: javac -d out src/**/*.java
4. Run the application:
- cmd: java -cp out ui.Main
- Alternatively, open the project in an IDE (IntelliJ, Eclipse, NetBeans) and run ui.Main.

## 4. Program Usage
### Drawing Shapes
1. Select a shape type (Line, Rectangle, Ellipse) from the menu or toolbar.
2. Click and drag on the canvas.
3. Release the mouse to finalize the shape.
#### Color Selection
- Use Extras → Choose Color or the toolbar color icon.
- Newly created shapes use the selected color.
#### Undo
- Removes the last created shape.
- Available via Extras → Undo Last Shape or toolbar.
#### Eraser Mode
- Activate via Extras → Eraser Mode or toolbar.
- Click on shapes to remove them.
- Drawing is disabled while eraser mode is active.
#### Saving
- Use File → Save or toolbar.
- Saves the current canvas as a JPG file.
- Prompts before overwriting existing files.
#### Loading
- Use File → Load or toolbar.
- Loads a JPG image into the canvas.
#### New File
- Clears the canvas after confirmation.

## 5. Architecture
### Packages:
#### api  
Contains the Shape interface used by all geometric shapes.
#### model  
Contains concrete shape implementations: Line, Rectangle, Ellipse.
#### logic  
Contains helper logic such as ShapeFactory and ShapeType.
#### ui  
Contains all user interface classes:
- DrawingPanel (core drawing logic)
- MainFrame (main window)
- ShapeIcon (toolbar icons)
- FileManager (saving/loading shapes)
- Main (entry point)
#### Design Principles
- MVC‑inspired separation
1. Model: shape classes
2. View: DrawingPanel + MainFrame
3. Controller: mouse events inside DrawingPanel
- Serializable shapes: Enables saving and loading shape lists.
- BufferedImage rendering: Ensures stable and flicker‑free drawing.
- Robust error handling: File operations never crash the UI.

## 6. Typical Use Case
1. User starts the application.
2. Selects a shape type (e.g., Rectangle).
3. Chooses a color.
4. Draws several shapes on the canvas.
5. Removes one shape using the eraser.
6. Saves the drawing as a JPG file.
7. Loads the saved image later to continue working.
8. Uses undo to revert the last action.
9. Clears the canvas to start a new drawing.