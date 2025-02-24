package Ezebuiro.Entities;

import java.util.Objects;

public class Customer {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String boatLicense;
    private String countrycode;

    public Customer(int id, String firstName, String lastName, String email, String boatLicense, String countrycode) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.boatLicense = boatLicense;
        this.countrycode = countrycode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBoatLicense() {
        return boatLicense;
    }

    public void setBoatLicense(String boatLicense) {
        this.boatLicense = boatLicense;
    }

    public String getCountrycode() {
        return countrycode;
    }

    public void setCountrycode(String countrycode) {
        this.countrycode = countrycode;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", boatLicense='" + boatLicense + '\'' +
                ", countrycode='" + countrycode + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Customer customer = (Customer) obj;
        return id == customer.id &&
                firstName.equals(customer.firstName) &&
                lastName.equals(customer.lastName) &&
                email.equals(customer.email) &&
                boatLicense.equals(customer.boatLicense) &&
                countrycode.equals(customer.countrycode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email, boatLicense, countrycode);
    }

}

