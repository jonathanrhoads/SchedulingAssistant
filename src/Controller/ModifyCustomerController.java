package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ModifyCustomerController implements Initializable {
    public ComboBox countryComboBox;
    public ComboBox divisionComboBox;
    public TextField customerIdTextField;
    public TextField nameTextField;
    public TextField addressTextField;
    public TextField phoneTextField;
    public TextField postalCodeTextField;
    public Button saveButton;
    public Button cancelButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void onActionCountryComboBox(ActionEvent actionEvent) {
    }

    public void onActionSave(ActionEvent actionEvent) {
    }

    public void onActionCancel(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/MainMenuView.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 880, 850);
        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.show();
    }
}
