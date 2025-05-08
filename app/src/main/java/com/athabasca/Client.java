package com.athabasca;

public class Client {
    // This class is incomplete and should be updated once we have more information on what client data is stored

    // Private fields representing client information
    private Long phoneNumber;
    private String address;
    private String firstName;
    private String lastName;
    private String dateJoined;
    private String email;

    // Array representing the categories for the client (column headers)
    public static String[] Categories = {"First Name", "Last Name", "Phone number", "Address", "Date Joined", "Email"};

    // Constructor to initialize the client object with provided details
    Client(String firstName, String lastName, Long phoneNumber, String address, String dateJoined, String email) {
        this.firstName = firstName;
        this.address = address;
        this.lastName = lastName;
        this.dateJoined = dateJoined;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    // Getter method for the address field
    public String getAddress() {
        return address;
    }

    // Getter method for the dateJoined field
    public String getDateJoined() {
        return dateJoined;
    }

    // Getter method for the firstName field
    public String getFirstName() {
        return firstName;
    }

    // Getter method for the lastName field
    public String getLastName() {
        return lastName;
    }

    // Getter method for the phoneNumber field
    public Long getPhoneNumber() {
        return phoneNumber;
    }

    // Getter method for the email field
    public String getEmail() {
        return email;
    }

    // Override the toString method to provide a string representation of the client object
    @Override
    public String toString() {
        return firstName + "|" + lastName + "|" + phoneNumber + "|" + address + "|" + dateJoined + "|" + email;
    }

    // Setter method for the phoneNumber field
    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    // Setter method for the address field
    public void setAddress(String address) {
        this.address = address;
    }

    // Setter method for the firstName field
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    // Setter method for the lastName field
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    // Setter method for the dateJoined field
    public void setDateJoined(String dateJoined) {
        this.dateJoined = dateJoined;
    }
}
