package Ezebuiro.Database_Operations_Control;

import Ezebuiro.Entities.Boat;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

@Component
public interface IBoatDAO {
    void addBoat(Boat boat) throws SQLException;
    Boat getBoatById(int id) throws SQLException;
    List<Boat> getBoatbyBrand(String brand) throws SQLException;
    List<Boat> getAllBoats() throws SQLException;
    void updateBoat(Boat boat,boolean available) throws SQLException;
    List<Boat> searchBoats(int minseat, int maxseat, double maxRentPrice, String brand, String model) throws SQLException;
    void deleteBoat(int id) throws SQLException;
}