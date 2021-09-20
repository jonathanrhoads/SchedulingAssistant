package Helper;

import javafx.scene.control.Alert;

public class popUp {

    public popUp(int i) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");

        switch (i) {
            case 1:
                alert.setHeaderText("Username and Password incorrect");
                alert.setContentText("Fix your username and password then try again.");
                alert.showAndWait();
                break;
            case 2:
                alert.setHeaderText("No matches found");
                alert.setContentText("There were no parts matching your input.");
                alert.showAndWait();
                break;
        }

    }
}
