import java.io.*;
import java.util.HashMap;
import java.util.ArrayList;

public class Items {
    // A map to store items categorized by their types (e.g., FRUIT, VEGETABLE, MEAT)
    public static HashMap<String, ArrayList<Item>> Items = new HashMap<String, ArrayList<Item>>();
    
    // List of predefined categories
    public static String[] categories = {"FRUIT", "VEGETABLE", "MEAT"};
    
    // File to store inventory data
    static File inventory = new File("Inventory.txt");

    // Initialize the items map with empty ArrayLists for each category
    public static void init() {
        Items.put("FRUIT", new ArrayList<Item>());
        Items.put("VEGETABLE", new ArrayList<Item>());
        Items.put("MEAT", new ArrayList<Item>());
    }

    // Reads inventory data from the file and populates the items map
    public static void readInv() throws IOException {
        if (!inventory.exists()) {
            // Throw an error if the inventory file doesn't exist
            throw new Error("FILE NOT FOUND");
        } else {
            // Open the file for reading
            BufferedReader br = new BufferedReader(new FileReader(inventory.getPath()));
            String itemString;
            
            // Read each line from the file
            while ((itemString = br.readLine()) != null) {
                // Parse the line into item properties
                String[] itemList = itemString.split(",");
                
                // Create a new Item object using the parsed properties
                Item item = new Item(itemList[1], itemList[2], 
                        Integer.valueOf(itemList[3]), Integer.valueOf(itemList[4]), 
                        Double.valueOf(itemList[5]), Integer.valueOf(itemList[6]), 
                        Integer.valueOf(itemList[8]));
                
                // Add the item to its corresponding category in the map
                Items.get(item.getCategory()).add(item);
            }
            // Close the reader
            br.close();
        }
    }

    // Adds a new item to the inventory and writes the updated inventory to the file
    public static void addItem(String name, String category, int quantity, int minimumQuantity, 
                                double vendorPrice, int markup, int discount) {
        // Create a new Item object with the provided details
        Item item = new Item(name, category, quantity, minimumQuantity, vendorPrice, markup, discount);
        
        // Add the item to its corresponding category in the map
        Items.get(item.getCategory()).add(item);
        
        // Write the updated inventory to the file
        try {
            writeInv();
        } catch (IOException e) {
            // Throw an error if writing to the file fails
            throw new Error("FILE NOT WRITTEN");
        }
    }

    // Writes the current inventory data to the file
    public static void writeInv() throws IOException {
        if (!inventory.exists()) {
            // Throw an error if the inventory file doesn't exist
            throw new Error("FILE NOT FOUND");
        } else {
            // Open the file for writing
            BufferedWriter bw = new BufferedWriter(new FileWriter(inventory.getPath()));
            
            // Clear the file's content
            bw.write("");
            
            // Write each item from all categories to the file
            for (String category : categories) {
                for (int i = 0; i < Items.get(category).size(); i++) {
                    bw.append(Items.get(category).get(i).toString() + "\n");
                }
            }
            // Close the writer
            bw.close();
        }
    }
}
