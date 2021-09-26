package Controller;

import DAO.AppointmentDAO;
import DAO.ContactDAO;
import DAO.CustomerDAO;
import Model.Appointment;
import Model.Contact;
import Model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.*;
import java.util.ResourceBundle;

/**
 * The type Add appointments controller.
 */
public class AddAppointmentsController implements Initializable {
    /**
     * The Customer id text field.
     */
    public TextField customerIdTextField;
    /**
     * The Contact id text field.
     */
    public TextField contactIdTextField;
    /**
     * The Appointment id text field.
     */
    public TextField appointmentIdTextField;
    /**
     * The Title text field.
     */
    public TextField titleTextField;
    /**
     * The Location text field.
     */
    public TextField locationTextField;
    /**
     * The Type text field.
     */
    public TextField typeTextField;
    /**
     * The Description text field.
     */
    public TextArea descriptionTextField;
    /**
     * The Date picker.
     */
    public DatePicker datePicker;
    /**
     * The Add button.
     */
    public Button addButton;
    /**
     * The Cancel button.
     */
    public Button cancelButton;
    /**
     * The Add customer combo box.
     */
    public ComboBox addCustomerComboBox;
    /**
     * The Add contact combo box.
     */
    public ComboBox addContactComboBox;
    /**
     * The Start hour combo box.
     */
    public ComboBox startHourComboBox;
    /**
     * The Start minute combo box.
     */
    public ComboBox startMinuteComboBox;
    /**
     * The End hour combo box.
     */
    public ComboBox endHourComboBox;
    /**
     * The End minute combo box.
     */
    public ComboBox endMinuteComboBox;
    /**
     * The Hours.
     */
    ObservableList<String> hours = FXCollections.observableArrayList();
    /**
     * The Minutes.
     */
    ObservableList<String> minutes = FXCollections.observableArrayList();

    /**
     * Initialized the screen.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ObservableList<Customer> customers = CustomerDAO.getCustomers();
            ObservableList<String> customerNames = FXCollections.observableArrayList();
            ObservableList<Contact> contacts = ContactDAO.getContacts();
            ObservableList<String> contactNames = FXCollections.observableArrayList();

            for(Customer customer : customers) {
                customerNames.add(customer.getCustomerName());
            }

            for(Contact contact : contacts) {
                contactNames.add(contact.getContactName());
            }

            appointmentIdTextField.setText(String.valueOf(Appointment.nextAppointmentId()));
            addCustomerComboBox.setItems(customerNames);
            addContactComboBox.setItems(contactNames);

            hours.addAll("00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14",
                    "15", "16", "17", "18", "19", "20", "21", "22", "23");
            minutes.addAll("00", "15", "30", "45");
            startHourComboBox.setItems(hours);
            endHourComboBox.setItems(hours);
            startMinuteComboBox.setItems(minutes);
            endMinuteComboBox.setItems(minutes);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * On action add.
     *
     * @param actionEvent the action event
     * @throws SQLException the sql exception
     * @throws IOException  the io exception
     */
    public void onActionAdd(ActionEvent actionEvent) throws SQLException, IOException {
        try {
            int appointmentId = Integer.parseInt(appointmentIdTextField.getText());
            int customerId = getCustomerId();
            int contactId = getContactId();
            int userId = LogInController.currentUserId;
            String contactName = String.valueOf(addContactComboBox.getSelectionModel().getSelectedItem());
            String title = titleTextField.getText();
            String description = descriptionTextField.getText();
            String location = locationTextField.getText();
            String type = typeTextField.getText();
            LocalDate date = datePicker.getValue();
            LocalDateTime start = LocalDateTime.of(date.getYear(), date.getMonthValue(),
                    date.getDayOfMonth(), Integer.parseInt(String.valueOf(startHourComboBox.getValue())),
                    Integer.parseInt(String.valueOf(startMinuteComboBox.getValue())));
            LocalDateTime end = LocalDateTime.of(date.getYear(), date.getMonthValue(),
                    date.getDayOfMonth(), Integer.parseInt(String.valueOf(endHourComboBox.getValue())),
                    Integer.parseInt(String.valueOf(endMinuteComboBox.getValue())));

            Appointment appointment = new Appointment(appointmentId, title, description,
                    location, type, start, end, customerId, userId, contactId, contactName);

            if(isValidAppointment(appointment)){
                AppointmentDAO.addAppointment(appointment);

                Parent root = FXMLLoader.load(getClass().getResource("/View/MainMenuView.fxml"));
                Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root, 880, 850);
                stage.setTitle("Main Menu");
                stage.setScene(scene);
                stage.show();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Input");
            alert.setContentText("Please ensure all fields are filled out and correct.");
            alert.showAndWait();
        }

    }

    /**
     * Checks to make sure the inputs are valid
     *
     * @param appointment
     * @return
     * @throws SQLException
     */
    private boolean isValidAppointment(Appointment appointment) throws SQLException {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Incorrect Input");

        if(appointment.getTitle().isEmpty() || appointment.getTitle().isBlank()) {
            alert.setContentText("The title cannot be blank or empty.");
            alert.showAndWait();
            return false;
        } else if (appointment.getDescription().isEmpty() || appointment.getDescription().isBlank()){
            alert.setContentText("The description is either blank or empty.");
            alert.showAndWait();
            return false;
        } else if (appointment.getLocation().isEmpty() || appointment.getLocation().isBlank()){
            alert.setContentText("The location cannot be blank or empty.");
            alert.showAndWait();
            return false;
        } else if (appointment.getType().isEmpty() || appointment.getType().isBlank()) {
            alert.setContentText("The type cannot be blank or empty.");
            alert.showAndWait();
            return false;
        } else if (appointment.getStart().isAfter(appointment.getEnd())){
            alert.setContentText("The start time must be before the end time.");
            alert.showAndWait();
            return false;
        } else if (LocalDateTime.now().isAfter(appointment.getStart())) {
            alert.setContentText("The start time must be in the future.");
            alert.showAndWait();
            return false;
        } else if (appointment.getContactId() == 0 || appointment.getCustomerId() == 0){
            alert.setContentText("Valid choices for contact and customer are required.");
            alert.showAndWait();
            return false;
        }

        if (hasConflict(appointment, alert)) return false;
        return !checkWithinBusinessHours(appointment, alert);
    }

    /**
     * This method checks whether a customer already has another appointment scheduled for that selected time.
     * @param appointment The appointment to be created
     * @param alert the alert object created in calling method
     * @return boolean
     *
     */
    private boolean hasConflict(Appointment appointment, Alert alert) throws SQLException {
        ObservableList<Appointment> allAppointments = AppointmentDAO.getAppointments();
        for(Appointment appt : allAppointments){
            if(appt.getCustomerId() == appointment.getCustomerId() &&
                    ((appt.getStart().isBefore(appointment.getEnd()) && appt.getStart().isAfter(appointment.getStart())) ||
                            (appt.getEnd().isBefore(appointment.getEnd()) && appt.getEnd().isAfter(appointment.getStart())) ||
                            (appt.getEnd().equals(appointment.getEnd()) && appt.getStart().equals(appointment.getStart())))) {

                alert.setHeaderText("Appointment Conflict");
                alert.setContentText("This appointment overlaps with ID: " + appt.getAppointmentId());
                alert.showAndWait();
                return true;
            }
        }
        return false;
    }

    /**
     * This method compares the input time values to the east coast open and close times to determine
     * if the users input is valid. The user puts their time in using their local time zone, that will then be converted
     * to Eastern Standard time which is the zone used by the business.
     * @param appointment appointment to confirm
     * @param alert alert
     * @return
     */
    private boolean checkWithinBusinessHours(Appointment appointment, Alert alert) {
        ZonedDateTime startEST = getEST(LocalDateTime.of(appointment.getStart().getYear(),
                appointment.getStart().getMonth(), appointment.getStart().getDayOfMonth(),
                appointment.getStart().getHour(), appointment.getStart().getMinute()));
        ZonedDateTime endEST = getEST(LocalDateTime.of(appointment.getEnd().getYear(),
                appointment.getEnd().getMonth(), appointment.getEnd().getDayOfMonth(),
                appointment.getEnd().getHour(), appointment.getEnd().getMinute()));
        ZonedDateTime open = ZonedDateTime.of((LocalDateTime.of(appointment.getEnd().getYear(),
                appointment.getEnd().getMonth(), appointment.getEnd().getDayOfMonth(),
                8, 0)), ZoneId.of("America/New_York"));
        ZonedDateTime close = ZonedDateTime.of((LocalDateTime.of(appointment.getEnd().getYear(),
                appointment.getEnd().getMonth(), appointment.getEnd().getDayOfMonth(),
                22, 0)), ZoneId.of("America/New_York"));

        if(startEST.isBefore(open) || endEST.isBefore(open) ||
                startEST.isAfter(close) || endEST.isAfter(close)) {

            alert.setHeaderText("Incorrect Time Input");
            alert.setContentText("Your appointment must be during the business hours\n" +
                    " of 8:00 and 22:00 Eastern Standard Time.");
            alert.showAndWait();
            return true;
        }
        return false;
    }

    /**
     * This method changes a time to Eastern Standard Time.
     * @param time
     * @return
     */
    private ZonedDateTime getEST(LocalDateTime time) {
        ZonedDateTime zoneDate = time.atZone(ZoneId.systemDefault());
        return zoneDate.withZoneSameInstant(ZoneId.of("America/New_York"));
    }

    /**
     * This method returns the contact ID of the selected contact in the contact combo box.
     * @return
     * @throws SQLException
     */
    private int getContactId() throws SQLException {
        ObservableList<Contact> contacts = ContactDAO.getContacts();
        String contactName = String.valueOf(addContactComboBox.getSelectionModel().getSelectedItem());
        for(Contact contact : contacts){
            if(contact.getContactName().equals(contactName)){
                return contact.getContactId();
            }
        }
        return 0;
    }

    /**
     * This method returns the customer ID of the selected contact in the customer combo box.
     * @return
     * @throws SQLException
     */
    private int getCustomerId() throws SQLException {
        ObservableList<Customer> customers = CustomerDAO.getCustomers();
        String customerName = String.valueOf(addCustomerComboBox.getSelectionModel().getSelectedItem());
        for(Customer customer : customers){
            if(customer.getCustomerName().equals(customerName)){
                return customer.getCustomerId();
            }
        }
        return 0;
    }

    /**
     * On action cancel.
     *
     * @param actionEvent the action event
     * @throws IOException the io exception
     */
    public void onActionCancel(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/MainMenuView.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 880, 850);
        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * On action customer combo box.
     *
     * @param actionEvent the action event
     * @throws SQLException the sql exception
     */
    public void onActionCustomerComboBox(ActionEvent actionEvent) throws SQLException {
        customerIdTextField.setText(String.valueOf(getCustomerId()));
    }

    /**
     * On action contact combo box.
     *
     * @param actionEvent the action event
     * @throws SQLException the sql exception
     */
    public void onActionContactComboBox(ActionEvent actionEvent) throws SQLException {
        contactIdTextField.setText(String.valueOf(getContactId()));
    }
}
