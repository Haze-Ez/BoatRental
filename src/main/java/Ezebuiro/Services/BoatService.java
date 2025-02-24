package Ezebuiro.Services;


import Ezebuiro.Database_Operations_Control.BoatDAO;
import Ezebuiro.Entities.Boat;

import java.sql.SQLException;
import java.util.List;

public class BoatService {
    private BoatDAO boatDAO;

    public BoatService() {
        this.boatDAO = new BoatDAO();
    }

    public void create(Boat boat){
        boatDAO.createBoat(boat);
    }

    public List<Boat> getAllBoats(){
        return boatDAO.getAllBoats();
    }

    public List<Boat> getBoatsByBrand(String brand){
       return boatDAO.SearchbyBrand(brand);
    }

    public Boat getBoat(int id) throws SQLException {
        return boatDAO.searchById(id);
    }

    public List<Boat> Advancedsearch(int min, int max, double price, String brand, String model) throws SQLException {

        return boatDAO.Advancedsearch(min, max, price, brand, model);
    }

    public void Availability(Boat boat,boolean available) throws SQLException {
        boatDAO.updateBoatAvailability(boat,available);
    }

    public  void delete(int id) throws SQLException {
        boatDAO.deleteBoat(id);
    }
}
