package com.athabasca;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.*;
import com.google.auth.oauth2.GoogleCredentials;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.function.Consumer;

public class DatabaseUtil {
    // Firebase Database URL constant
    public static final String URL = "https://finalproject12-1fd07-default-rtdb.firebaseio.com";
    
    // Instance variable for Firebase Database
    private static FirebaseDatabase database;

    // Constructor to initialize Firebase Database connection
    public DatabaseUtil() {
        try {
            // Load the service account key JSON file from the project directory
            Path pathAbsolute = Paths.get(System.getProperty("user.dir")+"/app/src/main/resources/serviceAccountKey.json");
            Path pathBase = Paths.get(System.getProperty("user.dir"));
            Path pathRelative = pathBase.relativize(pathAbsolute);
            // Load the service account key JSON file from the project directory
            FileInputStream serviceAccount = new FileInputStream(pathRelative.toString());

            // Set up Firebase options using the service account credentials and database URL
            FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl(DatabaseUtil.URL)
                .build();

            // Initialize Firebase App if it hasn't been initialized yet
            if(FirebaseApp.getApps().size() == 0) {
                FirebaseApp.initializeApp(options);
            }

            // Get reference to the Firebase Database instance
            DatabaseUtil.database = FirebaseDatabase.getInstance();
        } catch (IOException e) {
            e.printStackTrace(); // Print stack trace if there is an error initializing Firebase
        }
    }

    // Method to read data from the Firebase Database at a specific path
    public void readData(String path, Consumer<Object> callback) {
        // Get a reference to the specified path in the database
        DatabaseReference ref = database.getReference(path);
        
        // Add a listener to retrieve data when the data is available
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                // Retrieve the data from the snapshot
                Object data = snapshot.getValue();
                callback.accept(data); // Pass the data to the callback
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Handle error if data retrieval is cancelled
                System.err.println("Error retrieving data: " + error.getMessage());
                callback.accept(null); // Pass null to the callback in case of error
            }
        });
    }

    // Method to append data to a specific path in the Firebase Database
    @SuppressWarnings("unchecked") // Suppress unchecked cast warning
    public void appendData(String path, Object data, Consumer<Boolean> callback) {
        // Get reference to the specified path in the database
        DatabaseReference ref = database.getReference(path);
        
        // Update the children of the reference with the provided data
        ref.updateChildren((Map<String,Object>) data, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError error, DatabaseReference ref) {
                if (error != null) {
                    // Handle error if data appending fails
                    System.err.println("Error appending data: " + error.getMessage());
                    callback.accept(false); // Return false in case of error
                } else {
                    System.out.println("Data appended successfully to path: " + path);
                    callback.accept(true); // Return true if data is successfully appended
                }
            }
        });
    }

    // Method to write data to a specific path in the Firebase Database
    public void writeData(String path, Object data, Consumer<Boolean> callback) {
        // Get reference to the specified path in the database
        DatabaseReference ref = database.getReference(path);
        
        // Set the value of the reference with the provided data
        ref.setValue(data, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError error, DatabaseReference ref) {
                if (error != null) {
                    // Handle error if data writing fails
                    System.err.println("Error writing data: " + error.getMessage());
                    callback.accept(false); // Return false in case of error
                } else {
                    System.out.println("Data written successfully to path: " + path);
                    callback.accept(true); // Return true if data is successfully written
                }
            }
        });
    }

    // Method to read employee-specific data from Firebase
    public void readEmployee(String employee, Consumer<Object> callback) {
        // Get reference to the 'assigned' data of a specific employee
        DatabaseReference ref = database.getReference("employee/"+employee+"/assigned");
        
        // Add a listener to retrieve data when it's available
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                // Retrieve the data from the snapshot
                Object data = snapshot.getValue();
                callback.accept(data); // Pass the data to the callback
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Handle error if data retrieval is cancelled
                System.err.println("Error retrieving data: " + error.getMessage());
                callback.accept(null); // Pass null to the callback in case of error
            }
        });
    }
}
