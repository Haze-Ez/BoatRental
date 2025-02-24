package Ezebuiro.Utility;

import Ezebuiro.Database_Operations_Control.BoatDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseInitializer {
    private static final String URL = "jdbc:h2:~/boatrental;AUTO_SERVER=TRUE";
    private static final String USER = "sa";
    private static final String PASSWORD = "";


    public static void initialize() {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {

            // Drop tables if they exist
            try (PreparedStatement dropRentalEvent = conn.prepareStatement("DROP TABLE IF EXISTS RentalEvent;");
                 PreparedStatement dropCustomer = conn.prepareStatement("DROP TABLE IF EXISTS Customer;");
                 PreparedStatement dropBoat = conn.prepareStatement("DROP TABLE IF EXISTS Boat;") ) {
                dropRentalEvent.executeUpdate();
                dropCustomer.executeUpdate();
                dropBoat.executeUpdate();
            }

            // Create Boat table
            try (PreparedStatement createBoat = conn.prepareStatement("CREATE TABLE Boat (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT, " +
                    "brand VARCHAR(255) NOT NULL, " +
                    "model VARCHAR(255) NOT NULL, " +
                    "pricePerDay DOUBLE NOT NULL, " +
                    "seats INT NOT NULL, " +
                    "plateNumber VARCHAR(50) UNIQUE NOT NULL, " +
                    "available BOOLEAN NOT NULL);");) {
                createBoat.executeUpdate();
            }

            // Create Customer table
            try (PreparedStatement createCustomer = conn.prepareStatement("CREATE TABLE Customer (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT, " +
                    "firstName VARCHAR(255) NOT NULL, " +
                    "lastName VARCHAR(255) NOT NULL, " +
                    "email VARCHAR(255) UNIQUE NOT NULL, " +
                    "boatLicense VARCHAR(255) NOT NULL, " +
                    "countrycode VARCHAR(10) NOT NULL);");) {
                createCustomer.executeUpdate();
            }

            // Create RentalEvent table
            try (PreparedStatement createRentalEvent = conn.prepareStatement("CREATE TABLE RentalEvent (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT, " +
                    "boatId INT NOT NULL, " +
                    "customerId INT NOT NULL, " +
                    "rentalDate DATE NOT NULL, " +
                    "returnDate DATE, " +
                    "totalCost DOUBLE NOT NULL, " +
                    "isClosed BOOLEAN NOT NULL, " +
                    "FOREIGN KEY (boatId) REFERENCES Boat(id), " +
                    "FOREIGN KEY (customerId) REFERENCES Customer(id));");) {
                createRentalEvent.executeUpdate();
            }

            // Insert sample boats
            try (PreparedStatement insertBoats = conn.prepareStatement("INSERT INTO Boat (brand, model, pricePerDay, seats, plateNumber, available) VALUES " +
                    "('Yamaha', 'SX210', 300.0, 10, 'ABC123', true), " +
                    "('Bayliner', 'VR5', 250.0, 8, 'XYZ789', true), " +
                    "('Sea Ray', 'SPX 210', 280.0, 9, 'DEF456', false);");) {
                insertBoats.executeUpdate();
            }

            // Insert sample customers
            try (PreparedStatement insertCustomers = conn.prepareStatement("INSERT INTO Customer (firstName, lastName, email, boatLicense, countrycode) VALUES " +
                    "('John', 'Doe', 'john.doe@example.com', 'B12345', 'US'), " +
                    "('Jane', 'Smith', 'jane.smith@example.com', 'C67890', 'CA'), " +
                    "('Alice', 'Brown', 'alice.brown@example.com', 'D54321', 'UK');");) {
                insertCustomers.executeUpdate();
            }

            // Insert sample rental events
            try (PreparedStatement insertRentals = conn.prepareStatement("INSERT INTO RentalEvent (boatId, customerId, rentalDate, returnDate, totalCost, isClosed) VALUES " +
                    "(1, 1, '2025-02-10', '2025-02-12', 600.0, true), " +
                    "(2, 2, '2025-02-15', NULL, 250.0, false);");) {
                insertRentals.executeUpdate();
            }

            System.out.println("Database initialized successfully with sample data.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

