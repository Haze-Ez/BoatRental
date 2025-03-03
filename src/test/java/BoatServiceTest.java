import Ezebuiro.Config.AppConfig;
import Ezebuiro.Database_Operations_Control.Implements.BoatDAO;
import Ezebuiro.Entities.Boat;
import Ezebuiro.Services.BoatService;
import Ezebuiro.Utility.DatabaseInitializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BoatServiceTest {

    private BoatService boatService = null;
    AnnotationConfigApplicationContext context = null;
    DatabaseInitializer dbinit = null;

    @BeforeEach
    void setUp() throws SQLException {
        context = new AnnotationConfigApplicationContext(AppConfig.class);
        dbinit = context.getBean(DatabaseInitializer.class);
        dbinit.initialize();
        boatService = context.getBean(BoatService.class);

    }

    @Test
    public void testAddBoat () throws SQLException {
        int oldBoats = boatService.getAllBoats().size();
        Boat boat = new Boat(0,"Tank","XPV-311",400.0,350,"ABAB1243",true);
        boatService.addBoat(boat);
        int newBoats = boatService.getAllBoats().size();
        assertTrue(newBoats == oldBoats+1);
    }

    @Test
    public void getAllBoats() throws SQLException {
        List<Boat> boats = boatService.getAllBoats();
        int numberOfBoats = boats.size();
        assertNotNull(boats);
        assertFalse(boats.isEmpty());
        for(Boat boat : boats) {
            assertNotNull(boat);
        }
        assertTrue(numberOfBoats == 10);//10 Boats//
    }

    @Test
    public void getBoatById() throws SQLException {
        Boat boat = boatService.getBoatById(1);
        assertNotNull(boat);
        assertTrue(boat.getId() == 1);
        assertTrue(boat.getBrand().equals("Bayliner"));

    }

    @Test
    public void getBoatbyBrand() throws SQLException {
        List<Boat> boats = boatService.getBoatsByBrand("Yamaha");
        Boat Yamaha = boats.getLast();
        assertNotNull(Yamaha);
        assertTrue(boats.contains(Yamaha));

    }

    @Test
    public void UpdateBoatAvailability() throws SQLException {
        Boat boat1 = boatService.getAllBoats().getFirst();
        boolean initial_state = boat1.isAvailable();

        if(boat1.isAvailable()){
            boatService.UpdateBoat(boat1,false);
            boat1 = boatService.getAllBoats().getFirst();
            boolean update = boat1.isAvailable();
            assertFalse(initial_state==update);
        }
        else{
            boatService.UpdateBoat(boat1,true);
            boat1 = boatService.getAllBoats().getFirst();
            boolean update = boat1.isAvailable();
            assertFalse(initial_state==update);
        }

    }

    @Test
    public void Advanced_search() throws SQLException {
        List<Boat> boatSearch = boatService.Advancedsearch(6,10 ,400.0,"Yamaha","SX210");
        boolean result = boatSearch.size() >= 1;
        if(result){
            System.out.println("The filter returned the following boats:");
            for(Boat boat : boatSearch){
                System.out.println(boat);
            }
        }else{
            System.out.println("The filter did not return anything:");
        }
    }


     @Test
    public void testDeleteBoat () throws SQLException {
         Boat boat = new Boat(0,"Tank","XPV-311",400.0,350,"ABAB1243",true);
         boatService.addBoat(boat); //In order to prevent foreign key violations//
         int initialsize= boatService.getAllBoats().size(); //10+1//
         boatService.delete(11);
         int afterdelete = boatService.getAllBoats().size(); //10//
         assertTrue(afterdelete==initialsize-1); //1 boat deleted//

     }
}
