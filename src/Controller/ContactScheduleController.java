package Controller;

import DAO.AppointmentDAO;
import DAO.ContactDAO;
import Model.Appointment;
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

public class ContactScheduleController implements Initializable {
    public Label contactScheduleLabel;
    public Button backButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ObservableList<String> schedules = ContactDAO.getContactSchedules();
            String text = "Schedule for each contact:\n\n";

            for(String schedule : schedules) {
                text = text.concat(schedule);
            }

            contactScheduleLabel.setText(text);

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public void onActionBack(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/MainMenuView.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 880, 850);
        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.show();
    }
}
