package Ezebuiro.Services;

import Ezebuiro.Database_Connectivity.DatabaseConnection;
import Ezebuiro.Database_Operations_Control.BoatDAO;
import Ezebuiro.Database_Operations_Control.RentalEventDAO;
import Ezebuiro.Entities.Boat;
import Ezebuiro.Entities.RentalEvent;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class RentalEventService {
    private RentalEventDAO eventDAO;
    private BoatDAO boatDAO;

    public RentalEventService() {
        this.eventDAO = new RentalEventDAO();
        this.boatDAO = new BoatDAO();
    }

    public Boat getBoatById(int boatId) throws SQLException {
        return boatDAO.searchById(boatId);
    }

    public void setAvailableById(int boatId, boolean available) throws SQLException {
        Boat boat = getBoatById(boatId);
        boatDAO.updateBoatAvailability(boat, available);
    }

    public boolean isAvailable(int boatId) throws SQLException {
        Boat boat = getBoatById(boatId);
        return boat.isAvailable();
    }

    public void RentaBoat(RentalEvent event) throws SQLException {
        Connection connection = null;
        try {
            connection = DatabaseConnection.getConnection();  // Get connection
            connection.setAutoCommit(false);  // Start transaction

            if (isAvailable(event.getBoatId())) {
                RentalEvent event0 = new RentalEvent(event.getId(), event.getBoatId(), event.getCustomerId(),event.getRentalDate(), event.getReturnDate(), event.getTotalCost(), false);

                // Create the rental event
                eventDAO.addRentalEvent(event0);

                // Mark the boat as unavailable
                setAvailableById(event0.getBoatId(), false);

                connection.commit();  // Commit the transaction if everything succeeds
            } else {
                throw new SQLException("Boat is not available for rent.");
            }

        } catch (SQLException e) {
            if (connection != null) {
                connection.rollback();  // Rollback transaction in case of error
            }
            throw e;  // Rethrow the exception
        } finally {
            if (connection != null) {
                connection.setAutoCommit(true);  // Reset auto-commit to true
            }
        }
    }


    public RentalEvent getRentalEventById(int id) throws SQLException {
        return eventDAO.EventbyID(id);
    }

    public List<RentalEvent> getAllRentalEvents() throws SQLException {
        return eventDAO.getAllEvents();
    }

    public void updateRentalEvent(RentalEvent rentalEvent,Date returnDate,boolean closed,Double total) throws SQLException {
       eventDAO.updateEvent(rentalEvent,returnDate,closed,total);
    }

    public void deleteRentalEvent(int id) throws SQLException {
       eventDAO.deleteEvent(id);
    }
}






