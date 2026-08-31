package logic;

public class ShapeFactory {
    public static ShapeType createShape(String type) {
        if(type == null) {
            return null;
        }
        switch(type.toLowerCase()) {
            case "ellipse" : return ShapeType.ELLIPSE;
            case "line" : return ShapeType.LINE;
            case "rectangle" : return ShapeType.RECTANGLE;
            default : return null;
        }
    }
}