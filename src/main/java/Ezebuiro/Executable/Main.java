package Ezebuiro.Executable;

import Ezebuiro.Database_Operations_Control.BoatDAO;
import Ezebuiro.Database_Operations_Control.BoatDAO;
import Ezebuiro.Entities.Boat;
import Ezebuiro.Entities.Customer;
import Ezebuiro.Entities.RentalEvent;
import Ezebuiro.Services.BoatService;
import Ezebuiro.Services.CustomerService;
import Ezebuiro.Services.RentalEventService;

import java.sql.SQLException;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello and welcome!");

        // Initialize database (if necessary)
     // new Ezebuiro.Utility.DatabaseInitializer().initialize();

        // Fetch all boats from the database
        BoatService boatService = new BoatService();
        List<Boat> boats = boatService.getAllBoats();

        RentalEventService rentalService = new RentalEventService();
      Boat boatCreateTest = new Boat(0,"Shark","XPS-314",400.0,350,"ABAB1212",false);
      //boatService.create(boatCreateTest);
        // Check if there are boats and print the first and last ones
            if (!boats.isEmpty()) {
                System.out.println("First Boat: " + boats.get(0));
                System.out.println("Last Boat: " + boats.get(boats.size() - 1));

                System.out.println("------------------------------------------------------------------------------------------------------------------------");

                try {
                    System.out.println("Selection: "+boatService.Advancedsearch(5,10,300,"Sea Ray","SPX 210"));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("------------------------------------------------------------------------------------------------------------------------");

                try {
                    for(RentalEvent event : rentalService.getAllRentalEvents())
                        System.out.print(event+"\n");

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                System.out.println();

                System.out.println("------------------------------------------------------------------------------------------------------------------------");


                for(Boat boat : boats)
                    System.out.print(boat+"\n");

                System.out.println();

            } else {
                System.out.println("No boats available.");
            }
        System.out.println("------------------------------------------------------------------------------------------------------------------------");

        CustomerService customerService = new CustomerService();
        try {
            List<Customer> customers = customerService.getAllCustomers();

            for(Customer customer : customers)
                System.out.print(customer+"\n");



        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}
