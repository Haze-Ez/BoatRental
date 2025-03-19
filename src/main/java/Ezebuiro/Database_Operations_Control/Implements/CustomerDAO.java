package Ezebuiro.Database_Operations_Control.Implements;

import Ezebuiro.Database_Connectivity.DatabaseConnection;
import Ezebuiro.Database_Operations_Control.ICustomerDAO;
import Ezebuiro.Entities.Customer;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CustomerDAO implements ICustomerDAO {
    @Override
    public void addCustomer(Customer customer) throws SQLException {
        String sql = "INSERT INTO Customer(firstname, lastname, email, boatLicense, countrycode) VALUES ( ?, ?, ?, ?, ?)";

        try (
                Connection connect = DatabaseConnection.getConnection();
                PreparedStatement ps = connect.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, customer.getFirstName());
            ps.setString(2, customer.getLastName());
            ps.setString(3, customer.getEmail());
            ps.setString(4, customer.getBoatLicense());
            ps.setString(5, customer.getCountrycode());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            try {
                if (rs.next()) {
                    customer.setId(rs.getInt(1));
                }
            } catch (SQLException e) {
                System.out.println("Error, No Customer Created");
            }
        }
    }

    @Override
    public Customer getCustomerById(int id) throws SQLException {
        Customer customer = null;
        String sql = "SELECT * FROM Customer WHERE id = ?";

        try(
                Connection connect = DatabaseConnection.getConnection();
                PreparedStatement ps = connect.prepareStatement(sql)){
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                customer = mapnewcustomer(rs);
            }
        }
        return customer;
    }

    @Override
    public Customer getByName(String name) throws SQLException {
        Customer customer = null;

        String[] parts = name.split("_",2);
        String sql = "SELECT * FROM Customer WHERE LOWER(firstname) = ? AND LOWER(lastname) = ?";

        try{
            Connection connect = new DatabaseConnection().getConnection();
            PreparedStatement ps = connect.prepareStatement(sql);{
                ps.setString(1, parts[0].toLowerCase());
                ps.setString(2, parts[1].toLowerCase());
                ResultSet rs = ps.executeQuery();

                if(rs.next()) {
                    customer = mapnewcustomer(rs);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error Searching by name./nBe sure of the format: first_name: " + e.getMessage());
        }
        return customer;
    }

    @Override
    public List<Customer> getAllCustomers() throws SQLException {
        List<Customer> customers = new ArrayList<>();

        String sql = "SELECT * FROM Customer";

        try(
                Connection connect = DatabaseConnection.getConnection();
                Statement stmt = connect.createStatement();
        ){
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                customers.add(mapnewcustomer(rs));
            }
        }
        return customers;
    }

    @Override
    public void updateCustomer(Customer customer, String boatLicense) throws SQLException {
        String sql = "UPDATE Customer SET firstName = ?,lastName = ?,email = ?,boatLicense = ?,countrycode = ? WHERE id = ?";
        try(
                Connection connect = DatabaseConnection.getConnection();
                PreparedStatement ps = connect.prepareStatement(sql)){

            ps.setString(1, customer.getFirstName());
            ps.setString(2, customer.getLastName());
            ps.setString(3, customer.getEmail());
            ps.setString(4, boatLicense);
            ps.setString(5, customer.getCountrycode());
            ps.setInt(6, customer.getId());
            ps.executeUpdate();
        }
    }

    @Override
    public void deleteCustomer(int id) throws SQLException {
        String sql = "DELETE FROM Customer WHERE id = ?";

        try(
                Connection connect = DatabaseConnection.getConnection();
                PreparedStatement ps = connect.prepareStatement(sql)){

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public Customer mapnewcustomer(ResultSet rs) throws SQLException {
        Customer customer = new Customer(
                rs.getInt("id"),
                rs.getString("firstName"),
                rs.getString("lastName"),
                rs.getString("email"),
                rs.getString("boatLicense"),
                rs.getString("countrycode")
        );
        return customer;
    }







}
