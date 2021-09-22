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
    public Spinner startHourSpinner;
    public Spinner startMinuteSpinner;
    public Spinner endHourSpinner;
    public Spinner endMinuteSpinner;
    public Button addButton;
    public Button cancelButton;
    public ComboBox addCustomerComboBox;
    public ComboBox addContactComboBox;

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

            startHourSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(00, 23, 00));
            startMinuteSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(00, 59, 00));
            endHourSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(00, 23, 00));
            endMinuteSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(00, 59, 00));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void onActionAdd(ActionEvent actionEvent) throws SQLException, IOException {
        int appointmentId = Integer.parseInt(appointmentIdTextField.getText());
        int customerId = getCustomerId();
        int contactId = getContactId();
        int userId = LogInController.currentUserId;
        String title = titleTextField.getText();
        String description = descriptionTextField.getText();
        String location = locationTextField.getText();
        String type = typeTextField.getText();
        LocalDateTime start = LocalDateTime.from(datePicker.getValue())
                .plusHours((Long)startHourSpinner.getValue())
                .plusMinutes((Long)startMinuteSpinner.getValue());
        LocalDateTime end = LocalDateTime.from(datePicker.getValue())
                .plusHours((Long)endHourSpinner.getValue())
                .plusMinutes((Long)endMinuteSpinner.getValue());

        Appointment appointment = new Appointment(appointmentId, title, description,
                location, type, start, end, customerId, userId, contactId);

        if(isValidAppointment(appointment)){
            AppointmentDAO.addAppointment(appointment);

            Parent root = FXMLLoader.load(getClass().getResource("/View/MainMenuView.fxml"));
            Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 880, 850);
            stage.setTitle("Main Menu");
            stage.setScene(scene);
            stage.show();
        }
    }

    private boolean isValidAppointment(Appointment appointment) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
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
