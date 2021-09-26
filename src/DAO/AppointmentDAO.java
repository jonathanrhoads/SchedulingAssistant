package DAO;

import Model.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.Month;

/**
 * The type Appointment dao.
 */
public class AppointmentDAO {

    /**
     * Gets appointments.
     *
     * @return the appointments
     * @throws SQLException the sql exception
     */
    public static ObservableList<Appointment> getAppointments () throws SQLException {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        String stmt = "SELECT a.Appointment_ID, a.Title, a.Description, a.Location, " +
                "a.Type, a.Start, a.End, a.Customer_ID, a.User_ID, a.Contact_ID, c.Contact_Name" +
                " FROM appointments AS a JOIN contacts AS c ON a.Contact_ID = c.Contact_ID;";
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
            String contactName = result.getString("Contact_Name");
            appointments.add(new Appointment(appointmentId, title, description, location, type,
                    start, end, customerId, userId , contactId, contactName));
        }
        DBConnection.closeConnection();
        return appointments;
    }

    /**
     * Add appointment.
     *
     * @param appointment the appointment
     * @throws SQLException the sql exception
     */
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

    /**
     * Update appointment.
     *
     * @param appointment the appointment
     * @throws SQLException the sql exception
     */
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

    /**
     * Delete appointment.
     *
     * @param appointment the appointment
     * @throws SQLException the sql exception
     */
    public static void deleteAppointment(Appointment appointment) throws SQLException {
        String stmt = "DELETE FROM appointments WHERE Appointment_ID = " + appointment.getAppointmentId() + ";";
        Query.makeQuery(DBConnection.openConnection(), stmt);
    }

    /**
     * Gets appointments by type and month.
     *
     * @return the appointments by type and month
     * @throws SQLException the sql exception
     */
    public static ObservableList<String> getAppointmentsByTypeAndMonth () throws SQLException {
        ObservableList<String> data = FXCollections.observableArrayList();
        String stmt = "SELECT Type, MONTH(Start) AS Month, COUNT(*) AS Count FROM appointments GROUP BY Type, Month;";
        Query.makeQuery(DBConnection.openConnection(), stmt);
        ResultSet result = Query.getResult();
        while(result.next()) {
            String type = result.getString("Type");
            String month = result.getString("Month");
            String count = result.getString("Count");
            String line = "Type: " + type + "\tMonth: " + Month.of(Integer.parseInt(month)) + "\tCount: " + count + "\n";
            data.add(line);
        }
        DBConnection.closeConnection();
        return data;
    }
}
