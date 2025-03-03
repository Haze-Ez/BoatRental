package Ezebuiro.Database_Operations_Control;

import Ezebuiro.Entities.Customer;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;


@Component
public interface ICustomerDAO {
    void addCustomer(Customer customer) throws SQLException;
    Customer getCustomerById(int id) throws SQLException;
    Customer getByName(String name) throws SQLException;
    List<Customer> getAllCustomers() throws SQLException;
    void updateCustomer(Customer customer,String boatLicense) throws SQLException;
    void deleteCustomer(int id) throws SQLException;
}
