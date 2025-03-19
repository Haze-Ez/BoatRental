package Ezebuiro.Database_Operations_Control.Implements;

import Ezebuiro.Database_Connectivity.DatabaseConnection;
import Ezebuiro.Database_Operations_Control.IBoatDAO;
import Ezebuiro.Entities.Boat;
import org.springframework.stereotype.Repository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BoatDAO implements IBoatDAO {

    @Override
    public void addBoat(Boat boat) {
        String sql = "INSERT INTO Boat(brand, model, platenumber, priceperday, available,seats) VALUES(?,?,?,?,?,?)";

        try (Connection conn = DatabaseConnection.getConnection();) {
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            {
                stmt.setString(1, boat.getBrand());
                stmt.setString(2, boat.getModel());
                stmt.setString(3, boat.getPlateNumber());
                stmt.setDouble(4, boat.getPricePerDay());
                stmt.setBoolean(5, boat.isAvailable());
                stmt.setInt(6, boat.getSeats());
                int affectedRows = stmt.executeUpdate();
            }
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    boat.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating Boat failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Boat getBoatById(int id) throws SQLException {
        Boat boat = null;
        String sql = "SELECT * FROM Boat WHERE id = ?";

        try {
            Connection connect = DatabaseConnection.getConnection();
            PreparedStatement stmt = connect.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                boat = mapnewboat(rs);
            }
        }catch(SQLException e){
            System.out.println("No boat by this id");
        }
        return boat;
    }

    @Override
    public List<Boat> getBoatbyBrand(String brand) throws SQLException {
        List<Boat> boats = new ArrayList<>();
        String sql = "SELECT * FROM Boat WHERE brand = ?";

        try (
                Connection connect = DatabaseConnection.getConnection();
                PreparedStatement stmt = connect.prepareStatement(sql)) {

            stmt.setString(1, brand);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                boats.add(mapnewboat(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return boats;
    }

    @Override
    public List<Boat> getAllBoats() {
        List<Boat> boats = new ArrayList<>();
        String sql = "SELECT * FROM Boat";

        try {
            Connection db_con = DatabaseConnection.getConnection();
            Statement state = db_con.createStatement();
            ResultSet rs = state.executeQuery(sql);

            while (rs.next()) {
                boats.add(mapnewboat(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return boats;
    }

    @Override
    public void updateBoat(Boat boat,boolean available) throws SQLException {
        String sql = "UPDATE Boat SET available=? WHERE id=?";
        try (
                Connection con = DatabaseConnection.getConnection();
                PreparedStatement state = con.prepareStatement(sql)) {

            state.setBoolean(1, available);
            state.setInt(2, boat.getId());
            int rowsUpdated = state.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Boat availability updated successfully.");
            } else {
                System.out.println("No boat found with the provided ID.");
            }
        } catch (SQLException e) {
            System.err.println("Error updating boat availability: " + e.getMessage());
            throw e;  // Propagate the exception to the caller for further handling or logging
        }
    }

    @Override
    public List<Boat> searchBoats(int minseat, int maxseat, double maxRentPrice, String brand, String model) throws SQLException {
        List<Boat> boats = new ArrayList<>();
        String sql = "SELECT * FROM Boat WHERE seats BETWEEN ? AND ? AND pricePerDay = ? AND brand = ? AND model = ?";

        try (
                Connection connect = DatabaseConnection.getConnection();
                PreparedStatement stmt = connect.prepareStatement(sql)) {

            stmt.setInt(1, minseat);
            stmt.setInt(2, maxseat);
            stmt.setDouble(3, maxRentPrice);
            stmt.setString(4, brand);
            stmt.setString(5, model);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                boats.add(mapnewboat(rs));
            }
        }
        return boats;

    }

    @Override
    public void deleteBoat(int id) throws SQLException {
        String sql = "DELETE FROM Boat WHERE id=?";
        try (
                Connection connect = DatabaseConnection.getConnection();
                PreparedStatement stmt = connect.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public Boat mapnewboat(ResultSet rs) throws SQLException {
        Boat boat = new Boat(
                rs.getInt("id"),
                rs.getString("brand"),
                rs.getString("model"),
                rs.getDouble("pricePerDay"),
                rs.getInt("seats"),
                rs.getString("plateNumber"),
                rs.getBoolean("available")
        );
        return boat;
    }
}



