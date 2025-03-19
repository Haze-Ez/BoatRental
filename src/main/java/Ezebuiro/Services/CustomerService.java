package Ezebuiro.Services;

import Ezebuiro.Database_Operations_Control.ICustomerDAO;
import Ezebuiro.Database_Operations_Control.Implements.CustomerDAO;
import Ezebuiro.Entities.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
@Service
public class CustomerService {

    private final ICustomerDAO customerDAO;
    @Autowired
    public CustomerService(CustomerDAO customerDAO) {
        this.customerDAO = new CustomerDAO();
    }

    public void addCustomer(Customer customer) throws SQLException {
        customerDAO.addCustomer(customer);
    }

    public Customer getCustomer(int id) throws SQLException {
        return customerDAO.getCustomerById(id);
    }

    public Customer getCustomer_name(String name) throws SQLException {
        return customerDAO.getByName(name);
    }

    public List<Customer> getAllCustomers() throws SQLException {
        return customerDAO.getAllCustomers();
    }

    public void updateCustomer(Customer customer, String license) throws SQLException {
        customerDAO.updateCustomer(customer,license);
    }

    public void deleteCustomer(int id) throws SQLException {
        customerDAO.deleteCustomer(id);
    }
}
