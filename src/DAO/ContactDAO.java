package DAO;

import Model.Contact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ContactDAO {

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
}
