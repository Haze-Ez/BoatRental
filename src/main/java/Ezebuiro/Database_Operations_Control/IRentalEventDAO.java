package Ezebuiro.Database_Operations_Control;

import Ezebuiro.Entities.RentalEvent;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;


@Component
public interface IRentalEventDAO {
    void addRentalEvent(RentalEvent event) throws SQLException;
    RentalEvent getRentalEventById(int id) throws SQLException;
    List<RentalEvent> getAllRentalEvents() throws SQLException;
    void updateRentalEvent(RentalEvent event, Date returnDate, boolean isClosed, Double TotalCost) throws SQLException;
    void deleteRentalEvent(int id) throws SQLException;
}