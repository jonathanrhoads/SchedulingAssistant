package Controller;

import DAO.AppointmentDAO;
import DAO.CustomerDAO;
import Model.Appointment;
import Model.Customer;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * The type Totals reports controller.
 */
public class TotalsReportsController implements Initializable {
    /**
     * The Total appointments label.
     */
    public Label totalAppointmentsLabel;
    /**
     * The Total customers label.
     */
    public Label totalCustomersLabel;
    /**
     * The Back button.
     */
    public Button backButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ObservableList<Customer> customers = CustomerDAO.getCustomers();
            ObservableList<Appointment> appointments = AppointmentDAO.getAppointments();

            totalAppointmentsLabel.setText(String.valueOf(appointments.size()));
            totalCustomersLabel.setText(String.valueOf(customers.size()));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * On action back.
     *
     * @param actionEvent the action event
     * @throws IOException the io exception
     */
    public void onActionBack(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/MainMenuView.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 880, 850);
        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.show();
    }
}