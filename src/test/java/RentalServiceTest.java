import Ezebuiro.Entities.Boat;
import Ezebuiro.Entities.RentalEvent;
import Ezebuiro.Services.*;
import Ezebuiro.Database_Operations_Control.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class RentalServiceTest {
    private RentalEventService rentalEventService;
    private BoatService boatService;
    private CustomerService customerService;
    private List<RentalEvent> events;

    @BeforeEach
    void setUp() throws SQLException {
        rentalEventService = new RentalEventService();
        boatService = new BoatService();
        customerService = new CustomerService();
        events = rentalEventService.getAllRentalEvents();
    }

    @Test
    public void TestAddEvent() throws SQLException {
        if(!(boatService.getBoat(3).isAvailable())){
            boatService.Availability(boatService.getBoat(3),true);
        }

        if (!(events.isEmpty())){
            rentalEventService.deleteRentalEvent((events.getLast().getId()));
        }

        RentalEvent event = new RentalEvent(0,3, 1, Date.valueOf("2025-02-10"), Date.valueOf("2025-02-12"), 560.0, false);

        rentalEventService.RentaBoat(event);
        assertNotNull(events);

        events = rentalEventService.getAllRentalEvents();
        RentalEvent testEvent = events.getLast();
        assertEquals(event.getBoatId(), testEvent.getBoatId());
        assertEquals(event.getCustomerId(), testEvent.getCustomerId());
        assertEquals(event.getRentalDate(), testEvent.getRentalDate());
        assertEquals(event.getReturnDate(), testEvent.getReturnDate());
        assertEquals(event.getTotalCost(), testEvent.getTotalCost(), 0.01); // Floating point precision check
        assertEquals(event.isClosed(), testEvent.isClosed());
    }

    @Test
    void TestGetEventById() throws SQLException {
        RentalEvent event = events.getLast();
        assertEquals(event,rentalEventService.getRentalEventById(event.getId()));
    }

    @Test
    void TestUpdateEvent() throws SQLException {
        // Ensure boat is available before proceeding
        Boat boat = boatService.getBoat(3);
        if (!boat.isAvailable()) {
            boatService.Availability(boat, true);
        }


        // If an event exists, delete the last one to prevent primary key conflict
        if (!events.isEmpty()) {
            rentalEventService.deleteRentalEvent(events.getLast().getId());
        }

        // Create a new rental event
        RentalEvent event = new RentalEvent(0, 3, 1, Date.valueOf("2025-02-10"), null, 0.0, false);
        rentalEventService.RentaBoat(event);

        // Refresh event list
        events = rentalEventService.getAllRentalEvents();
        assertFalse(events.isEmpty()); // Ensure event creation was successful

        RentalEvent sqlEvent = events.getLast(); // Fetch newly created event

        // Validate event insertion
        assertEquals(event.getBoatId(), sqlEvent.getBoatId());
        assertEquals(event.getCustomerId(), sqlEvent.getCustomerId());
        assertEquals(event.getRentalDate(), sqlEvent.getRentalDate());
        assertEquals(event.getReturnDate(), sqlEvent.getReturnDate());
        assertEquals(event.getTotalCost(), sqlEvent.getTotalCost(), 0.01);
        assertEquals(event.isClosed(), sqlEvent.isClosed());

        // Perform event update
        rentalEventService.updateRentalEvent(sqlEvent, Date.valueOf("2025-02-14"), true, 1200.0);

        // Re-fetch the event after update
        sqlEvent = rentalEventService.getRentalEventById(sqlEvent.getId());

        // Validate the update
        assertEquals(Date.valueOf("2025-02-14"), sqlEvent.getReturnDate());
        assertTrue(sqlEvent.isClosed());
        assertEquals(1200.0, sqlEvent.getTotalCost(), 0.01);
    }



    @Test
    public void TestGetBoatById() throws SQLException {
        List<Boat> boats = boatService.getAllBoats();
        Boat boat1 = boats.get(0);
        assertEquals(rentalEventService.getBoatById(1),boat1);
    }



}