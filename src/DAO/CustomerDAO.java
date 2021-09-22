package DAO;

import Model.Appointment;
import Model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class CustomerDAO {

    public static ObservableList<Customer> getCustomers() throws SQLException {
        ObservableList<Customer> customers = FXCollections.observableArrayList();
        String stmt = "SELECT c.Customer_ID, c.Customer_Name, c.Address, c.Postal_Code, c.Phone, " +
                "d.Division, co.Country, c.Division_ID FROM customers AS c JOIN first_level_divisions AS d ON c.Division_ID = d.Division_ID " +
                "JOIN countries AS co ON d.Country_ID = co.Country_ID";
        Query.makeQuery(DBConnection.openConnection(), stmt);
        ResultSet result = Query.getResult();
        while(result.next()) {
            int customerId = result.getInt("Customer_ID");
            String customerName = result.getString("Customer_Name");
            String address = result.getString("Address");
            String postalCode = result.getString("Postal_Code");
            String phone = result.getString("Phone");
            String division = result.getString("Division");
            String country = result.getString("Country");
            int divisionId = result.getInt("Division_ID");
            customers.add(new Customer(customerId, customerName, address, postalCode, phone,
                    division, country, divisionId));
        }
        DBConnection.closeConnection();
        return customers;
    }


}
