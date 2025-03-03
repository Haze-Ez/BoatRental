package Ezebuiro.Services;


import Ezebuiro.Database_Operations_Control.BoatDAO;
import Ezebuiro.Entities.Boat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class BoatService {
    private final BoatDAO boatDAO;

    @Autowired
    public BoatService(BoatDAO boatDAO) {
        this.boatDAO = new BoatDAO();
    }

    public void addBoat(Boat boat){
        boatDAO.addBoat(boat);
    }

    public List<Boat> getAllBoats(){
        return boatDAO.getAllBoats();
    }

    public List<Boat> getBoatsByBrand(String brand){

        List<Boat> boats = null;
        try {
            boats= boatDAO.getBoatbyBrand(brand);
        } catch (SQLException e) {
            e.getMessage();
        }
        return boats;
    }

    public Boat getBoatById(int id) throws SQLException {
        return boatDAO.getBoatById(id);
    }

    public List<Boat> Advancedsearch(int min, int max, double price, String brand, String model) throws SQLException {

        return boatDAO.searchBoats(min, max, price, brand, model);
    }

    public void Availability(Boat boat,boolean available) throws SQLException {
        boatDAO.updateBoat(boat,available);
    }

    public  void delete(int id) throws SQLException {
        boatDAO.deleteBoat(id);
    }
}
