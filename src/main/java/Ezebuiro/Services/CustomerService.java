package Ezebuiro.Services;

import Ezebuiro.Database_Operations_Control.CustomerDAO;
import Ezebuiro.Entities.Customer;

import java.sql.SQLException;
import java.util.List;

public class CustomerService {

    private CustomerDAO customerDAO;

    public CustomerService() {
        this.customerDAO = new CustomerDAO();
    }

    public void addCustomer(Customer customer) throws SQLException {
        customerDAO.Createcustomer(customer);
    }

    public Customer getCustomer(int id) throws SQLException {
        return customerDAO.searchbyid(id);
    }

    public Customer getCustomer_name(String name) throws SQLException {
        return customerDAO.searchByName(name);
    }

    public List<Customer> getAllCustomers() throws SQLException {
        return customerDAO.getAllCustomers();
    }

    public void updateCustomer(Customer customer, String license) throws SQLException {
        customerDAO.updatecustomer(customer,license);
    }

    public void deleteCustomer(int id) throws SQLException {
        customerDAO.deletecustomer(id);
    }
}
