public class Pentagon extends Shape{
    private static double inRadius = 0.20;
    private static double area = 0.14;
    private static double unitPrice = 11.6;
    Pentagon(){
        super(unitPrice, "Pentagon", inRadius,area);
        
        calculateArea();
        super.dimensionList[dimensionList.length-1] = this.area;
        super.unitPrice = unitPrice * area;
        calculatUnitPrice();
    }
// This method is responsible for calculateArea.
    public static void calculateArea(){
        area = Math.rint((inRadius * 6 * Math.sqrt(inRadius) * 100))/100;
    }
// This method is responsible for calculatUnitPrice.
    public void calculatUnitPrice(){
        super.unitPrice = Math.rint(this.unitPrice * area*100)/100;
    }
// This method is responsible for toString.
    public String toString(){
        return ("Dimensions: " +inRadius+ " m(radius), area: " + area + "m^2");
    }
    @Override
// This method is responsible for setDimensions.
    public void setDimensions(double ... i){
        inRadius = i[0];
        calculateArea();
    }
    @Override
// This method is responsible for setDimList.
    public void setDimList(){
        super.dimensionList[0] = inRadius;
        super.dimensionList[1] = area;
        calculatUnitPrice();
    }
}
