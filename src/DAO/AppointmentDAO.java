package DAO;

import Model.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
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

    public static void addAppointment(Appointment appointment) throws SQLException {
        String stmt = "INSERT INTO appointments (Appointment_ID, Title, Description, " +
                "Location, Type, Start, End, Customer_ID, User_ID, Contact_ID) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

        Connection connection = DBConnection.openConnection();
        PreparedStatement pstmt = connection.prepareStatement(stmt);

        pstmt.setInt(1, appointment.getAppointmentId());
        pstmt.setString(2, appointment.getTitle());
        pstmt.setString(3, appointment.getDescription());
        pstmt.setString(4, appointment.getLocation());
        pstmt.setString(5, appointment.getType());
        pstmt.setTimestamp(6, Timestamp.valueOf(appointment.getStart()));
        pstmt.setTimestamp(7, Timestamp.valueOf(appointment.getEnd()));
        pstmt.setInt(8, appointment.getCustomerId());
        pstmt.setInt(9, appointment.getUserId());
        pstmt.setInt(10, appointment.getContactId());

        pstmt.executeUpdate();
    }

    public static void updateAppointment(Appointment appointment) throws SQLException {
        String stmt = "UPDATE appointments " +
                "SET Title = ?, Description = ?, " +
                "Location = ?, Type = ?, Start = ?, End = ?, Customer_ID = ?, " +
                "User_ID = ?, Contact_ID = ? " +
                "WHERE Appointment_ID = " + appointment.getAppointmentId() + ";";

        Connection connection = DBConnection.openConnection();
        PreparedStatement pstmt = connection.prepareStatement(stmt);

        pstmt.setString(1, appointment.getTitle());
        pstmt.setString(2, appointment.getDescription());
        pstmt.setString(3, appointment.getLocation());
        pstmt.setString(4, appointment.getType());
        pstmt.setTimestamp(5, Timestamp.valueOf(appointment.getStart()));
        pstmt.setTimestamp(6, Timestamp.valueOf(appointment.getEnd()));
        pstmt.setInt(7, appointment.getCustomerId());
        pstmt.setInt(8, appointment.getUserId());
        pstmt.setInt(9, appointment.getContactId());

        pstmt.executeUpdate();
    }

    public static void deleteAppointment(Appointment appointment) throws SQLException {
        String stmt = "DELETE FROM appointments WHERE Appointment_ID = " + appointment.getAppointmentId() + ";";
        Query.makeQuery(DBConnection.openConnection(), stmt);
    }
}
