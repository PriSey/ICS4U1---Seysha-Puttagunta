import java.util.HashMap;

public class Item {
    private static HashMap<String, Integer> counter = new HashMap<>() {
        {
            for (String i : Items.categories) {
                put(i, 1);
            }
        }
    };
    private String sku;
    private String name;
    private String category;
    private int quantity;
    private int minimumQuantity;
    private double vendorPrice;
    private int markup;
    private double regularPrice;
    private int currentDiscount;
    private double currentPrice;

    // Constructor to initialize an item with specified properties and calculate derived values
    Item(String name, String category, int quantity, int minimumQuantity, double vendorPrice, int markup,
            int currentDiscount) {
        this.name = name;
        this.category = category;
        this.quantity = quantity;
        this.minimumQuantity = minimumQuantity;
        this.vendorPrice = vendorPrice;
        this.markup = markup;
        this.regularPrice = Math.rint((((double) markup / 100) + 1) * vendorPrice * 100) / 100;
        this.currentDiscount = currentDiscount;
        this.currentPrice = Math.rint(((1 - ((double) currentDiscount / 100)) * regularPrice) * 100) / 100;
        generateSku();
    }

    // Generates a unique SKU (Stock Keeping Unit) for the item based on its category and counter
    private void generateSku() {
        int count = counter.get(category);
        sku = category.substring(0, 3) + "-" + String.format("%04d", count);
        counter.put(category, count + 1);
    }

    // Converts the item object into a comma-separated string representation
    public String toString() {
        return sku + "," + name + "," + category + "," + quantity + "," + minimumQuantity + "," + vendorPrice + "," + markup + ","
                + regularPrice + "," + currentDiscount + "," + currentPrice;
    }

    // Returns the category of the item
    public String getCategory() {
        return category;
    }

    // Returns the name of the item
    public String getName() {
        return name;
    }

    // Returns the SKU (Stock Keeping Unit) of the item
    public String getSKU() {
        return sku;
    }

    // Checks if two items are equal by comparing their SKUs
    public Boolean equals(Item other) {
        return this.sku.equals(other.getSKU());
    }
}
