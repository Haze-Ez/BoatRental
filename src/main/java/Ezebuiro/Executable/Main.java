package Ezebuiro.Executable;

import Ezebuiro.Config.AppConfig;
import Ezebuiro.Services.BoatService;
import Ezebuiro.Services.CustomerService;
import Ezebuiro.Services.RentalEventService;
import Ezebuiro.Utility.DatabaseInitializer;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        DatabaseInitializer dbinit = context.getBean(DatabaseInitializer.class);
        System.out.println("Hello and welcome! This is the Boat Rental Program");


        //dbinit.initialize();
        BoatService boatService = context.getBean(BoatService.class);
        CustomerService customerService = context.getBean(CustomerService.class);
        RentalEventService rentalEventService = context.getBean(RentalEventService.class);

        try {
            System.out.println(customerService.getAllCustomers().getFirst().toString());
            System.out.println(customerService.getAllCustomers().getLast().toString());
            System.out.println(boatService.getAllBoats().getFirst());
            System.out.println(boatService.getAllBoats().getLast());
            System.out.println(rentalEventService.getAllRentalEvents().getFirst());
            System.out.println(rentalEventService.getAllRentalEvents().getLast());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
