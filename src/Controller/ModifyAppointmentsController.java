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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ResourceBundle;

public class ModifyAppointmentsController implements Initializable {
    public TextField customerIdTextField;
    public TextField contactIdTextField;
    public TextField appointmentIdTextField;
    public TextField titleTextField;
    public TextField locationTextField;
    public TextField typeTextField;
    public TextArea descriptionTextField;
    public DatePicker datePicker;
    public Button saveButton;
    public Button cancelButton;
    public ComboBox addCustomerComboBox;
    public ComboBox addContactComboBox;
    public ComboBox startHourComboBox;
    public ComboBox startMinuteComboBox;
    public ComboBox endHourComboBox;
    public ComboBox endMinuteComboBox;
    ObservableList<String> hours = FXCollections.observableArrayList();
    ObservableList<String> endHours = FXCollections.observableArrayList();
    ObservableList<String> minutes = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Appointment appointment = MainMenuController.appointmentToModify;
            ObservableList<Customer> customers = CustomerDAO.getCustomers();
            ObservableList<String> customerNames = FXCollections.observableArrayList();
            ObservableList<Contact> contacts = ContactDAO.getContacts();
            ObservableList<String> contactNames = FXCollections.observableArrayList();
            String selectedCustomerName = "";
            String selectedContactName = "";

            for(Customer customer : customers) {
                customerNames.add(customer.getCustomerName());
                if(appointment.getCustomerId() == customer.getCustomerId()) {
                    selectedCustomerName = customer.getCustomerName();
                }
            }

            for(Contact contact : contacts) {
                contactNames.add(contact.getContactName());
                if(appointment.getContactId() == contact.getContactId()) {
                    selectedContactName = contact.getContactName();
                }
            }

            appointmentIdTextField.setText(String.valueOf(appointment.getAppointmentId()));
            addCustomerComboBox.setItems(customerNames);
            addContactComboBox.setItems(contactNames);
            addCustomerComboBox.setValue(selectedCustomerName);
            addContactComboBox.setValue(selectedContactName);

            hours.addAll("08", "09", "10", "11", "12", "13", "14",
                    "15", "16", "17", "18", "19", "20", "21");
            minutes.addAll("00", "15", "30", "45");
            startHourComboBox.setItems(hours);
            endHours = hours;
            endHours.add("22");
            endHourComboBox.setItems(endHours);
            startMinuteComboBox.setItems(minutes);
            endMinuteComboBox.setItems(minutes);

            datePicker.setValue(appointment.getStart().toLocalDate());
            startHourComboBox.setValue(appointment.getStart().getHour());
            startMinuteComboBox.setValue(appointment.getStart().getMinute());
            endHourComboBox.setValue(appointment.getEnd().getHour());
            endMinuteComboBox.setValue(appointment.getEnd().getMinute());

            customerIdTextField.setText(String.valueOf(getCustomerId()));
            contactIdTextField.setText(String.valueOf(getContactId()));
            titleTextField.setText(appointment.getTitle());
            locationTextField.setText(appointment.getLocation());
            typeTextField.setText(appointment.getType());
            descriptionTextField.setText(appointment.getDescription());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void onActionSave(ActionEvent actionEvent) throws SQLException, IOException {
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
                AppointmentDAO.updateAppointment(appointment);

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

    public void onActionCancel(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/MainMenuView.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 880, 850);
        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.show();
    }

    private boolean isValidAppointment(Appointment appointment) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Incorrect Input");

        if(appointment.getTitle().isEmpty() || appointment.getTitle().isBlank()) {
            alert.setContentText("The title is either blank or empty.");
            alert.showAndWait();
            return false;
        } else if (appointment.getDescription().isEmpty() || appointment.getDescription().isBlank()){
            alert.setContentText("The description is either blank or empty.");
            alert.showAndWait();
            return false;
        } else if (appointment.getLocation().isEmpty() || appointment.getLocation().isBlank()){
            alert.setContentText("The location is either blank or empty.");
            alert.showAndWait();
            return false;
        } else if (appointment.getType().isEmpty() || appointment.getType().isBlank()) {
            alert.setContentText("The type is either blank or empty.");
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
        } else if (appointment.getStart().atZone(ZoneId.systemDefault()).withZoneSameInstant("America/New_York")){

        }

        else if (appointment.getEnd().isAfter(LocalDateTime.of(appointment.getEnd().getYear(),
                appointment.getEnd().getMonthValue(), appointment.getEnd().getDayOfMonth(), 22, 0))){
            alert.setContentText("Appointments must end by 22:00 (10PM).");
            alert.showAndWait();
            return false;
        }
        return true;
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


    public void onActionCustomerComboBox(ActionEvent actionEvent) throws SQLException {
        customerIdTextField.setText(String.valueOf(getCustomerId()));
    }

    public void onActionContactComboBox(ActionEvent actionEvent) throws SQLException {
        contactIdTextField.setText(String.valueOf(getContactId()));
    }


}
