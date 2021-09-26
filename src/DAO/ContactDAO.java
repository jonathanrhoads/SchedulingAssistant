package DAO;

import Model.Appointment;
import Model.Contact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * The type Contact dao.
 */
public class ContactDAO {

    /**
     * Gets contacts.
     *
     * @return the contacts
     * @throws SQLException the sql exception
     */
    public static ObservableList<Contact> getContacts () throws SQLException {
        ObservableList<Contact> contacts = FXCollections.observableArrayList();
        String stmt = "SELECT * FROM contacts";
        Query.makeQuery(DBConnection.openConnection(), stmt);
        ResultSet result = Query.getResult();
        while (result.next()) {
            int contactId = result.getInt("Contact_ID");
            String contactName = result.getString("Contact_Name");
            String email = result.getString("Email");
            contacts.add(new Contact(contactId, contactName, email));
        }
        DBConnection.closeConnection();
        return contacts;
    }

    /**
     * Gets contact schedules.
     *
     * @return the contact schedules
     * @throws SQLException the sql exception
     */
    public static ObservableList<String> getContactSchedules () throws SQLException {
        ObservableList<String> appointments = FXCollections.observableArrayList();
        String stmt = "SELECT a.Appointment_ID, a.Title, a.Description, " +
                "a.Type, a.Start, a.End, a.Customer_ID, c.Contact_Name" +
                " FROM appointments AS a JOIN contacts AS c ON a.Contact_ID = c.Contact_ID " +
                "ORDER BY c.Contact_Name, a.Start;";
        Query.makeQuery(DBConnection.openConnection(), stmt);
        ResultSet result = Query.getResult();
        while(result.next()) {
            int appointmentId = result.getInt("Appointment_ID");
            String title = result.getString("Title");
            String description = result.getString("Description");
            String type = result.getString("Type");
            LocalDateTime start = result.getTimestamp("Start").toLocalDateTime();
            LocalDateTime end = result.getTimestamp("End").toLocalDateTime();
            int customerId = result.getInt("Customer_ID");
            String contactName = result.getString("Contact_Name");

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");
            String line = contactName + "- ID: " + appointmentId + " Title: " + title + " Type: " + type +
                    " Begins: " + dtf.format(start) + " Ends: " + dtf.format(end) + " Customer ID: " + customerId + "\n";
            appointments.add(line);
        }
        DBConnection.closeConnection();
        return appointments;
    }
}
