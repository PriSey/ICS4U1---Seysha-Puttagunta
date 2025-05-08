package com.athabasca;

import java.util.ArrayList;
import java.util.Map;
import java.util.function.Consumer;

public class Clients {
    // List to store the client data
    public static ArrayList<Client> clients = new ArrayList<>();

    // Method to load clients from the database and trigger the callback
    public static void loadClients(Consumer<ArrayList<Client>> callback) {
        clients = new ArrayList<>(); // Reset the client list
        DatabaseUtil db = new DatabaseUtil(); // Create an instance of DatabaseUtil to interact with the database

        // Read client data from the database
        db.readData("client", data -> {
            if (data != null) { // If data is received
                System.out.println("Data received in callback: " + data);
                try {
                    // Cast the received data to a Map of Map<String, Object> type
                    @SuppressWarnings("unchecked")
                    Map<String, Map<String, Object>> loadedData = (Map<String, Map<String, Object>>) data;

                    // Iterate over the data and create client objects
                    for (Map.Entry<String, Map<String, Object>> entry : loadedData.entrySet()) {
                        System.out.println("Processing entry: " + entry);
                        
                        // Create a new client and add it to the clients list
                        clients.add(new Client(
                            (String) entry.getValue().get("f_name"), // First name
                            (String) entry.getValue().get("l_name"), // Last name
                            (Long) entry.getValue().get("p_number"), // Phone number
                            (String) entry.getValue().get("address"), // Address
                            (String) entry.getValue().get("date_joined"), // Date joined
                            (String) entry.getKey().replaceAll("\\\\", "\\.") // Email, replacing backslashes
                        ));
                    }

                    // Log the loaded clients for debugging
                    System.out.println("Clients loaded: " + clients);
                } catch (ClassCastException e) {
                    // Catch and log any casting errors during data processing
                    System.err.println("Error casting data: " + e.getMessage());
                    e.printStackTrace();
                }
                callback.accept(clients); // Invoke the callback with the loaded clients
            } else {
                // If no data is found, log the message and return an empty client list
                System.out.println("No data found at path: client");
                callback.accept(clients); // Invoke the callback with the empty list
            }
        });
    }

    // Method to add a new client to the list
    public static void addClient(Client c) { 
        clients.add(c); // Add the client to the clients list
    }
}
