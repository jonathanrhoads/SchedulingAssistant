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

public class AddAppointmentsController implements Initializable {
    public TextField customerIdTextField;
    public TextField contactIdTextField;
    public TextField appointmentIdTextField;
    public TextField titleTextField;
    public TextField locationTextField;
    public TextField typeTextField;
    public TextArea descriptionTextField;
    public DatePicker datePicker;
    public Button addButton;
    public Button cancelButton;
    public ComboBox addCustomerComboBox;
    public ComboBox addContactComboBox;
    public ComboBox startHourComboBox;
    public ComboBox startMinuteComboBox;
    public ComboBox endHourComboBox;
    public ComboBox endMinuteComboBox;
    ObservableList<String> hours = FXCollections.observableArrayList();
    ObservableList<String> minutes = FXCollections.observableArrayList();

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

    private ZonedDateTime getEST(LocalDateTime time) {
        ZonedDateTime zoneDate = time.atZone(ZoneId.systemDefault());
        return zoneDate.withZoneSameInstant(ZoneId.of("America/New_York"));
    }

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

    public void onActionCancel(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/MainMenuView.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 880, 850);
        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.show();
    }

    public void onActionCustomerComboBox(ActionEvent actionEvent) throws SQLException {
        customerIdTextField.setText(String.valueOf(getCustomerId()));
    }

    public void onActionContactComboBox(ActionEvent actionEvent) throws SQLException {
        contactIdTextField.setText(String.valueOf(getContactId()));
    }
}
