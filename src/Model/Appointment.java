package Model;

import DAO.AppointmentDAO;
import DAO.ContactDAO;
import DAO.CustomerDAO;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * The type Appointment.
 */
public class Appointment {
    private int appointmentId;
    private String title;
    private String description;
    private String location;
    private String type;
    private LocalDateTime start;
    private LocalDateTime end;
    private int customerId;
    private int userId;
    private int contactId;
    private String contactName;

    /**
     * Next appointment id int.
     *
     * @return the int
     * @throws SQLException the sql exception
     */
    public static int nextAppointmentId() throws SQLException {
        ObservableList<Appointment> appointments = AppointmentDAO.getAppointments();
        int lastId = 0;

        for (Appointment appointment : appointments) {
            if(appointment.getAppointmentId() > lastId) {
                lastId = appointment.getAppointmentId();
            }
        }
        return lastId + 1;
    }

    /**
     * Instantiates a new Appointment.
     *
     * @param appointmentId the appointment id
     * @param title         the title
     * @param description   the description
     * @param location      the location
     * @param type          the type
     * @param start         the start
     * @param end           the end
     * @param customerId    the customer id
     * @param userId        the user id
     * @param contactId     the contact id
     * @param contactName   the contact name
     */
    public Appointment(int appointmentId, String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, int customerId, int userId, int contactId, String contactName) {
        this.appointmentId = appointmentId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
        this.contactName = contactName;
    }

    /**
     * Gets appointment id.
     *
     * @return the appointment id
     */
    public int getAppointmentId() {
        return appointmentId;
    }

    /**
     * Sets appointment id.
     *
     * @param appointmentId the appointment id
     */
    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    /**
     * Gets title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets title.
     *
     * @param title the title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets description.
     *
     * @param description the description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets location.
     *
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets location.
     *
     * @param location the location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets type.
     *
     * @param type the type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets start.
     *
     * @return the start
     */
    public LocalDateTime getStart() {
        return start;
    }

    /**
     * Sets start.
     *
     * @param start the start
     */
    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    /**
     * Gets end.
     *
     * @return the end
     */
    public LocalDateTime getEnd() {
        return end;
    }

    /**
     * Sets end.
     *
     * @param end the end
     */
    public void setEnd(LocalDateTime end) {
        this.end = end;
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
     * Gets user id.
     *
     * @return the user id
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets user id.
     *
     * @param userId the user id
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Gets contact id.
     *
     * @return the contact id
     */
    public int getContactId() {
        return contactId;
    }

    /**
     * Sets contact id.
     *
     * @param contactId the contact id
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    /**
     * Gets contact name.
     *
     * @return the contact name
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * Sets contact name.
     *
     * @param contactName the contact name
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }


}
