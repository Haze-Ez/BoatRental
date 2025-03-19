package Ezebuiro.Executable;

import Ezebuiro.Config.AppConfig;
import Ezebuiro.Database_Operations_Control.IBoatDAO;
import Ezebuiro.Database_Operations_Control.ICustomerDAO;
import Ezebuiro.Database_Operations_Control.IRentalEventDAO;
import Ezebuiro.Services.BoatService;
import Ezebuiro.Services.CustomerService;
import Ezebuiro.Services.RentalEventService;
import Ezebuiro.Utility.DatabaseInitializer;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ImportResource;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        DatabaseInitializer dbinit = context.getBean(DatabaseInitializer.class);
        System.out.println("Hello and welcome! This is the Boat Rental Program");


        //dbinit.initialize();
        IBoatDAO boat = context.getBean(IBoatDAO.class);
        ICustomerDAO customer = context.getBean(ICustomerDAO.class);
        IRentalEventDAO rentalEvent = context.getBean(IRentalEventDAO.class);

        BoatService Bservice = context.getBean(BoatService.class);
        RentalEventService Rservice = context.getBean(RentalEventService.class);
        CustomerService Cservice = context.getBean(CustomerService.class);
        try {
            System.out.println(Bservice.getAllBoats().getFirst());
            System.out.println(Bservice.getAllBoats().getLast());
            System.out.println(Rservice.getAllRentalEvents().getFirst());
            System.out.println(Rservice.getAllRentalEvents().getLast());
            System.out.println(Cservice.getAllCustomers().getFirst());
            System.out.println(Cservice.getAllCustomers().getLast());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
//        try {
//            System.out.println(customer.getAllCustomers().getFirst().toString());
//            System.out.println(customer.getAllCustomers().getLast().toString());
//            System.out.println(boat.getAllBoats().getFirst());
//            System.out.println(boat.getAllBoats().getLast());
//            System.out.println(rentalEvent.getAllRentalEvents().getFirst());
//            System.out.println(rentalEvent.getAllRentalEvents().getLast());
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
    }

}
