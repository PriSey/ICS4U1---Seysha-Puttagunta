import javax.swing.*;
public class ShapeBox extends JCheckBox{
// This field stores shape.
    private Shape shape;
    ShapeBox(String txt, Shape shape){
        this.shape = shape;
        setText(txt);
    }
// This method is responsible for getShape.
    public Shape getShape(){
        return shape;
    }
}
