public class Donut extends Shape{
    private static double outerRadius = 0.20;
    private static double innerRadius = 0.10;
    private static double area = 0.18;
    private static double unitPrice = Math.rint(Math.PI);
    Donut(){
        super(unitPrice, "Donut", outerRadius, innerRadius ,area);
        calculateArea();
        super.dimensionList[dimensionList.length-1] = this.area;
        calculatUnitPrice();
    }
// This method is responsible for calculateArea.
    public static void calculateArea(){
        area = Math.rint(((2*Math.PI * (outerRadius * outerRadius) - 2*Math.PI * (innerRadius * innerRadius)) * 100))/100;
    }
// This method is responsible for calculatUnitPrice.
    public void calculatUnitPrice(){
        super.unitPrice = Math.rint(this.unitPrice * area*100)/100;
    }
// This method is responsible for toString.
    public String toString(){
        return ("Dimensions: " +innerRadius+ " m(inner radius), " + outerRadius + " m(outer radius) ,area: " + area + "m^2");
    }
    @Override
// This method is responsible for setDimensions.
    public void setDimensions(double ... i){
        outerRadius = i[0];
        innerRadius = i[1];
        calculateArea();
    }
    @Override
// This method is responsible for setDimList.
    public void setDimList(){
        super.dimensionList[0] = innerRadius;
        super.dimensionList[1] = outerRadius;
        super.dimensionList[2] = area;
        calculatUnitPrice();
    }
}