package Controller;

import DAO.CountriesDAO;
import DAO.DivisionsDAO;
import Model.Country;
import Model.Customer;
import Model.Division;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddCustomerController implements Initializable {
    public ComboBox countryComboBox;
    public ComboBox divisionComboBox;
    public TextField customerIdTextField;
    public TextField nameTextField;
    public TextField addressTextField;
    public TextField phoneTextField;
    public TextField postalCodeTextField;
    public Button addButton;
    public Button cancelButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ObservableList<Country> countries = CountriesDAO.getCountries();
            ObservableList<String> countryNames = FXCollections.observableArrayList();

            for(Country country : countries){
                countryNames.add(country.getCountry());
            }
            countryComboBox.setItems(countryNames);
            customerIdTextField.setText(String.valueOf(Customer.nextCustomerId()));


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void onActionCountryComboBox(ActionEvent actionEvent) throws SQLException {
        ObservableList<Division> divisions = DivisionsDAO.getDivisions();
        ObservableList<String> countryMatches = FXCollections.observableArrayList();
        ObservableList<Country> countries = CountriesDAO.getCountries();
        String selectedCountry = String.valueOf(countryComboBox.getSelectionModel().getSelectedItem());
        int countryId = 0;
        for(Country country : countries){
            if(selectedCountry.equals(country.getCountry())){
                countryId = country.getCountryId();
            }
        }

        for(Division division : divisions) {
            if(division.getCountryId() == countryId){
                countryMatches.add(division.getDivision());
            }
        }
        divisionComboBox.setItems(countryMatches);
    }

    public void onActionAdd(ActionEvent actionEvent) {
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
