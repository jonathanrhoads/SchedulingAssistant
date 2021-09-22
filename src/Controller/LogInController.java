package Controller;

import DAO.UserDAO;
import Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    public static int currentUserId;
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
    private void onActionLogIn(ActionEvent actionEvent) throws SQLException, IOException {
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        User user = UserDAO.getUser(username);

        if (user != null && user.getPassword().equals(password)){

            logInLogger(username, password, true);
            currentUserId = user.getUserId();

            Parent root = FXMLLoader.load(getClass().getResource("/View/MainMenuView.fxml"));
            Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 880, 850);
            stage.setTitle("Main Menu");
            stage.setScene(scene);
            stage.show();
        }
        else {

            logInLogger(username, password, false);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

            if(Locale.getDefault().getLanguage().equals("fr")){
                alert.setTitle("Erreur");
                alert.setHeaderText("Entrée Incorrecte");
                alert.setContentText("Le nom d’utilisateur ou le mot de passe saisi est incorrect.");
                ButtonType ok = new ButtonType("D’accord");
                alert.getButtonTypes().setAll(ok);
                alert.showAndWait().ifPresent(response -> {
                    if (response == ok) {
                        alert.close();
                    }
                });
            }
            else {
                alert.setTitle("Error");
                alert.setHeaderText("Incorrect Input");
                alert.setContentText("The username or password entered is incorrect.");
                alert.getButtonTypes().setAll(ButtonType.OK);
                alert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        alert.close();
                    }
                });
            }
        }

    }

    private static void logInLogger (String username, String password, boolean success) {
        try {
            ZonedDateTime utcNow = ZonedDateTime.now(ZoneOffset.UTC);
            String successStatus = success ? "SUCCESSFUL" : "FAILED";

            PrintWriter pw = new PrintWriter(new FileOutputStream(new File("login_activity.txt"),true));
            pw.append(successStatus + " LOG IN ATTEMPT --- Username : " + username + " --- Password : " + password + " --- UTC Time : " + utcNow + "\n");
            pw.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(LogInController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
