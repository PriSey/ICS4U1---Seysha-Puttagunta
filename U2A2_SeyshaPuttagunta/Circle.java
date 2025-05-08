public class Circle extends Shape {
// This field stores 0.20.
    private static double radius = 0.20;
// This field stores 0.25.
    private static double area = 0.25;
// This method is responsible for Math.rint.
    private static double unitPrice = Math.rint(Math.PI);
    Circle(){
        super(unitPrice, "Circle", radius,area);
        calculateArea();
        super.dimensionList[dimensionList.length-1] = this.area;
        super.unitPrice = unitPrice * area;
        calculatUnitPrice();
    }
// This method is responsible for calculateArea.
    public static void calculateArea(){
        area = Math.rint(((Math.PI * 2 * (radius * radius)) * 100))/100;
    }
// This method is responsible for calculatUnitPrice.
    public void calculatUnitPrice(){
        super.unitPrice = Math.rint(this.unitPrice * area*100)/100;
    }
// This method is responsible for toString.
    public String toString(){
        return ("Dimensions: " +radius+ " m(radius), area: " + area + "m^2");
    }
    @Override
// This method is responsible for setDimensions.
    public void setDimensions(double ... i){
        radius = i[0];
        calculateArea();
        calculatUnitPrice();
    }
    @Override
// This method is responsible for setDimList.
    public void setDimList(){
        super.dimensionList[0] = radius;
        super.dimensionList[1] = area;
    }
}
