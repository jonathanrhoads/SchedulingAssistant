package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
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
    public TableView customersTableView1;
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
