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

/**
 * The type Main menu controller.
 */
public class MainMenuController implements Initializable {
    /**
     * The Customers table view.
     */
    public TableView<Customer> customersTableView;
    /**
     * The Customer id col.
     */
    public TableColumn customerIdCol;
    /**
     * The Customer name col.
     */
    public TableColumn customerNameCol;
    /**
     * The Customer address col.
     */
    public TableColumn customerAddressCol;
    /**
     * The Customer division col.
     */
    public TableColumn customerDivisionCol;
    /**
     * The Customer country col.
     */
    public TableColumn customerCountryCol;
    /**
     * The Customer postal col.
     */
    public TableColumn customerPostalCol;
    /**
     * The Customer phone col.
     */
    public TableColumn customerPhoneCol;
    /**
     * The Appointments table view.
     */
    public TableView<Appointment> appointmentsTableView;
    /**
     * The Appt id col.
     */
    public TableColumn apptIdCol;
    /**
     * The Appt title col.
     */
    public TableColumn apptTitleCol;
    /**
     * The Appt description col.
     */
    public TableColumn apptDescriptionCol;
    /**
     * The Appt location col.
     */
    public TableColumn apptLocationCol;
    /**
     * The Appt contact col.
     */
    public TableColumn apptContactCol;
    /**
     * The Appt type col.
     */
    public TableColumn apptTypeCol;
    /**
     * The Appt start col.
     */
    public TableColumn apptStartCol;
    /**
     * The Appt end col.
     */
    public TableColumn apptEndCol;
    /**
     * The Total appointments report button.
     */
    public Button totalAppointmentsReportButton;
    /**
     * The Contact schedule report button.
     */
    public Button contactScheduleReportButton;
    /**
     * The Week radio button.
     */
    public RadioButton weekRadioButton;
    /**
     * The Appt filter toggle group.
     */
    public ToggleGroup apptFilterToggleGroup;
    /**
     * The Month radio button.
     */
    public RadioButton monthRadioButton;
    /**
     * The Add customer button.
     */
    public Button addCustomerButton;
    /**
     * The Modify customer button.
     */
    public Button modifyCustomerButton;
    /**
     * The Delete customer button.
     */
    public Button deleteCustomerButton;
    /**
     * The Add appointment button.
     */
    public Button addAppointmentButton;
    /**
     * The Modify appointment button.
     */
    public Button modifyAppointmentButton;
    /**
     * The Delete appointment button.
     */
    public Button deleteAppointmentButton;
    /**
     * The constant appointmentToModify.
     */
    public static Appointment appointmentToModify;
    /**
     * The constant customerToModify.
     */
    public static Customer customerToModify;
    /**
     * The User id col.
     */
    public TableColumn userIdCol;
    /**
     * The Appt customer id col.
     */
    public TableColumn apptCustomerIdCol;
    /**
     * The All radio button.
     */
    public RadioButton allRadioButton;
    /**
     * The Log out button.
     */
    public Button logOutButton;
    /**
     * The Cust report button.
     */
    public Button custReportButton;
    /**
     * The Totals reports.
     */
    public Button totalsReports;


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

    /**
     * On action customer appointment report.
     *
     * @param actionEvent the action event
     * @throws IOException the io exception
     */
    public void onActionCustomerAppointmentReport(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/CustomerAppointmentsReportView.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 400, 400);
        stage.setTitle("Customer Appointment Report");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * On action totals.
     *
     * @param actionEvent the action event
     * @throws IOException the io exception
     */
    public void onActionTotals(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/TotalsReportsView.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 350, 185);
        stage.setTitle("Totals Report");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * On action contact schedule.
     *
     * @param actionEvent the action event
     * @throws IOException the io exception
     */
    public void onActionContactSchedule(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/ContactScheduleView.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 700, 400);
        stage.setTitle("Contact Schedule Report");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * On action show week appointments.
     *
     * @param actionEvent the action event
     * @throws SQLException the sql exception
     */
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

    /**
     * On action show month appointments.
     *
     * @param actionEvent the action event
     * @throws SQLException the sql exception
     */
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

    /**
     * On action add customer.
     *
     * @param actionEvent the action event
     * @throws IOException the io exception
     */
    public void onActionAddCustomer(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/AddCustomerView.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 585, 400);
        stage.setTitle("Add Customer");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * On action modify customer.
     *
     * @param actionEvent the action event
     * @throws IOException the io exception
     */
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

    /**
     * On action delete customer.
     * LAMBDA: The lambda used in this method reduces the number of lines of code needed.
     * It creates a boolean value based off if there are Appointments containing the selected customer's ID.
     * The code uses a filtered list to temporarily store the matching appointment objects.
     *
     * @param actionEvent the action event
     * @throws SQLException the sql exception
     */
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

        boolean hasAppointments = (appointments.filtered(a -> a.getCustomerId() == customer.getCustomerId())).size() > 1;

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

    /**
     * On action add appointment.
     *
     * @param actionEvent the action event
     * @throws IOException the io exception
     */
    public void onActionAddAppointment(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/AddAppointmentView.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 725, 590);
        stage.setTitle("Add Appointment");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * On action modify appointment.
     *
     * @param actionEvent the action event
     * @throws IOException the io exception
     */
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

    /**
     * On action delete appointment.
     *
     * @param actionEvent the action event
     * @throws SQLException the sql exception
     */
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

    /**
     * On action show all appointments.
     *
     * @param actionEvent the action event
     * @throws SQLException the sql exception
     */
    public void onActionShowAllAppointments(ActionEvent actionEvent) throws SQLException {
        ObservableList<Appointment> appointments = AppointmentDAO.getAppointments();
        appointmentsTableView.setItems(appointments);
    }

    /**
     * On action log out.
     *
     * @param actionEvent the action event
     */
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
