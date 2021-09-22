package Controller;

import DAO.AppointmentDAO;
import DAO.CustomerDAO;
import Model.Appointment;
import Model.Customer;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {
    public TableView customersTableView;
    public TableColumn customerIdCol;
    public TableColumn customerNameCol;
    public TableColumn customerAddressCol;
    public TableColumn customerDivisionCol;
    public TableColumn customerCountryCol;
    public TableColumn customerPostalCol;
    public TableColumn customerPhoneCol;
    public TableView appointmentsTableView;
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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
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
            apptContactCol.setCellValueFactory(new PropertyValueFactory<>("contactId"));
            apptTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
            apptStartCol.setCellValueFactory(new PropertyValueFactory<>("start"));
            apptEndCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void onActionTotalAppointments(ActionEvent actionEvent) {
    }

    public void onActionContactSchedule(ActionEvent actionEvent) {
    }

    public void onActionShowWeekAppointments(ActionEvent actionEvent) {
    }

    public void onActionShowMonthAppointments(ActionEvent actionEvent) {
    }
}
