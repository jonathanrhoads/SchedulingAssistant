package Controller;

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
            ObservableList<Contact> contacts = FXCollections.observableArrayList();
            appointmentIdTextField.setText(String.valueOf(Appointment.nextAppointmentId()));
            addCustomerComboBox.setItems();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void onActionAdd(ActionEvent actionEvent) throws SQLException {
        int appointmentId = Appointment.nextAppointmentId();
        String contactId = contactIdTextField.getText();

    }

    public void onActionCancel(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/MainMenuView.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 880, 850);
        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.show();
    }

    public void onActionCustomerComboBox(ActionEvent actionEvent) {
    }

    public void onActionContactComboBox(ActionEvent actionEvent) {
    }
}
