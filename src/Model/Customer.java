package Model;

import DAO.CustomerDAO;
import javafx.collections.ObservableList;

import java.sql.SQLException;

/**
 * The type Customer.
 */
public class Customer {
    private int customerId;
    private String customerName;
    private String address;
    private String postalCode;
    private String phone;
    private String division;
    private String country;
    private int divisionId;

    /**
     * Next customer id int.
     *
     * @return the int
     * @throws SQLException the sql exception
     */
    public static int nextCustomerId() throws SQLException {
        ObservableList<Customer> customers = CustomerDAO.getCustomers();
        int lastId = 0;
        for (Customer customer : customers) {
            if(customer.getCustomerId() > lastId) {
                lastId = customer.getCustomerId();
            }
        }
        return lastId + 1;
    }

    /**
     * Instantiates a new Customer.
     *
     * @param customerId   the customer id
     * @param customerName the customer name
     * @param address      the address
     * @param postalCode   the postal code
     * @param phone        the phone
     * @param division     the division
     * @param country      the country
     * @param divisionId   the division id
     */
    public Customer(int customerId, String customerName, String address, String postalCode, String phone, String division, String country, int divisionId) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.division = division;
        this.country = country;
        this.divisionId = divisionId;
    }

    /**
     * Gets customer id.
     *
     * @return the customer id
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * Sets customer id.
     *
     * @param customerId the customer id
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * Gets customer name.
     *
     * @return the customer name
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * Sets customer name.
     *
     * @param customerName the customer name
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * Gets address.
     *
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets address.
     *
     * @param address the address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets postal code.
     *
     * @return the postal code
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Sets postal code.
     *
     * @param postalCode the postal code
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * Gets phone.
     *
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets phone.
     *
     * @param phone the phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Gets division.
     *
     * @return the division
     */
    public String getDivision() {
        return division;
    }

    /**
     * Sets division.
     *
     * @param division the division
     */
    public void setDivision(String division) {
        this.division = division;
    }

    /**
     * Gets country.
     *
     * @return the country
     */
    public String getCountry() {
        return country;
    }

    /**
     * Sets country.
     *
     * @param country the country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Gets division id.
     *
     * @return the division id
     */
    public int getDivisionId() {
        return divisionId;
    }

    /**
     * Sets division id.
     *
     * @param divisionId the division id
     */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }
}
