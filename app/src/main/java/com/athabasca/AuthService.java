package com.athabasca;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class AuthService {
    // Firebase Authentication instance
    private FirebaseAuth auth;

    // API key for Firebase authentication
    private static final String FIREBASE_API_KEY = "AIzaSyAxATCcxeG0shBCxPyo_ZdEhDhb4W0qaRU";

    // Constructor initializes the FirebaseAuth instance and database utility
    public AuthService() {
        new DatabaseUtil(); // Initialize the database utility
        auth = FirebaseAuth.getInstance(); // Get FirebaseAuth instance
    }

    /**
     * Logs in a user using email and password and retrieves an ID token.
     * 
     * @param email    User's email address
     * @param password User's password
     * @return The ID token if login is successful, otherwise null
     */
    public String loginUserAndToken(String email, String password) {
        try {
            // URL for Firebase authentication API
            String url = "https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key=" + FIREBASE_API_KEY;
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            // Set HTTP method and headers
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");

            // Create the payload with email and password
            String payload = String.format("{\"email\":\"%s\",\"password\":\"%s\",\"returnSecureToken\":true}", email, password);

            // Write payload to the request body
            con.setDoOutput(true);
            try (OutputStream os = con.getOutputStream()) {
                os.write(payload.getBytes(StandardCharsets.UTF_8));
            }

            // Get the response code from the server
            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // Check if the response is successful
                try (Scanner scanner = new Scanner(con.getInputStream())) {
                    // Read the response body
                    String responseBody = scanner.useDelimiter("\\A").next();
                    JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();

                    // Extract the ID token from the response
                    String idToken = jsonObject.get("idToken").getAsString();
                    System.out.println("Successfully logged in and obtained ID token: " + idToken);
                    return idToken; // Return the ID token
                }
            } else {
                // Handle error responses
                try (Scanner scanner = new Scanner(con.getErrorStream())) {
                    String errorResponse = scanner.useDelimiter("\\A").next();
                    System.err.println("Error response: " + errorResponse); // Print error details
                }
            }
        } catch (Exception e) {
            // Handle exceptions during login
            System.err.println("Error logging in user: " + e.getMessage());
            e.printStackTrace(); // Print stack trace for debugging
        }
        return null; // Return null if login fails
    }

    /**
     * Verifies the given ID token using Firebase Authentication.
     * 
     * @param idToken The ID token to verify
     */
    public void verifyIdToken(String idToken) {
        try {
            // Verify the ID token using FirebaseAuth
            FirebaseToken decodedToken = auth.verifyIdToken(idToken);
            System.out.println("Successfully verified ID token: " + decodedToken.getUid());
        } catch (FirebaseAuthException e) {
            // Handle errors during token verification
            System.err.println("Error verifying ID token: " + e.getMessage());
            e.printStackTrace(); // Print stack trace for debugging
        }
    }
}
