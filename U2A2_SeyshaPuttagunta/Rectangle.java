import java.util.Arrays;

public class Rectangle extends Shape {
    private static double width = 0.30;
    private static double length = 0.60;
    private static double area = 1.90;
    private static double unitPrice = 2.98;
    Rectangle(){
        super(unitPrice, "Rectangle", width,length,area);
        calculateArea();
        super.dimensionList[dimensionList.length-1] = this.area;
        calculatUnitPrice();
    }
// This method is responsible for calculateArea.
    public static void calculateArea(){
        area = Math.rint((width * length * 100))/100;
    }
// This method is responsible for calculatUnitPrice.
    public void calculatUnitPrice(){
        super.unitPrice = Math.rint(this.unitPrice * area*100)/100;
    }
// This method is responsible for toString.
    public String toString(){
        return ("Dimensions: " +width + "x" + length+" m, area: " + area + "m^2");
    }
    @Override
// This method is responsible for setDimensions.
    public void setDimensions(double ... i){
        width = i[0];
        length = i[1];
        calculateArea();
    }
    @Override
// This method is responsible for setDimList.
    public void setDimList(){
        super.dimensionList[0] = width;
        super.dimensionList[1] = length;
        super.dimensionList[2] = area;
        calculatUnitPrice();
    }
}
