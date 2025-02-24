package Ezebuiro.Entities;

import Ezebuiro.Database_Operations_Control.BoatDAO;
import Ezebuiro.Database_Operations_Control.CustomerDAO;
import Ezebuiro.Entities.Customer;
import Ezebuiro.Entities.Boat;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Objects;

public class RentalEvent {
    private int id;
    private int boatId;
    private int customerId;
    private Date rentalDate;
    private Date returnDate;
    private double totalCost;
    private boolean isClosed;

    private BoatDAO boatDAO;
    private CustomerDAO customerDAO;

    public RentalEvent(int id, int boatId, int customerId, Date rentalDate, Date returnDate, Double totalCost, boolean isClosed) {
        this.id = id;
        this.boatId = boatId;
        this.customerId = customerId;
        this.rentalDate = rentalDate;
        this.returnDate = returnDate;
        this.totalCost = totalCost;
        this.isClosed = isClosed;

        this.boatDAO = new BoatDAO();
        this.customerDAO = new CustomerDAO();

    }


    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBoatId() {
        return boatId;
    }

    public void setBoatId(int boatId) {
        this.boatId = boatId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public Date getRentalDate() {
        return rentalDate;
    }

    public void setRentalDate(Date rentalDate) {
        this.rentalDate = rentalDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public void setClosed(boolean isClosed) {
        this.isClosed = isClosed;
    }


    @Override
    public String toString() {
        String boatBrand = null;
        boatBrand = (boatDAO != null) ? boatDAO.searchById(boatId).getBrand() : "N/A";
        String customerFirstName = null;
        try {
            customerFirstName = (customerDAO != null) ? customerDAO.searchbyid(customerId).getFirstName() : "N/A";
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String customerLastName = null;
        try {
            customerLastName = (customerDAO != null) ? customerDAO.searchbyid(customerId).getLastName() : "N/A";
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return "{ RentalEvent -- " +
                "id=" + id +
                ", boat_Brand=" + boatBrand +
                ", customer_FirstName=" + customerFirstName +
                ", customer_LastName=" + customerLastName +
                ", rentalDate=" + rentalDate +
                ", returnDate=" + returnDate +
                ", totalCost=" + totalCost +
                ", isClosed=" + isClosed +
                " --}";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        RentalEvent event = (RentalEvent) obj;
        return id == event.id &&
                boatId == event.boatId &&
                customerId == event.customerId &&
                rentalDate.equals(event.rentalDate) &&
                Objects.equals(returnDate, event.returnDate); // returnDate can be null
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, boatId, customerId, rentalDate, returnDate);
    }




}