package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

public class LogInController implements Initializable {
    public Button logInButton;
    public Button exitButton;
    public TextField usernameTextField;
    public TextField passwordTextField;
    public Label titleLabel;
    public Label usernameLabel;
    public Label passwordLabel;
    public Label welcomeLabel;
    public Label locationLabel;
    public Label locationValueLabel;
    ResourceBundle rb;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Locale.setDefault(Locale.FRENCH); // Uncomment to test FRENCH
        rb = ResourceBundle.getBundle("Helper/Nat", Locale.getDefault());
        locationValueLabel.setText(ZoneId.systemDefault().toString());
        titleLabel.setText(rb.getString("title"));
        welcomeLabel.setText(rb.getString("welcome"));
        locationLabel.setText(rb.getString("location"));
        usernameLabel.setText(rb.getString("username"));
        passwordLabel.setText(rb.getString("password"));
        logInButton.setText(rb.getString("login"));
        exitButton.setText(rb.getString("exit"));

    }

    @FXML
    private void onActionExit(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        if(Locale.getDefault().getLanguage().equals("fr")){
            alert.setTitle("Sortie");
            alert.setHeaderText("Programme de sortie");
            alert.setContentText("Êtes-vous sûr de vouloir quitter?");
            ButtonType oui = new ButtonType("Oui");
            ButtonType pas = new ButtonType("Pas");
            alert.getButtonTypes().setAll(oui, pas);
            alert.showAndWait().ifPresent(response -> {
                if (response == oui) {
                    System.exit(0);
                }
                else {
                    alert.close();
                }
            });
        }
        else {
            alert.setTitle("Exit");
            alert.setHeaderText("Exit Program");
            alert.setContentText("Are you sure you want to exit?");
            alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.YES) {
                    System.exit(0);
                }
                else {
                    alert.close();
                }
            });
        }
    }

    @FXML
    private void onActionLogIn(ActionEvent actionEvent) {

    }
}
