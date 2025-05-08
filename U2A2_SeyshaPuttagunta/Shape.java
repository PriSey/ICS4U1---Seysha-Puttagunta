public abstract class Shape {
// This field stores unitPrice.
    protected double unitPrice;
// This field stores dimensionList.
    protected double[] dimensionList;
// This field stores name.
    protected String name;

    Shape(double unitPrice, String name, double ... dimensionList){
        this.unitPrice = unitPrice;
        this.name = name;
        this.dimensionList = dimensionList;
    }
// This method is responsible for getUnitPrice.
    public double getUnitPrice(){
        return unitPrice;
    }
// This method is responsible for getDimensionList.
    public double[] getDimensionList(){
        return dimensionList;
    }
// This method is responsible for getName.
    public String getName(){
        return name;
    }
// This method is responsible for toString.
    public String toString() {
        return super.toString();
    }
// This method is responsible for equals.
    public boolean equals(Shape other){
        return this.name.equals(other.name);
    }
// This method is responsible for getArea.
    public double getArea(){
        return dimensionList[dimensionList.length -1];
    }
// This method is responsible for setDimensions.
    public abstract void setDimensions(double ... i);
// This method is responsible for setDimList.
    public abstract void setDimList();
// This method is responsible for setDimList.
    public void setDimList(double ... dims){
        for(int i = 0; i < dims.length;i++){
            dimensionList[i] = dims[i];
        }
    }
}
