package Controller;

import DAO.AppointmentDAO;
import DAO.CustomerDAO;
import Model.Appointment;
import Model.Customer;
import com.sun.javafx.collections.ElementObservableListDecorator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {
    public TableView<Customer> customersTableView;
    public TableColumn customerIdCol;
    public TableColumn customerNameCol;
    public TableColumn customerAddressCol;
    public TableColumn customerDivisionCol;
    public TableColumn customerCountryCol;
    public TableColumn customerPostalCol;
    public TableColumn customerPhoneCol;
    public TableView<Appointment> appointmentsTableView;
    public TableColumn apptIdCol;
    public TableColumn apptTitleCol;
    public TableColumn apptDescriptionCol;
    public TableColumn apptLocationCol;
    public TableColumn apptContactCol;
    public TableColumn apptTypeCol;
    public TableColumn apptStartCol;
    public TableColumn apptEndCol;
    public Button totalAppointmentsReportButton;
    public Button contactScheduleReportButton;
    public RadioButton weekRadioButton;
    public ToggleGroup apptFilterToggleGroup;
    public RadioButton monthRadioButton;
    public Button addCustomerButton;
    public Button modifyCustomerButton;
    public Button deleteCustomerButton;
    public Button addAppointmentButton;
    public Button modifyAppointmentButton;
    public Button deleteAppointmentButton;
    public static Appointment appointmentToModify;
    public static Customer customerToModify;
    public TableColumn userIdCol;
    public TableColumn apptCustomerIdCol;
    public RadioButton allRadioButton;
    public Button logOutButton;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            monthRadioButton.fireEvent(new ActionEvent());

            ObservableList<Appointment> appointments = AppointmentDAO.getAppointments();
            ObservableList<Customer> customers = CustomerDAO.getCustomers();

            customersTableView.setItems(customers);
            customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
            customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            customerAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
            customerDivisionCol.setCellValueFactory(new PropertyValueFactory<>("division"));
            customerCountryCol.setCellValueFactory(new PropertyValueFactory<>("country"));
            customerPostalCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
            customerPhoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));

            appointmentsTableView.setItems(appointments);
            apptIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
            apptTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
            apptDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
            apptLocationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
            apptContactCol.setCellValueFactory(new PropertyValueFactory<>("contactName"));
            apptTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
            apptStartCol.setCellValueFactory(new PropertyValueFactory<>("start"));
            apptEndCol.setCellValueFactory(new PropertyValueFactory<>("end"));
            apptCustomerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
            userIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
            userIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }



    public void onActionTotalAppointments(ActionEvent actionEvent) {
    }

    public void onActionContactSchedule(ActionEvent actionEvent) {
    }

    public void onActionShowWeekAppointments(ActionEvent actionEvent) throws SQLException {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime week = now.plusDays(7);

        ObservableList<Appointment> allAppointments = AppointmentDAO.getAppointments();
        ObservableList<Appointment> weekAppointments = FXCollections.observableArrayList();

        for(Appointment appointment : allAppointments) {
            if (appointment.getStart().isBefore(week)){
                weekAppointments.add(appointment);
            }
        }

        if(weekAppointments.size() < 1){
            appointmentsTableView.setPlaceholder(new Label("No rows to display"));
        }
        else {
            appointmentsTableView.setItems(weekAppointments);
        }
    }

    public void onActionShowMonthAppointments(ActionEvent actionEvent) throws SQLException {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime month = now.plusMonths(1);

        ObservableList<Appointment> allAppointments = AppointmentDAO.getAppointments();
        ObservableList<Appointment> monthAppointments = FXCollections.observableArrayList();

        for(Appointment appointment : allAppointments) {
            if (appointment.getStart().isBefore(month)){
                monthAppointments.add(appointment);
            }
        }

        if(monthAppointments.size() < 1){
            appointmentsTableView.setPlaceholder(new Label("No rows to display"));
        }
        else {
            appointmentsTableView.setItems(monthAppointments);
        }
    }

    public void onActionAddCustomer(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/AddCustomerView.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 585, 400);
        stage.setTitle("Add Customer");
        stage.setScene(scene);
        stage.show();
    }

    public void onActionModifyCustomer(ActionEvent actionEvent) throws IOException {
        customerToModify = customersTableView.getSelectionModel().getSelectedItem();

        if(customerToModify != null) {
            Parent root = FXMLLoader.load(getClass().getResource("/View/ModifyCustomerView.fxml"));
            Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 585, 400);
            stage.setTitle("Add Customer");
            stage.setScene(scene);
            stage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Error");
            alert.setHeaderText("No Selection");
            alert.setContentText("You must make a selection to modify.");
            alert.showAndWait();
        }

    }

    public void onActionDeleteCustomer(ActionEvent actionEvent) throws SQLException {

        Customer customer = customersTableView.getSelectionModel().getSelectedItem();
        ObservableList<Appointment> appointments = AppointmentDAO.getAppointments();


        if(customer == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No Selection");
            alert.setContentText("You must make a selection to delete.");
            alert.showAndWait();
            return;
        }

        boolean hasAppointments = false;

        for(Appointment appointment : appointments){
            if (appointment.getCustomerId() == customer.getCustomerId()){
                hasAppointments = true;
                break;
            }
        }

        if(hasAppointments) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Appointments Found");
            alert.setContentText("Customers with appointments can not be deleted.");
            alert.showAndWait();
            return;
        } else {
            CustomerDAO.deleteCustomer(customer);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Deleted Item");
            alert.setContentText("You have successfully deleted the selected customer.");
            alert.showAndWait();
        }

        ObservableList<Customer> customers = CustomerDAO.getCustomers();
        customersTableView.setItems(customers);
    }

    public void onActionAddAppointment(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/AddAppointmentView.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 725, 590);
        stage.setTitle("Add Appointment");
        stage.setScene(scene);
        stage.show();
    }

    public void onActionModifyAppointment(ActionEvent actionEvent) throws IOException {
        appointmentToModify = appointmentsTableView.getSelectionModel().getSelectedItem();

        if(appointmentToModify != null) {
            Parent root = FXMLLoader.load(getClass().getResource("/View/ModifyAppointmentView.fxml"));
            Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 725, 590);
            stage.setTitle("Add Appointment");
            stage.setScene(scene);
            stage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No Selection");
            alert.setContentText("You must make a selection to modify.");
            alert.showAndWait();
        }

    }

    public void onActionDeleteAppointment(ActionEvent actionEvent) throws SQLException {
        Appointment appointment = appointmentsTableView.getSelectionModel().getSelectedItem();

        if(appointment == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No Selection");
            alert.setContentText("You must make a selection to delete.");
            alert.showAndWait();
            return;
        } else {
            AppointmentDAO.deleteAppointment(appointment);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Deleted Appointment");
            alert.setContentText("Deleted appointment ID: " +
                    appointment.getAppointmentId() + " Type: " + appointment.getType());
            alert.showAndWait();
        }

        ObservableList<Appointment> appointments = AppointmentDAO.getAppointments();
        appointmentsTableView.setItems(appointments);
    }

    public void onActionShowAllAppointments(ActionEvent actionEvent) throws SQLException {
        ObservableList<Appointment> appointments = AppointmentDAO.getAppointments();
        appointmentsTableView.setItems(appointments);
    }

    public void onActionLogOut(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Log Out");
        alert.setContentText("Are you sure you want to log out?");
        alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                Parent root = null;
                try {
                    root = FXMLLoader.load(getClass().getResource("/View/LogInView.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root, 690, 480);
                stage.setTitle("Log In");
                stage.setScene(scene);
                stage.show();
            }
            else {
                alert.close();
            }
        });
    }
}
