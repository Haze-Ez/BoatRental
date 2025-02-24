package Ezebuiro.Database_Operations_Control;

import Ezebuiro.Database_Connectivity.DatabaseConnection;
import Ezebuiro.Entities.RentalEvent;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RentalEventDAO {

    public void addRentalEvent(RentalEvent rentalEvent) throws SQLException {
        String sql = "INSERT INTO RentalEvent(boatId, customerId, rentalDate, returnDate, totalCost, isClosed)" +
                " VALUES (?,?,?,?,?,?)";

        try (
                Connection connect = DatabaseConnection.getConnection();
                PreparedStatement ps = connect.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {

            ps.setInt(1, rentalEvent.getBoatId());
            ps.setInt(2, rentalEvent.getCustomerId());
            ps.setDate(3, rentalEvent.getRentalDate());
            ps.setDate(4, rentalEvent.getReturnDate());
            ps.setDouble(5, rentalEvent.getTotalCost());
            ps.setBoolean(6, rentalEvent.isClosed());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                rentalEvent.setId(rs.getInt(1));
            }
        }

    }

    public RentalEvent EventbyID(int id) throws SQLException {
        RentalEvent event = null;
        String sql = "SELECT * FROM RentalEvent WHERE id = ?";

        try(
                Connection connect = DatabaseConnection.getConnection();
                PreparedStatement ps = connect.prepareStatement(sql)){

                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                if(rs.next()) {
                    event = maptoEvent(rs);
                }
        }
        return event;
    }

    public List<RentalEvent> getAllEvents() throws SQLException {
        List<RentalEvent> events = new ArrayList<>();

        String sql = "SELECT * FROM RentalEvent";

        try(
                Connection connect = DatabaseConnection.getConnection();
                Statement stmt = connect.createStatement();
        ){
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                events.add(maptoEvent(rs));
            }
        }
        return events;
    }

    public void updateEvent(RentalEvent event, Date returnDate, boolean isClosed,Double TotalCost) throws SQLException {
        String sql = "UPDATE RentalEvent SET boatId=?,customerId=?,rentalDate=?,returnDate=?,totalCost=?,isClosed=? WHERE id = ?";
        try(
                Connection connect = DatabaseConnection.getConnection();
                PreparedStatement ps = connect.prepareStatement(sql)){

            ps.setInt(1, event.getBoatId());
            ps.setInt(2, event.getCustomerId());
            ps.setDate(3, event.getRentalDate());
            ps.setDate(4, returnDate);
            ps.setDouble(5, TotalCost);
            ps.setBoolean(6, isClosed);
            ps.setInt(7,event.getId());
            ps.executeUpdate();
        }
    }

    public void deleteEvent(int id) throws SQLException {
        String sql = "DELETE FROM RentalEvent WHERE id = ?";

        try(
                Connection connect = DatabaseConnection.getConnection();
                PreparedStatement ps = connect.prepareStatement(sql)){

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }




    public RentalEvent maptoEvent(ResultSet rs) throws SQLException {
        RentalEvent event = new RentalEvent(
                rs.getInt("id"),
                rs.getInt("boatId"),
                rs.getInt("customerID"),
                rs.getDate("rentalDate"),
                rs.getDate("returnDate"),
                rs.getDouble("totalCost"),
                rs.getBoolean("isClosed")
        );

        return event;
    }


}