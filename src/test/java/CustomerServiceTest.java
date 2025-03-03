

import Ezebuiro.Database_Operations_Control.CustomerDAO;
import Ezebuiro.Entities.Boat;
import Ezebuiro.Entities.Customer;
import Ezebuiro.Services.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class CustomerServiceTest {
    private CustomerService customerService;
    private Customer customer;
    List<Customer> customers;
    @BeforeEach
    void setUp() throws SQLException {
        customerService = new CustomerService(new CustomerDAO());
        customers = customerService.getAllCustomers();
        customer = customerService.getCustomer_name("Lizzy_Irithyl");
    }

    @Test
    public void testAddCustomer() throws SQLException {
        if(!(customers.isEmpty())){
            customerService.deleteCustomer(customers.getLast().getId());
        }
        Customer customer1= new Customer(0,"Lizzy","Irithyl","bliz.haze@example.com","A0001","UK");
        customerService.addCustomer(customer1);
        assertNotNull(customer1);

        customers = customerService.getAllCustomers();
        assertEquals(customer1, customers.getLast());
    }

    @Test
    public void testGetCustomerById() throws SQLException {
      Customer customer1 = customers.getFirst();
      assertEquals(customer1,customerService.getCustomer(customers.getFirst().getId()));
    }


    @Test
    public void testGetAllCustomers() throws SQLException {
        List<Customer> customers = customerService.getAllCustomers();
        assertNotNull(customers);
        assertFalse(customers.isEmpty());
        for(Customer customer : customers) {
            assertNotNull(customer);
        }
    }

    @Test
    public void testUpdateCustomer() throws SQLException {
        customerService.updateCustomer(customer,"000A1");
        customer = customerService.getCustomer_name("lizzy_irithyl");
        assertEquals("000A1",customer.getBoatLicense());
    }




}
