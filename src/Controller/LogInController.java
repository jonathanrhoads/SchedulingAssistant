package Controller;

import DAO.AppointmentDAO;
import DAO.UserDAO;
import Model.Appointment;
import Model.User;
import javafx.collections.ObservableList;
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
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The type Log in controller.
 */
public class LogInController implements Initializable {
    /**
     * The Log in button.
     */
    public Button logInButton;
    /**
     * The Exit button.
     */
    public Button exitButton;
    /**
     * The Username text field.
     */
    public TextField usernameTextField;
    /**
     * The Password text field.
     */
    public TextField passwordTextField;
    /**
     * The Title label.
     */
    public Label titleLabel;
    /**
     * The Username label.
     */
    public Label usernameLabel;
    /**
     * The Password label.
     */
    public Label passwordLabel;
    /**
     * The Welcome label.
     */
    public Label welcomeLabel;
    /**
     * The Location label.
     */
    public Label locationLabel;
    /**
     * The Location value label.
     */
    public Label locationValueLabel;
    /**
     * The constant currentUserId.
     */
    public static int currentUserId;
    /**
     * The Rb.
     */
    ResourceBundle rb;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//         Locale.setDefault(Locale.FRENCH); // Uncomment to test FRENCH
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
    /**
     * This method creates an alert to verify if the user wants to exit from the program or continue
     * on the current screen. It does this by  creating two buttons, one for yes and one for no.
     * LAMBDA: The lambda is used here to grab the response from within the alert and follow the logic to exit
     * if the response is yes and continue on the log in screen if the response is no.
     * @param actionEvent actionEvent
     */
    public void onActionExit(ActionEvent actionEvent) {
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

    /**
     * This method checks for upcoming appointments within the next 15 minutes. It also handles the french language
     * based off of the users default zone.
     * @throws SQLException SQLException
     */
    private void upcomingAppointments() throws SQLException {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime fifteenOut = now.plusMinutes(15);
        ObservableList<Appointment> allAppointments = AppointmentDAO.getAppointments();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        if(Locale.getDefault().getLanguage().equals("fr")){
            alert.setTitle("Alerte");
            for(Appointment appointment : allAppointments) {
                if(appointment.getStart().isBefore(fifteenOut) && appointment.getStart().isAfter(now)){
                    alert.setHeaderText("Nomination à venir");
                    alert.setContentText("Identification des rendez-vous: " +
                            appointment.getAppointmentId() + " commence à " + dtf.format(appointment.getStart()));
                    alert.showAndWait();
                    return;
                }
            }
            alert.setHeaderText("Nomination à venir");
            alert.setContentText("Il n’y a pas de rendez-vous à venir.");
            alert.showAndWait();

        } else {
            alert.setTitle("Alert");
            for(Appointment appointment : allAppointments) {
                if(appointment.getStart().isBefore(fifteenOut) && appointment.getStart().isAfter(now)){
                    alert.setHeaderText("Upcoming Appointment");
                    alert.setContentText("Appointment ID: " +
                            appointment.getAppointmentId() + " starts at " + dtf.format(appointment.getStart()));
                    alert.showAndWait();
                    return;
                }
            }

            alert.setHeaderText("Upcoming Appointment");
            alert.setContentText("There are no upcoming appointments.");
            alert.showAndWait();
        }
    }

    /**
     * This method compares the selected username to the password that corresponds with it inside the database.
     * If the combination is correct then the user will be forwarded to the main menu screen.
     * @param actionEvent actionEvent
     * @throws SQLException SQLException
     * @throws IOException IOException
     */
    public void onActionLogIn(ActionEvent actionEvent) throws SQLException, IOException {
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        User user = UserDAO.getUser(username);

        if (user != null && user.getPassword().equals(password)){

            logInLogger(username, password, true);
            currentUserId = user.getUserId();
            upcomingAppointments();

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

    /**
     * This method is called when there is a log in attempt. It will determine if the log in was successful based off
     * the boolean value passed in then log that to the login_activity.txt file with specific information. If there is no
     * file then one will be created. If it is already there then the next activity will be appended on a new line.
     *
     * @param username The attempted username.
     * @param password The attempted password.
     * @param success Boolean whether the login attempt was successful or not.
     */
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
