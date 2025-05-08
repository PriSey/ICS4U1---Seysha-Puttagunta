public class Triangle extends Shape{
    private static double width = 0.30;
    private static double length = 0.61;
    private static double area;
    private static double unitPrice = 2.98;
    Triangle(){
        super(unitPrice, "Triangle", width,length,area);
        calculateArea();
        super.dimensionList[dimensionList.length-1] = this.area;
        super.unitPrice = unitPrice * area;
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
    }
}
    

