package Ezebuiro.Utility;

import Ezebuiro.Database_Operations_Control.BoatDAO;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
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
            String[] brands = {"Yamaha", "Bayliner", "Sea Ray", "Chaparral", "Boston Whaler"};
            String[] models = {"SX210", "VR5", "SPX 210", "H20 19", "Montauk 150"};
            String insertBoatSQL = "INSERT INTO Boat (brand, model, available, pricePerDay, seats, plateNumber) VALUES (?, ?, ?, ?, ?, ?);";

            try (PreparedStatement boatStmt = conn.prepareStatement(insertBoatSQL)) {
                for (int i = 1; i <= 10; i++) {
                    boatStmt.setString(1, brands[i % brands.length]);
                    boatStmt.setString(2, models[i % models.length]);
                    boatStmt.setBoolean(3, i % 2 == 0); // Some available, some not
                    boatStmt.setDouble(4, 200 + (i * 20)); // Price varies
                    boatStmt.setInt(5, 6 + (i % 5)); // Seat count varies
                    boatStmt.setString(6, "PLT" + (100 + i)); // Plate number
                    boatStmt.executeUpdate();
                }
            }

            // Step 4: Insert 20 Customers
            String insertCustomerSQL = "INSERT INTO Customer (firstName, lastName, email, boatLicense, countrycode) VALUES (?, ?, ?, ?, ?);";
            String[] firstNames = {"John", "Jane", "Alex", "Emily", "Chris", "Sarah", "Michael", "Laura", "Daniel", "Emma"};
            String[] lastNames = {"Smith", "Johnson", "Brown", "Williams", "Jones", "Miller", "Davis", "Garcia", "Rodriguez", "Wilson"};
            String[] countryCodes = {"AF", "HU", "UK", "CA", "US"};

            try (PreparedStatement customerStmt = conn.prepareStatement(insertCustomerSQL)) {
                for (int i = 1; i <= 20; i++) {
                    customerStmt.setString(1, firstNames[i % firstNames.length]);
                    customerStmt.setString(2, lastNames[i % lastNames.length]);
                    customerStmt.setString(3, "customer" + i + "@email.com");
                    customerStmt.setString(4, "EZE" + (1000 + i)); // Boat license
                    customerStmt.setString(5, countryCodes[i % countryCodes.length]);
                    customerStmt.executeUpdate();
                }
            }

            // Step 5: Insert 50 Rental Events
            String insertRentalSQL = "INSERT INTO RentalEvent (customerId, boatId, rentalDate, returnDate, totalCost, isClosed) " +
                    "VALUES (?, ?, CURRENT_DATE, ?, ?, ?);";
            Random random = new Random();

            try (PreparedStatement rentalStmt = conn.prepareStatement(insertRentalSQL)) {

                for (int i = 1; i <= 50; i++) {
                    boolean isClosed = random.nextBoolean();
                    Date returnDate = isClosed ? Date.valueOf(LocalDate.now().plusDays(3)) : null; //Use of Java Variable
                    double total = isClosed ? 310 * 3.0 : 0.0; // Only calculate total if returned

                    rentalStmt.setInt(1, random.nextInt(20) + 1); // Random customer ID (1-20)
                    rentalStmt.setInt(2, random.nextInt(10) + 1); // Random boat ID (1-10)
                    rentalStmt.setDate(3,returnDate);
                    rentalStmt.setDouble(4,total);
                    rentalStmt.setBoolean(5,isClosed);
                    rentalStmt.executeUpdate();
                }
            }

            System.out.println("Database initialized successfully with 10 boats, 20 customers, and 50 rental events.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

