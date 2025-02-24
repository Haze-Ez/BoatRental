import Ezebuiro.Entities.Boat;
import Ezebuiro.Services.BoatService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BoatServiceTest {

    private BoatService boatService;
    private Boat boat1;
    List <Boat> boats;
    @BeforeEach
    void setUp() throws SQLException {
        boatService = new BoatService();
        boats = boatService.getAllBoats();

    }
    @Test
    public void testAddBoat () {
       try {
           if (!(boats.isEmpty())) {
               boatService.delete(boats.getLast().getId());
           }
       }catch(SQLException e) {
           e.printStackTrace();
       }

       Boat boat = new Boat(0,"Tank","XPV-311",400.0,350,"ABAB1243",true);
        boatService.create(boat);
        boats = boatService.getAllBoats();

        assertFalse(boats.isEmpty());
        Boat sqlBoat = boats.getLast();

        assertEquals(boat, sqlBoat);
    }

    @Test
    public void getAllBoats() {
        List<Boat> boats = boatService.getAllBoats();
        assertNotNull(boats);
        assertFalse(boats.isEmpty());
        for(Boat boat : boats) {
            assertNotNull(boat);
        }
    }

    @Test//Test ran after delete test
    public void getBoatById() throws SQLException {
        boats = boatService.getAllBoats();
        Boat lastBoat = boats.getLast();
        assertEquals(lastBoat,boatService.getBoat(lastBoat.getId()));

    }

    @Test
    public void getBoatbyBrand() throws SQLException {
        List<Boat> boats = boatService.getBoatsByBrand("Tank");
        Boat Tank = boats.getLast();

        assertTrue(boats.contains(Tank));
        assertEquals(boats.getLast(),Tank);
    }

    @Test
    public void UpdateBoatAvailability() throws SQLException {
        boat1 = boatService.getBoatsByBrand("Tank").get(0);
        if(boat1.isAvailable()){
            boatService.Availability(boatService.getBoat(boat1.getId()),false);
            boat1 = boatService.getBoat(boat1.getId());
            assertFalse(boat1.isAvailable());
        }else{
            boatService.Availability(boatService.getBoat(boat1.getId()),true);
            boat1 = boatService.getBoat(boat1.getId());
            assertTrue(boat1.isAvailable());
        }

    }

    @Test
    public void Advanced_search() throws SQLException {
        boat1 = boatService.getBoatsByBrand("Tank").get(0);
       List<Boat> boatSearch = boatService.Advancedsearch(300,500,400.0,"Tank","XPV-311");
        assertEquals(boat1,boatSearch.get(0));
    }


}
