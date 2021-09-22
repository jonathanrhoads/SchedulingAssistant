package DAO;

import Model.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class AppointmentDAO {

    public static ObservableList<Appointment> getAppointments () throws SQLException {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        String stmt = "SELECT * FROM appointments";
        Query.makeQuery(DBConnection.openConnection(), stmt);
        ResultSet result = Query.getResult();
        while(result.next()) {
            int appointmentId = result.getInt("Appointment_ID");
            String title = result.getString("Title");
            String description = result.getString("Description");
            String location = result.getString("Location");
            String type = result.getString("Type");
            LocalDateTime start = result.getTimestamp("Start").toLocalDateTime();
            LocalDateTime end = result.getTimestamp("End").toLocalDateTime();
            int customerId = result.getInt("Customer_ID");
            int userId = result.getInt("User_ID");
            int contactId = result.getInt("Contact_ID");
            appointments.add(new Appointment(appointmentId, title, description, location, type,
                    start, end, customerId, userId , contactId));
        }
        DBConnection.closeConnection();
        return appointments;
    }
}
